package com.yupi.yupao.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yupao.model.domain.User;
import com.yupi.yupao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class PreCachejob {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserService userService;
    @Resource
    private RedissonClient redissonClient;
    //重点用户
    private List<Long> mainUser = Arrays.asList(1L);

    //    @Scheduled(cron = "0 54 11 * * ? ")
//    public void doCacheRecommendUsers(){
//        String redisKey = String.format("huoji:user:recommend:%s",mainUser);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        Page<User> userPage = userService.page(new Page<>(1,10), queryWrapper);
//        try {
//            valueOperations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
//
//        } catch (Exception e){
//            log.error("redis set key error",e);
//        }
//    }
//}
    @Scheduled(cron = "0 2 11 * * ? ")
    public void doCacheRecommendUsers() {
        RLock lock = redissonClient.getLock("huoji:precachejob:docache:lock");
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                System.out.println("getLock: " + Thread.currentThread().getId());
                for (Long userId : mainUser) {
                    String redisKey = String.format("huoji:user:recommend:%s", userId);
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1, 10), queryWrapper);
                    try {
                        valueOperations.set(redisKey, userPage, 30000, TimeUnit.MILLISECONDS);

                    } catch (Exception e) {
                        log.error("redis set key error", e);
                    }
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                System.out.println("unLock: " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
