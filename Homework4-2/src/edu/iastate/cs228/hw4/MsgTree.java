package edu.iastate.cs228.hw4;

/**
 * Represents a node in a binary tree used for message encoding and decoding.
 * Each node contains a payload character and references to its left and right children.
 * The class also provides methods for printing character codes and decoding messages.
 * 
 * @author issmale bekri
 */

public class MsgTree {
	
	/**
     * The payload character stored in this tree node.
     */
	public char payloadChar;
	
	/**
     * Reference to the left child of this tree node.
     */
	public MsgTree left;
	
	/**
     * Reference to the right child of this tree node.
     */
	public MsgTree right;
	
	/**
     * Total number of characters encountered during encoding.
     */
	private static int totalChar;
	
	/**
     * Total number of bits used in the encoding process.
     */
	private static int totalBits;
	
	/**
     * Total number of characters in the message being decoded.
     */
	private static int characters;
	
	/**
	 * Total number of bits used in the compressed message.
	 */
	private static int compressedBits;
	
	/**
	 * The default number of bits required to represent an uncompressed character (16 bits).
	 */
	private static double uncompressedCharBits  = 16.0;
	
	/**
     * A static index used for traversing the tree string during tree construction.
     */
	private static int staticCharIdx = 0;
	


	/**
	 * Constructs a MsgTree by building a tree from the given encoding string.
	 * The encoding string represents the structure of the tree, where '^' indicates
	 * a null child.
	 * 
	 * @param encodingString The string representing the structure of the tree.
	 */
	public MsgTree(String encodingString){
		
		//initialize the payloadChar
		payloadChar = encodingString.charAt(staticCharIdx);
		staticCharIdx++;
		
		//constructing the left child
		left = new MsgTree(encodingString.charAt(staticCharIdx));
		if (left.payloadChar == '^') {
			left = new MsgTree(encodingString);
		}
		staticCharIdx++;
		
		//constructing the right child
		right = new MsgTree(encodingString.charAt(staticCharIdx));
		if (right.payloadChar == '^') {
			right = new MsgTree(encodingString);
		}
	}
	
	/**
	 * Constructs a MsgTree with a single node containing the specified payload character.
	 * The node has null left and right children.
	 * 
	 * @param payloadChar The character to be stored in the payload of the node.
	 */
	public MsgTree(char payloadChar){
		
		this.payloadChar = payloadChar;
		left = null;
		right = null;
		
	}
	
	/**
	 * Recursively prints characters and their binary codes in the message tree.
	 * it updates the total character count and total bit count for statistical purposes.
	 * 
	 * @param root The root of the message tree.
	 * @param code The binary code representing the path to the current node in the tree.
	 */
	public static void printCodes(MsgTree root, String code){
		
		//Base case
		if(root == null) {
			return;
		}
		
		if(root.payloadChar == '\n') {
			System.out.println("\\n\t\t"+ code);
			totalChar++;
			totalBits += code.length();
		}
		
		else if(root.payloadChar != '^') {
			System.out.println(root.payloadChar + "\t\t" + code); 
			totalChar++;
			totalBits += code.length();
		}
		
		//recursively print the code for the left and right children
		if(root.left != null) {
			printCodes(root.left,  code + 0);
		}
		if(root.right != null) {
			printCodes(root.right,  code + 1);
		}
		
	}
	
	/**
	 * Decodes a message using the provided message tree and binary-encoded message.
	 * Prints the decoded characters to the console and updates character count for statistics.
	 * 
	 * @param codes The message tree used for decoding.
	 * @param msg The binary-encoded message to be decoded.
	 */
	public void decode(MsgTree codes, String msg) {
		MsgTree codeTree = codes;
		int i = 0;
		while(i < msg.length()) {
			//if the bit is 0 go left node
			if(msg.charAt(i) == '0') 
			   {
				   if(codeTree.left != null) 
					   codeTree = codeTree.left;
			   }
			   //if the bit is 1 go right node
			   else if (msg.charAt(i) == '1') 
			   {
				   if(codeTree.right != null) 
					   codeTree = codeTree.right;
			   }
			i++;
			//if a character print the character
			if(codeTree.payloadChar != '^') 
			   {
				   System.out.print(codeTree.payloadChar);
				   characters++;
				   codeTree = codes;  // reset to start the tree again for the next character
			   }
		}
		compressedBits = msg.length();
	}
	
	/**
	 * Prints statistics related to the encoding and decoding process.
	 * Displays the average bits per character, total character count, and space savings percentage.
	 */
	public static void printStats()
	   {
		   System.out.println();
		   System.out.println("STATISTICS:");
		   //calculate the average bits
		   double avgBitsPerChar = ((double) compressedBits / characters);
		   System.out.printf("Avg bits/char:\t\t%.1f%n", avgBitsPerChar); 
		   //print the total character
		   System.out.println("Total Character:" + "\t" + characters);
		   //calculate the space saving
		   double spaceSaving = Math.round((1.0 - compressedBits /(uncompressedCharBits * characters)) * 100);
		   System.out.println("Space savings:" + "\t\t" +  spaceSaving + "%");
	   }

}
