package com.fei.netty.springmvc.redis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@JedisInterface
public interface IJedisHashCommand {

	Long hset(String key, String field, String value);

	String hget(String key, String field);

	Long hsetnx(String key, String field, String value);

	String hmset(String key, Map<String, String> hash);

	List<String> hmget(String key, String... fields);

	Long hincrBy(String key, String field, long value);

	Double hincrByFloat(final String key, final String field, final double value);

	Boolean hexists(String key, String field);

	Long hdel(String key, String... field);

	Long hlen(String key);

	Set<String> hkeys(String key);

	List<String> hvals(String key);

	Map<String, String> hgetAll(String key);

	Long hset(byte[] key, byte[] field, byte[] value);

	byte[] hget(byte[] key, byte[] field);

	Long hsetnx(byte[] key, byte[] field, byte[] value);

	String hmset(byte[] key, Map<byte[], byte[]> hash);

	List<byte[]> hmget(byte[] key, byte[]... fields);

	Long hincrBy(byte[] key, byte[] field, long value);

	Double hincrByFloat(byte[] key, byte[] field, double value);

	Boolean hexists(byte[] key, byte[] field);

	Long hdel(byte[] key, byte[]... field);

	Long hlen(byte[] key);

	Set<byte[]> hkeys(byte[] key);

	Collection<byte[]> hvals(byte[] key);

	Map<byte[], byte[]> hgetAll(byte[] key);

	Long del(String key);

	Long del(byte[] key);

}
