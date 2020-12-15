import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        HashMap<Integer, Integer> map = new HashMap<>();


        // ArrayList<Long> values = new ArrayList<>();

        int i = 0;

        for (String string : data.get(0).split(",")) {
            map.put(Integer.parseInt(string), i);
            i++;
        }

        int current = 0;

        while (i < 30000000 - 1) {
            int next;
            // System.out.println(i);
            if (map.containsKey(current)) {
                next = i - map.get(current);
            } else {
                next = 0;
            }
            // System.out.println("Current: " + current);
            // System.out.println("Next: " + next);
            map.put(current, i);
            current = next;
            i++;
        }

        System.out.println(current);
    }

    
            // long prev = values.get(i - 1);
            // // System.out.println(prev);
            // ArrayList<Long> locals = getIndex(values, prev);
            // // System.out.println(locals);
            // if (locals.size() == 1) {
            //     values.add(0L);
            // } else {
            //     int size = locals.size();
            //     values.add(locals.get(size - 1) - locals.get(size - 2));
            // }

    public static ArrayList<Long> getIndex(ArrayList<Long> values, long target) {
        ArrayList<Long> locations = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) == target) {
                locations.add(Long.valueOf(i));
            }
        }

        return locations;
    }
}
