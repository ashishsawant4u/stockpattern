package com.stockpattern.demo.dao;

import java.util.List;

import com.stockpattern.demo.models.StockPrice;

public interface StockPatternDao {

	public List<StockPrice> findAll();
}
