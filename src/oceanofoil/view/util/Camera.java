/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view.util;

import java.awt.Rectangle;



/**
 *
 * @author Den
 */
public class Camera {
    private Rectangle viewport;

    private int worldWidth;
    private int worldHeight;

    private int initialViewportWidth;
    private int initialViewportHeight;

    private float scale;

    public Camera(int startX, int startY, int width, int height, int worldWidth, int worldHeight, float scale) {
        this.viewport = new Rectangle(startX, startY, width, height);

        this.initialViewportWidth = width;
        this.initialViewportHeight = height;

        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        
        this.scale = scale;
    }
    
    public int getX() {
        return viewport.x;
    }
    
    public int getY() {
        return viewport.y;
    }

    public float getScale() {
        return scale;
    }

    public void moveHorizontal(int deltaX) {
        setX(viewport.x + deltaX);
    }

    public void moveVertical(int deltaY) {
        setY(viewport.y + deltaY);
    }

    public void changeZoom(float deltaZoom) {
        setZoom(scale + deltaZoom);
    }

    private void setX(int x) {
        viewport.x = x;
        
        if(viewport.x < 0) {
            viewport.x = 0;
        } else if(viewport.x + viewport.width > worldWidth) {
            viewport.x = worldWidth - viewport.width;
        }
    }

    private void setY(int y) {
        viewport.y = y;

        if(viewport.y < 0) {
            viewport.y = 0;
        } else if(viewport.y + viewport.height > worldHeight) {
            viewport.y = worldHeight - viewport.height;
        }
    }

    private void setZoom(float scale) {


        int xMid = viewport.x + viewport.width / 2;
        int yMid = viewport.y + viewport.height / 2;

        viewport.width = (int)(this.initialViewportWidth / scale);
        viewport.height = (int)(this.initialViewportHeight / scale);

        if(viewport.width > worldWidth) {
            viewport.width = worldWidth;
            setX(0);
            return;
        }

        if(viewport.height > worldHeight) {
            viewport.height = worldHeight;
            setY(0);
            return;
        }


        if(scale > 5) {
            scale = 5;
        }

        this.scale = scale;

        setX(xMid - viewport.width / 2);
        setY(yMid - viewport.height / 2);
    }
}
