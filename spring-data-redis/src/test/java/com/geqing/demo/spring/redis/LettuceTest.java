package com.geqing.demo.spring.redis;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redis.lettuce.config.SpringLettuceConfig;
import com.heqing.demo.spring.redis.lettuce.dao.RedisLettuceDao;
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
        classes = SpringLettuceConfig.class
)
@ActiveProfiles("single")
public class LettuceTest {

    @Autowired
    RedisLettuceDao redisLettuceDao;

    @Test
    public void test() {
        // 测试连接模式 单例/哨兵/集群
        redisLettuceDao.set("deploy", "single");
        Object result = redisLettuceDao.get("deploy");
        System.out.println("-->"+result);
    }

    @Test
    public void testSet() {
        redisLettuceDao.set("test", "heqing");
        redisLettuceDao.set("test1", "shiyan", 1, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        Object result = redisLettuceDao.get("test");
        System.out.println("-->"+result);
    }
    @Test
    public void testSetGet() {
        Object result = redisLettuceDao.getSet("test", "shiyan");
        System.out.println("-->"+result);
    }

    @Test
    public void testDelete() {
        Boolean result = redisLettuceDao.delete("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testExpire() {
        Boolean result = redisLettuceDao.expire("test", 1, TimeUnit.MINUTES);
        System.out.println("-->"+result);
    }

    @Test
    public void testExpireAt() {
        long time = System.currentTimeMillis() + 1000;
        Boolean result = redisLettuceDao.expireAt("test1", new Date(time));
        System.out.println("-->"+result);
    }

    @Test
    public void testPersist() {
        Boolean result = redisLettuceDao.persist("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testDump() {
        byte[] bytes = redisLettuceDao.dump("test");
        if(bytes != null && bytes.length > 0) {
            System.out.println(bytes+"-->"+bytes.length);
        } else {
            System.out.println("-->null");
        }
    }

    @Test
    public void testCountExistingKeys() {
        Boolean result = redisLettuceDao.countExistingKeys("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testKeys() {
        Set<String> result = redisLettuceDao.keys("test*");
        System.out.println("-->"+result);
    }

    @Test
    public void testMove() {
        Boolean result = redisLettuceDao.move("test1", 1);
        System.out.println("-->"+result);
    }

    @Test
    public void testRandomKey() {
        Object result = redisLettuceDao.randomKey();
        System.out.println("-->"+result);
    }

    @Test
    public void testRename() {
        redisLettuceDao.rename("test1", "test");
    }

    @Test
    public void testType() {
        DataType result = redisLettuceDao.type("test");
        System.out.println("-->"+ result);
    }

    @Test
    public void testAppend() {
        int result = redisLettuceDao.append("test1", "123");
        System.out.println("-->"+ result);
    }

    @Test
    public void testDecrement() {
        Long result = redisLettuceDao.decrement("test1");
        System.out.println("-->"+ result);

        result = redisLettuceDao.decrement("test1", 10);
        System.out.println("-->"+ result);
    }

    @Test
    public void testIncrement() {
        Long result = redisLettuceDao.increment("test1");
        System.out.println("-->"+ result);

        result = redisLettuceDao.increment("test1", 10);
        System.out.println("-->"+ result);

        Double resultD = redisLettuceDao.increment("test1", 3.1415);
        System.out.println("-->"+ resultD);
    }

    @Test
    public void testRange() {
        String result = redisLettuceDao.range("test", 0, -2);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPush() {
        Long result = redisLettuceDao.lLeftPush("testList", "test1");
        System.out.println("-->"+ result);

        result = redisLettuceDao.lLeftPushAll("testList", "test2", "test3");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPush() {
        Long result = redisLettuceDao.lRightPush("testList", "test4");
        System.out.println("-->"+ result);

        result = redisLettuceDao.lRightPushAll("testList", "test5", "test6");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLIndex() {
        Object result = redisLettuceDao.lIndex("testList", 1);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLSize() {
        Long result = redisLettuceDao.lSize("testList");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPop() {
        Object result = redisLettuceDao.lLeftPop("testList");
        System.out.println("-->"+ result);

        result = redisLettuceDao.lLeftPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPop() {
        Object result = redisLettuceDao.lRightPop("testList");
        System.out.println("-->"+ result);

        result = redisLettuceDao.lRightPop("testList", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRange() {
        List<Object> result = redisLettuceDao.lRange("testList", 1, -1);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLRemove() {
        Long result = redisLettuceDao.lRemove("testList", 1, "test4");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLSet() {
        redisLettuceDao.lSet("testList", 1, "test4");
    }

    @Test
    public void testLTrim() {
        redisLettuceDao.lTrim("testList", 0, 1);
    }

    @Test
    public void testsSize() {
        Long result = redisLettuceDao.sSize("testSet");
        System.out.println("-->"+ result);
    }

    @Test
    public void testsAdd() {
        redisLettuceDao.sAdd("testSet", "test1", "test2");
        redisLettuceDao.sAdd("testSet0", "test1", "test2", "test3");
        Long result = redisLettuceDao.sAdd("testSet1", "test1",  "test3");

        System.out.println("-->"+ result);
    }

    @Test
    public void testsMembers() {
        Set<Object> set = redisLettuceDao.sMembers("testSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsIsMember() {
        Boolean set = redisLettuceDao.sIsMember("testSet", "test3");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsDiff() {
        List<String> list = Arrays.asList("testSet0", "testSet1");
        Set<Object> set = redisLettuceDao.sDiff( list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList( "testSet0", "testSet1");
        redisLettuceDao.sDiffAndStore(list, "testSetStore1");
    }

    @Test
    public void testsIntersect() {
        List<String> list = Arrays.asList("testSet", "testSet0", "testSet1");
        Set<Object> set = redisLettuceDao.sIntersect(list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet", "testSet1", "testSet0");
        redisLettuceDao.sIntersectAndStore(list, "testSetStore2");
    }

    @Test
    public void testsUnion() {
        List<String> list = Arrays.asList("testSet", "testSet0", "testSet1");
        Set<Object> set = redisLettuceDao.sUnion(list);
        System.out.println("-->"+ JSONObject.toJSONString(set));

        list = Arrays.asList("testSet", "testSet1", "testSet0");
        redisLettuceDao.sUnionStore(list, "testSetStore3");
    }

    @Test
    public void testsMove() {
        Boolean set = redisLettuceDao.sMove("testSet0", "testSet1", "test21");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsPop() {
        Object set = redisLettuceDao.sPop("testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRandember() {
        Object set = redisLettuceDao.sRandember("testSet1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRemove() {
        Object set = redisLettuceDao.sRemove("testSet1", "test1");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzAdd() {
        for(int i=0; i<3; i++) {
            Object set = redisLettuceDao.zAdd("testSortedSet", "heqing"+i, 10+i);
            System.out.println("-->"+ JSONObject.toJSONString(set));
        }
    }

    @Test
    public void testzCard() {
        Object set = redisLettuceDao.zCard("testSortedSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzCount() {
        Object set = redisLettuceDao.zCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIncrby() {
        Object set = redisLettuceDao.zIncrby("testSortedSet", "heqing", -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRange() {
        Set<Object> set = redisLettuceDao.zRange("testSortedSet", 1, -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRangeByScore() {
        Set<Object> set = redisLettuceDao.zRangeByScore("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRank() {
        Long set = redisLettuceDao.zRank("testSortedSet", "heqing2");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemove() {
        Long set = redisLettuceDao.zRemove("testSortedSet", "heqing");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRange() {
        Long set = redisLettuceDao.zRemoveRange("testSortedSet", 1,3);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRangeByScore() {
        Long set = redisLettuceDao.zRemoveRangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRange() {
        Set<Object> set = redisLettuceDao.zReverseRange("testSortedSet", 1,2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRevrangeByScore() {
        Set<Object> set = redisLettuceDao.zRevrangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRank() {
        Long set = redisLettuceDao.zReverseRank("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzScore() {
        Double set = redisLettuceDao.zScore("testSortedSet", "heqing4");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzUunionAndStore() {
        List<String> keys = Arrays.asList("testSortedSet", "testSortedSet1");
        Long set = redisLettuceDao.zUunionAndStore(keys, "zUunionAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIntersectAndStore() {
        List<String> keys = Arrays.asList("testSortedSet", "testSortedSet1");
        Long set = redisLettuceDao.zIntersectAndStore(keys, "zIntersectAndStore");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzLexCount() {
        Long set = redisLettuceDao.zLexCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testhPutAll() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "heqing");
        info.put("age", 30);
        info.put("qq", "975656343");
//        info.put("wechat", "hq0556246512");
        redisLettuceDao.hPutAll("testMap", info);
    }

    @Test
    public void testhPut() {
        redisLettuceDao.hPut("testMap", "wechat", "hq0556246512");
    }

    @Test
    public void testhDel() {
        Long result = redisLettuceDao.hDelete("testMap", "wechat");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhExists() {
        Boolean result = redisLettuceDao.hasKey("testMap", "qq");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhGet() {
        Object result = redisLettuceDao.hGet("testMap", "qq");
        System.out.println("-->"+ result);

        Map<Object, Object> map = redisLettuceDao.hGet("testMap");
        System.out.println("-->"+ map);
    }

    @Test
    public void testhincrBy() {
        Long result = redisLettuceDao.hIncrement("testMap", "age", 2);
        System.out.println("-->"+ result);

        double result1 =  redisLettuceDao.hIncrement("testMap", "age", 3.14);
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhKeys() {
        Set<Object> result = redisLettuceDao.hKeys("testMap");
        System.out.println("-->"+ result);

        List<Object> result1 = redisLettuceDao.hVals("testMap");
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhLen() {
        Long result = redisLettuceDao.hLen("testMap");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhMget() {
        List<Object> fileList = Arrays.asList("name", "age");
        List<Object> result = redisLettuceDao.hMget("testMap", fileList);
        System.out.println("-->"+ result);
    }

    @Test
    public void testhSetNx() {
        Boolean result = redisLettuceDao.hSetNx("testMap", "age", 30);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoAdd() {
        Long result = redisLettuceDao.geoAdd("testGeo", new Point(121.47, 31.23), "shanghai");
        System.out.println("-->"+ result);

        Map<Object, Point> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("anqing", new Point(117.03, 30.52));
        memberCoordinateMap.put("hefei", new Point(117.27, 31.86));
        result = redisLettuceDao.geoAdd("testGeo", memberCoordinateMap);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoPosition() {
        List<Point> result = redisLettuceDao.geoPosition("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoDistance() {
        Distance result = redisLettuceDao.geoDistance("testGeo", "hefei", "anqing", Metrics.KILOMETERS);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeoRadius() {
        Distance distance = new Distance(500, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> result = redisLettuceDao.geoRadius("testGeo", "hefei", distance, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));

        Circle circle = new Circle(116.405285, 39.904989, Metrics.KILOMETERS.getMultiplier());
        result = redisLettuceDao.geoRadius("testGeo", circle, args);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeohash() {
        List<String> result = redisLettuceDao.geohash("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoRemove() {
        Long result = redisLettuceDao.geoRemove("testGeo", "hefei");
        System.out.println("-->"+ result);
    }
}
