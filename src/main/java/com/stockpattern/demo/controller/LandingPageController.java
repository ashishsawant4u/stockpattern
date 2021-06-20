package com.stockpattern.demo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockpattern.demo.dao.StockPatternDao;


@Controller
@RequestMapping("/stockpattern")
public class LandingPageController {

	@Resource(name = "stockPatternDao")
	StockPatternDao stockPatternDao;
	
	
	@RequestMapping("/home")
	public String getLandingPage(Model model)
	{
		model.addAttribute("stockPriceList", stockPatternDao.findAll());
		
		return "landingPage";
	}
	
}
