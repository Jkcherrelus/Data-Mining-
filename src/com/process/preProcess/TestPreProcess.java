package com.process.preProcess;

import java.util.HashMap;

public class TestPreProcess {
	
	private HashMap<String,Double> trainingWordCounts = new HashMap<String,Double>();
	private HashMap<String,Double> processedWordCounts = new HashMap<String,Double>();
	
	public TestPreProcess (HashMap<String,Double> trainingWordCounts){
		this.trainingWordCounts = trainingWordCounts;
	}
	
	
	
	public HashMap<String,Double> runPreProcess(){
		
		for (String key : trainingWordCounts.keySet()){
			if(trainingWordCounts.get(key) >= 50){
				if(!key.matches("^.*[^a-zA-Z0-9 ].*$"))
					processedWordCounts.put(key, trainingWordCounts.get(key));
			}	
		}
		
		return processedWordCounts;
		
	}

}
