package com.example.konrad.androidgameproject.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import static android.util.Base64.decode;

public class Utility {
    public static Bitmap decodeBase64(String input)  {
        byte[] decodedByte = decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    // Check for internet connection
//    private static checkForInternetConnection(Context context): Boolean {
//
//        ConnectivityManager connectivityManager = this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        boolean isDeviceConnectedToInternet = networkInfo != null && networkInfo.isConnected();
//        if (!isDeviceConnectedToInternet) {
//            createAlert(context);
//            Log.i("INTERNET","There is no internet");
//            return false;
//        } else {
//            Log.i("INTERNET","There is internet");
//            return true;
//        }
//    }
//
//    private static void createAlert(final Context context) {
//        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//        alertDialog.setTitle("Network Error");
//        alertDialog.setMessage("Please check internet connection");
//        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
//                DialogInterface dialog, which: Int ->
//                startActivity(new Intent(Settings.ACTION_SETTINGS));
//        });
//        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", {
//                Toast.makeText(context, " You must be connected to the internet", Toast.LENGTH_SHORT).show();
//
//        });
//
//        alertDialog.show();
//    }
}
