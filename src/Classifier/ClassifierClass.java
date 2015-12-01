package Classifier;

import java.util.HashMap;
import tokenize.Tokenizer;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassifierClass {
	private String classname;
	
	private Map<String, Integer> dict = new HashMap<String, Integer>();
	
	
	public ClassifierClass(String name){
		classname = name;
		
	}
	
	public String getname(){
		return classname;
	}
	
	public Set<String> returnkeySet(){
		return dict.keySet();
	}
	
	
	
	public void updatewords(List<String> words){
		for (String word : words) {// loop through all words.
			if (dict.containsKey(word)) {// check if the dictionary contains
											// the word(as a key).
				dict.put(word, dict.get(word) + 1);// then increment the
													// occurence amount for
													// that word with 1.
			} else {
				dict.put(word, 1);// otherwise add a new (key,value) pair to
									// the map initially with a value equal
									// to 1.
			}
		}
		
	}
	
	public int getvocsize(){
	
		return dict.size();
	}
	
	public int getocc(String word){
		
		int occ = dict.containsKey(word) ? dict.get(word) : 0;
		return occ;
	}
	
	
}
