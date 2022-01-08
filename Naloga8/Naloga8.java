import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Naloga8 {

    static int count;

    public static void main(String[] args) throws IOException {

        String line;
        int n;
        BufferedReader br;

        br = new BufferedReader(new FileReader(args[0]));
        n = Integer.parseInt(br.readLine());

        HashMap<Integer, Node> nodes = new HashMap<>();

        for (int i = 0; i < n; i++) {

            line = br.readLine();

            nodes.put(Integer.parseInt(line.split(",")[0]), new Node(Integer.parseInt(line.split(",")[0]),
                    Integer.parseInt(line.split(",")[1]),
                    Integer.parseInt(line.split(",")[2]),
                    Integer.parseInt(line.split(",")[3])));

        }

        br.close();

        Node rootNode = findRootNode(nodes);

        BinaryTree tree = new BinaryTree(rootNode, nodes);
        Writer out = new FileWriter(args[1]);

        out.write(tree.printTree());
        out.close();

    }

    static Node findRootNode(HashMap<Integer, Node> nodes) {

        Set<Integer> p_id = new TreeSet<Integer>();
        Set<Integer> all_id = new TreeSet<Integer>();

        for (Node node : nodes.values()) {
            if (node.id_l != -1) {
                p_id.add(node.id_l);
            }
            if (node.id_r != -1) {
                p_id.add(node.id_r);
            }
            all_id.add(node.id);

        }
        all_id.removeAll(p_id);
        return nodes.get(all_id.iterator().next());

    }

    static class BinaryTree {
        Node root;

        HashMap<Integer, Node> nodes;

        public BinaryTree(Node rootNode, HashMap<Integer, Node> nodes) {

            this.root = rootNode;
            this.nodes = nodes;

            this.root.y = 0;

            // recursevly build tree from nodes
            buildTree(root);

            count=0;
            x(root);

            

        }

        private void buildTree(Node root) {

            if (root.id_l != -1) {
                root.left = nodes.get(root.id_l);
                root.left.parent = root;
                root.left.y = root.y + 1;
               
                buildTree(root.left);

            }
            if (root.id_r != -1) {
                root.right = nodes.get(root.id_r);
                root.right.parent = root;
                root.right.y = root.y + 1;
                
                buildTree(root.right);

            }

        }

        void x(Node root){

            if(root==null){
                return;
            }
            x(root.left);
            
            root.x=count;
            count++;
            x(root.right);

        }

        
        public String printTree() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            StringBuilder sb = new StringBuilder();

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                sb.append(node.v).append(",").append(node.x).append(",").append(node.y)
                        .append("\n");

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            return sb.toString();

        }

    }

    static class Node {

        int id, v, id_l, id_r;
        Node parent;
        int x;
        int y;
        Node left;
        Node right;

        public Node(int id, int v, int id_l, int id_r) {
            this.id = id;
            this.v = v;
            this.id_l = id_l;
            this.id_r = id_r;
        }

        public int getId_l() {
            return id_l;
        }

        public int getId_r() {
            return id_r;
        }
    }

}
