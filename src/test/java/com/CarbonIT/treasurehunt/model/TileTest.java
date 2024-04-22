package com.CarbonIT.treasurehunt.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    private Tile plainTile;
    private Tile mountainTile;
    private Tile treasureTile;

    @BeforeEach
    public void setUp() {
        // Initialisation des tuiles avec différents terrains
        plainTile = new Tile(Tile.Terrain.PLAIN);
        mountainTile = new Tile(Tile.Terrain.MOUNTAIN);
        treasureTile = new Tile(Tile.Terrain.TREASURE);
    }

    @Test
    public void testTerrainInitialization() {
        // Vérifier l'initialisation correcte des terrains
        assertEquals(Tile.Terrain.PLAIN, plainTile.getTerrain());
        assertEquals(Tile.Terrain.MOUNTAIN, mountainTile.getTerrain());
        assertEquals(Tile.Terrain.TREASURE, treasureTile.getTerrain());
    }

    @Test
    public void testAddTreasuresToTreasureTile() {
        // Ajouter des trésors à une tuile de type TREASURE
        treasureTile.addTreasures(5);
        assertEquals(5, treasureTile.getTreasureCount());

        // Ajouter plus de trésors
        treasureTile.addTreasures(3);
        assertEquals(8, treasureTile.getTreasureCount());
    }

    @Test
    public void testAddTreasuresToNonTreasureTile() {
        // Ajouter des trésors à une tuile qui n'est pas de type TREASURE
        plainTile.addTreasures(5);
        assertEquals(0, plainTile.getTreasureCount());

        mountainTile.addTreasures(5);
        assertEquals(0, mountainTile.getTreasureCount());
    }

    @Test
    public void testRemoveTreasure() {
        // Supprimer des trésors d'une tuile TREASURE
        treasureTile.addTreasures(3);
        treasureTile.removeTreasure();
        assertEquals(2, treasureTile.getTreasureCount());

        // Essayer de supprimer plus de trésors qu'il n'y en a
        treasureTile.removeTreasure();
        treasureTile.removeTreasure();
        treasureTile.removeTreasure();  // Un de trop
        assertEquals(0, treasureTile.getTreasureCount());
    }

    @Test
    public void testRemoveTreasureFromNonTreasureTile() {
        // Supprimer des trésors d'une tuile qui n'est pas TREASURE
        plainTile.removeTreasure();
        assertEquals(0, plainTile.getTreasureCount());

        mountainTile.removeTreasure();
        assertEquals(0, mountainTile.getTreasureCount());
    }
}
