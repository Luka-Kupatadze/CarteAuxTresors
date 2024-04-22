package com.CarbonIT.treasurehunt.util;


import com.CarbonIT.treasurehunt.model.Adventurer;
import com.CarbonIT.treasurehunt.model.Tile;
import com.CarbonIT.treasurehunt.model.TreasureMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class FileReader {

    /**
     * Lit un fichier de configuration et initialise la carte aux trésors et les aventuriers.
     * @param filePath Chemin du fichier à lire.
     * @return TreasureMap initialisée selon les données du fichier.
     * @throws FileNotFoundException Si le fichier n'est pas trouvé.
     */
    public static TreasureMap readMapFromFile(String filePath) throws FileNotFoundException {

        File file;
        Scanner scanner;
        try {
            file = new File(filePath);
            scanner = new Scanner(file);

        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        TreasureMap map = null;
        boolean mapCreated = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;  // Ignore les lignes vides et les commentaires
            }


            try {
                String[] parts = line.split(" - ");
                switch (parts[0]) {
                    case "C":
                        if (mapCreated) {
                            throw new IllegalArgumentException("Multiple map creation lines found");
                        }
                        int width = Integer.parseInt(parts[1]);
                        int height = Integer.parseInt(parts[2]);
                        map = new TreasureMap(width, height);
                        mapCreated = true;
                        break;
                    case "M":
                        int mx = Integer.parseInt(parts[1]);
                        int my = Integer.parseInt(parts[2]);
                        map.getTile(mx, my).setTerrain(Tile.Terrain.MOUNTAIN);
                        break;
                    case "T":
                        int tx = Integer.parseInt(parts[1]);
                        int ty = Integer.parseInt(parts[2]);
                        int treasures = Integer.parseInt(parts[3]);
                        Tile tile = map.getTile(tx, ty);
                        tile.setTerrain(Tile.Terrain.TREASURE);
                        tile.addTreasures(treasures);
                        break;
                    case "A":
                        //
                        String name = parts[1];
                        int ax = Integer.parseInt(parts[2]);
                        int ay = Integer.parseInt(parts[3]);
                        Adventurer.Orientation orientation = Adventurer.Orientation.valueOf(parts[4]);
                        //check validity of sequence (only A, G, D)
                        if (!parts[5].matches("^[AGD]+$")) {
                            throw new IllegalArgumentException("Invalid sequence of movements: " + parts[5]);
                        }
                        String sequence = parts[5];
                        Adventurer adventurer = new Adventurer(name, ax, ay, orientation, sequence);
                        map.addAdventurer(adventurer);
                        break;
                    default:
                        scanner.close();
                        throw new IllegalArgumentException("Unrecognized line start: " + parts[0]);
            }
            } catch (Exception e) {
                scanner.close();
                throw new IllegalArgumentException("Invalid line: " + line, e);

            }

        }
        scanner.close();
        if (!mapCreated) {
            throw new IllegalArgumentException("Map creation line not found or not the first line");
        }
        return map;
    }
}
