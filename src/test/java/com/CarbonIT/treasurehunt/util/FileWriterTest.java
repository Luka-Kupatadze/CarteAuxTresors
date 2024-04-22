package com.CarbonIT.treasurehunt.util;

import com.CarbonIT.treasurehunt.model.Adventurer;
import com.CarbonIT.treasurehunt.model.Tile;
import com.CarbonIT.treasurehunt.model.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterTest {

    @TempDir
    Path tempDir;
    private File outputFile;
    private TreasureMap map;

    @BeforeEach
    public void setUp() throws Exception {
        try {
            outputFile = tempDir.resolve("output.txt").toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        map = new TreasureMap(3, 3);
        map.getTile(1, 1).setTerrain(Tile.Terrain.MOUNTAIN);
        map.getTile(2, 2).setTerrain(Tile.Terrain.TREASURE);
        map.getTile(2, 2).addTreasures(5);
        Adventurer adventurer = new Adventurer("Indiana", 0, 0, Adventurer.Orientation.N, "");
        map.addAdventurer(adventurer);
    }

    @Test
    public void testSaveMapToFile() throws Exception {
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("M - 1 - 1", reader.readLine());
            assertEquals("T - 2 - 2 - 5", reader.readLine());
            assertEquals("A - Indiana - 0 - 0 - NORTH - 0", reader.readLine());
        }
    }

    @Test
    public void testSaveMapToFileWithMultipleAdventurers() throws Exception {
        Adventurer adventurer2 = new Adventurer("Lara", 2, 2, Adventurer.Orientation.S, "ADG");
        map.addAdventurer(adventurer2);
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("M - 1 - 1", reader.readLine());
            assertEquals("T - 2 - 2 - 5", reader.readLine());
            assertEquals("A - Indiana - 0 - 0 - NORTH - 0", reader.readLine());
            assertEquals("A - Lara - 2 - 2 - SOUTH - 0", reader.readLine());
        }
    }


    @Test
    public void testSaveMapToFileWithNoTreasures() throws Exception {
        for (int i = 0; i <= 4; i++) {
            map.getTile(2,2).removeTreasure();
        }
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("M - 1 - 1", reader.readLine());
            assertEquals("A - Indiana - 0 - 0 - NORTH - 0", reader.readLine());
        }
    }

    @Test
    public void testSaveMapToFileWithNoAdventurers() throws Exception {
        map.getAdventurers().clear();
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("M - 1 - 1", reader.readLine());
            assertEquals("T - 2 - 2 - 5", reader.readLine());
        }
    }

    @Test
    public void testSaveMapToFileWithNoTreasuresAndNoAdventurers() throws Exception {
        for (int i = 0; i <= 4; i++) {
            map.getTile(2,2).removeTreasure();
        }
        map.getAdventurers().clear();
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("M - 1 - 1", reader.readLine());
        }
    }


    @Test
    public void testSaveMapToFileWithNoMountains() throws Exception {
        map.getTile(1, 1).setTerrain(Tile.Terrain.PLAIN);
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("T - 2 - 2 - 5", reader.readLine());
            assertEquals("A - Indiana - 0 - 0 - NORTH - 0", reader.readLine());
        }
    }


    @Test
    public void testSaveMapToFileWithNoMountainsAndNoAdventurers() throws Exception {
        map.getTile(1, 1).setTerrain(Tile.Terrain.PLAIN);
        map.getAdventurers().clear();
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("T - 2 - 2 - 5", reader.readLine());
        }
    }


    @Test
    public void testSaveMapToFileWithNoMountainsAndNoTreasures() throws Exception {
        map.getTile(1, 1).setTerrain(Tile.Terrain.PLAIN);
        map.getTile(2, 2).setTerrain(Tile.Terrain.PLAIN);
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
            assertEquals("A - Indiana - 0 - 0 - NORTH - 0", reader.readLine());
        }
    }


    @Test
    public void testSaveMapToFileWithNoMountainsAndNoTreasuresAndNoAdventurers() throws Exception {
        map.getTile(1, 1).setTerrain(Tile.Terrain.PLAIN);
        map.getTile(2, 2).setTerrain(Tile.Terrain.PLAIN);
        map.getAdventurers().clear();
        FileWriter.saveMapToFile(map, outputFile.getPath());

        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            assertEquals("C - 3 - 3", reader.readLine());
        }
    }

}
