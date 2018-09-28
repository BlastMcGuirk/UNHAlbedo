package chrisandbrendanappdev.unhalbedo.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import chrisandbrendanappdev.unhalbedo.data.DataEnums.*;

import static android.R.id.list;

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
    private SnowState snowState;

    // Patchiness Percentage
    private int patchinessPercentage;

    // Snow Surface Age
    private SnowSurfaceAge snowSurfaceAge;
    private boolean snowMelt;

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

    // Notes
    private String notes;

    // End Time
    private Calendar endCalendar;

    public DataSubmission() {
        stationID = null;
        latitude = longitude = 0;
        startCalendar = null;
        cloudCoverage = null;
        snowState = null;
        patchinessPercentage = -999;
        snowSurfaceAge = null;
        snowMelt = false;
        groundCover = null;
        incoming1 = incoming2 = incoming3 = -999;
        outgoing1 = outgoing2 = outgoing3 = -999;
        snowDepth = -999;
        snowWeightWithTube = -999;
        snowTubeWeight = -999;
        temperature = -999;
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
        output += "Snow State: " + snowState + '\n';
        output += "Patchiness Percentage: " + patchinessPercentage + "%" + '\n';
        output += "Snow Surface Age: " + snowSurfaceAge + '\n';
        output += "Snow Melt: " + snowMelt + '\n';
        output += "Ground Cover: " + groundCover + '\n';
        output += "Incoming: " + incoming1 + " - " + incoming2 + " - " + incoming3 + '\n';
        output += "Outgoing: " + outgoing1 + " - " + outgoing2 + " - " + outgoing3 + '\n';
        output += "Snow Depth: " + snowDepth + '\n';
        output += "Snow Weight w/ Tube: " + snowWeightWithTube + '\n';
        output += "Snow Tube Weight: " + snowTubeWeight + '\n';
        output += "Temperature: " + temperature + '\n';
        output += "Notes: " + notes + '\n';
        output += "End Time: " + endCalendar.getTime();
        return output;
    }

    public JSONObject getJSON() {
        JSONObject json = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);

        try {
            // id
            // user - users id
            // date - date of submission (now)

            json.put("station_Number", stationID.toString());
            json.put("latitude", latitude);
            json.put("longitude", longitude);

            json.put("observation_Date", dateFormat.format(startCalendar.getTime()));
            json.put("observation_Time", timeFormat.format(startCalendar.getTime()));
            json.put("end_Albedo_Observation_time", timeFormat.format(endCalendar));

            json.put("cloud_Coverage", cloudCoverage.toString());
            json.put("snow_State", snowState.toString());
            json.put("Patchiness_percentage", patchinessPercentage);
            json.put("snow_surface_Age", snowSurfaceAge.toString());
            json.put("snow_Melt", snowMelt);

            json.put("incoming_Shortwave_1", incoming1);
            json.put("incoming_Shortwave_2", incoming2);
            json.put("incoming_Shortwave_3", incoming3);
            json.put("outgoing_Shortwave_1", outgoing1);
            json.put("outgoing_Shortwave_2", outgoing2);
            json.put("outgoing_Shortwave_3", outgoing3);

            json.put("surface_Skin_Temperature", temperature);
            json.put("snow_Depth", snowDepth);
            json.put("tube_cap_weight", snowTubeWeight);
            json.put("tube_cap_snow_weight", snowWeightWithTube);

            json.put("observation_Notes", notes);

            json.put("albedo", getAlbedo());

            //json.put("snow_density", snowDensity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public StationID getStationID() {return stationID;}
    public void setStationID(StationID stationID) {this.stationID = stationID;}

    public double getLatitude() {return latitude;}
    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}
    public void setLongitude(double longitude) {this.longitude = longitude;}

    public Calendar getStartCalendar() {return startCalendar;}
    public void setStartCalendar(Calendar startCalendar) {this.startCalendar = startCalendar;}
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.US);
        return dateFormat.format(startCalendar.getTime());
    }
    public String getStartTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);
        return timeFormat.format(startCalendar.getTime());
    }

    public CloudCover getCloudCoverage() {return cloudCoverage;}
    public void setCloudCoverage(CloudCover cloudCoverage) {this.cloudCoverage = cloudCoverage;}

    public SnowState getSnowState() {return snowState;}
    public void setSnowState(SnowState snowState) {this.snowState = snowState;}

    public int getPatchinessPercentage() {return patchinessPercentage;}
    public void setPatchinessPercentage(int patchinessPercentage) {this.patchinessPercentage = patchinessPercentage;}

    public SnowSurfaceAge getSnowSurfaceAge() {return snowSurfaceAge;}
    public void setSnowSurfaceAge(SnowSurfaceAge snowSurfaceAge) {this.snowSurfaceAge = snowSurfaceAge;}

    public boolean isSnowMelt() {return snowMelt;}
    public void setSnowMelt(boolean snowMelt) {this.snowMelt = snowMelt;}

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

    public void setTemperature(double temperature) {this.temperature = temperature;}
    public double getTemperature() {return temperature;}

    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}

    public Calendar getEndCalendar() {return endCalendar;}
    public void setEndCalendar(Calendar endCalendar) {this.endCalendar = endCalendar;}
    public String getEndTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);
        return timeFormat.format(endCalendar.getTime());
    }

    public double getAlbedo() {
        return ((outgoing1 / incoming1) + (outgoing2 / incoming2) + (outgoing3 / incoming3)) / 3.0;
    }
}
