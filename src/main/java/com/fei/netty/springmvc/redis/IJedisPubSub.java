package com.fei.netty.springmvc.redis;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.JedisPubSub;

@JedisInterface
public interface IJedisPubSub {

	Long publish(String channel, String message);

	void subscribe(JedisPubSub jedisPubSub, String... channels);

	void psubscribe(JedisPubSub jedisPubSub, String... patterns);

	Long publish(byte[] channel, byte[] message);

	void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels);

	void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns);

}
