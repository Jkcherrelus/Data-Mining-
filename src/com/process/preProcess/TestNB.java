package com.process.preProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class TestNB {
	
	private ArrayList<File> allTrainingFiles = new ArrayList<File>();
	private ArrayList<File> allTestingFiles = new ArrayList<File>();
	private double spamMessageTotal, regMessageTotal, correctClassification, accuracy;
	//new variables
	private HashMap<String, Integer> wordCountInSpam = new HashMap<String, Integer>();
	private HashMap<String, Integer> wordCountInRegular = new HashMap<String, Integer>();
	HashSet<String> wordsInAMessage = new HashSet<String>();
	
	public TestNB(ArrayList<File> allTrainingFiles, 
			ArrayList<File> allTestingFiles){
		this.allTrainingFiles = allTrainingFiles;
		this.allTestingFiles = allTestingFiles;
		
	}
	
	
	public void runNB() throws FileNotFoundException{
		
		//Count how many times each word occurs in spam emails and regular emails of training set
		
		for(int i = 0; i < allTrainingFiles.size(); i++){
			
			HashSet<String> wordsInAMessage = new HashSet<String>();
			
			Scanner sc = new Scanner(new FileReader(allTrainingFiles.get(i)));
			if(allTrainingFiles.get(i).toString().contains("-")){//Then is a regular message
				while(sc.hasNext()){
					String s = sc.next();
					
					wordsInAMessage.add(s);
				}//end while
				
				for(String word : wordsInAMessage){
					
					if(wordCountInRegular.containsKey(word)){
						Integer value = wordCountInRegular.get(word);
						wordCountInRegular.put(word, ++value);
					}else {
						wordCountInRegular.put(word, 1);
					}
					
				}
				regMessageTotal++;
			} else{ //Then is a spam message
				while(sc.hasNext()){
					String s = sc.next();
					
					wordsInAMessage.add(s);
				}
				for(String word : wordsInAMessage){
					
					if(wordCountInSpam.containsKey(word)){
						Integer value = wordCountInSpam.get(word);
						wordCountInSpam.put(word, ++value);
					}else {
						wordCountInSpam.put(word, 1);
					}
					
				}
				spamMessageTotal++;
			}
			sc.close();
		}
		
	
		//Grab a test file
		for (int i = 0; i < allTestingFiles.size(); i++){
			ArrayList<Double> probabilityForSpam = new ArrayList<Double>();
			ArrayList<Double> probabilityForReg = new ArrayList<Double>();
			double spamProb = 0, regProb = 0;
			
			HashSet<String> wordsInTestMessage = new HashSet<String>();
			Scanner sc = new Scanner(new FileReader(allTestingFiles.get(i)));
			while(sc.hasNext()){
				String s = sc.next();
				
				wordsInTestMessage.add(s);
			}
			sc.close();
			
			//now wordsInTestMessage has all the words in this test file
			
			for(String testWord : wordsInTestMessage){
				if(wordCountInSpam.containsKey(testWord)){
					double value = wordCountInSpam.get(testWord) / spamMessageTotal;
					probabilityForSpam.add(value);
				} if(wordCountInRegular.containsKey(testWord)){
					double value = wordCountInRegular.get(testWord) / regMessageTotal;
					probabilityForReg.add(value);
				} if (!wordCountInSpam.containsKey(testWord)){
					double value = (0 + (2 * .5)) / (2 + 2);
					probabilityForSpam.add(value);
				} if (!wordCountInRegular.containsKey(testWord)){
					double value = (0 + (2 * .5)) / (2 + 2);
					probabilityForReg.add(value);
				}
			}

			
			//Calculate P(W|R) and P(W|S)
			//private double spamMessageTotal, regMessageTotal;
			for (int j = 0; j < probabilityForSpam.size(); j++) {
				spamProb += Math.log(probabilityForSpam.get(j));
			}
			for (int k = 0; k < probabilityForReg.size(); k++) {
				regProb += Math.log(probabilityForReg.get(k));
			}
			
			//Now calculate P(W|R)*P(R) and P(W|S)*P(S)
			spamProb = spamProb * (spamMessageTotal / allTrainingFiles.size());
			regProb = regProb * (regMessageTotal / allTrainingFiles.size());
			
			if(spamProb >= regProb){
				if(allTestingFiles.get(i).toString().contains("spm")){
					correctClassification += 1;
				}
			}else {
				if(allTestingFiles.get(i).toString().contains("-")){
					correctClassification += 1;
				}
			}
			
			
			
			
		}
		
		accuracy = (correctClassification / allTestingFiles.size()) * 100;
		System.out.println("For Naive Bayes the accuracy of classification is: " + String.format("%.2f", accuracy) + "%");
		
	}
	
}
