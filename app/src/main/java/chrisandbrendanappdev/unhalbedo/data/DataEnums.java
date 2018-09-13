package chrisandbrendanappdev.unhalbedo.data;

/**
 * A Java class that holds the different enumerated types required for storing data
 */

public class DataEnums {

    public enum StationID {
        NONE("None", 0, 0),
        NHCH19("NH-CH-19", 42.93995, -72.313214),
        NHCS10("NH-CS-10", 44.388238, -71.269535),
        NHMR4("NH-MR-4", 43.149944, -71.556467),
        NHHL25("NH-HL-25", 42.914089, -71.610217),
        NHHL58("NH-HL-58", 42.739811, -71.472811),
        NHST99("NH-ST-99", 43.10876, -70.94871),
        NHRC46("NH-RC-46", 43.0171, -70.999),
        NHMR6("NH-MR-6", 43.52, -71.819),
        NHMR58("NH-MR-58", 43.2783, -71.4697),
        NHGR1("NH-GR-1", 43.5949, -71.7414),
        NHGR11("NH-GR-11", 43.760317, -71.688856),
        NHBK24("NH-BK-24", 43.437617, -71.213364),
        NHCS7("NH-CS-7", 44.49611, -71.57639);

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

        public void setOtherType(String type) {
            typeName = type;
        }
    }

    public enum SnowState {
        SNOW_COVERED("Snow-covered"), PATCHY_SNOW("Patchy Snow"),
        SNOW_FREE_DORMANT("Snow-free/Dormant"), SNOW_FREE_GREEN("Snow-free/Green");

        private String typeName;

        SnowState(String type) {
            typeName = type;
        }

        @Override
        public String toString() {
            return typeName;
        }
    }

}
