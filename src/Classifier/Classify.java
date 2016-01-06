package Classifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import tokenize.TokenizeDirectory;
import tokenize.Tokenizer;




public class Classify {

	private Scanner scanner = new Scanner(System.in);
	private boolean hasclasses = false;
	private boolean legitnumber = false;
	private int numberclasses;
	private MNBTClassifer classifier;
	private Map<String, String> testmap = new HashMap<String, String>();
	private Tokenizer tokenizer = new Tokenizer();
	private TokenizeDirectory dir = new TokenizeDirectory();
	private ClassifierClass[] testarray;
	private ClassifierClass cc;
	private ClassifierClass tempclass;
	private String tempclassname;
	private String directorypath;
	public String[] classnames;

	private void run(){

		print("Welcom to our Multinominal Naive Bayesian Text Classifier.");
		print("Please choose between: HELP, ADDCLASS, TRAIN, TEST, SHOWCLASSES");
		while (scanner.hasNext()) {
			String keuze = scanner.next();
			//------------------------TRAIN---------------------------------------------------------
			if (keuze.equals("TRAIN") || keuze.equals("train")) {
				if (hasclasses){
					print("Which class do you want to train?");
					scanner.nextLine();
					boolean notsuccesful = scanner.hasNext();
					while (notsuccesful){
						keuze = scanner.nextLine();
						tempclassname = keuze;					
						boolean correctclass = false;
						while (!correctclass){
							for(ClassifierClass x : testarray){
								if (x.getname().equals(tempclassname)){
									cc = x;	
									correctclass = true;
									break;
								}


							}
							if(!correctclass){
								print("Class does not exist. Please give another one.");
								tempclassname = scanner.nextLine();
							}
						}


						boolean correctdir = false;
						while (!correctdir){

							print("please give the folder name where your training files are located");
							keuze = scanner.nextLine();
							String filedirectory = keuze;
							try {
								dir.DirectoryTokenizer2(filedirectory, cc);
								classifier.updatevocsize();
								cc = null;
								correctdir = true;
								notsuccesful = false;
								print("What do you want to do? (ADDCLASS, TRAIN, TEST)");
								break;
							} catch (Exception e) {
								errorPrint("Directory does not contain files!");

							}

						}

					}
				}

				else{
					print("please define some classes first (type ADDCLASS)");
				}

			}
			//--------------------------ADDCLASS------------------------------------------
			else if (keuze.equals("ADDCLASS") || keuze.equals("addclass")){
				print("Please type a number and hit enter");
				scanner.nextLine();
				while (scanner.hasNext()) {
					keuze = scanner.nextLine();
					if(!legitnumber){
						try{
							numberclasses = Integer.parseInt(keuze);
							legitnumber = true;							
							print("Please type " +numberclasses +" classnames seperated by spaces");


						} 
						catch (NumberFormatException e){
							errorPrint("Not a valid number. Please give an integer!");
							continue;
						}
					}
					else{
						String[] classarray = keuze.split(" ");
						if(classarray.length == numberclasses){
							classnames = classarray;

							for(int x=0; x < classarray.length; x++){								
								testmap.put(classarray[x], "1"); //1 is given as identifier, but identifiers were only used for the automatic loading of files.
							}

							if(!hasclasses){
								classifier = new MNBTClassifer(testmap);
								testmap.clear();
								hasclasses = true;
								legitnumber = false;
								try {
									TimeUnit.SECONDS.sleep(2);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}
								testarray = classifier.getClasses();
								//print("test namen:" +testarray[0].getname() + testarray[1].getname() +testarray[2].getname());
								//print("test oké");
								print("What do you want to do? (ADDCLASS, TRAIN, TEST)");
								break;
							}
							else{
								try {
									classifier.addclass(testmap);
									testarray = classifier.getClasses();
									testmap.clear();
									legitnumber = false;
									print("What do you want to do? (ADDCLASS, TRAIN, TEST)");

									break;
								} catch (Exception e1) {
									
									errorPrint("Class already exist , please give a new one");
									testmap.clear();
								}

								try {
									TimeUnit.SECONDS.sleep(2);
								} catch (InterruptedException e) {

									e.printStackTrace();
								}


							}					
						}

						else{
							print("Please type " +numberclasses +" classnames seperated by spaces ");
						}

					}					
				}
			}

			//-----------------------------------TEST-------------------------------
			else if (keuze.equals("TEST")|| keuze.equals("test") || keuze.equals("Test")){
				if(hasclasses){
					if(classifier.allclassestrained()){
						print("Please give the name of the folder where your test files are located");
						scanner.nextLine();

						boolean cont = scanner.hasNext();

						while(cont){
							directorypath = scanner.nextLine();

							if (directorypath.endsWith(".txt")){

								ArrayList<String> tokenized = tokenizer.tokenize(directorypath);
								if (tokenized != null){
									String answer = classifier.classify(tokenizer.tokenize(directorypath));
									print("This file is classified as: " +answer);
									print("Is this correct? Yes / No");
									keuze = scanner.nextLine();
									if (keuze.equals("yes") || keuze.equals("Yes") || keuze.equals("YES")){
										for(ClassifierClass x : testarray){
											String testclass = x.getname();
											if(testclass.equals(answer)){
												dir.addtoClass(directorypath, x);
												classifier.updatevocsize();
												print("What do you want to do now? (ADDCLASS, TRAIN, TEST, SHOWCLASSES)");
												cont = false;
												break;
											}


										}
									}
									else if (keuze.equals("no") || keuze.equals("No") || keuze.equals("NO")){
										System.out.println("What is the correct classs?");
										boolean correct = true;
										while (correct){
											String correctclass = scanner.nextLine();
											boolean classexist = false;

											for(ClassifierClass x : testarray){
												String testclass = x.getname();

												if(testclass.equals(correctclass)){
													classexist = true;
													tempclass = x;
												}
											}
											if(classexist){
												dir.addtoClass(directorypath, tempclass);
												classifier.updatevocsize();
												print("What do you want to do? (ADDCLASS, TRAIN, TEST, SHOWCLASSES)");
												correct = false;
												cont = false;
												break;
											}
											else{
												errorPrint("The given class does not exist");
												print("What class does this file belong to?");

											}




										}
									}
									else{
										errorPrint("please answer with yes or no!");
										directorypath = null;
									}		
								}
								else{
									errorPrint("File not found");
								}
							}


							else{
								errorPrint("Not a correct filename! Extension must be of type .txt");
							}

						}
					}
					else{
						print("Not all classes are trained.");
					}
				}

				else{
					print("please define some classes first (type ADDCLASS)");
				}
			}

			//Geen correct commando
			else if (keuze.equals("SHOWCLASSES" ) || keuze.equals("showclasses")){
				if(testarray != null){
					for(int x = 0; x < testarray.length ; x++){
						print(testarray[x].getname());
					}
				}
				else{
					errorPrint("There are no classes yet.");
				}


			}
			else if (keuze.equals("HELP") || keuze.equals("help") || keuze.equals("Help")){
				print("");
				//TODO
			}

			else{
				print("Please choose between: HELP, ADDCLASS, TRAIN, TEST, SHOWCLASSES");
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
