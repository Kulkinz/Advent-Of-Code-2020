import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<Integer> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextInt());
        }
        sc.close();

        Collections.sort(data);
        System.out.println(data);

        int start = 0;
        int acc1 = 0;
        int acc3 = 0;

        for (int i = 0; i < data.size(); i++) {
            switch (data.get(i) - start) {
                case 1:
                    acc1++;
                    start = data.get(i);
                    break;

                case 2:
                    start = data.get(i);
                    break;

                case 3:
                    acc3++;
                    start = data.get(i);
                    break;
            
                default:

                    break;
            }
        }
        
        acc3++;

        System.out.println(acc1 * acc3);

        int max = data.get(data.size() - 1);

        data.add(max + 3);

        

        Map<Integer, int[]> map = new HashMap<>();
        Map<Integer, Long> values = new HashMap<>();

        int length = data.size();

        for (int i = 0; i < length; i++) {
            map.put(data.get(i), generateConnections(data, i));
        }

        values.put(0, (long) 0);
        values.put(max + 3, (long) 1);

        for (int i = length - 2; i >= 0; i--) {
            int[] array = map.get(data.get(i));

            long acc = 0;
            for (int j : array) {
                acc += values.get(j);
            }

            values.put(data.get(i), acc);
        }

        System.out.println(values.get(1));
        System.out.println(values.get(2));
        System.out.println(values.get(3));

        System.out.println(values.get(1) + values.get(2) + values.get(3));
        
    }

    public static int[] generateConnections(ArrayList<Integer> data, int position) {
        int[] value = {0,0,0};

        for (int i = 0; i < 3; i++) {
            try {
                if (data.get(i + 1 + position) <= data.get(position) + 3) {
                    value[i] = data.get(i + 1 + position);
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }

        return value;
    }

    // public static long combinations(ArrayList<Integer> data, int current, int max) {

    //     long acc = 0;
        
        
    //     int one = current + 1;
    //     int two = current + 2;
    //     int three = current + 3;

    //     int oneI = data.indexOf(one);
    //     int twoI = data.indexOf(two);
    //     int threeI = data.indexOf(three);

    //     if (current == max) {
    //         return 1;
    //     }

    //     if (oneI != -1) {
    //         acc += combinations(data, one, max);
    //     }
    //     if (twoI != -1) {
    //         acc += combinations(data, two, max);
    //     }
    //     if (threeI != -1) {
    //         acc += combinations(data, three, max);
    //     }

    //     return acc;
    // }
}
