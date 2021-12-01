import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class Naloga2 {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String line;

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        br.mark(0);
        int stodstavki = 0;
        while ((line = br.readLine()) != null) {

            stodstavki++;
        }

        br.close();

        String[] odstavki = new String[stodstavki];

        BufferedReader br2 = new BufferedReader(new FileReader(args[0]));

        int i = 0;

        while ((line = br2.readLine()) != null) {

            odstavki[i] = line;
            i++;
        }

        

        int[] beriVrsta =new int[stodstavki];
        for (int m = 0; m < beriVrsta.length; m++) {
            beriVrsta[m]=m+1;
            
        }

        for (int h = 0; h <= beriVrsta.length/2-1; h++) {

            if (h%2==1) {
                beriVrsta[h]=h+1;
                
            }
            else{
                int tmp=beriVrsta[h];

                beriVrsta[h]=beriVrsta.length-1-h + beriVrsta.length%2 ;
                beriVrsta[beriVrsta.length -2 - h + beriVrsta.length % 2] =tmp;

            }
            
        }

        int[] copy = new int[beriVrsta.length];

        for (int n = 0; n <= beriVrsta.length - 1; n++) {
            copy[n] = beriVrsta[beriVrsta.length - 1 - n];
        }
        beriVrsta=copy;


        

        //System.out.println(beriVrsta);

        PrintWriter writer = new PrintWriter(args[1], "UTF-8");
        //test
       
       



        for (int j = odstavki.length-1; j>=0; j--) {

            String odstavek = odstavki[beriVrsta[j]-1];

            int stPovedi = 0;
            for (int j2 = 0; j2 < odstavek.length(); j2++) {
                if (odstavek.charAt(j2) == '.') {

                    stPovedi++;
                }

            }
            String[] povedi = new String[stPovedi];

            String poved = "";
            boolean preberseenobesedo = false;
            int blanks = 0;

            int povediI = 0;

            //System.out.println(reverse(odstavek).toString());
            for (char c : reverse(' '+odstavek).toCharArray()) { //zadnja poved nima blanka zato je dodan

                

                if (preberseenobesedo && c == ' ') {
                    blanks++;
                }

                if (blanks == 2) { 
                   

                    String[] besede = poved.trim().split(" ");
                    
                    String[] besedeSorted=new String[besede.length];
                   
                  
                    for(int a=besede.length-1;a>=0;a-=2){

                        if (a==0 && (besede.length-1) % 2 == 0){
                            besedeSorted[besedeSorted.length-1]=besede[0];
                            break;
                        }
                                besedeSorted[besede.length-a]=besede[a];
                                //System.out.println(besede.length - a);
                           
                                besedeSorted[besede.length - a-1]=besede[a-1];
                                //System.out.println(besede.length - a -1);
                    }

                    

                   
                    //System.out.println(String.join("-", besedeSorted));
                    
                    povedi[povediI] = String.join(" ", besedeSorted).trim();

                    povediI++;
                    poved = "";
                    blanks = 0;
                    preberseenobesedo = false;

                }

                poved += c;
                if (Character.isUpperCase(c)) {
                    preberseenobesedo = true;

                }

            }

          
                writer.print(String.valueOf(String.join(" ",povedi).trim()));
                if(j!=0){
                    writer.print("\r");
                writer.print("\n");

                }
                

              



           

        }

       
        writer.close();

        br2.close();
    }

    static String reverse(String str) {
        char[] original = str.toCharArray();
        char[] copy = new char[original.length];

        for (int n = 0; n <= original.length - 1; n++) {
            copy[n] = original[original.length - 1 - n];
        }

        return new String(copy);
    }
    // za poved beres iz obratne smeri do velke crke in dodas se eno besedo.
}