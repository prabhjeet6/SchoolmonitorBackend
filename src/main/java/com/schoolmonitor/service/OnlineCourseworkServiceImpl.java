package com.schoolmonitor.service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmonitor.messagebroker.MessageBrokerAndElasticClientFactory;
import com.schoolmonitor.model.SearchInputModel;
import com.schoolmonitor.model.SearchResponseModel;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2020
 */
@Service
public class OnlineCourseworkServiceImpl implements OnlineCourseworkService {
	Logger logger = LoggerFactory.getLogger(OnlineCourseworkServiceImpl.class.getName());
	@Autowired
	SearchResponseModel searchResponseModel;
	int totalNumberOfRecords=0;
	void produceSearchTerm(SearchInputModel page) {
		KafkaProducer<String, String> producer = MessageBrokerAndElasticClientFactory.createProducer();
		// create a producer Record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("relevant_videos",
				page.getSearchTerm());
		// send data -asynchronous
		producer.send(producerRecord, new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception e) {
				if (null != e) {
					logger.error("Producer sending data failed", e);
				}
			}
		});
		logger.info("Shutting down producer");
		// flush and close
		producer.close();
		logger.info("Producer has been Shut down");
	}

	SearchHit[] queryElasticSearch(SearchInputModel searchInputModel, RestHighLevelClient client) throws IOException {
		SearchRequest searchRequest = new SearchRequest("onlinecoursework");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(
				QueryBuilders.multiMatchQuery(searchInputModel.getSearchTerm(), "url", "category", "name", "author", "Thumbnail"));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();

		SearchHit[] searchHits = hits.getHits();
		this.totalNumberOfRecords = searchHits.length;
		
		SearchHit[] resultPage = Arrays.copyOfRange(searchHits, 2*(searchInputModel.getCurrentPage()-1), 2*(searchInputModel.getCurrentPage()-1)+2);
		
		return resultPage;
	}

	Object consumeSearchTerm(SearchInputModel searchInputModel, KafkaConsumer<String, String> consumer, RestHighLevelClient client)
			throws IOException {
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> consumerRecord : records) {
			 	for (SearchHit hit : queryElasticSearch(searchInputModel, client)) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap();
					searchResults.add(sourceAsMap);
				}
				logger.info("Shutting down consumer");
				client.close();
				logger.info("Consumer has been shut down");

				searchResponseModel.setTotalNumberOfRecords(totalNumberOfRecords);
				searchResponseModel.setSearchResults(searchResults);
				return searchResponseModel;
			}
		}
	}

	public Object searchOnlineCoursework(SearchInputModel searchInputModel) throws IOException {

		produceSearchTerm(searchInputModel);
		RestHighLevelClient client = MessageBrokerAndElasticClientFactory.createElasticSearchClient();
		KafkaConsumer<String, String> consumer = MessageBrokerAndElasticClientFactory.createConsumer("relevant_videos");
		return consumeSearchTerm(searchInputModel, consumer, client);

	}

}
