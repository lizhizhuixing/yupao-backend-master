package com.yupi.yupao.service;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedissonTest {
    @Resource
    private RedissonClient redissonClient;
    @Test
    public void RedissonClientTest(){
        //List存在本地jvm中
        ArrayList<String> list = new ArrayList<>();
        list.add("yupao");
        System.out.println("list:" + list.get(0));
        //redisson存在本地内存中
        RList<String> huoji = redissonClient.getList("huoji");
        huoji.add("huojishashou");
        huoji.get(0);
        huoji.remove(0);
    }
}
