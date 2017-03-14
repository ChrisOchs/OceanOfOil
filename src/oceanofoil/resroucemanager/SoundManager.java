/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.resroucemanager;

import java.io.IOException;
import java.util.EnumMap;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Den
 */
public class SoundManager {
    public enum SoundName {
        Click, 
        Moo
    }

    public enum MusicName {
        Song1
    }

    private static SoundManager manager = new SoundManager();

    public static SoundManager getSoundManager() {
        return manager;
    }

    private EnumMap<SoundName, Audio> sounds = new EnumMap<SoundName, Audio>(SoundName.class);
    private EnumMap<SoundName, String> soundLocations = new EnumMap<SoundName, String>(SoundName.class);
    
    private EnumMap<MusicName, Audio> music = new EnumMap<MusicName, Audio>(MusicName.class);
    private EnumMap<MusicName, String> musicLocations = new EnumMap<MusicName, String>(MusicName.class);

    private SoundManager() {
        soundLocations.put(SoundName.Click, "click.wav");
        soundLocations.put(SoundName.Moo, "moo.wav");
        musicLocations.put(MusicName.Song1, "Song1.wav");
    }

    public void playSound(SoundName sound) {
        try {
            if (!sounds.containsKey(sound)) {
                sounds.put(sound, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/sounds/" + soundLocations.get(sound))));
            }

            sounds.get(sound).playAsSoundEffect(1, 1, false);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(-3);
        }
    }

    public void playSong(MusicName musicName) {
        try {
            if (!music.containsKey(musicName)) {
                music.put(musicName, AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/music/" + musicLocations.get(musicName))));
            }

            music.get(musicName).playAsMusic(1, 1, true);
        } catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(-3);
        }
    }
}
