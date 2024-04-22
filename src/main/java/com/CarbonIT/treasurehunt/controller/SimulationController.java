package com.CarbonIT.treasurehunt.controller;

import com.CarbonIT.treasurehunt.model.TreasureMap;
import com.CarbonIT.treasurehunt.util.FileWriter;

import java.io.IOException;

public class SimulationController {

    private TreasureMap map;

    /**
     * Constructeur pour le contrôleur de simulation.
     * @param map La carte du trésor à utiliser pour la simulation.
     */
    public SimulationController(TreasureMap map) {
        this.map = map;
    }

    /**
     * Exécute un cycle complet de simulation.
     */
    public void runSimulation() {
        // Tant qu'il reste des mouvements à effectuer
        // Faire avancer tous les aventuriers
        while (map.hasRemainingMoves()) map.moveAdventurers();
        // Afficher le résultat final
        try {
            FileWriter.saveMapToFile(map,"src/main/resources/output.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
