import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        System.out.println(data);

        int timeStamp = Integer.parseInt(data.get(0));
        String[] rawData = data.get(1).split(",");
        ArrayList<Integer> busIds = new ArrayList<>();
        ArrayList<Integer> minuteDifference = new ArrayList<>();

        System.out.println(Arrays.toString(rawData));

        long ans = 0;

        for (int i = 0; i < rawData.length; i++) {
            if (!rawData[i].equalsIgnoreCase("x")) {
                busIds.add(Integer.parseInt(rawData[i]));
                minuteDifference.add(i);
            }
        }

        long acc = 1;
        long LCD = 1;

        for (Integer integer : busIds) {
            LCD *= integer;
        }

        long inc = 1;

        for (int i = 0; i < busIds.size(); i++) {

            while ((acc + minuteDifference.get(i)) % busIds.get(i) != 0) {
                acc += inc;
            }

            System.out.println(acc);

            inc *= busIds.get(i);   

        }

        System.out.println(LCD);

        while (acc - LCD > 0) {
            acc -= LCD;
        }

        // while (ans == 0) {
        //     acc += busIds.get(0);

        //     for (int i = 1; i < busIds.size(); i++) {
        //         long spot = acc + minuteDifference.get(i);

        //         if (spot % busIds.get(i) != 0) {
        //             ans = -1;
        //             break;
        //         }
        //     }

        //     if (ans == -1) {
        //         ans = 0;
        //     } else {
        //         ans = acc;
        //     }
        // }

        // for (String string : values) {
        //     if (!string.equalsIgnoreCase("x")) {
        //         int number = Integer.parseInt(string);

        //         int x = 1 + (int) Math.ceil(timeStamp / number);
        //         int closestTime = number * x;

        //         // System.out.println(x);
        //         System.out.println(closestTime);

        //         int different = closestTime - timeStamp;

        //         System.out.println(different);

        //         if (different < acc) {
        //             acc = different;
        //             ans = different * number;
        //         }
        //     }
        // }

        System.out.println(acc);
    }
}
