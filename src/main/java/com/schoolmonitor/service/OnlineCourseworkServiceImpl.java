package com.schoolmonitor.service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.schoolmonitor.messagebroker.MessageBrokerAndElasticClientFactory;

import net.minidev.json.JSONObject;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 28, 2020
 */
@Service
public class OnlineCourseworkServiceImpl implements OnlineCourseworkService{
	
	Logger logger = LoggerFactory.getLogger(OnlineCourseworkServiceImpl.class.getName());
	
	public Object searchOnlineCoursework(String searchTerm) throws IOException {
		
 		KafkaProducer<String, String> producer=MessageBrokerAndElasticClientFactory.createProducer();
		//create a producer Record
		ProducerRecord<String, String> producerRecord =new ProducerRecord<String, String>("relevant_videos", searchTerm);
		
		// send data -asynchronous
		producer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if ( null!=e) {
                    logger.error("Producer sending data failed", e);
                }
            }
        });
		//flush and close
		producer.close();
		
		RestHighLevelClient client = MessageBrokerAndElasticClientFactory.createElasticSearchClient();

		KafkaConsumer<String, String> consumer = MessageBrokerAndElasticClientFactory.createConsumer("relevant_videos");
		
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> consumerRecord : records) {
				SearchRequest searchRequest = new SearchRequest("onlinecoursework");
				
				SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder(); 
				searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchTerm, "url","category","name","author","Thumbnail")); 
				searchRequest.source(searchSourceBuilder); 
				
				SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
				SearchHits hits = searchResponse.getHits();
				
				SearchHit[] searchHits = hits.getHits();
				
				List<Map<String, Object> > results=new ArrayList<Map<String, Object>>();
				
				for (SearchHit hit : searchHits) {
					Map<String, Object> sourceAsMap = hit.getSourceAsMap();
					results.add(sourceAsMap);
				}

				client.close();
			return	results;
			
			}
			
		}
		
	}
}
