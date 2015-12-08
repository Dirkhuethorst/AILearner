package test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Classifier.ClassifierClass;
import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;

public class TestEmails {

	public TestEmails() {

	}
	public static void main(String[] args) {
		Map<String, String> array = new HashMap<String, String>();
		array.put("spam", "spm");
		array.put("ham", "msg");
		MNBTClassifer classifier = new MNBTClassifer(array);
		Tokenizer tokenizer = new Tokenizer();
		TokenizeDirectory dir = new TokenizeDirectory();
		dir.DirectoryTokenizer("EMAILS", classifier.getClasses());
		classifier.updatevocsize();
		String directorypath = "TESTMAILS";
		ClassifierClass[] classes = classifier.getClasses();
		TokenizeDirectory tokenizedirectory = new TokenizeDirectory();
		double correct = 0;
		double incorrect = 0;
		File directory = new File(directorypath);
		File[] directoryListing = directory.listFiles();
		System.out.println(Arrays.toString(directoryListing));
		if (directoryListing != null) {
			for (File child : directoryListing) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypath + "/" +child.getName();
					String category = tokenizedirectory.getCategory(classes, filename);
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
