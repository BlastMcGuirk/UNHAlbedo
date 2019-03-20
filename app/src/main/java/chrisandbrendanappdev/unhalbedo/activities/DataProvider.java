package chrisandbrendanappdev.unhalbedo.activities;

import chrisandbrendanappdev.unhalbedo.data.DataSubmission;

/**
 *  A class that holds a DataSubmission for the survey
 */

public interface DataProvider {
    DataSubmission getData();
    void setData(DataSubmission savedData);
}
