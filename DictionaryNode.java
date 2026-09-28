/**
 * DictionaryNode class.
 * 
 * DictionaryNode is an object that holds the information about a value in a BST.
 * 
 * The DictionaryNode class implements a DictionaryNode in a 'Binary Search Tree' 
 * by storing its String word and definition, as well as two pointers to its 
 * children, if any, to describe its position in the tree. 
 * 
 * DictionaryNode's children are denoted by references to its 'left' and 'right'
 * nodes. These public member variables act as a link to a DictionaryNode's 
 * subtrees, and are initialised as null until its position is defined.
 * 
 * */
public class DictionaryNode{

	public String word;
	public String definition;
	public DictionaryNode left;
	public DictionaryNode right;

	/**
	 * DictionaryNode constructor.
	 * 
	 * Constructs a DictionaryNode object, sets its word and definition to the
	 * String parameters passed in, then sets its 'left' and 'right' subtree 
	 * links to null.
	 * 
	 * @param w Specificies the word or phrase of a DictionaryNode as a String.
	 * @param d Specifies the definition of a word or phrase as a String.
	 * */
	public DictionaryNode(String w, String d){

		this.word = w;
		this.definition = d;
		this.left = null;
		this.right = null;
	}


}
