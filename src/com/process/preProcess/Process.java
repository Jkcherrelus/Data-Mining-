package com.process.preProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public interface Process 
{
	HashMap<String,Integer> getDictionary();
	String fileCreate(String path) throws FileNotFoundException, IOException;
	void makeMap(String mess, ArrayList<String> list,
			HashSet<String> set, HashMap<String, Integer> dictionary);
	
}
