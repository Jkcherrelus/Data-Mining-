package com.NaiveBayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

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
	private Double totalSpam = 0.0;//total spam emails
	private Double totalReg = 0.0;//total regular emails
	
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
		HashMap<String,Double> test;
		HashMap<String,Double> training;
		
		
		
		//iterate through the files
		for(String testName : testSet.keySet())
		{
			//grab the hash map for the file by name
			test = testSet.get(testName);
			//System.out.println(testName);
			//go through all the training files
			for(String trainName : trainingSet.keySet())
			{
				//System.out.println(trainName);
				//grab the training map by name
				training = trainingSet.get(trainName);
				
				if(trainName.contains("-"))
				{
					//System.out.println(trainName);
					for(String word:test.keySet())
					{
						//check to see if the word appears in the training set
						if(training.containsKey(word) )
						{
							//check to see if the word appears more than once
							if(training.get(word) > 1)
							{
								//
								Double value = (regMap.get(word) == null) ? 0.0:regMap.get(word);
								regMap.put(word, ++value);
							}
						}//end if
					}//end inner most loop
				}//end if
				else //if the file is spam go here
				{

					//System.out.println(trainName);
					for(String word:test.keySet())
					{
						//check to see if the word appears in the training set
						if(training.containsKey(word) )
						{
							//check to see if the word appears more than once
							if(training.get(word) > 1)
							{
								Double value = (spamMap.get(word) == null) ? 0.0:spamMap.get(word);
								spamMap.put(word, ++value);
							}
						}//end if
					}//end inner most loop
				}//end else
			}//end second loop
		}//end outer loop
		
		//is it spam, return the class probability
		classLog(true);
		classLog(false);
		
		System.out.println(regMap);
	}//end method
	

	public Double classLog(boolean spam)
	{
		Double log=0.0;
		for(String word:trainingSet.keySet())
		{
			if(word.contains("-"))
			{
				totalReg++;
			}
			else {
				totalSpam++;
			}
		}
		if(spam)
		{
			return Math.log(totalSpam/(totalReg+totalSpam));
		}
		return Math.log(totalReg/(totalReg+totalSpam));
	}
	
	
	public Double sumLogs(Double count)
	{
	
		return Math.log(count);
	}
	
}
