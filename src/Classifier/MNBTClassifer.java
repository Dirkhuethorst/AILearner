package Classifier;

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
		for (int i = 1; i <= numberofclasses; i++){ 
			String classname = names[i];
			classes[i] = new ClassifierClass(classname);
			
		}
	}

	
	public double wordprob(String word, Vocabulary classvoc) {
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
		int occ = classvoc.getocc(word);
		int Nc = classvoc.getvocsize();
		double prob = (occ + k)/ (Nc + V);
		
		return prob;
	}
	
	public double classify(){
		return 0;
	}
	
	public static void main(String[] args) {
		String[] array = {"spam", "ham"};
		MNBTClassifer classifier = new MNBTClassifer(2, array);// use either test1() or test2() to run a test.
	}
	
	
}
