package com.heqing.demo.spring.redis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redis.config.SpringJedisConfig;
import com.heqing.demo.spring.redis.config.SpringLettuceConfig;
import com.heqing.demo.spring.redis.repository.RedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {SpringLettuceConfig.class, SpringJedisConfig.class}
)
/**
  *  第一个参数指定 客户端    jedis / lettuce
 *   第二个参数指定 连接模式  single(单例) / sentinel(哨兵) / cluster(集群)
  */
@ActiveProfiles({"lettuce","single"})
public class RedisTest {

    @Autowired
    RedisRepository redisRepository;

    @Test
    public void test() {
        // 测试连接模式 单例/哨兵/集群
//        redisDao.set("deploy", "single");
        Object result = redisRepository.get("deploy");
        System.out.println("-->"+result);
    }

    @Test
    public void testSet() {
        redisRepository.set("test", "heqing");
        redisRepository.set("test1", "shiyan", 1, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        Object result = redisRepository.get("test");
        System.out.println("-->"+result);
    }
    @Test
    public void testSetGet() {
        Object result = redisRepository.getSet("test", "shiyan");
        System.out.println("-->"+result);
    }

    @Test
    public void testDelete() {
        Boolean result = redisRepository.delete("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testExpire() {
        Boolean result = redisRepository.expire("test", 1, TimeUnit.MINUTES);
        System.out.println("-->"+result);
    }

    @Test
    public void testExpireAt() {
        long time = System.currentTimeMillis() + 1000;
        Boolean result = redisRepository.expireAt("test1", new Date(time));
        System.out.println("-->"+result);
    }

    @Test
    public void testPersist() {
        Boolean result = redisRepository.persist("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testDump() {
        byte[] bytes = redisRepository.dump("test");
        if(bytes != null && bytes.length > 0) {
            System.out.println(bytes+"-->"+bytes.length);
        } else {
            System.out.println("-->null");
        }
    }

    @Test
    public void testCountExistingKeys() {
        Boolean result = redisRepository.countExistingKeys("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testKeys() {
        Set<String> result = redisRepository.keys("test*");
        System.out.println("-->"+result);
    }

    @Test
    public void testMove() {
        Boolean result = redisRepository.move("test1", 1);
        System.out.println("-->"+result);
    }

    @Test
    public void testRandomKey() {
        Object result = redisRepository.randomKey();
        System.out.println("-->"+result);
    }

    @Test
    public void testRename() {
        redisRepository.rename("test1", "test");
    }

    @Test
    public void testType() {
        DataType result = redisRepository.type("test");
        System.out.println("-->"+ result);
    }

    @Test
    public void testAppend() {
        int result = redisRepository.append("test1", "123");
        System.out.println("-->"+ result);
    }

    @Test
    public void testDecrement() {
        Long result = redisRepository.decrement("test1");
        System.out.println("-->"+ result);

        result = redisRepository.decrement("test1", 10);
        System.out.println("-->"+ result);
    }

    @Test
    public void testIncrement() {
        Long result = redisRepository.increment("test1");
        System.out.println("-->"+ result);

        result = redisRepository.increment("test1", 10);
        System.out.println("-->"+ result);

        Double resultD = redisRepository.increment("test1", 3.1415);
        System.out.println("-->"+ resultD);
    }

    @Test
    public void testRange() {
        String result = redisRepository.range("test", 0, -2);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPush() {
        Long result = redisRepository.lLeftPush("testList", "test1");
        System.out.println("-->"+ result);

        result = redisRepository.lLeftPushAll("testList", "test2", "test3");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPush() {
        Long result = redisRepository.lRightPush("testList", "test4");
        System.out.println("-->"+ result);

        result = redisRepository.lRightPushAll("testList", "test5", "test6");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLIndex() {
        Object result = redisRepository.lIndex("testList", 1);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLSize() {
        Long result = redisRepository.lSize("testList");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPop() {
        Object result = redisRepository.lLeftPop("testList");
        System.out.println("-->"+ result);

        result = redisRepository.lLeftPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPop() {
        Object result = redisRepository.lRightPop("testList");
        System.out.println("-->"+ result);

        result = redisRepository.lRightPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRange() {
        List<Object> result = redisRepository.lRange("testList", 1, -1);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLRemove() {
        Long result = redisRepository.lRemove("testList", 1, "test4");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLSet() {
        redisRepository.lSet("testList", 1, "test4");
    }

    @Test
    public void testLTrim() {
        redisRepository.lTrim("testList", 0, 1);
    }

    @Test
    public void testsSize() {
        Long result = redisRepository.sSize("testSet");
        System.out.println("-->"+ result);
    }

    @Test
    public void testsAdd() {
        redisRepository.sAdd("testSet", "test1", "test2");
        redisRepository.sAdd("testSet0", "test1", "test2", "test3");
        Long result = redisRepository.sAdd("testSet1", "test1",  "test3");

        System.out.println("-->"+ result);
    }

    @Test
    public void testsMembers() {
        Set<Object> set = redisRepository.sMembers("testSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsIsMember() {
        Boolean set = redisRepository.sIsMember("testSet", "test3");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsDiff() {
        List<String> list = Arrays.asList("testSet1");
        Set<Object> set = redisRepository.sDiff("testSet0", list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList( "testSet1");
        redisRepository.sDiffAndStore("testSet0", list, "testSetStore1");
    }

    @Test
    public void testsIntersect() {
        List<String> list = Arrays.asList("testSet0", "testSet1");
        Set<Object> set = redisRepository.sIntersect("testSet", list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet1", "testSet0");
        redisRepository.sIntersectAndStore("testSet", list, "testSetStore2");
    }

    @Test
    public void testsUnion() {
        List<String> list = Arrays.asList("testSet0", "testSet1");
        Set<Object> set = redisRepository.sUnion("testSet", list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet1", "testSet0");
        redisRepository.sUnionStore("testSet", list, "testSetStore3");
    }

    @Test
    public void testsMove() {
        Boolean set = redisRepository.sMove("testSet0", "testSet1", "test21");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsPop() {
        Object set = redisRepository.sPop("testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRandember() {
        Object set = redisRepository.sRandember("testSet1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRemove() {
        Object set = redisRepository.sRemove("testSet1", "test1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzAdd() {
        for(int i=0; i<3; i++) {
            Object set = redisRepository.zAdd("testSortedSet", "heqing"+i, 10+i);
            System.out.println("-->"+ JSONObject.toJSONString(set));
        }
    }

    @Test
    public void testzCard() {
        Object set = redisRepository.zCard("testSortedSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzCount() {
        Object set = redisRepository.zCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIncrby() {
        Object set = redisRepository.zIncrby("testSortedSet", "heqing", -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRange() {
        Set<Object> set = redisRepository.zRange("testSortedSet", 1, -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRangeByScore() {
        Set<Object> set = redisRepository.zRangeByScore("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRank() {
        Long set = redisRepository.zRank("testSortedSet", "heqing2");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemove() {
        Long set = redisRepository.zRemove("testSortedSet", "heqing");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRange() {
        Long set = redisRepository.zRemoveRange("testSortedSet", 1,3);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRangeByScore() {
        Long set = redisRepository.zRemoveRangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRange() {
        Set<Object> set = redisRepository.zReverseRange("testSortedSet", 1,2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRevrangeByScore() {
        Set<Object> set = redisRepository.zRevrangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRank() {
        Long set = redisRepository.zReverseRank("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzScore() {
        Double set = redisRepository.zScore("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzUunionAndStore() {
        List<String> keys = Arrays.asList("testSortedSet", "testSortedSet1");
        Long set = redisRepository.zUnionAndStore("testSortedSet", keys, "zUunionAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIntersectAndStore() {
        List<String> keys = Arrays.asList("testSortedSet1");
        Long set = redisRepository.zIntersectAndStore("testSortedSet", keys, "zIntersectAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzLexCount() {
        Long set = redisRepository.zLexCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testhPutAll() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "heqing");
        info.put("age", 30);
        info.put("qq", "975656343");
//        info.put("wechat", "hq0556246512");
        redisRepository.hPutAll("testMap", info);
    }

    @Test
    public void testhPut() {
        redisRepository.hPut("testMap", "wechat", "hq0556246512");
    }

    @Test
    public void testhDel() {
        Long result = redisRepository.hDelete("testMap", "wechat");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhExists() {
        Boolean result = redisRepository.hasKey("testMap", "qq");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhGet() {
        Object result = redisRepository.hGet("testMap", "qq");
        System.out.println("-->"+ result);

        Map<Object, Object> map = redisRepository.hGet("testMap");
        System.out.println("-->"+ map);
    }

    @Test
    public void testhincrBy() {
        Long result = redisRepository.hIncrement("testMap", "age", 2);
        System.out.println("-->"+ result);

        double result1 =  redisRepository.hIncrement("testMap", "age", 3.14);
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhKeys() {
        Set<Object> result = redisRepository.hKeys("testMap");
        System.out.println("-->"+ result);

        List<Object> result1 = redisRepository.hVals("testMap");
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhLen() {
        Long result = redisRepository.hLen("testMap");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhMget() {
        List<Object> fileList = Arrays.asList("name", "age");
        List<Object> result = redisRepository.hMget("testMap", fileList);
        System.out.println("-->"+ result);
    }

    @Test
    public void testhSetNx() {
        Boolean result = redisRepository.hSetNx("testMap", "age", 30);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoAdd() {
        Long result = redisRepository.geoAdd("testGeo", new Point(121.47, 31.23), "shanghai");
        System.out.println("-->"+ result);

        Map<Object, Point> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("anqing", new Point(117.03, 30.52));
        memberCoordinateMap.put("hefei", new Point(117.27, 31.86));
        result = redisRepository.geoAdd("testGeo", memberCoordinateMap);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoPosition() {
        List<Point> result = redisRepository.geoPosition("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoDistance() {
        Distance result = redisRepository.geoDistance("testGeo", "hefei", "anqing", Metrics.KILOMETERS);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeoRadius() {
        Distance distance = new Distance(500, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> result = redisRepository.geoRadius("testGeo", "hefei", distance, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));

        Circle circle = new Circle(116.405285, 39.904989, Metrics.KILOMETERS.getMultiplier());
        result = redisRepository.geoRadius("testGeo", circle, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeohash() {
        List<String> result = redisRepository.geohash("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoRemove() {
        Long result = redisRepository.geoRemove("testGeo", "hefei");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogAdd() {
        Long result = redisRepository.logAdd("log", "test1", "test2");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogSize() {
        Long result = redisRepository.logSize("log");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogDelete() {
        redisRepository.logDelete("log");
    }
}
