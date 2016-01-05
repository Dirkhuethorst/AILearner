package classifierTUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Classifier.ClassifierClass;
import Classifier.MNBTClassifer;
import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;


public class TUI {
	/**
	 * Prints a message on standard output.
	 * @param msg Message that is printed.
	 */
	public void print(String msg){
		System.out.println(msg);
	}
	
	/**
	 * Prints a message on standard output in red.
	 * @param msg That is printed in red.
	 */
	public void errorPrint(String msg){
		System.err.println(msg);
	}
	
	public void handleInput() {
		String input = readString("");
		if (input.equals("HELP")) {
			print("TRAIN");
		} else if (input.equals("HINT")) {
			

		} else if (input.equals("")) {
			print("");
		} else {
			
		}
	}
	
	public String readString(String tekst) {
		print(tekst);
		String antw = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			antw = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (antw == null) ? "" : antw;
	}
}
