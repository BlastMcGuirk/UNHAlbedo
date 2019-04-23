package chrisandbrendanappdev.unhalbedo.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.DngCreator;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import chrisandbrendanappdev.unhalbedo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyOutgoingPhoto extends SurveyFragment {

    // Views
    private Button mTakePhotoButton;

    private File mRawGalleryFolder;
    private String mCameraId;
    private CameraCharacteristics mCameraCharacteristics;
    private CaptureResult mCaptureResult;
    private CameraDevice mCameraDevice;
    private CameraDevice.StateCallback mCameraDeviceStateCallback
            = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            mCameraDevice = camera;
            createSession();
            // Toast.makeText(getApplicationContext(), "Camera Opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };
    private CameraCaptureSession mCameraCaptureSession;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;
    private static File mRawImageFile;
    private static CountDownLatch rawCountdown = new CountDownLatch(1);
    private ImageReader mRawImageReader;
    private final ImageReader.OnImageAvailableListener mOnRawImageAvailableListener =
            new ImageReader.OnImageAvailableListener() {

                @Override
                public void onImageAvailable(ImageReader reader) {
                    // Camera stuff
                    Image rawImage = reader.acquireNextImage();
                    mBackgroundHandler.post(new ImageSaver(mActivity, rawImage,
                            mCaptureResult, mCameraCharacteristics));
                }
            };
    private Activity mActivity;

    private class ImageSaver implements Runnable {

        private final Image mImage;
        private final CaptureResult mCaptureResult;
        private final CameraCharacteristics mCameraCharacteristics;

        private ImageSaver(Activity activity, Image image, CaptureResult captureResult,
                           CameraCharacteristics cameraCharacteristics) {
            mActivity = activity;
            mImage = image;
            mCaptureResult = captureResult;
            mCameraCharacteristics = cameraCharacteristics;
        }

        @Override
        public void run() {
            int format = mImage.getFormat();
            switch (format) {
                case ImageFormat.RAW_SENSOR:
                    try {
                        rawCountdown.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DngCreator dngCreator = new DngCreator(mCameraCharacteristics, mCaptureResult);
                    FileOutputStream rawFileOutputStream;
                    try {
                        System.out.println("Saving file...");
                        rawFileOutputStream = new FileOutputStream(mRawImageFile);
                        dngCreator.writeImage(rawFileOutputStream, mImage);
                        System.out.println("Saved!");

                        data.setOutgoingImage(mImage, mRawImageFile);
                        data.setOutgoingImageReader(mRawImageReader);
                        saveDataAndContinueWithoutAddingToBackStack(new SurveyOutgoingPhotoView());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadData();
        if (data.getOutgoingImage() != null) {
            saveDataAndContinueWithoutAddingToBackStack(new SurveyOutgoingPhotoView());
        }

        View v = inflater.inflate(R.layout.survey_outgoing_picture_fragment, container, false);

        init(v, "Outgoing Shortwave Pic");

        mActivity = getActivity();
        createImageGallery();

        return v;
    }

    @Override
    void getViews(View v) {
        mTakePhotoButton = v.findViewById(R.id.survey_picture_take);
    }

    @Override
    void addOnClickListeners() {
        mTakePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTakePhotoButton.setText(R.string.survey_picture_taking_photo_label);
                    }
                });
                captureStillImage();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        openBackgroundThread();

        setupCamera();
        openCamera();
    }

    @Override
    public void onPause() {

        closeCamera();
        closeBackgoundThread();

        super.onPause();
    }

    private void createImageGallery() {
        File storageDirectory = Objects.requireNonNull(getActivity()).getFilesDir();
        mRawGalleryFolder = new File(storageDirectory, "Raw Images");
        if (mRawGalleryFolder.exists()) {
            File[] files = mRawGalleryFolder.listFiles();
            for (File f : files) {
                boolean del = f.delete();
                if (!del) {
                    System.err.println("Cannot delete: " + f.getName());
                }
            }
            boolean del = mRawGalleryFolder.delete();
            if (!del) {
                System.err.println("Cannot delete gallery");
            }
        }
        boolean res = mRawGalleryFolder.mkdirs();
        if (!res) {
            System.err.println("Cannot make dir");
        }
    }

    File createRawImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "RAW_" + timeStamp + "_";

        return File.createTempFile(imageFileName, ".dng", mRawGalleryFolder);

    }

    private void setupCamera() {
        CameraManager cameraManager = (CameraManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (!contains(cameraCharacteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)
                )) {
                    continue;
                }
                if (cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) ==
                        CameraCharacteristics.LENS_FACING_FRONT) {
                    continue;
                }

                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                assert map != null;
                Size largestRawImageSize = Collections.max(
                        Arrays.asList(map.getOutputSizes(ImageFormat.RAW_SENSOR)),
                        new CompareSizeByArea());
                mRawImageReader = ImageReader.newInstance(largestRawImageSize.getWidth(),
                        largestRawImageSize.getHeight(),
                        ImageFormat.RAW_SENSOR,
                        1);
                mRawImageReader.setOnImageAvailableListener(mOnRawImageAvailableListener,
                        mBackgroundHandler);

                mCameraId = cameraId;
                mCameraCharacteristics = cameraCharacteristics;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager cameraManager = (CameraManager) Objects.requireNonNull(getActivity()).getSystemService(Context.CAMERA_SERVICE);
        try {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                System.err.println("Permission not granted! :(");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[] {Manifest.permission.CAMERA},
                        2);
            } else {
                System.out.println("Permission is already granted!");
            }
            cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {

        if(mCameraCaptureSession != null) {
            mCameraCaptureSession.close();
            mCameraCaptureSession = null;
        }
        if(mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    private void createSession() {
        try {
            mCameraDevice.createCaptureSession(Collections.singletonList(mRawImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mCameraCaptureSession = session;
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                            Toast.makeText(
                                    getActivity(),
                                    "create camera session failed!",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera2 background thread");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void closeBackgoundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void captureStillImage() {

        try {
            CaptureRequest.Builder captureStillBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            captureStillBuilder.addTarget(mRawImageReader.getSurface());

            CameraCaptureSession.CaptureCallback captureCallback =
                    new CameraCaptureSession.CaptureCallback() {
                        @Override
                        public void onCaptureStarted(@NonNull CameraCaptureSession session,
                                                     @NonNull CaptureRequest request,
                                                     long timestamp, long frameNumber) {
                            super.onCaptureStarted(session, request, timestamp, frameNumber);

                            try {
                                mRawImageFile = createRawImageFile();
                                rawCountdown.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                       @NonNull CaptureRequest request,
                                                       @NonNull TotalCaptureResult result) {
                            super.onCaptureCompleted(session, request, result);
                            mCaptureResult = result;
                        }
                    };

            mCameraCaptureSession.capture(
                    captureStillBuilder.build(), captureCallback, null
            );

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private static class CompareSizeByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private static Boolean contains(int[] modes) {
        if(modes == null) {
            return false;
        }
        for(int i : modes) {
            if(i == android.hardware.camera2.CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_RAW) {
                return true;
            }
        }
        return false;
    }
}
