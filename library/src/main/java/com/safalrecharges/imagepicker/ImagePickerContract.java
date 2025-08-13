package com.safalrecharges.imagepicker;

import android.content.Intent;

import java.io.File;

public interface ImagePickerContract {
    @SuppressWarnings("UnusedReturnValue")
    ImagePicker setWithImageCrop(int aspectRatioX, int aspectRatioY);
    ImagePicker setWithImageCrop();
    // todo add this in v1.1

//    ImagePicker setWithIntentPickerTitle(String title);
//    ImagePicker setWithIntentPickerTitle(@StringRes int title);


    void choosePicture(boolean includeCamera);

    void openCamera();

    File getImageFile();

    void handlePermission(int requestCode, int[] grantResults);

    void handleActivityResult(int resultCode, int requestCode, Intent data);
}
