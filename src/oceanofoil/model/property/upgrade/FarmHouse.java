package oceanofoil.model.property.upgrade;

import oceanofoil.model.property.Property;

/**
 *
 * @author Den
 */
public class FarmHouse implements BlockUpgrade {

    private Property property;

    public FarmHouse(Property property) {
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }

    public String upgradeName() {
        return "Farm House";
    }
}
