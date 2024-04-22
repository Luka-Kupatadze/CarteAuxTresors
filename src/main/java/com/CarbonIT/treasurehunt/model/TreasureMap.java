package com.CarbonIT.treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

public class TreasureMap {

        private Tile[][] map;  // Grille représentant les tuiles de la carte
        private List<Adventurer> adventurers;  // Liste des aventuriers sur la carte

        /**
         * Constructeur pour TreasureMap.
         * @param width Largeur de la carte (nombre de colonnes).
         * @param height Hauteur de la carte (nombre de lignes).
         */
        public TreasureMap(int width, int height) {
            map = new Tile[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    map[i][j] = new Tile(Tile.Terrain.PLAIN);  // Initialisation par défaut à PLAIN
                }
            }
            adventurers = new ArrayList<>();
        }

        /**
         * Ajoute un aventurier à la carte.
         * @param adventurer L'aventurier à ajouter.
         */
        public void addAdventurer(Adventurer adventurer) {
            if (isPositionValid(adventurer.getPosX(), adventurer.getPosY())) {
                adventurers.add(adventurer);
            }
        }

        public List<Adventurer> getAdventurers() {
            return adventurers;
        }

        /**
         * Vérifie si la position est dans les limites de la carte.
         * @param x Position horizontale.
         * @param y Position verticale.
         * @return true si la position est valide, false sinon.
         */
        private boolean isPositionValid(int y, int x) {
            return x >= 0 && x < map[0].length && y >= 0 && y < map.length;
        }

        /**
         * Récupère la tuile à une position donnée.
         * @param x Position horizontale.
         * @param y Position verticale.
         * @return La tuile à la position spécifiée.
         */
        public Tile getTile(int x, int y) {
            if (isPositionValid(y, x)) {
                return map[y][x];
            } else {
                throw new IllegalArgumentException("Position out of map bounds");
            }
        }

        /**
         * Déplace tous les aventuriers sur la carte selon leur séquence de mouvements.
         */
        public void moveAdventurers() {
            for (Adventurer adventurer : adventurers) {
                int[] prevision = adventurer.previsionMove();
                // Vérifier la nouvelle position et gérer les collisions ou les trésors
                if (isPositionValid(prevision[1] , prevision[0]) && map[prevision[1]][prevision[0]].getTerrain() != Tile.Terrain.MOUNTAIN) {
                    if ( isPositionValid(prevision[1] , prevision[0]) && (adventurer.getPosX() != prevision[0] || adventurer.getPosY() != prevision[1])){
                        adventurer.move();
                        if (map[adventurer.getPosY()][adventurer.getPosX()].getTerrain() == Tile.Terrain.TREASURE) {
                            adventurer.collectTreasure();
                        }
                    }
                    else{
                        adventurer.move();
                    }
                }
                else {
                    adventurer.removeMove();
                }

            }
        }


        public boolean hasRemainingMoves() {
            for (Adventurer adventurer : adventurers) {
                if (!adventurer.getMovementSequence().isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        public int getWidth() {
            return map[0].length;
        }

        public int getHeight() {
            return map.length;
        }

}
