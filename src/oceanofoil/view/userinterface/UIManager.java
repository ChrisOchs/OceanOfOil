/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.view.userinterface;

import static org.lwjgl.opengl.GL11.*;

import oceanofoil.Main;
import oceanofoil.controller.MapSelectionManager;
import oceanofoil.controller.MouseManager;
import oceanofoil.gamemanager.GameTimer;
import oceanofoil.model.company.DrillingCompany;
import oceanofoil.model.property.Property;
import oceanofoil.model.property.upgrade.oilcompany.Derrick;
import oceanofoil.model.property.upgrade.oilcompany.Pumpjack;
import oceanofoil.view.WorldTile;
import oceanofoil.view.userinterface.dialogs.PropertyPopup;
import oceanofoil.view.userinterface.dialogs.TileBuildNote;
import oceanofoil.view.userinterface.dialogs.TilePopup;
import oceanofoil.view.util.Camera;
import oceanofoil.view.util.Point2f;

/**
 *
 * @author Den
 */
public class UIManager {
    private CompanyInterface companyInterface;
    private CompanyOptionsMenu optionsMenu;

    private TileBuildNote buildNote;
    private TilePopup tilePopup;
    private PropertyPopup propertyPopup;

    private MapSelectionManager selectionManager = MapSelectionManager.getMapSelectionManager();

    private MouseManager mouseManager;

    public UIManager(DrillingCompany company, GameTimer timer, MouseManager mouseManager) {
        this.mouseManager = mouseManager;
        companyInterface = new CompanyInterface(company, timer);
        optionsMenu = new CompanyOptionsMenu(company);
    }

    public boolean buildNoteDisplayed() {
        return buildNote == null;
    }

    public boolean tilePopupDisplayed() {
        return tilePopup == null;
    }

    public boolean propertyPopupDisplayed() {
        return propertyPopup == null;
    }

    public void setBuildNoteTile(WorldTile tile) {
        buildNote = new TileBuildNote(tile);
    }

    public void removeBuildNote() {
        buildNote = null;
    }

    public void removePropertyPopup() {
        propertyPopup = null;
    }

    public void removeTilePopup() {
        tilePopup = null;
    }

    public Property getPropertyPopupProperty() {
        return propertyPopup == null ? null : propertyPopup.getProperty();
    }

    public WorldTile getTilePopupTile() {
        return tilePopup == null ? null : tilePopup.getWorldTile();
    }

    public void setTilePopupTile(WorldTile tile) {
        tilePopup = new TilePopup(tile);
    }

    public void setPropertyPopupProperty(Property prop) {
        propertyPopup = new PropertyPopup(prop);
    }

    public boolean handleMouseMove(int screenX, int screenY, Camera camera) {
        int worldX = (int)(screenX / camera.getScale()) + camera.getX();
        int worldY = (int)( (Main.DISPLAY_HEIGHT - screenY) / camera.getScale() ) + camera.getY();

        if(screenX >= Main.DISPLAY_WIDTH - 256 && screenY >= Main.DISPLAY_HEIGHT - 80) {
            tilePopup = null;
            propertyPopup = null;

            selectionManager.setMousedOverProperty(null);
            selectionManager.setMousedOverTile(null);

            return true;
        }

        if(screenX >= Main.DISPLAY_WIDTH - 556 && screenY >= Main.DISPLAY_HEIGHT - 40) {
            tilePopup = null;
            propertyPopup = null;

            selectionManager.setMousedOverProperty(null);
            selectionManager.setMousedOverTile(null);

            return true;
        }
        
        if (buildNote != null) {
            Point2f buildNoteLocation = buildNote.getAbsoluteLocation();

            if (worldX >= buildNoteLocation.x + 60 / camera.getScale() && worldX <= buildNoteLocation.x + (384 - 60) / camera.getScale()
                    && worldY >= buildNoteLocation.y + 40 / camera.getScale() && worldY <= buildNoteLocation.y + (384 - 40) / camera.getScale()) {

                tilePopup = null;
                propertyPopup = null;

                selectionManager.setMousedOverProperty(null);
                selectionManager.setMousedOverTile(null);

                int xPos = (int)(((int)buildNote.getAbsoluteLocation().x - camera.getX()) * camera.getScale());
                int yPos = (int)(((int)buildNote.getAbsoluteLocation().y - camera.getY()) * camera.getScale());

                buildNote.handleMouseOver(screenX - xPos, Main.DISPLAY_HEIGHT - screenY - yPos);

                return true;
            }
        }

        return false;
    }

    public boolean handleMouseClick(int screenX, int screenY, Camera camera) {
        int worldX = (int)(screenX / camera.getScale()) + camera.getX();
        int worldY = (int)( (Main.DISPLAY_HEIGHT - screenY) / camera.getScale() ) + camera.getY();

        if(screenX >= Main.DISPLAY_WIDTH - 256 && screenY >= Main.DISPLAY_HEIGHT - 80) {


            return true;
        }

        if(screenX >= Main.DISPLAY_WIDTH - 556 && screenY >= Main.DISPLAY_HEIGHT - 40) {

            return true;
        }
        
        if (buildNote != null) {
            Point2f buildNoteLocation = buildNote.getAbsoluteLocation();

            if (worldX >= buildNoteLocation.x + 60 / camera.getScale() && worldX <= buildNoteLocation.x + (384 - 60) / camera.getScale()
                    && worldY >= buildNoteLocation.y + 40 / camera.getScale() && worldY <= buildNoteLocation.y + (384 - 40) / camera.getScale()) {

                int xPos = (int)(((int)buildNote.getAbsoluteLocation().x - camera.getX()) * camera.getScale());
                int yPos = (int)(((int)buildNote.getAbsoluteLocation().y - camera.getY()) * camera.getScale());

                int selectedOption = buildNote.getSelectedOption(screenX - xPos, Main.DISPLAY_HEIGHT - screenY - yPos);

                if(selectedOption > -1) {
                    if(selectedOption == 0) {
                        buildNote.getWorldTile().getWorldBlock().setTileUpgrade(new Derrick());
                    }
                    else if(selectedOption == 1) {
                        buildNote.getWorldTile().getWorldBlock().setTileUpgrade(new Pumpjack());
                    }

                    MapSelectionManager.getMapSelectionManager().setSelectedTile(null);
                    buildNote = null;
                }

                return true;
            }
        }

        return false;
    }

    public void draw(Camera camera) {
        glLoadIdentity();

        glTranslatef(Main.DISPLAY_WIDTH - 256, 0, 0);
        companyInterface.draw();

        glTranslatef(-300, 0, 0);
        optionsMenu.draw();
        
        glLoadIdentity();

        if(buildNote != null) {
            int xPos = (int)(((int)buildNote.getAbsoluteLocation().x - camera.getX()) * camera.getScale());
            int yPos = (int)(((int)buildNote.getAbsoluteLocation().y - camera.getY()) * camera.getScale());

            glTranslatef(xPos, yPos, 0);
            buildNote.draw();
        }

        glLoadIdentity();

        if(propertyPopup != null) {
            glTranslatef(mouseManager.mouseLocation.x, Main.DISPLAY_HEIGHT - mouseManager.mouseLocation.y, 0);
            propertyPopup.draw();
        } else if(tilePopup != null) {
            glTranslatef(mouseManager.mouseLocation.x, Main.DISPLAY_HEIGHT - mouseManager.mouseLocation.y, 0);
            tilePopup.draw();
        }
    }
}
