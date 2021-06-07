/**
 * 
 */
package com.schoolmonitor.config;

import java.time.Duration;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;



/**
 * @author Prabhjeet Singh
 *
 *         Jun 5, 2021
 */
@Configuration
public class CacheConfiguration {

	/**
	 * The JDK-based Cache implementation resides under
	 * org.springframework.cache.concurrent package. It allows one to use
	 * ConcurrentHashMap as a backing Cache store.As the cache is created by the
	 * application, it is bound to its lifecycle, making it suitable for basic use
	 * cases, tests or simple applications. The cache scales well and is very fast
	 * but it does not provide any management or persistence capabilities nor
	 * eviction contracts.
	 **/
	/*
	 * @Bean public CacheManager schoolmonitorCacheManager() { return new
	 * ConcurrentMapCacheManager("searchCache"); }
	 */

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {

		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(
				"", 12458);
		if (!config.getPassword().isPresent())
			config.setPassword(RedisPassword.of(""));
		return new JedisConnectionFactory(config);

	}

	@Bean
	public RedisCacheManager schoolmonitorCacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
			    .entryTtl(Duration.ofSeconds(3600))
				.disableCachingNullValues();
		
		RedisCacheManager schoolmonitorCacheManager = RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(config)
				.withInitialCacheConfigurations(Collections.singletonMap("searchCache", config.disableCachingNullValues()))
				.transactionAware()
				.build();
		return schoolmonitorCacheManager;
	}
}
