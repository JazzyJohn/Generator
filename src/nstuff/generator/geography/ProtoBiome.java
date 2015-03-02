package nstuff.generator.geography;

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
    LAKE{
        @Override
        public String toString() {
            return "L";
        }
    },
    SAND_DESERT{
        @Override
        public String toString() {
            return "D";
        }
    },
    STONE_DESERT{
        @Override
        public String toString() {
            return "SD";
        }
    },
    MEADOWS{
        @Override
        public String toString() {
            return "ME";
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
    STEPPE{
        @Override
        public String toString() {
            return "ST";
        }
    },
    TUNDRA{
        @Override
        public String toString() {
            return "T";
        }
    },
    FOREST{
        @Override
        public String toString() {
            return "F";
        }
    },
    MOUNTAIN{
        @Override
        public String toString() {
            return "M";
        }
    },
    MOUNTAIN_TOP{
        @Override
        public String toString() {
            return "MT";
        }
    },
    BADLANDS{
        @Override
        public String toString() {
            return "B";
        }
    }


}
