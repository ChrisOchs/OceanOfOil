
package oceanofoil.model.worldgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import oceanofoil.Main;

/**
 *
 * @author 
 */
public class NameGenerator {

    private static NameGenerator nameGenerator = new NameGenerator();

    private static NameGenerator getNameGenerator() {
        return nameGenerator;
    }

    private ArrayList<String> maleFirstNames = new ArrayList<String>();
    private ArrayList<String> femaleFirstNames = new ArrayList<String>();
    private ArrayList<String> lastNames = new ArrayList<String>();

    private NameGenerator() {
        try {
            InputStream is = Main.class.getResourceAsStream("/worldgenerationdata/malefirstnames.txt");
            Scanner scanner = new Scanner(is);

            while (scanner.hasNext()) {
                maleFirstNames.add(scanner.nextLine());
            }

            scanner.close();
            is.close();

            is = Main.class.getResourceAsStream("/worldgenerationdata/femalefirstnames.txt");
            scanner = new Scanner(is);
            
            while (scanner.hasNext()) {
                femaleFirstNames.add(scanner.nextLine());
            }

            scanner.close();
            is.close();

            is = Main.class.getResourceAsStream("/worldgenerationdata/lastnames.txt");
            scanner = new Scanner(is);

            while (scanner.hasNext()) {
                lastNames.add(scanner.nextLine());
            }

            scanner.close();
            is.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(-2);
        }
    }

    public static String getRandomFamilyName() {
        return nameGenerator.lastNames.get((int)(Math.random() * nameGenerator.lastNames.size()));
    }

    public static String getRandomMaleName() {
        return nameGenerator.maleFirstNames.get((int)(Math.random() * nameGenerator.maleFirstNames.size()));
    }
    
    public static String getRandomFemaleName() {
        return nameGenerator.femaleFirstNames.get((int)(Math.random() * nameGenerator.femaleFirstNames.size()));
    }
}
