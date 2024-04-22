package com.CarbonIT.treasurehunt;

import com.CarbonIT.treasurehunt.controller.SimulationController;
import com.CarbonIT.treasurehunt.util.FileReader;

import java.io.FileNotFoundException;

public class App {

    //lancement de l'application TreasureHunt
    public static void main(String[] args) throws FileNotFoundException {

        SimulationController simulation = new SimulationController(FileReader.readMapFromFile("src/main/resources/input.txt"));
        simulation.runSimulation();

    }
}
