package com.process.preProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import com.NaiveBayes.NaiveBayes;

public class main {

	public static void main(String[] args) throws FileNotFoundException 
	{
		//local class objects
				main main = new main();
				HashMap<String,Double> kNNdictionary = new HashMap<String,Double>();
				HashMap<String,Double> trainingWordCounts = new HashMap<String,Double>();
				HashMap<String,Double> processedkNNdictionary = new HashMap<String,Double>();
				
				//training files
				File[] trainingFile = new File("train").listFiles();
				File[] testingFile = new File("test").listFiles();
				
				//split the files into spam and non spam
				ArrayList<File> spamFiles = new ArrayList<File>();
				ArrayList<File> emailFiles = new ArrayList<File>();
				ArrayList<File> allTrainingFiles = new ArrayList<File>();
				ArrayList<File> allTestingFiles = new ArrayList<File>();
				
				//add the files to the array list 
				for(int i=0;i<trainingFile.length;i++)
				{
					
					allTrainingFiles.add(trainingFile[i]);
					
					if(trainingFile[i].getName().startsWith("spm"))
					{
						spamFiles.add(trainingFile[i]);
					}
					else
					{
						emailFiles.add(trainingFile[i]);
					}

				}
				
				for(int i=0;i<testingFile.length;i++)
				{
					
					allTestingFiles.add(testingFile[i]);
				}

		///////////////RUNNING THE KNN SECTION/////////////////////////////////////////////////////
				//Getting the dictionary of words from the training set
						for(int i = 0; i < allTrainingFiles.size(); i++){
							Scanner sc = new Scanner(new FileReader(allTrainingFiles.get(i)));
						    while(sc.hasNext()){
						        String s = sc.next();
						        kNNdictionary.put(s, 0.0);
						        
						        if(trainingWordCounts.containsKey(s)){
						        	double value = trainingWordCounts.get(s);
							        trainingWordCounts.put(s, ++value);
						        }else {
						        	trainingWordCounts.put(s, 0.0);
						        }
						        
						        
						    }
						    
						    sc.close();
						}					
				
				TestNB testNB = new TestNB(allTrainingFiles, allTestingFiles);
				testNB.runNB();
				
				kNN test1 = new kNN(allTrainingFiles, allTestingFiles, 1, kNNdictionary);
				kNN test3 = new kNN(allTrainingFiles, allTestingFiles, 3, kNNdictionary);
				kNN test5 = new kNN(allTrainingFiles, allTestingFiles, 5, kNNdictionary);
				kNN test20 = new kNN(allTrainingFiles, allTestingFiles, 20, kNNdictionary);
				
//				try {
//					test1.runKNN();
//					test3.runKNN();
//					test5.runKNN();
//					test20.runKNN();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				
				System.out.println("BEGINNING THE PREPROCESSING");
				TestPreProcess test = new TestPreProcess(trainingWordCounts);
				processedkNNdictionary = test.runPreProcess();
				System.out.println("FINISHED THE PREPROCESSING");
				
				kNN processedTest1 = new kNN(allTrainingFiles, allTestingFiles, 1, processedkNNdictionary);
				kNN processedTest3 = new kNN(allTrainingFiles, allTestingFiles, 3, processedkNNdictionary);
				kNN processedTest5 = new kNN(allTrainingFiles, allTestingFiles, 5, processedkNNdictionary);
				kNN processedTest20 = new kNN(allTrainingFiles, allTestingFiles, 20, processedkNNdictionary);
				
//				try {
//					processedTest1.runKNN();
//					processedTest3.runKNN();
//					processedTest5.runKNN();
//					processedTest20.runKNN();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
				
				//Naive-Bayes
//				NaiveBayes naive = new NaiveBayes(processedTest1.getTestFiles(),processedTest1.getTrainingFiles());
//				naive.classifyTest();
				

		///////////////ENDING THE KNN SECTION/////////////////////////////////////////////////////
				System.out.println("program end");

				
			}



}
