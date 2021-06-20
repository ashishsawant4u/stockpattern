package com.stockpattern.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.stockpattern.demo.models.StockPrice;

@Component("stockPatternDao")
public class StockPatternDaoImpl implements StockPatternDao {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	public List<StockPrice> findAll() {
        List<StockPrice> StockPriceList = jdbcTemplate.query("SELECT * FROM daily_eod", new BeanPropertyRowMapper<StockPrice>(StockPrice.class));
        return StockPriceList;
    }

}
