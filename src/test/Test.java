package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;

public class Test {

	public static void main(String[] args) {
		Map<String, String> array = new HashMap<String, String>();
		array.put("male", "M");
		array.put("female", "F");
		MNBTClassifer classifier = new MNBTClassifer(array);
		Tokenizer tokenizer = new Tokenizer();
		TokenizeDirectory dir = new TokenizeDirectory();
		dir.DirectoryTokenizer("BLOGS", classifier.getClasses());
		classifier.updatevocsize();
		String dirktest = "TESTTEST.txt";
//		String spam = "TESTMAILS/spmsgc34.txt";
//		String ham =  "TESTMAILS/8-817msg1.txt";
		//System.out.println("V_SIZE " + classifier.getClasses()[0].getname() + "= " + classifier.getClasses()[0].getvocsize());
		//System.out.println("V_SIZE " + classifier.getClasses()[1].getname() + "= " + classifier.getClasses()[1].getvocsize());
		//System.out.println("wordprob /'company/' :" + classifier.wordprob("company", classifier.getClasses()[0]) + " class: " + classifier.getClasses()[0].getname());
		//System.out.println("wordprob /'company/' :" + classifier.wordprob("company", classifier.getClasses()[1]) + " class: " + classifier.getClasses()[1].getname());
		
		System.out.println("Supposed to be female: " + classifier.classify(tokenizer.tokenize(dirktest)));
		//System.out.println("Supposed to be ham: " + classifier.classify(tokenizer.tokenize(ham)));

	
		// TODO Auto-generated method stub

	}

}
