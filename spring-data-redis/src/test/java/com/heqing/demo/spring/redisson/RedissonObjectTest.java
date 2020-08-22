package com.heqing.demo.spring.redisson;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redisson.config.SpringRedissonConfig;
import com.heqing.demo.spring.redisson.model.People;
import com.heqing.demo.spring.redisson.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = SpringRedissonConfig.class
)
@ActiveProfiles("single")
public class RedissonObjectTest {

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void test() {
        RBucket key = redissonClient.getBucket("redissonType");
        key.set("single");

        key = redissonClient.getBucket("redissonType");
        System.out.println("-->"+key.get());
    }

    @Test
    public void testKety() {
        RKeys keys = redissonClient.getKeys();

        // 删除test开头的，且后面只有1位的
        long deletedKeysAmount = keys.deleteByPattern("test?");
        System.out.println("deleteByPattern -->" + deletedKeysAmount);

        // 删除key
        long numOfDeletedKeys = keys.delete("redissonType", "redisType");
        System.out.println("numOfDeletedKeys -->" + numOfDeletedKeys);

        // 获取所有key 名
        Iterable<String> allKeys = keys.getKeys();
        System.out.println("getKeys -->" + JSONObject.toJSONString(allKeys));

        // 获取所有test开头的key
        Iterable<String> foundedKeys = keys.getKeysByPattern("test*");
        System.out.println("getKeysByPattern -->" + JSONObject.toJSONString(foundedKeys));

        // 随机获取一个key
        String randomKey = keys.randomKey();
        System.out.println("randomKey -->" + randomKey);

        // 获取key的数量
        long keysAmount = keys.count();
        System.out.println("keysAmount -->" + keysAmount);
    }

    @Test
    public void tesObject() {
        People people1 = new People();
        people1.setId(1L);
        people1.setName("测试1");
        people1.setGender("M");
        people1.setAge(30);
        people1.setCreateTime(new Date());

        People people2 = people1;
        people2.setId(2L);
        people2.setName("测试2");

        RBucket<People> bucket = redissonClient.getBucket("people:1");
        // 将对象存入redis中
        bucket.set(people1);
        People obj1 = bucket.get();
        System.out.println("getBucket -->" + JSONObject.toJSONString(obj1));

        bucket = redissonClient.getBucket("people:2");
        // 只有key不存在的时候才存入
        bucket.trySet(people2);
        obj1 = bucket.get();
        System.out.println("trySet -->" + JSONObject.toJSONString(obj1));

        // 原值替换桶的新值为var2
        bucket.compareAndSet(people1, people2);
        obj1 = bucket.get();
        System.out.println("compareAndSet -->" + JSONObject.toJSONString(obj1));

        // 有值便返回原来的值，没有则存入新值并返回
        People people = bucket.getAndSet(people1);
        System.out.println("getAndSet -->" + JSONObject.toJSONString(people));

        // 获取多个key的值
        RBuckets buckets = redissonClient.getBuckets();
        Map<String, People> peopleList = buckets.get("people:1", "people:2");
        System.out.println("get -->" + JSONObject.toJSONString(peopleList));

        // 返回并删除值
        bucket = redissonClient.getBucket("people:2");
        // 只有key不存在的时候才存入
        bucket.getAndDelete();
        obj1 = bucket.get();
        System.out.println("getAndDelete -->" + JSONObject.toJSONString(obj1));
    }

    @Test
    public void testStream() {
        RBinaryStream stream = redissonClient.getBinaryStream("img");

        try {
            OutputStream os = stream.getOutputStream();
            byte[] writeBuffer = FileUtil.file2byte("D:\\test\\timg.jpg");
            os.write(writeBuffer);
            System.out.println("length -->"+ writeBuffer.length);
            os.flush();
            os.close();

            InputStream is = stream.getInputStream();
            byte[] readBuffer = new byte[35339];
            is.read(readBuffer);
            FileUtil.byte2file(readBuffer,"D:\\test\\test.jpg");
            is.close();
        } catch (Exception e) {
            e.getMessage();
        } finally {

        }
    }

    @Test
    public void testGeospatial() {
        RGeo<String> geo = redissonClient.getGeo("testGeo");
        geo.add(37.618423, 55.751244, "Moscow");
        geo.add(new GeoEntry(13.361389, 38.115556, "Palermo"),
                new GeoEntry(15.087269, 37.502669, "Catania"));

        // 获取两个地址间的距离
        Double distance = geo.dist("Palermo", "Catania", GeoUnit.METERS);
        System.out.println("dist -->"+distance);

        // 获取地名的坐标
        Map<String, String> hash = geo.hash("Palermo", "Catania");
        System.out.println("hash -->"+hash);

        // 从键里面返回所有给定位置元素的位置
        Map<String, GeoPosition> positions = geo.pos( "Palermo", "Catania", "test1");
        System.out.println("pos -->"+positions);

        // 注意：以下方法 redis 3.2已上的版本才支持
        //获取给定地点范围内的 地点信息
        List<String> radius = geo.radius(15, 37, 200, GeoUnit.KILOMETERS);
        System.out.println("radius -->"+radius);

        Map<String, GeoPosition> citiesWithPositions = geo.radiusWithPosition(15, 37, 200, GeoUnit.KILOMETERS);
        System.out.println("radiusWithPosition -->"+citiesWithPositions);
    }


    @Test
    public void testBitSet() {
        RBitSet set = redissonClient.getBitSet("testBitSet");
        set.set(0, true);
        set.set(1812, false);
        set.clear(0);
        set.and("e");
        set.xor("anotherBitset");
    }

    @Test
    public void testAtomicLong() {
        RAtomicLong atomicLong = redissonClient.getAtomicLong("testAtomicLong");
        // 设置值
        atomicLong.set(3);
        long value = atomicLong.get();
        System.out.println("-->"+value);

        // 自增1
        atomicLong.incrementAndGet();
        value = atomicLong.get();
        System.out.println("-->"+value);

        // 加上传入值
        atomicLong.addAndGet(5);
        value = atomicLong.get();
        System.out.println("-->"+value);

        // 自减1
        atomicLong.decrementAndGet();
        value = atomicLong.get();
        System.out.println("-->"+value);
    }

    @Test
    public void testAtomicDouble() {
        RAtomicDouble atomicDouble = redissonClient.getAtomicDouble("testAtomicDouble");
        atomicDouble.set(2.81);
        double value = atomicDouble.get();
        System.out.println("-->"+value);

        atomicDouble.addAndGet(4.11);
        value = atomicDouble.get();
        System.out.println("-->"+value);
    }

    @Test
    public void testBloomFilter() {
        RBloomFilter<People> bloomFilter = redissonClient.getBloomFilter("sample");
        // 初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        bloomFilter.add(new People(1L, "test1"));
        bloomFilter.add(new People(2L, "test2"));
        bloomFilter.contains(new People(3L, "test3"));
    }

    @Test
    public void testHyperLogLog() {
        RHyperLogLog<Integer> log = redissonClient.getHyperLogLog("testHyperLogLog");
        log.add(1);
        log.add(2);
        log.add(3);

        long count = log.count();
        System.out.println("-->"+count);
    }

    @Test
    public void testRateLimiter() {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("testRateLimiter");
        // 初始化
        // 最大流速 = 每1秒钟产生10个令牌
        rateLimiter.trySetRate(RateType.OVERALL, 10, 1, RateIntervalUnit.SECONDS);

        CountDownLatch latch = new CountDownLatch(2);
        rateLimiter.acquire(3);
    }

    @Test
    public void testTopic() {
        RTopic topic = redissonClient.getTopic("testTopic");
        long clientsReceivedMessage = topic.publish("写代码中");
        System.out.println("-->"+clientsReceivedMessage);
    }
}
