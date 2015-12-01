package Classifier;

//import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MNBTClassifer {
	private int k = 1;
	//public Map<String, Integer> spamdict = new HashMap<String, Integer>();
	//public Map<String, Integer> hamdict = new HashMap<String, Integer>();
	private Set<String> vocabulary = new HashSet<String>();
	private int numberofclasses;
	private ClassifierClass[] classes; 
	
	public MNBTClassifer(int amountclasses, String[] names){
		numberofclasses = amountclasses;
		classes = new ClassifierClass[amountclasses];
		for (int i = 0; i < numberofclasses; i++){ 
			String classname = names[i];
			classes[i] = new ClassifierClass(classname);
			
		}
	}

	//calculate estimator for a word, given a class.
	public double wordprob(String word, ClassifierClass cc) {
		/**spamdict.put("viagra", 5);
		spamdict.put("girls", 1);
		spamdict.put("discount", 1);
		spamdict.put("coupon", 1);
		hamdict.put("viagra", 1);
		hamdict.put("schedule", 1);
		hamdict.put("meeting", 1);
		vocabulary.add("viagra");
		vocabulary.add("girls");
		vocabulary.add("discount");
		vocabulary.add("schedule");
		vocabulary.add("meeting");
		vocabulary.add("coupon"); //we kunnen als we meerdere voc. hebben ook addall gebruiken,
		//deze laat dubbele woorden weg.
		
		**/
		int V = vocabulary.size();
		int occ = cc.getocc(word);
		int Nc = cc.getvocsize();
		double estimator = (occ + k)/ (Nc + V);
		
		return estimator;
	}
	
	public String classify(ArrayList<String> text){
		double[] finalprob = new double[numberofclasses];
		Map<String, Integer> tempmap = new HashMap<String, Integer>(); //tijdelijke map om voorkomen van woorden in op te slaan
		for (String word : text) {// loop through all words.
			if (tempmap.containsKey(word)) {// check if the dictionary contains
											// the word(as a key).
				tempmap.put(word, tempmap.get(word) + 1);// then increment the
													// occurence amount for
													// that word with 1.
			} else {
				tempmap.put(word, 1);// otherwise add a new (key,value) pair to
									// the map initially with a value equal
									// to 1.
			}
		}
		Set<String> uniquewords = new HashSet<String>();
		uniquewords.addAll(text);
		ArrayList<String> uniquewordslist = new ArrayList<String>(uniquewords);
		
		double[] prob = new double[uniquewordslist.size()];
		
		for (int i = 0; i < numberofclasses; i++){ 
			double prior = classes[i].getvocsize()/vocabulary.size();
			finalprob[i] = prior;
			for(int x=0; x < uniquewordslist.size(); x++){
				int power = tempmap.get(uniquewordslist.get(x));
				
				prob[x] = wordprob(uniquewordslist.get(x), classes[i]);
				finalprob[i] = finalprob[i] * Math.pow(prob[x], power);
				
			}
			
			
		}
		//get maximum value of finalprob[i]
		double vmax = finalprob[0];
		int imax = 0;
		for (int i = 0; i < finalprob.length; i++){
			if (finalprob[imax] > vmax){
			imax = i;
			vmax = finalprob[imax];
			}
		}
		
		return classes[imax].getname();
	}
	
	public void updatevocsize(){
		
		for (int i = 1; i <= numberofclasses; i++){ 
			vocabulary.addAll(classes[i].returnkeySet());
			
		}
	}
	
	
	public static void main(String[] args) {
		String[] array = {"spam", "ham"};
		ArrayList<String> testfile= new ArrayList<String>();
		testfile.add("viagra");
		testfile.add("discount");
		MNBTClassifer classifier = new MNBTClassifer(2, array);
		classifier.updatevocsize();
		classifier.classify(testfile);
	}
	
	
}
