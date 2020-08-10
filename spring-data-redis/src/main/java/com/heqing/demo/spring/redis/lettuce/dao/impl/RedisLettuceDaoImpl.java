package com.heqing.demo.spring.redis.lettuce.dao.impl;

import com.heqing.demo.spring.redis.lettuce.dao.RedisLettuceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisLettuceDaoImpl implements RedisLettuceDao {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.boundValueOps(key).set(value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(key).set(value, timeout, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    @Override
    public Object getSet(String key, String value) {
        return redisTemplate.boundValueOps(key).getAndSet(value);
    }

    @Override
    public Boolean delete(String keys) {
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public  byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    @Override
    public Boolean countExistingKeys(String key) {
        // 注意此处 key 如果传入多个会报错
        List<String> keyList = new ArrayList<>();
        keyList.add(key);
        long num = redisTemplate.countExistingKeys(keyList);
        return num>0 ? true : false;
    }

    @Override
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    @Override
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    @Override
    public Object randomKey() {
        return redisTemplate.randomKey();
    }

    @Override
    public void rename(String oldkey, String newkey) {
        redisTemplate.rename(oldkey, newkey);
    }

    @Override
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    @Override
    public int append(String key, String value) {
        return redisTemplate.boundValueOps(key).append(value);
    }

    @Override
    public Long decrement(String key) {
        return redisTemplate.boundValueOps(key).decrement();
    }

    @Override
    public Long decrement(String key, long decrement) {
        return redisTemplate.boundValueOps(key).decrement(decrement);
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.boundValueOps(key).increment();
    }

    @Override
    public Long increment(String key, long increment) {
        return redisTemplate.boundValueOps(key).increment(increment);
    }

    @Override
    public Double increment(String key, double increment) {
        return redisTemplate.boundValueOps(key).increment(increment);
    }

    @Override
    public String range(String key, long startOffset, long endOffset) {
        return redisTemplate.boundValueOps(key).get(startOffset, endOffset);
    }

    @Override
    public Long lLeftPush(String key, Object value) {
        return redisTemplate.boundListOps(key).leftPush(value);
    }

    @Override
    public Long lLeftPushAll(String key, Object... values) {
        return redisTemplate.boundListOps(key).leftPushAll(values);
    }

    @Override
    public Long lRightPush(String key, Object value) {
        return redisTemplate.boundListOps(key).rightPush(value);
    }

    @Override
    public Long lRightPushAll(String key, Object... values) {
        return redisTemplate.boundListOps(key).rightPushAll(values);
    }

    @Override
    public Object lIndex(String key, long index) {
        return redisTemplate.boundListOps(key).index(index);
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.boundListOps(key).size();
    }

    @Override
    public Object lLeftPop(String key) {
        return redisTemplate.boundListOps(key).leftPop();
    }

    @Override
    public Object lLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.boundListOps(key).leftPop(timeout, unit);
    }

    @Override
    public Object lRightPop(String key) {
        return redisTemplate.boundListOps(key).rightPop();
    }

    @Override
    public Object lRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.boundListOps(key).rightPop(timeout, unit);
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.boundListOps(key).range(start, end);
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.boundListOps(key).remove(count, value);
    }

    @Override
    public void lSet(String key, long index, Object value) {
        long size = lSize(key);
        if(index < size) {
            redisTemplate.boundListOps(key).set(index, value);
        }
    }

    @Override
    public void lTrim(String key, long start, long end) {
        redisTemplate.boundListOps(key).trim(start, end);
    }

    @Override
    public Long sAdd(String key, Object... members) {
        return redisTemplate.boundSetOps(key).add(members);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redisTemplate.boundSetOps(key).members();
    }

    @Override
    public Boolean sIsMember(String key, Object member) {
        return redisTemplate.boundSetOps(key).isMember(member);
    }

    @Override
    public Boolean sMove(String srckey, String dstkey, Object member) {
        return redisTemplate.boundSetOps(srckey).move(dstkey, member);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.boundSetOps(key).size();
    }

    @Override
    public Long sRemove(String key, Object... members) {
        return redisTemplate.boundSetOps(key).remove(members);
    }

    @Override
    public Set<Object> sDiff(List<String> compares) {
        return redisTemplate.boundSetOps(compares.get(0)).diff(compares.subList(1, compares.size()));
    }

    @Override
    public void sDiffAndStore(List<String> compares, String destination) {
        redisTemplate.boundSetOps(compares.get(0)).diffAndStore(compares.subList(1, compares.size()), destination);
    }

    @Override
    public Set<Object> sIntersect(List<String> compares) {
        return redisTemplate.boundSetOps(compares.get(0)).intersect(compares.subList(1, compares.size()));
    }

    @Override
    public void sIntersectAndStore(List<String> compares, String destination) {
        redisTemplate.boundSetOps(compares.get(0)).intersectAndStore(compares.subList(1, compares.size()), destination);
    }

    @Override
    public Object sPop(String key) {
        return redisTemplate.boundSetOps(key).pop();
    }

    @Override
    public Object sRandember(String key) {
        return redisTemplate.boundSetOps(key).randomMember();
    }

    @Override
    public Set<Object> sUnion(List<String> compares) {
        return redisTemplate.boundSetOps(compares.get(0)).union(compares.subList(1, compares.size()));
    }

    @Override
    public void sUnionStore(List<String> compares, String destination) {
        redisTemplate.boundSetOps(compares.get(0)).unionAndStore(compares.subList(1, compares.size()), destination);
    }

    @Override
    public Boolean zAdd(String key, Object member, double score) {
        return redisTemplate.boundZSetOps(key).add(member, score);
    }

    @Override
    public Long zCard(String key) {
        return redisTemplate.boundZSetOps(key).zCard();
    }

    @Override
    public Long zCount(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).count(min, max);
    }

    @Override
    public Double zIncrby(String key, Object member, double score) {
        return redisTemplate.boundZSetOps(key).incrementScore(member,score );
    }

    @Override
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).range(start, end);
    }

    @Override
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).rangeByScore(min, max);
    }

    @Override
    public Long zRank(String key, Object member) {
        return redisTemplate.boundZSetOps(key).rank(member);
    }

    @Override
    public Long zRemove(String key, Object... members) {
        return redisTemplate.boundZSetOps(key).remove(members);
    }

    @Override
    public Long zRemoveRange(String key, long min, long max) {
        return redisTemplate.boundZSetOps(key).removeRange(min, max);
    }

    @Override
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).removeRangeByScore(min, max);
    }

    @Override
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.boundZSetOps(key).reverseRange(start, end);
    }

    @Override
    public Set<Object> zRevrangeByScore(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).reverseRangeByScore(min, max);
    }

    @Override
    public Long zReverseRank(String key, Object member) {
        return redisTemplate.boundZSetOps(key).reverseRank(member);
    }

    @Override
    public Double zScore(String key, Object member) {
        return redisTemplate.boundZSetOps(key).score(member);
    }

    @Override
    public Long zUunionAndStore(List<String> compares, String destination) {
        return redisTemplate.boundZSetOps(compares.get(0)).unionAndStore(compares.subList(1, compares.size()), destination);
    }

    @Override
    public Long zIntersectAndStore(List<String> compares, String destination) {
        return redisTemplate.boundZSetOps(compares.get(0)).intersectAndStore(compares.subList(1, compares.size()), destination);
    }

    @Override
    public Long zLexCount(String key, double min, double max) {
        return redisTemplate.boundZSetOps(key).count(min, max);
    }

    @Override
    public void hPutAll(String key, Map<String, Object> value) {
        redisTemplate.boundHashOps(key).putAll(value);
    }

    @Override
    public void hPut(String key, Object field, Object value) {
        redisTemplate.boundHashOps(key).put(field, value);
    }

    @Override
    public Long hDelete(String key, Object... member) {
        return redisTemplate.boundHashOps(key).delete(member);
    }

    @Override
    public Boolean hasKey(String key, Object member) {
        return redisTemplate.boundHashOps(key).hasKey(member);
    }

    @Override
    public Object hGet(String key, String member) {
        return redisTemplate.boundHashOps(key).get(member);
    }

    @Override
    public Map<Object, Object> hGet(String key) {
        return redisTemplate.boundHashOps(key).entries();
    }

    @Override
    public Long hIncrement(String key, String field, long increment) {
        return redisTemplate.boundHashOps(key).increment(field, increment);
    }

    @Override
    public Double hIncrement(String key, String field, double increment) {
        return redisTemplate.boundHashOps(key).increment(field, increment);
    }

    @Override
    public Set<Object> hKeys(String key) {
        return redisTemplate.boundHashOps(key).keys();
    }

    @Override
    public List<Object> hVals(String key) {
        return redisTemplate.boundHashOps(key).values();
    }

    @Override
    public Long hLen(String key) {
        return redisTemplate.boundHashOps(key).size();
    }

    @Override
    public List<Object> hMget(String key, List<Object> field) {
        return redisTemplate.boundHashOps(key).multiGet(field);
    }

    @Override
    public Boolean hSetNx(String key, Object field, Object value) {
        return redisTemplate.boundHashOps(key).putIfAbsent(field, value);
    }

    @Override
    public Long geoAdd(String key, Point point, Object member) {
        return redisTemplate.boundGeoOps(key).add(point, member);
    }

    @Override
    public Long geoAdd(String key, Map<Object, Point> memberCoordinateMap) {
        return redisTemplate.boundGeoOps(key).add(memberCoordinateMap);
    }

    @Override
    public List<Point> geoPosition(String key, Object... members) {
        return redisTemplate.boundGeoOps(key).position(members);
    }

    @Override
    public Distance geoDistance(String key, Object member1, Object member2, Metrics metric) {
        return redisTemplate.boundGeoOps(key).distance(member1, member2, metric);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return redisTemplate.boundGeoOps(key).radius(member, distance, args);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Circle circle, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return redisTemplate.boundGeoOps(key).radius(circle, args);
    }

    @Override
    public List<String> geohash(String key, Object... members) {
        return redisTemplate.boundGeoOps(key).hash(members);
    }

    @Override
    public Long geoRemove(String key, Object... members) {
        return redisTemplate.boundGeoOps(key).remove(members);
    }
}
