package com.example.spacechase.models;

import com.example.spacechase.App;
import com.example.spacechase.models.items.Note;
import com.example.spacechase.models.level.GameClock;
import com.example.spacechase.controllers.Controller;
import com.example.spacechase.controllers.LevelEndedMenuController;
import com.example.spacechase.controllers.PauseMenuController;
import com.example.spacechase.models.characters.Character;
import com.example.spacechase.models.characters.Player;
import com.example.spacechase.models.items.Item;
import com.example.spacechase.models.level.Tile;
import com.example.spacechase.services.SoundEngine;
import com.example.spacechase.utils.Data;
import com.example.spacechase.utils.Direction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a level. A level contains
 * components of id, file, score, tile map, clock, state,
 * characters, items, player, labels and scene. It can be
 * initialised by start, restarted by restart and proceed
 * to next level by next.
 *
 * @author Tristan Tsang
 * @author Karl Clifford
 * @author Alex Hallsworth
 * @author Ben Thornber
 * @version 1.0.1
 */
public class Level {
    /**
     * Spacing of tiles.
     */
    public static final double TILE_SPACING = 0;
    /**
     * Fxml file path of end credits.
     * @see javafx.fxml
     */
    private static final String END_CREDITS_FXML_PATH = "fxml/endCredits.fxml";
    /**
     * Fxml file path of pause menu.
     * @see javafx.fxml
     */
    private static final String PAUSE_MENU_FXML_PATH = "fxml/pauseMenu.fxml";
    /**
     * Fxml file path of level ended menu.
     * @see javafx.fxml
     */
    private static final String LEVEL_ENDED_MENU_FXML_PATH =
            "fxml/levelEndedMenu.fxml";
    /**
     * Spacing of HBox.
     * @see javafx.scene.layout.HBox
     */
    private static final int HBOX_SPACING = 10;
    /**
     * Normal font size.
     * @see javafx.scene.text.Font
     */
    private static final int NORM_FONT_SIZE = 16;
    /**
     * Font family.
     * @see javafx.scene.text.Font
     */
    private static final String FONT_FAMILY = "neuropol x rg";
    /**
     * ID of the level.
     */
    private final int id;
    /**
     * File of the level.
     */
    private final File file;
    /**
     * Score of the level.
     */
    private int score;
    /**
     * Time of the level.
     */
    private double time;
    /**
     * Tile map.
     */
    private final Tile[][] tileMap;
    /**
     * Clock of the level.
     */
    private final GameClock clock;
    /**
     * Player in level.
     */
    private Player player;
    /**
     * Characters of the level.
     */
    private final ArrayList<Character> characters;
    /**
     * Items of the level.
     */
    private final ArrayList<Item> items;
    /**
     * Label of the time.
     */
    private Label timeLabel;
    /**
     * Label of the score.
     */
    private Label scoreLabel;
    /**
     * Border pane of the level.
     * @see javafx.scene.layout.Pane
     */
    private BorderPane pane;

    /**
     * Creates a level object.
     *
     * @param id      id of the level.
     * @param file    file of the level.
     * @param time    game time.
     * @param score   score of the player.
     * @param tileMap map that contains all the tiles.
     */
    public Level(int id, File file, double time, int score, Tile[][] tileMap) {
        this.id = id;
        this.file = file;
        this.time = time;
        this.score = score;
        this.tileMap = tileMap;
        this.characters = new ArrayList<>();
        this.items = new ArrayList<>();
        this.clock = new GameClock(this);
    }

    /**
     * Gets id of the level.
     *
     * @return id of the level.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the file that the level is read from.
     *
     * @return file of the level.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the pane of the level.
     * @return pane of the level.
     */
    public Pane getPane() {
        return pane;
    }

    /**
     * Gets the characters in the level.
     *
     * @return all the characters in the map.
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Gets the items in the level.
     *
     * @return all the items in the map.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets the current game time of the level.
     *
     * @return game time.
     */
    public double getTime() {
        return time;
    }

    /**
     * Gets the game clock instance of the level.
     *
     * @return game clock of the level.
     */
    public GameClock getClock() {
        return clock;
    }


    /**
     * @return tileMap
     */
    public Tile[][] getTileMap() {
        return tileMap;
    }

    /**
     * Sets the new time and updates it in the label.
     *
     * @param time new time to be set.
     */
    public void setTime(double time) {
        this.time = time;
        timeLabel.setText(String.format("Oxygen: %.2f", time));
    }

    /**
     * Gets the player's current score in the level.
     * @return current score of the player.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the new score and updates it in the label.
     * @param score new score to be set.
     */
    public void setScore(int score) {
        this.score = score;
        scoreLabel.setText(String.format("Score: %s", score));
    }

    /**
     * Removes the item from items.
     * @param item item.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Removes the character from characters.
     * @param character character.
     */
    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    /**
     * Draws out all the labels, tiles, character, and items in level.
     * and starts the level.
     */
    public void start() {
        createTimeLabel();
        createScoreLabel();

        Button pauseButton = createPauseButton();

        HBox hBox = new HBox();
        hBox.setSpacing(HBOX_SPACING);
        hBox.setStyle("-fx-background-color: BLACK");
        hBox.getChildren().addAll(pauseButton, timeLabel, scoreLabel);

        AnchorPane anchorPane = new AnchorPane();
        draw(anchorPane);

        pane = new BorderPane();
        pane.setTop(hBox);
        pane.setCenter(anchorPane);
        pane.setStyle("-fx-background-color: BLACK");
        pane.setPrefWidth(App.STAGE_WIDTH);
        pane.setPrefHeight(App.STAGE_HEIGHT);

        Controller.setRoot(pane);

        player.initialize();
        clock.initialize();

        // Stop the playing music and start the level music.
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.LEVEL_MUSIC,
                SoundEngine.getMusicVolume(),
                true);
    }

    /**
     * Creates a label for the time of level.
     */
    private void createTimeLabel() {
        timeLabel = new Label("Oxygen: " + time);
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setFont(Font.font(FONT_FAMILY, NORM_FONT_SIZE));
    }

    /**
     * Creates a label for the score of level.
     */
    private void createScoreLabel() {
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(FONT_FAMILY, NORM_FONT_SIZE));
    }

    /**
     * Creates a pause button that can pause the level
     * by stopping the game clock and showing the pause
     * level menu.
     * @return pause button.
     */
    private Button createPauseButton() {
        Button pauseButton = new Button("||");
        pauseButton.setFont(Font.font(FONT_FAMILY, NORM_FONT_SIZE));
        pauseButton.setOnMouseClicked(e -> {
            clock.setRun(false);

            Data.saveLevel(this);
            PauseMenuController controller = (PauseMenuController)
                    new Controller()
                            .loadFxml(PAUSE_MENU_FXML_PATH);
            controller.start(this);
        });

        return pauseButton;
    }

    /**
     * Draws all the tiles and entities.
     * @param pane pane of the level.
     */
    private void draw(AnchorPane pane) {
        /* Loops through each tile of the map and draws it out,
         sets each tile their neighbour and link tile if possible.
         Draws the item and character if they exist on a tile. */
        for (int y = 0; y < tileMap.length; y++) {
            Tile[] row = tileMap[y];
            // For every tile in a row.
            for (int x = 0; x < row.length; x++) {
                Tile tile = row[x];
                char[] colours = tile.getColours();

                /* For every direction, tries to set a link and neighbour
                 if there is any for the tile. */
                for (Direction direction : Direction.values()) {
                    Tile link = getLinkTile(tile, direction);
                    tile.setLink(direction, link);

                    Tile neighbour = getNeighbourTile(tile, direction);
                    tile.setNeighbour(direction, neighbour);
                }

                // For every colour in the tile, draws it onto the canvas.
                for (int i = 0; i < colours.length; i++) {
                    char c = colours[i];
                    final Color color = getColorFromChar(c);
                    final double rectSize = Tile.TILE_SIZE / 2;
                    Rectangle rectangle = new Rectangle(
                            (Tile.TILE_SIZE + TILE_SPACING) * x
                                    * App.SCALE_X
                            + rectSize * (i % 2),
                            (Tile.TILE_SIZE + TILE_SPACING) * y
                                    * App.SCALE_Y
                            + rectSize * (i < colours.length / 2 ? 0 : 1),
                            rectSize,
                            rectSize);
                    rectangle.setFill(color);
                    rectangle.setViewOrder(2);
                    pane.getChildren().add(rectangle);
                }

                Item item = tile.getItem();
                /* If there is an item, assign level and tile to item,
                create an image and draw it. */
                if (item != null) {
                    ImageView image = item.getImageView();
                    image.setViewOrder(1);
                    pane.getChildren().add(image);
                }

                Character character = tile.getCharacter();
                /* If there is a character, assign level and tile to
                 character, create an image and draw it. */
                if (character != null) {
                    /* If character is a player, then assign it to
                     the player of this level. */
                    if (character instanceof Player p) {
                        player = p;
                    }

                    ImageView image = character.getImageView();
                    image.setViewOrder(0);
                    pane.getChildren().add(image);
                }
            }
        }
    }

    /**
     * Gets colour from a given character.
     * @param c character of the colour.
     * @return the colour.
     */
    private Color getColorFromChar(char c) {
        return switch (c) {
            case 'R' -> Color.RED;
            case 'Y' -> Color.YELLOW;
            case 'B' -> Color.BLUE;
            case 'G' -> Color.GREEN;
            case 'C' -> Color.CYAN;
            case 'M' -> Color.MAGENTA;
            default -> Color.BLACK;
        };
    }

    /**
     * Gets the linking tile in direction.
     * @param tile      initial tile.
     * @param direction direction.
     * @return tile in the given direction of the given tile.
     */
    private Tile getLinkTile(Tile tile, Direction direction) {
        return recursiveSearchLink(tile, tile, direction);
    }

    /**
     * Recursively search through each tile that links with
     * the current tile and return the tile that has the
     * same colour(s) with initial tile.
     * @param current   current tile.
     * @param goal      initial tile.
     * @param direction direction
     * @return the result of the tile.
     */
    private Tile recursiveSearchLink(Tile current, Tile goal,
                                     Direction direction) {
        int a = switch (direction) {
            case LEFT -> -1;
            case RIGHT -> 1;
            default -> 0;
        };

        int b = switch (direction) {
            case UP -> -1;
            case DOWN -> 1;
            default -> 0;
        };


        int x = current.getX() + a;
        int y = current.getY() + b;

        /* Index is out of bounds when:
         y is below 0 or more than the number of rows in tile map;
         x is below 0 or more than the number of tiles in a row. */
        boolean outOfBounds = y < 0 || y > tileMap.length - 1
                || x < 0
                || x > tileMap[0].length - 1;

        /* If the index is out of bounds, then return null
         as there are no tiles that can be linked within
         this direction.
         Otherwise, get the next tile and checks if it
         has a linkable tile or call recursively until
         one is found. */
        if (outOfBounds) {
            return null;
        } else {
            current = tileMap[y][x];
            return current.equalsColour(goal)
                    ? current
                    : recursiveSearchLink(current, goal, direction);
        }
    }

    /**
     * Gets the neighbour tile of a tile.
     * @param tile      initial tile.
     * @param direction direction to search for.
     * @return the neighbour tile of the given tile.
     */
    private Tile getNeighbourTile(Tile tile, Direction direction) {
        int x = tile.getX();
        int y = tile.getY();
        return switch (direction) {
            case UP -> y > 0 ? tileMap[y - 1][x] : null;
            case RIGHT -> x < tileMap[0].length - 1 ? tileMap[y][x + 1] : null;
            case DOWN -> y < tileMap.length - 1 ? tileMap[y + 1][x] : null;
            case LEFT -> x > 0 ? tileMap[y][x - 1] : null;
        };
    }

    /**
     * Ends the level by stopping the game clock
     * and loads the level ended menu.
     * @param isCleared level is cleared or not.
     */
    public void end(boolean isCleared) {
        clock.setRun(false);

        String playerName = file.getParentFile().getName();

        /* Tries to replace this level with fresh start to player profile.
         Copy next level if level is cleared.
         Catches if there is an I/O exception while loading the file. */
        try {
            Data.copyLevel(id, playerName);

            /* Copy the file for next level to player profile if level is
             cleared. */
            if (isCleared) {
                Data.addHighScore(id, playerName, (score + (int) time));
                Data.copyLevel(id + 1, playerName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LevelEndedMenuController controller = (LevelEndedMenuController)
                new Controller()
                        .loadFxml(LEVEL_ENDED_MENU_FXML_PATH);
        controller.start(this, isCleared);

        // Stop the playing music.
        App.MUSIC_PLAYER.stopMusic();
        // Initialise the sound engine to play a sound effect.
        if (isCleared) {
            // Play win sound effect.
            App.MUSIC_PLAYER.playSound(
                    SoundEngine.Sound.WIN,
                    SoundEngine.getMusicVolume(), false);
        } else {
            // Play loose sound effect.
            App.MUSIC_PLAYER.playSound(
                    SoundEngine.Sound.LOOSE,
                    SoundEngine.getMusicVolume(), false);
        }
    }

    /**
     * Restarts the level.
     */
    public void restart() {
        /* Tries to load the same level.
         Catches if there is an I/O exception while loading the file. */
        try {
            String playerName = file.getParentFile().getName();
            Data.copyLevel(id, playerName);
            Level newLevel = Data.readLevel(file);
            newLevel.start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        // Stop the playing music and start the level music.
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.LEVEL_MUSIC,
                SoundEngine.getMusicVolume(),
                true);
    }

    /**
     * Starts the next level.
     */
    public void next() {

        /* Tries to load the next level from player profile.
         Catches if there is an I/O exception while loading the file. */
        try {
            String playerName = file.getParentFile().getName();
            Data.copyLevel(id, playerName);
            File nextFile = new File(String.format("%s/%d.txt",
                    file.getParentFile().getPath(),
                    id + 1));
            /* Goes back to end credits if there's no next level.
             Otherwise, start the next level. */
            if (!nextFile.exists()) {
                Controller controller = new Controller();
                controller.loadFxml(END_CREDITS_FXML_PATH);
                return;
            } else {
                Level newLevel = Data.readLevel(nextFile);
                newLevel.start();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Stop the playing music and start the level music.
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.LEVEL_MUSIC,
                SoundEngine.getMusicVolume(),
                true);
    }

    /**
     * Call and concat all tiles toString from the level map.
     * @return string of all tiles from the map.
     */
    private String getMapString() {
        return String.join("\n",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(Tile::toString)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));
    }

    /**
     * Gets all messages in string.
     * @return all messages in string.
     */
    private String getMessageString() {
        return String.join("",
                Arrays.stream(tileMap)
                .map(ts -> String.join("\n",
                        Arrays.stream(ts)
                                .map(Tile::getItem)
                                .filter(item -> item instanceof Note)
                                .map(item -> (Note) item)
                                .map(Note::getMessage)
                                .toArray(String[]::new)))
                .toArray(String[]::new));
    }

    /**
     * Returns the height of the map, width of the map, time of the level,
     * score of the level, string data of all tiles, characters and items.
     * @return string of all contents in level.
     */
    @Override
    public String toString() {
        int height = tileMap.length;
        int width = tileMap[0].length;

        return String.format("%d %d %.2f %d\n%s\n%s",
                width,
                height,
                time,
                score,
                getMapString(),
                getMessageString());
    }
}
