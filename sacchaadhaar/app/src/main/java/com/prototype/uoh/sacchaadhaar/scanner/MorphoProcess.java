package com.prototype.uoh.sacchaadhaar.scanner;

/**
 * Created by kc on 30/3/17.
 */
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.morpho.android.usb.USBManager;
import com.morpho.morphosmart.sdk.ErrorCodes;
import com.morpho.morphosmart.sdk.MorphoDevice;
import com.morpho.morphosmart.sdk.MorphoImage;

import java.io.FileOutputStream;
import java.security.Permission;
import java.util.Observable;
import java.util.Observer;

import static com.morpho.morphosmart.sdk.CompressionAlgorithm.MORPHO_NO_COMPRESS;
import static com.morpho.morphosmart.sdk.DetectionMode.MORPHO_ENROLL_DETECT_MODE;
import static com.morpho.morphosmart.sdk.LatentDetection.LATENT_DETECT_DISABLE;

public class MorphoProcess{
    private static final int MORPHO_WAKEUP_LED_ON = 64;
    private static final int MORPHO_ENROLL_DETECT_MODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;

    public static void getPermission(Activity activity){
        USBManager.getInstance().initialize(activity,"com.prototype.uoh.sacchaadhaar.USD_ACTION");
    }
    public static boolean checkPermission(){
        if(USBManager.getInstance().isDevicesHasPermission()!=true)
            return false;
        else
            return true;
    }
    public static String enumerate(MorphoDevice morphoDevice,Activity activity){
            Integer nUsbDevices = new Integer(0);
            String  sensorName="";
            int ret = morphoDevice.initUsbDevicesNameEnum(nUsbDevices);
            if(ret== ErrorCodes.MORPHO_OK){
                if(nUsbDevices>0){
                    sensorName = morphoDevice.getUsbDeviceName(0);
                    Toast.makeText(activity,sensorName,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(activity, "nbUsbDevice<=0", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(activity, "! MORPHO_OK", Toast.LENGTH_SHORT).show();
            }
            return sensorName;

    }
    public static int connect(Activity activity,MorphoDevice morphoDevice,String sensorName){
        int ret=morphoDevice.openUsbDevice(sensorName,0);
        return ret;
    }
    public static void getImage(Activity activity,MorphoDevice morphoDevice){
        MorphoImage morphoImage = new MorphoImage();
        int ret = morphoDeviceGetImage((Observer)activity,morphoImage,morphoDevice);
        if(ret==ErrorCodes.MORPHO_OK){
            String message = "";
            try{
                String fileName = "sdcard/Image" + morphoImage.getCompressionAlgorithm().getExtension();

                int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                    FileOutputStream fos = new FileOutputStream(fileName);
                    fos.write(morphoImage.getImage());
                    message = "Image RAW successfully exported in file [" + fileName + "]";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    fos.close();
                }
                else{
                    Toast.makeText(activity, "Write External Storage Permission not Granted", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }catch (Exception e){
                Toast.makeText(activity,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        morphoDevice.closeDevice();
    }
    private static int morphoDeviceGetImage(Observer observer,MorphoImage morphoImage,MorphoDevice morphoDevice){
        return morphoDevice.getImage(30, 0, MORPHO_NO_COMPRESS, 0, MORPHO_ENROLL_DETECT_MODE, LATENT_DETECT_DISABLE,morphoImage, 64,observer);
    }



}
