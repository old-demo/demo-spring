package com.heqing.demo.spring.redisson;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redisson.config.SpringRedissonConfig;
import com.heqing.demo.spring.redisson.model.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringRedissonConfig.class
)
@ActiveProfiles("single")
public class RedissonCollectionsTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testMap() {
        RMap<String, People> map = redissonClient.getMap("testMap");
        // 以异步方式将指定值与指定键关联。
        People prevObject = map.put("1", new People(1L, "test1"));
        // 仅当与指定键没有任何关联时，才将指定值与指定键关联。
        People currentObject = map.putIfAbsent("2", new People(2L, "test2"));
        // 删除指定键
        People obj = map.remove("2");

        Set<String> ids = new HashSet();
        ids.add("1");ids.add("2");
        Map<String, People> result =  map.getAll(ids);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testMapCache() {
        RMapCache<String, People> map = redissonClient.getMapCache("testMapCache");
        // 有效时间 ttl = 10分钟
        map.put("key1", new People(1L, "test1"), 10, TimeUnit.MINUTES);
        // 有效时间 ttl = 10分钟, 最长闲置时间 maxIdleTime = 10秒钟
        map.put("key2", new People(2L, "test2"), 10, TimeUnit.MINUTES, 10, TimeUnit.SECONDS);

        Set<String> ids = new HashSet();
        ids.add("key1");ids.add("key2");
        Map<String, People> result =  map.getAll(ids);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testSet() {
        // 有序集
        RSet<People> set = redissonClient.getSet("testSet");
        set.add(new People(1L, "test1"));
        People people = set.random();
        System.out.println("-->"+ JSONObject.toJSONString(people));

        set.remove(new People(1L, "test1"));
        people = set.random();
        System.out.println("-->"+ JSONObject.toJSONString(people));
    }

    @Test
    public void testSortedSet() {
        // 计分排序集
        RSortedSet<Integer> set = redissonClient.getSortedSet("anySet");
        set.add(3);
        set.add(1);
        set.add(2);
        System.out.println("-->"+ JSONObject.toJSONString(set.readAll()));

        RSortedSet<People> setP = redissonClient.getSortedSet("testSortedSetPeopel");
        setP.trySetComparator(new Comparator() {
            public int compare(Object o1, Object o2) {
                //强制类型转换来调用函数里price变量，再来比较
                long id1 = ((People) o1).getId();
                long id2 = ((People) o2).getId();
                if (id1 == id2) {
                    return 0;
                } else if (id1 < id2) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }); // 配置元素比较器
        setP.add(new People(3L, "test3"));
        setP.add(new People(1L, "test1"));
        setP.add(new People(2L, "test2"));
        System.out.println("-->"+ JSONObject.toJSONString(setP.readAll()));
    }

    @Test
    public void testScoredSortedSet() {
        // 字典排序集
        RScoredSortedSet<String> set = redissonClient.getScoredSortedSet("testScoredSortedSet");
        set.add(0.13, "test1");
        set.addAsync(0.251, "test2");
        set.add(0.302, "test3");
        Collection<String> setList = set.readAll();
        System.out.println("-->"+ JSONObject.toJSONString(setList));

        String first = set.pollFirst();
        System.out.println("-->"+ first);

        String last = set.pollLast();
        System.out.println("-->"+ last);

        int index = set.rank("test2"); // 获取元素在集合中的位置
        System.out.println("-->"+ index);

        Double score = set.getScore("test2"); // 获取元素的评分
        System.out.println("-->"+ score);
    }

    @Test
    public void testLexSortedSet() {
        //  字典排序集
        RLexSortedSet set = redissonClient.getLexSortedSet("testLexSortedSet");
        set.add("q");
        set.addAsync("h");
        set.add("a");
        System.out.println("-->"+ JSONObject.toJSONString(set.readAll()));
    }

    @Test
    public void testList() {
        // list集合
        RList<String> list = redissonClient.getList("testList");
        list.add("test");
        String value = list.get(0);
        System.out.println("-->"+ value);

        list.remove("test");
        value = list.get(0);
        System.out.println("-->"+ value);
    }

    @Test
    public void testQueue() {
        // 队列
        RQueue<String> queue = redissonClient.getQueue("testQueue");
        queue.add("test1");
        queue.add("test2");
        String peek = queue.peek();
        System.out.println("-->"+ peek);

        String poll = queue.poll();
        System.out.println("-->"+ poll);
    }

    @Test
    public void testDeque() {
        // 双端队列
        RDeque<String> queue = redissonClient.getDeque("testDeque");
        queue.addFirst("test1");
        queue.addLast("test2");
        String peek =queue.removeFirst();
        System.out.println("-->"+ peek);

        String poll = queue.removeLast();
        System.out.println("-->"+ poll);
    }

    @Test
    public void testDelayedQueue() {
        // 延迟队列
        RQueue<String> distinationQueue = redissonClient.getDeque("testDelayedQueue");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(distinationQueue);
        // 10秒钟以后将消息发送到指定队列
        delayedQueue.offer("msg1", 10, TimeUnit.SECONDS);
        // 一分钟以后将消息发送到指定队列
        delayedQueue.offer("msg2", 20, TimeUnit.MINUTES);
//        delayedQueue.destroy();
    }

}
