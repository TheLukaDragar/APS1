import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;


public class daqdas{

    public static void main(String[] args) throws IOException {

        StreamTokenizer st = new StreamTokenizer(new FileReader(args[0]));

        st.nextToken();

        ArithThree arithThree = new ArithThree();
        arithThree.rootNode = arithThree.expression(st);
        while(st.ttype!= StreamTokenizer.TT_EOF){
            System.out.println(st.sval);
            st.nextToken();

        }

        arithThree.preorder(arithThree.rootNode);
    

        
        
    }

    
    

    

    static class ArithThree{

        static final String AND = "AND";
        static final String OR = "OR";
        static final String NOT = "NOT";
        static final int L_BRACKER = '(';
        static final int R_BRACKER = ')';

        public ArithNode rootNode;

        public class ArithNode{
            public ArithNode(String sval) {
                this.value=sval;
            }
            int operator;
            String value;
            ArithNode left;
            ArithNode right;

            public boolean isLeaf(){return left==null &&  right == null;}
        }

        public void preorder(ArithNode rootNode) {
            if (rootNode == null)
              return;
        
            // traverse the root node
            System.out.print(rootNode.value + "->");
            // traverse the left child
            preorder(rootNode.left);
            // traverse the right child
            preorder(rootNode.right);
          }





   

    public ArithNode expression(StreamTokenizer st) throws IOException{
       
        ArithNode rootNode = not(st);
        rootNode = and(st);

        rootNode = or(st);



        return rootNode;
    }

    private ArithNode not(StreamTokenizer st) throws IOException{
        ArithNode root = value(st);
        if(st.sval == ArithThree.NOT){
            return not2(st,root);
        }else{
            return root;
        }
    }

    private ArithNode and(StreamTokenizer st) throws IOException{
        ArithNode root = value(st);
        if(st.sval == ArithThree.AND){
            return and2(st,root);
        }else{
            return root;
        }
    }

    private ArithNode or(StreamTokenizer st) throws IOException{
        ArithNode root = value(st);
        if(st.sval == ArithThree.OR){
            return or2(st,root);
        }else{
            return root;
        }
    }

    private ArithNode not2(StreamTokenizer st,ArithNode left) throws IOException{
        ArithNode root = new ArithNode(st.sval);
        root.left= left;
        st.nextToken();
        root.right=value(st);
        if(st.sval==ArithThree.NOT){
            return not2(st, root);

        }else{return root;}
       
    }

    private ArithNode and2(StreamTokenizer st,ArithNode left) throws IOException{
        ArithNode root = new ArithNode(st.sval);
        root.left= left;
        st.nextToken();
        root.right=value(st);
        if(st.sval==ArithThree.AND){
            return and2(st, root);

        }else{return root;}
       

        
    }
    private ArithNode or2(StreamTokenizer st,ArithNode left) throws IOException{
        ArithNode root = new ArithNode(st.sval);
        root.left= left;
        st.nextToken();
        root.right=not(st);
        root.right=and(st);
        if(st.sval==ArithThree.OR){
            return and2(st, root);

        }else{return root;}
       
    }

    private ArithNode value(StreamTokenizer st) throws IOException{
        if(st.ttype == StreamTokenizer.TT_WORD && st.ttype!=ArithThree.L_BRACKER && st.ttype!=ArithThree.R_BRACKER){
            ArithNode node = new ArithNode(st.sval);
            st.nextToken();
            return node;
            

        }
        else if(st.ttype!= ArithThree.L_BRACKER)
        {
            return null;
        }
        else{
            st.nextToken();
            ArithNode node = expression(st);
            if(st.ttype != ArithThree.R_BRACKER){
                return null;
            }
            else{
                st.nextToken();
                return node;
            }
        }
    }
}
    
}