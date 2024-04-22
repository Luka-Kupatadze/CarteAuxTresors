package com.CarbonIT.treasurehunt.util;

import com.CarbonIT.treasurehunt.model.Adventurer;
import com.CarbonIT.treasurehunt.model.Tile;
import com.CarbonIT.treasurehunt.model.TreasureMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {

    @TempDir
    Path tempDir;

    private File inputFile;

    @BeforeEach
    public void setUp(){
        inputFile = tempDir.resolve("input.txt").toFile();
    }

    @AfterEach
    public void cleanUp() {
        inputFile.delete();
    }

    private void writeToFile(String content) throws IOException {
        try (PrintWriter out = new PrintWriter(inputFile)) {
            out.println(content);
        }
    }

    @Test
    public void testReadMapFromFileCorrectly() throws Exception {
        String data = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        writeToFile(data);

        TreasureMap map = FileReader.readMapFromFile(inputFile.getPath());
        assertNotNull(map);
        assertEquals(Tile.Terrain.MOUNTAIN, map.getTile(1, 1).getTerrain());
        assertEquals(Tile.Terrain.TREASURE, map.getTile(2, 2).getTerrain());
        assertEquals(3, map.getTile(2, 2).getTreasureCount());
        assertEquals(1, map.getAdventurers().size());
        Adventurer adventurer = map.getAdventurers().get(0);
        assertEquals("Indiana", adventurer.getName());
        assertEquals(Adventurer.Orientation.N, adventurer.getOrientation());
        assertEquals("AADA", adventurer.getMovementSequence());
    }

    @Test
    public void testReadMapFromFileWithInvalidFormat() {
        String invalidData = "X - 1 - 2 - 3";  // Invalid line format
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }


    @Test
    public void testFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> FileReader.readMapFromFile("nonexistentfile.txt"));
    }

    @Test
    public void testReadMapFromFileWithEmptyFile() throws Exception {
        writeToFile("");
        assertThrows(IllegalArgumentException.class, () -> FileReader.readMapFromFile(inputFile.getPath()));
    }

    @Test
    public void testReadMapFromFileWithComment() throws Exception {
        String data = """
                # This is a comment
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        writeToFile(data);

        TreasureMap map = FileReader.readMapFromFile(inputFile.getPath());
        assertNotNull(map);
        assertEquals(Tile.Terrain.MOUNTAIN, map.getTile(1, 1).getTerrain());
        assertEquals(Tile.Terrain.TREASURE, map.getTile(2, 2).getTerrain());
        assertEquals(3, map.getTile(2, 2).getTreasureCount());
        assertEquals(1, map.getAdventurers().size());
        Adventurer adventurer = map.getAdventurers().get(0);
        assertEquals("Indiana", adventurer.getName());
        assertEquals(Adventurer.Orientation.N, adventurer.getOrientation());
        assertEquals("AADA", adventurer.getMovementSequence());
    }

    @Test
    public void testReadMapFromFileWithEmptyLines() throws Exception {
        String data = """
                C - 3 - 3
                
                M - 1 - 1
                
                T - 2 - 2 - 3
                
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        writeToFile(data);

        TreasureMap map = FileReader.readMapFromFile(inputFile.getPath());
        assertNotNull(map);
        assertEquals(Tile.Terrain.MOUNTAIN, map.getTile(1, 1).getTerrain());
        assertEquals(Tile.Terrain.TREASURE, map.getTile(2, 2).getTerrain());
        assertEquals(3, map.getTile(2, 2).getTreasureCount());
        assertEquals(1, map.getAdventurers().size());
        Adventurer adventurer = map.getAdventurers().get(0);
        assertEquals("Indiana", adventurer.getName());
        assertEquals(Adventurer.Orientation.N, adventurer.getOrientation());
        assertEquals("AADA", adventurer.getMovementSequence());
    }

    @Test
    public void testReadMapFromFileWithInvalidOrientation() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - 0 - 0 - INVALID - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }


    @Test
    public void testReadMapFromFileWithInvalidTreasureCount() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - INVALID
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithInvalidCoordinates() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - INVALID - 2 - 3
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithInvalidAdventurerOrientation() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - 0 - 0 - INVALID - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithInvalidAdventurerCoordinates() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - INVALID - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithInvalidAdventurerMovement() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                A - Indiana - 0 - 0 - NORTH - INVALID
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }


    @Test
    public void testReadMapFromFileWithInvalidCommand() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - 3
                X - Indiana - 0 - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithInvalidTreasureCountFormat() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 2 - 2 - INVALID
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }

    @Test
    public void testReadMapFromFileWithOutOfBoundsTreasureCoordinates() {
        String invalidData = """
                C - 3 - 3
                M - 1 - 1
                T - 3 - 3 - 3
                A - Indiana - 0 - 0 - NORTH - AADA
                """;
        assertThrows(IllegalArgumentException.class, () -> {
            writeToFile(invalidData);
            FileReader.readMapFromFile(inputFile.getPath());
        });
    }
}
