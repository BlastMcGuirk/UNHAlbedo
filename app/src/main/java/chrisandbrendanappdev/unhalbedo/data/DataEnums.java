package chrisandbrendanappdev.unhalbedo.data;

import android.support.annotation.NonNull;

/**
 * A Java class that holds the different enumerated types required for storing data
 */

public class DataEnums {

    // Station ID of submission, containing lat/lon coordinates of station
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

        @NonNull
        @Override
        public String toString() {
            return stationName;
        }
    }

    // Cloud cover text and acronym
    public enum CloudCover {
        ACLR("All Clear", "ACLR"), CLR("Clear", "CLR"), PCL("Partly Cloudy", "PCL"), OVC("Overcast", "OVC");

        private String stringName;
        private String subName;

        CloudCover(String name, String sub) {
            stringName = name;
            subName = sub;
        }

        public String getSubName() { return subName; }

        @NonNull
        @Override
        public String toString() {
            return stringName;
        }
    }

    // Snow Surface Age display text and submission text
    public enum SnowSurfaceAge {
        CURRENTLY_SNOWING("Currently Snowing", "N/A"), LESS_THAN_ONE_DAY("< 1 Day", "<1d"),
        ONE_DAY("1 Day", "1d"), TWO_DAYS("2 Days", "2d"), THREE_DAYS("3 Days", "3d"),
        FOUR_DAYS("4 Days", "4d"), FIVE_DAYS("5 Days", "5d"), SIX_DAYS("6 Days", "6d"),
        ONE_WEEK("1 Week", "1w"), TWO_WEEKS("2 Weeks", "2w"), THREE_WEEKS("3 Weeks", "3w"),
        FOUR_PLUS_WEEKS("4+ Weeks", "4w");

        private String stringName;
        private String subName;

        SnowSurfaceAge(String name, String sub) {
            stringName = name;
            subName = sub;
        }

        public String getSubName() { return subName; }

        @NonNull
        @Override
        public String toString() { return stringName; }
    }

    // Ground Cover display text and submission text
    public enum GroundCover {
        GRASS_LIVING("Grass (Living)", "GL"), GRASS_DEAD("Grass (Dead)", "GD"),
        SOIL_WET("Wet Soil", "WS"), SOIL_DRY("Dry Soil", "DS"),
        PAVEMENT("Pavement", "P"), WOODEN_DECK("Wooden Deck", "WD"),
        OTHER("Other", "Ot");

        private String typeName;
        private String subName;

        GroundCover(String type, String sub) {
            typeName = type;
            subName = sub;
        }

        public String getSubName() { return subName; }

        @NonNull
        @Override
        public String toString() { return typeName; }

        public void setOtherType(String type) {
            typeName = type;
        }
    }

    // Snow State display text and submission text
    public enum SnowState {
        SNOW_COVERED("Snow-covered", "SC"), PATCHY_SNOW("Patchy Snow", "PS"),
        SNOW_FREE_DORMANT("Snow-free/Dormant", "SFD"), SNOW_FREE_GREEN("Snow-free/Green", "SFG");

        private String typeName;
        private String subName;

        SnowState(String type, String sub) {
            typeName = type;
            subName = sub;
        }

        public String getSubName() { return subName; }

        @NonNull
        @Override
        public String toString() {
            return typeName;
        }
    }

}
