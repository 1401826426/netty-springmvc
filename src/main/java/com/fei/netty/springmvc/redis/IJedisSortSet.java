package com.fei.netty.springmvc.redis;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

@JedisInterface
public interface IJedisSortSet {

	Long zadd(String key, double score, String member);

	Long zadd(String key, double score, String member, ZAddParams params);

	Long zadd(String key, Map<String, Double> scoreMembers);

	Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params);

	Set<String> zrange(String key, long start, long end);

	Long zrem(String key, String... member);

	Double zincrby(String key, double score, String member);

	Double zincrby(String key, double score, String member, ZIncrByParams params);

	Long zrank(String key, String member);

	Long zrevrank(String key, String member);

	Set<String> zrevrange(String key, long start, long end);

	Set<Tuple> zrangeWithScores(String key, long start, long end);

	Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	Long zcard(String key);

	Double zscore(String key, String member);

	Long zadd(byte[] key, double score, byte[] member);

	Long zadd(byte[] key, double score, byte[] member, ZAddParams params);

	Long zadd(byte[] key, Map<byte[], Double> scoreMembers);

	Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params);

	Set<byte[]> zrange(byte[] key, long start, long end);

	Long zrem(byte[] key, byte[]... member);

	Double zincrby(byte[] key, double score, byte[] member);

	Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params);

	Long zrank(byte[] key, byte[] member);

	Long zrevrank(byte[] key, byte[] member);

	Set<byte[]> zrevrange(byte[] key, long start, long end);

	Set<Tuple> zrangeWithScores(byte[] key, long start, long end);

	Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end);

	Long zcard(byte[] key);

	Double zscore(byte[] key, byte[] member);

	Long zcount(String key, double min, double max);

	Long zcount(String key, String min, String max);

	Long zcount(byte[] key, double min, double max);

	Long zcount(byte[] key, byte[] min, byte[] max);

}
