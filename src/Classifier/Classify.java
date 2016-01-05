package Classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;




public class Classify {

	private Scanner scanner = new Scanner(System.in);
	private boolean trained = false;
	private boolean hasclasses = false;
	private boolean legitnumber = false;
	private boolean finished = false;
	private int numberclasses;
	private MNBTClassifer classifier;
	private Map<String, String> array = new HashMap<String, String>();
	private Tokenizer tokenizer = new Tokenizer();
	private TokenizeDirectory dir = new TokenizeDirectory();
	private ClassifierClass[] testarray;
	private ClassifierClass cc;
	private String tempclassname;
	private String directorypath;
	
	private void run(){
		
		print("Welcom to our Multinominal Naive Bayesian Text Classifier.");
		print("Please choose between: HELP, ADDCLASS, TRAIN, TEST");
		while (scanner.hasNext()) {
			String keuze = scanner.next();
			//------------------------TRAIN---------------------------------------------------------
			if (keuze.equals("TRAIN") || keuze.equals("train")) {
				if (hasclasses){
					print("Which class do you want to train?");
					scanner.nextLine();
					while (scanner.hasNext()){
						keuze = scanner.nextLine();
						tempclassname = keuze;
						for(ClassifierClass x : testarray){
							if (x.getname().equals(tempclassname)){
								cc = x;	
							}
							
							
						}
						
						if (cc != null){
							print("please give the folder name where your training files are located");
							keuze = scanner.nextLine();
							String filedirectory = keuze;
							print(filedirectory);
							dir.DirectoryTokenizer2(filedirectory, cc);
							classifier.updatevocsize();
							cc = null;
							break;
						}
						else{
							print("The given class does not exist!");
						}
					}
				}
				else{
					print("please define some classes first (type ADDCLASSS)");
				}
			
			}
			//--------------------------ADDCLASS------------------------------------------
			else if (keuze.equals("ADDCLASS")){
				print("Please type a number and hit enter");
				scanner.nextLine();
				while (scanner.hasNext()) {
					keuze = scanner.nextLine();
					if(!legitnumber){
						try{
							numberclasses = Integer.parseInt(keuze);
							legitnumber = true;
							String numberclasses = keuze;
							print("Please type " +numberclasses +" classnames seperated by spaces");
							
							
						} 
						catch (NumberFormatException e){
							print("please type a number and hit enter!!");
							continue;
						}
					}
					else{
						String[] classarray = keuze.split(" ");
						if(classarray.length == numberclasses){
							
							for(int x=0; x < classarray.length; x++){
								Integer idint = x+1;
								print("test id: " +idint.toString());
								array.put(classarray[x], idint.toString());
								
								
							}
							if(!hasclasses){
								classifier = new MNBTClassifer(array);
								hasclasses = true;
								legitnumber = false;
								try {
									TimeUnit.SECONDS.sleep(2);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								testarray = classifier.getClasses();
								//print("test namen:" +testarray[0].getname() + testarray[1].getname() +testarray[2].getname());
								//print("test oké");
								break;
							}
							else{
								classifier.addclass(array);
								try {
									TimeUnit.SECONDS.sleep(2);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								testarray = classifier.getClasses();
								break;
								
							}					
						}
						
						else{
							print("Please type " +numberclasses +" classnames seperated by spaces");
						}
						
					}					
				}
			}
			
			//-----------------------------------TEST-------------------------------
			else if (keuze.equals("TEST")){
				if(hasclasses){
					print("Please give the name of the folder where your test files are located");
					scanner.nextLine();
					while(scanner.hasNext()){
						directorypath = scanner.nextLine();
						if (directorypath.endsWith(".txt")){
							String answer = classifier.classify(tokenizer.tokenize(directorypath));
							print("This file is classified as: " +answer);
							print("Is this correct? Yes / No");
							keuze = scanner.nextLine();
							if (keuze.equals("yes") || keuze.equals("Yes") || keuze.equals("YES")){
								
							}
							else if (keuze.equals("no") || keuze.equals("No") || keuze.equals("NO")){
							}
							else{
								errorPrint("please answer with yes or no!");
								directorypath = null;
							}		
						}
						else{
							print("Not a correct filename!");
						}
										
					}
				}
						
				else{
					print("please define some classes first (type ADDCLASS)");
				}
			}
			
			//Geen correct commando
			else{
				print("Please choose between: HELP, ADDCLASS, TRAIN, TEST");
			}
			
			
		}
	}
	public static void main(String[] args) {
		Classify classify = new Classify();
		classify.run();
	}
	
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
	**/
	public void errorPrint(String msg){
		System.err.println(msg);
	}

/*
	
	
	
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
*/
}
