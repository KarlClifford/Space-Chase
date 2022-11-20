package com.example.spacechase;

import javafx.animation.AnimationTimer;
import java.time.Clock;
import java.util.Arrays;

/**
 * This class represents a game clock. A game clock contains
 * components of a game loop and time interval for each character update.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class GameClock {
    /**
     * milliseconds in one second.
     */
    private static final double MILLISECONDS = 1000.0;
    /**
     * time interval between each tick of the clock.
     */
    private static final double CLOCK_TICK = 50.0;
    /**
     * time interval between each tick of the player.
     */
    private static final double PLAYER_TICK = 100.0;
    /**
     * time interval between each tick of the npc.
     */
    private static final double NPC_TICK = 1000.0;
    /**
     * determines whether game is running.
     */
    private boolean run = true;
    /**
     * level of this clock.
     */
    private final Level level;

    /**
     * Creates a new GameClock instance.
     * @param level level
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
                final Character[] characters = level
                        .getCharacters()
                        .toArray(Character[]::new)
                        .clone();

                if (time <= 0) {
                    this.stop();
                }

                if (run) {
                    if (now - last >= CLOCK_TICK) {
                        last = now;
                        level.setTime(time - CLOCK_TICK / MILLISECONDS);
                    }

                    if (now - playerLast >= PLAYER_TICK) {
                        for (Player player : Arrays.stream(characters)
                                .filter(c -> c instanceof Player)
                                .toArray(Player[]::new)) {
                            player.update();
                        }

                        playerLast = now;

                    } else if (now - npcLast >= NPC_TICK) {
                        for (Character character : characters) {
                            if (!(character instanceof Player)) {
                                character.update();
                            }
                        }

                        npcLast = now;
                    }
                }
            }
        };

        timer.start();
    }

    /**
     * @param r boolean of run.
     */
    public void setRun(boolean r) {
        run = r;
    }
}
