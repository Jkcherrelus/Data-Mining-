package com.process.preProcess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class kNN {
	
	//Variables
	private HashMap<String,Double> dictionary = new HashMap<String,Double>();
	private ArrayList<File> allTrainingFiles = new ArrayList<File>();
	private ArrayList<File> allTestingFiles = new ArrayList<File>();
	private HashMap<String,HashMap<String, Double>> testingFileCounts = new HashMap<String,HashMap<String, Double>>();
	private HashMap<String,HashMap<String, Double>> trainingFileCounts = new HashMap<String,HashMap<String, Double>>();
	private int k;
	private double accuracy,  spamTotal, msgTotal, correctClassification;
	
	public kNN(ArrayList<File> allTrainingFiles, ArrayList<File> allTestingFiles, int k, HashMap<String,Double> dictionary){
		this.allTrainingFiles = allTrainingFiles;
		this.allTestingFiles = allTestingFiles;
		this.k = k;
		this.dictionary = dictionary;
	}
	
	
	public void runKNN() throws IOException{		

//Compare dictionary to testing messages and count words in message (include 0)
		//Finding the counts for all the testing files
		for(int i = 0; i < allTestingFiles.size(); i++){
			
			HashMap<String,Double> wordCountInSingleDoc = new HashMap<String,Double>();
			wordCountInSingleDoc.putAll(dictionary);
			
			//Get file
			Scanner sc = new Scanner(new FileReader(allTestingFiles.get(i)));
		    while(sc.hasNext()){
		    	//Get word
		        String s = sc.next();
		        
		        //If there is a word in testing files that is not in dictionary then skip
		        //If vectors are not the same length then can't do cosine similarity
		        if(dictionary.containsKey(s) == true){
		        	Double value = wordCountInSingleDoc.get(s);
		        	wordCountInSingleDoc.put(s, ++value);
		        }
		        
		    }
		    sc.close();
		    
		    //put into testingFileCounts hash map
			testingFileCounts.put(allTestingFiles.get(i).toString(), wordCountInSingleDoc);
		}

		//Finding the counts for all the training files
		for(int i = 0; i < allTrainingFiles.size(); i++){
			
			HashMap<String,Double> wordCountInSingleDoc = new HashMap<String,Double>();
			wordCountInSingleDoc.putAll(dictionary);
			
			//Get file
			Scanner sc = new Scanner(new FileReader(allTrainingFiles.get(i)));
		    while(sc.hasNext()){
		    	//Get word
		        String s = sc.next();
		        
		        //If there is a word in testing files that is not in dictionary then skip
		        //If vectors are not the same length then can't do cosine similarity
		        if(dictionary.containsKey(s) == true){
		        	Double value = wordCountInSingleDoc.get(s);
		        	wordCountInSingleDoc.put(s, ++value);
		        }
		        
		    }
		    sc.close();
		    
		    //put into testingFileCounts hash map
		    trainingFileCounts.put(allTrainingFiles.get(i).toString(), wordCountInSingleDoc);
		}
		
//Find cosine similarity of test message to every training message
		
		
		//loop through testing files
		for (File test : allTestingFiles) {
			HashMap<String,Double> testingFileMap = new HashMap<String,Double>();
			HashMap<String,Double> cosineSimilarities = new HashMap<String,Double>();
			
			//Get testing file
			testingFileMap = testingFileCounts.get(test.toString());
			
			//Put word counts into vector
			Object[] testingFileVector =  testingFileMap.values().toArray();
			
			//loop through training files		
			for(File train : allTrainingFiles){
				HashMap<String,Double> trainingFileMap = new HashMap<String,Double>();
				
				//Get training file
				trainingFileMap = trainingFileCounts.get(train.toString());
				
				//Put word counts into vector
				Object[] trainingFileVector =  trainingFileMap.values().toArray();
				
				
				//Figure out the cosine similarity
				double cosineSimilarity = 0.0;
				double dotProduct = 0.0;
				double normA = 0.0;
				double normB = 0.0;
				
				for (int i = 0; i < testingFileVector.length; i++) {
			        dotProduct += (Double) testingFileVector[i] * (Double)trainingFileVector[i];
			        normA += Math.pow((Double) testingFileVector[i], 2);
			        normB += Math.pow((Double) trainingFileVector[i], 2);
			    }   
			    
				//Final Calculation for cosine similarity
				cosineSimilarity = dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
				
				//Store training filename and cosine similarity value
				cosineSimilarities.put(train.toString(), cosineSimilarity);
			}
			
			//find k number of max cosine similarity
			ArrayList<Double> kMaxSimilarity = new ArrayList<Double>(k);
			ArrayList<String> kMaxSimilarityFileName = new ArrayList<String>(k);
			double count = 0;
			int minIndex = 0;
			double minValue = 0.0;
			
			for (String key : cosineSimilarities.keySet()){
				   //Populate at the start
				   if(count < k){
					   kMaxSimilarity.add(cosineSimilarities.get(key));
					   kMaxSimilarityFileName.add(key);
					   
					   minIndex = kMaxSimilarity.indexOf(Collections.min(kMaxSimilarity));
					   minValue = kMaxSimilarity.get(minIndex);
					   count++;
				   }else {					   
					   //If new cosine similarity value is greater than min then replace
					   if(cosineSimilarities.get(key) > minValue){		   
						   kMaxSimilarity.set(minIndex, cosineSimilarities.get(key));
						   kMaxSimilarityFileName.set(minIndex, key);
						   
						   minIndex = kMaxSimilarity.indexOf(Collections.min(kMaxSimilarity));
						   minValue = kMaxSimilarity.get(minIndex);   
					   }
					   
				   } 
			}
		
			//check majority type of mail from k number of max similarities
			//int k, spamTotal, msgTotal, correctClassification;
			for (String temp : kMaxSimilarityFileName) {
				if(temp.contains("spm")){
					spamTotal += 1;
				} else {
					msgTotal += 1;
				}
			}
			
			
			//Check if which classification is higher then check that test message 
			//matches that classification. If yes then add to correctClassification variable
			if(spamTotal >= msgTotal){ //If training set says it is spam
				if(test.toString().contains("spm")){
					correctClassification += 1;
				}
			}else { //Training set says it is regular
				if(test.toString().contains("-")){
					correctClassification += 1;
				}
			}
			
			spamTotal = 0;
			msgTotal = 0;
			
		}
		
		accuracy = (correctClassification / allTestingFiles.size()) * 100;
		System.out.println("For k=" + k +", the accuracy of classification is: " + String.format("%.2f", accuracy) + "%");
	}
	
}
