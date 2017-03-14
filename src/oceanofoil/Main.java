package oceanofoil;

import java.awt.Toolkit;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import oceanofoil.controller.MapSelectionManager;
import oceanofoil.controller.MouseManager;
import oceanofoil.model.GameWorld;
import oceanofoil.model.company.DrillingCompany;
import oceanofoil.model.player.Player;
import oceanofoil.model.property.Property;
import oceanofoil.resroucemanager.SoundManager;
import oceanofoil.gamemanager.GameTimer;
import oceanofoil.view.util.Camera;
import oceanofoil.view.WorldMap;
import oceanofoil.view.WorldTile;
import oceanofoil.view.userinterface.UIManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {

    public static final int DISPLAY_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 64;
    public static final int DISPLAY_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    
    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    static {
        try {
            LOGGER.addHandler(new FileHandler("errors.log", true));
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, ex.toString(), ex);
        }
    }

    public static void main(String[] args) {
        Main main = null;
        try {

            main = new Main();
            main.create();
            main.run();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (main != null) {
                main.destroy();
            }
        }
    }

    public Main() {
        
    }

    private Camera camera;

    private WorldMap map;

    private GameTimer updateTimer = new GameTimer();

    private UIManager uiManager;

    public void create() throws LWJGLException, Exception {
        //Display
        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("Ocean of Oil");
        Display.create();

        Display.setLocation(0, 0);

        //Keyboard
        Keyboard.create();

        //Mouse
        Mouse.setGrabbed(false);
        Mouse.create();

        //OpenGL
        initGL();
        resizeGL();

        uiManager = new UIManager(new DrillingCompany(new Player("Chris"), "Plainview Drilling"), updateTimer, mouseClickManager);
        map = new WorldMap(loadLevel("world1.txt"));
        camera = new Camera(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT, 2048, 2048, 1.0f);


        //SoundManager.getSoundManager().playSong(SoundManager.MusicName.Song1);
    }

    private GameWorld loadLevel(String levelName) throws Exception {
        InputStream is = Main.class.getResourceAsStream("/leveldata/" + levelName);
        Scanner scanner = new Scanner(is);

        StringBuilder builder = new StringBuilder();

        while(scanner.hasNext()) {
            builder.append(scanner.nextLine());
            builder.append("\n");
        }

        GameWorld world = new GameWorld(builder.toString().replaceAll("\t", ""));

        return world;
    }

    public void destroy() {
        //Methods already check if created before destroying.
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
        AL.destroy();
    }

    public void initGL() {
        //2D Initialization
        glClearColor(0f, 0f, 0f, 0.0f);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);
        glEnable(GL_TEXTURE_2D);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void processKeyboard() {
        if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) {
            camera.moveVertical(-5);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) {
            camera.moveVertical(5);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)) {
            camera.moveHorizontal(-5);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)) {
            camera.moveHorizontal(5);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
            camera.changeZoom(0.05f);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
            camera.changeZoom(-0.05f);
        }
    }

    private MouseManager mouseClickManager = new MouseManager();
    private MapSelectionManager mapSelectionManager = MapSelectionManager.getMapSelectionManager();

    public void processMouse() {
        int x = Mouse.getX();
        int y = Mouse.getY();

        int worldX = (int)(x / camera.getScale()) + camera.getX();
        int worldY = (int)( (DISPLAY_HEIGHT - y) / camera.getScale() ) + camera.getY();

        mouseClickManager.updateMouseState();

        if(!uiManager.handleMouseMove(x, y, camera)) {
            map.updateMouseOverProperty(worldX, worldY);
        }
        
        int mouseWheelDelta = Mouse.getDWheel();
        
        if(mouseWheelDelta > 0) {
            camera.changeZoom(0.05f);
        } else if(mouseWheelDelta < 0) {
            camera.changeZoom(-0.05f);
        }

        if (mouseClickManager.leftMouseButtonClicked()) {

            if(!uiManager.handleMouseClick(x, y, camera)) {
                map.updateSelectedProperty(worldX, worldY);

                SoundManager.getSoundManager().playSound(SoundManager.SoundName.Click);

                WorldTile selectedTile = mapSelectionManager.getSelectedTile();

                if (selectedTile != null) {
                    uiManager.setBuildNoteTile(selectedTile);
                } else {
                    uiManager.removeBuildNote();
                }
            }
        }

        Property mousedOverProperty = mapSelectionManager.getMousedOverProperty();
        Property selectedProperty = mapSelectionManager.getSelectedProperty();

        if(selectedProperty == null || selectedProperty != mousedOverProperty) {
            if (uiManager.propertyPopupDisplayed()) {
                if (uiManager.getPropertyPopupProperty() != mousedOverProperty) {
                    uiManager.setPropertyPopupProperty(mousedOverProperty);
                }
            } else {
                uiManager.setPropertyPopupProperty(mousedOverProperty);
            }
        } else {
            uiManager.removePropertyPopup();
        }

        WorldTile mousedOverTile = mapSelectionManager.getMousedOverTile();

        if (mousedOverTile != null && mousedOverTile.getWorldBlock().getProperty() == MapSelectionManager.getMapSelectionManager().getSelectedProperty()) {
            if (uiManager.tilePopupDisplayed()) {
                if (uiManager.getTilePopupTile() != mousedOverTile) {
                    uiManager.setTilePopupTile(mousedOverTile);
                }
            } else {
                uiManager.setTilePopupTile(mousedOverTile);
            }
        } else {
            uiManager.removeTilePopup();
        }
    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glLoadIdentity();

        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        gluOrtho2D(0, DISPLAY_WIDTH, 0, DISPLAY_HEIGHT);
        glScalef(1, -1, 1);
        glTranslatef(0, -DISPLAY_HEIGHT, 0);
        glMatrixMode(GL_MODELVIEW);

        glPushMatrix();

        glScalef(camera.getScale(), camera.getScale(), 1.0f);
        glTranslatef(-camera.getX(), -camera.getY(), 0);

        glPushMatrix();

        map.draw();

        glPopMatrix();

        glPopMatrix();

        glPushMatrix();

        uiManager.draw(camera);

        glPopMatrix();

        glMatrixMode(GL_PROJECTION);
        glPopMatrix();
        glMatrixMode(GL_MODELVIEW);
    }

    public void resizeGL() {
        //2D Scene
        glViewport(0, 0, DISPLAY_WIDTH, DISPLAY_HEIGHT);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0.0f, DISPLAY_WIDTH, 0.0f, DISPLAY_HEIGHT);
        glPushMatrix();

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glPushMatrix();
    }

    public void run() {
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            if (Display.isVisible()) {
                processKeyboard();
                processMouse();
                update();
                render();
            } else {
                if (Display.isDirty()) {
                    render();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            Display.update();
            Display.sync(60);
        }
    }

    public void update() {
        updateTimer.update();
    }
}
