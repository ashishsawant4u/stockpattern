package com.stockpattern.demo.dao;

import java.util.Date;
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


	@Override
	public List<StockPrice> findByDayInterval(Date fromDate, int scale) {

		String DAY_INTERVAL_QUERY = "SELECT * FROM daily_eod WHERE market_date <= ? ORDER BY market_date DESC LIMIT ?";
		
		List<StockPrice> StockPriceList = jdbcTemplate.query(DAY_INTERVAL_QUERY , 
				new Object[] { fromDate,scale },
				new BeanPropertyRowMapper<StockPrice>(StockPrice.class));
		
        return StockPriceList;
	}


	@Override
	public List<StockPrice> getDayCandlesAfter(Date fromDate) {
	
		String DAY_INTERVAL_QUERY = "SELECT * FROM daily_eod WHERE market_date >= ? ORDER BY market_date ASC";
		
		List<StockPrice> StockPriceList = jdbcTemplate.query(DAY_INTERVAL_QUERY , 
				new Object[] { fromDate },
				new BeanPropertyRowMapper<StockPrice>(StockPrice.class));
		
        return StockPriceList;
	}

}
