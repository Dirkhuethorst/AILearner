package test;

import java.util.HashMap;
import java.util.Map;

import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;

public class Test {

	public static void main(String[] args) {
		Map<String, String> array = new HashMap<String, String>();
		array.put("spam", "spm");
		array.put("ham", "msg");
		MNBTClassifer classifier = new MNBTClassifer(array);
		Tokenizer tokenizer = new Tokenizer();
		TokenizeDirectory dir = new TokenizeDirectory();
		dir.DirectoryTokenizer("EMAILS", classifier.getClasses());
		classifier.updatevocsize();
		
		String spam = "EMAILS/spma13.txt";
		String ham =  "TESTMAILS/8-817msg1.txt";
		System.out.println("V_SIZE " + classifier.getClasses()[0].getname() + "= " + classifier.getClasses()[0].getvocsize());
		System.out.println("V_SIZE " + classifier.getClasses()[1].getname() + "= " + classifier.getClasses()[1].getvocsize());
		classifier.updatevocsize();
		System.out.println("V_SIZE " + classifier.getClasses()[0].getname() + "= " + classifier.getClasses()[0].getvocsize());
		System.out.println("V_SIZE " + classifier.getClasses()[1].getname() + "= " + classifier.getClasses()[1].getvocsize());
		System.out.println("Supposed to be spam: " + classifier.classify(tokenizer.tokenize(spam)));
		System.out.println("Supposed to be ham: " + classifier.classify(tokenizer.tokenize(ham)));

	
		// TODO Auto-generated method stub

	}

}
