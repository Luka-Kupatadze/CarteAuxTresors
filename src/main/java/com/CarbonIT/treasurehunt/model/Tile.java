package com.CarbonIT.treasurehunt.model;


public class Tile {
        // Enumération pour les types de terrain
        public enum Terrain {
            PLAIN, MOUNTAIN, TREASURE
        }

        private Terrain terrain;  // Le type de terrain de la case
        private int treasureCount;  // Nombre de trésors dans cette case (utile si le terrain est TREASURE)

        /**
         * Constructeur pour Tile.
         * @param terrain Le type de terrain de la case.
         */
        public Tile(Terrain terrain) {
            this.terrain = terrain;
            this.treasureCount = 0;  // Initialement, il n'y a pas de trésor
        }

        /**
         * Ajoute des trésors à cette case. Ne fait rien si ce n'est pas une case de type TREASURE.
         * @param count Nombre de trésors à ajouter.
         */
        public void addTreasures(int count) {
            if (terrain == Terrain.TREASURE) {
                this.treasureCount += count;
            }
        }

        /**
         * Retire un trésor de cette case. Ne fait rien si aucun trésor n'est disponible ou si ce n'est pas une case TREASURE.
         */
        public void removeTreasure() {

            if (terrain == Terrain.TREASURE && treasureCount > 0) {
                treasureCount--;
            }
            else if (treasureCount == 1) {
                terrain = Terrain.PLAIN;
                treasureCount = 0;
            }
        }


        /**
         * Obtient le type de terrain de la case.
         * @return Le type de terrain.
         */
        public Terrain getTerrain() {
            return terrain;
        }

        /**
         * Définit le type de terrain de cette case.
         * @param terrain Le nouveau type de terrain.
         */
        public void setTerrain(Terrain terrain) {
            this.terrain = terrain;
        }

        /**
         * Obtient le nombre de trésors présents dans cette case.
         * @return Le nombre de trésors.
         */
        public int getTreasureCount() {
            return treasureCount;
        }


}
