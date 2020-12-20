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
    
    public static void main(String[] args) throws Exception {


        // char[][] test = {{'1','4','7'},{'2','5','8'},{'3','6','9'}};
        // System.out.println(test[0][2]);

        // System.out.println(Arrays.deepToString(test));
        // System.out.println(Arrays.deepToString(rotatePiece(test, 360)));
        // System.out.println(Arrays.deepToString(flipPieceHor(test)));
        // System.out.println(Arrays.deepToString(flipPieceVir(test)));
    

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

                // Entry e = new Entry(tile, piece, rotatePiece(piece, 90), rotatePiece(piece, 180), rotatePiece(piece, 270));

                // map.put(tile, e);

                ArrayList<char[][]> combinations = new ArrayList<>();

                for (int i = 1; i <= 4; i++) {
                    char[][] rotate = rotatePiece(piece, i * 90);

                    combinations.add(rotate);
                    combinations.add(flipPieceHor(rotate));
                    combinations.add(flipPieceVir(rotate));
                }

                tileList.add(tile);
                map.put(tile, combinations);

                // System.out.println(Arrays.deepToString(piece));
            } else {
                pieceRaw.add(string);
            }
        }

        System.out.println(map.get(tileList.get(0)).size());

        // Entry e = map.get(tileList.get(0));

        // System.out.println(e.tile);
        // System.out.println(Arrays.deepToString(e.or1));
        // System.out.println(Arrays.deepToString(e.or2));
        // System.out.println(Arrays.deepToString(e.or3));
        // System.out.println(Arrays.deepToString(e.or4));

        int size = (int) Math.sqrt(tileList.size());
        System.out.println(size);
        solve(tileList, new Entry[size][size], size, -1, 0);
        
    }

    public static Entry[][] solve(ArrayList<Integer> tileList, ArrayList<ArrayList<Entry>> worklist, Entry[][] acc, int size, int x, int y) {

        if (tileList.isEmpty()) {
            return acc;
        } else {
            ArrayList<Entry> aaa = (filter(acc, generateNext(tileList, x, y, size), x, y));

            for (Entry entry : aaa) {
                System.out.println("X: " + entry.x + " Y: " + entry.y + " ID: " + entry.id + " Index: " + entry.index);
            }

            return process(aaa);
        }
    }

    public static Entry[][] process(ArrayList<Entry> next) {
        

    }

    public static ArrayList<Entry> filter(Entry[][] acc, ArrayList<Entry> next, int x, int y) {
        ArrayList<Entry> local = new ArrayList<>();

        if (x == -1) {
            return next;
        }

        for (Entry entry : next) {
            boolean bool = true;
            if (x == 0) {
                bool = compareSides(acc[x][y], entry);
            } else if (y == 0) {
                bool = compareTops(acc[x][y], entry);
            } else {
                bool = compareSides(acc[x][y], entry) && compareTops(acc[x][y], entry);
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

        if (localX < size - 1) {
            localX++;
        } else {
            localX = 0;
            localY++;
        }

        for (Integer integer : tileList) {
            ArrayList<char[][]> bbb = map.get(integer);
            for (int i = 0; i < bbb.size(); i++) {
                Entry e = new Entry(localX, localY, integer, i);
                aaa.add(e);
            }
            
        }

        return aaa;
    }




    // for (int tileNum : tileList) {
    //     for (char[][] c : map.get(tileNum)) {
            
    //         int size = (int) Math.sqrt(tileList.size());
    //         int[][] coords = new int[size][size];

    //         coords[0][0] = tileNum;

    //         ArrayList<Integer> localTileList = (ArrayList<Integer>) tileList.clone();
    //         localTileList.remove(tileNum);

    //         for (int y = 0; y < args.length; y++) {

    //             for (int x = 0; x < args.length; x++) {
                    
    //                 for (int localTile : localTileList) {
                        
    //                     for (char[][] c2 : map.get(localTile)) {
    //                         if (y == 0 && x == 0) {
    //                             break;
    //                         }

    //                         if (y == 0) {
    //                             compareSides(map.get(coords[x-1][y]), c2);
    //                         } else {
                            
    //                         }
                            
    //                         if (x == 0) {
                            
    //                         } else {
                            
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
    
    // int size = (int) Math.sqrt(tileList.size());
    // int[][] coords = new int[size][size];

    // ArrayList<Integer> localTileList = (ArrayList<Integer>) tileList.clone();

    // for (int y = 0; y < size; y++) {
    //     for (int x = 0; x < size; x++) {
    //         int temp = localTileList.size();
    //         int currentTile = 0;
    //         for (int i = 0; i < temp; i++) {
    //             if (y == 0) {
                    
    //             } else {

    //             }

    //             if (x == 0) {

    //             } else {

    //             }
    //         }
            
    //     }
    // }

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
            if (piece1[i][bottom] != piece2[i][bottom]) {
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

    public static char[][] rotatePiece(char[][] original, int degrees) {
        char[][] copy = original.clone();

        // System.out.println(Arrays.deepToString(original));

        int amount = degrees / 90;
        
        // System.out.println(copy[0][2]);

        for (int i = 0; i < amount; i++) {

            char[][] rotated = new char[original.length][original.length];
            
            for (int y = 0; y < rotated.length; y++) {
                for (int x = 0; x < rotated.length; x++) {

                    int x2 = y;
                    int y2 = rotated.length - 1 - x;

                    // System.out.println("x1: " + x + " y2: " + y + " point1: " + copy[x][y]);
                    // System.out.println("x2: " + x2 + " y2: " + y2 + " point2: " + copy[x2][y2]);

                    rotated[x][y] = copy[x2][y2];
                }
            }

            // System.out.println(Arrays.deepToString(rotated));

            copy = rotated.clone();

            // System.out.println(Arrays.deepToString(copy));

        }

        return copy;
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
