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

        HashMap<ArrayList<Integer>, Boolean> grid = new HashMap<>();

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

        int tilesSize = tiles.size();
        int noDupsSize = noDups.size();

        int difference = tilesSize - noDupsSize;

        System.out.println(tilesSize - difference - difference);

        // System.out.println(tiles.size());
        // System.out.println(noDups.size());
        

        // for (ArrayList<Integer> arrayList : tiles) {

            // System.out.println(arrayList);
            // if (grid.get(arrayList)) {
            //     acc++;
            // }
        // }

        // System.out.println(acc);
        
    }
    
}
