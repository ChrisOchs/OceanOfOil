/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oceanofoil.resroucemanager;

import java.util.EnumMap;
import java.util.HashMap;
import oceanofoil.model.WorldBlock;
import oceanofoil.model.property.upgrade.Barn;
import oceanofoil.model.property.upgrade.FarmHouse;
import oceanofoil.model.property.upgrade.oilcompany.Derrick;
import oceanofoil.model.property.upgrade.oilcompany.Pumpjack;
import oceanofoil.view.WorldTile;
import oceanofoil.view.util.Point2f;

/**
 *
 * @author Den
 */
public class TileManager {

    private static TileManager tileManager = new TileManager();

    public static TileManager getTileManager() {
        return tileManager;
    }

    private HashMap<Integer, Point2f[]> borderTypeMap = new HashMap<Integer, Point2f[]>();
    private HashMap<Class, Point2f[]> upgradeTypeMap = new HashMap<Class, Point2f[]>();
    private HashMap<Integer, Point2f[]> railroadTypeMap = new HashMap<Integer, Point2f[]>();
    private EnumMap<WorldBlock.TerrainType, Point2f[]> terrainTypeMap = new EnumMap<WorldBlock.TerrainType, Point2f[]>(WorldBlock.TerrainType.class);

    private TileManager() {
        borderTypeMap.put(WorldTile.NORTH, new Point2f[]{new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f), new Point2f(0, 0)});
        borderTypeMap.put(WorldTile.SOUTH, new Point2f[]{new Point2f(0, 0.5f), new Point2f(0, 0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f)});
        borderTypeMap.put(WorldTile.EAST, new Point2f[]{new Point2f(0, 0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f)});
        borderTypeMap.put(WorldTile.WEST, new Point2f[]{new Point2f(0.5f, 0), new Point2f(0, 0), new Point2f(0, 0.5f), new Point2f(0.5f, 0.5f)});

        borderTypeMap.put(WorldTile.NORTH_EAST, new Point2f[]{new Point2f(0, 0.5f), new Point2f(0.5f, 0.5f), new Point2f(0.5f, 1), new Point2f(0, 1)});
        borderTypeMap.put(WorldTile.NORTH_WEST, new Point2f[]{new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f), new Point2f(0, 1), new Point2f(0.5f, 1)});
        borderTypeMap.put(WorldTile.SOUTH_WEST, new Point2f[]{new Point2f(0.5f, 1), new Point2f(0, 1), new Point2f(0, 0.5f), new Point2f(0.5f, 0.5f)});
        borderTypeMap.put(WorldTile.SOUTH_EAST, new Point2f[]{new Point2f(0f, 1), new Point2f(0.5f, 1f), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f)});

        borderTypeMap.put(WorldTile.OPEN_NORTH, new Point2f[]{new Point2f(1, 1), new Point2f(0.5f, 1), new Point2f(0.5f, 0.5f), new Point2f(1, 0.5f)});
        borderTypeMap.put(WorldTile.OPEN_SOUTH, new Point2f[]{new Point2f(0.5f, 0.5f), new Point2f(1, 0.5f), new Point2f(1, 1), new Point2f(0.5f, 1)});

        borderTypeMap.put(WorldTile.OPEN_WEST, new Point2f[]{new Point2f(0.5f, 1), new Point2f(0.5f, 0.5f), new Point2f(1, 0.5f), new Point2f(1, 1)});
        borderTypeMap.put(WorldTile.OPEN_EAST, new Point2f[]{new Point2f(1, 1), new Point2f(1, 0.5f), new Point2f(0.5f, 0.5f), new Point2f(0.5f, 1)});

        borderTypeMap.put(WorldTile.NORTH_SOUTH, new Point2f[]{new Point2f(0.5f, 0.5f), new Point2f(0.5f, 0f), new Point2f(1, 0), new Point2f(1, 0.5f)});
        borderTypeMap.put(WorldTile.EAST_WEST, new Point2f[]{new Point2f(0.5f, 0), new Point2f(1, 0), new Point2f(1, 0.5f), new Point2f(0.5f, 0.5f)});

        upgradeTypeMap.put(FarmHouse.class, new Point2f[] {new Point2f(0,0), new Point2f(0.25f, 0), new Point2f(0.25f, 0.25f), new Point2f(0, 0.25f) });
        upgradeTypeMap.put(Barn.class, new Point2f[] {new Point2f(0.25f,0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.25f), new Point2f(0.25f, 0.25f) });
        upgradeTypeMap.put(Pumpjack.class, new Point2f[] {new Point2f(0.5f,0), new Point2f(0.75f, 0), new Point2f(0.75f, 0.25f), new Point2f(0.5f, 0.25f) });
        upgradeTypeMap.put(Derrick.class, new Point2f[] {new Point2f(0.75f,0), new Point2f(1, 0), new Point2f(1, 0.25f), new Point2f(0.75f, 0.25f) });

        railroadTypeMap.put(WorldTile.EAST_WEST, new Point2f[] { new Point2f(0,0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f) });
        railroadTypeMap.put(WorldTile.NORTH_SOUTH, new Point2f[] { new Point2f(0,0.5f), new Point2f(0, 0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f) });

        railroadTypeMap.put(WorldTile.NORTH_WEST, new Point2f[] { new Point2f(0,0.5f), new Point2f(0.5f, 0.5f), new Point2f(0.5f, 1), new Point2f(0, 1) });
        railroadTypeMap.put(WorldTile.NORTH_EAST, new Point2f[] { new Point2f(0.5f,0.5f), new Point2f(0, 0.5f), new Point2f(0, 1), new Point2f(0.5f, 1) });
        railroadTypeMap.put(WorldTile.SOUTH_EAST, new Point2f[] { new Point2f(0.5f, 1), new Point2f(0, 1), new Point2f(0, 0.5f), new Point2f(0.5f, 0.5f) });
        railroadTypeMap.put(WorldTile.SOUTH_WEST, new Point2f[] { new Point2f(0, 1), new Point2f(0.5f, 1), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f) });

        railroadTypeMap.put(WorldTile.WEST, new Point2f[] { new Point2f(0.5f, 0), new Point2f(1, 0), new Point2f(1, 0.5f), new Point2f(0.5f, 0.5f) });
        railroadTypeMap.put(WorldTile.EAST, new Point2f[] { new Point2f(1, 0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f), new Point2f(1, 0.5f) });
        railroadTypeMap.put(WorldTile.NORTH, new Point2f[] { new Point2f(1, 0), new Point2f(1, 0.5f), new Point2f(0.5f, 0.5f), new Point2f(0.5f, 0) });
        railroadTypeMap.put(WorldTile.SOUTH, new Point2f[] { new Point2f(0.5f, 0.5f), new Point2f(0.5f, 0), new Point2f(1, 0), new Point2f(1, 0.5f) });

        terrainTypeMap.put(WorldBlock.TerrainType.RollingHills, new Point2f[] {new Point2f(0, 0), new Point2f(0.5f, 0), new Point2f(0.5f, 0.5f), new Point2f(0, 0.5f) });
        terrainTypeMap.put(WorldBlock.TerrainType.Rocky, new Point2f[] {new Point2f(0.5f, 0), new Point2f(1, 0), new Point2f(1, 0.5f), new Point2f(0.5f, 0.5f) });
    }

    public Point2f[] getTexturePointsForBorder(int type) {
        return borderTypeMap.get(type);
    }

    public Point2f[] getTexturePointsForUpgrade(Class upgradeCls) {
        return upgradeTypeMap.get(upgradeCls);
    }

    public Point2f[] getTexturePointsForRailroad(int type) {
        return railroadTypeMap.get(type);
    }

    public Point2f[] getTexturePointsForTerrainType(WorldBlock.TerrainType type) {
        return terrainTypeMap.get(type);
    }
}
