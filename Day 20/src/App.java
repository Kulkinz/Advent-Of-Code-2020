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

                // System.out.println(Arrays.deepToString(piece));
            } else {
                pieceRaw.add(string);
            }
        }


        // ArrayList<String> solution = new ArrayList<>();

        
        // file = new File("find.txt");
        // sc = new Scanner(file);

        // while (sc.hasNextLine()) {
        //     solution.add(sc.nextLine());
        // }
        // sc.close();



        // for (int i = 0; i < map.get(1171).size(); i++) {
        //     char[][] testing = map.get(1171).get(i);
        //     boolean test = true;
        //     for (int x = 0; x < solution.size(); x++) {
        //         for (int y = 0; y < solution.size(); y++) {
        //             if (testing[x][y] != solution.get(y).charAt(x)) {
        //                 test = false;
        //             }
        //         }
        //     }

        //     if (test) {
        //         System.out.println(i);
        //         System.out.println(Arrays.deepToString(testing));
        //         System.out.println(solution);
        //     }
        // }





        // System.out.println(compareSides(map.get(1951).get(5), map.get(2311).get(10)));
        // System.out.println(compareSides(map.get(2311).get(5), map.get(3079).get(9)));
        // System.out.println(compareSides(map.get(2729).get(10), map.get(1427).get(5)));
        // System.out.println(compareSides(map.get(1427).get(5), map.get(2473).get(1)));
        // System.out.println(compareSides(map.get(2971).get(5), map.get(1489).get(5)));
        // System.out.println(compareSides(map.get(1489).get(5), map.get(1171).get(11)));



        // System.out.println(compareTops(map.get(1951).get(5), map.get(2729).get(5)));
        // System.out.println(compareTops(map.get(2729).get(5), map.get(2971).get(5)));
        // System.out.println(compareTops(map.get(2311).get(5), map.get(1427).get(5)));
        // System.out.println(compareTops(map.get(1427).get(5), map.get(1489).get(5)));
        // System.out.println(compareTops(map.get(3079).get(9), map.get(2473).get(1)));


        
        // System.out.println("-----------------");
        // draw(map.get(2473).get(8));
        // System.out.println("");
        // draw(map.get(1171).get(11));
        // System.out.println(compareTops(map.get(2473).get(8), map.get(1171).get(11)));
        // System.out.println("-----------------");

        






        System.out.println(map.get(tileList.get(0)).size());

        // Entry e = map.get(tileList.get(0));

        // System.out.println(e.tile);
        // System.out.println(Arrays.deepToString(e.or1));
        // System.out.println(Arrays.deepToString(e.or2));
        // System.out.println(Arrays.deepToString(e.or3));
        // System.out.println(Arrays.deepToString(e.or4));

        System.out.println(tileList.size());
        int size = (int) Math.sqrt(tileList.size());
        System.out.println(size);

        // generateNext(tileList, x, y, size)

        System.out.println(tileList);

        // for (Integer integer : tileList) {
        //     for (char[][] piece : map.get(integer)) {
        //         System.out.println(Arrays.deepToString(piece));
        //     }
        // }


        
        Recursion a = solve(new Recursion(new Entry[size][size]), tileList, size, 0, 0);

        Entry[][] b = a.entry;

        for (Entry[] entries : b) {
            for (Entry entries2 : entries) {
                if (entries2 != null) {
                    System.out.println("X: " + entries2.x + " Y: " + entries2.y + " ID: " + entries2.id + " Index: " + entries2.index);
                }
            }
        }

        long ans = 1;
        ans *= b[0][0].id;
        ans *= b[0][size-1].id;
        ans *= b[size-1][0].id;
        ans *= b[size-1][size-1].id;

        System.out.println(ans);

        
        
    }

    static int abc = 0;

    public static Recursion solve(Recursion rec, ArrayList<Integer> tileList, int size, int x, int y) {
        
        if (tileList.isEmpty()) {
            return rec;
        } else {
            // System.out.println("X: " + x);
            // System.out.println("Y: " + y);
            ArrayList<Entry> aaa = (filter(rec.entry, generateNext(tileList, x, y, size), x, y));

            for (Entry entry : aaa) {
                // System.out.println("X: " + entry.x + " Y: " + entry.y + " ID: " + entry.id + " Index: " + entry.index);
            }

            
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
            // return new Recursion(new Entry[0][0]);
        }
        
    }

    public static Recursion process(Recursion rec, ArrayList<Integer> tileList, ArrayList<Entry> next, int size, int x, int y) {

        

        // int acc = 0;

        // Entry[][] b = rec.entry;

        // for (Entry[] entries : b) {
        //     for (Entry entries2 : entries) {
        //         if (entries2 != null) {
        //             acc++;
        //         }
        //     }
        // }

        // if (acc == 13) {
        //     System.out.println("Debug -----------------------------------------------------------------------------------");

        //     System.out.println("Tilelist: " + tileList);
    
        //     System.out.println("Size: " + size);
        //     System.out.println("X: " +x);
        //     System.out.println("Y: " +y);
    
        //     for (Entry entry : next) {
        //         System.out.println("X: " + entry.x + " Y: " + entry.y + " ID: " + entry.id + " Index: " + entry.index);
        //     }
    
        //     System.out.println("rec: -----------------------");
    
        //     for (Entry[] entry : rec.entry) {
        //         for (Entry entry2 : entry) {
        //             if (entry2 != null) {
        //                 System.out.println("X: " + entry2.x + " Y: " + entry2.y + " ID: " + entry2.id + " Index: " + entry2.index);
        //             }
        //         }
        //     }
            
        //     System.out.println("End Debug -----------------------------------------------------------------------------------");

            
        //     System.out.println(abc);
        //     return new Recursion(new Entry[0][0]);
        // } else {
        //     abc++;
        // }


        // if (abc > 10) {
        //     return new Recursion(new Entry[0][0]);

        // } else {
        //     abc++;
        // }

        // return new Recursion(new Entry[0][0]);

        if (next.isEmpty()) {
            Recursion localRec = new Recursion(rec.entry);
            localRec.state = false;
            return localRec;
        } else {
            ArrayList<Integer> localTileList = (ArrayList<Integer>) tileList.clone();
            Entry currentEntry = next.remove(0);
            localTileList.remove(localTileList.indexOf(currentEntry.id));

            // System.out.println("X: " + currentEntry.x + " Y: " + currentEntry.y + " ID: " + currentEntry.id + " Index: " + currentEntry.index);
            // System.out.println(localTileList);
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
            // Entry[][] spot = rec.entry.clone();

            // System.out.println(Arrays.deepToString(rec.entry));
            // System.out.println(currentEntry.x);
            spot[currentEntry.x][currentEntry.y] = currentEntry;
            // System.out.println(Arrays.deepToString(spot));

            // System.out.println("---------------------------------------------------- updated");
            
            // for (Entry[] entries : spot) {
            //     for (Entry entries2 : entries) {
            //         System.out.println("X: " + entries2.x + " Y: " + entries2.y + " ID: " + entries2.id + " Index: " + entries2.index);
            //     }
            // }

            // System.out.println("---------------------------------------------------- before");

            // for (Entry[] entries : rec.entry) {
            //     for (Entry entries2 : entries) {
            //         System.out.println("X: " + entries2.x + " Y: " + entries2.y + " ID: " + entries2.id + " Index: " + entries2.index);
            //     }
            // }


            Recursion localRec = new Recursion(spot);
            Recursion attempt = solve(localRec, localTileList, size, x, y);

            if (attempt.state) {
                return attempt;
            } else {
                // System.out.println("EMPTY  !!!!!!!!!!!!!!!!!!!!!!");
                return process(rec, tileList, next, size, x, y);
            }
        }

    }

    
    // ArrayList<Entry> aaa = (filter(rec.entry, generateNext(tileList, x, y, size), x, y));

    public static ArrayList<Entry> filter(Entry[][] acc, ArrayList<Entry> next, int x, int y) {
        ArrayList<Entry> local = new ArrayList<>();

        // System.out.println("Filter::: -----------");


        if (x == -1) {
            return next;
        }

        for (Entry entry : next) {


            boolean bool = true;
            if (x == 0 && y == 0) {
                bool = true;
            } else if (x == 0) {
                // System.out.println("-----------------");
                // draw(map.get(acc[x][y-1].id).get(acc[x][y-1].index));
                // System.out.println("");
                // draw(map.get(entry.id).get(entry.index));
                bool = compareTops(acc[x][y - 1], entry);
                // System.out.println(bool);
                // System.out.println("-----------------");
            } else if (y == 0) {
                bool = compareSides(acc[x - 1][y], entry);
            } else {
                // System.out.println("-----------------");
                // draw(map.get(acc[x][y-1].id).get(acc[x][y-1].index));
                // System.out.println("");
                // draw(map.get(entry.id).get(entry.index));
                bool = compareSides(acc[x - 1][y], entry) && compareTops(acc[x][y - 1], entry);
                // System.out.println(bool);
                // System.out.println("-----------------");
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

    public static ArrayList<String> draw(char[][] a) {
            
        ArrayList<String> piece = new ArrayList<>();
        for (int y = 0; y < a.length; y++) {
            String b = "";
            for (int x = 0; x < a.length; x++) {
                b += String.valueOf(a[x][y]);
            }
            // System.out.println(b);
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
