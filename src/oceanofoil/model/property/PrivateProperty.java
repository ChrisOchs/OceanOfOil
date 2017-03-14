package oceanofoil.model.property;

import oceanofoil.model.property.owner.Family;
import oceanofoil.model.property.owner.PropertyOwner;

/**
 *
 * @author 
 */
public class PrivateProperty extends Property {

    public PrivateProperty(int id, int municipalityId, PropertyOwner owner) {
        super(id, municipalityId, (owner instanceof Family) ? ((Family)owner).getFamilyName() + " Tract": "", owner);
    }
}
