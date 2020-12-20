import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    static HashMap<Integer, ArrayList<char[][]>> map = new HashMap<>();
    static HashMap<Integer, ArrayList<String>> pieceMap = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
    
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        // System.out.println(data);

        ArrayList<Integer> tileList = new ArrayList<>();

        int tile = -1;
        ArrayList<String> pieceRaw = new ArrayList<>();

        for (String string : data) {
            if (string.contains("Tile")) {
                tile = Integer.parseInt(string.split(" ")[1].replace(":", ""));
                // System.out.println(tile);
            } else if (string.equalsIgnoreCase("")) {
                
                char[][] piece = new char[pieceRaw.get(0).length()][pieceRaw.size()];
                for (int j = 0; j < pieceRaw.size(); j++) {
                    for (int i = 0; i < pieceRaw.get(0).length(); i++) {
                        piece[i][j] = pieceRaw.get(j).charAt(i);
                    }
                }

                pieceRaw = new ArrayList<>();

                ArrayList<char[][]> combinations = new ArrayList<>();


                combinations.add(piece);
                combinations.add(flipPieceHor(piece));
                combinations.add(flipPieceVir(piece));
                combinations.add(flipPieceHor(flipPieceVir(piece)));
                
                char[][] rotate = rotatePiece(piece, 90);

                combinations.add(rotate);
                combinations.add(flipPieceHor(rotate));
                combinations.add(flipPieceVir(rotate));
                combinations.add(flipPieceHor(flipPieceVir(rotate)));

                tileList.add(tile);
                map.put(tile, combinations);

            } else {
                pieceRaw.add(string);
            }
        }


        System.out.println(map.get(tileList.get(0)).size());

        System.out.println(tileList.size());
        int size = (int) Math.sqrt(tileList.size());
        System.out.println(size);

        System.out.println(tileList);

        
        Recursion a = solve(new Recursion(new Entry[size][size]), tileList, size, 0, 0);

        Entry[][] b = a.entry;

        for (Entry[] entries : b) {
            for (Entry entries2 : entries) {
                if (entries2 != null) {
                    System.out.println("X: " + entries2.x + " Y: " + entries2.y + " ID: " + entries2.id + " Index: " + entries2.index);

                    ArrayList<String> drawing = removeBorders(draw(map.get(entries2.id).get(entries2.index)));

                    // for (String string : drawing) {
                        
                    //     System.out.println(string);
                    // }

                    pieceMap.put(entries2.id, drawing);
                }
            }
        }

        long ans = 1;
        ans *= b[0][0].id;
        ans *= b[0][size-1].id;
        ans *= b[size-1][0].id;
        ans *= b[size-1][size-1].id;

        System.out.println(ans);

        ArrayList<String> finalImage = new ArrayList<>();

        ArrayList<ArrayList<String>> yMerged = new ArrayList<>();

        for (int x = 0; x < size; x++) {

            ArrayList<String> yPieces = new ArrayList<>();
            for (int y = 0; y < size; y++) {
                
                // System.out.println(b[x][y].id);
                yPieces.addAll(pieceMap.get(b[x][y].id));
                
            }

            yMerged.add(yPieces);
            
        }

        for (int i = 0; i < yMerged.get(0).size(); i++) {
            
            String line = "";

            for (ArrayList<String> arrayList : yMerged) {
                line += arrayList.get(i);
            }

            finalImage.add(line);
        }

        // for (String arrayList2 : finalImage) {

        //     System.out.println(arrayList2);
        // }

        // ArrayList<String> test = new ArrayList<>();
        // test.add("..................#.");
        // test.add("#....##....##....###");
        // test.add(".#..#..#..#..#..#...");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");
        // test.add("....................");

        // System.out.println(hasSeaMonster(test));


        ArrayList<ArrayList<String>> combinations = new ArrayList<>();

        combinations.add(finalImage);
        combinations.add(flipPieceHor(finalImage));
        combinations.add(flipPieceVir(finalImage));
        combinations.add(flipPieceHor(flipPieceVir(finalImage)));
                
        ArrayList<String> rotate = rotatePiece(finalImage, 90);

        combinations.add(rotate);
        combinations.add(flipPieceHor(rotate));
        combinations.add(flipPieceVir(rotate));
        combinations.add(flipPieceHor(flipPieceVir(rotate)));

        ArrayList<String> selected = new ArrayList<>();

        for (ArrayList<String> arrayList : combinations) {
            if (hasSeaMonster(arrayList)) {
                selected = arrayList;
                // System.out.println(hasSeaMonster(selected));
                break;
            }
        }
        
        // for (String arrayList2 : selected) {

        //     System.out.println(arrayList2);
        // }

        // System.out.println("-----------------");

        ArrayList<String> modified = replaceSeaMonsters(selected);

        for (String arrayList2 : modified) {

            System.out.println(arrayList2);
        }

        System.out.println(countRoughWaters(modified));
    }

    public static int countRoughWaters(ArrayList<String> image) {
        int acc = 0;


        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.size(); x++) {
                if (image.get(y).charAt(x) == '#') {
                    acc++;
                }
            }
        }

        return acc;
    }

    public static boolean hasSeaMonster(ArrayList<String> image) {
        
        char[][] split = new char[image.size()][image.size()];


        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.size(); x++) {
                split[x][y] = image.get(y).charAt(x);
                
            }
        }

        // System.out.println(Arrays.deepToString(split));

        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.size(); x++) {
            
                if (split[x][y] == '#') {
                    // System.out.println("#: X: " + x + " Y: " + y);
                    ArrayList<int[]> locations = generateChecks(x, y);

                    boolean valid = true;
                    for (int[] is : locations) {
                        // System.out.println("location-to-check: X: " + is[0] + " Y: " + is[1]);
                        try {
                            if (split[is[0]][is[1]] != '#') {
                                valid = false;
                                break;
                            }
                        } catch (Exception e) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static ArrayList<String> replaceSeaMonsters(ArrayList<String> image) {
        
        char[][] split = new char[image.size()][image.size()];


        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.size(); x++) {
                split[x][y] = image.get(y).charAt(x);
                
            }
        }

        // System.out.println(Arrays.deepToString(split));
        char[][] newMap = split.clone();

        for (int y = 0; y < image.size(); y++) {
            for (int x = 0; x < image.size(); x++) {
            
                if (split[x][y] == '#') {
                    // System.out.println("#: X: " + x + " Y: " + y);
                    ArrayList<int[]> locations = generateChecks(x, y);

                    boolean valid = true;
                    for (int[] is : locations) {
                        // System.out.println("location-to-check: X: " + is[0] + " Y: " + is[1]);
                        try {
                            if (split[is[0]][is[1]] != '#') {
                                valid = false;
                                break;
                            }
                        } catch (Exception e) {
                            valid = false;
                            break;
                        }
                    }

                    if (valid) {
                        for (int[] is : locations) {
                            newMap[is[0]][is[1]] = 'O';
                        }
                    }
                }
            }
        }

        return draw(newMap);
    }

    public static ArrayList<int[]> generateChecks(int x, int y) {
        
        ArrayList<int[]> seaMonster = new ArrayList<>();
        seaMonster.add(new int[] {x,y});
        seaMonster.add(new int[] {x + 1, y + 1});
        seaMonster.add(new int[] {x + 4, y + 1});
        seaMonster.add(new int[] {x + 5, y});
        seaMonster.add(new int[] {x + 6, y});
        seaMonster.add(new int[] {x + 7, y + 1});
        seaMonster.add(new int[] {x + 10, y + 1});
        seaMonster.add(new int[] {x + 11, y});
        seaMonster.add(new int[] {x + 12, y});
        seaMonster.add(new int[] {x + 13, y + 1});
        seaMonster.add(new int[] {x + 16, y + 1});
        seaMonster.add(new int[] {x + 17, y});
        seaMonster.add(new int[] {x + 18, y});
        seaMonster.add(new int[] {x + 18, y - 1});
        seaMonster.add(new int[] {x + 19, y});

        return seaMonster;
    }

    public static ArrayList<String> removeBorders(ArrayList<String> piece) {
        ArrayList<String> removedBorders = new ArrayList<>();

        for (int i = 0; i < piece.size(); i++) {
            if (!(i == 0 || i == piece.size() - 1)) {
                removedBorders.add(piece.get(i).substring(1,piece.get(i).length() -1));
            }
        }

        return removedBorders;
    }

    public static Recursion solve(Recursion rec, ArrayList<Integer> tileList, int size, int x, int y) {
        
        if (tileList.isEmpty()) {
            return rec;
        } else {
            ArrayList<Entry> aaa = (filter(rec.entry, generateNext(tileList, x, y, size), x, y));

            
            int nextX;
            int nextY;

            if (x < size-1) {
                nextX = x + 1;
                nextY = y;
            } else {
                nextX = 0;
                nextY = y + 1;
            }
            
            return process(rec, tileList, aaa, size, nextX, nextY);
        }
        
    }

    public static Recursion process(Recursion rec, ArrayList<Integer> tileList, ArrayList<Entry> next, int size, int x, int y) {


        if (next.isEmpty()) {
            Recursion localRec = new Recursion(rec.entry);
            localRec.state = false;
            return localRec;
        } else {
            ArrayList<Integer> localTileList = (ArrayList<Integer>) tileList.clone();
            Entry currentEntry = next.remove(0);
            localTileList.remove(localTileList.indexOf(currentEntry.id));

            Entry[][] spot = new Entry[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Entry e = rec.entry[i][j];
                    if (e != null) {
                        Entry eLocal = new Entry(e.x, e.y, e.id, e.index);
                        spot[i][j] = eLocal;
                    }
                }
            }

            spot[currentEntry.x][currentEntry.y] = currentEntry;


            Recursion localRec = new Recursion(spot);
            Recursion attempt = solve(localRec, localTileList, size, x, y);

            if (attempt.state) {
                return attempt;
            } else {
                return process(rec, tileList, next, size, x, y);
            }
        }

    }

    

    public static ArrayList<Entry> filter(Entry[][] acc, ArrayList<Entry> next, int x, int y) {
        ArrayList<Entry> local = new ArrayList<>();



        if (x == -1) {
            return next;
        }

        for (Entry entry : next) {


            boolean bool = true;
            if (x == 0 && y == 0) {
                bool = true;
            } else if (x == 0) {
                bool = compareTops(acc[x][y - 1], entry);
            } else if (y == 0) {
                bool = compareSides(acc[x - 1][y], entry);
            } else {
                bool = compareSides(acc[x - 1][y], entry) && compareTops(acc[x][y - 1], entry);
            }

            if (bool) {
                local.add(entry);
            }
        }

        return local;
    }

    public static ArrayList<Entry> generateNext(ArrayList<Integer> tileList, int x, int y, int size) {

        ArrayList<Entry> aaa = new ArrayList<>();

        int localX = x;
        int localY = y;

        for (Integer integer : tileList) {
            ArrayList<char[][]> bbb = map.get(integer);
            for (int i = 0; i < bbb.size(); i++) {
                Entry e = new Entry(localX, localY, integer, i);
                // System.out.println("X: " + e.x + " Y: " + e.y + " ID: " + e.id + " Index: " + e.index);
            
                aaa.add(e);
            }
            
        }

        return aaa;
    }


    public static boolean compareSides(char[][] piece1, char[][] piece2) {
        boolean result = true;

        int side = piece1.length - 1;

        for (int i = 0; i < piece1.length; i++) {
            if (piece1[side][i] != piece2[0][i]) {
                result = false;
            }
        }

        return result;

    }

    public static boolean compareTops(char[][] piece1, char[][] piece2) {
        boolean result = true;

        int bottom = piece1.length - 1;

        for (int i = 0; i < piece1.length; i++) {
            if (piece1[i][bottom] != piece2[i][0]) {
                result = false;
            }
        }

        return result;

    }

    public static boolean compareSides(Entry e1, Entry e2) {
        boolean result = true;
        char[][] piece1 = map.get(e1.id).get(e1.index);
        char[][] piece2 = map.get(e2.id).get(e2.index);

        int side = piece1.length - 1;

        for (int i = 0; i < piece1.length; i++) {
            if (piece1[side][i] != piece2[0][i]) {
                result = false;
            }
        }

        return result;

    }

    public static boolean compareTops(Entry e1, Entry e2) {
        boolean result = true;
        char[][] piece1 = map.get(e1.id).get(e1.index);
        char[][] piece2 = map.get(e2.id).get(e2.index);

        int bottom = piece1.length - 1;

        for (int i = 0; i < piece1.length; i++) {
            if (piece1[i][bottom] != piece2[i][0]) {
                result = false;
            }
        }

        return result;

    }

    public static char[][] flipPieceHor(char[][] original) {
        char[][] rotated = new char[original.length][original.length];

        for (int y = 0; y < rotated.length; y++) {
            for (int x = 0; x < rotated.length; x++) {
                int targetY = rotated.length - 1 - y;

                rotated[x][targetY] = original[x][y];
            }
        }
        
        return rotated;
    }

    public static ArrayList<String> flipPieceHor(ArrayList<String> original) {

        char[][] initial = new char[original.size()][original.size()];

        for (int y = 0; y < original.size(); y++) {
            for (int x = 0; x < original.size(); x++) {
                initial[x][y] = original.get(y).charAt(x); 
            }
        }

        char[][] rotated = flipPieceHor(initial);

        ArrayList<String> rotatedFinal = draw(rotated);
        
        return rotatedFinal;
    }

    public static char[][] flipPieceVir(char[][] original) {
        char[][] rotated = new char[original.length][original.length];

        for (int y = 0; y < rotated.length; y++) {
            for (int x = 0; x < rotated.length; x++) {
                int targetX = rotated.length - 1 - x;

                rotated[targetX][y] = original[x][y];
            }
        }
        
        return rotated;
    }

    public static ArrayList<String> flipPieceVir(ArrayList<String> original) {

        char[][] initial = new char[original.size()][original.size()];

        for (int y = 0; y < original.size(); y++) {
            for (int x = 0; x < original.size(); x++) {
                initial[x][y] = original.get(y).charAt(x); 
            }
        }

        char[][] rotated = flipPieceVir(initial);

        ArrayList<String> rotatedFinal = draw(rotated);
        
        return rotatedFinal;
    }

    public static char[][] rotatePiece(char[][] original, int degrees) {
        char[][] copy = original.clone();

        int amount = degrees / 90;
        

        for (int i = 0; i < amount; i++) {

            char[][] rotated = new char[original.length][original.length];
            
            for (int y = 0; y < rotated.length; y++) {
                for (int x = 0; x < rotated.length; x++) {

                    int x2 = y;
                    int y2 = rotated.length - 1 - x;

                    rotated[x][y] = copy[x2][y2];
                }
            }

            copy = rotated.clone();

        }

        return copy;
    }

    public static ArrayList<String> rotatePiece(ArrayList<String> original, int degrees) {

        char[][] initial = new char[original.size()][original.size()];

        for (int y = 0; y < original.size(); y++) {
            for (int x = 0; x < original.size(); x++) {
                initial[x][y] = original.get(y).charAt(x); 
            }
        }

        char[][] rotated = rotatePiece(initial, degrees);

        ArrayList<String> rotatedFinal = draw(rotated);
        
        return rotatedFinal;
    }

    public static ArrayList<String> draw(char[][] a) {
            
        ArrayList<String> piece = new ArrayList<>();
        for (int y = 0; y < a.length; y++) {
            String b = "";
            for (int x = 0; x < a.length; x++) {
                b += String.valueOf(a[x][y]);
            }
            piece.add(b);
        }

        return piece;
    }

    public static class Recursion {
        boolean state = true;

        Entry[][] entry;

        public Recursion(Entry[][] entry) {
            this.entry = entry;
        }
        
    }

    public static class Entry {
        int x;
        int y;
        int index;
        int id;

        public Entry(int x, int y, int id, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
            this.id = id;
        }
    }
}
