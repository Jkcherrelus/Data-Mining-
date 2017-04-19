package com.process.preProcess;

import java.util.HashMap;

public class TestPreProcess {
	
	private HashMap<String,Integer> trainingWordCounts = new HashMap<String,Integer>();
	private HashMap<String,Integer> processedWordCounts = new HashMap<String,Integer>();
	
	public TestPreProcess (HashMap<String,Integer> trainingWordCounts){
		this.trainingWordCounts = trainingWordCounts;
	}
	
	
	
	public HashMap<String,Integer> runPreProcess(){
		
		for (String key : trainingWordCounts.keySet()){
			if(trainingWordCounts.get(key) >= 50){
				if(!key.matches("^.*[^a-zA-Z0-9 ].*$"))
					processedWordCounts.put(key, trainingWordCounts.get(key));
			}	
		}
		
		System.out.println(processedWordCounts);
		
		System.out.println(processedWordCounts.size());
		
		return processedWordCounts;
		
		
		
	}

}
