package nstuff.world.geography;

/**
 * Created by Ivan.Ochincenko on 02.03.15.
 */
public enum ProtoBiome {
    GLACIER {
        @Override
        public String toString() {
            return "G";
        }
        @Override
        public String getName() {
            return "Glacier";
        }
    },
    OCEAN{
        @Override
        public String toString() {
            return "O";
        }

        @Override
        public String getName() {
            return "Ocean";
        }
    },
    SHOAL{
        @Override
        public String toString() {
            return "S";
        }
        @Override
        public String getName() {
            return "Shoal";
        }
    },
  /*  LAKE{
        @Override
        public String toString() {
            return "L";
        }
    },*/
    DESERT_SAND{
        @Override
        public String toString() {
            return "D";
        }
        @Override
        public String getName() {
          return "Desert";
        }
    },
    DESERT_ROCK{
        @Override
        public String toString() {
            return "DR";
        }
        @Override
        public String getName() {
            return "Rock Desert";
        }
    },
    GRASSLAND{
        @Override
        public String toString() {
            return "GR";
        }
        @Override
        public String getName() {
            return "Savanna";
        }
    },
    SAVANNA{
        @Override
        public String toString() {
            return "SA";
        }
        @Override
        public String getName() {
            return "Savanna";
        }
    },
    SWAMP{
        @Override
        public String toString() {
            return "SW";
        }
        @Override
        public String getName() {
            return "Swamp";
        }
    },
    SHRUBLAND{
        @Override
        public String toString() {
            return "SH";
        }

        @Override
        public String getName() {
            return "Shrubland";
        }
    },
    TUNDRA{
        @Override
        public String toString() {
            return "T";
        }
        @Override
        public String getName() {
            return "Tundra";
        }
    },
    FOREST_BROADLEAF{
        @Override
        public String toString() {
            return "FB";
        }
        @Override
        public String getName() {
            return "Leaf Forest";
        }
    },
    FOREST_CONIFER{
        @Override
        public String toString() {
            return "FC";
        }
        @Override
        public String getName() {
            return "Conifer Forest";
        }
    },
    MOUNTAIN{
        @Override
        public String toString() {
            return "M";
        }
        @Override
        public String getName() {
            return "Mountain";
        }
    },
    MOUNTAIN_PEAK{
        @Override
        public String toString() {
            return "MP";
        }
        @Override
        public String getName() {
            return "Mountain Peak";
        }
    },
    BADLANDS{
        @Override
        public String toString() {
            return "B";
        }
        @Override
        public String getName() {
            return "Badlands";
        }
    },
    JUNGLE{
        @Override
        public String toString() {
            return "J";
        }

        @Override
        public String getName() {
            return "Jungle";
        }
    };

    public String getName() {
        return "";
    }


}
