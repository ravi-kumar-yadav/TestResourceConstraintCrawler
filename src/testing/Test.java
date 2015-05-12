package testing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

public class Test {

	/**
	 * @param args
	 */
	
	public static String wikiURLCategoryMappingFile = "/media/ravi/901C32CC1C32ACDA/output_wikidump";
	public static String goldCategoryFile = "CatListFinal.txt";
	public static int depth = 10;
	
	public static Map<String,Vector<String>> allURLCategoryMapping;
	public static HashSet<String> allGoldCategories;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ResourceConstraintCrawler rcc = new ResourceConstraintCrawler();

		// All trimmed and lowerCased, but may contain spaces in between
		allURLCategoryMapping = rcc.loadWikiDumpURLCategoryMapping(wikiURLCategoryMappingFile);
		
		// All trimmed and lowerCased, but may contain spaces in between
		allGoldCategories = rcc.loadGoldCategories(goldCategoryFile);
//		
		ArrayList<Result> accuracy_resource_constraint = new ArrayList<Result>();
		ArrayList<Result> accuracy_nutch_default = new ArrayList<Result>();
		
//		rcc.printData("inputFolder/ResourceConstraintResult/tobeCrawled0");
		
		for(int i=0; i<depth; i++) {
			Result res = rcc.computeAccuracy("inputFolder/ResourceConstraintResult/tobeCrawled"+i);
			accuracy_resource_constraint.add(res);
		}
		
		for(int i=0; i<depth; i++) {
			Result res = rcc.computeAccuracy("inputFolder/NutchResult/tobeCrawled"+i);
			accuracy_nutch_default.add(res);
		}

		
		
		/*****Displaying Accuracies *****/
		
		System.out.println("\nNutch Crawler Accuracy :");
		for(int i=0; i<depth; i++) {
			System.out.println("Depth " + (i+1) + " : " + accuracy_nutch_default.get(i).accuracy);
		}
		
		System.out.println("Resource Constraint Crawler Accuracy :");
		for(int i=0; i<depth; i++) {
			System.out.println("Depth " + (i+1) + " : " + accuracy_resource_constraint.get(i).accuracy);
		}

		
	}

}
