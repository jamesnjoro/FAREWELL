package com.example.groundzero.farewell;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetCheck {
    Context context;
   private boolean haveWifi = false;
    private boolean havemobile = false;

    public InternetCheck(Context context){

        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connect = (ConnectivityManager)
                  context.getSystemService(Service.CONNECTIVITY_SERVICE);
            NetworkInfo[] infos = connect.getAllNetworkInfo();
            for(NetworkInfo info:infos){
                if(info.getTypeName().equalsIgnoreCase("WIFI")){
                    if(info.isConnected()){
                        haveWifi = true;
                    }
                    if(info.getTypeName().equalsIgnoreCase("mobile")){
                        if(info.isConnected()){
                            havemobile = true;
                        }
                    }
                }

    }
        return havemobile || haveWifi;
    }
}

