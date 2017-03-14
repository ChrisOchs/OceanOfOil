package oceanofoil.model.property.owner;

import java.util.ArrayList;
import oceanofoil.model.worldgenerator.NameGenerator;

/**
 *
 * @author 
 */
public class Family implements PropertyOwner {
    
    private String husband;
    private String wife;

    private ArrayList<String> children = new ArrayList<String>();

    private String familyName;

    public Family(String familyName) {
        this.familyName = familyName;

        husband = NameGenerator.getRandomMaleName() + " " + familyName;
        wife = NameGenerator.getRandomFemaleName() + " " + familyName;

        int childCount = (int)(Math.random() * 5);

        for(int c = 0; c < childCount; c++) {
            String firstName = "";

            if(Math.random() < 0.5) {
                firstName = NameGenerator.getRandomMaleName();
            } else {
                firstName = NameGenerator.getRandomFemaleName();
            }

            children.add(firstName + " " + familyName);
        }
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getHusband() {
        return husband;
    }

    public String getWife() {
        return wife;
    }
}
