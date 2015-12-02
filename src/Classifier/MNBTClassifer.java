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

	public MNBTClassifer(Map<String, String> names){ 
		numberofclasses = names.size();
		classes = new ClassifierClass[numberofclasses];
		int i = 0;
		for (String child : names.keySet()){ 
			classes[i] = new ClassifierClass(child, names.get(child));
			i += 1;
		}
	}
	public ClassifierClass[] getClasses(){
		return classes;
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
		
		double V = vocabulary.size();
		double occ = cc.getocc(word);
		double Nc = cc.getvocsize();
		double estimator = (occ + k)/ (Nc + V);
		
		return estimator;
	}

	public String classify(ArrayList<String> text){
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

		for (ClassifierClass child : classes){ 
			double files = getTotalFiles();
			//double prior = classes[i].getvocsize()/vocabulary.size();
			double prior = (double) child.getNumberOfFiles()/files;
			System.out.println("total files : " + files);
			System.out.println(child.getname() + "files: " + child.getNumberOfFiles());
			System.out.println(child.getname() + " " + prior);
			for(int x=0; x < uniquewordslist.size(); x++){
				int power = tempmap.get(uniquewordslist.get(x));

				prob[x] = wordprob(uniquewordslist.get(x), child);
				prob[x] = Math.abs(Math.log(prob[x])/Math.log(2));
				double set = child.getFinalProb() + Math.pow(prob[x], power);
				child.setFinalProb(set);
				}
			child.setFinalProb(child.getFinalProb() * prior);
			
			

		}
		for (ClassifierClass child : classes) {
			System.out.println(child.getname() + "final prob: " + child.getFinalProb());
		}
//		System.out.println(finalprob[0] + "<-- final probality on place one");
//		System.out.println(finalprob[1] + "<-- final probality on place two");
		//get maximum value of finalprob[i]
		double vmax = 0;
		ClassifierClass finalClass = null;
		for (ClassifierClass child : classes) {
			if(child.getFinalProb() > vmax){
				vmax = child.getFinalProb();
				finalClass = child;
			}
		}
		
		return finalClass.getname();
	}

	public void updatevocsize(){
		for (ClassifierClass child : classes) {
			vocabulary.addAll(child.returnkeySet());
			System.out.println(child.getname() + " V-Set" + child.returnkeySet());
		}
		System.out.println(vocabulary.toString());
	}

	public int getTotalFiles(){
		int files = 0;
		for (ClassifierClass child : classes){
			files += child.getNumberOfFiles();
		}
		System.out.println(files);
		return files;
	}
	public static void main(String[] args) {
	}


}
