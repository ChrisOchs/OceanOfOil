package oceanofoil.model.property;

import oceanofoil.model.property.owner.PropertyOwner;
import oceanofoil.model.worldgenerator.PropertyGenerator;

/**
 *
 * @author Chris
 */
public class MunicipalProperty extends Property {

    public MunicipalProperty(int id, PropertyOwner owner) {
        super(id, id, PropertyGenerator.getNextTownName(), owner);
    }
}
