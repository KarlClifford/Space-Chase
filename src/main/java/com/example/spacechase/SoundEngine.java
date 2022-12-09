package com.example.spacechase;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The SoundEngine class handles playing audio files such as music
 * and sound effects.
 * @author Karl Clifford
 * @version 1.0.0
 */
public class SoundEngine {
    /**
     * Volume the game music should play at.
     */
    public static final int MUSIC_VOLUME = 30;
    /**
     * Volume that sound effects should play at.
     */
    public static final int SOUND_EFFECT_VOLUME = 100;
    /**
     * The maximum volume sound is allowed to play at.
     */
    public static final double MAX_VOLUME = 100.0;

    /**
     * The value that determines the rate at which the volume fades out.
     */
    public static final double VOLUME_DECREMENT_VALUE = 0.1;

    /**
     * Map to store the file paths for the different sound effects.
     */
    private final Map<Sound, String> soundFiles = new HashMap<>();

    /**
     * Media player for playing the music.
     * @see javafx.scene.media
     */
    private MediaPlayer mediaPlayer;

    /**
     * The SoundEngine constructor populates all the sound effects with
     * their respective file path locations, so they can be run in game.
     */
    public SoundEngine() {

        // Populate the map with the file paths for the different sound effects.
        soundFiles.put(Sound.MENU_MUSIC,
                "sounds/kim-lightyear-angel-eyes-chiptune-edit-110226.mp3");
        soundFiles.put(Sound.LEVEL_MUSIC,
                "sounds/kim-lightyear-legends-109307.mp3");
        soundFiles.put(Sound.REWARD, "sounds/collectcoin-6075.mp3");
        soundFiles.put(Sound.CLOCK,
                "sounds/time-passing-sound-effect-fast-clock-108403.mp3");
        soundFiles.put(Sound.WIN, "sounds/winsquare-6993.mp3");
        soundFiles.put(Sound.LOOSE, "sounds/game-over-38511.mp3");
    }

    // Enumerated type for the different sound effects.
    public enum Sound {
        /**
         * Will form part of the key to retrieve the menu music file.
         */
        MENU_MUSIC,
        /**
         * Will form part of the key to retrieve the level music file.
         */
        LEVEL_MUSIC,
        /**
         * Will form part of the key to retrieve the reward sound effect file.
         */
        REWARD,
        /**
         * Will form part of the key to retrieve the clock sound effect file.
         */
        CLOCK,
        /**
         * Will form part of the key to retrieve the win sound effect file.
         */
        WIN,
        /**
         * Will form part of the key to retrieve the loose sound effect file.
         */
        LOOSE


    }

    /**
     * The playSound methods plays an audio file.
     * @param effect The file to play.
     * @param volume The volume to play the media at.
     * @param loop Whether the media should loop, true for yes.
     */
    public void playSound(Sound effect, int volume, boolean loop) {
        // Load the audio file for the specified sound effect.
        Media sound = new Media(
                Objects.requireNonNull(getClass()
                        .getResource(getFilename(effect))).toString());

        // Create a media player for the audio file.
        this.mediaPlayer = new MediaPlayer(sound);

        // Set the volume of the media player.
        mediaPlayer.setVolume(volume / MAX_VOLUME);
        /*
         * Play the sound effect on a loop if loop is true,
         * otherwise just play it once.
         */
        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }

        // Start playing the sound effect.
        mediaPlayer.play();
    }

    /**
     * Stops the media that's playing.
     */
    public void stopMusic() {
        // Stop the music immediately.
        mediaPlayer.stop();
    }

    /**
     * Stops the media that's playing with a gradual fade out.
     * @param duration The duration to fade the music for.
     */
    public void fadeOutMusic(long duration) {
        // Get the current volume of the music.
        double volume = mediaPlayer.getVolume();

        // Gradually decrease the volume of the music over time
        // until it reaches 0 (mute).
        while (volume > 0) {
            volume -= VOLUME_DECREMENT_VALUE;
            mediaPlayer.setVolume(volume);
            // Pause the thread before decreasing the volume again.
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // Volume has already reached zero.
                System.out.println("Error: Has the sound already stopped?");
            }
        }

        // Stop the music when the volume reaches 0.
        mediaPlayer.stop();
    }

    /**
     * Gets the file path for the specified sound effect.
     * @param effect The sound effect to get.
     * @return the enumerated key to retrieve the media file path.
     */
    public String getFilename(Sound effect) {
        // Look up the file path for the specified sound effect
        // in the map and return it.
        return soundFiles.get(effect);
    }

    /**
     * Sets the playback speed of the media player.
     * @param speed The speed to use.
     */
    public void setPlaybackSpeed(double speed) {
        // Set the playback speed of the media player.
        mediaPlayer.setRate(speed);
    }

}

