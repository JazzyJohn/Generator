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
    },
    OCEAN{
        @Override
        public String toString() {
            return "O";
        }
    },
    SHOAL{
        @Override
        public String toString() {
            return "S";
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
    },
    DESERT_ROCK{
        @Override
        public String toString() {
            return "DR";
        }
    },
    GRASSLAND{
        @Override
        public String toString() {
            return "GR";
        }
    },
    SAVANNA{
        @Override
        public String toString() {
            return "SA";
        }
    },
    SWAMP{
        @Override
        public String toString() {
            return "SW";
        }
    },
    SHRUBLAND{
        @Override
        public String toString() {
            return "SH";
        }
    },
    TUNDRA{
        @Override
        public String toString() {
            return "T";
        }
    },
    FOREST_BROADLEAF{
        @Override
        public String toString() {
            return "FB";
        }
    },
    FOREST_CONIFER{
        @Override
        public String toString() {
            return "FC";
        }
    },
    MOUNTAIN{
        @Override
        public String toString() {
            return "M";
        }
    },
    MOUNTAIN_PEAK{
        @Override
        public String toString() {
            return "MP";
        }
    },
    BADLANDS{
        @Override
        public String toString() {
            return "B";
        }
    },
    JUNGLE{
        @Override
        public String toString() {
            return "J";
        }
    }


}
