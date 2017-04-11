package com.process.preProcess;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class main {

	public static void main(String[] args) 
	{
		//local class objects
		main main = new main();
		HashMap<String,Integer> dictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> spamDictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> emailDictionary = new HashMap<String,Integer>();
		
		
		//maps that hold all the words that appear more than 50 times
		HashMap<String,Integer> trimDictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> spamTrimDictionary = new HashMap<String,Integer>();
		HashMap<String,Integer> emailTrimDictionary = new HashMap<String,Integer>();
		
		//sets for all the emails by type
		HashSet<String> set = new HashSet<String>();
		HashSet<String> spamSet = new HashSet<String>();
		HashSet<String> emailSet = new HashSet<String>();
		
		//training files
		File[] trainingFile = new File("train").listFiles();
		
		//split the files into spam and non spam
		ArrayList<File> spamFiles = new ArrayList<File>();
		ArrayList<File> emailFiles = new ArrayList<File>();
		
		//add the files to the array list 
		for(int i=0;i<trainingFile.length;i++)
		{
			if(trainingFile[i].getName().startsWith("spm"))
			{
				spamFiles.add(trainingFile[i]);
			}
			else
			{
				emailFiles.add(trainingFile[i]);
			}

		}

		
		PreProcess spamPreProcess = new PreProcess(spamSet,spamDictionary, spamFiles);
		PreProcess emailPreProcess = new PreProcess(emailSet,emailDictionary, emailFiles);
		
		spamPreProcess.trim(spamTrimDictionary, spamSet);
		emailPreProcess.trim(emailTrimDictionary, emailSet);
		
		System.out.println("spam files: \n" + spamTrimDictionary);
		System.out.println("spam files: \n" + emailTrimDictionary);
		//spamPreProcess.trim(trimDictionary, set);
		
		System.out.println();
		
		System.out.println();
		System.out.println("program end");
		
		System.out.println("im sorry for swearing");
	}


}
