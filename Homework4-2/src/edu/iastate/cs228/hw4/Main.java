package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author issmale bekri
 *
 */

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException{
		
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please enter the filename to decode: ");
		String filename = scnr.nextLine();
		scnr.close();
		File file = new File(filename);
		
		String tree = "";
		String message = "";
		
		try {
			int count = 0;
			Scanner scnr1 = new Scanner(file);
			while(scnr1.hasNextLine()) {
				count++;
				scnr1.nextLine();
			}
			scnr1.close();
			
			Scanner scnr2 = new Scanner(file);
			
			int i = 0;
			while (i < count - 1) {
			    tree += scnr2.nextLine() + "\n";
			    i++;
			}
			if (!tree.isEmpty() && tree.charAt(tree.length() - 1) == '\n') {
			    tree = tree.substring(0, tree.length() - 1);
			}
			
			message = scnr2.nextLine();
			scnr2.close();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Please type a correct filename");
		}
		
		MsgTree msgTree = new MsgTree(tree);
		System.out.println("Character" + "\t" + "Code");
		System.out.println("-------------------------");
		msgTree.printCodes(msgTree, "");
		System.out.println();
		msgTree.decode(msgTree, message);
		MsgTree.printStats();
	}

}
