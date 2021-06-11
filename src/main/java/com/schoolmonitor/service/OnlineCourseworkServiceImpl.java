package com.schoolmonitor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
import org.springframework.cache.annotation.Cacheable;
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
	int totalNumberOfRecords = 0;

	SearchHit[] queryElasticSearch(SearchInputModel searchInputModel, RestHighLevelClient client) throws IOException {
		SearchRequest searchRequest = new SearchRequest("onlinecoursework");

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchInputModel.getSearchTerm(), "url", "category",
				"name", "author", "Thumbnail"));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = searchResponse.getHits();

		SearchHit[] searchHits = hits.getHits();
		this.totalNumberOfRecords = searchHits.length;

		SearchHit[] resultPage = Arrays.copyOfRange(searchHits, 2 * (searchInputModel.getCurrentPage() - 1),
				2 * (searchInputModel.getCurrentPage() - 1) + 2);

		return resultPage;
	}

	@Cacheable(key = "#root.args[0].searchTerm.concat('-').concat(#root.args[0].currentPage)", cacheManager = "schoolmonitorCacheManager", cacheNames = "searchCache")
	public Object searchOnlineCoursework(SearchInputModel searchInputModel) throws IOException {

		RestHighLevelClient client = MessageBrokerAndElasticClientFactory.createElasticSearchClient();
		List<Map<String, Object>> searchResults = new ArrayList<Map<String, Object>>();
		for (SearchHit hit : queryElasticSearch(searchInputModel, client)) {
			if (null != hit) {
				Map<String, Object> sourceAsMap = hit.getSourceAsMap();
				searchResults.add(sourceAsMap);
			}
		}
		searchResponseModel.setTotalNumberOfRecords(totalNumberOfRecords);
		searchResponseModel.setSearchResults(searchResults);
		return searchResponseModel;
	}

}
