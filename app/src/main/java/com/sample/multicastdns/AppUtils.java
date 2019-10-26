package com.sample.multicastdns;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by AKASH on 26/10/19.
 */
public class AppUtils {
    /**
     * @param mContext
     * @return
     */
    public static NsdManager initializeNSDManger(Context mContext) {
        return (NsdManager) mContext.getSystemService(Context.NSD_SERVICE);
    }

    /**
     * Method to show the Toast message.
     *
     * @param message String which indicates the message to be displayed as
     *                Toast.
     */

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
