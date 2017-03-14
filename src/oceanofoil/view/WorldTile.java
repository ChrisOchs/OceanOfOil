/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view;

import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;


import oceanofoil.model.WorldBlock;
import oceanofoil.model.property.MunicipalProperty;
import oceanofoil.model.property.PrivateProperty;
import oceanofoil.model.property.Property;
import oceanofoil.model.property.upgrade.Barn;
import oceanofoil.model.property.upgrade.BlockUpgrade;
import oceanofoil.resroucemanager.SoundManager;
import oceanofoil.resroucemanager.TextureManager;
import oceanofoil.resroucemanager.TileManager;
import oceanofoil.view.util.Point2f;

/**
 *
 * @author Den
 */
public class WorldTile implements Drawable {

    public static final int NORTH = 0x0000000F;
    public static final int SOUTH = 0x000000F0;
    public static final int EAST = 0x00000F00;
    public static final int WEST = 0x0000F000;

    public static final int NORTH_EAST = 0x00000F0F;
    public static final int NORTH_WEST = 0x0000F00F;
    public static final int SOUTH_EAST = 0x00000FF0;
    public static final int SOUTH_WEST = 0x0000F0F0;

    public static final int OPEN_NORTH = 0x0000FFF0;
    public static final int OPEN_SOUTH = 0x0000FF0F;
    public static final int OPEN_EAST = 0x0000F0FF;
    public static final int OPEN_WEST = 0x00000FFF;

    public static final int NORTH_SOUTH = 0x000000FF;
    public static final int EAST_WEST = 0x0000FF00;

    public static final int CLOSED = 0x00000FFFF;

    public static final int NO_BORDER = 0x00000000;

    public static final int TILE_SIZE = 128;

    private static TextureManager textureManager = TextureManager.getTextureManager();
    private static TileManager tileManager = TileManager.getTileManager();

    private WorldBlock worldBlock;
    private RailroadTile associatedRailroadTile = null;

    private int type = 0;

    private int x;
    private int y;

    public WorldTile(WorldBlock block, int type, int r, int c) {
        this.worldBlock = block;
        this.type = type;

        this.x = c;
        this.y = r;
    }

    public void registerRailroadTile(RailroadTile rrTile) {
        this.associatedRailroadTile = rrTile;
    }

    public boolean containsRailroadTile() {
        return associatedRailroadTile != null;
    }

    public RailroadTile getRailroadTile() {
        return associatedRailroadTile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public WorldBlock getWorldBlock() {
        return worldBlock;
    }

    public void doClick() {
        BlockUpgrade tileUpgrade = worldBlock.getBlockUpgrade();

        if(tileUpgrade instanceof Barn) {
            SoundManager.getSoundManager().playSound(SoundManager.SoundName.Moo);
        }
    }

    @Override
    public void draw() {

        Point2f [] texPoints;
        
        if(worldBlock.getTerrainType() != WorldBlock.TerrainType.Plain && worldBlock.getBlockUpgrade() == null) {
            textureManager.getTexture(TextureManager.TextureName.TerrainTypeTiles).bind();

            texPoints = tileManager.getTexturePointsForTerrainType(worldBlock.getTerrainType());

            glColor3f(1.0f, 1.0f, 1.0f);

            glBegin(GL_QUADS);
            {
                glTexCoord2f(texPoints[0].x, texPoints[0].y);
                glVertex2f(x, y);

                glTexCoord2f(texPoints[1].x, texPoints[1].y);
                glVertex2f(x + 1, y);

                glTexCoord2f(texPoints[2].x, texPoints[2].y);
                glVertex2f(x + 1, y + 1);

                glTexCoord2f(texPoints[3].x, texPoints[3].y);
                glVertex2f(x, y + 1);
            }
            glEnd();
        }
        
        textureManager.getTexture(TextureManager.TextureName.Borders).bind();

        texPoints = tileManager.getTexturePointsForBorder(type);

        if (texPoints != null) {

            glColor3f(1.0f, 1.0f, 1.0f);
            
            glBegin(GL_QUADS);
            {
                glTexCoord2f(texPoints[0].x, texPoints[0].y);
                glVertex2f(x, y);

                glTexCoord2f(texPoints[1].x, texPoints[1].y);
                glVertex2f(x + 1, y);

                glTexCoord2f(texPoints[2].x, texPoints[2].y);
                glVertex2f(x + 1, y + 1);

                glTexCoord2f(texPoints[3].x, texPoints[3].y);
                glVertex2f(x, y + 1);
            }
            glEnd();
        }

        BlockUpgrade tileUpgrade = worldBlock.getBlockUpgrade();

        if (tileUpgrade != null) {
            textureManager.getTexture(TextureManager.TextureName.PropertyUpgrades).bind();

            texPoints = tileManager.getTexturePointsForUpgrade(tileUpgrade.getClass());

            glBegin(GL_QUADS);
            {
                glColor3f(1.0f, 1.0f, 1.0f);

                glTexCoord2f(texPoints[0].x, texPoints[0].y);
                glVertex2f(x, y);

                glTexCoord2f(texPoints[1].x, texPoints[1].y);
                glVertex2f(x + 1, y);

                glTexCoord2f(texPoints[2].x, texPoints[2].y);
                glVertex2f(x + 1, y + 1);

                glTexCoord2f(texPoints[3].x, texPoints[3].y);
                glVertex2f(x, y + 1);
            }
            glEnd();
        }
    }

    public void drawPropertyHoverHighlight() {

        Property p = worldBlock.getProperty();

        if(p instanceof PrivateProperty) {
            glColor4f(0.0f, 1.0f, 0.0f, 0.2f);
        } else if(p instanceof MunicipalProperty) {
            glColor4f(0.0f, 0.0f, 1.0f, 0.2f);
        }

        glDisable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        {
            glVertex2f(x, y);
            glVertex2f(x + 1, y);
            glVertex2f(x + 1, y + 1);
            glVertex2f(x, y + 1);
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);
    }

    public void drawPropertySelectedHighlight() {

        Property p = worldBlock.getProperty();

        if(p instanceof PrivateProperty) {
            glColor4f(0.0f, 1.0f, 0.0f, 0.3f);
        } else if(p instanceof MunicipalProperty) {
            glColor4f(0.0f, 0.0f, 1.0f, 0.3f);
        }

        glDisable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        {
            glVertex2f(x, y);
            glVertex2f(x + 1, y);
            glVertex2f(x + 1, y + 1);
            glVertex2f(x, y + 1);
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);
    }


    public void drawTilePropertySelectedHighlight() {

        textureManager.getTexture(TextureManager.TextureName.HighlightedTile).bind();

        glBegin(GL_QUADS);
        {
            glColor3f(1.0f, 1.0f, 1.0f);

            glTexCoord2f(0, 0);
            glVertex2f(x, y);

            glTexCoord2f(1, 0);
            glVertex2f(x + 1, y);

            glTexCoord2f(1, 1);
            glVertex2f(x + 1, y + 1);

            glTexCoord2f(0, 1);
            glVertex2f(x, y + 1);
        }
        glEnd();
    }

    public void drawTileSelectedHighlight() {

        textureManager.getTexture(TextureManager.TextureName.SelectedTile).bind();

        glBegin(GL_QUADS);
        {
            glColor3f(1.0f, 1.0f, 1.0f);
            
            glTexCoord2f(0, 0);
            glVertex2f(x, y);

            glTexCoord2f(1, 0);
            glVertex2f(x + 1, y);

            glTexCoord2f(1, 1);
            glVertex2f(x + 1, y + 1);

            glTexCoord2f(0, 1);
            glVertex2f(x, y + 1);
        }
        glEnd();


    }
}
