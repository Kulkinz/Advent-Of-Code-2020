import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> rules = new ArrayList<>();

        
        File file = new File("data1.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            rules.add(sc.nextLine());
        }
        sc.close();

        System.out.println(rules);

        HashMap<Integer, ArrayList<String>> map = new HashMap<>();

        for (String string : rules) {
            String[] params = string.split(": ");
            int num = Integer.parseInt(params[0]);
            ArrayList<String> rule = new ArrayList<>();

            if (params[1].charAt(0) == '"' && params[1].charAt(2) == '"') {
                rule.add(String.valueOf(params[1].charAt(1)));
            } else {
                ArrayList<String> split = new ArrayList<>(Arrays.asList(params[1].split(" ")));
                split.add(0, "(");
                split.add(")");

                rule.addAll(split);
            }

            map.put(num, rule);

            System.out.println("Num: " + num +  " Rule: " + rule);

        }

        ArrayList<String> prev = new ArrayList<>();

        while (!map.get(0).equals(prev)) {
            prev = map.get(0);

            ArrayList<String> update = new ArrayList<>();

            for (String string : prev) {
                if (string.equalsIgnoreCase("(")) {
                    update.add("(");
                } else if (string.equalsIgnoreCase(")")) {
                    update.add(")");
                } else if (string.equalsIgnoreCase("|")) {
                    update.add("|");
                } else {
                    try {
                        int x = Integer.parseInt(string);
                        update.addAll(map.get(x));
                    } catch (Exception e) {
                        update.add(string);
                    }
                }
            }

            // System.out.println(update);

            map.put(0, update);
        }


        // -------------------------------------------------

        ArrayList<String> possibilities = map.get(0);

        System.out.println(possibilities);

        // System.out.println(Arrays.toString(findDeepestBrackets(amountOfBrackets(possibilities))));

        System.out.println(process(possibilities, new ArrayList<>()));


        // --------------------------------------------------

        ArrayList<String> messages = new ArrayList<>();

        file = new File("data2.txt");
        sc = new Scanner(file);

        while (sc.hasNextLine()) {
            messages.add(sc.nextLine());
        }
        sc.close();

        System.out.println(messages);
    }

    public static ArrayList<String> process(ArrayList<String> x, ArrayList<String> list) {

        ArrayList<String> result = new ArrayList<>();

        System.out.println(x);

        ArrayList<int[]> locations = amountOfBrackets(x);

        if (!locations.isEmpty()) {
            int[] deepestLocations = findDeepestBrackets(locations);
            int low = deepestLocations[0];
            int high = deepestLocations[1];
        
            ArrayList<String> targetString = new ArrayList<String>(x.subList(low, high + 1));
            targetString.remove(0);
            targetString.remove(targetString.size() - 1);
        
            result = process(targetString, list);
                    
            System.out.println("Target: " + targetString);
            System.out.println("Result: " + result);
        
            x.subList(low, high + 1).clear();
        
            System.out.println("X: " + x);
        } else {
            ArrayList<String> strings = new ArrayList<>();
            String acc = "";
            for (String string : x) {
                acc += string;    
            }

            strings.add(acc);

            System.out.println(acc);

            return strings;
        }

        return list;
    }

    // public static ArrayList<ArrayList<String>> process(ArrayList<String> x) {
    //     ArrayList<ArrayList<String>> result = new ArrayList<>();
        
    //     ArrayList<int[]> locations = amountOfBrackets(x);

    //     if (result.size() == 1) {
    //         ArrayList<ArrayList<String>> element = new ArrayList<>();
    //         element.add(result.get(0));
    //         return element;
    //     }

    //     if (!locations.isEmpty()) {
    //         int[] deepestLocations = findDeepestBrackets(locations);
    //         int low = deepestLocations[0];
    //         int high = deepestLocations[1];

    //         ArrayList<String> targetString = new ArrayList<String>(x.subList(low, high + 1));
    //         targetString.remove(0);
    //         targetString.remove(targetString.size() - 1);

    //         result = process(targetString);
            
    //         System.out.println(targetString);
    //         System.out.println(result);

    //         x.subList(low, high + 1).clear();

    //         System.out.println(x);



    //     } else {

    //         ArrayList<String> options = new ArrayList<>();
    //         int index = x.indexOf("|");
    //         int max = x.size();

    //         String temp = "";
    //         for (int i = 0; i < index; i++) {
    //             temp += x.get(i);
    //         }
    //         options.add(temp);
    //         temp = "";
    //         for (int i = index + 1; i < max; i++) {
    //             temp += x.get(i);
    //         }
    //         options.add(temp);

    //         System.out.println("options: " + options);

    //         result.add(options);
    //     }

    //     return result;


    // }

    public static int[] findDeepestBrackets(ArrayList<int[]> locations) {
        int leftBracket = -1;
        int rightBracket = -1;
        int last = 0;

        for (int[] is : locations) {
            if (last == 0) {
                if (is[1] == 0) {
                    leftBracket = is[0];
                } else if (is[1] == 1) {
                    rightBracket = is[0];
                    break;
                }
            }
        }

        int[] location = {leftBracket, rightBracket};

        return location;
    }

    public static ArrayList<int[]> amountOfBrackets(ArrayList<String> x) {
        ArrayList<int[]> brackets = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            // System.out.println(x.get(i));
            if (x.get(i).equalsIgnoreCase("(")) {
                int[] state = {i,0};
                brackets.add(state);
            } else if (x.get(i).equalsIgnoreCase(")")) {
                int[] state = {i,1};
                brackets.add(state);
            }
        }

        return brackets;
    }
}
