package com.kernal.smartvision.utils;

import android.hardware.Camera;

import com.kernal.smartvisionocr.utils.KernalLSCXMLInformation;

/**
 * Created by user on 2018/5/18.
 */

public class VINRecogParameter {
    public byte[] data;
    public int rotation;
    public boolean islandscape;
    public boolean isFirstProgram=true;
    public boolean isTakePic;
    public KernalLSCXMLInformation wlci;
    public int selectedTemplateTypePosition;
    public Camera.Size size;
}
