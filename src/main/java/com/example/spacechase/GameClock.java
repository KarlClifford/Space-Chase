package com.example.spacechase;

import javafx.animation.AnimationTimer;

import java.time.Clock;
import java.util.Arrays;

/**
 * This class represents a game clock. A game clock contains
 * components of a game loop and time interval for each character update.
 *
 * @author Rami Abdulrazzaq
 * @author Tristan Tsang
 * @version 1.0.1
 */
public class GameClock {
    /**
     * Milliseconds in one second.
     */
    private static final double MILLISECONDS = 1000.0;
    /**
     * Time interval between each tick of the clock.
     */
    private static final double CLOCK_TICK = 50.0;
    /**
     * Time interval between each tick of the player.
     */
    private static final double PLAYER_TICK = 100.0;
    /**
     * Time interval between each tick of the npc.
     */
    private static final double NPC_TICK = 1000.0;

    /**
     * Time until bomb explodes.
     */
    private static final double BOMB_EXPLODE = 3000.0;
    /**
     * Determines whether game is running.
     */
    private boolean run = true;
    /**
     * Level of the clock.
     */
    private final Level level;

    /**
     * Creates a new GameClock instance.
     *
     * @param level level of the clock.
     */
    public GameClock(Level level) {
        this.level = level;
    }

    /**
     * Initializes the countdown of time and updates of characters.
     */
    public void initialize() {
        run = true;
        AnimationTimer timer = new AnimationTimer() {
            final Clock clock = java.time.Clock.systemDefaultZone();
            private long last = clock.millis();
            private long playerLast = last;
            private long npcLast = last;

            @Override
            public void handle(long l) {
                long now = clock.millis();
                double time = level.getTime();
                final Character[] characters = level.getCharacters()
                        .toArray(Character[]::new)
                        .clone();
                final Bomb[] bombs = level.getItems()
                        .stream()
                        .filter(item -> item instanceof Bomb)
                        .toArray(Bomb[]::new)
                        .clone();
                // If there is no more time, then stop the timer.
                if (time <= 0) {
                    this.stop();
                }
                // If game is running, update every character in the map.
                if (run) {
                    /* If last tick is more than or equal to the clock tick,
                     then decrease the time by time interval. */
                    if (now - last >= CLOCK_TICK) {
                        last = now;
                        level.setTime(time - CLOCK_TICK / MILLISECONDS);
                    }
                    /* If last tick is more than or equal to the player tick,
                     then update the player in level. */
                    if (now - playerLast >= PLAYER_TICK) {
                        // Update each player in all the characters of level.
                        for (Player player : Arrays.stream(characters)
                                .filter(c -> c instanceof Player)
                                .toArray(Player[]::new)) {
                            player.update();
                        }
                        playerLast = now;
                    }
                    /* If last tick is more than or equal to the enemy tick,
                     then update the enemies in level. */
                    if (now - npcLast >= NPC_TICK) {
                        /* Update each non-player in all the characters
                         of level. */
                        for (Character character : characters) {
                            if (!(character instanceof Player)) {
                                character.update();
                            }
                        }
                        npcLast = now;
                    }
                    for (Bomb bomb : bombs) {
                        /*if bomb is not triggered
                        then check if it can be triggered and
                        trigger it.
                         */
                        if (!bomb.getIsTriggered()) {
                            if (bomb.canTrigger()) {
                                bomb.trigger(now);
                            }
                            //if the bomb isn't detonated then detonate it.
                        } else if (!bomb.getIsDetonated()) {
                            /*if 3 seconds have passed
                            since it has been triggered
                            then detonate it.
                             */
                            if (now - bomb.getInitTime() >= BOMB_EXPLODE) {
                                bomb.setIsDetonated();
                                bomb.destroyItems();
                            }
                        }
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Sets run.
     *
     * @param r boolean of run.
     */
    public void setRun(boolean r) {
        run = r;
    }

}
