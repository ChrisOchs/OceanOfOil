
package oceanofoil.model.worldgenerator;

import java.util.ArrayList;
import oceanofoil.model.property.owner.Family;

/**
 *
 * @author Den
 */
public class PropertyGenerator {
    private static String [] part1 = {"Coyote", "Colby", "Death", "Oatman", "Central", "Forgotten", "Goodsprings", "Silverton", "Calico"};
    private static String [] part2 = {"Hills", "Gulch", "City"};

    private static ArrayList<String> usedCityNames  = new ArrayList<String>();

    public static String getNextTownName() {
        String townName = "";

        do {
            townName = part1[(int)(Math.random() * part1.length)] + " " + part2[(int)(Math.random() * part2.length)];
        } while(usedCityNames.contains(townName));

        return townName;
    }

    public static Family getNextFamily() {
        return new Family(NameGenerator.getRandomFamilyName());
    }
}
