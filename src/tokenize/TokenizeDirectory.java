package tokenize;

import java.io.File;
import java.util.List;

import test.Category;

public class TokenizeDirectory {
	public static void main(String[] args) {
		DirectoryTokenizer("EMAILS", null);

	}
	public static void DirectoryTokenizer(String directoryPath, List<Object> categories) {
		// TODO maybe change <Object> to not yet implemented Category Object
		File dir = new File(directoryPath);
		File[] directoryListing = dir.listFiles();
		if (directoryListing != null) {
			for (File child : directoryListing) {
				String category = Category.getCategoryWithAbbreviation(child.getName());
				String path = directoryPath + "/"+ child.getName();
				//Vocabulary.add(category,Tokenizer.tokenize(path); )
				//TODO add to existing/new map
			}
		} else {
			// TODO implement else
		}
	}
}
