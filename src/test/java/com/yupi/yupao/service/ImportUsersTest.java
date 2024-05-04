package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@SpringBootTest
public class ImportUsersTest {
    @Resource
    private UserService userService;
    ExecutorService executorService = new ThreadPoolExecutor(16, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
 //@Test
    public void doCurrentInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 10000;
        //分10组
        int batchSize = 1000;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < INSERT_NUM / batchSize; i++) {
            ArrayList<User> userList = new ArrayList<>();
            while (true) {
                for (; i < batchSize; i++) {
                    j++;
                    User user = new User();
                    user.setUsername("假火鸡杀手");
                    user.setUserAccount("huoji");
                    user.setAvatarUrl("https://tse3-mm.cn.bing.net/th/id/OIP-C.dA6WdZ6sUB2oN6pkPEk2HgHaGG?w=197&h=180&c=7&r=0&o=5&dpr=1.3&pid=1.7");
                    user.setGender(0);
                    user.setUserPassword("123456789");
                    user.setPhone("15834765547");
                    user.setEmail("https://github.com/lizhizhuixing");
                    user.setUserStatus(0);
                    user.setIsDelete(0);
                    user.setTags("[]");
                    user.setUserRole(0);
                    user.setPlanetCode("720");
                    userList.add(user);
                }
                if (j % 1000 == 0) {
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, 1000);
            }, executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }
}