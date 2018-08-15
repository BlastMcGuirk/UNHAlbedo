package chrisandbrendanappdev.unhalbedo.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import chrisandbrendanappdev.unhalbedo.data.DataEnums.*;

/**
 * Stores all the information that is submitted in a submission
 */

public class DataSubmission implements Serializable {

    // Location
    private StationID stationID;
    private double latitude;
    private double longitude;

    // Start Time
    private Calendar startCalendar;

    // Sky Analysis
    private CloudCover cloudCoverage;

    // Snow State
    private int patchinessPercentage;

    // Snow Surface Age
    private SnowSurfaceAge snowSurfaceAge;

    // Ground Cover
    private GroundCover groundCover;

    // Incoming Shortwaves
    private double incoming1, incoming2, incoming3;

    // Outgoing Shortwaves
    private double outgoing1, outgoing2, outgoing3;

    // Additional Snow Depth and Density Data
    private double snowDepth;
    private double snowWeightWithTube;
    private double snowTubeWeight;
    private double temperature;
    private boolean snowMelt;

    // Notes
    private String notes;

    // End Time
    private Calendar endCalendar;

    public DataSubmission() {
        stationID = null;
        latitude = longitude = -999;
        startCalendar = null;
        cloudCoverage = null;
        patchinessPercentage = -999;
        snowSurfaceAge = null;
        groundCover = null;
        incoming1 = incoming2 = incoming3 = -999;
        outgoing1 = outgoing2 = outgoing3 = -999;
        snowDepth = -999;
        snowWeightWithTube = -999;
        snowTubeWeight = -999;
        temperature = -999;
        snowMelt = false;
        notes = "";
        endCalendar = null;
    }

    @Override
    public String toString() {
        String output = "";
        output += "Station: " + stationID + '\n';
        output += "Lat/Lon: " + latitude + "/" + longitude + '\n';
        output += "Start Date: " + startCalendar.getTime() + '\n';
        output += "Cloud Coverage: " + cloudCoverage + '\n';
        output += "Patchiness Percentage: " + patchinessPercentage + "%" + '\n';
        output += "Snow Surface Age: " + snowSurfaceAge + '\n';
        output += "Ground Cover: " + groundCover + '\n';
        output += "Incoming: " + incoming1 + " - " + incoming2 + " - " + incoming3 + '\n';
        output += "Outgoing: " + outgoing1 + " - " + outgoing2 + " - " + outgoing3 + '\n';
        output += "Snow Depth: " + snowDepth + '\n';
        output += "Snow Weight w/ Tube: " + snowWeightWithTube + '\n';
        output += "Snow Tube Weight: " + snowTubeWeight + '\n';
        output += "Temperature: " + temperature + '\n';
        output += "Snow Melt: " + snowMelt + '\n';
        output += "Notes: " + notes + '\n';
        output += "End Time: " + endCalendar.getTime();
        return output;
    }

    public StationID getStationID() {return stationID;}
    public void setStationID(StationID stationID) {this.stationID = stationID;}

    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}

    public Calendar getStartCalendar() {return startCalendar;}
    public void setStartCalendar(Calendar startCalendar) {this.startCalendar = startCalendar;}

    public CloudCover getCloudCoverage() {return cloudCoverage;}
    public void setCloudCoverage(CloudCover cloudCoverage) {this.cloudCoverage = cloudCoverage;}

    public int getPatchinessPercentage() {return patchinessPercentage;}
    public void setPatchinessPercentage(int patchinessPercentage) {this.patchinessPercentage = patchinessPercentage;}

    public SnowSurfaceAge getSnowSurfaceAge() {return snowSurfaceAge;}
    public void setSnowSurfaceAge(SnowSurfaceAge snowSurfaceAge) {this.snowSurfaceAge = snowSurfaceAge;}

    public GroundCover getGroundCover() {return groundCover;}
    public void setGroundCover(GroundCover groundCover) {this.groundCover = groundCover;}

    public double getIncoming1() {return incoming1;}
    public void setIncoming1(double incoming1) {this.incoming1 = incoming1;}

    public double getIncoming2() {return incoming2;}
    public void setIncoming2(double incoming2) {this.incoming2 = incoming2;}

    public double getIncoming3() {return incoming3;}
    public void setIncoming3(double incoming3) {this.incoming3 = incoming3;}

    public double getOutgoing1() {return outgoing1;}
    public void setOutgoing1(double outgoing1) {this.outgoing1 = outgoing1;}

    public double getOutgoing2() {return outgoing2;}
    public void setOutgoing2(double outgoing2) {this.outgoing2 = outgoing2;}

    public double getOutgoing3() {return outgoing3;}
    public void setOutgoing3(double outgoing3) {this.outgoing3 = outgoing3;}

    public double getSnowDepth() {return snowDepth;}
    public void setSnowDepth(double snowDepth) {this.snowDepth = snowDepth;}

    public double getSnowWeightWithTube() {return snowWeightWithTube;}
    public void setSnowWeightWithTube(double snowWeightWithTube) {this.snowWeightWithTube = snowWeightWithTube;}

    public double getSnowTubeWeight() {return snowTubeWeight;}
    public void setSnowTubeWeight(double snowTubeWeight) {this.snowTubeWeight = snowTubeWeight;}

    public double getTemperature() {return temperature;}
    public void setTemperature(double temperature) {this.temperature = temperature;}

    public boolean isSnowMelt() {return snowMelt;}
    public void setSnowMelt(boolean snowMelt) {this.snowMelt = snowMelt;}

    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}

    public Calendar getEndCalendar() {return endCalendar;}
    public void setEndCalendar(Calendar endCalendar) {this.endCalendar = endCalendar;}
}
