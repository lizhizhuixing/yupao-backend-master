package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;
    //@Test
    void curdRedisTemplate(){
        //增
        ValueOperations opsForValue = redisTemplate.opsForValue();
        opsForValue.set("huojiString","chicken");
        opsForValue.set("huojiInt",1);
        opsForValue.set("huojiDouble",2.0);
        User user = new User();
        user.setId(1L);
        user.setUsername("huoji");
        opsForValue.set("huojiUser",user);
        //查
        Object huoji = opsForValue.get("huojiString");
        Assertions.assertTrue("chicken".equals((String) huoji));
        huoji = opsForValue.get("huojiInt");
        Assertions.assertTrue(1 == (Integer) huoji);
        huoji = opsForValue.get("huojiDouble");
        Assertions.assertTrue(2.0 == (Double) huoji);
        System.out.println(opsForValue.get("huojiUser"));
    }
}
