import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Naloga3 {
    public static LinkedList linkedList;
    public static void main(String[] args) throws IOException {
        String line;

       

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        br.readLine(); 
        
        while ((line = br.readLine()) != null) {

            String[] values = line.split(",");
        switch (line.charAt(0)) {
            // Initialize linkedlist
            case 's':
            linkedList = new LinkedList(Integer.parseInt(values[1]));
                break;
            // Insert into linked list
            case 'i':
                linkedList.insert(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
                break;
            // remove element from a linked list
            case 'r':
            linkedList.remove(Integer.parseInt(values[1]));
                break;
        }
           
        }
        br.close();

        Writer out = new FileWriter(args[1]);
        LinkedListElement first = linkedList.first.next;
        out.write(Integer.toString(linkedList.numClenov()));
        out.write("\n");
        while (first != null) {
            out.write(first.toString());
            out.write("\n");
            first = first.next;
        }
        out.close();

    }

}

class LinkedListElement {
    Integer[] elementi;
    int num_elementi;
    LinkedListElement next;

    LinkedListElement(Integer[] elementi, LinkedListElement next) {
        this.elementi = elementi;
        this.next = next;
        this.num_elementi = 0;

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < elementi.length; i++) {

            if (elementi[i] == null) {
                s.append(i == 0 ? "NULL" : ",NULL");
            } else {
                s.append(i == 0 ? elementi[i] : "," + elementi[i]);
            }
        }
        return s.toString();
    }

}

class LinkedList {
    protected LinkedListElement first;
    protected LinkedListElement last;
    protected int size;

    LinkedList(int n) {
        this.init(n);
    }

    public void init(int N) {
        this.size=N;   

        first = new LinkedListElement(null, new LinkedListElement(new Integer[size], null));// header je null ki kaze na
                                                                                            // prvielement
                                                                                        
                                                                                        
        last = null;

    }

    private void insertIntoArray(LinkedListElement el, int p, int v) {
        Integer[] tmp = new Integer[size];
        int h =0;

        for (int i = 0; i < size; i++) {
            if(h==p){
                tmp[h]=v;
                h++;
            }
            if(el.elementi[i]!=null){
                tmp[h]=el.elementi[i];
                h++;
            }
            if(h<i && el.elementi[i]==null){
                break;
            }

            
        }

        el.num_elementi++;
        el.elementi=tmp;




    }

    private void makeNewClenAndInsert(LinkedListElement el, int p, int v) {
        int size1= el.num_elementi/2;


        Integer[] array1 = new Integer[size];
        Integer[] array2 = new Integer[size];

        int kamvstavit=1;

        for (int i = 0; i < size; i++) {

            if(i<size1){
                array1[i]=el.elementi[i];

            }else{
                array2[i]=el.elementi[i];

                if(p==i){
                    kamvstavit=2;

                }
              

            }
            
        }

        el.elementi = array1;
        el.next = new LinkedListElement(array2, el.next);
        // Set number of elements in arrays:)
       

        if(kamvstavit==1){
            insertIntoArray(el, p, v);
        }else{
            insertIntoArray(el.next, p, v);

        }




    }




    public void insert(int v, int p) {
      
        int pm1 = p - 1;
        int sum_stevec = 0;
        int element_stevec = 0;

        LinkedListElement i = this.first.next;

        if (i.elementi[0] == null || p == 0) {
            if (i.num_elementi == size) {
               
                makeNewClenAndInsert(i, p, v);
            } else {
                //
                // insert in pos 0 of next
                insertIntoArray(i, p, v);

            }
            return;
        }
     

        loop: while (i != null) {

            element_stevec = sum_stevec;
            sum_stevec += i.num_elementi;

            if (sum_stevec >= p) {

                for (int k = 0; k < size; k++) {

                    if (i.elementi[k] != null) {

                        if (element_stevec == pm1) {

                            if (k == size - 1) {// ce je zadnji

                                if (i.next.num_elementi == size) {
                                    // split pos 0
                                    makeNewClenAndInsert(i.next, 0, v);
                                } else {
                                    //
                                    // insert in pos 0 of next
                                    insertIntoArray(i.next, 0, v);

                                }

                            } else {

                                if (i.num_elementi == size) {
                                    // split k+1
                                    makeNewClenAndInsert(i, p+1, v);

                                } else {
                                    // insert k+1
                                    insertIntoArray(i, p+1, v);
                                }

                            }
                            break loop;

                        } 
                        element_stevec++;
                    }

                }
            }
            i=i.next;

        }

    }

    public void remove(int position) {
        LinkedListElement i = first.next;

        int element_stevec = 0;
        int sum_stevec = 0;
       
        // Go through all elements of a linked list (arrays):(
        loop:
        while (i != null) {

            element_stevec = sum_stevec;
            sum_stevec += i.num_elementi;

            if(sum_stevec >= position) {
                // Go through every element in a array
                for (int k = 0; k < size; k++) {

                    if (i.elementi[k] != null) {

                        // Check if we have reached the element
                        if (element_stevec == position) {

                            int limit =  size / 2  + 1;

                            // If next element is null, we must remove no matter what
                            if (i.num_elementi >= limit || i.next == null){
                                removeElementFromProvidedArray(i, k);

                            }
                               
                            else{
                                removeElementFromProvidedArrayAndMoveElements(i, k);
                            }
                                
                            break loop;
                        }
                        element_stevec++; // increment current element index
                    }
                }
            }
            i = i.next;
        }
    }

    

    private void removeElementFromProvidedArray(LinkedListElement el, int pos) {
        Integer[] tmp = new Integer[size];
        int h = 0;
        
        for (int i = 0; i < size; i++) {
            if (i != pos) {
                tmp[h] = el.elementi[i];
                h++;
            }
        }
       
        el.num_elementi--;
        el.elementi = tmp;
    }

    private void removeElementFromProvidedArrayAndMoveElements(LinkedListElement el, int pos) {
        LinkedListElement next = el.next;

        int limit =  size / 2  + 1;

        if (next.num_elementi >= limit) {
            // Remove element we are removing
            removeElementFromProvidedArray(el, pos);
            // Store element we want to move
            int value_to_be_added = next.elementi[0];
            // Remove first element form second array
            removeElementFromProvidedArray(next, 0);
            // Then add that element to last position of first array
            el.elementi[el.num_elementi] = value_to_be_added;
            el.num_elementi++;
        } else {
            // There are not enough elements in second array, so we need to free that element!
            freeSecondElement(el, pos);
        }
    }

    private void freeSecondElement(LinkedListElement el, int pos) {
        Integer[] temp_arr = new Integer[size];
        int moving_index = 0;
        int second_array_index = 0;
        // firstly remove element from array
        for (int i = 0; i < size; i++) {
            // We have reached first null element, now we know where we have finished
            if (el.elementi[i] == null) {
                second_array_index = moving_index;
                break;
            }
            if (i != pos) {
                temp_arr[moving_index] = el.elementi[i];
                moving_index++;
            }
        }

        LinkedListElement next_element = el.next;

        for (int i = 0; i < next_element.num_elementi; i++) {

            temp_arr[second_array_index] = next_element.elementi[i];
            second_array_index++;
            // Break if we have filled whole array
            if (second_array_index == size) {
                break;
            }
        }
        // Rewire pointers
        el.elementi = temp_arr;
        el.next = el.next.next;
        el.num_elementi = second_array_index;
    }

    public int numClenov() {
        LinkedListElement i = first.next;
        int counter = 0;
        while (i != null) {
            counter++;
            i = i.next;
        }
        return counter;
    }

}
