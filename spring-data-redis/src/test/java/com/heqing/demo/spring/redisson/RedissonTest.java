package com.heqing.demo.spring.redisson;

import com.alibaba.fastjson.JSONObject;
import com.heqing.demo.spring.redisson.config.SpringRedissonConfig;
import com.heqing.demo.spring.redisson.dao.RedissonDao;
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
@ActiveProfiles("cluster")
public class RedissonTest {

    @Autowired
    RedissonDao redissonDao;

    @Test
    public void test() {
        // 测试连接模式 单例/哨兵/集群
        redissonDao.set("deploy", "single");
        Object result = redissonDao.get("deploy");
        System.out.println("-->"+result);
    }

    @Test
    public void testSet() {
        redissonDao.set("test1", 1);
//        redissonDao.set("test1", "shiyan", 1, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        Object result = redissonDao.get("test");
        System.out.println("-->"+result);
    }
    @Test
    public void testSetGet() {
        Object result = redissonDao.getSet("test", "shiyan");
        System.out.println("-->"+result);
    }

    @Test
    public void testDelete() {
        Boolean result = redissonDao.delete("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testExpire() {
        Boolean result = redissonDao.expire("test", 1, TimeUnit.MINUTES);
        System.out.println("-->"+result);
    }

    @Test
    public void testExpireAt() {
        long time = System.currentTimeMillis() + 1000;
        Boolean result = redissonDao.expireAt("test", new Date(time));
        System.out.println("-->"+result);
    }

    @Test
    public void testPersist() {
        Boolean result = redissonDao.persist("test1");
        System.out.println("-->"+result);
    }

    @Test
    public void testDump() {
        byte[] bytes = redissonDao.dump("test");
        if(bytes != null && bytes.length > 0) {
            System.out.println(bytes.toString()+"-->"+bytes.length);
        } else {
            System.out.println("-->null");
        }
    }

    @Test
    public void testCountExistingKeys() {
        Boolean result = redissonDao.countExistingKeys("test");
        System.out.println("-->"+result);
    }

    @Test
    public void testKeys() {
        Set<String> result = redissonDao.keys("test*");
        System.out.println("-->"+result);
    }

    @Test
    public void testMove() {
        Boolean result = redissonDao.move("test1", 1);
        System.out.println("-->"+result);
    }

    @Test
    public void testRandomKey() {
        Object result = redissonDao.randomKey();
        System.out.println("-->"+result);
    }

    @Test
    public void testRename() {
        redissonDao.rename("test1", "test");
    }

    @Test
    public void testType() {
        RType result = redissonDao.type("test1");
        System.out.println("-->"+ result);
    }

    @Test
    public void testAppend() {
        redissonDao.append("test", "123");
    }

    @Test
    public void testDecrement() {
        Long result = redissonDao.decrement("test1");
        System.out.println("-->"+ result);
    }

    @Test
    public void testIncrement() {
        Long result = redissonDao.increment("test1");
        System.out.println("-->"+ result);

        result = redissonDao.increment("test1", 10);
        System.out.println("-->"+ result);

        Double resultD = redissonDao.increment("test1", 3.1415);
        System.out.println("-->"+ resultD);
    }

    @Test
    public void testLLeftPush() {
        redissonDao.lLeftPush("testList1", "test1");

        List<Object> list = Arrays.asList("test2", "test3");
        redissonDao.lLeftPushAll("testList1",list);
    }

    @Test
    public void testLRightPush() {
        redissonDao.lRightPush("testList1", "test4");

        List<Object> list = Arrays.asList("test5", "test6");
        redissonDao.lRightPushAll("testList1", list);
    }

    @Test
    public void testLIndex() {
        Object result = redissonDao.lIndex("testList", 1);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLSize() {
        int result = redissonDao.lSize("testList1");
        System.out.println("-->"+ result);
    }

    @Test
    public void testLLeftPop() {
        Object result = redissonDao.lLeftPop("testList1");
        System.out.println("-->"+ result);

        result = redissonDao.lLeftPop("testList1", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRightPop() {
        Object result = redissonDao.lRightPop("testList1");
        System.out.println("-->"+ result);

        result = redissonDao.lRightPop("testList1", 1, TimeUnit.MINUTES);
        System.out.println("-->"+ result);
    }

    @Test
    public void testLRange() {
        List<Object> result = redissonDao.lRange("testList1");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLRemove() {
        Boolean result = redissonDao.lRemove("testList1", 1, "test2");
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testLSet() {
        redissonDao.lSet("testList1", 1, "test0");
    }

    @Test
    public void testLTrim() {
        redissonDao.lTrim("testList1", 1, 2);
    }

    @Test
    public void testsSize() {
        int result = redissonDao.sSize("testSet");
        System.out.println("-->"+ result);
    }

    @Test
    public void testsAdd() {
        List<Object> list = Arrays.asList("test1", "test2");
        redissonDao.sAdd("testSet", list);

        list = Arrays.asList("test1", "test2", "test3");
        redissonDao.sAdd("testSet0", list);

        list = Arrays.asList("test1",  "test3");
        Boolean result = redissonDao.sAdd("testSet1", list);
        System.out.println("-->"+ result);
    }

    @Test
    public void testsMembers() {
        Set<Object> set = redissonDao.sMembers("testSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsIsMember() {
        Boolean set = redissonDao.sIsMember("testSet", "test3");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsDiff() {
        Set<Object> set = redissonDao.sDiff("testSet0", "testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsIntersect() {
        Set<Object> set = redissonDao.sIntersect("testSet", "testSet0", "testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsUnion() {
        Set<Object> set = redissonDao.sUnion("testSet", "testSet0", "testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsMove() {
        Boolean set = redissonDao.sMove("testSet0", "testSet1", "test1");
        System.out.println("-->"+ set);
    }

    @Test
    public void testsPop() {
        Object set = redissonDao.sPop("testSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRandember() {
        Object set = redissonDao.sRandember("testSet");
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testsRemove() {
        Object set = redissonDao.sRemove("testSet1", Arrays.asList("test1"));
        System.out.println("set-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzAdd() {
        for(int i=0; i<3; i++) {
            Object set = redissonDao.zAdd("testSortedSet1", "heqing"+(i+1), 11+i);
            System.out.println("-->"+ JSONObject.toJSONString(set));
        }
    }

    @Test
    public void testzCard() {
        Object set = redissonDao.zCard("testSortedSet");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzCount() {
        Object set = redissonDao.zCount("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIncrby() {
        Object set = redissonDao.zIncrby("testSortedSet", "heqing", -2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRange() {
        ArrayList<Object> set = redissonDao.zRange("testSortedSet", 0, 1);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRangeByScore() {
        ArrayList<Object> set = redissonDao.zRangeByScore("testSortedSet", 11, 13);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRank() {
        int set = redissonDao.zRank("testSortedSet", "heqing2");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemove() {
        Boolean set = redissonDao.zRemove("testSortedSet", Arrays.asList("heqing"));
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRange() {
        int set = redissonDao.zRemoveRange("testSortedSet", 1,3);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRemoveRangeByScore() {
        int set = redissonDao.zRemoveRangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRange() {
        ArrayList<Object> set = redissonDao.zReverseRange("testSortedSet", 1,2);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzRevrangeByScore() {
        LinkedHashSet<Object> set = redissonDao.zRevrangeByScore("testSortedSet", 10,15);
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzReverseRank() {
        int set = redissonDao.zReverseRank("testSortedSet", "heqing1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzScore() {
        Double set = redissonDao.zScore("testSortedSet", "heqing1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzUnion() {
        int set = redissonDao.zUnion("testSortedSet", "testSortedSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testzIntersectAndStore() {
        int set = redissonDao.zIntersectAndStore("testSortedSet", "testSortedSet1");
        System.out.println("-->"+ JSONObject.toJSONString(set));
    }

    @Test
    public void testhPutAll() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "heqing");
        info.put("age", 30);
        info.put("qq", "975656343");
//        info.put("wechat", "hq0556246512");
        redissonDao.hPutAll("testMap", info);
    }

    @Test
    public void testhPut() {
        redissonDao.hPut("testMap", "wechat", "hq0556246512");
    }

    @Test
    public void testhDel() {
        Long result = redissonDao.hDelete("testMap", "wechat");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhExists() {
        Boolean result = redissonDao.hasKey("testMap", "qq");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhGet() {
        Object result = redissonDao.hGet("testMap", "qq");
        System.out.println("-->"+ result);

        Map<Object, Object> map = redissonDao.hGet("testMap");
        System.out.println("-->"+ map);
    }

    @Test
    public void testhincrBy() {
        Object result = redissonDao.hIncrement("testMap", "age", 2);
        System.out.println("-->"+ result);

        Object result1 =  redissonDao.hIncrement("testMap", "age", 3.14);
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhKeys() {
        Set<Object> result = redissonDao.hKeys("testMap");
        System.out.println("-->"+ result);

        List<Object> result1 = redissonDao.hVals("testMap");
        System.out.println("-->"+ result1);
    }

    @Test
    public void testhLen() {
        int result = redissonDao.hLen("testMap");
        System.out.println("-->"+ result);
    }

    @Test
    public void testhMget() {
        Set<Object> fileList = new HashSet<>();
        fileList.addAll(Arrays.asList("name", "age"));
        Map<Object, Object> result = redissonDao.hMget("testMap", fileList);
        System.out.println("-->"+ result);
    }

    @Test
    public void testhSetNx() {
        Boolean result = redissonDao.hSetNx("testMap", "age", 30);
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoAdd() {
        Long result = redissonDao.geoAdd("testGeo", 121.47, 31.23, "shanghai");
        System.out.println("-->"+ result);

        result = redissonDao.geoAdd("testGeo", new GeoEntry(117.03, 30.52 ,"anqing"), new GeoEntry(117.27, 31.86 ,"hefei"));
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoPosition() {
        // 报错,,成员可能传的不对
        Map<Object, GeoPosition> result = redissonDao.geoPosition("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoDistance() {
        Double result = redissonDao.geoDistance("testGeo", "hefei", "anqing", GeoUnit.KILOMETERS);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeoRadius() {
        List<Object> result = redissonDao.geoRadius("testGeo", 117.03, 30.52, 500, GeoUnit.KILOMETERS, GeoOrder.ASC, 5);
        System.out.println("-->"+ JSONObject.toJSONString(result));
    }

    @Test
    public void testgeohash() {
        Map<Object, String> result = redissonDao.geohash("testGeo", "shanghai", "anqing");
        System.out.println("-->"+ result);
    }

    @Test
    public void testgeoRemove() {
        Boolean result = redissonDao.geoRemove("testGeo", Arrays.asList("hefei"));
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogAdd() {
        Boolean result = redissonDao.logAdd("log", Arrays.asList("test1", "test2"));
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogSize() {
        Long result = redissonDao.logSize("log");
        System.out.println("-->"+ result);
    }

    @Test
    public void testlogDelete() {
        redissonDao.logDelete("log");
    }
}
