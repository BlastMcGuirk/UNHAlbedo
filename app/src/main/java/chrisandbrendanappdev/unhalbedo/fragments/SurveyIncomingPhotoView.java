package chrisandbrendanappdev.unhalbedo.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyIncomingPhotoView extends SurveyFragment {

    // Views
    private ImageView imageView;
    private Button mRetakePhotoButton;
    private Button mUsePhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.survey_display_picture_fragment, container, false);

        init(v, "Incoming Shortwave Pic");

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceLayout) {
        Image img = data.getIncomingImage();
        File file = data.getIncomingImageFile();
        imageView.setImageBitmap(generateBitMap(img, file));
    }

    @Override
    void getViews(View v) {
        imageView = v.findViewById(R.id.survey_display_image_view);
        mRetakePhotoButton = v.findViewById(R.id.survey_display_retake_photo);
        mUsePhoto = v.findViewById(R.id.survey_display_use_photo);
    }

    @Override
    void addOnClickListeners() {
        mRetakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setIncomingImage(null, null);
                data.getIncomingImageReader().close();
                saveDataAndContinueWithoutAddingToBackStack(new SurveyIncomingPhoto());
            }
        });
        mUsePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataAndContinue(new SurveyOutgoingPhoto());
            }
        });
    }

    private static Bitmap generateBitMap(Image img, File file) {
        System.out.println("Doing stuff");
        final Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        int scaledWidth = img.getWidth()/3;
        int scaledHeight = img.getHeight()/3;
        Matrix m = new Matrix();
        m.postRotate(90);
        final Bitmap scaled = Bitmap.createScaledBitmap(bm, scaledWidth, scaledHeight, true);
        System.out.println("Almost done doing");
        return Bitmap.createBitmap(scaled, 0, 0, scaledWidth, scaledHeight, m, true);
    }
}
