/**
 * DictionaryBST class.
 * 
 * DictionaryBST is a 'Binary Search Tree' data structure of dictionary items.
 * 
 * The DictionaryBST class implements a data structure to store, sort, and
 * modify a dictionary of words or phrases and their respective definitions.
 * Each word is stored in an instance of a DictionaryNode, and every node's
 * position follows an alphabetical order, which is maintained throughout
 * every operation performed on the DictionaryBST. 
 * 
 * This class contains multiple instances of public class DictionaryNode:
 * @see DictionaryNode#DictionaryNode(String value)
 * */
public class DictionaryBST{
	
	DictionaryNode root;

   /**
 	* StrBST constructor.
 	* 
 	* Constructs an empty DictionaryBST and sets its root to null.
 	*/
	public DictionaryBST(){

		root = null;
	}

	/**
	 * insert(String w, String d)
	 * 
	 * Adds a value to the DictionaryBST, and maintains the order alphabetically 
	 * by calling a supporting recursive method to traverse through tree, to and
	 * from the root.
	 * 
	 * This method calls the following supportive method:
	 * @see #insertR(DictionaryNode current, String w)
	 * 
	 * @param w Specifies the String word to add.
	 * @param d Specifies the String definition of a word.
	 */
	public void insert(String w, String d){

		root = insertR(root, w, d);
	}

	/**
	 * insertR(DictionaryNode current, String w, String d)
	 * 
	 * Traverses through the DictionaryBST until an appropriate place for the new 
	 * word is found, then shifts existing values accordingly before returning 
	 * to the root of the tree.
	 * 
	 * This is a private recursive method that supports the following method:
	 * @see #insert(String w, String d)
	 * 
	 * @param current Specifies the currently observed DictionaryNode to check.
	 * @param w Specifies the String word to add.
	 * @param d Specifies the String definition of a word.
	 * @return the final DictionaryNode root after traversing back up the tree.
	 */
	private DictionaryNode insertR(DictionaryNode current, String w, String d){

		if(current == null){	
			current = new DictionaryNode(w, d);
		}	

		int check = w.compareToIgnoreCase(current.word);
		if(check < 0){
			current.left = insertR(current.left, w, d);
		}
		else if(check > 0){
			current.right = insertR(current.right, w, d);
		}			
		return current;		
	}

	/**
	 * remove(String w)
	 * 
	 * Removes a word, and thus its definition, from the DictionaryBST, and 
	 * maintains the order alphabetically by calling a supporting recursive 
	 * method to traverse through the tree, to and from the root.
	 * 
	 * This method calls the following supportive method:
	 * @see #removeR(DictionaryNode current, String w)
	 * 
	 * @param w Specifies the String word to remove.
	 */
	public void remove(String w){
		
		root = removeR(root, w);	
	}

	/**
	 * removeR(DictionaryNode current, String w)
	 * 
	 * Removes a value from the BST by comparing the String to remove against the
	 * the String value of the currently observed DictionaryNode, then traverses 
	 * to either the left or right subtree until the current node matches the 
	 * specified word to remove.
	 * 
	 * The int variable 'check' is given by the result of compareToIgnoreCase()
	 * which will be less than 0 if currently observed node's word is in the
	 * left sub-tree and will be greater than 0 if it's in the right sub-tree. 
	 * 
	 * This is a private recursive method that supports the following method:
	 * @see #remove(String w)
	 * 
	 * @param current Specifies the currently observed DictionaryNode to check.
	 * @param w Specifies the String word to remove.
	 * @return the final DictionaryNode root after traversing back up the tree.
	 */
	private DictionaryNode removeR(DictionaryNode current, String w){
		
		if(current == null){
			return null;
		}			
		int check = w.compareToIgnoreCase(current.word);
		if(check < 0){
			current.left = removeR(current.left, w);
		}
		else if(check > 0){			
		 	current.right = removeR(current.right, w);
		}
		else if(check == 0){

			/* If the node has one or no children */
			if(current.left == null){
				return current.right;
			} 
			if(current.right == null){
				return current.left;
			}
			/* If the node has two children */
	        DictionaryNode parent = current;
	        DictionaryNode successor = current.right;
	        while(successor.left != null){
	            parent = successor;
	            successor = successor.left;
	        }
	        current.word = successor.word;
	        current.definition = successor.definition;
	        if(parent == current){
	            current.right = successor.right;
	        }
	        else if(parent != current) {
	        	parent.left = successor.right;
	        }	        
	        return current;
        }
        return current;
	}

	/**
	 * search(String w)
	 * 
	 * Searches for a specified String value in the BST by calling a supporting
	 * recursive method that traverses through the tree to compare the String
	 * words of each DictionaryNode.
	 * 
	 * @param w Specifies the String word to search for.
	 * @return 'true' if the value is found, and 'false' otherwise.
	 */
	public boolean search(String w){

		DictionaryNode current = searchR(root, w);	
		if(current != null){
			return true;
		} 
		return false;
	}

	/**
	 * searchR(String w)
	 * 
	 * Compares the String word of the observed DictionaryNode by using the int
	 * result of compareToIgnoreCase() to determine what subtree to search 
	 * through next.
	 * 
	 * The value of 'check' will be 0 if the observed word is the same as the
	 * word to search for, and then returns that node, or null if not found by
	 * the end of the search. 
	 * 
	 * 
	 * This is a private recursive method that supports the following methods:
	 * @see #search(String w) 
	 * @see #remove(String w)	
	 * 
	 * @param current Specifies the currently observed DictionaryNode to check.
	 * @param w Specifies the String word to search for.
	 * @return the DictionaryNode if the value is found, and null otherwise.
	 */
	private DictionaryNode searchR(DictionaryNode current, String w){

		if(current == null){	
			return null;
		}

		int check = w.compareToIgnoreCase(current.word);
		if(check < 0){
			current = searchR(current.left, w);
		}
		else if(check > 0){
			current = searchR(current.right, w);
		}
		return current;					
	}

	/**
	 * print()
	 * 
	 * Prints the StrBST to the system, where its values are printed in their
	 * respective positions to depict the structure and order of the tree.
	 * 
	 * This method calls the following supportive method:
	 * @see #printDictionaryR(DictionaryNode current) 
	 */
	public void printDictionary(){

		printDictionaryR(root);		
	}
	
	/**
	 * printDictionaryR(DictionaryNode current)
	 * 
	 * Prints each observed DictionaryNode's information as a format to the 
	 * System. Each DictionaryNode is printed following an in-order traversal, 
	 * by recursively calling this method on the left subtree, then the right 
	 * subtree. Returns when there is no DictionaryNode to print.
	 * 
	 * This is a private recursive method that supports the following method:
	 * @see #printDictionary() 	
	 * 
	 * @param current Specifies the currently observed DictionaryNode to print.
	 */
	private void printDictionaryR(DictionaryNode current){

		if(current == null){
			return;
		}
		printDictionaryR(current.left);
		printDictionaryItem(current.word);
		printDictionaryR(current.right);       
	}

	/**
	 * printDictionaryItem(String w)
	 * 
	 * Prints the DictionaryNode that contains the definition of the word passed
	 * in, by first searching the DictionaryBST for the String, then prints the 
	 * word and then its definition, or a message if not found, to the System.
	 * 
	 * This method calls the following supportive method:
	 * @see searchR(String w)
	 * 
	 * @param w Specifies the String word to search for.
	 * */
	public void printDictionaryItem(String w){

		DictionaryNode current = searchR(root, w);
		if(current == null){
			System.out.println("Could not find '" + w + "'");
			return;
		}
		System.out.println();
		System.out.println(current.word);
		System.out.println(current.definition);
	}

	/**
	 * printTree()
	 * 
	 * Prints the words in the structure of a tree to the System, starting at 
	 * the root DictionaryNode of this DictionaryBST.
	 * 
	 * This method calls the following supporting method:
	 * @see TreeFormatter#topDown(Node root)
	 */
	public void printTree(){ 

		TreeFormatter treeform = new TreeFormatter();
		System.out.println(treeform.topDown(root));
	}
}
	




