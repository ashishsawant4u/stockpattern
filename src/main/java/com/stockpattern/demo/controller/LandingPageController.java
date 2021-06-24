package com.stockpattern.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockpattern.demo.strategy.StockPatternStrategy;


@Controller
@RequestMapping("/stockpattern")
public class LandingPageController {

	@Resource(name = "stockPatternStrategy")
	StockPatternStrategy stockPatternStrategy;
	
	
	@RequestMapping("/home")
	public String getLandingPage(Model model) throws Exception
	{
		String string = "April 20, 2015";
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date fromDate = format.parse(string);
		
		//model.addAttribute("stockPriceList", stockPatternStrategy.getMovingAverage(fromDate, 44));
		
		model.addAttribute("stockPriceList", stockPatternStrategy.backTestMovingAverage(fromDate, 44));
		return "landingPage";
	}
	
}
