package com.stockpattern.demo.strategy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.stockpattern.demo.dao.StockPatternDao;
import com.stockpattern.demo.indicators.Indicators;
import com.stockpattern.demo.models.StockPrice;

@Component("stockPatternStrategy")
public class StockPatternStrategyImpl implements StockPatternStrategy {

	@Resource(name = "stockPatternDao")
	StockPatternDao stockPatternDao;
	
	@Override
	public List<StockPrice> getMovingAverage(Date fromDate,int averageScale) {
		
		List<StockPrice>  stockPriceList = stockPatternDao.findByDayInterval(fromDate, averageScale);
		
		List<Float> closePriceList = stockPriceList.stream().map(stock->stock.getClosePrice()).collect(Collectors.toList());
		
		Indicators.getMovingAverage(closePriceList, averageScale);
		
		return stockPriceList;
	}
	
	@Override
	public List<StockPrice> backTestMovingAverage(Date fromDate,int averageScale) {
		
		List<StockPrice>  stockPriceWithMA = new ArrayList<StockPrice>();
		
		while(fromDate.compareTo(new Date()) < 0)
		{
			
			List<StockPrice>  stockPriceList = stockPatternDao.findByDayInterval(fromDate, averageScale);
			
			List<Float> closePriceList = stockPriceList.stream().map(stock->stock.getClosePrice()).collect(Collectors.toList());
			
			float movingAvg = Indicators.getMovingAverage(closePriceList, averageScale);
			
			//for(StockPrice dayCadle : stockPriceList)
			for(int i=0;i<stockPriceList.size();i++)
			{
				StockPrice dayCadle = stockPriceList.get(i);
				
				if(dayCadle.getMarketDate().compareTo(fromDate) == 0)
				{
					dayCadle.setMovingAverage(movingAvg);
					
					if(movingAvg < dayCadle.getLowPrice() && (dayCadle.getLowPrice()-dayCadle.getMovingAverage())<=2)
					{
						dayCadle.setHasSupport(true);
					}
					
					if(stockPriceWithMA.size()>=1)
					{
						StockPrice previousDayCandle = stockPriceWithMA.get(stockPriceWithMA.size()-1);
						if(previousDayCandle.isHasSupport())
						{
							System.out.println("SUPPORT CANDLE >> "+previousDayCandle);
							boolean isGreenCandle = dayCadle.getClosePrice()>dayCadle.getOpenPrice();
							if(isGreenCandle)
							{
								dayCadle.setEntry(true);
								float buyPrice = dayCadle.getHighPrice()+1;
								float stopLoss = dayCadle.getLowPrice()-1;
								float targetPrice = buyPrice+((buyPrice-stopLoss)*2);
								dayCadle.setOrderDetails("BUY : "+buyPrice+" STOPLOSS : "+stopLoss+" TARGET "+targetPrice);
								System.out.println("ENTRY CANDLE >> "+dayCadle);
								StockPrice exitPrice = getExit(dayCadle);
								if(null!=exitPrice)
								{
									dayCadle.setTradeResult(exitPrice.getTradeResult());
									System.out.println("EXIT CANDLE >> "+exitPrice);
								}
							}
						}
					}
					
					stockPriceWithMA.add(dayCadle);
				}
			}
			
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			fromDate = calendar.getTime();
			
		}
		
		return stockPriceWithMA;
		
	}
	
	private StockPrice getExit(StockPrice entry)
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(entry.getMarketDate());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		
		List<StockPrice>  stockPriceList = stockPatternDao.getDayCandlesAfter(calendar.getTime());
		
		float buyPrice = entry.getHighPrice()+1;
		float stopLossPrice = entry.getLowPrice()-1;
		float targetPrice = buyPrice+((buyPrice-stopLossPrice)*2);
		StockPrice exitPrice = null;
		
		for(StockPrice dayCandle : stockPriceList)
		{
			if(dayCandle.getLowPrice() <= targetPrice && targetPrice <=dayCandle.getHighPrice())
			{
				dayCandle.setExit(true);
				dayCandle.setTradeResult("TARGET_EXIT ON "+new SimpleDateFormat("dd-MMM-yyyy").format(dayCandle.getMarketDate()));
				exitPrice =  dayCandle;
				break;
			}
			if(dayCandle.getLowPrice() <= stopLossPrice && stopLossPrice <=dayCandle.getHighPrice())
			{
				dayCandle.setExit(true);
				dayCandle.setTradeResult("STOP_LOSS ON "+new SimpleDateFormat("dd-MMM-yyyy").format(dayCandle.getMarketDate()));
				exitPrice =  dayCandle;
				break;
			}
			
		}
		
		return exitPrice;
	}
	

}
