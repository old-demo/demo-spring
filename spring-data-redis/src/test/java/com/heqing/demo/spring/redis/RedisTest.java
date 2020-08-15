package com.heqing.demo.spring.redis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redis.config.SpringJedisConfig;
import com.heqing.demo.spring.redis.config.SpringLettuceConfig;
import com.heqing.demo.spring.redis.dao.RedisDao;
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
    RedisDao redisDao;

    @Test
    public void test() {
        // 测试连接模式 单例/哨兵/集群
//        redisDao.set("deploy", "single");
        Object result = redisDao.get("deploy");
        System.out.println("-->"+result);
    }

    @Test
    public void testSet() {
        redisDao.set("test", "heqing");
        redisDao.set("test1", "shiyan", 1, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        Object result = redisDao.get("test");
        System.out.println("-->"+result);
    }
    @Test
    public void testSetGet() {
        Object result = redisDao.getSet("test", "shiyan");
        System.out.println("-->"+result);
    }

    @Test
    public void testDelete() {
        Boolean result = redisDao.delete("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testExpire() {
        Boolean result = redisDao.expire("test", 1, TimeUnit.MINUTES);
        System.out.println("-->"+result);
    }

    @Test
    public void testExpireAt() {
        long time = System.currentTimeMillis() + 1000;
        Boolean result = redisDao.expireAt("test1", new Date(time));
        System.out.println("-->"+result);
    }

    @Test
    public void testPersist() {
        Boolean result = redisDao.persist("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testDump() {
        byte[] bytes = redisDao.dump("test");
        if(bytes != null && bytes.length > 0) {
            System.out.println(bytes+"-->"+bytes.length);
        } else {
            System.out.println("-->null");
        }
    }

    @Test
    public void testCountExistingKeys() {
        Boolean result = redisDao.countExistingKeys("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testKeys() {
        Set<String> result = redisDao.keys("test*");
        System.out.println("-->"+result);
    }

    @Test
    public void testMove() {
        Boolean result = redisDao.move("test1", 1);
        System.out.println("-->"+result);
    }

    @Test
    public void testRandomKey() {
        Object result = redisDao.randomKey();
        System.out.println("-->"+result);
    }

    @Test
    public void testRename() {
        redisDao.rename("test1", "test");
    }

    @Test
    public void testType() {
        DataType result = redisDao.type("test");
        System.out.println("-->"+ result);
    }

    @Test
    public void testAppend() {
        int result = redisDao.append("test1", "123");
        System.out.println("-->"+ result);
    }

    @Test
    public void testDecrement() {
        Long result = redisDao.decrement("test1");
        System.out.println("-->"+ result);

        result = redisDao.decrement("test1", 10);
        System.out.println("-->"+ result);
    }

    @Test
    public void testIncrement() {
        Long result = redisDao.increment("test1");
        System.out.println("-->"+ result);

        result = redisDao.increment("test1", 10);
        System.out.println("-->"+ result);

        Double resultD = redisDao.increment("test1", 3.1415);
        System.out.println("-->"+ resultD);
    }

    @Test
    public void testRange() {
        String result = redisDao.range("test", 0, -2);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPush() {
        Long result = redisDao.lLeftPush("testList", "test1");
        System.out.println("-->"+ result);

        result = redisDao.lLeftPushAll("testList", "test2", "test3");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPush() {
        Long result = redisDao.lRightPush("testList", "test4");
        System.out.println("-->"+ result);

        result = redisDao.lRightPushAll("testList", "test5", "test6");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLIndex() {
        Object result = redisDao.lIndex("testList", 1);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLSize() {
        Long result = redisDao.lSize("testList");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPop() {
        Object result = redisDao.lLeftPop("testList");
        System.out.println("-->"+ result);

        result = redisDao.lLeftPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPop() {
        Object result = redisDao.lRightPop("testList");
        System.out.println("-->"+ result);

        result = redisDao.lRightPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRange() {
        List<Object> result = redisDao.lRange("testList", 1, -1);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLRemove() {
        Long result = redisDao.lRemove("testList", 1, "test4");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLSet() {
        redisDao.lSet("testList", 1, "test4");
    }

    @Test
    public void testLTrim() {
        redisDao.lTrim("testList", 0, 1);
    }

    @Test
    public void testsSize() {
        Long result = redisDao.sSize("testSet");
        System.out.println("-->"+ result);
    }

    @Test
    public void testsAdd() {
        redisDao.sAdd("testSet", "test1", "test2");
        redisDao.sAdd("testSet0", "test1", "test2", "test3");
        Long result = redisDao.sAdd("testSet1", "test1",  "test3");

        System.out.println("-->"+ result);
    }

    @Test
    public void testsMembers() {
        Set<Object> set = redisDao.sMembers("testSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsIsMember() {
        Boolean set = redisDao.sIsMember("testSet", "test3");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsDiff() {
        List<String> list = Arrays.asList("testSet0", "testSet1");
        Set<Object> set = redisDao.sDiff( list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList( "testSet0", "testSet1");
        redisDao.sDiffAndStore(list, "testSetStore1");
    }

    @Test
    public void testsIntersect() {
        List<String> list = Arrays.asList("testSet", "testSet0", "testSet1");
        Set<Object> set = redisDao.sIntersect(list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet", "testSet1", "testSet0");
        redisDao.sIntersectAndStore(list, "testSetStore2");
    }

    @Test
    public void testsUnion() {
        List<String> list = Arrays.asList("testSet", "testSet0", "testSet1");
        Set<Object> set = redisDao.sUnion(list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet", "testSet1", "testSet0");
        redisDao.sUnionStore(list, "testSetStore3");
    }

    @Test
    public void testsMove() {
        Boolean set = redisDao.sMove("testSet0", "testSet1", "test21");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsPop() {
        Object set = redisDao.sPop("testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRandember() {
        Object set = redisDao.sRandember("testSet1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRemove() {
        Object set = redisDao.sRemove("testSet1", "test1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzAdd() {
        for(int i=0; i<3; i++) {
            Object set = redisDao.zAdd("testSortedSet", "heqing"+i, 10+i);
            System.out.println("-->"+ JSONObject.toJSONString(set));
        }
    }

    @Test
    public void testzCard() {
        Object set = redisDao.zCard("testSortedSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzCount() {
        Object set = redisDao.zCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIncrby() {
        Object set = redisDao.zIncrby("testSortedSet", "heqing", -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRange() {
        Set<Object> set = redisDao.zRange("testSortedSet", 1, -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRangeByScore() {
        Set<Object> set = redisDao.zRangeByScore("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRank() {
        Long set = redisDao.zRank("testSortedSet", "heqing2");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemove() {
        Long set = redisDao.zRemove("testSortedSet", "heqing");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRange() {
        Long set = redisDao.zRemoveRange("testSortedSet", 1,3);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRangeByScore() {
        Long set = redisDao.zRemoveRangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRange() {
        Set<Object> set = redisDao.zReverseRange("testSortedSet", 1,2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRevrangeByScore() {
        Set<Object> set = redisDao.zRevrangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRank() {
        Long set = redisDao.zReverseRank("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzScore() {
        Double set = redisDao.zScore("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzUunionAndStore() {
        List<String> keys = Arrays.asList("testSortedSet", "testSortedSet1");
        Long set = redisDao.zUunionAndStore(keys, "zUunionAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIntersectAndStore() {
        List<String> keys = Arrays.asList("testSortedSet", "testSortedSet1");
        Long set = redisDao.zIntersectAndStore(keys, "zIntersectAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzLexCount() {
        Long set = redisDao.zLexCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testhPutAll() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "heqing");
        info.put("age", 30);
        info.put("qq", "975656343");
//        info.put("wechat", "hq0556246512");
        redisDao.hPutAll("testMap", info);
    }

    @Test
    public void testhPut() {
        redisDao.hPut("testMap", "wechat", "hq0556246512");
    }

    @Test
    public void testhDel() {
        Long result = redisDao.hDelete("testMap", "wechat");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhExists() {
        Boolean result = redisDao.hasKey("testMap", "qq");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhGet() {
        Object result = redisDao.hGet("testMap", "qq");
        System.out.println("-->"+ result);

        Map<Object, Object> map = redisDao.hGet("testMap");
        System.out.println("-->"+ map);
    }

    @Test
    public void testhincrBy() {
        Long result = redisDao.hIncrement("testMap", "age", 2);
        System.out.println("-->"+ result);

        double result1 =  redisDao.hIncrement("testMap", "age", 3.14);
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhKeys() {
        Set<Object> result = redisDao.hKeys("testMap");
        System.out.println("-->"+ result);

        List<Object> result1 = redisDao.hVals("testMap");
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhLen() {
        Long result = redisDao.hLen("testMap");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhMget() {
        List<Object> fileList = Arrays.asList("name", "age");
        List<Object> result = redisDao.hMget("testMap", fileList);
        System.out.println("-->"+ result);
    }

    @Test
    public void testhSetNx() {
        Boolean result = redisDao.hSetNx("testMap", "age", 30);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoAdd() {
        Long result = redisDao.geoAdd("testGeo", new Point(121.47, 31.23), "shanghai");
        System.out.println("-->"+ result);

        Map<Object, Point> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("anqing", new Point(117.03, 30.52));
        memberCoordinateMap.put("hefei", new Point(117.27, 31.86));
        result = redisDao.geoAdd("testGeo", memberCoordinateMap);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoPosition() {
        List<Point> result = redisDao.geoPosition("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoDistance() {
        Distance result = redisDao.geoDistance("testGeo", "hefei", "anqing", Metrics.KILOMETERS);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeoRadius() {
        Distance distance = new Distance(500, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> result = redisDao.geoRadius("testGeo", "hefei", distance, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));

        Circle circle = new Circle(116.405285, 39.904989, Metrics.KILOMETERS.getMultiplier());
        result = redisDao.geoRadius("testGeo", circle, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeohash() {
        List<String> result = redisDao.geohash("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoRemove() {
        Long result = redisDao.geoRemove("testGeo", "hefei");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogAdd() {
        Long result = redisDao.logAdd("log", "test1", "test2");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogSize() {
        Long result = redisDao.logSize("log");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogDelete() {
        redisDao.logDelete("log");
    }
}
