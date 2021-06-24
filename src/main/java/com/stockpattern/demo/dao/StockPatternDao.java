package com.stockpattern.demo.dao;

import java.util.Date;
import java.util.List;

import com.stockpattern.demo.models.StockPrice;

public interface StockPatternDao {

	public List<StockPrice> findAll();
	
	public List<StockPrice> findByDayInterval(Date fromDate,int scale);
	
	public List<StockPrice> getDayCandlesAfter(Date fromDate);
}
