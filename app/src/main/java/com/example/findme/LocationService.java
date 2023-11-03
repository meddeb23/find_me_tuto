package com.example.findme;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.example.findme.ui.dashboard.DashboardFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(MainActivity.permission){
            System.out.println("Getting location");
            FusedLocationProviderClient mClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
            mClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.

                            if (location != null) {
                                System.out.println("location found");

                                // Logic to handle location object
                                String phoneNumber = intent.getExtras().getString("phone");

                                System.out.println("Sending SMS to : "+phoneNumber);
                                String smsText = "position #"+ location.getLongitude()+"#"+location.getLatitude();
                                if(MainActivity.permission){
                                    SmsManager manager = SmsManager.getDefault();
                                    manager.sendTextMessage(phoneNumber, null, smsText,null, null);
                                    System.out.println("sending sms to " + phoneNumber);
                                }
                            }
                        }
                    });
        }
        return super.onStartCommand(intent,flags, startId);
    }
}