/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import oceanofoil.controller.MapSelectionManager;
import oceanofoil.model.GameWorld;
import oceanofoil.model.RailroadBlock;
import oceanofoil.model.WorldBlock;
import oceanofoil.model.property.Property;
import oceanofoil.resroucemanager.TextureManager;
import oceanofoil.resroucemanager.TrueTypeFontManager;
import oceanofoil.view.util.Point2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

public class WorldMap implements Drawable {

    public static final int MAP_TILE_SIZE = 512;

    private MapSelectionManager mapSelectionManager = MapSelectionManager.getMapSelectionManager();

    private Texture mapTexture = TextureManager.getTextureManager().getTexture(TextureManager.TextureName.MapBackground);

    private GameWorld gameWorld;

    private WorldTile [][] worldTiles;
    private RailroadTile [][] railroadTiles;

    public WorldMap(GameWorld gameWorld) {
        this.gameWorld = gameWorld;

        ArrayList<ArrayList<WorldBlock>> worldBlocks = gameWorld.getWorldBlocks();

        worldTiles = new WorldTile[worldBlocks.size()][worldBlocks.get(0).size()];

        for (int r = 0; r < worldBlocks.size(); r++) {
            for (int c = 0; c < worldBlocks.get(r).size(); c++) {
                int type = 0;

                if (r > 0) {
                    if (worldBlocks.get(r - 1).get(c).getProperty()
                            != worldBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.NORTH;
                    }
                } else {
                    type += WorldTile.NORTH;
                }

                if (r < worldBlocks.size() - 1) {
                    if (worldBlocks.get(r + 1).get(c).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.SOUTH;
                    }
                } else {
                    type += WorldTile.SOUTH;
                }

                if (c > 0) {
                    if (worldBlocks.get(r).get(c - 1).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.WEST;
                    }
                } else {
                    type += WorldTile.WEST;
                }

                if (c < worldBlocks.get(r).size() - 1) {
                    if (worldBlocks.get(r).get(c + 1).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.EAST;
                    }
                } else {
                    type += WorldTile.EAST;
                }

                worldTiles[r][c] = new WorldTile(worldBlocks.get(r).get(c), type, r, c);
            }
        }

        ArrayList<ArrayList<RailroadBlock>> rrBlocks = gameWorld.getRailroadBlocks();

        railroadTiles = new RailroadTile[rrBlocks.size()][rrBlocks.get(0).size()];

        for (int r = 0; r < rrBlocks.size(); r++) {
            for (int c = 0; c < rrBlocks.get(r).size(); c++) {

                if (rrBlocks.get(r).get(c).getProperty() == null) {
                    continue;
                }

                int type = 0;

                if (r > 0) {
                    if (rrBlocks.get(r - 1).get(c).getProperty() == rrBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.SOUTH;
                    }
                }

                if (r < rrBlocks.size() - 1) {
                    if (rrBlocks.get(r + 1).get(c).getProperty() == rrBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.NORTH;
                    }
                }

                if (c > 0) {
                    if (rrBlocks.get(r).get(c - 1).getProperty() == rrBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.WEST;
                    }
                }

                if (c < rrBlocks.get(0).size() - 1) {
                    if (rrBlocks.get(r).get(c + 1).getProperty() == rrBlocks.get(r).get(c).getProperty()) {
                        type += WorldTile.EAST;
                    }
                }

                railroadTiles[r][c] = new RailroadTile(rrBlocks.get(r).get(c), type, c, r);
                worldTiles[r][c].registerRailroadTile(railroadTiles[r][c]);
            }
        }
    }

    @Override
    public void draw() {
        Color.white.bind();

        mapTexture.bind();
        glPushMatrix();

        glScalef((float)MAP_TILE_SIZE, (float)MAP_TILE_SIZE, 1.0f);

        glBegin(GL_QUADS);
        {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    glTexCoord2f(0, 0);
                    glVertex2f(r, c);

                    glTexCoord2f(1, 0);
                    glVertex2f(r + 1, c);

                    glTexCoord2f(1, 1);
                    glVertex2f(r + 1, c + 1);

                    glTexCoord2f(0, 1);
                    glVertex2f(r, c + 1);
                }
            }
        }
        glEnd();

        glPopMatrix();

        glPushMatrix();

        glScalef((float)WorldTile.TILE_SIZE, (float)WorldTile.TILE_SIZE, 1.0f);

        for(int r = 0; r < worldTiles.length; r++) {
            for(int c = 0; c < worldTiles[r].length; c++) {
                if(mapSelectionManager.getSelectedProperty() == worldTiles[r][c].getWorldBlock().getProperty()) {
                    worldTiles[r][c].drawPropertySelectedHighlight();
                } else if(mapSelectionManager.getMousedOverProperty() == worldTiles[r][c].getWorldBlock().getProperty()
                        && mapSelectionManager.getMousedOverProperty() != mapSelectionManager.getSelectedProperty()) {
                    worldTiles[r][c].drawPropertyHoverHighlight();
                }

                worldTiles[r][c].draw();

                if(railroadTiles[r][c] != null) {
                    railroadTiles[r][c].draw();
                }

                if(mapSelectionManager.getSelectedTile() == worldTiles[r][c]) {
                    worldTiles[r][c].drawTileSelectedHighlight();
                }

                if(mapSelectionManager.getMousedOverTile() == worldTiles[r][c]
                   && worldTiles[r][c].getWorldBlock().getProperty() == mapSelectionManager.getSelectedProperty()
                   && worldTiles[r][c] != mapSelectionManager.getSelectedTile()) {

                    worldTiles[r][c].drawTilePropertySelectedHighlight();
                }
            }
        }

        glPopMatrix();

        HashMap<Property, Point2f> propertyTitleLocations = gameWorld.getPropertyTitleLocations();

        TrueTypeFont ttf = TrueTypeFontManager.getFontManager().getFont("Book Antiqua", 32);

        for(Entry<Property, Point2f> entry : propertyTitleLocations.entrySet()) {
            Point2f location = entry.getValue();

            ttf.drawString(location.x * 64, location.y * 64, entry.getKey().getPropertyName(), Color.black);
        }

    }

    public void updateMouseOverProperty(int worldX, int worldY) {
        WorldTile tile = getWorldTileAtWorldPoint(worldX, worldY);
        Property property = null;

        if(tile != null) {
            property = tile.getWorldBlock().getProperty();
        }

        mapSelectionManager.setMousedOverProperty(property);
        mapSelectionManager.setMousedOverTile(tile);
    }

    public void updateSelectedProperty(int worldX, int worldY) {
        WorldTile tile = getWorldTileAtWorldPoint(worldX, worldY);
        Property property = null;

        if (tile != null) {
            property = tile.getWorldBlock().getProperty();
        }

        if (property == mapSelectionManager.getSelectedProperty()) {
            if (tile == mapSelectionManager.getSelectedTile()) {
                mapSelectionManager.setSelectedProperty(null);
                mapSelectionManager.setSelectedTile(null);
            } else {
                mapSelectionManager.setSelectedTile(tile);
                tile.doClick();
            }
        } else {
            mapSelectionManager.setSelectedProperty(property);
            mapSelectionManager.setSelectedTile(null);
        }
    }

    private WorldTile getWorldTileAtWorldPoint(int x, int y) {
        int indexX = x / WorldTile.TILE_SIZE;
        int indexY = y / WorldTile.TILE_SIZE;

        if(indexX < 0 || indexY < 0 || indexX > worldTiles[0].length - 1 || indexY > worldTiles.length - 1) {
            return null;
        }

        return worldTiles[indexY][indexX];
    }
}
