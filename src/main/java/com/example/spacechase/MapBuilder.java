/**
 * MapBuilder.java
 * This class creates a javafx window that allows you to edit a map
 * and save it as a .txt file.
 *
 * @author Tristan Tsang
 * @version 2.0
 *
 * width int
 * height int
 * time double
 * drag Node
 */

package com.example.spacechase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MapBuilder extends Application {
    /**
     * path to images.
     */
    private static final String PATH_TO_IMAGES = "images/";
    /**
     * width of the stage.
     */
    private static final int STAGE_WIDTH = 800;
    /**
     * height of the stage.
     */
    private static final int STAGE_HEIGHT = 500;
    /**
     * resizeable of the stage.
     */
    private static final boolean STAGE_RESIZEABLE = true;
    /**
     * spacing between elements in variable box.
     */
    private static final int VAR_BOX_SPACING = 20;
    /**
     * size of tiles.
     */
    private static final double TILE_SIZE = 25.0;
    /**
     * spacing of tiles.
     */
    private static final double TILE_SPACING = 5.0;
    /**
     * length of string data.
     */
    private static final int DATA_LENGTH = 4;
    /**
     * width of the map.
     */
    private int width;
    /**
     * height of the map.
     */
    private int height;
    /**
     * time of the map.
     */
    private double time;
    /**
     * drag object of the mouse.
     */
    private Node drag;

    /**
     * Initializes the builder.
     *
     * @param stage primary stage.
     */
    @Override
    public void start(final Stage stage) {
        final BorderPane pane = new BorderPane();
        final AnchorPane anchorPane = new AnchorPane();

        TextField widthTextField = new TextField();
        TextField heightTextField = new TextField();
        TextField timeTextField = new TextField();

        widthTextField.setText("0");
        heightTextField.setText("0");
        timeTextField.setText("0");

        Label widthLabel = new Label();
        Label heightLabel = new Label();
        Label timeLabel = new Label();

        widthLabel.setText("width");
        heightLabel.setText("height");
        timeLabel.setText("time");

        VBox widthVBox = new VBox();
        VBox heightVBox = new VBox();
        VBox timeVBox = new VBox();

        widthVBox.getChildren().addAll(widthLabel, widthTextField);
        heightVBox.getChildren().addAll(heightLabel, heightTextField);
        timeVBox.getChildren().addAll(timeLabel, timeTextField);

        createListeners(new TextField[] {
                widthTextField,
                heightTextField,
                timeTextField
        });

        Button setButton = createSetButton(anchorPane,
                widthTextField,
                heightTextField,
                timeTextField);

        Button saveButton = createSaveButton(stage, anchorPane);
        Button openButton = createOpenButton(stage,
                anchorPane,
                widthTextField,
                heightTextField,
                timeTextField);

        HBox variableBox = new HBox();
        variableBox.setSpacing(VAR_BOX_SPACING);
        variableBox.getChildren().addAll(widthVBox,
                heightVBox,
                timeVBox,
                setButton,
                openButton,
                saveButton);

        HBox entityPicker = createEntityPicker();

        VBox bottomBox = new VBox();
        bottomBox.getChildren().addAll(entityPicker, variableBox);

        pane.setTop(anchorPane);
        pane.setBottom(bottomBox);
        anchorPane.toBack();


        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("Map Builder");
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setResizable(STAGE_RESIZEABLE);
        stage.show();
    }

    /**
     * create a draggable image for every entity in images resources.
     * If something is dragged onto the bin image, that image is removed.
     * If one of the entity images are dragged, then it sets the current
     * drag to that image.
     * @return HBox of entity picker.
     */
    private HBox createEntityPicker() {

        HBox entityPicker = new HBox();

        File imagesDir =
                new File(Objects.requireNonNull(getClass()
                        .getResource(PATH_TO_IMAGES))
                        .getPath());
        for (File file : Objects.requireNonNull(imagesDir.listFiles())) {
            String imagePath = file.getPath();
            ImageView entity = createImageView(imagePath);

            if (file.getName().equals("bin.png")) {
                entity.setOnMouseDragReleased(e -> {
                    if (drag != null
                            && drag instanceof ImageView
                            && drag.getParent() instanceof Group group) {
                        group.getChildren().remove(drag);
                        drag = null;
                    }
                });
            } else {
                entity.setOnDragDetected(e -> {
                    drag = entity;
                    drag.startFullDrag();

                    e.consume();
                });
            }
            entityPicker.getChildren().add(entity);
        }
        return entityPicker;
    }

    /**
     * When open button is clicked, a file opening dialog is shown.
     * It takes the given file, gets its content, and iterate through
     * to create a tile map in the anchor pane. It also assigns all
     * the variables (width, height, time) from file.
     *
     * @param stage           stage.
     * @param anchorPane      anchor pane.
     * @param widthTextField  width text field.
     * @param heightTextField height text field.
     * @param timeTextField   time text field.
     * @return open button.
     */
    private Button createOpenButton(final Stage stage,
                                    final AnchorPane anchorPane,
                                    final TextField widthTextField,
                                    final TextField heightTextField,
                                    final TextField timeTextField) {
        Button openButton = new Button();
        openButton.setText("Open");
        openButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null && file.exists()) {
                try {
                    Object[] vars = openFile(file);

                    width = (int) vars[0];
                    height = (int) vars[1];
                    time = (double) vars[2];

                    widthTextField.setText(String.valueOf(width));
                    heightTextField.setText(String.valueOf(height));
                    timeTextField.setText(String.valueOf(time));

                    String[][] tileMap = (String[][]) vars[3];
                    String[][] entityMap = (String[][]) vars[4];

                    anchorPane.getChildren().clear();

                    for (int y = 0; y < tileMap.length; y++) {
                        String[] row = tileMap[y];
                        for (int x = 0; x < row.length; x++) {
                            String colours = tileMap[y][x];
                            String entity = entityMap[y][x];

                            Group group = createTile(x, y, colours);

                            if (entity != null) {
                                ImageView imageView =
                                        createEntityImageView(x, y, entity);
                                group.getChildren().add(imageView);
                            }

                            anchorPane.getChildren().add(group);
                        }
                    }

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            e.consume();
        });

        return openButton;
    }

    /**
     * When save button is clicked, a file saving dialog will be shown.
     * It takes the content from the pane and all the variables (width,
     * height, time), and saves them to the specified file.
     *
     * @param stage      stage.
     * @param anchorPane anchor pane.
     * @return save button.
     */
    private Button createSaveButton(final Stage stage,
                                    final AnchorPane anchorPane) {
        Button saveButton = new Button();
        saveButton.setText("Save");

        saveButton.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("map.txt");
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                String content = String.format("%d %d %.2f 0\n%s",
                        width,
                        height,
                        time,
                        getContentFromPane(anchorPane));
                try {
                    saveFile(file, content);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            e.consume();
        });

        return saveButton;
    }

    /**
     * When set button is clicked, a black tile map of given width and height
     * is generated.
     *
     * @param anchorPane      anchor pane.
     * @param widthTextField  width text field.
     * @param heightTextField height text field.
     * @param timeTextField   time text field.
     * @return set button.
     */
    private Button createSetButton(final AnchorPane anchorPane,
                                   final TextField widthTextField,
                                   final TextField heightTextField,
                                   final TextField timeTextField) {
        Button setButton = new Button();
        setButton.setText("Set");
        setButton.setOnMouseClicked(e -> {
            width = Integer.parseInt(widthTextField.getText());
            height = Integer.parseInt(heightTextField.getText());
            time = Integer.parseInt(timeTextField.getText());

            anchorPane.getChildren().clear();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Group group = createTile(x, y);

                    anchorPane.getChildren().add(group);
                }
            }

            e.consume();
        });

        return setButton;
    }


    /**
     * for every text field, create a listener which checks whether
     * the new value can be an integer, otherwise set it back to
     * the old value.
     *
     * @param textFields text fields
     */
    private void createListeners(final TextField[] textFields) {
        for (TextField textField : textFields) {
            textField.textProperty().addListener(
                    ((observableValue, oldValue, newValue) -> {
                        try {
                            Integer.parseInt(newValue);
                            ((StringProperty) observableValue).setValue(
                                    newValue);
                        } catch (NumberFormatException e) {
                            ((StringProperty) observableValue).setValue(
                                    oldValue);
                        }
                    })
            );
        }
    }

    /**
     * Creates an image view from path.
     *
     * @param imagePath path of the image.
     * @return image view.
     */
    private ImageView createImageView(final String imagePath) {
        ImageView imageView = new ImageView("file:" + imagePath);
        imageView.setFitHeight(TILE_SIZE * 2);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Creates a rectangle with given position, offset, and colour,
     * which also can be interacted by user to change colour.
     *
     * @param x     position x
     * @param y     position y
     * @param i     offset i
     * @param color color of the rectangle
     * @return rectangle
     */
    private Rectangle createRectangle(final int x, final int y, final int i,
                                      final Color color) {
        Rectangle rectangle = new Rectangle(
                (TILE_SIZE * 2 + TILE_SPACING) * x
                        + TILE_SIZE * (i % 2),
                (TILE_SIZE * 2 + TILE_SPACING) * y
                        + TILE_SIZE * (i < 2 ? 0 : 1),
                TILE_SIZE,
                TILE_SIZE);
        rectangle.setFill(color);

        rectangle.setOnMouseClicked(e -> {
            switchColor(rectangle);
            e.consume();
        });

        rectangle.setOnDragDetected(e -> {
            drag = rectangle;
            rectangle.startFullDrag();
            e.consume();
        });

        rectangle.setOnMouseDragEntered(e -> {
            if (drag != null && drag instanceof Rectangle) {
                rectangle.setFill(((Rectangle) drag).getFill());
            }

            e.consume();
        });


        return rectangle;
    }

    /**
     * Create a new image of entity on tile.
     *
     * @param x     x position.
     * @param y     y position.
     * @param image given image.
     * @return image view of the entity.
     */
    private ImageView createEntityImageView(final int x, final int y,
                                            final Image image) {
        ImageView imageView = new ImageView(image);

        imageView.setLayoutX((TILE_SIZE * 2 + TILE_SPACING) * x
                + TILE_SIZE / 2);
        imageView.setLayoutY((TILE_SIZE * 2 + TILE_SPACING) * y
                + TILE_SIZE / 2);
        imageView.setFitHeight(TILE_SIZE);
        imageView.setPreserveRatio(true);

        imageView.setOnDragDetected(e -> {
            drag = imageView;
            drag.startFullDrag();

            e.consume();
        });

        return imageView;
    }

    /**
     * Create a new image of entity on tile.
     *
     * @param x      x position.
     * @param y      y position.
     * @param entity data of the entity.
     * @return image view of the entity.
     */
    private ImageView createEntityImageView(final int x, final int y,
                                            final String entity) {
        return createEntityImageView(
                x,
                y,
                getImageFromType(entity)
        );
    }

    /**
     * Gets image created from the type of entity.
     *
     * @param type type of entity.
     * @return image of the entity.
     */
    private Image getImageFromType(final String type) {
        return new Image(Objects.requireNonNull(
                getClass().getResource(
                        PATH_TO_IMAGES
                                + switch (type) {
                            case "CP" -> "player.png";
                            case "C^" -> "flying_assassin.png";
                            case "CF" -> "floor_following.png";
                            case "CS" -> "smart_thief.png";
                            case "L@" -> "clock.png";
                            case "LD" -> "door.png";
                            default -> "bin.png";
                        }
                )
        ).toExternalForm());
    }

    /**
     * Create a default tile.
     *
     * @param x x position.
     * @param y y position.
     * @return group tile.
     */
    private Group createTile(final int x, final int y) {
        return createTile(x, y, "WWWW");
    }

    /**
     * Creates a tile based on given colours.
     *
     * @param x       x position.
     * @param y       y position.
     * @param colours colours of the tile.
     * @return group tile.
     */
    private Group createTile(final int x, final int y, final String colours) {
        Group group = new Group();
        for (int i = 0; i < colours.length(); i++) {
            char c = colours.charAt(i);
            Rectangle rectangle = createRectangle(x, y, i, getColorFromChar(c));
            group.getChildren().add(rectangle);
        }

        group.setOnMouseDragReleased(e -> {
            if (drag != null && drag instanceof ImageView imageView) {
                ImageView[] ivs = group.getChildren().stream()
                        .filter(o -> o instanceof ImageView)
                        .toArray(ImageView[]::new);
                if (ivs.length > 0) {
                    for (ImageView i : ivs) {
                        group.getChildren().remove(i);
                    }
                }

                if (imageView.getParent() instanceof Group g) {
                    g.getChildren().remove(imageView);
                }

                ImageView entity =
                        createEntityImageView(x, y, imageView.getImage());
                entity.setLayoutX((TILE_SIZE * 2 + TILE_SPACING) * x
                        + TILE_SIZE / 2);
                entity.setLayoutY((TILE_SIZE * 2 + TILE_SPACING) * y
                        + TILE_SIZE / 2);
                group.getChildren().add(entity);
                drag = null;
            }

            e.consume();
        });

        return group;
    }

    /**
     * Switches the color of the shape to next color. Order as follows:
     * Black -> Red -> Yellow -> Blue -> Green -> Black.
     *
     * @param shape shape
     */
    private void switchColor(final Shape shape) {
        shape.setFill(
                switch (shape.getFill().toString()) {
                    case "0x000000ff" -> Color.RED;
                    case "0xff0000ff" -> Color.YELLOW;
                    case "0xffff00ff" -> Color.BLUE;
                    case "0x0000ffff" -> Color.GREEN;
                    default -> Color.BLACK;
                }
        );
    }

    /**
     * Gets all the tiles in pane and return it as one formatted string.
     *
     * @param pane AnchorPane
     * @return contents in pane
     */
    private String getContentFromPane(final AnchorPane pane) {
        StringBuilder tiles = new StringBuilder();
        StringBuilder entities = new StringBuilder();

        int i = 1;
        for (Node elem : pane.getChildren()) {
            Group group = (Group) elem;

            for (Node node : group.getChildren()) {
                if (node instanceof Shape) {
                    char c = getCharFromColor(
                            ((Shape) node).getFill().toString());
                    tiles.append(c);

                    if (i % (width * DATA_LENGTH) == 0) {
                        tiles.append('\n');
                    } else if (i % DATA_LENGTH == 0) {
                        tiles.append(' ');
                    }

                    i++;
                } else {
                    String path = ((ImageView) node).getImage().getUrl();
                    String type = getTypeFromPath(path);
                    int x = (int) ((node.getLayoutX() - TILE_SIZE / 2)
                            / (TILE_SIZE * 2 + TILE_SPACING));
                    int y = (int) ((node.getLayoutY() - TILE_SIZE / 2)
                            / (TILE_SIZE * 2 + TILE_SPACING));

                    String entity = type + x + y + " ";
                    entities.append(entity);
                }
            }
        }

        return tiles.toString().trim() + "\n" + entities.toString().trim();
    }

    /**
     * Get entity type from image.
     *
     * @param path path of the image.
     * @return type of the entity.
     */
    private String getTypeFromPath(final String path) {
        String name = path.replaceAll(".*/", "");
        return switch (name) {
            case "player.png" -> "CP";
            case "floor_following.png" -> "CF";
            case "flying_assassin.png" -> "C^";
            case "smart_thief.png" -> "CS";
            case "clock.png" -> "L@";
            case "door.png" -> "LD";
            default -> "";
        };
    }

    /**
     * @param color Color
     * @return character of the corresponding color.
     */
    private char getCharFromColor(final String color) {
        return switch (color) {
            case "0xff0000ff" -> 'R';
            case "0xffff00ff" -> 'Y';
            case "0x008000ff" -> 'G';
            case "0x0000ffff" -> 'B';
            default -> 'W';
        };
    }

    /**
     * @param c character
     * @return color of the corresponding character.
     */
    private Color getColorFromChar(final char c) {
        return switch (c) {
            case 'R' -> Color.RED;
            case 'Y' -> Color.YELLOW;
            case 'G' -> Color.GREEN;
            case 'B' -> Color.BLUE;
            default -> Color.BLACK;
        };
    }


    /**
     * @param file file to be iterated.
     * @return contents from the file.
     * @throws FileNotFoundException file not found.
     */
    private Object[] openFile(final File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        final int w = scan.nextInt();
        final int h = scan.nextInt();
        final double t = scan.nextDouble();
        final int s = scan.nextInt();

        String[][] tileMap = new String[h][w];

        int row = 0;
        int column = 0;
        boolean isLastTile = false;

        while (!isLastTile) {
            String colours = scan.next();
            tileMap[column][row] = colours;

            row++;
            if (row > w - 1) {
                row = 0;
                column++;
            }

            isLastTile = column > h - 1;
        }

        String[][] entityMap = new String[h][w];

        while (scan.hasNext()) {
            String itemData = scan.next();

            if (itemData.length() == DATA_LENGTH) {
                String type = itemData.substring(0, 2);
                int x = Integer.parseInt(itemData.substring(2, 3));
                int y = Integer.parseInt(itemData.substring(3, DATA_LENGTH));

                entityMap[y][x] = type;
            }
        }

        scan.close();

        return new Object[] {w, h, t, tileMap, entityMap};
    }

    /**
     * Writes content to the file.
     *
     * @param file    file to be saved
     * @param content contents to be written in the file
     * @throws IOException file cannot be written.
     */
    private void saveFile(final File file, final String content)
            throws IOException {
        FileWriter writer = new FileWriter(file.getAbsolutePath());
        writer.write(content);
        writer.close();
    }

    /**
     * Main method.
     * @param args parameters.
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
