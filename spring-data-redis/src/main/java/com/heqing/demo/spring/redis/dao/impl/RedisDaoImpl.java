package com.heqing.demo.spring.redis.dao.impl;

import com.heqing.demo.spring.redis.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisDaoImpl implements RedisDao {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
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
        return redisTemplate.opsForValue().append(key, value);
    }

    @Override
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(String key, long decrement) {
        return redisTemplate.opsForValue().decrement(key, decrement);
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long increment(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public Double increment(String key, double increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public String range(String key, long startOffset, long endOffset) {
        return redisTemplate.opsForValue().get(key, startOffset, endOffset);
    }

    @Override
    public Long lLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long lLeftPushAll(String key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long lRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public Long lRightPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Object lLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Object lLeftPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    @Override
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    @Override
    public Object lRightPop(String key, long timeout, TimeUnit unit) {
        return redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    @Override
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public void lSet(String key, long index, Object value) {
        long size = lSize(key);
        if(index < size) {
            redisTemplate.opsForList().set(key, index, value);
        }
    }

    @Override
    public void lTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    @Override
    public Long sAdd(String key, Object... members) {
        return redisTemplate.opsForSet().add(key, members);
    }

    @Override
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    @Override
    public Boolean sIsMember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    @Override
    public Boolean sMove(String srckey, String dstkey, Object member) {
        return redisTemplate.boundSetOps(srckey).move(dstkey, member);
    }

    @Override
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Long sRemove(String key, Object... members) {
        return redisTemplate.opsForSet().remove(key, members);
    }

    @Override
    public Set<Object> sDiff(List<String> compares) {
        return redisTemplate.opsForSet().difference(compares.get(0), compares.subList(1, compares.size()));
    }

    @Override
    public void sDiffAndStore(List<String> compares, String destination) {
        redisTemplate.opsForSet().differenceAndStore(compares.get(0), compares.subList(1, compares.size()), destination);
    }

    @Override
    public Set<Object> sIntersect(List<String> compares) {
        return redisTemplate.opsForSet().intersect(compares.get(0), compares.subList(1, compares.size()));
    }

    @Override
    public void sIntersectAndStore(List<String> compares, String destination) {
        redisTemplate.opsForSet().intersectAndStore(compares.get(0), compares.subList(1, compares.size()), destination);
    }

    @Override
    public Object sPop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    @Override
    public Object sRandember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    @Override
    public Set<Object> sUnion(List<String> compares) {
        return redisTemplate.opsForSet().union(compares.get(0), compares.subList(1, compares.size()));
    }

    @Override
    public void sUnionStore(List<String> compares, String destination) {
        redisTemplate.opsForSet().unionAndStore(compares.get(0), compares.subList(1, compares.size()), destination);
    }

    @Override
    public Boolean zAdd(String key, Object member, double score) {
        return redisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Long zCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    @Override
    public Double zIncrby(String key, Object member, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, member,score );
    }

    @Override
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    @Override
    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    @Override
    public Long zRank(String key, Object member) {
        return redisTemplate.opsForZSet().rank(key, member);
    }

    @Override
    public Long zRemove(String key, Object... members) {
        return redisTemplate.opsForZSet().remove(key, members);
    }

    @Override
    public Long zRemoveRange(String key, long min, long max) {
        return redisTemplate.opsForZSet().removeRange(key, min, max);
    }

    @Override
    public Long zRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    @Override
    public Set<Object> zReverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    @Override
    public Set<Object> zRevrangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    @Override
    public Long zReverseRank(String key, Object member) {
        return redisTemplate.opsForZSet().reverseRank(key, member);
    }

    @Override
    public Double zScore(String key, Object member) {
        return redisTemplate.opsForZSet().score(key, member);
    }

    @Override
    public Long zUunionAndStore(List<String> compares, String destination) {
        return redisTemplate.opsForZSet().unionAndStore(compares.get(0), compares.subList(1, compares.size()), destination);
    }

    @Override
    public Long zIntersectAndStore(List<String> compares, String destination) {
        return redisTemplate.opsForZSet().intersectAndStore(compares.get(0), compares.subList(1, compares.size()), destination);
    }

    @Override
    public Long zLexCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    @Override
    public void hPutAll(String key, Map<String, Object> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public void hPut(String key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public Long hDelete(String key, Object... member) {
        return redisTemplate.opsForHash().delete(key, member);
    }

    @Override
    public Boolean hasKey(String key, Object member) {
        return redisTemplate.opsForHash().hasKey(key, member);
    }

    @Override
    public Object hGet(String key, String member) {
        return redisTemplate.opsForHash().get(key, member);
    }

    @Override
    public Map<Object, Object> hGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Long hIncrement(String key, String field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    @Override
    public Double hIncrement(String key, String field, double increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    @Override
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    @Override
    public List<Object> hVals(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    @Override
    public Long hLen(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public List<Object> hMget(String key, List<Object> field) {
        return redisTemplate.opsForHash().multiGet(key, field);
    }

    @Override
    public Boolean hSetNx(String key, Object field, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    @Override
    public Long geoAdd(String key, Point point, Object member) {
        return redisTemplate.opsForGeo().add(key, point, member);
    }

    @Override
    public Long geoAdd(String key, Map<Object, Point> memberCoordinateMap) {
        return redisTemplate.opsForGeo().add(key, memberCoordinateMap);
    }

    @Override
    public List<Point> geoPosition(String key, Object... members) {
        return redisTemplate.opsForGeo().position(key, members);
    }

    @Override
    public Distance geoDistance(String key, Object member1, Object member2, Metrics metric) {
        return redisTemplate.opsForGeo().distance(key, member1, member2, metric);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return redisTemplate.opsForGeo().radius(key, member, distance, args);
    }

    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Circle circle, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return redisTemplate.opsForGeo().radius(key, circle, args);
    }

    @Override
    public List<String> geohash(String key, Object... members) {
        return redisTemplate.opsForGeo().hash(key, members);
    }

    @Override
    public Long geoRemove(String key, Object... members) {
        return redisTemplate.opsForGeo().remove(key, members);
    }

    @Override
    public Long logAdd(String key, Object... elements) {
        return redisTemplate.opsForHyperLogLog().add(key, elements);
    }

    @Override
    public Long logSize(String... keys) {
        return redisTemplate.opsForHyperLogLog().size(keys);
    }

    @Override
    public Long logUnion(String destkey, String... sourcekeys) {
        return redisTemplate.opsForHyperLogLog().union(destkey, sourcekeys);
    }

    @Override
    public void logDelete(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }
}
