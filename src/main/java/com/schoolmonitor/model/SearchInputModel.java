/**
 * 
 */
package com.schoolmonitor.model;

/**
 * @author Prabhjeet Singh
 *
 * Apr 4, 2021
 */
public class SearchInputModel  {

	
	String searchTerm;
	
	/**current page is requested page**/
	int currentPage;	 
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
