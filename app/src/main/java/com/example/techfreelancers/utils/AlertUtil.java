package com.example.techfreelancers.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertUtil {

    public static void showAlert(Context context, String flag, String alertMessage) {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Set the dialog title and message
        builder.setTitle(flag)
                .setMessage(alertMessage);
        // Add a button to the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle the button click (if needed)
                dialog.dismiss(); // Close the dialog
            }
        });
        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
