package com.CarbonIT.treasurehunt.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdventurerTest {

    private Adventurer adventurer;

    @BeforeEach
    public void setUp() {
        // Initialisation de l'aventurier avec position initiale (0, 0), orientation nord et une séquence de mouvements.
        adventurer = new Adventurer("Indiana", 0, 0, Adventurer.Orientation.N, "AADA");
    }

    @Test
    public void testInitialPosition() {
        // Vérifier les positions initiales et l'orientation.
        assertEquals(0, adventurer.getPosX());
        assertEquals(0, adventurer.getPosY());
        assertEquals(Adventurer.Orientation.N, adventurer.getOrientation());
    }

    @Test
    public void testMovementSequence() {
        // Exécuter les mouvements un par un et vérifier la position et l'orientation après chaque mouvement.
        adventurer.move(); // Avance 'A'
        assertEquals(0, adventurer.getPosX());
        assertEquals(-1, adventurer.getPosY());

        adventurer.move(); // Avance 'A'
        assertEquals(0, adventurer.getPosX());
        assertEquals(-2, adventurer.getPosY());

        adventurer.move(); // Tourner à Droite 'D'
        adventurer.move(); // Avance 'A'
        assertEquals(1, adventurer.getPosX());
        assertEquals(-2, adventurer.getPosY());
        assertEquals(Adventurer.Orientation.E, adventurer.getOrientation());
    }

    @Test
    public void testTurnLeftAndRight() {
        // Teste la rotation à gauche et à droite
        adventurer.move(); // Avance 'A'
        adventurer.move(); // Avance 'A'
        adventurer.move(); // Tourner à Droite 'D'
        assertEquals(Adventurer.Orientation.E, adventurer.getOrientation());

        adventurer = new Adventurer("Indiana", 0, 0, Adventurer.Orientation.N, "G");
        adventurer.move(); // Tourner à Gauche 'G'
        assertEquals(Adventurer.Orientation.O, adventurer.getOrientation());
    }

    @Test
    public void testTreasureCollection() {
        // Tester la collecte des trésors
        adventurer.collectTreasure();
        adventurer.collectTreasure();
        assertEquals(2, adventurer.getTreasuresCollected());
    }

    @Test
    public void testRemainingMovementSequence() {
        // Vérifie que la séquence de mouvements est correctement mise à jour.
        adventurer.move(); // Avance 'A'
        assertEquals("ADA", adventurer.getMovementSequence());

        adventurer.move(); // Avance 'A'
        assertEquals("DA", adventurer.getMovementSequence());
    }
}
