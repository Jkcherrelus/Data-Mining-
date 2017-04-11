package com.process.preProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PreProcess implements Process
{
	private HashMap<String,Integer> dictionary;
	private HashSet<String> set;
	private String path;
	
	//initializes the dictionary
	public PreProcess(HashSet<String> set,HashMap<String,Integer> dictionary,ArrayList<File> file)
	{
		this.set = set;
		this.dictionary = dictionary;
		this.path = path;
		initialize(set,dictionary,file);
	}
	
	private void initialize(HashSet<String> set,HashMap<String,Integer> dictionary,ArrayList<File> file)
	{
		ArrayList<String> list = new ArrayList();
		
		
		try {
			for(int i=0;i<file.size();i+=1)
			{
				String mess =fileCreate(file.get(i).getPath());
				makeMap(mess,list,set,dictionary);

			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	@Override
	public HashMap<String, Integer> getDictionary() {
		// TODO Auto-generated method stub
		return dictionary;
	}
	@Override
	public String fileCreate(String path) throws IOException 
	{
		String message = null;
		String email = "";
		FileReader reader = new FileReader(path);
		BufferedReader buff = new BufferedReader(reader);
		
		
		while((message = buff.readLine()) != null)
		{
			email += message;
			System.out.println("message: " + message);
		}
		
		email = email.toUpperCase();
		
		return email;
	}
	@Override
	public void makeMap(String mess, ArrayList<String> list,
			HashSet<String> set, HashMap<String, Integer> dictionary)
	{
		mess = mess.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
		String[] message = mess.split("\\ ");
	
		int j = 1;
		System.out.println(message.length);
		
		//add all the words to an array list and hashset
		for(int i=0; i<message.length;i++)
		{
			list.add(message[i]);
			set.add(message[i]);//holds all unique words
			
		}
		
		//creates the map for the email
		for(String nextString: set)
		{
			int numTimes = Collections.frequency(list, nextString);
			dictionary.put(nextString, numTimes);
			System.out.println(nextString + " : " +numTimes);
		}
	}
	
	public HashMap<String,Integer> trim(HashMap<String,Integer> trimDictionary,HashSet<String> set)
	{
		String key = "";
		int value = 0;
		Iterator<String> iterate = set.iterator();
		
		while(iterate.hasNext())
		{
			dictionary.entrySet().iterator().next();
			System.out.println(iterate.next());
			
			if(iterate.hasNext())
			{
				key = iterate.next();
				value = dictionary.get(key);
				
			}
			
			if(value > 50)
			{
				trimDictionary.put(key,value);
				System.out.println("value is added to trim:" + value);
			}
			
			
		}

		System.out.println("trim dictionary: " + trimDictionary);
		return trimDictionary;
	}
}
