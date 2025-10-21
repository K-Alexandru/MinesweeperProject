package com.minesweeper.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Utility class for loading and caching game resources (images).
 * Implements singleton pattern and caches loaded images for efficiency.
 */
public class ResourceLoader {
    private static final Logger LOGGER = Logger.getLogger(ResourceLoader.class.getName());
    private static ResourceLoader instance;
    private final Map<String, ImageIcon> imageCache;

    // Resource file names
    public static final String BOMB = "bomb.png";
    public static final String RED_BOMB = "redbomb.jpg";
    public static final String EMPTY = "empty.png";
    public static final String FLAG = "flag.png";
    public static final String ONE = "one.png";
    public static final String TWO = "two.png";
    public static final String THREE = "three.png";
    public static final String FOUR = "four.png";
    public static final String FIVE = "five.png";
    public static final String SIX = "six.png";
    public static final String SEVEN = "seven.png";
    public static final String EIGHT = "eight.png";
    public static final String GAME_OVER = "gameover.png";

    private ResourceLoader() {
        this.imageCache = new HashMap<>();
    }

    /**
     * Gets the singleton instance of ResourceLoader.
     */
    public static synchronized ResourceLoader getInstance() {
        if (instance == null) {
            instance = new ResourceLoader();
        }
        return instance;
    }

    /**
     * Loads an image from resources.
     *
     * @param filename The name of the image file
     * @return The loaded ImageIcon, or null if not found
     */
    public ImageIcon loadImage(String filename) {
        // Check cache first
        if (imageCache.containsKey(filename)) {
            return imageCache.get(filename);
        }

        try {
            URL url = getClass().getClassLoader().getResource(filename);
            if (url == null) {
                LOGGER.warning("Image not found: " + filename);
                return null;
            }

            ImageIcon icon = new ImageIcon(url);
            imageCache.put(filename, icon);
            return icon;
        } catch (Exception e) {
            LOGGER.severe("Error loading image " + filename + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Loads and scales an image to the specified dimensions.
     *
     * @param filename The name of the image file
     * @param width The target width
     * @param height The target height
     * @return The scaled ImageIcon, or null if not found
     */
    public ImageIcon loadScaledImage(String filename, int width, int height) {
        String cacheKey = filename + "_" + width + "x" + height;

        // Check cache first
        if (imageCache.containsKey(cacheKey)) {
            return imageCache.get(cacheKey);
        }

        ImageIcon original = loadImage(filename);
        if (original == null) {
            return null;
        }

        Image scaledImage = original.getImage().getScaledInstance(
            width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageCache.put(cacheKey, scaledIcon);
        return scaledIcon;
    }

    /**
     * Gets the appropriate image for a number (1-8).
     *
     * @param number The number of adjacent mines
     * @return The corresponding ImageIcon
     */
    public ImageIcon getNumberImage(int number) {
        return switch (number) {
            case 1 -> loadImage(ONE);
            case 2 -> loadImage(TWO);
            case 3 -> loadImage(THREE);
            case 4 -> loadImage(FOUR);
            case 5 -> loadImage(FIVE);
            case 6 -> loadImage(SIX);
            case 7 -> loadImage(SEVEN);
            case 8 -> loadImage(EIGHT);
            default -> loadImage(EMPTY);
        };
    }

    /**
     * Gets the appropriate scaled image for a number (1-8).
     */
    public ImageIcon getScaledNumberImage(int number, int width, int height) {
        return switch (number) {
            case 1 -> loadScaledImage(ONE, width, height);
            case 2 -> loadScaledImage(TWO, width, height);
            case 3 -> loadScaledImage(THREE, width, height);
            case 4 -> loadScaledImage(FOUR, width, height);
            case 5 -> loadScaledImage(FIVE, width, height);
            case 6 -> loadScaledImage(SIX, width, height);
            case 7 -> loadScaledImage(SEVEN, width, height);
            case 8 -> loadScaledImage(EIGHT, width, height);
            default -> loadScaledImage(EMPTY, width, height);
        };
    }

    /**
     * Clears the image cache to free memory.
     */
    public void clearCache() {
        imageCache.clear();
    }
}
