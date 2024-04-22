package com.CarbonIT.treasurehunt.model;

public class Adventurer {

    private String name;  // Nom de l'aventurier
    private int posX;  // Position horizontale sur la carte
    private int posY;  // Position verticale sur la carte
    private Orientation orientation;  // Orientation actuelle de l'aventurier
    private String movementSequence;  // Séquence de mouvements sous forme de chaîne de caractères
    private int treasuresCollected;  // Nombre de trésors collectés

    /**
     * Enumération des orientations possibles pour les aventuriers.
     */
    public enum Orientation {
        N, E, S, O
    }

    /**
     * Constructeur pour Adventurer.
     * @param name Nom de l'aventurier.
     * @param posX Position horizontale initiale.
     * @param posY Position verticale initiale.
     * @param orientation Orientation initiale.
     * @param movementSequence Séquence de mouvements.
     */
    public Adventurer(String name, int posX, int posY, Orientation orientation, String movementSequence) {
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.orientation = orientation;
        this.movementSequence = movementSequence;
        this.treasuresCollected = 0;  // Initialisation à zéro
    }

    /**
     * Effectue un mouvement selon l'orientation et la commande actuelle.
     */
    public void move() {
        if (movementSequence.isEmpty()) {
            return;
        }
        char command = movementSequence.charAt(0);
        movementSequence = movementSequence.substring(1);

        switch (command) {
            case 'A':
                advance();
                break;
            case 'G':
                turnLeft();
                break;
            case 'D':
                turnRight();
                break;
            default:
                break;
        }
    }

    // L'aventurier n'avance pas mais on enleve une case
    public void removeMove() {
        if (movementSequence.isEmpty()) {
            return;
        }
        movementSequence = movementSequence.substring(1);
    }


    private void advance() {
        switch (orientation) {
            case N:
                posY--;
                break;
            case S:
                posY++;
                break;
            case E:
                posX++;
                break;
            case O:
                posX--;
                break;
        }
    }

    public int[] previsionMove() {
        int[] prevision = new int[2];

        if (getMovementSequence().split("")[0].equals("D") || getMovementSequence().split("")[0].equals("G")) {
            return new int[] {posX, posY};
        }
        else {
            prevision[0] = posX;
            prevision[1] = posY;
            switch (orientation) {
                case N:
                    prevision[1]--;
                    break;
                case S:
                    prevision[1]++;
                    break;
                case E:
                    prevision[0]++;
                    break;
                case O:
                    prevision[0]--;
                    break;
            }
        }
        return prevision;
    }

    private void turnLeft() {
        System.out.println(orientation.ordinal());
        System.out.println((orientation.ordinal() + 3) % 4);
        this.orientation = Orientation.values()[(orientation.ordinal() + 3) % 4]; // Rotate left
    }

    private void turnRight() {
        // Rotate right
        this.orientation = Orientation.values()[(orientation.ordinal() + 1) % 4];
    }

    public void collectTreasure() {
        treasuresCollected += 1;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int[] getPosition() {
        return new int[] {posX, posY};
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public String getMovementSequence() {
        return movementSequence;
    }

    public int getTreasuresCollected() {
        return treasuresCollected;
    }

}
