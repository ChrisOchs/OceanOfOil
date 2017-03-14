package oceanofoil.model.property.upgrade;

import oceanofoil.model.property.Property;


/**
 *
 * @author 
 */
public class Barn implements BlockUpgrade {
    
    private Property property;

    public Barn(Property p) {
        this.property = p;
    }

    public String upgradeName() {
        return "Barn";
    }
}
