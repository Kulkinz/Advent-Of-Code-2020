import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();
        ArrayList<String> form = new ArrayList<>();

        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        data.add("");

        ArrayList<String> tempGroup = new ArrayList<>();

        String split = "";
        for (String string : data) {
            if (string.equalsIgnoreCase("")) {
                System.out.println(tempGroup);
                groups.add(tempGroup);
                tempGroup = new ArrayList<>();
            } else {
                tempGroup.add(string);
            }
        }
        
        int acc = 0;

        // for (String s : form) {
            
        //     s = s.replace(" ", "");
        //     boolean[] isItThere = new boolean[Character.MAX_VALUE];

        //     for (int i = 0; i < s.length(); i++) {
        //         isItThere[s.charAt(i)] = true;
        //     }

        //     int count = 0;
        //     for (int i = 0; i < isItThere.length; i++) {
        //         if (isItThere[i]) {
        //             count++;
        //         }
        //     }
        //     System.out.println(count);

        //     acc += count;
        // }

        for (ArrayList<String> group : groups) {

            int people = group.size();

            Map<Character, Integer> map = new HashMap<>();

            for (String string : group) {
                string = string.trim();
                for(char c : string.toCharArray()) {
                    if (map.containsKey(c)) {
                        int counter = map.get(c);
                        map.put(c, ++counter);
                    } else {
                        map.put(c,1);
                    }
                }
            }


            for (char c : map.keySet()) {
                if(map.get(c) == people) {
                    acc++;
                }
            }
        }

        System.out.println(acc);
    }
}
