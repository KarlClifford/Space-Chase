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
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Level {
    /**
     * x offset of canvas.
     */
    public static final int CANVAS_OFFSET_X = 100;
    /**
     * spacing of tiles.
     */
    public static final int TILE_SPACING = 5;
    /**
     * spacing of hbox.
     */
    private static final int HBOX_SPACING = 10;
    /**
     * normal font size.
     */
    private static final int NORM_FONT_SIZE = 16;
    /**
     * id of the level.
     */
    private final int id;
    /**
     * file of the level.
     */
    private final File file;
    /**
     * score of the level.
     */
    private int score;
    /**
     * time of the level.
     */
    private double time;
    /**
     * tile map.
     */
    private final Tile[][] tileMap;
    /**
     * clock of the level.
     */
    private final GameClock clock;
    /**
     * state of the level.
     */
    private final GameState state;
    /**
     * player in level.
     */
    private Player player;
    /**
     * characters of the level.
     */
    private ArrayList<Character> characters;
    /**
     * items of the level.
     */
    private ArrayList<Item> items;
    /**
     * label of the time.
     */
    private Label timeLabel;
    /**
     * label of the score.
     */
    private Label scoreLabel;
    /**
     * scene of the level.
     */
    private Scene scene;
    /**
     * pane of the level.
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
        this.state = new GameState(this);
        this.clock = new GameClock(this);
    }

    /**
     * @return id of the level.
     */
    public int getId() {
        return id;
    }

    /**
     * @return file of the level.
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the scene of the level.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @return all the characters in the map.
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * @return all the items in the map.
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * @return game time.
     */
    public double getTime() {
        return time;
    }

    /**
     * @return game clock.
     */
    public GameClock getClock() {
        return clock;
    }

    /**
     * @return state of the game.
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the new time and updates it in the label.
     *
     * @param time new time to be set.
     */
    public void setTime(double time) {
        this.time = time;
        timeLabel.setText(String.format("Time: %.2f", time));
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
        timeLabel = new Label();
        timeLabel.setText("Time: " + time);
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        scoreLabel = new Label();
        scoreLabel.setText("Score: " + score);
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        Button pauseButton = state.createPauseButton();

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
     * Draws all the tiles and entities.
     * @param group pane of the canvas.
     */
    private void draw(Group group) {
        Canvas canvas = (Canvas) group.getChildren().get(0);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int y = 0; y < tileMap.length; y++) {
            Tile[] row = tileMap[y];

            for (int x = 0; x < row.length; x++) {
                Tile tile = row[x];

                char[] colours = tile.getColours();

                for (Direction direction : Direction.values()) {
                    Tile link = getLinkTile(tile, direction);
                    tile.setLink(direction, link);

                    Tile neighbour = getNeighbourTile(tile, direction);
                    tile.setNeighbour(direction, neighbour);
                }

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
                if (item != null) {
                    item.setLevel(this);
                    item.setTile(tile);

                    ImageView image = item.createImageView(tile.getX(),
                            tile.getY());
                    group.getChildren().add(image);
                    items.add(item);
                }

                Character character = tile.getCharacter();
                if (character != null) {
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

        boolean outOfBounds = y < 0 || y > tileMap.length - 1
                || x < 0
                || x > tileMap[0].length - 1;

        if (outOfBounds) {
            return null;
        } else {
            current = tileMap[y][x];
            return current.equalsColour(goal)
                    ? current
                    : recursiveSearchLink(current, goal, direction);
        }
    }

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

        try {
            File nextFile = Data.copyLevel(id + 1, playerName);
            if (nextFile == null) {
                Controller controller = new Controller();
                controller.loadFxml("fxml/levelMenu.fxml");
            } else {
                Level newLevel = Data.readLevel(nextFile);
                newLevel.start();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * @return string of all contents in level.
     */
    @Override
    public String toString() {
        int height = tileMap.length;
        int width = tileMap[0].length;

        String tiles = String.join("\n",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(Tile::getColours)
                                        .map(String::valueOf)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));

        String characters = String.join(" ",
                Arrays.stream(tileMap)
                        .map(ts -> String.join(" ",
                                Arrays.stream(ts)
                                        .map(Tile::getCharacter)
                                        .filter(Objects::nonNull)
                                        .map(Character::toString)
                                        .toArray(String[]::new)))
                        .toArray(String[]::new));

        String items = String.join(" ",
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


        return String.format("%d %d %.2f %d\n%s\n%s %s",
                width,
                height,
                time,
                score,
                tiles,
                characters,
                items);
    }
}
