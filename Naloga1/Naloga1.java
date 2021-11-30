
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Naloga1 {

    public static void main(String[] args) throws IOException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(args[0]));

        int dim = Integer.parseInt(br.readLine());

        int[][] field = new int[dim][dim];

        int i = 0;
        while ((line = br.readLine()) != null) {
            int j = 0;
            for (String s : line.split(",")) {

                field[i][j] = Integer.parseInt(s);
                j++;
            }
            i++;
        }

        br.close();

        Queue queue = new Queue();
        boolean[] visited = new boolean[dim * dim];
        queue.enqueue(0, 0);

        while (!queue.empty()) {

            Integer vertex = queue.front();
            Integer sum = queue.frontsum();
            queue.dequeue();

            System.out.printf("%d %d\n", vertex, sum);

            int x = vertex % dim;
            int y = vertex / dim;

            if (x == dim - 1 && y == dim - 1) {
                // found the end
                //System.out.println("Found!");
                //System.out.println(sum);
                PrintWriter writer = new PrintWriter(args[1], "UTF-8");
                writer.println(String.valueOf(sum));
                writer.close();
                break;
            }

            visited[vertex] = true;

            int curr_height = field[y][x];
            int xp1=x+1;
            int xm1=x-1;
            int yp1=y+1;
            int ym1=y-1;

            if (xp1 < dim && isHeightOk(curr_height, field[y][xp1]) && !visited[(xp1) + y * dim]) {
                visited[(xp1) + y * dim]=true;

                queue.enqueue((xp1) + y * dim, isGoingUp(curr_height, field[y][xp1], sum));// desno

            }
            if (xm1 >= 0 && isHeightOk(curr_height, field[y][xm1]) && !visited[(xm1) + y * dim]) {
                visited[(xm1) + y * dim]=true;
                queue.enqueue((xm1) + y * dim, isGoingUp(curr_height, field[y][xm1], sum));// levo

            }

            if (yp1 < dim && isHeightOk(curr_height, field[yp1][x]) && !visited[x + (yp1) * dim]) {
                visited[x + (yp1) * dim]=true;

                queue.enqueue(x + (yp1) * dim, isGoingUp(curr_height, field[yp1][x], sum));// gor

            }

            if (ym1 >= 0 && isHeightOk(curr_height, field[ym1][x]) && !visited[x + (ym1) * dim]) {

                visited[x + (ym1) * dim]=true;
                queue.enqueue(x + (ym1) * dim, isGoingUp(curr_height, field[ym1][x], sum));// dol

            }

        }

    }

    static boolean isHeightOk(int now, int next) {

       
        if (now < next) {
            return next - now <= 20;
        }
        if (now > next) {
            return now - next <= 30;
        }
        return true;

    }

    static int isGoingUp(int now, int next, int sum) {

        if (now < next) {
            int a = next - now >= 0 ? next - now : (next - now) * -1;
            return sum + a;
        }
        return sum;

    }

}

class QueueElement {
    Integer element;
    Integer sum;
    QueueElement next;

    QueueElement() {
        element = null;
        sum = 0;
        next = null;
    }
}

class Queue {
    private QueueElement front;
    private QueueElement rear;

    public Queue() {
        makenull();
    }

    public void makenull() {
        front = null;
        rear = null;
    }

    public boolean empty() {
        return (front == null);
    }

    public Integer front() {
        if (!empty())
            return front.element;
        else
            return null;
    }

    public Integer frontsum() {
        if (!empty())
            return front.sum;
        else
            return null;
    }

    public void enqueue(Integer intt, Integer sum) {
        QueueElement el = new QueueElement();
        el.element = intt;
        el.sum = sum;
        el.next = null;

        if (empty()) {
            front = el;
        } else {
            rear.next = el;
        }

        rear = el;
    }

    public void dequeue() {
        if (!empty()) {
            front = front.next;

            if (front == null)
                rear = null;
        }
    }
}
