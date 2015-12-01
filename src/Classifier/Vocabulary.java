package Classifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vocabulary {
	private String vocname;
	private Map<String, Integer> dict = new HashMap<String, Integer>();
	private int wordcounter;
	
	public Vocabulary(String classname){
		vocname = classname;
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
		wordcounter = wordcounter + words.size();// update the total amount of words.
	}
	
	public String getvocname(){
		return vocname;
	}
	
	public int getvocsize(){
		return dict.size();
	}
	
	public int getocc(String word){
		int occ = dict.containsKey(word) ? dict.get(word) : 0;
		return occ;
	}
	

}
