package oceanofoil.model;

import oceanofoil.model.property.Property;
import oceanofoil.model.property.upgrade.BlockUpgrade;

/**
 *
 * @author 
 */
public class WorldBlock {
    
    public enum TerrainType {
        Plain,
        RollingHills,
        Rocky
    }

    private Property property;

    private BlockUpgrade blockUpgrade;

    private TerrainType terrain = TerrainType.Plain;

    public WorldBlock(Property p) {
        this.property = p;
    }

    public void setTileUpgrade(BlockUpgrade upgrade) {
        this.blockUpgrade = upgrade;
    }

    public void setTerrainType(TerrainType type) {
        this.terrain = type;
    }

    public TerrainType getTerrainType() {
        return terrain;
    }

    public Property getProperty() {
        return property;
    }

    public BlockUpgrade getBlockUpgrade() {
        return blockUpgrade;
    }
}
