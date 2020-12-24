import java.io.File;
import java.util.ArrayList;
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

        Tile start = new Tile();

        createSurrounding(start);

        // Tile ne1 = start.ne;

        // createSurrounding(ne1);

        // Tile ne2 = ne1.ne;

        // createSurrounding(ne2);

        // Tile se1 = ne2.se;

        // createSurrounding(se1);

        // Tile sw1 = se1.sw;

        // createSurrounding(sw1);

        // Tile w = sw1.w;

        // createSurrounding(w);

        // w.state = true;
        // se1.state = true;

        // start.print();
        // start.printState();

        // ne1.print();
        // ne1.printState();

        // ne2.print();
        // ne2.printState();

        // se1.print();
        // se1.printState();

        // sw1.print();
        // sw1.printState();

        // w.print();
        // w.printState();

        int acc = 0;

        ArrayList<Tile> tiles = new ArrayList<>();

        tiles.add(start);

        for (ArrayList<String> arrayList : instructions) {

            Tile current = start;

            for (String arrayList2 : arrayList) {
                
                switch (arrayList2) {
                    case "ne":
                        
                        current = current.ne;
                        createSurrounding(current);
                        tiles.add(current);

                        break;

                    case "nw":

                        current = current.nw;
                        createSurrounding(current);
                        tiles.add(current);

                        break;

                    case "w":

                        current = current.w;
                        createSurrounding(current);
                        tiles.add(current);

                        break;

                    case "sw":

                        current = current.sw;
                        createSurrounding(current);
                        tiles.add(current);

                        break;
                    
                    case "se":

                        current = current.se;
                        createSurrounding(current);
                        tiles.add(current);

                        break;

                    case "e":

                        current = current.e;
                        createSurrounding(current);
                        tiles.add(current);

                        break;
                
                    default:
                        break;
                }

            }

            current.state = !current.state;

        
        }

        List<Tile> nodups = tiles.stream().distinct().collect(Collectors.toList());

        for (Tile tile : nodups) {
            if (tile.state) {
                acc++;
            }
        }
        System.out.println(acc);
        
    }

    public static void createSurrounding(Tile tile) {

        Tile ne = tile.ne;
        Tile nw = tile.nw;
        Tile w = tile.w;
        Tile sw = tile.sw;
        Tile se = tile.se;
        Tile e = tile.e;

        if (ne == null) {
            ne = new Tile();
        }
        
        if (nw == null) {
            nw = new Tile();
        }

        if (w == null) {
            w = new Tile();
        }

        if (sw == null) {
            sw = new Tile();
        }

        if (se == null) {
            se = new Tile();
        }

        if (e == null) {
            e = new Tile();
        }

        tile.ne = ne;
        tile.nw = nw;
        tile.w = w;
        tile.sw = sw;
        tile.se = se;
        tile.e = e;

        ne.sw = tile;
        ne.se = tile.e;
        ne.w = tile.nw;

        nw.se = tile;
        nw.e = tile.ne;
        nw.sw = tile.w;

        w.e = tile;
        w.ne = tile.nw;
        w.se = tile.sw;

        sw.ne = tile;
        sw.nw = tile.w;
        sw.e = tile.se;

        se.nw = tile;
        se.w = tile.sw;
        se.ne = tile.e;

        e.w = tile;
        e.nw = tile.ne;
        e.sw = tile.se;
    }

    public static class Tile {

        boolean state = false;
        Tile ne = null;
        Tile nw = null;
        Tile w = null;
        Tile sw = null;
        Tile se = null;
        Tile e = null;

        public void print() {
            System.out.println("THIS: " + this + " NE: " + ne + " NW: " + nw + " W: " + w + " SW: " + sw + " SE: " + se + " E: " + e);
        }

        public void printState() {
            System.out.println("THIS: " + state + " NE: " + ne.state + " NW: " + nw.state + " W: " + w.state + " SW: " + sw.state + " SE: " + se.state + " E: " + e.state);
        }
    }
}
