package com.stockpattern.demo.indicators;

import java.util.List;

public class Indicators {
	
	
	public static float getMovingAverage(List<Float> priceList,int averageScale)
    {
		float sum = 0.0F;
		
		
		for(float price : priceList)
		{	
			sum = sum +price;
		}
		
		//System.out.println("SUM >> "+sum);
		
		float avg = sum/averageScale;
		
		//System.out.println("AVG >> "+avg);
		
		return avg; 
    }

}
