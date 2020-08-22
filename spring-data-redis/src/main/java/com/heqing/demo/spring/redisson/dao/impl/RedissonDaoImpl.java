package com.heqing.demo.spring.redisson.dao.impl;

import com.heqing.demo.spring.redisson.dao.RedissonDao;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

// https://github.com/redisson/redisson/wiki/11.-Redis%E5%91%BD%E4%BB%A4%E5%92%8CRedisson%E5%AF%B9%E8%B1%A1%E5%8C%B9%E9%85%8D%E5%88%97%E8%A1%A8
@Repository
public class RedissonDaoImpl implements RedissonDao {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public void set(String key, Object value) {
        redissonClient.getBucket(key).set(value);
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redissonClient.getBucket(key).set(value, timeout, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redissonClient.getBucket(key).get();
    }

    @Override
    public Object getSet(String key, Object value) {
        return redissonClient.getBucket(key).getAndSet(value);
    }

    @Override
    public Boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redissonClient.getBucket(key).expire(timeout, timeUnit);
    }

    @Override
    public Boolean expireAt(String key, Date date) {
        return redissonClient.getBucket(key).expireAt(date);
    }

    @Override
    public Boolean persist(String key) {
        return redissonClient.getBucket(key).clearExpire();
    }

    @Override
    public byte[] dump(String key) {
        return redissonClient.getBucket(key).dump();
    }

    @Override
    public Boolean countExistingKeys(String key) {
        return redissonClient.getBucket(key).isExists();
    }

    @Override
    public Set<String> keys(String pattern) {
        Set<String> keys = new HashSet<>();
        Iterable<String> result = redissonClient.getKeys().getKeysByPattern(pattern);
        for(String key : result) {
            keys.add(key);
        }
        return keys;
    }

    @Override
    public Boolean move(String key, int dbIndex) {
        return redissonClient.getBucket(key).move(dbIndex);
    }

    @Override
    public Object randomKey() {
        return redissonClient.getKeys().randomKey();
    }

    @Override
    public void rename(String oldkey, String newkey) {
        redissonClient.getBucket(oldkey).rename(newkey);
    }

    @Override
    public RType type(String key) {
        return redissonClient.getKeys().getType(key);
    }

    @Override
    public void append(String key, String value) {
        try {
            OutputStream out = redissonClient.getBinaryStream(key).getOutputStream();
            out.write(value.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long decrement(String key) {
        return redissonClient.getAtomicLong(key).decrementAndGet();
    }

    @Override
    public Long increment(String key) {
        return redissonClient.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public Long increment(String key, long increment) {
        return redissonClient.getAtomicLong(key).addAndGet(increment);
    }

    @Override
    public Double increment(String key, double increment) {
        return redissonClient.getAtomicDouble(key).addAndGet(increment);
    }

    @Override
    public void lLeftPush(String key, Object value) {
        redissonClient.getDeque(key).addFirst(value);
    }

    @Override
    public void lLeftPushAll(String key, List<Object> values) {
        redissonClient.getDeque(key).addAll(values);
    }

    @Override
    public void lRightPush(String key, Object value) {
        redissonClient.getDeque(key).addLast(value);
    }

    @Override
    public void lRightPushAll(String key, List<Object> values) {
        redissonClient.getList(key).addAll(values);
    }

    @Override
    public Object lIndex(String key, int index) {
        return redissonClient.getList(key).get(index);
    }

    @Override
    public int lSize(String key) {
        return redissonClient.getList(key).size();
    }

    @Override
    public Object lLeftPop(String key) {
        return redissonClient.getDeque(key).pollFirst();
    }

    @Override
    public Object lLeftPop(String key, long timeout, TimeUnit unit) {
        Object obj = null;
        try {
            obj = redissonClient.getBlockingDeque(key).pollFirst(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object lRightPop(String key) {
        return redissonClient.getDeque(key).pollLast();
    }

    @Override
    public Object lRightPop(String key, long timeout, TimeUnit unit) {
        Object obj = null;
        try {
            obj = redissonClient.getBlockingDeque(key).pollLast(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public List<Object> lRange(String key) {
        return redissonClient.getList(key).readAll();
    }

    @Override
    public Boolean lRemove(String key, int count, Object value) {
        return redissonClient.getList(key).remove(value, count);
    }

    @Override
    public void lSet(String key, int index, Object value) {
        redissonClient.getList(key).fastSet(index, value);
    }

    @Override
    public void lTrim(String key, int start, int end) {
        redissonClient.getList(key).trim(start, end);
    }

    @Override
    public Boolean sAdd(String key, List<Object> members) {
        return redissonClient.getSet(key).addAll(members);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redissonClient.getSet(key).readAll();
    }

    @Override
    public Boolean sIsMember(String key, Object member) {
        return redissonClient.getSet(key).contains(member);
    }

    @Override
    public Boolean sMove(String srckey, String dstkey, Object member) {
        return redissonClient.getSet(srckey).move(dstkey, member);
    }

    @Override
    public int sSize(String key) {
        return redissonClient.getSet(key).size();
    }

    @Override
    public Boolean sRemove(String key, List<Object> members) {
        return redissonClient.getSet(key).removeAll(members);
    }

    @Override
    public Set<Object> sDiff(String key, String... compares) {
        return redissonClient.getSet(key).readDiff(compares);
    }

    @Override
    public Set<Object> sIntersect(String key, String... compares) {
        return redissonClient.getSet(key).readIntersection(compares);
    }

    @Override
    public Set<Object> sUnion(String key, String... compares) {
        return redissonClient.getSet(key).readUnion(compares);
    }

    @Override
    public Object sPop(String key) {
        return redissonClient.getSet(key).removeRandom();
    }

    @Override
    public Object sRandember(String key) {
        return redissonClient.getSet(key).random();
    }

    @Override
    public Boolean zAdd(String key, Object member, double score) {
        return redissonClient.getScoredSortedSet(key).add(score, member);
    }

    @Override
    public int zCard(String key) {
        return redissonClient.getScoredSortedSet(key).size();
    }

    @Override
    public int zCount(String key, double min, double max) {
        return redissonClient.getScoredSortedSet(key).count(min, true, max, true);
    }

    @Override
    public Double zIncrby(String key, Object member, double score) {
        return redissonClient.getScoredSortedSet(key).addScore(member, score);
    }

    @Override
    public ArrayList<Object> zRange(String key, int start, int end) {
        return (ArrayList<Object>) redissonClient.getScoredSortedSet(key).valueRange(start, end);
    }

    @Override
    public ArrayList<Object> zRangeByScore(String key, double min, double max) {
        return (ArrayList<Object>) redissonClient.getScoredSortedSet(key).valueRange(min, true, max, true);
    }

    @Override
    public Integer zRank(String key, Object member) {
        return redissonClient.getScoredSortedSet(key).rank(member);
    }

    @Override
    public Boolean zRemove(String key, List<Object> members) {
        return redissonClient.getScoredSortedSet(key).removeAll(members);
    }

    @Override
    public int zRemoveRange(String key, int start, int end) {
        return redissonClient.getScoredSortedSet(key).removeRangeByRank(start, end);
    }

    @Override
    public int zRemoveRangeByScore(String key, double start, double end) {
        return redissonClient.getScoredSortedSet(key).removeRangeByScore(start, true, end, true);
    }

    @Override
    public ArrayList<Object> zReverseRange(String key, int start, int end) {
        return (ArrayList<Object>)redissonClient.getScoredSortedSet(key).valueRangeReversed(start, end);
    }

    @Override
    public LinkedHashSet<Object> zRevrangeByScore(String key, double min, double max) {
        return (LinkedHashSet<Object>)redissonClient.getScoredSortedSet(key).valueRangeReversed(min, true, max, true);
    }

    @Override
    public Integer zReverseRank(String key, Object member) {
        return redissonClient.getScoredSortedSet(key).revRank(member);
    }

    @Override
    public Double zScore(String key, Object member) {
        return redissonClient.getScoredSortedSet(key).getScore(member);
    }

    @Override
    public int zUnion(String key, String... compares) {
        return redissonClient.getScoredSortedSet(key).union(compares);
    }

    @Override
    public int zIntersectAndStore(String key, String... compares) {
        return redissonClient.getScoredSortedSet(key).intersection(compares);
    }

    @Override
    public void hPutAll(String key, Map<String, Object> value) {
        redissonClient.getMap(key).putAll(value);
    }

    @Override
    public void hPut(String key, Object field, Object value) {
        redissonClient.getMap(key).put(field, value);
    }

    @Override
    public Long hDelete(String key, Object... member) {
        return redissonClient.getMap(key).fastRemove(member);
    }

    @Override
    public Boolean hasKey(String key, Object member) {
        return redissonClient.getMap(key).containsKey(member);
    }

    @Override
    public Object hGet(String key, String member) {
        return redissonClient.getMap(key).get(member);
    }

    @Override
    public Map<Object, Object> hGet(String key) {
        return redissonClient.getMap(key).readAllMap();
    }

    @Override
    public Object hIncrement(String key, String field, Number increment) {
        return redissonClient.getMap(key).addAndGet(field, increment);
    }

    @Override
    public Set<Object> hKeys(String key) {
        return redissonClient.getMap(key).readAllKeySet();
    }

    @Override
    public List<Object> hVals(String key) {
        return (List<Object>)redissonClient.getMap(key).readAllValues();
    }

    @Override
    public int hLen(String key) {
        return redissonClient.getMap(key).size();
    }

    @Override
    public Map<Object, Object> hMget(String key, Set<Object> field) {
        return redissonClient.getMap(key).getAll(field);
    }

    @Override
    public Boolean hSetNx(String key, Object field, Object value) {
        return redissonClient.getMap(key).fastPutIfAbsent(field, value);
    }

    @Override
    public Long geoAdd(String key, GeoEntry... entries) {
        return redissonClient.getGeo(key).add(entries);
    }

    @Override
    public Long geoAdd(String key, double longitude, double latitude, Object member) {
        return redissonClient.getGeo(key).add(longitude, latitude, member);
    }

    @Override
    public Map<Object, GeoPosition> geoPosition(String key, Object... members) {
        return redissonClient.getGeo(key).pos(members);
    }

    @Override
    public Double geoDistance(String key, Object member1, Object member2, GeoUnit metric) {
        return redissonClient.getGeo(key).dist(member1, member2,metric );
    }

    @Override
    public List<Object> geoRadius(String key, double longitude, double latitude, double radius, GeoUnit geoUnit, GeoOrder geoOrder, int count) {
        return redissonClient.getGeo(key).radius(longitude, latitude, radius, geoUnit, geoOrder, count);
    }

    @Override
    public Map<Object, String> geohash(String key, Object... members) {
        return redissonClient.getGeo(key).hash(members);
    }

    @Override
    public Boolean geoRemove(String key, List<Object> members) {
        return redissonClient.getGeo(key).removeAll(members);
    }

    @Override
    public Boolean logAdd(String key, List<Object> elements) {
        return redissonClient.getHyperLogLog(key).addAll(elements);
    }

    @Override
    public Long logSize(String key) {
        return redissonClient.getHyperLogLog(key).count();
    }

    @Override
    public void logUnion(String key, String... sourcekeys) {
        redissonClient.getHyperLogLog(key).mergeWith(sourcekeys);
    }

    @Override
    public Boolean logDelete(String key) {
        return redissonClient.getHyperLogLog(key).delete();
    }
}
