package com.example.spacechase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;

/**
 * Data interface handles game file loading.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public interface Data {
    /**
     * URL path to resources that contains all the resources.
     */
    String PATH_TO_RESOURCE = "src/main/resources/";
    /**
     * URL path to package that contains resources of the package.
     */
    String PATH_TO_PACKAGE = PATH_TO_RESOURCE
            + "com/example/spacechase/";
    /**
     * URL path to data that contains all the data needed.
     */
    String PATH_TO_DATA = PATH_TO_PACKAGE
            + "data/";
    /**
     * URL path to profiles that contains all profiles data.
     */
    String PATH_TO_PROFILES = PATH_TO_DATA
            + "profiles/";
    /**
     * URL path to levels that contains all default levels.
     */
    String PATH_TO_LEVELS = PATH_TO_DATA
            + "levels/";
    /**
     * Fixed length for each string data.
     */
    int DATA_LENGTH = 4;

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

        int x = 0;
        int y = 0;
        boolean isLastTile = false;

        // While the current tile is not the last tile, keep reading tiles.
        while (!isLastTile) {
            String colours = scan.next();

            /* If the colour string has the correct length,
             create a tile and assign it to corresponding position
             to the 2D tile map array. */
            if (colours.length() == DATA_LENGTH) {
                tileMap[y][x] = new Tile(x, y, colours);

                x++;

                // If x has reached the width, then move onto the next row.
                if (x > width - 1) {
                    x = 0;
                    y++;
                }

                isLastTile = y > height - 1;
            }
        }

        /* While there is entity data, creates a new entity
         where an entity can be a character or an item. */
        while (scan.hasNext()) {
            String itemData = scan.next();

            /* If the data has the correct length,
             create a new entity and assign it to the tile. */
            if (itemData.length() == DATA_LENGTH) {
                char type = itemData.charAt(0);
                char subType = itemData.charAt(1);
                int a = Integer.parseInt(itemData.substring(2, 3));
                int b = Integer.parseInt(itemData.substring(3, 4));

                Tile tile = tileMap[b][a];

                /* If the type of entity is an item,
                 create an item and assign it to the tile.
                 Otherwise, create a character and assign it
                 to the tile. */
                if (type == 'L') {
                    Item item = createItemFromType(subType);
                    tile.setItem(item);
                } else {
                    Character character = createCharacterFromType(subType);
                    tile.setCharacter(character);
                    character.setTile(tile);
                }
            }
        }

        scan.close();

        return new Level(id, file, time, score, tileMap);
    }

    /**
     * Gets the instance of the corresponding item from given id.
     * @param type id of the item.
     * @return instance of the item.
     */
    private static Item createItemFromType(char type) {
        return switch (type) {
            case '*' -> new Bomb();
            case '|' -> new Lever();
            case '@' -> new Clock();
            case 'D' -> new Door();
            default -> null;
        };
    }

    /**
     * Gets the instance of the corresponding character from given id.
     * @param type id of the character.
     * @return instance of the character.
     */
    private static Character createCharacterFromType(char type) {
        return switch (type) {
            case 'P' -> new Player();
            case '^' -> new FlyingAssassin();
            case 'F' -> new FloorFollowing();
            case 'S' -> new SmartThief();
            default -> null;
        };
    }

    /**
     * Gets all the levels for a player.
     * @param name player name.
     * @return a list of level objects created from the player's profile folder.
     */
    static Level[] getLevelsFromProfile(String name) {
        File playerFolder = new File(PATH_TO_PROFILES + name);

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
        File profileFolders = new File(PATH_TO_PROFILES);

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
        File folder = new File(PATH_TO_PROFILES + name);

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
        File folder = new File(PATH_TO_PROFILES + name);
        deleteFiles(folder);
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

        File input = new File(String.format("%s%s",
                PATH_TO_LEVELS,
                fileName));
        File output = new File(String.format("%s%s/%s",
                PATH_TO_PROFILES,
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
