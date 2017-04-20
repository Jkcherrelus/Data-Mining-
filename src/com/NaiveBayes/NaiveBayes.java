package com.NaiveBayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class NaiveBayes 
{
	private HashMap<String, HashMap<String, Double>> trainingSet;
	private HashMap<String, HashMap<String, Double>> testSet;
	private HashMap<String, Double> regMap = new HashMap<String,Double>();
	private HashMap<String, Double> spamMap = new HashMap<String,Double>();;
	private boolean spam;
	private boolean email;
	private Double spamCount;
	private Double emailCount;
	
	public NaiveBayes(HashMap<String,HashMap<String,Double>> test, 
			HashMap<String,HashMap<String,Double>> training)
	{
		this.trainingSet = training;
		this.testSet = test;
		spam = false;
		email = false;
	}
	
	
	public void classifyTest()
	{
		Iterator<String> testIterator = testSet.keySet().iterator();
		Iterator<String> trainingIterator = trainingSet.keySet().iterator();
		
		Double count = 0.0;
		while(testIterator.hasNext())
		{
			
			Iterator<Entry<String,Double>> testMapIterate = testSet.get(testIterator.next()).entrySet().iterator();
			while(trainingIterator.hasNext())
			{
				//checks whether the message is a regular message
				if(trainingIterator.next().contains("-"))
				{
					Iterator<Entry<String,Double>> trainingMapIterate = trainingSet.get(trainingIterator.next()).entrySet().iterator();
					while(testMapIterate.hasNext())
					{
						String testWord = testMapIterate.next().getKey();
						
						if(trainingMapIterate.next().getValue() > 0.0)
						{
							regMap.put(testWord, count++);
						}
					}
					count =0.0;
				}
				else {
					Iterator<Entry<String,Double>> trainingMapIterate = trainingSet.get(trainingIterator.next()).entrySet().iterator();
					while(testMapIterate.hasNext())
					{
						String testWord = testMapIterate.next().getKey();
						
						if(testMapIterate.next().getKey() == trainingMapIterate.next().getKey()
								&& trainingMapIterate.next().getValue() > 0)
						{
							spamMap.put(testWord, count++);
						}
					}
					count = 0.0;
				}
			}
			
			System.out.println(regMap);
			
		}
	}
	

	public Double sumLogs(Double count)
	{
		return Math.log(count);
	}
	
}
