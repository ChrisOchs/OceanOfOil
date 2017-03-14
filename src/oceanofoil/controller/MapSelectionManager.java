/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.controller;

import oceanofoil.model.property.Property;
import oceanofoil.view.WorldTile;

/**
 *
 * @author 
 * 
 */
public class MapSelectionManager {
    
    private static MapSelectionManager mapSelectionManager = new MapSelectionManager();

    public static MapSelectionManager getMapSelectionManager() {
        return mapSelectionManager;
    }

    private Property selectedProperty = null;
    private Property mousedOverProperty = null;

    private WorldTile selectedTile = null;
    private WorldTile mouseOverTile = null;

    private MapSelectionManager() {
        
    }

    public void setSelectedProperty(Property p) {
        this.selectedProperty = p;
    }

    public Property getSelectedProperty() {
        return selectedProperty;
    }

    public void setMousedOverProperty(Property p) {
        mousedOverProperty = p;
    }

    public Property getMousedOverProperty() {
        return mousedOverProperty;
    }

    public void setSelectedTile(WorldTile wt) {
        this.selectedTile = wt;
    }

    public WorldTile getSelectedTile() {
        return selectedTile;
    }

    public void setMousedOverTile(WorldTile wt) {
        this.mouseOverTile = wt;
    }

    public WorldTile getMousedOverTile() {
        return mouseOverTile;
    }
}
