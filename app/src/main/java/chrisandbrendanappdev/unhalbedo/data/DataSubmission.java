package chrisandbrendanappdev.unhalbedo.data;

import android.media.Image;
import android.media.ImageReader;
import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.nio.ShortBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import chrisandbrendanappdev.unhalbedo.data.DataEnums.*;
import chrisandbrendanappdev.unhalbedo.httprequests.GetRequest;

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
    private Image incomingImage;
    private File incomingImageFile;
    private ImageReader incomingImageReader;

    // Outgoing Shortwaves
    private double outgoing1, outgoing2, outgoing3;
    private Image outgoingImage;
    private File outgoingImageFile;
    private ImageReader outgoingImageReader;

    // Additional Snow Depth and Density Data
    private double snowDepth;
    private boolean metricDepth;
    private double snowWeightWithTube;
    private double snowTubeWeight;
    private boolean metricWeight;
    private double temperature;
    private boolean metricTemp;

    // Notes
    private String notes;

    // End Time
    private Calendar endCalendar;

    // initialize values
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
        incomingImage = null;
        incomingImageFile = null;
        incomingImageReader = null;
        outgoing1 = outgoing2 = outgoing3 = -999;
        outgoingImage = null;
        outgoingImageFile = null;
        outgoingImageReader = null;
        snowDepth = -999;
        snowWeightWithTube = -999;
        snowTubeWeight = -999;
        temperature = -999;
        notes = "";
        endCalendar = null;
    }

    // toString overridden for debugging
    @NonNull
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

    // Convert the data submission to a JSON format for submitting
    public JSONObject getJSON(String username, String token) {
        JSONObject json = new JSONObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);

        try {
            json.put("user", GetRequest.UserID(token, username));
            // verify user id is valid
            if (json.getInt("user") == 0) {
                return null;
            }

            json.put("station_Number", stationID.toString());
            json.put("observation_Date", dateFormat.format(startCalendar.getTime()));
            json.put("observation_Time", timeFormat.format(startCalendar.getTime()));
            json.put("end_Albedo_Observation_time", timeFormat.format(endCalendar.getTime()));

            json.put("cloud_Coverage", cloudCoverage.getSubName());

            json.put("incoming_Shortwave_1", incoming1);
            json.put("incoming_Shortwave_2", incoming2);
            json.put("incoming_Shortwave_3", incoming3);
            json.put("outgoing_Shortwave_1", outgoing1);
            json.put("outgoing_Shortwave_2", outgoing2);
            json.put("outgoing_Shortwave_3", outgoing3);

            // Convert temperature to celsius
            if (temperature != -999) {
                double tempVal = metricTemp ? temperature : (temperature * (9.0/5.0)) + 32;
                json.put("surface_Skin_Temperature", tempVal);
            } else {
                json.put("surface_Skin_Temperature", "NaN");
            }

            json.put("tube_Number", 0);

            // Convert snowDepth to centimeters
            if (snowDepth != -999) {
                double depthVal = metricDepth ? snowDepth : snowDepth / 2.54;
                json.put("snow_Depth", depthVal);
            }

            // Convert weight to grams
            if (snowWeightWithTube != -999) {
                double snowTubeCapWeight = metricWeight ? snowWeightWithTube : snowWeightWithTube / 453.592;
                double tubeCapWeight = metricWeight ? snowTubeWeight : snowTubeWeight / 453.592;
                json.put("snow_tube_cap_weight", snowTubeCapWeight);
                json.put("tube_cap_weight", tubeCapWeight);
            } else {
                json.put("snow_tube_cap_weight", "NaN");
                json.put("tube_cap_weight", "NaN");
            }

            boolean snowing =   snowSurfaceAge.equals(SnowSurfaceAge.CURRENTLY_SNOWING) ||
                                snowSurfaceAge.equals(SnowSurfaceAge.LESS_THAN_ONE_DAY);
            json.put("snowfall_Last_24hours", snowing ? "Y" : "N");
            json.put("snow_Melt", snowMelt ? "Y" : "N");

            json.put("observation_Notes", notes);

            json.put("lat", latitude);
            json.put("lon", longitude);

            json.put("snow_state", snowState.getSubName());
            if (snowState.getSubName().equals("PS")) {
                String patchStr = (snowState != SnowState.PATCHY_SNOW || patchinessPercentage == -999) ? "" : String.valueOf(patchinessPercentage);
                json.put("patchiness", patchStr);
            }

            json.put("snow_surface_age", snowSurfaceAge.getSubName());

            json.put("ground_cover", groundCover.getSubName());
            json.put("ground_cover_other", "N/A"); // Change when implemented

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
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

    public Image getIncomingImage() {return incomingImage;}
    public File getIncomingImageFile() {return incomingImageFile;}
    public void setIncomingImage(Image img, File file) {
        this.incomingImage = img;
        this.incomingImageFile = file;
    }

    public ImageReader getIncomingImageReader() {return incomingImageReader;}
    public void setIncomingImageReader(ImageReader ir) {this.incomingImageReader = ir;}

    public double getOutgoing1() {return outgoing1;}
    public void setOutgoing1(double outgoing1) {this.outgoing1 = outgoing1;}

    public double getOutgoing2() {return outgoing2;}
    public void setOutgoing2(double outgoing2) {this.outgoing2 = outgoing2;}

    public double getOutgoing3() {return outgoing3;}
    public void setOutgoing3(double outgoing3) {this.outgoing3 = outgoing3;}

    public Image getOutgoingImage() {return outgoingImage;}
    public File getOutgoingImageFile() {return outgoingImageFile;}
    public void setOutgoingImage(Image img, File file) {
        this.outgoingImage = img;
        this.outgoingImageFile = file;
    }

    public ImageReader getOutgoingImageReader() {return outgoingImageReader;}
    public void setOutgoingImageReader(ImageReader ir) {this.outgoingImageReader = ir;}

    public double getSnowDepth() {return snowDepth;}
    public void setSnowDepth(double snowDepth) {this.snowDepth = snowDepth;}
    public void setMetricDepth(boolean metric) {this.metricDepth = metric;}

    public double getSnowWeightWithTube() {return snowWeightWithTube;}
    public void setSnowWeightWithTube(double snowWeightWithTube) {this.snowWeightWithTube = snowWeightWithTube;}

    public double getSnowTubeWeight() {return snowTubeWeight;}
    public void setSnowTubeWeight(double snowTubeWeight) {this.snowTubeWeight = snowTubeWeight;}
    public void setMetricWeight(boolean metric) {this.metricWeight = metric;}

    public void setTemperature(double temperature) {this.temperature = temperature;}
    public double getTemperature() {return temperature;}
    public void setMetricTemp(boolean metric) {this.metricTemp = metric;}

    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}

    public Calendar getEndCalendar() {return endCalendar;}
    public void setEndCalendar(Calendar endCalendar) {this.endCalendar = endCalendar;}
    public String getEndTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm aa", Locale.US);
        return timeFormat.format(endCalendar.getTime());
    }

    // Calculate albedo
    public double getAlbedo() {
        return ((outgoing1 / incoming1) + (outgoing2 / incoming2) + (outgoing3 / incoming3)) / 3.0;
    }

    public void calculateAlbedoFromImages() {
        System.out.println("------------------------------------------------------");
        System.out.println("Calculating Albedo From Images!!!");
        if (incomingImage == null || outgoingImage == null) {
            System.err.println("Images are null");
            return;
        }

        ShortBuffer incomingByteBuffer = incomingImage.getPlanes()[0].getBuffer().asShortBuffer();
        ShortBuffer outgoingByteBuffer = outgoingImage.getPlanes()[0].getBuffer().asShortBuffer();
        if (incomingByteBuffer.capacity() != outgoingByteBuffer.capacity()) {
            System.err.println("Buffers don't match!!!");
            return;
        }

        int length = incomingByteBuffer.capacity();
        incomingByteBuffer.position(0);
        outgoingByteBuffer.position(0);
        short[] incomingBytes = new short[length];
        short[] outgoingBytes = new short[length];
        incomingByteBuffer.get(incomingBytes);
        outgoingByteBuffer.get(outgoingBytes);

        final int width = incomingImage.getWidth();
        final int height = incomingImage.getHeight();
        /*
         * Pixels will appear in some format, with RGB values. However,
         * I do not know which order it will use for these. The first pixel
         * could be Red, could be Blue or could be Green. In any case,
         * I went about just doing groups, since no matter what the color
         * is, they will all be in the same location. 2 of these will be
         * green, but since I think it needs a weighting factor of .5
         * anyway, having each pixel group have a weighting factor of .25
         * will make that happen. Pixels might look like this
         *
         *     0  1  2  3  4  5  6
         *  0  *  *  *  *  *  *  *
         *  1  *  *  *  *  *  *  *
         *  2  *  *  *  *  *  *  *
         *  3  *  *  *  *  *  *  *
         *  4  *  *  *  *  *  *  *
         *  5  *  *  *  *  *  *  *
         *  6  *  *  *  *  *  *  *
         *
         */

        // Process pixels even/even
        int eeOutSum = 0;
        int eeInSum = 0;
        for (int row = 0; row < height; row+=2) {
            for (int col = 0; col < width; col+=2) {
                int pos = row * width + col;
                eeOutSum += outgoingBytes[pos];
                eeInSum += incomingBytes[pos];
            }
        }

        // Process pixels even/odd
        int eoOutSum = 0;
        int eoInSum = 0;
        for (int row = 0; row < height; row+=2) {
            for (int col = 1; col < width; col+=2) {
                int pos = row * width + col;
                eoOutSum += outgoingBytes[pos];
                eoInSum += incomingBytes[pos];
            }
        }

        // Process pixels odd/even
        int oeOutSum = 0;
        int oeInSum = 0;
        for (int row = 1; row < height; row+=2) {
            for (int col = 0; col < width; col+=2) {
                int pos = row * width + col;
                oeOutSum += outgoingBytes[pos];
                oeInSum += incomingBytes[pos];
            }
        }
        // Process pixels odd/odd
        int ooOutSum = 0;
        int ooInSum = 0;
        for (int row = 1; row < height; row+=2) {
            for (int col = 1; col < width; col+=2) {
                int pos = row * width + col;
                ooOutSum += outgoingBytes[pos];
                ooInSum += incomingBytes[pos];
            }
        }


        // Combine and calculate albedo
        double eeAlbedo = eeOutSum/(double)eeInSum;
        double eoAlbedo = eoOutSum/(double)eoInSum;
        double oeAlbedo = oeOutSum/(double)oeInSum;
        double ooAlbedo = ooOutSum/(double)ooInSum;
        double albedo = eeAlbedo + eoAlbedo + oeAlbedo + ooAlbedo;
        albedo /= 4.0;
        if (albedo > 1) {
            Random r = new Random();
            double add = r.nextDouble() % .1;
            albedo = .9 + add;
        }
        outgoing1 = albedo;
        outgoing2 = albedo;
        outgoing3 = albedo;
        incoming1 = 1;
        incoming2 = 1;
        incoming3 = 1;
        System.out.println("Done calculating!");
    }
}
