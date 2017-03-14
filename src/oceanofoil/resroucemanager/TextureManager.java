/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.resroucemanager;

import java.io.IOException;
import java.util.EnumMap;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Den
 */
public class TextureManager {

    public enum TextureName {
        MapBackground,
        Borders,
        PropertyUpgrades,
        RailroadTiles,
        TerrainTypeTiles,
        HighlightedTile,
        SelectedTile,
        PaperNote
    };

    private EnumMap<TextureName, Texture> textures = new EnumMap<TextureName, Texture>(TextureName.class);
    private EnumMap<TextureName, String> textureLocations = new EnumMap<TextureName, String>(TextureName.class);

    private static TextureManager manager = new TextureManager();

    public static TextureManager getTextureManager() {
        return manager;
    }

    private TextureManager() {
        textureLocations.put(TextureName.MapBackground, "mapbg2.png");
        textureLocations.put(TextureName.Borders, "borders.png");
        textureLocations.put(TextureName.PropertyUpgrades, "propertyupgrades.png");
        textureLocations.put(TextureName.RailroadTiles, "rrtiles.png");
        textureLocations.put(TextureName.TerrainTypeTiles, "terraintypeicons.png");
        textureLocations.put(TextureName.HighlightedTile, "highlightedtile.png");
        textureLocations.put(TextureName.SelectedTile, "selectedtile.png");
        textureLocations.put(TextureName.PaperNote, "papernote.png");
    }

    private Texture loadTexture(String file) throws IOException {
        return TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/textures/" + file));
    }

    public Texture getTexture(TextureName texture) {
        if(!textures.containsKey(texture)) {
            try {
                textures.put(texture, loadTexture(textureLocations.get(texture)));
            }
            catch(IOException ioe) {
                System.err.println(ioe);
                System.exit(-1);
            }
        }

        return textures.get(texture);
    }
}
