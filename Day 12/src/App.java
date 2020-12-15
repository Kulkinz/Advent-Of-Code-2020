import java.io.File;
import java.util.ArrayList;
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

        int[] waypointPosition = {10,1};
        int[] shipPosition = {0,0};

        for (String string : data) {
            char instruction = string.charAt(0);
            int amount = Integer.parseInt(string.substring(1));

            if (instruction == 'F') {
                shipPosition = moveShip(waypointPosition, shipPosition, amount);
            } else {
                waypointPosition = moveWaypoint(waypointPosition, instruction, amount);
            }

            System.out.println("Waypoint X: " + waypointPosition[0] + " Waypoint Y: " + waypointPosition[1]);
            System.out.println("Ship X: " + shipPosition[0] + " Ship Y: " + shipPosition[1]);
        }

        System.out.println(Math.abs(shipPosition[0]) + Math.abs(shipPosition[1]));
    }

    public static int[] moveWaypoint(int[] position, char instruction, int amount) {
        int[] newPosition = position;
        
            
        int prevX;
        int prevY;

        switch (instruction) {

            case 'N':
                
                newPosition[1] += amount;

                break;
            
            case 'S':

                newPosition[1] -= amount;

                break;
        
            case 'E':
                
                newPosition[0] += amount;

                break;
            
            case 'W':

                newPosition[0] -= amount;

                break;
                
            case 'R':

                for (int i = 0; i < amount / 90; i++) {
                    
                    prevX = newPosition[0];
                    prevY = newPosition[1];
            
                    newPosition[0] = prevY;
                    newPosition[1] = -prevX;
                }

                break;
            
            case 'L':

                
                for (int i = 0; i < amount / 90; i++) {

                    prevX = newPosition[0];
                    prevY = newPosition[1];
            
                    newPosition[0] = -prevY;
                    newPosition[1] = prevX;
                }

                break;

            default:
                break;
        }

        return newPosition;
    }

    public static int[] moveShip(int[] waypointPosition, int[] shipPosition, int amount) {
        int[] newShipPosition = shipPosition;

        newShipPosition[0] += waypointPosition[0] * amount;
        newShipPosition[1] += waypointPosition[1] * amount;

        return newShipPosition;
    }
}
