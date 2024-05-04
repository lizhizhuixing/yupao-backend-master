package com.yupi.yupao.service;

import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class WatchDogTest {
    @Resource
    private RedissonClient redissonClient;

    @Test
    void testWatchDog() {
//        RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
//        try {
//            // 只有一个线程能获取到锁
//            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
//                Thread.sleep(300000);//todo 实际要执行的代码
//                System.out.println("getLock: " + Thread.currentThread().getId());
//            }
//        } catch (InterruptedException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // 只能释放自己的锁
//            if (lock.isHeldByCurrentThread()) {
//                System.out.println("unLock: " + Thread.currentThread().getId());
//                lock.unlock();
//            }
//        }
//    }
        RLock lock = redissonClient.getLock("huojishashou:waatchDog:lock");
        try {
            if (lock.tryLock(0,-1,TimeUnit.MILLISECONDS)){
                Thread.sleep(30000L);
                System.out.println("getLock: " + Thread.currentThread().getId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock:" + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
