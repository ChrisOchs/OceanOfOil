package oceanofoil.model.property;

import oceanofoil.model.RailroadBlock;
import oceanofoil.model.property.owner.PropertyOwner;


/**
 *
 * @author 
 */
public class RailProperty extends Property {

    public RailProperty(int id, int municipalityId, PropertyOwner owner) {
        super(id, municipalityId, "Railroad " + id, owner);
    }

    public RailroadBlock createRailroadBlock() {
        RailroadBlock rrB = new RailroadBlock(this);
        worldBlocks.add(rrB);

        return rrB;
    }
}
