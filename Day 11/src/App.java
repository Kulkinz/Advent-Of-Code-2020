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

        int width = data.get(0).length();
        int height = data.size();

        char[][] map = new char[width][height];

        for (int i = 0; i < data.size(); i++) {
            String line = data.get(i);
            char[] values = line.toCharArray();

            for (int j = 0; j < values.length; j++) {
                map[j][i] = values[j];
            }
        }
        
        System.out.println(Arrays.deepToString(map));

        // System.out.println(data);

        char[][] oldMap = map;
        char[][] newMap = generateNext(oldMap);

        // System.out.println(Arrays.deepToString(newMap));
        
        int acc = 1;

        while (!Arrays.deepEquals(oldMap, newMap)) {
            oldMap = newMap.clone();
            
            newMap = generateNext(oldMap);

            // System.out.println(Arrays.deepToString(newMap));
            

            acc++;
        }


        // System.out.println(Arrays.deepEquals(oldMap, newMap));

        System.out.println(count(newMap));

    }

    public static char[][] generateNext(char[][] currentMap) {
        int height = currentMap.length;
        int width = currentMap[0].length;

        char[][] newMap = new char[width][height];


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int x = i;
                int y = j;

                int[] pos = {x,y};

                int[] up = find(pos, currentMap, "up");
                int[] down = find(pos, currentMap, "down");
                int[] left = find(pos, currentMap, "left");
                int[] right = find(pos, currentMap, "right");
                int[] upRight = find(pos, currentMap, "upright");
                int[] downRight = find(pos, currentMap, "downright");
                int[] upLeft = find(pos, currentMap, "upleft");
                int[] downLeft = find(pos, currentMap, "downleft");


                char current = currentMap[x][y];
                int acc = 0;

                int[] bad = {-1,-1};

                if (!Arrays.equals(up, bad)) {
                    char spot = currentMap[up[0]][up[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(down, bad)) {
                    char spot = currentMap[down[0]][down[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(left, bad)) {
                    char spot = currentMap[left[0]][left[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(right, bad)) {
                    char spot = currentMap[right[0]][right[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(upLeft, bad)) {
                    char spot = currentMap[upLeft[0]][upLeft[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(upRight, bad)) {
                    char spot = currentMap[upRight[0]][upRight[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(downLeft, bad)) {
                    char spot = currentMap[downLeft[0]][downLeft[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }
                if (!Arrays.equals(downRight, bad)) {
                    char spot = currentMap[downRight[0]][downRight[1]];
                    if (spot == '#') {
                        acc++;
                    }
                }

                // System.out.println("X: " + x + " Y: " + y + " Spot: " + newMap[x][y] + " Acc: " + acc);

                if (current == 'L' && acc == 0) {
                    newMap[x][y] = '#';
                } else if (current == '#' && acc >= 5) {
                    newMap[x][y] = 'L';
                } else {
                    newMap[x][y] = current;
                }
            }
        }

        return newMap;
    }

    public static int[] find(int[] pos, char[][] map, String direction) {
        int[] location = {-1,-1};

        int x = pos[0];
        int y = pos[1];

        int height = map.length - 1;
        int width = map[0].length - 1;

        switch (direction) {
            case "right":
                x++;

                while (x <= width) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x++;
                    }
                }

                break;

            case "left":
                
                x--;

                while (x >= 0) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x--;
                    }
                }

                break;

            case "up":

                y--;

                while (y >= 0) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        y--;
                    }
                }

                break;

            case "down":

                y++;

                while (y <= height) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        y++;
                    }
                }

                break;

            case "upleft":

                x--;
                y--;

                while (x >= 0 && y >= 0) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x--;
                        y--;
                    }
                }
                break;

            case "upright":

                x++;
                y--;

                while (x <= width && y >= 0) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x++;
                        y--;
                    }
                }

                break;

            case "downleft":

                x--;
                y++;

                while (x >= 0 && y <= height) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x--;
                        y++;
                    }
                }

                break;

            case "downright":

                x++;
                y++;

                while (x <= width && y <= height) {
                    if (map[x][y] != '.') {
                        location[0] = x;
                        location[1] = y;
                        break;
                    } else {
                        x++;
                        y++;
                    }
                }

                break;
        
            default:
                break;
        }

        return location;

    }

    public static int count(char[][] currentMap) {
        int acc = 0;

        int height = currentMap.length;
        int width = currentMap[0].length;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                char current = currentMap[i][j];

                if (current == '#') {
                    acc++;
                }
            }
        }

        return acc;
    }
}
