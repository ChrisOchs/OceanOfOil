package oceanofoil.resroucemanager;

import java.awt.Font;
import java.util.HashMap;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author 
 */
public class TrueTypeFontManager {
    
    private static TrueTypeFontManager fontManager = new TrueTypeFontManager();

    public static TrueTypeFontManager getFontManager() {
        return fontManager;
    }

    HashMap<String, TrueTypeFont> fontMap = new HashMap<String, TrueTypeFont>();

    private TrueTypeFontManager() {
        
    }

    public TrueTypeFont getFont(String name, int size) {
        if (!fontMap.containsKey(name + size)) {
            Font font = new Font(name, Font.BOLD, size);
            fontMap.put(name + size, new TrueTypeFont(font, false));
        }

        return fontMap.get(name + size);
    }
}
