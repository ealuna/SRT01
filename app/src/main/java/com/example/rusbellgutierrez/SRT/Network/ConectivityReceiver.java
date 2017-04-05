package com.example.rusbellgutierrez.SRT.Network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.rusbellgutierrez.SRT.Activitys.Activity_Acceso;

/**
 * Created by Russbell on 5/04/2017.
 */

public class ConectivityReceiver extends BroadcastReceiver{

    public static ConnectivityReceiverListener connectivityReceiverListener;

    public ConectivityReceiver() {
        super();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        boolean isConnected=activeNetwork!=null
                && activeNetwork.isConnectedOrConnecting();
        if (connectivityReceiverListener !=null){
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }
    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm=(ConnectivityManager)context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
