package chrisandbrendanappdev.unhalbedo.activities;

import chrisandbrendanappdev.unhalbedo.data.DataSubmission;

/**
 * Created by Brendan on 8/4/2018.
 */

public interface DataProvider {
    DataSubmission getData();
    void setData(DataSubmission savedData);
}
