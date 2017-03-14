/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view.userinterface;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import oceanofoil.gamemanager.GameTimer;
import oceanofoil.model.company.DrillingCompany;
import oceanofoil.model.player.Player;
import oceanofoil.resroucemanager.TrueTypeFontManager;
import static org.lwjgl.opengl.GL11.*;
import oceanofoil.view.Drawable;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Den
 */
public class CompanyInterface implements Drawable {

    private DrillingCompany company;

    private GameTimer timer;
    
    public CompanyInterface(DrillingCompany company, GameTimer timer) {
        this.company = company;
        this.timer = timer;
    }

    public void draw() {
        glColor3f(1.0f, 1.0f, 1.0f);

        glDisable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        {
            glVertex2f(0, 0);

            glVertex2f(256, 0);

            glVertex2f(256, 80);

            glVertex2f(0, 80);
        }
        glEnd();

        glEnable(GL_TEXTURE_2D);

        TrueTypeFont ttf = TrueTypeFontManager.getFontManager().getFont("Book Antiqua", 14);

        ttf.drawString(8, 2, "Company Name: " + company.getCompanyName(), Color.black);
        ttf.drawString(8, 18, "Owner: " + ((Player)company.getCompanyOwner()).getName(), Color.black);
        ttf.drawString(8, 34, "Bank Account: " + NumberFormat.getCurrencyInstance().format(company.getBankAccount()), Color.black);
        ttf.drawString(8, 50, "Current Date: " + new SimpleDateFormat("d MMM yyyy (EEE)").format(timer.getCurrentGameDate()), Color.black);
    }
}
