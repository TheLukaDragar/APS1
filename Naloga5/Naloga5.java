import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Naloga5 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        int stkupcki = Integer.parseInt(br.readLine());

        int[] kupcki = new int[stkupcki];

        int i = 0;

        String line;

        while ((line = br.readLine()) != null) {
            kupcki[i] = Integer.parseInt(line);
            i++;
        }
        br.close();

        int N = 0;

        for (int j = 0; j < stkupcki; j++) {

            if(N==0 && optimalVzemi(kupcki)==0){
                N = -1;
                break; 
            }
            // If there are a different number of stones in each pile, then the first player
            // has a winning strategy
            // vzeti mora tok da bo v vsakem stolpu razlicno st kamnov
            for (int vzemi = optimalVzemi(kupcki); vzemi > 0; vzemi--) {// probavaj

                int kamencki = kupcki[j];
                kupcki[j] = kamencki - vzemi;

                if (optimalVzemi(kupcki) == 0) { // to je winning move da so vsi razlicni
                    int stKamenckov = 0;
                    for (int l : kupcki) {
                        stKamenckov += l;
                    }
                    stKamenckov+=1;
                    if (N > stKamenckov || N == 0) {// glej za konec kamenckov
                        N = stKamenckov;
                    }
                }
                kupcki[j] = kamencki;

            }

        }

        Writer out = new FileWriter(args[1]);
        out.write(String.valueOf(N));
        out.write("\n");
        out.close();



    }

    static int optimalVzemi(int[] kupcki) {
        int ret = 0;

        for (int j : kupcki) {
            ret = ret ^ j;
        }
        return ret;

    }

}
