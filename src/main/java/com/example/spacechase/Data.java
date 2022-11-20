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
 * This interface represents the access to data where it contains
 * methods of reading and copy a level, loading, saving and deleting
 * a profile.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public interface Data {
    /**
     * path to resources.
     */
    String PATH_TO_RESOURCE = "src/main/resources/";
    /**
     * path to package.
     */
    String PATH_TO_PACKAGE = PATH_TO_RESOURCE
            + "com/example/spacechase/";
    /**
     * path to data.
     */
    String PATH_TO_DATA = PATH_TO_PACKAGE
            + "data/";
    /**
     * path to profiles.
     */
    String PATH_TO_PROFILES = PATH_TO_DATA
            + "profiles/";
    /**
     * path to levels.
     */
    String PATH_TO_LEVELS = PATH_TO_DATA
            + "levels/";
    /**
     * length for each string data.
     */
    int DATA_LENGTH = 4;

    /**
     * Reads the level file and returns a level object.
     * @param file level file.
     * @return Level object.
     * @throws FileNotFoundException
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

        while (!isLastTile) {
            String colours = scan.next();

            if (colours.length() == DATA_LENGTH) {
                tileMap[y][x] = new Tile(x, y, colours);

                x++;
                if (x > width - 1) {
                    x = 0;
                    y++;
                }

                isLastTile = y > height - 1;
            }
        }

        while (scan.hasNext()) {
            String itemData = scan.next();

            if (itemData.length() == DATA_LENGTH) {
                char type = itemData.charAt(0);
                char subType = itemData.charAt(1);
                int a = Integer.parseInt(itemData.substring(2, 3));
                int b = Integer.parseInt(itemData.substring(3, 4));

                Tile tile = tileMap[b][a];

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
     * Get the instance of the corresponding item from given id.
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
     * Get the instance of the corresponding character from given id.
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
     * @param name player name.
     * @return a list of level objects created from the player's profile folder.
     */
    static Level[] getLevels(String name) {
        File playerFolder = new File(PATH_TO_PROFILES + name);

        return Arrays.stream(
                Objects.requireNonNull(
                        playerFolder.listFiles()
                ))
                .map(file -> {
                    try {
                        return Data.readLevel(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Level[]::new);
    }

    /**
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
     * @throws IOException
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
     * @param n id of the level.
     * @param name player name.
     * @return The file copied.
     * @throws IOException
     */
    static File copyLevel(int n, String name) throws IOException {
        String fileName = n + ".txt";

        File input = new File(String.format("%s%s",
                PATH_TO_LEVELS,
                fileName));
        File output = new File(String.format("%s%s/%s",
                PATH_TO_PROFILES,
                name,
                fileName));

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

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
