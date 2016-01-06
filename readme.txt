The zip file contains all classes and files to use our classifier.

To view the Demo, compile and run the DEMOMAIL.java file in src/test

To open the TUI, compile and run the Classify.java file in src/Classifier

The TUI has 5 basic commands, ADDCLASS, SHOWCLASSES, HELP, TRAIN and TEST.

Entering one of these commands will guide you further through the program.
You cannot TRAIN without any classes present.
You cannot TEST before all classes have been TRAINed.

If the user input is wrong, the program will handle the errors and ask for a correct input.

The Programm will “lose” all its “smartness” once it is shut down.

You can test the TUI as follows:
- ADDCLASSES (E.G. “man”, “vrouw”)
- TRAIN both classes the TRAINFILES in BLOGS/MALE/TRAIN and BLOGS/FEMALE/TRAIN
- TEST a file from either BLOGS/FEMALE/TEST or BLOGS/MALE/TEST