/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.controller;

import oceanofoil.view.util.Point2f;
import org.lwjgl.input.Mouse;

/**
 *
 * @author Den
 */
public class MouseManager {
    private boolean lmbPressed = false;
    private boolean lmbClicked = false;

    public Point2f mouseLocation = new Point2f(0, 0);

    public MouseManager() {
        
    }

    public void updateMouseState() {
        lmbClicked = false;

        boolean lmbState = false;

        if(Mouse.isButtonDown(0)) {
            lmbState = true;
        }

        if(lmbState == false && lmbPressed == true) {
            lmbClicked = true;
        }

        lmbPressed = lmbState;

        mouseLocation.x = Mouse.getX();
        mouseLocation.y = Mouse.getY();
    }

    public boolean leftMouseButtonClicked() {
        return lmbClicked;
    }
}
