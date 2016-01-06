package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Classifier.ClassifierClass;
import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;

public class DEMOMAIL {
	private static ClassifierClass ham;
	private static ClassifierClass spam;
	private static ClassifierClass smallham;
	private static ClassifierClass smallspam;
	public static void main(String[] args) {
		System.out.println("In this demo we will show our classifier will improve after training using blogs.");
		System.out.println("First we will train the Classifier with a small (15) number of files.");
		System.out.println("Then we will test 15 files and check whether the classifier makes a correct prediction");
		System.out.println("The same will be done after training with a large(+- 170) number of files");
		System.out.println("The difference will be shown in the end.");
		Map<String, String> array = new HashMap<String, String>();
		array.put("spam", "");
		array.put("ham", "");
		MNBTClassifer classifier = new MNBTClassifer(array);
		MNBTClassifer smallClassifier = new MNBTClassifer(array);
		ClassifierClass[] classes = classifier.getClasses();
		ClassifierClass[] smallClasses = smallClassifier.getClasses();
		for (ClassifierClass classe : smallClasses){
			if (classe.getname().equals("ham")){
				smallham = classe;
			} else if (classe.getname().equals("spam")){
				smallspam = classe;
			}
		}
		for (ClassifierClass classe : classes){
			if (classe.getname().equals("ham")){
				ham = classe;
			} else if (classe.getname().equals("spam")){
				spam = classe;
			}
		}
		Tokenizer tokenizer = new Tokenizer();
		TokenizeDirectory dir = new TokenizeDirectory();
		try {
			dir.DirectoryTokenizer2("EMAILS/HAM/SMALTRAIN", smallham);
			dir.DirectoryTokenizer2("EMAILS/SPAM/SMALTRAIN", smallspam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double smallcorrect = 0;
		double smallincorrect = 0;
		
		String directorypathSmallHam = "EMAILS/HAM/TEST";
		String directorypathSmallSpam = "EMAILS/SPAM/TEST";
		File directorySmallHAM = new File(directorypathSmallHam);
		File[] directoryListingSmallHAM = directorySmallHAM.listFiles();
		File directorySmallSPAM = new File(directorypathSmallSpam);
		File[] directoryListingSmallSPAM = directorySmallSPAM.listFiles();
		if (directoryListingSmallHAM != null) {
			for (File child : directoryListingSmallHAM) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypathSmallHam + "/" +child.getName();
					String category = "ham";
					String totest = smallClassifier.classify(tokenizer.tokenize(filename));
					if (totest.equals(category)) {
						smallcorrect += 1;
					} else {
						smallincorrect +=1;
					}
					//System.out.println("supposed to be " + "ham" + ": " + totest);
				}
			}
		}
		if (directoryListingSmallSPAM != null) {
			for (File child : directoryListingSmallSPAM) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypathSmallSpam + "/" +child.getName();
					String category = "spam";
					String totest = smallClassifier.classify(tokenizer.tokenize(filename));
					if (totest.equals(category)) {
						smallcorrect += 1;
					} else {
						smallincorrect +=1;
					}
					//System.out.println("supposed to be " + "spam" + ": " + totest);
				}
			}
		}
		
		
		try {
			dir.DirectoryTokenizer2("EMAILS/HAM/TRAIN", ham);
			dir.DirectoryTokenizer2("EMAILS/SPAM/TRAIN", spam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double correct = 0;
		double incorrect = 0;
		String directorypathHam = "EMAILS/HAM/TEST";
		String directorypathSpam = "EMAILS/SPAM/TEST";
		File directoryHAM = new File(directorypathHam);
		File[] directoryListingHAM = directoryHAM.listFiles();
		File directorySPAM = new File(directorypathSpam);
		File[] directoryListingSPAM = directorySPAM.listFiles();
		if (directoryListingHAM != null) {
			for (File child : directoryListingHAM) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypathHam + "/" +child.getName();
					String category = "ham";
					String totest = classifier.classify(tokenizer.tokenize(filename));
					if (totest.equals(category)) {
						correct += 1;
					} else {
						incorrect +=1;
					}
					//System.out.println("supposed to be " + "ham" + ": " + totest);
				}
			}
		}
		if (directoryListingSPAM != null) {
			for (File child : directoryListingSPAM) {
				if (child.getName().endsWith(".txt")){
					String filename = directorypathSpam + "/" +child.getName();
					String category = "spam";
					String totest = classifier.classify(tokenizer.tokenize(filename));
					if (totest.equals(category)) {
						correct += 1;
					} else {
						incorrect +=1;
					}
					//System.out.println("supposed to be " + "spam" + ": " + totest);
				}
			}
		}
		double percentage = correct/(correct+incorrect);
		double smallpercentage = smallcorrect/(smallcorrect+smallincorrect);
		System.out.println("percentage correct (small): " + smallpercentage);
		System.out.println("percentage correct: " + percentage);
		System.out.println("Improved by " + (percentage-smallpercentage)/smallpercentage + "%.");
	}
	
}
