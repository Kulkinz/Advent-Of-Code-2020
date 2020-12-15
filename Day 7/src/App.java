import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {
    
    static Map<String, ArrayList<Entry>> map = new HashMap<>();
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        for (String string : data) {
            String[] mainBags = string.split("contain");

            int lastSpot = mainBags[1].length() - 2;

            String theBags = mainBags[1].trim().substring(0, lastSpot);

            String[] bags = theBags.split(",");

            for (int i = 0; i < bags.length; i++) {
                bags[i] = bags[i].trim();

                if (bags[i].endsWith("s")) {
                    bags[i] = bags[i].substring(0,bags[i].length() - 1);
                }
            }

            //System.out.println(Arrays.toString(mainBags));
            //System.out.println(Arrays.toString(bags));

            ArrayList<Entry> entries = new ArrayList<>();

            for (String bag : bags) {
                String[] split = bag.split(" ");

                int amount;

                String nextBag = "";

                if (split[0].equalsIgnoreCase("no")) {
                    amount = 0;
                    nextBag = bag;
                } else {
                    amount = Integer.parseInt(split[0]);

                    for (int i = 1; i < split.length; i++) {
                        nextBag = nextBag + " " + split[i];
                    }
                }

                nextBag = nextBag.trim();

                Entry e = new Entry(nextBag, amount);

                entries.add(e);

            }

            String targetBag = mainBags[0].trim();

            if (targetBag.endsWith("s")) {
                targetBag = targetBag.substring(0,targetBag.length() - 1);
            }


            map.put(targetBag, entries);
        }

        int acc = 0;

        //System.out.println(map.get("bright white bag"));

        for (String string : data) {
            String[] mainBags = string.split("contain");

            String targetBag = mainBags[0].trim();

            if (targetBag.endsWith("s")) {
                targetBag = targetBag.substring(0,targetBag.length() - 1);
            }

            ArrayList<String> visited = new ArrayList<>();
            visited.add("no other bag");

            if (lookForBag(targetBag, visited, new ArrayList<>())) {
                acc++;
                //System.out.println(targetBag);
            }


        }

        //System.out.println(acc);

        int problem2 = countBags("shiny gold bag");

        System.out.println(problem2);
        
        
    }

    public static boolean lookForBag(String bag, ArrayList<String> visited, ArrayList<String> todo) {

        ArrayList<Entry> children = map.get(bag);

        if (bag.contains("shiny gold")) {
            return true;
        } else if (isInList(bag, visited)) {
            if (todo.isEmpty()) {
                return false;
            } else {
                return lookForBag(todo.remove(0), visited, todo);
            }
        } else {
            for (Entry entry : children) {
                todo.add(entry.bag);
            }
            visited.add(bag);
            return lookForBag(todo.remove(0), visited, todo);
        }
    }

    public static int countBags(String bag) {
        System.out.println("checking: " + bag);


        ArrayList<Entry> children = map.get(bag);

        if (bag.contains("no other bag")) {
            return 1;
        } else {
            int sum = 0;
            for (Entry entry : children) {
                System.out.println("amount: " + entry.amount);
                sum += entry.amount + entry.amount *countBags(entry.bag);
            }
            System.out.println("sum: " + sum);
            return sum;
        }
    }

    public static boolean isInList(String bag, ArrayList<String> visited) {
        for (String string : visited) {
            if (string.equalsIgnoreCase(bag.toLowerCase())) {
                return true;
            }
        }
        return false;
    }



    public static ArrayList<String> removeNoOther(ArrayList<String> visited) {
        ArrayList<String> temp = new ArrayList<>();
        for (String string : visited) {
            if (!string.equalsIgnoreCase("no other bag")) {
                temp.add(string);
            }
        }
        return temp;
    }

    public static class Entry {
        String bag;
        int amount;

        public Entry(String bag, int amount) {
            this.amount = amount;
            this.bag = bag;
        }
    }
    
}
