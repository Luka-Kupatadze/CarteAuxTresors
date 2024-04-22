package com.CarbonIT.treasurehunt.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreasureMapTest {

    private TreasureMap map;
    private Adventurer adventurer;

    @BeforeEach
    public void setUp() {
        // Création d'une carte de taille 5x5
        map = new TreasureMap(5, 5);
        // Ajout d'un aventurier à la position (1,1) avec orientation Nord
        adventurer = new Adventurer("Indiana", 1, 1, Adventurer.Orientation.N, "AA");
        map.addAdventurer(adventurer);
    }




    @Test
    public void testAddAdventurer() {
        // Création et ajout d'un nouvel aventurier à la carte
        Adventurer newAdventurer = new Adventurer("Lara", 2, 2, Adventurer.Orientation.S, "ADG");
        map.addAdventurer(newAdventurer);

        // Vérifier que l'aventurier a bien été ajouté à la liste des aventuriers sur la carte
        assertTrue(map.getAdventurers().contains(newAdventurer));
    }

    @Test
    public void testMoveAdventurerValid() {
        map.moveAdventurers();  // Après un mouvement 'A'
        assertEquals(1, adventurer.getPosX());
        assertEquals(0, adventurer.getPosY());  // S'est déplacé vers le nord

        map.moveAdventurers();  // Après un deuxième mouvement 'A'
        assertEquals(1, adventurer.getPosX());
        assertEquals(0, adventurer.getPosY());  // ne se deplace pas car il est bloqué par le bord de la carte
    }

    @Test
    public void testMoveAdventurerIntoMountain() {
        // Placer une montagne à la position (1, 0)
        map.getTile(1, 0).setTerrain(Tile.Terrain.MOUNTAIN);
        map.moveAdventurers();
        // L'aventurier doit rester à sa position initiale car il ne peut pas traverser une montagne
        assertEquals(1, adventurer.getPosX());
        assertEquals(1, adventurer.getPosY());
    }

    @Test
    public void testAdventurerCollectsTreasure() {
        // Placer un trésor à la position (1, 0)
        map.getTile(1, 0).setTerrain(Tile.Terrain.TREASURE);
        map.getTile(1, 0).addTreasures(1);
        map.moveAdventurers();
        // Vérifier que l'aventurier a collecté le trésor
        assertEquals(1, adventurer.getTreasuresCollected());
        assertEquals(0, map.getTile(1, 0).getTreasureCount());  // Le trésor doit être collecté
    }
}
