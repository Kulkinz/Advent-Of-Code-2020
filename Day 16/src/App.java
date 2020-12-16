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

        ArrayList<Entry> fields = new ArrayList<>();
        String[] ticket = {};
        ArrayList<String[]> otherTickets = new ArrayList<>();

        int space = 0;
        for (String string : data) {
            if (string.equalsIgnoreCase("")) {
                space++;
            } else if (space == 0) {
                String field = string.split(": ")[0];
                String range1 = string.split(": ")[1].split(" or ")[0];
                String range2 = string.split(": ")[1].split(" or ")[1];

                Entry newEntry = new Entry(field, 
                                            Integer.parseInt(range1.split("-")[0]),
                                            Integer.parseInt(range1.split("-")[1]),
                                            Integer.parseInt(range2.split("-")[0]),
                                            Integer.parseInt(range2.split("-")[1]));
                
                fields.add(newEntry);
            } else if (space == 1) {
                if (!string.equalsIgnoreCase("your ticket:")) {
                    ticket = string.split(",");
                }
            } else {
                if (!string.equalsIgnoreCase("nearby tickets:")) {
                    otherTickets.add(string.split(","));
                }
            }
        }

        System.out.println(Arrays.deepToString(fields.toArray()));
        System.out.println(Arrays.toString(ticket));
        System.out.println(Arrays.deepToString(otherTickets.toArray()));

        ArrayList<Integer> invalid = new ArrayList<>();

        for (String[] strings : otherTickets) {
            for (String string : strings) {
                int x = Integer.parseInt(string);

                if (!checkValid(fields, x)) {
                    invalid.add(x);
                }
            }
        }

        int acc = 0;

        for (Integer integer : invalid) {
            acc += integer;
        }
        System.out.println(invalid);

        System.out.println(acc);
    }

    public static boolean checkValid(ArrayList<Entry> fields, int value) {
        boolean valid = false;
        for (Entry entry : fields) {
            // System.out.println((entry.range1Low <= value && entry.range1High >= value));
            // System.out.println((entry.range2Low <= value && entry.range2High >= value));
            if ((entry.range1Low <= value && entry.range1High >= value) || (entry.range2Low <= value && entry.range2High >= value)) {
                valid = true;
            }
        }

        return valid;
    }

    public static class Entry {
        String field;
        int range1Low;
        int range1High;
        int range2Low;
        int range2High;

        public Entry(String field, int range1Low, int range1High, int range2Low, int range2High) {
            this.field = field;
            this.range1Low = range1Low;
            this.range1High = range1High;
            this.range2Low = range2Low;
            this.range2High = range2High;
        }
    }
}
