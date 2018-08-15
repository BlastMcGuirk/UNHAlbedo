package chrisandbrendanappdev.unhalbedo.data;

/**
 * A Java class that holds the different enumerated types required for storing data
 */

public class DataEnums {

    public enum StationID {
        NHCS10("NH-CS-10", 1, 1), NHMR4("NH-MR-4", 2, 2), NHHL25("NH-HL-25", 3, 3),
        NHCH19("NH-CH-19", 4, 4), NHHL58("NH-HL-58", 5, 5), NHST99("NH-ST-99", 6, 6),
        NHRC46("NH-RC-46", 7, 7), NHMR6("NH-MR-6", 8, 8), NHMR58("NH-MR-58", 9, 9),
        NHGR1("NH-GR-1", 10, 10), NHGR11("NH-GR-11", 11, 11), NHBK24("NH-BK-24", 12, 12),
        NHCS7("NH-CS-7", 13, 13), NONE("None", 0, 0);

        private String stationName;
        private double latitude, longitude;

        StationID(String name, double lat, double lon) {
            stationName = name;
            latitude = lat;
            longitude = lon;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        @Override
        public String toString() {
            return stationName;
        }
    }

    public enum CloudCover {
        ACLR("All Clear"), CLR("Clear"), PCL("Partly Cloudy"), OVC("Overcast");

        private String stringName;

        CloudCover(String name) {
            stringName = name;
        }

        @Override
        public String toString() {
            return stringName;
        }
    }

    public enum SnowSurfaceAge {
        CURRENTLY_SNOWING("Currently Snowing"), LESS_THAN_ONE_DAY("< 1 Day"),
        ONE_DAY("1 Day"), TWO_DAYS("2 Days"), THREE_DAYS("3 Days"),
        FOUR_DAYS("4 Days"), FIVE_DAYS("5 Days"), SIX_DAYS("6 Days"),
        ONE_WEEK("1 Week"), TWO_WEEKS("2 Weeks"), THREE_WEEKS("3 Weeks"),
        FOUR_PLUS_WEEKS("4+ Weeks");

        private String stringName;

        SnowSurfaceAge(String name) {
            stringName = name;
        }

        @Override
        public String toString() { return stringName; }
    }

    public enum GroundCover {
        GRASS_LIVING("Grass (Living)"), GRASS_DEAD("Grass (Dead)"),
        SOIL_WET("Wet Soil"), SOIL_DRY("Dry Soil"),
        PAVEMENT("Pavement"), WOODEN_DECK("Wooden Deck"),
        OTHER("Other");

        private String typeName;

        GroundCover(String type) {
            typeName = type;
        }

        @Override
        public String toString() { return typeName; }
    }

}
