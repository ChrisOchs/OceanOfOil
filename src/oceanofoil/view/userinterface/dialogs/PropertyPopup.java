package oceanofoil.view.userinterface.dialogs;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import oceanofoil.model.property.PrivateProperty;
import oceanofoil.model.property.Property;
import oceanofoil.model.property.owner.Family;
import oceanofoil.resroucemanager.TrueTypeFontManager;
import static org.lwjgl.opengl.GL11.*;
import oceanofoil.view.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Den
 */
public class PropertyPopup implements Drawable {

    private Property property;

    public PropertyPopup(Property p) {
        this.property = p;
    }

    public Property getProperty() {
        return property;
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

        ttf.drawString(24, 24, "Property: " + property.getPropertyName(), Color.black);

        if(property instanceof PrivateProperty) {
            Family family = (Family)property.getPropertyOwner();
            ttf.drawString(24, 40, "Type: Private Property", Color.black);
            ttf.drawString(24, 56, "Landowner: " + family.getHusband(), Color.black);
        }

        ttf.drawString(24, 72, "Land Size: " + property.getWorldBlocks().size() * 4 + " acres" , Color.black);
    }
}
