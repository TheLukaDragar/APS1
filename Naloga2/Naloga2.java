import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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

        //https://www.geeksforgeeks.org/reverse-the-elements-only-at-odd-positions-in-the-given-array/

        for (int j = 0; j < odstavki.length; j++) {

            System.out.print(odstavki[j]);

            String odstavek = odstavki[j];

            int stPovedi = 0;
            for (int j2 = 0; j2 < odstavek.length(); j2++) {
                if (odstavek.charAt(j2) == '.') {

                    stPovedi++;
                }

            }
            String[] povedi = new String[stPovedi-1];

            String poved = "";
            boolean preberseenobesedo = false;
            int blanks = 0;

            int povediI = 0;
            for (char c : reverse(odstavek).toCharArray()) {

                

                if (preberseenobesedo && c == ' ') {
                    blanks++;
                }

                if (blanks >= 2) {
                    blanks = 0;

                    String[] besede = poved.split(" ");
                    String[] besedeSorted=new String[besede.length];
                    int a2=0;

                  
                    for(int a=besede.length-1;a>=0;a--){

                        
                            if(a%2==0){
                                besedeSorted[a2++]=besede[a];
                                System.out.println(a2+1);
                            }else{
                                besedeSorted[a2--]=besede[a];
                                System.out.println(a2-1);
                            }
                            a2++;

                        

                        

                       
                        
                    }

                   

                    povedi[povediI] = String.join(" ", besedeSorted);
                    povediI++;
                    poved = "";
                    preberseenobesedo = false;

                }

                poved += c;
                if (Character.isUpperCase(c)) {
                    preberseenobesedo = true;

                }

            }

            for (String string : povedi) {

                System.out.println(string);
                System.out.println("-------------------------------------");

            }

           

        }

        //

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