import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class App {

    static HashMap<ArrayList<Integer>, Boolean> grid = new HashMap<>();
    
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


        ArrayList<ArrayList<String>> instructions = new ArrayList<>();

        for (String string : data) {
            
            char[] charArray = string.toCharArray();

            ArrayList<String> instruction = new ArrayList<>();

            char prev = ' ';

            for (char c : charArray) {
                switch (c) {
                    case 'n':
                        prev = 'n';
                        break;

                    case 'e':
                        
                        if (prev == ' ') {
                            instruction.add("e");
                        } else if (prev == 'n') {
                            instruction.add("ne");
                            prev = ' ';
                        } else {
                            instruction.add("se");
                            prev = ' ';
                        }
                        break;

                    case 's':
                        prev = 's';
                        break;
                    
                    case 'w':
                        
                        if (prev == ' ') {
                            instruction.add("w");
                        } else if (prev == 'n') {
                            instruction.add("nw");
                            prev = ' ';
                        } else {
                            instruction.add("sw");
                            prev = ' ';
                        }
                        break;

                    default:
                        break;
                }
            }
            
            // System.out.println(instruction);
            instructions.add(instruction);
        }

        System.out.println(instructions);


        int acc = 0;

        ArrayList<Integer> start = new ArrayList<>(Arrays.asList(new Integer[]{0,0,0}));

        ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();

        // tiles.add(start);

        for (ArrayList<String> arrayList : instructions) {

            ArrayList<Integer> current = (ArrayList<Integer>) start.clone();

            for (String arrayList2 : arrayList) {
                
                switch (arrayList2) {
                    case "ne":
                        
                        current.set(0, current.get(0) + 1);
                        current.set(2, current.get(2) - 1);
                        // tiles.add(current);
                        // grid.put(current, false);

                        // current = (ArrayList<Integer>) current.clone();

                        System.out.println("NE: " + current);

                        break;

                    case "nw":

                        current.set(1, current.get(1) + 1);
                        current.set(2, current.get(2) - 1);
                        // tiles.add(current);
                        // grid.put(current, false);

                        // current = (ArrayList<Integer>) current.clone();

                        System.out.println("NW: " + current);

                        break;

                    case "w":

                        current.set(0, current.get(0) - 1);
                        current.set(1, current.get(1) + 1);
                        // tiles.add(current);
                        // grid.put(current, false);
                        
                        // current = (ArrayList<Integer>) current.clone();

                        System.out.println("W: " + current);

                        break;

                    case "sw":

                        current.set(0, current.get(0) - 1);
                        current.set(2, current.get(2) + 1);
                        // tiles.add(current);
                        // grid.put(current, false);
                        
                        // current = (ArrayList<Integer>) current.clone();
                        
                        System.out.println("SW: " + current);

                        break;
                    
                    case "se":

                        current.set(1, current.get(1) - 1);
                        current.set(2, current.get(2) + 1);
                        // tiles.add(current);
                        // grid.put(current, false);
                        
                        // current = (ArrayList<Integer>) current.clone();

                        System.out.println("SE: " + current);

                        break;

                    case "e":

                        current.set(0, current.get(0) + 1);
                        current.set(1, current.get(1) - 1);
                        // tiles.add(current);
                        // grid.put(current, false);
                        
                        // current = (ArrayList<Integer>) current.clone();
                        
                        System.out.println("E: " + current);

                        break;
                
                    default:
                        break;
                }

            }

            tiles.add(current);
            // grid.put(current, !grid.get(current));

        
        }

        System.out.println(tiles);

        List<ArrayList<Integer>> noDups = tiles.stream().distinct().collect(Collectors.toList());

        System.out.println(noDups);
        

        for (ArrayList<Integer> arrayList : tiles) {


            if (grid.containsKey(arrayList)) {
                boolean test = grid.get(arrayList);
                grid.put(arrayList, !test);
            } else {
                grid.put(arrayList, true);
            }
        }

        ArrayList<ArrayList<Integer>> blackTiles = new ArrayList<>();

        for (ArrayList<Integer> arrayList : noDups) {
            if (grid.get(arrayList)) {
                acc++;
                blackTiles.add(arrayList);
            }
        }

        System.out.println(acc);

        ArrayList<ArrayList<Integer>> nextTiles = blackTiles;

        System.out.println(nextTiles);

        // System.out.println(nextTiles.get(0));

        // System.out.println(surroundings(nextTiles.get(0)));

        for (int i = 0; i < 100; i++) {
            
            nextTiles = generateOutsideTiles(nextTiles);

            // System.out.println(nextTiles);

            // HashMap<ArrayList<Integer>, Boolean> localGrid = (HashMap<ArrayList<Integer>, Boolean>) grid.clone();
            HashMap<ArrayList<Integer>, Boolean> localGrid = new HashMap<>();

            for (ArrayList<Integer> arrayList : nextTiles) {

                int surround = 0;

                for (ArrayList<Integer> arrayList2 : surroundings(arrayList)) {
                    if (grid.containsKey(arrayList2)) {
                        if (grid.get(arrayList2)) {
                            surround++;
                        }
                    }
                }

                // System.out.println(surround + "   " + grid.get(arrayList));

                
                if (grid.get(arrayList)) {
                    if (surround == 0 || surround > 2) {
                        localGrid.put(arrayList, false);
                    } else {
                        localGrid.put(arrayList, true);
                    }
                } else {
                    if (surround == 2) {
                        localGrid.put(arrayList, true);
                    } else {
                        localGrid.put(arrayList, false);
                    }
                }
            }

            grid = (HashMap<ArrayList<Integer>, Boolean>) localGrid.clone();

        }

        acc = 0;

        for (ArrayList<Integer> arrayList : nextTiles) {
            if (grid.get(arrayList)) {
                acc++;
            }
        }

        System.out.println(acc);

        
        
    }

    public static ArrayList<ArrayList<Integer>> generateOutsideTiles(ArrayList<ArrayList<Integer>> tiles) {

        ArrayList<ArrayList<Integer>> next = new ArrayList<>();

        for (ArrayList<Integer> arrayList : tiles) {
            
            next.addAll(surroundings(arrayList));
        }

        ArrayList<ArrayList<Integer>> noDups = (ArrayList<ArrayList<Integer>>) next.stream().distinct().collect(Collectors.toList());

        for (ArrayList<Integer> arrayList : noDups) {
            if (!grid.containsKey(arrayList)) {
                grid.put(arrayList, false);
            }
        }

        return noDups;

    }

    public static ArrayList<ArrayList<Integer>> surroundings(ArrayList<Integer> tile) {

        ArrayList<ArrayList<Integer>> tiles = new ArrayList<>();
        int x = tile.get(0);
        int y = tile.get(1);
        int z = tile.get(2);
        ArrayList<Integer> ne = new ArrayList<>();
        ne.add(x + 1);
        ne.add(y);
        ne.add(z - 1);
        ArrayList<Integer> nw = new ArrayList<>();
        nw.add(x);
        nw.add(y + 1);
        nw.add(z - 1);
        ArrayList<Integer> w = new ArrayList<>();
        w.add(x - 1);
        w.add(y + 1);
        w.add(z);
        ArrayList<Integer> sw = new ArrayList<>();
        sw.add(x - 1);
        sw.add(y);
        sw.add(z + 1);
        ArrayList<Integer> se = new ArrayList<>();
        se.add(x);
        se.add(y - 1);
        se.add(z + 1);
        ArrayList<Integer> e = new ArrayList<>();
        e.add(x + 1);
        e.add(y - 1);
        e.add(z);

        tiles.add(ne);
        tiles.add(nw);
        tiles.add(w);
        tiles.add(sw);
        tiles.add(se);
        tiles.add(e);

        return tiles;
    }
    
}
