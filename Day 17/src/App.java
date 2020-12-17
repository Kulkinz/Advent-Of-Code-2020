import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class App {

    static HashMap<ArrayList<Integer>,Character> map = new HashMap<>();
    
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        ArrayList<String> data = new ArrayList<>();

        
        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();


        ArrayList<ArrayList<Integer>> listOfCoords = new ArrayList<>();

        for (int i = 0; i < data.get(0).length(); i++) {
            
            for (int j = 0; j < data.size(); j++) {
                
                ArrayList<Integer> spot = new ArrayList<>();

                spot.add(i);
                spot.add(j);
                spot.add(0);
                spot.add(0);

                char state = data.get(j).charAt(i);

                System.out.println("Spot: " + spot + " State: " + state);

                map.put(spot, state);
                listOfCoords.add(spot);
            }
        }

        System.out.println(listOfCoords);

        for (int i = 0; i < 6; i++) {
            ArrayList<ArrayList<Integer>> localListOfCoords = (ArrayList<ArrayList<Integer>>) listOfCoords.clone();
        
            for (ArrayList<Integer> arrayList : listOfCoords) {
                ArrayList<ArrayList<Integer>> points = getSurrounding(arrayList);
    
                for (ArrayList<Integer> arrayList2 : points) {
                    // System.out.println(arrayList2);
                    // System.out.println(map.containsKey(arrayList2));
                    if (!map.containsKey(arrayList2)) {
                        map.put(arrayList2, '.');
    
                        localListOfCoords.add(arrayList2);
                        // System.out.println(arrayList2);
                    }
                }
            }
    
            listOfCoords = localListOfCoords;
            // localListOfCoords.clear();
    
            HashMap<ArrayList<Integer>,Character> localMap = (HashMap<ArrayList<Integer>,Character>) map.clone();
    
            for (ArrayList<Integer> arrayList : listOfCoords) {
                int x = getValuesAround(getSurrounding(arrayList));
                if (map.get(arrayList) == '#') {
    
                    if (!(x == 2 || x == 3)) {
                        localMap.put(arrayList, '.');
                    }
                } else if (map.get(arrayList) == '.') {
                    if (x == 3) {
                        localMap.put(arrayList, '#');
                    }
                }
            }
    
            map = localMap;
            // localMap.clear();
        }

        int acc = 0;

        // System.out.println(listOfCoords);

        for (ArrayList<Integer> arrayList : listOfCoords) {
            // System.out.println(arrayList);
            // System.out.println(map.get(arrayList));
            if (map.get(arrayList) == '#') {
                acc++;
            }
        }

        System.out.println(data);

        System.out.println(acc);

        // System.out.println(listOfCoords);
    }

    public static int getValuesAround(ArrayList<ArrayList<Integer>> surroundings) {

        int acc = 0;

        for (ArrayList<Integer> arrayList : surroundings) {
            if (map.containsKey(arrayList)) {
                if (map.get(arrayList) == '#') {
                    acc++;
                }
            }
        }

        return acc;

    }

    public static ArrayList<ArrayList<Integer>> getSurrounding(ArrayList<Integer> coord) {
        int x = coord.get(0);
        int y = coord.get(1);
        int z = coord.get(2);
        int w = coord.get(3);

        ArrayList<ArrayList<Integer>> middle = new ArrayList<>();
        ArrayList<ArrayList<Integer>> above = new ArrayList<>();
        ArrayList<ArrayList<Integer>> below = new ArrayList<>();
        ArrayList<ArrayList<Integer>> surroundingMain = new ArrayList<>();
        ArrayList<ArrayList<Integer>> surroundingAbove = new ArrayList<>();
        ArrayList<ArrayList<Integer>> surroundingBelow = new ArrayList<>();
        ArrayList<ArrayList<Integer>> surroundingFinal = new ArrayList<>();

        for (int yTarget = y - 1; yTarget <= y + 1; yTarget++) {
            
            for (int xTarget = x - 1; xTarget <= x + 1; xTarget++) {
                ArrayList<Integer> spot = new ArrayList<>();
                spot.add(xTarget);
                spot.add(yTarget);
                spot.add(z);
                spot.add(w);

                middle.add(spot);
            }
        }

        for (ArrayList<Integer> arrayList : middle) {
            ArrayList<Integer> spot = new ArrayList<>();
            spot.add(arrayList.get(0));
            spot.add(arrayList.get(1));
            spot.add(z + 1);
            spot.add(w);

            above.add(spot);
        }
        
        for (ArrayList<Integer> arrayList : middle) {
            ArrayList<Integer> spot = new ArrayList<>();
            spot.add(arrayList.get(0));
            spot.add(arrayList.get(1));
            spot.add(z - 1);
            spot.add(w);

            below.add(spot);
        }

        surroundingMain.addAll(middle);
        surroundingMain.addAll(above);
        surroundingMain.addAll(below);

        // surroundingMain.remove(coord);

        for (ArrayList<Integer> arrayList : surroundingMain) {
            ArrayList<Integer> spot = new ArrayList<>();
            spot.add(arrayList.get(0));
            spot.add(arrayList.get(1));
            spot.add(arrayList.get(2));
            spot.add(w - 1);

            surroundingBelow.add(spot);
        }

        for (ArrayList<Integer> arrayList : surroundingMain) {
            ArrayList<Integer> spot = new ArrayList<>();
            spot.add(arrayList.get(0));
            spot.add(arrayList.get(1));
            spot.add(arrayList.get(2));
            spot.add(w + 1);

            surroundingAbove.add(spot);
        }

        surroundingFinal.addAll(surroundingMain);
        surroundingFinal.addAll(surroundingAbove);
        surroundingFinal.addAll(surroundingBelow);

        surroundingFinal.remove(coord);

        return surroundingFinal;

    }
}
