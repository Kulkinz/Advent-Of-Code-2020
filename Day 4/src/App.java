import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Vector<String> data = new Vector<>();
        Vector<String> passport = new Vector<>();

        File file = new File("data.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            data.add(sc.nextLine());
        }
        sc.close();

        data.add("");

        String split = "";
        for (String string : data) {
            if (string.equalsIgnoreCase("")) {
                passport.add(split);
                split = "";
            } else {
                split = split + " " + string;
            }
        }
        int acc = 0;

        for (String string : passport) {
            String[] params = string.trim().split(" ");
            System.out.println(Arrays.toString(params));
            int valid = 0;

            for (String string2 : params) {
                String x =  string2.split(":")[1];
                switch (string2.substring(0,3)) {
                    case "byr":
                        if (Integer.parseInt(x) >= 1920 && Integer.parseInt(x) <= 2002) {
                                valid++;
                                System.out.println("1");
                        }
                        break;

                    case "iyr":
                        if (Integer.parseInt(x) >= 2010 && Integer.parseInt(x) <= 2020) {
                            valid++;
                            System.out.println("2");
                        }
                        
                        break;

                    case "eyr":
                        if (Integer.parseInt(x) >= 2020 && Integer.parseInt(x) <= 2030) {
                            valid++;
                            System.out.println("3");
                        }
                        break;

                    case "hgt":
                        String unit = x.substring(x.length() - 2);
                        String number = x.substring(0,x.length() - 2);

                        System.out.println(number);
                        if (unit.equalsIgnoreCase("in")) {
                            if (Integer.parseInt(number) >= 59 && Integer.parseInt(number) <= 76) {
                                valid++;
                                System.out.println("4");
                            }
                        } else if (unit.equalsIgnoreCase("cm")) {
                            if (Integer.parseInt(number) >= 150 && Integer.parseInt(number) <= 193) {
                                valid++;
                                System.out.println("4");
                            }
                        }
                        break;

                    case "hcl":
                        if (x.startsWith("#")) {
                            if (x.replaceAll("[^a-f0-9]", "").length() == 6) {
                                valid++;
                                System.out.println("5");
                            }
                        }
                        break;

                    case "ecl":
                        if (x.contains("amb") || x.contains("blu") || x.contains("brn") || x.contains("gry") || x.contains("grn") || x.contains("hzl") || x.contains("oth")) {
                            valid++;
                            System.out.println("6");
                        }
                        break;

                    case "pid":
                        if (x.length() == 9) {
                            valid++;
                            System.out.println("7");
                        }
                        break;

                    default:
                        break;
                }
            }
            System.out.println(valid);
            if (valid == 7) {
                acc++;
            }
        }

        // for (String string : passport) {
        //     if (string.contains("byr") && string.contains("iyr") && string.contains("eyr") && string.contains("hgt") && 
        //     string.contains("hcl") && string.contains("ecl") && string.contains("pid")) {
        //         acc++;
        //     }
        // }

        System.out.println(acc);
    }
}
