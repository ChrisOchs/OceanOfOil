package oceanofoil.view.userinterface.dialogs;

import oceanofoil.resroucemanager.TrueTypeFontManager;
import static org.lwjgl.opengl.GL11.*;
import oceanofoil.view.Drawable;
import oceanofoil.view.WorldTile;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author 
 */
public class TilePopup implements Drawable {

    private WorldTile tile;

    public TilePopup(WorldTile tile) {
        this.tile = tile;
    }

    public WorldTile getWorldTile() {
        return tile;
    }

    public void draw() {
        glColor3f(1.0f, 1.0f, 1.0f);

        glDisable(GL_TEXTURE_2D);
        
        glBegin(GL_QUADS);
        {
            glVertex2f(16, 128);

            glVertex2f(384, 128);

            glVertex2f(384, 16);

            glVertex2f(16, 16);
        }
        glEnd();

        glBegin(GL_TRIANGLES);
        {
            glVertex2f(0, 0);
            glVertex2f(16, 16);
            glVertex2f(24, 16);
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);

        TrueTypeFont ttf = TrueTypeFontManager.getFontManager().getFont("Book Antiqua", 14);

        ttf.drawString(24, 56, "Terrain Type: " + tile.getWorldBlock().getTerrainType().name(), Color.black);
        
        if(tile.getWorldBlock().getBlockUpgrade() != null) {
            ttf.drawString(24, 72, "Block Upgrade: " + tile.getWorldBlock().getBlockUpgrade().upgradeName(), Color.black);
        }

        if(tile.containsRailroadTile()) {
            ttf.drawString(24, 72, "Railroad: " + tile.getRailroadTile().getRailroadBlock().getProperty().getPropertyName(), Color.black);
        }

    }
}
