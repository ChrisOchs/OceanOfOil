/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view;

import oceanofoil.resroucemanager.TileManager;
import oceanofoil.resroucemanager.TextureManager;
import oceanofoil.view.util.Point2f;
import oceanofoil.model.RailroadBlock;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Den
 */
public class RailroadTile implements Drawable {

    private TextureManager textureManager = TextureManager.getTextureManager();
    private TileManager tileManager = TileManager.getTileManager();

    private RailroadBlock block;
    private int x;
    private int y;

    private int type;

    public RailroadTile(RailroadBlock block, int type, int x, int y) {
        this.block = block;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public RailroadBlock getRailroadBlock() {
        return block;
    }

    @Override
    public void draw() {
        textureManager.getTexture(TextureManager.TextureName.RailroadTiles).bind();

        Point2f[] texPoints = tileManager.getTexturePointsForRailroad(type);

        if (texPoints != null) {
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
}
