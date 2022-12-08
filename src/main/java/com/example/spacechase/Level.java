package com.example.spacechase;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class represents a level. A level contains
 * components of id, file, score, tile map, clock, state,
 * characters, items, player, labels and scene. It can be
 * initialised by start, restarted by restart and proceed
 * to next level by next.
 *
 * @author Tristan Tsang
 * @author Ben Thornber
 * @version 1.0.1
 */
public class Level {
    /**
     * X offset of canvas.
     *
     * @see javafx.scene.canvas
     */
    public static final int CANVAS_OFFSET_X = 100;
    /**
     * Spacing of tiles.
     */
    public static final int TILE_SPACING = 5;
    /**
     * Fxml file path of level menu.
     *
     * @see javafx.fxml
     */
    private static final String LEVEL_MENU_FXML_PATH = "fxml/levelMenu.fxml";
    /**
     * Fxml file path of pause menu.
     *
     * @see javafx.fxml
     */
    private static final String PAUSE_MENU_FXML_PATH = "fxml/pauseMenu.fxml";
    /**
     * Fxml file path of level ended menu.
     *
     * @see javafx.fxml
     */
    private static final String LEVEL_ENDED_MENU_FXML_PATH =
            "fxml/levelEndedMenu.fxml";
    /**
     * Spacing of HBox.
     *
     * @see javafx.scene.layout.HBox
     */
    private static final int HBOX_SPACING = 10;
    /**
     * Normal font size.
     *
     * @see javafx.scene.text.Font
     */
    private static final int NORM_FONT_SIZE = 16;
    /**
     * Font family.
     *
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
     * Scene of the level.
     *
     * @see javafx.scene
     */
    private Scene scene;
    /**
     * Pane of the level.
     *
     * @see javafx.scene.layout.BorderPane
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
     * Gets the scene of the level.
     *
     * @return the scene of the level.
     */
    public Scene getScene() {
        return scene;
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
     * Sets the new time and updates it in the label.
     *
     * @param time new time to be set.
     */
    public void setTime(double time) {
        this.time = time;
        timeLabel.setText(String.format("Oxygen: %.2f", time));
    }

    /**
     * Removes the item from items.
     *
     * @param item item.
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Removes the character from characters.
     *
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

        Canvas canvas = new Canvas(App.STAGE_WIDTH, App.STAGE_HEIGHT);

        Group group = new Group(canvas);

        draw(group);

        pane = new BorderPane();
        pane.setTop(hBox);
        pane.setLeft(group);
        pane.setStyle("-fx-background-color: BLACK");

        scene = new Scene(pane);
        Controller.setScene(scene);

        player.initialize(scene);
        clock.initialize();
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
     *
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
     * Ends the level by stopping the game clock
     * and loads the level ended menu.
     *
     * @param isCleared level is cleared or not.
     */
    public void end(boolean isCleared) {
        clock.setRun(false);

        LevelEndedMenuController controller = (LevelEndedMenuController)
                new Controller()
                        .loadFxml(LEVEL_ENDED_MENU_FXML_PATH);
        controller.start(this, isCleared);
    }

    /**
     * Draws all the tiles and entities.
     *
     * @param group pane of the canvas.
     */
    private void draw(Group group) {
        Canvas canvas = (Canvas) group.getChildren().get(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();

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
                    Color color = getColorFromChar(c);

                    gc.setFill(color);
                    gc.fillRect((Tile.TILE_SIZE + TILE_SPACING) * x
                                    + Tile.TILE_SIZE / 2 * (i % 2),
                            CANVAS_OFFSET_X
                                    + (Tile.TILE_SIZE + TILE_SPACING) * y
                                    + Tile.TILE_SIZE / 2
                                    * (i < colours.length / 2 ? 0 : 1),
                            Tile.TILE_SIZE / 2,
                            Tile.TILE_SIZE / 2);
                }

                Item item = tile.getItem();
                /* If there is an item, assign level and tile to item,
                create an image and draw it. */
                if (item != null) {
                    item.setLevel(this);
                    item.setTile(tile);

                    ImageView image = item.createImageView();
                    group.getChildren().add(image);
                    items.add(item);
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

                    character.setLevel(this);
                    character.createImageView();
                    character.draw();

                    ImageView image = character.getImageView();
                    group.getChildren().add(image);
                    characters.add(character);
                }
            }
        }
    }

    /**
     * Gets colour from a given character.
     *
     * @param c character of the colour.
     * @return the colour.
     */
    private Color getColorFromChar(char c) {
        return switch (c) {
            case 'R' -> Color.RED;
            case 'Y' -> Color.YELLOW;
            case 'B' -> Color.BLUE;
            case 'G' -> Color.GREEN;
            default -> Color.BLACK;
        };
    }

    /**
     * Gets the linking tile in direction.
     *
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
     *
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
     *
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
     * Restarts the level.
     */
    public void restart() {
        String playerName = file.getParentFile().getName();

        /* Tries to copy the same level and replace the current level.
         Catches if there is an I/O exception while loading the file. */
        try {
            File file = Data.copyLevel(id, playerName);
            Level newLevel = Data.readLevel(file);
            newLevel.start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Starts the next level.
     */
    public void next() {
        String playerName = file.getParentFile().getName();

        /* Tries to copy the next level to player profile.
         Catches if there is an I/O exception while loading the file. */
        try {
            File nextFile = Data.copyLevel(id + 1, playerName);
            /* Goes back to level menu if there's no next level.
             Otherwise, start the next level. */
            if (nextFile == null) {
                Controller controller = new Controller();
                controller.loadFxml(LEVEL_MENU_FXML_PATH);
            } else {
                Level newLevel = Data.readLevel(nextFile);
                newLevel.start();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Call and concat all tiles toString from the level map.
     *
     * @return string of all tiles from the map.
     */
    private String getMapString() {
        return String.join("\n",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(Tile::getColours)
                                        .map(String::valueOf)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));
    }

    /**
     * Call and concat all characters toString from the map.
     *
     * @return string of all characters from the map.
     */
    private String getCharactersString() {
        return String.join(" ",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(Tile::getCharacter)
                                        .filter(Objects::nonNull)
                                        .map(Character::toString)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));
    }

    /**
     * Call and concat all items toString from the map.
     *
     * @return string of all items from the map.
     */
    private String getItemsString() {
        return String.join(" ",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(t -> {
                                            Item item = t.getItem();
                                            return item == null
                                                    ? null
                                                    : item.toString()
                                                    + t.getX()
                                                    + t.getY();
                                        })
                                        .filter(Objects::nonNull)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));
    }

    /**
     * Returns the height of the map, width of the map, time of the level,
     * score of the level, string data of all tiles, characters and items.
     *
     * @return string of all contents in level.
     */
    @Override
    public String toString() {
        int height = tileMap.length;
        int width = tileMap[0].length;

        return String.format("%d %d %.2f %d\n%s\n%s %s",
                width,
                height,
                time,
                score,
                getMapString(),
                getCharactersString(),
                getItemsString());
    }
}
