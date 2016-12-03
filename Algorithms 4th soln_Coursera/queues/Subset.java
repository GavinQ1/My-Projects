import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {
    private static String[] resize(int max, String[] rq) {
        int N = rq.length;
        assert max >= N;
        String[] temp = new String[max];
        for (int i = 0; i < N; i++) 
            temp[i] = rq[i];
        
        return temp;
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> R = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        
        String[] arr = new String[2];
        if (k != 0) {
            int count = 0;
            while (!StdIn.isEmpty()) {
                arr[count++] = StdIn.readString();
                if (count == arr.length)
                    arr = Subset.resize(2*arr.length, arr);
            }
            StdRandom.shuffle(arr);
            
            for (int i = 0; i < k; i++) {
                if (arr[i] != null)
                    R.enqueue(arr[i]);
                else
                    k++;
            }
            while (!R.isEmpty()) 
                System.out.println(R.dequeue());
        }
                    
                    
                    
                    
    }
}