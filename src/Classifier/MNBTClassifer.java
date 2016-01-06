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
	private int numberofclasses = 0;
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

		double V = vocabulary.size();
		double occ = cc.getocc(word);
		double Nc = cc.getvocsize();
		double estimator = (occ + k)/ (Nc + (k*V));
		//		System.out.println("Vocabulary : " + V);
		//		System.out.println("Occurrance : " + occ);
		//		System.out.println("NC : " + Nc);
		//		System.out.println("Estimator : " + estimator);
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
			child.setFinalProb(0);
		}
		for (ClassifierClass child : classes){ 
			double files = getTotalFiles();
			//double prior = classes[i].getvocsize()/vocabulary.size();
			double prior = child.getNumberOfFiles()/files;
			//System.out.println("total files : " + files);
			//System.out.println(child.getname() + "files: " + child.getNumberOfFiles());
			//System.out.println(child.getname() + " " + prior);
			for(int x=0; x < uniquewordslist.size(); x++){
				int power = tempmap.get(uniquewordslist.get(x));

				prob[x] = wordprob(uniquewordslist.get(x), child);
				prob[x] = Math.log(prob[x])/Math.log(2);
				prob[x] = prob[x] * power;
				double set = child.getFinalProb() + prob[x];
				child.setFinalProb(set);
			}
			child.setFinalProb(child.getFinalProb() + (Math.log(prior)/Math.log(2)));
			double finalproob = child.getFinalProb();
			String finalproobstring = fmt(finalproob);
			//System.out.println(child.getname() +finalproob );

		}
		
		double vmax = classes[0].getFinalProb();
		ClassifierClass finalClass = null;
		for (ClassifierClass child : classes) {
			if(child.getFinalProb() >= vmax){
				vmax = child.getFinalProb();
				finalClass = child;
			}
		}
		String answerclass = finalClass.getname();
		Integer totfiles = getTotalFiles();
		String totalfiles = totfiles.toString();
		Integer vocsizetest = vocabulary.size();
		String stringvocsize = vocsizetest.toString();
		//System.out.println(answerclass +" " +"TotalFiles = "+totalfiles +" " +"Vocsize = " +stringvocsize);
		return finalClass.getname();
	}

	public void updatevocsize(){
		int i = 0;
		for (ClassifierClass child : classes) {
			vocabulary.addAll(child.returnkeySet());
			i += child.returnkeySet().size();
		}
	}

	public int getTotalFiles(){
		int files = 0;
		for (ClassifierClass child : classes){
			files += child.getNumberOfFiles();
		}
		//System.out.println(files);
		return files;
	}

	public void addclass(Map<String, String> names) throws Exception{
		//check if classname already exists.
		for (String name : names.keySet()){			
			for (int h = 0; h < classes.length; h++){
				if (classes[h].getname().equals(name)){					
					throw new Exception();
				}

			}

		}
		
		//make list of old and new classes.
		numberofclasses = numberofclasses + names.size();
		ClassifierClass[] tempclasses = new ClassifierClass[numberofclasses];
		int i = 0;
		for (String child : names.keySet()){ 
			tempclasses[i] = new ClassifierClass(child, names.get(child));
			i += 1;
		}
		for (ClassifierClass child : classes){
			tempclasses[i]= child;
			i +=1;
		}
		classes = new ClassifierClass[numberofclasses];
		classes = tempclasses;
		
	}

	public static String fmt(double d)
	{
		if(d == (long) d)
			return String.format("%d",(long)d);
		else
			return String.format("%s",d);
	}

	public boolean allclassestrained(){

		for(int x = 0; x < classes.length ; x++){
			if(classes[x].returnkeySet().isEmpty()){
				return false;
			}
		}
		return true;
	}

}