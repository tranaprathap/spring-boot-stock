package com.stockpurchase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockpurchase.model.RequiredStock;
import com.stockpurchase.model.StockData;
import com.stockpurchase.service.StockFetcher;


@RestController
@EnableScheduling
public class StockController {

    private static Logger log = LoggerFactory.getLogger(StockController.class);
    private List<StockData> listOfStockData=null;
    
    @GetMapping("/home")
    public String home(){
    	return "<html>"
    			+ "<body>"
    			+ "<a href='http://localhost:8090/getstocks'>Fetch Stock Data</a><br><br>"
    			+"<a href='http://localhost:8090/buystocks'>Buy Stock Data</a>"
    			+ "</body>"
    			+ "</html>";
    }
    
    @GetMapping("/getstocks")
   // @Scheduled(cron = "30 * * * * *")
    public List<String> getStockData() {
    	List<String> listOfStockRecords = StockFetcher.getStockData("MSFT");
    	if(!listOfStockRecords.isEmpty()) {
			listOfStockData = StockFetcher.currentStockData(listOfStockRecords);
    	}
    	return listOfStockRecords;
    }
 
    @GetMapping("/buystocks")
	private String buyStock() {
    	String status="Stock not purchased";
		status = StockFetcher.buyStockBusinessLogic(listOfStockData,status);
		return status;
	}

	
   
}