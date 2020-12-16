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

        ArrayList<String[]> valid = new ArrayList<>();

        for (String[] strings : otherTickets) {
            int num = 0;
            for (String string : strings) {
                int x = Integer.parseInt(string);

                if (!checkValid(fields, x)) {
                    num++;
                }
            }

            if (num == 0) {
                valid.add(strings);
            }
        }

        // System.out.println(Arrays.deepToString(valid.toArray()));
        
        ArrayList<ArrayList<String[]>> arrangements = new ArrayList<>();

        for (String[] strings : valid) {

            ArrayList<String[]> arrangement = new ArrayList<>();

            for (int i = 0; i < strings.length; i++) {
                int x = Integer.parseInt(strings[i]);

                String[] comb = getCombinations(fields, x);
                arrangement.add(comb);
            }

            arrangements.add(arrangement);
        }

        for (ArrayList<String[]> arrayList : arrangements) {
            // System.out.println(Arrays.deepToString(arrayList.toArray()));
        }

        ArrayList<ArrayList<String>> commons = new ArrayList<>();

        for (int i = 0; i < ticket.length; i++) {
            commons.add(getCommon(arrangements, i));
        }



        for (ArrayList<String> arrayList : commons) {
            System.out.println(arrayList.toString());
        }

        HashMap<String, Integer> areas = solve(commons);

        int acc = 1;

        acc *= Integer.parseInt(ticket[areas.get("departure location")]);
        acc *= Integer.parseInt(ticket[areas.get("departure station")]);
        acc *= Integer.parseInt(ticket[areas.get("departure platform")]);
        acc *= Integer.parseInt(ticket[areas.get("departure track")]);
        acc *= Integer.parseInt(ticket[areas.get("departure date")]);
        acc *= Integer.parseInt(ticket[areas.get("departure time")]);

        System.out.println(acc);
    }

    public static HashMap<String, Integer> solve(ArrayList<ArrayList<String>> commons) {

        HashMap<String, Integer> areas = new HashMap<>();

        ArrayList<Combo> values = generate(commons, new ArrayList<>());

        for (Combo combo : values) {
            System.out.println(combo.field + " " +  combo.spot);
            areas.put(combo.field, combo.spot);
        }
        return areas;

    }

    public static ArrayList<Combo> generate(ArrayList<ArrayList<String>> commons, ArrayList<Combo> acc) {

        int lowest = getLowest(commons);

        ArrayList<String> values = commons.get(lowest);

        if (values.size() == 1) {
            String current = values.get(0);

            Combo c = new Combo(lowest, current);
            acc.add(c);
            return generate(removeCurrent(commons, current), acc);
        } else {
            // return generate(commons, acc.add(e));
            return acc;
        }

    }

    public static ArrayList<ArrayList<String>> removeCurrent(ArrayList<ArrayList<String>> commons, String current) {
        ArrayList<ArrayList<String>> local = new ArrayList<>();

        for (ArrayList<String> arrayList : commons) {
            ArrayList<String> local2 = new ArrayList<>();

            for (String string : arrayList) {
                if (!string.equalsIgnoreCase(current)) {
                    local2.add(string);
                }
            }

            local.add(local2);
        }

        return local;
    }

    public static int getLowest(ArrayList<ArrayList<String>> commons) {
        int acc = 0;
        int size = Integer.MAX_VALUE;

        for (int i = 0; i < commons.size(); i++) {
            if (commons.get(i).size() < size) {
                acc = i;
                size = commons.get(i).size();
            }
        }

        return acc;
    }

    public static class Combo {
        int spot;
        String field;

        public Combo(int spot, String field) {
            this.spot = spot;
            this.field = field;
        }
    }

    public static String[] getCombinations(ArrayList<Entry> fields, int value) {
        String[] valid = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            if ((fields.get(i).range1Low <= value && fields.get(i).range1High >= value) || (fields.get(i).range2Low <= value && fields.get(i).range2High >= value)) {
                valid[i] = fields.get(i).field;
            } else {
                valid[i] = "";
            }
        }

        return valid;
    }

    public static ArrayList<String> getCommon(ArrayList<ArrayList<String[]>> arrangements, int i) {
        ArrayList<String> common = new ArrayList<>();

        String[] starting = arrangements.remove(0).get(i);
        for (String string : starting) {
            if (!string.equalsIgnoreCase("")) {
                common.add(string);
            }
        }

        for (ArrayList<String[]> arrayList : arrangements) {
            String[] targeted = arrayList.get(i);

            ArrayList<String> local = new ArrayList<>();
            
            for (String string : common) {
                boolean found = false;
                for (String string2 : targeted) {
                    if (string.equalsIgnoreCase(string2)) {
                        found = true;
                    }
                }
                if (found) {
                    local.add(string);
                }
            }

            common = (ArrayList<String>) local.clone();
        }

        return common;
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
