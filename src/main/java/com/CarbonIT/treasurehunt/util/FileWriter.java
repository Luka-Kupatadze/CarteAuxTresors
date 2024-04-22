package com.CarbonIT.treasurehunt.util;

import com.CarbonIT.treasurehunt.model.Adventurer;
import com.CarbonIT.treasurehunt.model.Tile;
import com.CarbonIT.treasurehunt.model.TreasureMap;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter {


    /**
     * Sauvegarde l'état final de la carte aux trésors dans un fichier.
     * @param map La carte aux trésors à sauvegarder.
     * @param filePath Le chemin du fichier où sauvegarder la carte.
     * @throws IOException Si une erreur d'écriture se produit.
     */
    public static void saveMapToFile(TreasureMap map, String filePath) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            // Écriture des dimensions de la carte
            writer.write(String.format("C - %d - %d%n", map.getWidth(), map.getHeight()));
            // Écriture des tuiles de la carte
            for (int y = 0; y < map.getHeight(); y++) {
                for (int x = 0; x < map.getWidth(); x++) {
                    Tile tile = map.getTile(x, y);
                    if (tile.getTerrain() == Tile.Terrain.MOUNTAIN) {
                        writer.write(String.format("M - %d - %d%n", x, y));
                    } else if (tile.getTerrain() == Tile.Terrain.TREASURE && tile.getTreasureCount() > 0) {
                        writer.write(String.format("T - %d - %d - %d%n", x, y, tile.getTreasureCount()));
                    }
                }
            }
            // Écriture des aventuriers
            List<Adventurer> adventurers = map.getAdventurers();
            for (Adventurer adventurer : adventurers) {
                writer.write(String.format("A - %s - %d - %d - %s - %d%n",
                        adventurer.getName(), adventurer.getPosX(), adventurer.getPosY(),
                        adventurer.getOrientation(), adventurer.getTreasuresCollected()));
            }
        }
    }
}
