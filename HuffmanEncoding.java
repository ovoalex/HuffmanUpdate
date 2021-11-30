import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoding {

    public static void main(String[] args) {
        String test = "Huffman Decoding";
        
        Map<Character, Integer> frequency = getFrequencies(test);
        PriorityQueue<Node> pq = makePriorityQueue(frequency);
        int n = pq.size();
        Node root = makeTree(pq, n);
        Map<Character, String> prefixCodes = new HashMap<>();
		getPrefixCodes(prefixCodes, root, "");
        String encoded = encode(test, prefixCodes);

        System.out.println("\nOriginal: " + test);
        System.out.println("Symbols and Frequencies: " + frequency);
		System.out.println("Prefix Codes: " + prefixCodes);
		System.out.println("Encoded: " + encoded + "\n");
    }

    private static String encode(String test, Map<Character, String> prefixCodes) {
		String encoded = "";

		for(int i = 0; i < test.length(); i++) {	// iterate through original string
			encoded += prefixCodes.get(test.charAt(i));	// add the prefix code of each character to the encoded string
		}

		return encoded;
	}

    private static void getPrefixCodes(Map<Character, String> prefixCodes, Node root, String prefix) {
		if(root != null) {	// check if root is null
			if(root.left == null && root.right == null) {	// check if it node is a leaf
				prefixCodes.put(root.symbol, prefix);		// if it is a leaf add it the prefix to the hashmap
			}
			else {
				prefix += '0';		// add a 0 to the prefix
				getPrefixCodes(prefixCodes, root.left, prefix);		// call function recursively to check the left child
				prefix = prefix.substring(0, prefix.length()-1);

				prefix += '1';		// add a 1 to the prefix
				getPrefixCodes(prefixCodes, root.right, prefix);	// call function recursively to check the right child
				prefix = prefix.substring(0, prefix.length()-1);
			}
		}

	}

    private static Node makeTree(PriorityQueue<Node> pq, int n) {
		Node r = new Node();

		for(int i = 0; i < n-1; i++) {
			Node p = pq.poll();		// get the first node in the queue
			Node q = pq.poll();		// get the second node in the queue
			r = new Node();			// create a new node and store the p as left and q as right
			r.left = p;
			r.right = q;
			r.frequency = p.frequency + q.frequency;	// frequency is the sum of p and q
			pq.add(r);		// add back into the queue
		}

		pq.poll();
		return r;
	}

    private static PriorityQueue<Node> makePriorityQueue(Map<Character, Integer> hm) {
		PriorityQueue<Node> pq = new PriorityQueue<>();

		for(char key : hm.keySet()) {
			Node node = new Node();		// create a new node
			node.symbol = key;			// store the key from hashmap in the symbol of node
			node.frequency = hm.get(key);	// store the value of the key in the frequency of the node
			node.left = null;	
			node.right = null;
			pq.add(node);		// add the node to the priority queue
		}

        return pq;
    }

    private static Map<Character, Integer> getFrequencies(String test) {
		Map<Character, Integer> hm = new HashMap<>();

		for(int i = 0; i < test.length(); i++) {
			if (!hm.containsKey(test.charAt(i))) { 	// check if character is in the hashmap
				hm.put(test.charAt(i), 1);			// store it in hashmap
			}
            else {
                hm.put(test.charAt(i), hm.get(test.charAt(i)) + 1);	// increase the value if already in hashmap
            }
		}
		
		return hm;
	}

}
