/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view.userinterface;

import static org.lwjgl.opengl.GL11.*;
import oceanofoil.model.company.DrillingCompany;
import oceanofoil.resroucemanager.TrueTypeFontManager;
import oceanofoil.view.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Den
 */
public class CompanyOptionsMenu implements Drawable {

    private DrillingCompany company;

    public CompanyOptionsMenu(DrillingCompany company) {
        this.company = company;
    }

    public void draw() {
        glColor3f(1.0f, 1.0f, 1.0f);

        glPushMatrix();

        glDisable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        {
            glVertex2f(0, 0);

            glVertex2f(300, 0);

            glVertex2f(300, 40);

            glVertex2f(0, 40);
        }
        glEnd();

        glColor3f(0.7f, 0.7f, 0.7f);

        glTranslatef(3, 3, 0);

        glBegin(GL_QUADS);
        {
            glVertex2f(0, 0);

            glVertex2f(80, 0);

            glVertex2f(80, 34);

            glVertex2f(0, 34);
        }
        glEnd();

        glTranslatef(86, 0, 0);

        glBegin(GL_QUADS);
        {
            glVertex2f(0, 0);

            glVertex2f(80, 0);

            glVertex2f(80, 34);

            glVertex2f(0, 34);
        }
        glEnd();

        glTranslatef(86, 0, 0);

        glBegin(GL_QUADS);
        {
            glVertex2f(0, 0);

            glVertex2f(80, 0);

            glVertex2f(80, 34);

            glVertex2f(0, 34);
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);

        glPopMatrix();

        glColor3f(1.0f, 1.0f, 1.0f);

        TrueTypeFont ttf = TrueTypeFontManager.getFontManager().getFont("Book Antiqua", 18);

        glTranslatef(20, 6, 0);
        ttf.drawString(0, 0, "Bank", Color.black);

        glTranslatef(86, 0, 0);
        ttf.drawString(0, 0, "Staff", Color.black);

        glTranslatef(86, 0, 0);
        ttf.drawString(0, 0, "Inbox", Color.black);
    }
}
