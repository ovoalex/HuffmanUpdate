import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class HuffmanCode {

    private static Scanner kb = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        char option = getOption();
		PriorityQueue<Node> pq;
		Node root;
		int n;

		switch(option) {
			case'a':
				String test = getString();
		    	Map<Character, Integer> frequency = getFrequency(test);
		    	pq = makePriorityQueue(frequency);
		   		n = pq.size();
            	root = makeTree(pq, n);
		    	Map<Character, String> prefixCodes = new HashMap<>();
		    	getPrefixCodes(prefixCodes, root, "");
		    	String encoded = encode(test, prefixCodes);
            	System.out.println("\nSymbols and Frequencies: " + frequency);
				System.out.println("Prefix Codes: " + prefixCodes);
		    	System.out.println("Encoded: " + encoded);
				break;
			case'b':
				String code = getCode();
            	pq = getPriorityQueue();
            	n = pq.size();
            	root = makeTree(pq, n);
            	String decoded = decode(code, root);
            	System.out.println("\nDecoded: " + decoded);
				break;
		}
        
    }

	private static PriorityQueue<Node> getPriorityQueue() throws IOException {
        PriorityQueue<Node> pq = new PriorityQueue<>();
		File file = new File("input.txt");
		Scanner inputFile = new Scanner(file);

		String[] symbols = inputFile.nextLine().split(",");
		String[] frequencies = inputFile.nextLine().split(",");
		int n = symbols.length;

		for(int i = 0; i < n; i++) {
			Node node = new Node();
			node.symbol = symbols[i].charAt(0);
			node.frequency = Integer.parseInt(frequencies[i]);
			node.left = null;
			node.right = null;
			pq.add(node);
		}

		inputFile.close();

        return pq;
    }

    private static String getCode() {
        System.out.print("Enter encoded binary string: ");
        return kb.nextLine();
    }

    private static char getOption() {
        String prompt = "Huffman Coding\n" +
                        "\ta) Encode\n" +
                        "\tb) Decode\n" +
                        "Enter option: ";
        char option;

        do {
            System.out.print(prompt);
            option = kb.nextLine().toLowerCase().charAt(0);
            if(option != 'a' && option != 'b') {
                System.out.println("\nIncorrect choice.\n");
            }
        } while(option != 'a' && option != 'b');

        return option;
    }

    static String decode(String encoded, Node root) {
		String decoded = "";
		Node temp = root;

		for(int i = 0; i < encoded.length(); i++) {
			int binary = Integer.parseInt(String.valueOf(encoded.charAt(i)));

			if(binary == 0) {
				temp = temp.left;
				if(temp.left == null && temp.right == null) {
					decoded += temp.symbol;
					temp = root;
				}
			}
			if(binary == 1) {
				temp = temp.right;
				if(temp.left == null && temp.right == null) {
					decoded += temp.symbol;
					temp = root;
				}
			}
		}

		return decoded;
	}

	private static String encode(String test, Map<Character, String> prefixCodes) {
		String encoded = "";

		for(int i = 0; i < test.length(); i++) {
			encoded += prefixCodes.get(test.charAt(i));
		}

		return encoded;
		break;
	}

	private static void getPrefixCodes(Map<Character, String> prefixCodes, Node root, String prefix) {
		if(root != null) {
			if(root.left == null && root.right == null) {
				prefixCodes.put(root.symbol, prefix);
			}
			else {
				prefix += '0';
				getPrefixCodes(prefixCodes, root.left, prefix);
				prefix = prefix.substring(0, prefix.length()-1);

				prefix += '1';
				getPrefixCodes(prefixCodes, root.right, prefix);
				prefix = prefix.substring(0, prefix.length()-1);
			}
		}

	}

	static Node makeTree(PriorityQueue<Node> pq, int n) {
		Node r = new Node();

		for(int i = 0; i < n-1; i++) {
			Node p = pq.poll();
			Node q = pq.poll();
			r = new Node();
			r.left = p;
			r.right = q;
			r.frequency = p.frequency + q.frequency;
			pq.add(r);
		}

		pq.poll();
		return r;
	}

	private static PriorityQueue<Node> makePriorityQueue(Map<Character, Integer> frequency) {
		PriorityQueue<Node> pq = new PriorityQueue<>();

		for(char key : frequency.keySet()) {
			Node node = new Node();
			node.symbol = key;
			node.frequency = frequency.get(key);
			node.left = null;
			node.right = null;
			pq.add(node);
		}

        return pq;
    }

	private static Map<Character, Integer> getFrequency(String test) throws IOException {
		Map<Character, Integer> frequency = new HashMap<>();

		for(int i = 0; i < test.length(); i++) {
			if (!frequency.containsKey(test.charAt(i))) {
				frequency.put(test.charAt(i), 1);
			}
            else {
                frequency.put(test.charAt(i), frequency.get(test.charAt(i)) + 1);
            }
		}
		String symbols = "";
		String frequencies = "";

		for(char key : frequency.keySet()) {
			symbols += key + ",";
			frequencies += frequency.get(key) + ",";
		}

		FileWriter fw = new FileWriter("input.txt");
		PrintWriter pw = new PrintWriter(fw);
		pw.println(symbols.substring(0, symbols.length()-1));
		pw.println(frequencies.substring(0, frequencies.length()-1));
		pw.close();
		return frequency;
	}

	private static String getString() {		
		System.out.print("Enter text: ");
		return kb.nextLine();
	}

}

class Node implements Comparable<Node> {
    int frequency;
    char symbol;
    Node left;
    Node right;

	public int compareTo(Node node) {
		return frequency - node.frequency;
	}

}
