/**
 * 
 */
package com.schoolmonitor.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Prabhjeet Singh
 *
 *         May 17, 2021
 */
@Component
public class SearchResponseModel implements Serializable {
	
	private static final long serialVersionUID = -1727209992731478315L;

	public SearchResponseModel() {

	}

	int totalNumberOfRecords;
	List<Map<String, Object>> searchResults;

	public int getTotalNumberOfRecords() {
		return totalNumberOfRecords;
	}

	public void setTotalNumberOfRecords(int totalNumberOfRecords) {
		this.totalNumberOfRecords = totalNumberOfRecords;
	}

	public List<Map<String, Object>> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<Map<String, Object>> searchResults) {
		this.searchResults = searchResults;
	}

}
