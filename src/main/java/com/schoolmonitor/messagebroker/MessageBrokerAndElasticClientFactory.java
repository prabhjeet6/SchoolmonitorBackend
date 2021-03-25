package com.schoolmonitor.messagebroker;

import java.util.Collections;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class MessageBrokerAndElasticClientFactory {

	/**
	 * @author PrabhjeetS
	 * @version 1.0 Dec 28, 2020
	 */
public static KafkaProducer<String, String> createProducer(){
    String bootstrapServers = "127.0.0.1:9092";

    // create Producer properties
    Properties properties = new Properties();
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

    // create safe Producer
    properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
    properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
    properties.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
    properties.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5"); // kafka 2.0 >= 1.1 so we can keep this as 5. Use 1 otherwise.

    // high throughput producer (at the expense of a bit of latency and CPU usage)
    properties.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
    properties.setProperty(ProducerConfig.LINGER_MS_CONFIG, "20");
    properties.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(32*1024)); // 32 KB batch size

    // create the producer
    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
    return producer;
}

public static RestHighLevelClient createElasticSearchClient() {
	//https://g68yjnfth8:730krklpfa@schoolmonitor-5569875963.us-east-1.bonsaisearch.net:443
	String hostName = "schoolmonitor-5569875963.us-east-1.bonsaisearch.net";
	String userName = "g68yjnfth8";
	String password = "730krklpfa";
	// Do not do, if you are running a local Elastic search instance
	final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));
	RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, 443, "https"))
			.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {

				public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
					return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
				}
			});
	RestHighLevelClient client = new RestHighLevelClient(builder);
	return client;
}


public static KafkaConsumer<String, String> createConsumer(String topic) {

	String bootstrapServers = "127.0.0.1:9092";
	String groupId = "schoolmonitor-elasticsearch";
	// create consumer configuration
	Properties properties = new Properties();
	properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	// earliest/latest/none
	properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    
	//To disable auto commit of offsets to be able to commit manually; true by default
	properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		
	//To get only 10 records per poll
	properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
	
	// create consumer
	KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

	// subscribe consumer to our topic(s)
	consumer.subscribe(Collections.singleton(topic));
	return consumer;
}

}