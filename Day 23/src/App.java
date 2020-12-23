import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws Exception {
    
        System.out.println("Hello, World!");

        ArrayList<Integer> cups = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            cups.add(sc.nextInt());
        }
        sc.close();

        System.out.println(cups);

        int index = 0;
        int length = cups.size();

        for (int i = 0; i < 100; i++) {
            int target = cups.get(index);

            System.out.println("current target:" + target);

            ArrayList<Integer> next3Terms = new ArrayList<>();

            for (int j = 1; j <= 3; j++) {
                
                int localTarget = (index + j >= length) ? index + j - length : index + j;

                next3Terms.add(cups.get(localTarget));
                
            }

            cups.removeAll(next3Terms);

            System.out.println(cups);
            System.out.println(next3Terms);

            int nextTarget = target - 1;

            int locationIndex = -1;

            while (nextTarget > 0 && locationIndex == -1) {

                if (next3Terms.indexOf(nextTarget) != -1) {
                    
                    nextTarget--;
                } else {
                    locationIndex = cups.indexOf(nextTarget);
                }
            }
            
            System.out.println("next target: " + nextTarget);

            if (locationIndex == -1) {
                locationIndex = cups.indexOf(Collections.max(cups));
            }

            System.out.println(locationIndex);
            System.out.println(cups.get(locationIndex));

            cups.addAll(locationIndex + 1, next3Terms);

            System.out.println(cups);

            index = (cups.indexOf(target) + 1 >= length) ? cups.indexOf(target) + 1 - length : cups.indexOf(target) + 1;

        }

        int indexOf1 = cups.indexOf(1);
        int iterateIndex = indexOf1 + 1;

        String acc = "";

        for (int i = 1; i < cups.size(); i++) {
            
            acc += cups.get(iterateIndex);

            iterateIndex = (iterateIndex + 1 >= length) ? iterateIndex + 1 - length : iterateIndex + 1;
            
        }

        System.out.println(acc);
    }
}
