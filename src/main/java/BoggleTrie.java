import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Trie with radix R = 26 used to solve Boggle Boards.
 * Valid strings consist ONLY of the uppercase letters A-Z.
 */
public class BoggleTrie implements Iterable<String> {
    private static final int R = 26;
    private final Node root;

    public BoggleTrie() {
        root = new Node();
    }

    /**
     * Adds a whole word to the trie
     */
    public void add(CharSequence word) {
        validateNotNull(word);
        validateWord(word);
        if (word.length() == 0) return;
        int index = toIndex(word.charAt(0));
        root.children[index] = add(word, root.children[index], 1);
    }

    private Node add(CharSequence word, Node node, int pos) {
        // Add new node if not yet created. otherwise reusing existing.
        if (node == null) node = new Node();

        // Base case: end of word. Don't create any new nodes.
        if (pos == word.length()) {
            node.isWord = true;
            return node;
        }

        // Recursive case: More letters to go. Keep creating new nodes.
        int index = toIndex(word.charAt(pos));
        node.children[index] = add(word, node.children[index], pos + 1);
        return node;
    }

    @Override
    public Iterator<String> iterator() {
        return getWords().iterator();
    }

    /**
     * Returns all words in the trie via a backtracking algorithm.
     */
    public List<String> getWords() {
        List<String> words = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < R; i++) {
            getWords(words, current, root.children[i], toChar(i));
        }
        return words;
    }

    private void getWords(List<String> words, StringBuilder current, Node node, char c) {
        // Base case: ignore null links
        if (node == null) return;

        // Append letter to current word
        current.append(c);

        // Add to collection of words if valid word found
        if (node.isWord) words.add(current.toString());

        // Recurse
        for (int i = 0; i < R; i++) {
            getWords(words, current, node.children[i], toChar(i));
        }

        // Backtrack
        current.deleteCharAt(current.length() - 1);
    }

    /**
     * Determines whether or not trie contains a word. Returns false for empty string
     */
    public boolean contains(CharSequence word) {
        validateNotNull(word);
        validateWord(word);

        // Since an empty string is not a valid Boggle word, return false here
        if (word.length() == 0) return false;

        // Traverse down trie to find word
        int index = toIndex(word.charAt(0));
        Node node = getNode(word, root.children[index], 1);
        return node != null && node.isWord;
    }

    /**
     * Returns true if the given word is a prefix of any word in the trie.
     *
     * @param word A word consisting only of the chars A-Z
     * @return True if the word is a prefix
     */
    public boolean isPrefix(CharSequence word) {
        validateNotNull(word);
        validateWord(word);
        if (word.length() == 0) return isPrefix(root);

        // Traverse down trie to find word
        int index = toIndex(word.charAt(0));
        Node node = getNode(word, root.children[index], 1);
        return node != null && isPrefix(node);
    }

    /**
     * Helper method to find a node given a search string. Returns null if the string is not part of the trie
     */
    private Node getNode(CharSequence word, Node node, int pos) {
        // Base case 1: null link found. We did not find the word so return null.
        if (node == null) return null;

        // Base case 2: end-of-word found. Return the node.
        if (word.length() == pos) return node;

        // Recurse
        int index = toIndex(word.charAt(pos));
        return getNode(word, node.children[index], pos + 1);
    }

    /**
     * Returns true if a given node is a prefix of a complete word
     */
    private boolean isPrefix(Node node) {
        for (int i = 0; i < R; i++) {
            if (node.children[i] != null) return true;
        }
        return false;
    }

    /**
     * Converts a character (A-Z) to an integer (0-25)
     */
    private int toIndex(char c) {
        return c - 'A';
    }

    /**
     * Converts an int (0-25) to a char (A-Z)
     */
    private char toChar(int index) {
        return (char) (index + 'A');
    }

    private void validateNotNull(Object arg) {
        if (arg == null) throw new IllegalArgumentException("Null argument");
    }

    private void validateWord(CharSequence word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            validateChar(c);
        }
    }

    private void validateChar(char c) {
        if (c < 'A' || c > 'Z') throw new IllegalArgumentException(String.format(
                "char %s is not between A and Z", c
        ));
    }

    /**
     * Represents a node in the tree
     */
    private static class Node {
        boolean isWord;
        Node[] children;

        private Node() {
            isWord = false;
            children = new Node[R];
        }
    }
}
