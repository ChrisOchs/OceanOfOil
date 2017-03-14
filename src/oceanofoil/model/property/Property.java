/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.model.property;

import java.util.ArrayList;
import java.util.HashSet;
import oceanofoil.model.WorldBlock;
import oceanofoil.model.property.owner.PropertyOwner;

/**
 *
 * @author Den
 */
public abstract class Property {

    private int id;
    private int municipalityId;

    private PropertyOwner owner;
    private String propertyName;

    protected ArrayList<WorldBlock> worldBlocks = new ArrayList<WorldBlock>();

    protected HashSet<Property> neighbors = new HashSet<Property>();

    public Property(int id, int municipalityId, String propertyName, PropertyOwner owner) {
        this.id = id;
        this.municipalityId = municipalityId;
        this.propertyName = propertyName;
        this.owner = owner;
    }

    public int getPropertyId() {
        return id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public WorldBlock createWorldBlock() {
        WorldBlock block = new WorldBlock(this);
        worldBlocks.add(block);
        
        return block;
    }

    public ArrayList<WorldBlock> getWorldBlocks() {
        return worldBlocks;
    }

    public void registerNeighbor(Property neighbor) {
        neighbors.add(neighbor);
    }
    
    public PropertyOwner getPropertyOwner() {
        return owner;
    }
}