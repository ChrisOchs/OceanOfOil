
package oceanofoil.view.userinterface.dialogs;

import oceanofoil.resroucemanager.TextureManager;
import oceanofoil.resroucemanager.TrueTypeFontManager;
import static org.lwjgl.opengl.GL11.*;
import oceanofoil.view.Drawable;
import oceanofoil.view.WorldTile;
import oceanofoil.view.util.Point2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author 
 */
public class TileBuildNote implements Drawable {
    
    private WorldTile tile;

    private boolean [] optionOver;

    public TileBuildNote(WorldTile tile) {
        this.tile = tile;

        optionOver = new boolean[] {false, false};
    }

    public WorldTile getWorldTile() {
        return tile;
    }

    public Point2f getAbsoluteLocation() {
        return new Point2f(tile.getX() * WorldTile.TILE_SIZE, tile.getY() * WorldTile.TILE_SIZE);
    }

    public void handleMouseOver(int x, int y) {

        if(x >= 85 && x <= 200 && y >= 110 && y < 130) {
            optionOver[0] = true;
        } else {
            optionOver[0] = false;
        }

        if(x >= 85 && x <= 200 && y >= 150 && y < 170) {
            optionOver[1] = true;
        } else {
            optionOver[1] = false;
        }
    }

    public int getSelectedOption(int x, int y) {

        if(x >= 85 && x <= 200 && y >= 110 && y < 130) {
            return 0;
        }

        if(x >= 85 && x <= 200 && y >= 150 && y < 170) {
            return 1;
        }

        return -1;
    }

    public void draw() {
        glColor3f(1.0f, 1.0f, 1.0f);

        TextureManager.getTextureManager().getTexture(TextureManager.TextureName.PaperNote).bind();

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 0);
            glVertex2f(0, 0);

            glTexCoord2f(1, 0);
            glVertex2f(384, 0);

            glTexCoord2f(1, 1);
            glVertex2f(384, 384);

            glTexCoord2f(0, 1);
            glVertex2f(0, 384);
        }
        glEnd();

        TrueTypeFont ttf = TrueTypeFontManager.getFontManager().getFont("Book Antiqua", 14);

        String introString = String.format("To [%s foreman]:", tile.getWorldBlock().getProperty().getPropertyName());

        ttf.drawString(80, 64, introString, Color.black);

        ttf.drawString(85, 110, " [   ] Please build a drill.", optionOver[0] ? Color.red : Color.black);
        ttf.drawString(85, 150, " [   ] Please build a pump jack.", optionOver[1] ? Color.red : Color.black);

        ttf.drawString(85, 200, "Kindest regards,", Color.black);
        ttf.drawString(85, 220, "[Your Name]", Color.black);
    }
}
