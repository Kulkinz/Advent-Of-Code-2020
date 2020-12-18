import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine().replace(" ", ""));
        }
        sc.close();

        System.out.println(data);


        long acc = 0;
        // System.out.println(test.toString());

        for (String string : data) {
            acc += Long.parseLong(process(getArguments(string)).get(0));
        }

        System.out.println(acc);        

        
    }

    public static ArrayList<String> getArguments(String x) {
        ArrayList<String> arguments = new ArrayList<>();

        String current = "";

        for (char c : x.toCharArray()) {
            switch (c) {
                case '(':
                    if (!current.equalsIgnoreCase("")) {
                        arguments.add(current);
                    }
                    arguments.add(String.valueOf(c));
                    current = "";
                    break;

                case ')':
                    if (!current.equalsIgnoreCase("")) {
                        arguments.add(current);
                    }
                    arguments.add(String.valueOf(c));
                    current = "";
                    break;

                case '*':
                    if (!current.equalsIgnoreCase("")) {
                        arguments.add(current);
                    }
                    arguments.add(String.valueOf(c));
                    current = "";
                    break;

                case '+':
                    if (!current.equalsIgnoreCase("")) {
                        arguments.add(current);
                    }
                    arguments.add(String.valueOf(c));
                    current = "";
                    break;

                default:
                    current += String.valueOf(c);
                    break;
            }
        }

        if (!current.equalsIgnoreCase("")) {
            arguments.add(current);
        }

        return arguments;
    }

    public static ArrayList<String> process(ArrayList<String> x) {
        ArrayList<int[]> locations = amountOfBrackets(x);

        String result = "";

        if (x.size() == 1) {
            ArrayList<String> element = new ArrayList<>();
            element.add(x.get(0));
            return element;
        }

        // System.out.println(Arrays.deepToString(locations.toArray()));

        if (!locations.isEmpty()) {
            int[] deepestLocations = findDeepestBrackets(locations);
            int low = deepestLocations[0];
            int high = deepestLocations[1];

            // System.out.println(Arrays.deepToString(locations.toArray()));
            // System.out.println(Arrays.toString(deepestLocations));

            ArrayList<String> targetString = new ArrayList<String>(x.subList(low, high + 1));
            targetString.remove(0);
            targetString.remove(targetString.size() - 1);
            // System.out.println(targetString);
            // x.replace(targetString, Integer.toString(process(targetString)));
            result = process(targetString).get(0);
            x.subList(low, high + 1).clear();
            x.add(low, result);
            // System.out.println(x.toString());
        } else {
            
            int index = x.indexOf("+");

            if (index != -1) {
                long a = Long.parseLong(x.get(index - 1));
                long b = Long.parseLong(x.get(index + 1));

                result = Long.toString(addition(a, b));
            } else {
                index = x.indexOf("*");
                long a = Long.parseLong(x.get(index - 1));
                long b = Long.parseLong(x.get(index + 1));

                result = Long.toString(multiplication(a, b));
            }
            
            int lower = index - 1;
            int higher = index + 2;
            

            // System.out.println(a);
            // System.out.println(op);
            // System.out.println(b);

            // if (op.equalsIgnoreCase("+")) {
            //     result = Long.toString(addition(a, b));
            // } else if (op.equalsIgnoreCase("*")) {
            //     result = Long.toString(multiplication(a, b));
            // }

            // System.out.println(result);
            
            // System.out.println(x.subList(lower, higher));
            x.subList(lower, higher).clear();
            x.add(lower, result);
            // System.out.println(x.toString());
        }

        process(x);
        
        return x;
    }

    public static long multiplication(long a, long b) {
        return a * b;
    }

    public static long addition(long a, long b) {
        return a + b;
    }

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
