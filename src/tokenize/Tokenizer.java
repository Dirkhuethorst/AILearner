package tokenize;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;;
public class Tokenizer {

	public static void main(String[] args) {
		tokenize("EMAILS/3-1msg1.txt");

	}
	public static ArrayList<String> tokenize(String pathname){
		String file = "";
		try {
			file = readFile(pathname);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Split the string at a space and put the words in an arraylist
		// remove all punctuation marks
		String[] temp = file.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
		ArrayList<String> res = new ArrayList<String>();
		for ( String word : temp) {
			word = word.toLowerCase();
			res.add(word);
		}
		System.out.println(res.toString());
		return res;
		
	}
	private static String readFile(String pathname) throws IOException {
	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
}