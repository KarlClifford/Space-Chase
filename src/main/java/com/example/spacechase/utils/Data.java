package com.example.spacechase.utils;

import com.example.spacechase.App;
import com.example.spacechase.models.characters.*;
import com.example.spacechase.models.characters.Character;
import com.example.spacechase.models.items.*;
import com.example.spacechase.models.level.Entity;
import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.Tile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;

/**
 * Data interface handles game file loading.
 * @author Tristan Tsang
 * @version 1.0.2
 */
public interface Data {
    /**
     * Path of directory that contains all profiles data.
     */
    String PROFILES_PATH = "data/profiles";
    /**
     * Path of directory that contains all default levels.
     */
    String LEVELS_PATH = "data/levels";
    /**
     * Fixed length for each string data.
     */
    int DATA_LENGTH = 4;

    /**
     * Gets the url object from a path.
     * @param path path of the source.
     * @return url object for the path.
     * @see java.net.URL
     */
    static URL getUrl(String path) {
        return App.class.getResource(path);
    }

    /**
     * Reads the level file and returns a level object.
     * @param file level file.
     * @return Level object.
     * @throws FileNotFoundException This exception is thrown
     * when file is not found.
     */
    static Level readLevel(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);

        String name = file.getName();
        final int id = Integer.parseInt(name
                .replaceAll("[^0-9]", ""));

        final int width = scan.nextInt();
        final int height = scan.nextInt();
        final double time = scan.nextDouble();
        final int score = scan.nextInt();

        Tile[][] tileMap = new Tile[height][width];
        Level level = new Level(id, file, time, score, tileMap);

        int x = 0;
        int y = 0;
        boolean isLastTile = false;

        // While the current tile is not the last tile, keep reading tiles.
        while (scan.hasNext() && !isLastTile) {
            String tileData = scan.next();

            /* If the tile data string has the correct length,
             create a tile and assign it to corresponding position
             to the 2D tile map array. */
            if (tileData.length() >= DATA_LENGTH) {
                String colours = tileData.substring(0, 4);
                Tile tile = tileMap[y][x] = new Tile(x, y, colours);

                /*
                 * If there is entity data, create that entity and
                 * set it to the tile.
                 */
                if (tileData.length() == DATA_LENGTH + 2) {
                    char entityType = tileData.charAt(4);
                    char entityColour = tileData.charAt(5);

                    Entity entity = createEntity(entityType);
                    entity.setTile(tile);
                    entity.setColour(entityColour);
                    entity.setLevel(level);
                    entity.createImageView();

                    /*
                     * Sets entity to tile's character if it is
                     * a character. Otherwise, sets entity to
                     * tile's item if it is an item.
                     */
                    if (entity instanceof Character character) {
                        tile.setCharacter(character);
                    } else if (entity instanceof Item item) {
                        tile.setItem(item);
                    }
                }

                x++;

                // If x has reached the width, then move onto the next row.
                if (x > width - 1) {
                    x = 0;
                    y++;
                }

                isLastTile = y > height - 1;
            }
        }

        scan.close();

        return level;
    }

    /**
     * Creates an entity from type.
     * @param type character code for the entity type.
     * @return an entity created from given type.
     */
    private static Entity createEntity(char type) {
        return switch (type) {
            case '*' -> new Bomb();
            case '|' -> new Lever();
            case '@' -> new Clock();
            case 'D' -> new Door();
            case 'P' -> new Player();
            case '^' -> new FlyingAssassin();
            case 'F' -> new FloorFollowing();
            case 'S' -> new SmartThief();
            default -> throw new IllegalStateException(
                    "Unexpected value: " + type);
        };
    }

    /**
     * Gets all the levels for a player.
     * @param name player name.
     * @return a list of level objects created from the player's profile folder.
     */
    static Level[] getLevelsFromProfile(String name) {
        URL url = getUrl(String.format("%s/%s", PROFILES_PATH, name));
        File playerFolder = new File(url.getPath());

        return Arrays.stream(
                Objects.requireNonNull(
                        playerFolder.listFiles()
                ))
                .map(file -> {
                    /* Try to read levels out of a file,
                     Catches exception when file is not found. */
                    try {
                        return Data.readLevel(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Level[]::new);
    }

    /**
     * Gets all the profiles from profile directory.
     * @return names of all the profiles.
     */
    static String[] getProfiles() {
        URL url = getUrl(PROFILES_PATH);
        File profileFolders = new File(url.getPath());

        return Arrays.stream(
                Objects.requireNonNull(
                        profileFolders.listFiles()
                ))
                .map(File::getName)
                .toArray(String[]::new);
    }

    /**
     * Creates a new profile from given name.
     * @param name player name.
     * @return whether it is a successful creation or not.
     * @throws IOException This exception is thrown when
     * fail to copy the level.
     */
    static boolean createProfile(String name) throws IOException {
        URL url = getUrl(PROFILES_PATH);
        File folder = new File(url.getPath() + name);

        if (folder.exists()) {
            return false;
        } else {
            folder.mkdirs();
            copyLevel(1, name);
            return true;
        }
    }

    /**
     * Deletes the file or all the sub files in the directory.
     * @param file file or folder to be deleted.
     */
    private static void deleteFiles(File file) {
        /* If this file is a directory, delete all of its sub-files
         and delete this directory.
         Otherwise, delete the file. */
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFiles(f);
            }

            file.delete();

        } else {
            file.delete();
        }
    }

    /**
     * Removes a profile of given name in the files.
     * @param name player name.
     */
    static void removeProfile(String name) {
        URL url = getUrl(PROFILES_PATH);
        File folder = new File(String.format("%s/%s",
                url.getPath(),
                name
        ));

        // Delete profile folder if exists.
        if (folder.exists()) {
            deleteFiles(folder);
        }
    }

    /**
     * Copies a level file with given id and pastes it in player's
     * profile folder.
     * @param id id of the level.
     * @param name player name.
     * @return The file copied.
     * @throws IOException This exception is thrown when it fails
     * in copying the files.
     */
    static File copyLevel(int id, String name) throws IOException {
        String fileName = id + ".txt";

        File input = new File(String.format("%s/%s",
                getUrl(LEVELS_PATH).getPath(),
                fileName));
        File output = new File(String.format("%s/%s/%s",
                getUrl(PROFILES_PATH).getPath(),
                name,
                fileName));

        // If the file does not exist, return null.
        if (!input.exists()) {
            return null;
        }

        Files.copy(input.toPath(),
                output.toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return output;
    }

    /**
     * Saves the snapshot of the given level.
     * @param level level to be saved.
     */
    static void saveLevel(Level level) {
        File file = level.getFile();
        String content = level.toString();

        /* Try to overwrite the file with contents of the level.
         Catch if there is an I/O exception on writing file. */
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
