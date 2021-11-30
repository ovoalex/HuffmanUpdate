import java.util.PriorityQueue;

public class HuffmanDecoding {
    
    public static void main(String[] args) {
        String code = "010100110000001000110110111001111001001111110100101101010100";
        char[] symbols = {' ', 'a', 'c', 'D', 'd', 'e', 'f', 'g', 'H', 'i', 'm', 'n', 'o', 'u'};
        int[] frequencies = {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1};
        int n = symbols.length;

        PriorityQueue<Node> pq = makePriorityQueue(symbols, frequencies, n);
        Node root = makeTree(pq, n);
        String decoded = decode(code, root);
		
		System.out.println("\nEncoded: " + code);
        System.out.println("Decoded: " + decoded + "\n");
    }

    private static String decode(String encoded, Node root) {
		String decoded = "";
		Node temp = root;

		for(int i = 0; i < encoded.length(); i++) {		// iterate through the encoded string
			int binary = Integer.parseInt(String.valueOf(encoded.charAt(i)));	// read a character from the string

			if(binary == 0) {	// check if the character is 0
				temp = temp.left;	// move into the left child of the node
				if(temp.left == null && temp.right == null) {	// check if node is a leaf
					decoded += temp.symbol;		// add the symbol of the leaf node
					temp = root;	// go back to the root of the tree
				}
			}
			if(binary == 1) {	// check if the character is 1
				temp = temp.right;	// move into the right child of the node
				if(temp.left == null && temp.right == null) {	// check if node is a leaf
					decoded += temp.symbol;		// add the symbol of the leaf node
					temp = root;	// go back to the root of the tree
				}
			}
		}

		return decoded;
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

    private static PriorityQueue<Node> makePriorityQueue(char[] symbols, int[] frequencies, int n) {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for(int i = 0; i < n; i++) {
            Node node = new Node();		// create a new node
            node.symbol = symbols[i];	// store the symbol at i in node
            node.frequency = frequencies[i];	// store the frequency at i in node
            node.left = null;
            node.right = null;
            pq.add(node);		// add the node to the priority queue
        }

        return pq;
    }

}
