package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Classifier.ClassifierClass;
import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;

public class TestBlogs {
	public static void main(String[] args) {
		// make instances of necessary classes/variables
		Tokenizer tokenizer = new Tokenizer();
		TokenizeDirectory dir = new TokenizeDirectory();
		
		double correct = 0;
		double incorrect = 0;
		
		//Create the classes male and female with their according identifiers
		Map<String, String> array = new HashMap<String, String>();
		array.put("male", "M");
		array.put("female", "F");
		MNBTClassifer classifier = new MNBTClassifer(array);
		
		//Use the known blogs to 'learn' the program
		dir.DirectoryTokenizer("BLOGS", classifier.getClasses());
		classifier.updatevocsize();
		
		//Identify the map where the blogs to be tested can be found.
		String directorypath = "TESTBLOGS";
		ClassifierClass[] classes = classifier.getClasses();
		//For each .txt file in the directory the class is predicted.
		//This is compared to the actual class in the filename to check it.
		//The check and the correct result will be printed
		File directory = new File(directorypath);
		File[] directoryListing = directory.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypath + "/" +child.getName();
					String category = dir.getCategory(classes, filename);
					String totest = classifier.classify(tokenizer.tokenize(filename));
					if (totest.equals(category)) {
						correct += 1;
					} else {
						incorrect +=1;
					}
					System.out.println("supposed to be " + category + ": " + totest);
				}
			}
		}
		double percentage = correct/(correct+incorrect);
		System.out.println("percentage correct: " + percentage);
	}

}
