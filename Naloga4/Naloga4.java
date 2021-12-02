import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Naloga4 {

    static int T;
    static int N;
    static int S;
    static int K;
    static int []LA;
    static int []LW;
    
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
     
        T = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        S = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());
       

        String[] input = br.readLine().split(",");
        LA = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            LA[i] = Integer.parseInt(input[i]);
        }

        input = br.readLine().split(",");
        LW = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            LW[i] = Integer.parseInt(input[i]);
        }
        br.close();

		ZunajQueue zunajQueue = new ZunajQueue(N, LA, LW);

		Queue postrizeniQueue = new Queue(LA, LW);

		Queue simQueue = new Queue(LA, LW).simQueue(T);

		Stranka naStolu=null;
		int strizenjecas=0;

		for (int i = 1; i < T; i++) {

			if(naStolu!=null && strizenjecas==0){
				postrizeniQueue.enqueue(naStolu);
				naStolu=null;
				S+=K;
			}

			if(naStolu==null && zunajQueue.curr_size>0){
				naStolu=zunajQueue.front();
				strizenjecas=S;

			}
			if(zunajQueue.curr_size>0){
				zunajQueue.Poosodobi(i);
			}


			Stranka naslednja = simQueue.front();
			if(naslednja!=null && naslednja.prihod==i){
				
				if(naStolu == null){
					naStolu = naslednja;
					strizenjecas=S;
				}else{
					zunajQueue.enqueue(naslednja);
				}
				simQueue.dequeue();

			}

			strizenjecas--;

		}

		PrintWriter writer = new PrintWriter(args[1], "UTF-8");
		

		while(!postrizeniQueue.empty()){

			writer.print(postrizeniQueue.front().id);
			
			
			postrizeniQueue.dequeue();
			if(!postrizeniQueue.empty()){
				writer.print(",");
			}

			
		}
		writer.print("\n");

		writer.close();


		





        
    }
}
class Stranka
{
	
    int id;
    int prihod;
    int potrplenje;
	Stranka next;

	Stranka(int id,int[] LA,int[] LW)
	{
        this.id=id;
        this.prihod=0;
        
		
        int h = 0;

        for (int i = 1; i <= id; i++) {

            if (h >= LA.length){
                h = 0;       
            } 

            this.prihod += LA[h];
            h++;
        }
      
        this.potrplenje = LW[(id - 1) % LW.length];
        this.next = null;
	}
}

class Queue
{
	
	private Stranka front;
	private Stranka rear;
	protected int[] LA;
	protected int[] LW;
	
	public Queue(int[] LA,int[] LW)
	{
		this.LA=LA;
		this.LW=LW;
		front = null;
		rear = null;
	
	}

	
	
	public boolean empty()
	{
		return (front == null);
	}
	
	public Stranka front()
	{
		if (!empty())
			return front;
		else
			return null;
	}
	
	public void enqueue(Stranka stranka)
	{
		
		if (empty()) {
            this.rear = stranka;
            this.front = this.rear;
        } else {
            this.rear.next = stranka;
            this.rear = this.rear.next;
        }
	}
	
	public void dequeue()
	{
		if (!empty()) {
            if (front == rear) {
                this.front = null;
                this.rear = this.front;
            } else {
                this.front = this.front.next;
            }
        }
	}
	public Queue simQueue(int T) {
        int id = 1;

       
        for (int i = 0; i < T; ) {
            Stranka stranka = new Stranka(id, LA, LW);
            if (stranka.prihod <= T) {
				id++;
                this.enqueue(stranka);

               

            }
            i = stranka.prihod;
        }
        return this;
    }
}

class ZunajQueue extends Queue{
	int N;
	int curr_size;

	public ZunajQueue(int N,int[] LA, int[] LW) {
		super(LA, LW);
		this.curr_size=0;
		this.N=N;
	
	}
	@Override
	
	public void enqueue(Stranka stranka) {
		if(this.curr_size<N){//gleda ce je dovolj prstora zunaj
			super.enqueue(stranka);
			this.curr_size++;
		}
		
	}

	@Override
	public void dequeue() {
		this.curr_size--;
		super.dequeue();
	}

	public void Poosodobi(int T) {
        Stranka trenuStranka = this.front();
        while (trenuStranka != null) {

            if (trenuStranka.prihod + trenuStranka.potrplenje <= T) {//stranka zapusti cakalnico ce je predolgo cakala
                this.dequeue();
            } else
                break;
            trenuStranka = trenuStranka.next;
        }
    }

	
	


}
