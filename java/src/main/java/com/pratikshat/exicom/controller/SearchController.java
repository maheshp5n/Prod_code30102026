package com.pratikshat.exicom.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.services.GlobalSearchFilterService;

@RestController
@RequestMapping("api/search")
public class SearchController {
		@Autowired
		com.pratikshat.exicom.repository.GlobalSearchIndexRepository dbGlobalSearch;
		@Autowired
		GlobalSearchFilterService dbSearch;
		
		@GetMapping
		public java.util.List<Map<String,Object>> getSearchResult(@RequestParam("searchkey")String searchString,@CurrentUser LoginUser user){
			//return dbGlobalSearch.findBySearchString(searchString);
			return dbSearch.oldsearch(searchString, user);
		}
		@GetMapping("advance")
		public Map<String,List> getSearchAllResult(@RequestParam("searchkey")String searchString,@CurrentUser LoginUser user){
			
			return dbSearch.getSearchFilter(searchString, user);
		}
}
