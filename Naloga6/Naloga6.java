import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.Writer;

import java.util.Stack;
import java.util.StringTokenizer;

public class Naloga6 {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        String line;
        BufferedReader br;

        br = new BufferedReader(new FileReader(args[0]));
        line = br.readLine();
        br.close();

        // Parse input
        String f = line.replace("(", "( ").replace(")", " )").toString();
        f = "( " + f;
        f += " )";

        StringTokenizer st = new StringTokenizer(f);

        Stack<Enode> nodes = new Stack<Enode>();

        Stack<String> atoms = new Stack<String>();

        Enode m, l, r;

        int[] priority = new int[123];
        priority['O'] = 1;
        priority['A'] = 2;
        priority['N'] = 3;
        priority[')'] = 0;

        String exp="";

       
      

        while (st.hasMoreTokens()) {
           
            exp = st.nextToken();
            

            if (exp.equals("(")) {
                atoms.push(exp);
            } else if (!exp.equals("AND") && !exp.equals("OR") && !exp.equals("NOT") && !exp.equals("(")
                    && !exp.equals(")")) {

                m = newNode(exp);
                nodes.push(m);

            } else if (!exp.equals(")")) {

                while (!atoms.isEmpty() && !atoms.peek().equals("(")
                        && (priority[atoms.peek().charAt(0)] >= priority[exp.charAt(0)])) {

                    // elemente z manjso ali enako asociativnostjo

                    m = newNode(atoms.pop());

                    if(!m.data.equals("NOT")){
                        l = nodes.pop();
                        m.left = l;
                        
                    }

                    r = nodes.pop();              
                    m.right = r;

                    nodes.push(m);

                }
                atoms.push(exp);

            } else if (exp.equals(")")) {
                while (!atoms.isEmpty() && !atoms.peek().equals("(")) {
                    m = newNode(atoms.pop());
                    
                    if(!m.data.equals("NOT")){
                        l = nodes.pop();
                        m.left = l;   
                    }
                    r = nodes.pop();
                    m.right = r;
                    nodes.push(m);
                }
                atoms.pop();

            }

            System.out.println(exp);

           

            
           

        }

        String out = postorder(nodes.peek(), new StringBuilder()).reverse().toString();

        out = out.substring(0, out.length() - 1);

        System.out.println(out);

        Writer outt = new FileWriter(args[1]);

        outt.write(out);
        outt.write("\n");
        outt.write(String.valueOf(treeHeight(nodes.peek())));
        outt.write("\n");
        outt.close();

    }

    static StringBuilder postorder(Enode root, StringBuilder sb) {
        if (root != null && root.data != null) {
            postorder(root.left, sb);
            postorder(root.right, sb);
            sb.append(",").append(new StringBuilder(root.data).reverse().toString());
        }
        return sb;
    }

    static int treeHeight(Enode root) {
        if (root == null)
            return 0;
        int leftTreeHeight = treeHeight(root.left);
        int rightTreeHeight = treeHeight(root.right);
        return Math.max(leftTreeHeight, rightTreeHeight) + 1;
    }

    static class Enode {
        String data;
        Enode left, right;
    };

    static Enode newNode(String s) {
        Enode n = new Enode();
        n.data = s;
        n.left = n.right = null;
        return n;
    }

}
