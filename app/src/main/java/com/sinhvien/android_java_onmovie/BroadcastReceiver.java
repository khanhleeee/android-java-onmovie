package com.sinhvien.android_java_onmovie;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.sinhvien.android_java_onmovie.authentic.SignInActivity;
import com.sinhvien.android_java_onmovie.authentic.SignUpActivity;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            if(isNetworkAvailable(context)){
                Toast.makeText(context, "Đã kết nối internet!", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(context, "Đã ngắt kết nối internet!", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void setContentView(int disconnect_wifi) {
    }

    private boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null){
            return  false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Network network = connectivityManager.getActiveNetwork();
            if (network==null){
                return  false;
            }
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return  capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return  networkInfo != null && networkInfo.isConnected();
        }
    }
}
