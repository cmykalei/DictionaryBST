import java.util.Collections;
import java.util.List;
import java.util.ArrayList; 

/**
 * TreeFormatter class.
 * 
 * Tree formatter class is a supporting printer class to print a BST's nodes in a tree
 * structure. Holds private methods for calculations, and a public static method to be
 * used externally for generating a String output in the form of a tree.
 * 
 * This class is written by:	
 * @author trincot
 * @see <a href="https://stackoverflow.com/questions/4965335/>How to BST Printer Classes</a> 
 */
public class TreeFormatter{

    int padding = 2; // minimum number of horizontal spaces between two node data

    private int indent(List<String> lines, int margin) {
        // If negative, prefix all lines with spaces and return 0
        if (margin >= 0) return margin;
        String spaces = " ".repeat(-margin);
        int i = 0;
        for (var line : lines) {
            lines.set(i++, spaces + line);
        }
        return 0;
    }
    
    private List<String> merge(List<String> left, List<String> right) {
        // Merge two arrays, where the right strings are indented so there is no overlap
        int minSize = Math.min(left.size(), right.size());
        int offset = 0;
        for (int i = 0; i < minSize; i++) {
            offset = Math.max(offset, left.get(i).length() + padding - right.get(i).replaceAll("\\S.*", "").length());
        }
        indent(right, -indent(left, offset));
        for (int i = 0; i < minSize; i++) {
            left.set(i, left.get(i) + right.get(i).substring(left.get(i).length()));
        }
        if (right.size() > minSize) {
            left.addAll(right.subList(minSize, right.size()));
        }
        return left;
    }

    private List<String> buildLines(DictionaryNode node) {
        if (node == null) return new ArrayList<>();
        List<String> lines = merge(buildLines(node.left), buildLines(node.right));
        int half = String.valueOf(node.word).length() / 2;
        int i = half;
        if (lines.size() > 0) {
            String line;
            i = lines.get(0).indexOf("*"); // Find index of first subtree
            if (node.right == null) {
                line = " ".repeat(i) + "┌─┘";
                i += 2;
            } else if (node.left == null) {
                line = " ".repeat(i = indent(lines, i - 2)) + "└─┐";
            } else {
                int dist = lines.get(0).length() - 1 - i; // Find distance between subtree roots
                line = String.format("%s┌%s┴%s┐", " ".repeat(i), "─".repeat(dist / 2 - 1), "─".repeat((dist - 1) / 2));
                i += dist / 2;
            }
            lines.set(0, line);
        }
        lines.add(0, " ".repeat(indent(lines, i - half)) + node.word);
        lines.add(0, " ".repeat(i + Math.max(0, half - i)) + "*"); // Add a marker for caller
        return lines;
    }
    
    /**
     * topDown(DictionaryNode root)
     * 
     * Formats a String in the form of a BST using the calculations
     * from the private class methods.
     * 
     * @see #buildLines(DictionaryNode root)
     * @return the String BST with formatted lines.
     * */
    public String topDown(DictionaryNode root) {
        List<String> lines = buildLines(root);
        return String.join("\n", lines.subList(1, lines.size()));
    }
}