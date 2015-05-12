package testing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;


public class ResourceConstraintCrawler {
	
	public Map<String,Vector<String>> loadWikiDumpURLCategoryMapping(String fileName){
		
		Map<String,Vector<String>> urlCategoryMapping = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream(fileName);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         urlCategoryMapping = (Map<String,Vector<String>>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Map<String,Vector<String>> class not found");
	         c.printStackTrace();
	      }
		
		System.out.println("Printing all URL-Category mapping :");
//		for(String str : urlCategoryMapping.keySet()){
//			System.out.println("URL : " + str + ", Category : " + urlCategoryMapping.get(str));
//		}
		
		return urlCategoryMapping;
	}
	
	
	public HashSet<String> loadGoldCategories(String fileName){
		
		BufferedReader br = null;
		HashSet<String> hs = new HashSet<String>();
		
		try {
 
			String line;
 
			br = new BufferedReader(new FileReader("inputFolder/"+fileName));
 
			System.out.println("Printing all Gold-Category :");			
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();				
//				System.out.println(line);				
				hs.add(line);
			}
			
			return hs;
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	public Result computeAccuracy(String filePath){
		Result res = null;
		int pos = 0;
		int neg = 0;
		
		
		BufferedReader br = null;
		 
		try { 
			String line;
			br = new BufferedReader(new FileReader(filePath));
 
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				line = line.replaceAll("http://en.wikipedia.org/wiki/", "");
				//System.out.println(line);
				
				// Find categories
				Vector<String> tempCategory = Test.allURLCategoryMapping.get(line);
				
				boolean isPresent = false; 
				
				if (tempCategory != null)
				{	// Check if anyone category is present in Gold Category list
					for(String cat : tempCategory){
						cat = cat.trim().toLowerCase();
						if(Test.allGoldCategories.contains(cat)){
							// If anyone of the category matches then increment positive count
							pos = pos + 1;
							System.out.println("Category !!! : " + cat);
							isPresent = true;
							break;
						}
					}
				}
				// If none of the category match then increment negative count
				if (!isPresent) {
					neg = neg+1;
				}
			}
			
			res = new Result();
			res.hitCount = pos;
			res.failCount = neg;
			res.accuracy = (res.hitCount * 100.0f)/(res.hitCount+res.failCount);
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return res;
		
	}
	
	
	public void printData(String filePath){
		BufferedReader br = null;
		 
		try {
 
			String line;
 
			br = new BufferedReader(new FileReader(filePath));
 
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				line = line.replaceAll("http://en.wikipedia.org/wiki/", "");
				System.out.println(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
