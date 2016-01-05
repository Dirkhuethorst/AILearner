package tokenize;

import java.io.File;
import java.util.List;

import Classifier.ClassifierClass;

public class TokenizeDirectory {
	
	public void DirectoryTokenizer(String directoryPath, ClassifierClass[] classes) {
		File dir = new File(directoryPath);
		File[] directoryListing = dir.listFiles();
		Tokenizer tokenize = new Tokenizer();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				
				String filename = child.getPath();
				String category = getCategory(classes, filename);
				
				String path = directoryPath + "/"+ child.getName();
				List<String> tokenized = tokenize.tokenize(path);
				for (ClassifierClass baby : classes) {
					if(baby.getname().equals(category)) {
						baby.updatewords(tokenized);
						baby.increaseFileCount();
					}
				} 
			}
		} 		
	}
	
	public void DirectoryTokenizer2(String directoryPath, ClassifierClass c_class) {
		File dir = new File(directoryPath);
		File[] directoryListing = dir.listFiles();
		Tokenizer tokenizer = new Tokenizer();
		if (directoryListing != null) {
			for (File child : directoryListing) {				
				
				List<String> tokenized = tokenizer.tokenize(child.getPath());
								
				c_class.updatewords(tokenized);
				c_class.increaseFileCount();
			}	
		}			
	}
	
	
	public String getCategory(ClassifierClass[] classes, String filename){
		for (ClassifierClass child : classes){
			if (filename.contains(child.getIdentifer())){
				return child.getname();
			}
		}
		return null;
	}
}
