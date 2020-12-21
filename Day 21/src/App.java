import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    static HashMap<String, ArrayList<String>> foodCorrelations = new HashMap<>();
    
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

        // ArrayList<String[]> allergies = new ArrayList<>();
        // ArrayList<String[]> food = new ArrayList<>();

        HashMap<String, ArrayList<Integer>> allergiesLocation = new HashMap<>();

        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<String> listOfAllergies = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String[] split = data.get(i).split(" \\(contains ");
            String[] foodStrings = split[0].split(" ");
            String[] allergiesStrings = split[1].substring(0,split[1].length() - 1).split(", ");

            System.out.println(Arrays.toString(foodStrings));
            System.out.println(Arrays.toString(allergiesStrings));

            Entry e = new Entry(foodStrings, allergiesStrings);

            // allergies.add(allergiesStrings);
            // food.add(foodStrings);
            values.add(e);
            

            for (String string : allergiesStrings) {
                if (allergiesLocation.containsKey(string)) {
                    ArrayList<Integer> spots = allergiesLocation.get(string);
                    spots.add(i);
                    allergiesLocation.put(string, spots);
                } else {
                    ArrayList<Integer> spots = new ArrayList<>();
                    spots.add(i);
                    allergiesLocation.put(string, spots);
                    listOfAllergies.add(string);
                }
            }
        }

        System.out.println("--------------------");

        ArrayList<String> possiblyContains = new ArrayList<>();


        for (String string : listOfAllergies) {

            ArrayList<String> possible = new ArrayList<>();

            System.out.println(string);
                
            ArrayList<Integer> indexes = allergiesLocation.get(string);

            System.out.println(indexes);
            
            for (String string2 : values.get(indexes.get(0)).food) {
                possible.add(string2);
            }

            for (Integer index : indexes) {
                ArrayList<String> local = new ArrayList<>();
                for (String string2 : values.get(index).food) {
                    local.add(string2);
                }

                possible.retainAll(local);
            }

            System.out.println(possible.toString());
            possiblyContains.addAll(possible);
            foodCorrelations.put(string, possible);
        }

        ArrayList<String> noDups = (ArrayList<String>) possiblyContains.stream().distinct().collect(Collectors.toList());

        System.out.println(noDups.toString());
        
        ArrayList<String> allFood = new ArrayList<>();

        for (Entry entry : values) {
            for (String string : entry.food) {
                allFood.add(string);
            }
        }

        allFood.removeAll(noDups);

        System.out.println(allFood);
        System.out.println(allFood.size());

        for (String string : listOfAllergies) {
            System.out.println(string);
            System.out.println(foodCorrelations.get(string).toString());
        }

        ArrayList<String> solution = solve(new Recursion(new ArrayList<>()), listOfAllergies).order;

        HashMap<String, String> x = new HashMap<>();

        for (int i = 0; i < solution.size(); i++) {
            System.out.println("Allergin: " + listOfAllergies.get(i) + " Item: " + solution.get(i));
            x.put(listOfAllergies.get(i), solution.get(i));
        }
        System.out.println(solution.toString());

        Collections.sort(listOfAllergies);

        String acc = "";

        for (String string : listOfAllergies) {
            acc += x.get(string) + ",";
        }

        acc = acc.substring(0, acc.length() - 1);

        System.out.println(acc);
    }

    public static Recursion solve(Recursion rec, ArrayList<String> items) {

        if (items.isEmpty()) {
            return rec;
        } else {

            ArrayList<String> localItems = (ArrayList<String>) items.clone();

            String allergies = localItems.remove(0);

            ArrayList<String> options = filter(rec.order, foodCorrelations.get(allergies));
            
            return process(rec, localItems, options);
        }
    }

    public static Recursion process(Recursion rec, ArrayList<String> items, ArrayList<String> options) {

        if (options.isEmpty()) {
            Recursion localRec = new Recursion(rec.order);
            localRec.state = false;
            return localRec;
        } else {

            ArrayList<String> localOrder = (ArrayList<String>) rec.order.clone();

            localOrder.add(options.remove(0));

            Recursion localRec = new Recursion((ArrayList<String>) localOrder.clone());
            Recursion attempt = solve(localRec, items);

            if (attempt.state) {
                return attempt;
            } else {
                return process(rec, items, options);
            }
        }
    }

    public static ArrayList<String> filter(ArrayList<String> already, ArrayList<String> next) {
        ArrayList<String> filtered = (ArrayList<String>) next.clone();
        filtered.removeAll(already);

        return filtered;
    }

    public static class Recursion {
        boolean state = true; 

        ArrayList<String> order;

        public Recursion(ArrayList<String> order) {
            this.order = order;
        }
    }

    public static class Entry {
        String[] food;
        String[] allergies;

        public Entry(String[] food, String[] allergies) {
            this.food = food;
            this.allergies = allergies;
        }
    }
}
