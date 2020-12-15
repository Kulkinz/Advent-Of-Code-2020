import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<Long> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        //long test = 2433745816f;

        while (sc.hasNextLine()) {
            data.add(sc.nextLong());
        }
        sc.close();

        int preamble = 25;

        long acc = 0;
        

        for (int i = preamble; i < data.size(); i++) {
            
            ArrayList<Long> factors = new ArrayList<>();

            for (int j = i - preamble; j < i; j++) {
                factors.add(data.get(j));
            }


            ArrayList<Long> sums = sums(factors);
            
            if (!sums.contains(data.get(i))) {
                acc = data.get(i);
                break;
            }
            System.out.println(sums);
            System.out.println(data.get(i));
            System.out.println(sums.contains(data.get(i)));
        }

        System.out.println(acc);

        System.out.println(filter(data, acc));
    }

    public static ArrayList<Long> sums(ArrayList<Long> values) {
        ArrayList<Long> totalSums = new ArrayList<>();
        
        for (Long integer1 : values) {
            for (Long integer2 : values) {
                if (integer1 != integer2) {
                    totalSums.add(integer1 + integer2);
                }
            }
        }

        return totalSums;

    }

    public static Long filter(ArrayList<Long> values, long filter) {
        long answer = 0;

        for (int i = 0; i < values.size(); i++) {
            long min = values.get(i);
            long max = values.get(i);
            long sum = 0;
            for (int j = i; j < values.size(); j++) {
                long value = values.get(j);
                sum += value;

                min = value < min ? value : min;
                max = value > max ? value : max;
                
                //System.out.println("Value: " + value);
                //System.out.println("Sum: " +sum);

                if (sum == filter) {
                    answer = min + max;
                    break;
                } else if (sum > filter) {
                    break;
                }

            }

            if (answer != 0) {
                break;
            }

            //System.out.println("----------------");
        }

        return answer;
    }
}
