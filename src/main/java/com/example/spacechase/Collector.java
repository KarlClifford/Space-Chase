package com.example.spacechase;

/**
 * This class represents a collector. A collector handles
 * the pickup process for in game items.
 *
 * @author Rami Abdulrazzaq
 * @author Tristan Tsang
 * @version 1.0.1
 */
public abstract class Collector extends Character {

    /**
     * checks if the link is not a bomb if(true) then,
     * checks if the link is a trigger and
     * if the bomb is not triggered if(true) then,
     * trigger the bomb and changeTile(link) else just changeTile(link).
     *
     * @param link the tile to move on to.
     */
    @Override
    protected void changeTile(Tile link) {
       if (!(link.getItem() instanceof Bomb)) {
           super.changeTile(link);
       }
    }

    /**
     * Plays the sound effect of reward.
     */
    private void playRewardSound() {
        // Initialise a new sound engine, so we can play a sound effect.
        SoundEngine soundEngine = new SoundEngine();
        // Play the reward sound effect.
        soundEngine.playSound(
                SoundEngine.Sound.REWARD,
                SoundEngine.SOUND_EFFECT_VOLUME,
                false);
    }

    /**
     * Collector picks up item from the tile.
     */
    @Override
    protected void update() {
        super.update();
        Item item = tile.getItem();
        // If there is an item, interact with it.
        if (item != null) {
            if (this instanceof Player) {
                playRewardSound();
            }
            item.interact(this);
        }
    }
}
