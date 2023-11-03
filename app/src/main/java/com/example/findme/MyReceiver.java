package com.example.findme;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.findme.ui.dashboard.DashboardFragment;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String messageBody,phoneNumber;
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
        {
            Bundle bundle =intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    messageBody = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context,
                                    "Message : "+messageBody+"Reçu de la part de;"+ phoneNumber,
                                    Toast.LENGTH_LONG )
                            .show();
                    System.out.println("message recieved from : "+phoneNumber+" with body: "+messageBody);
                    if (messageBody.contains("findme")){
                        System.out.println("message body contains findme");

                        Toast.makeText(context,
                                        phoneNumber+"request your location",
                                        Toast.LENGTH_LONG )
                                .show();
                        Intent intent1 = new Intent(context, LocationService.class);
                        // passing parameters
                        intent1.putExtra("phone", phoneNumber);
                        context.startService(intent1);
                    }
                    else if(messageBody.contains("position")){
                        System.out.println("message body contains position");

                        String[] splitedText = messageBody.split("#");
                        String Longitude = splitedText[1];
                        String Latitude = splitedText[2];

                        NotificationCompat.Builder mynotif = new NotificationCompat.Builder(
                                        context.getApplicationContext(),
                                        "myapplication_channel"
                        );
                        mynotif.setContentTitle("titre içi!");
                        mynotif.setContentText("votre message.");
                        mynotif.setSmallIcon(android.R.drawable.ic_dialog_map);
                        mynotif.setAutoCancel(true);
                        mynotif.setVibrate(new long[]{ 500,1000,200,2000});
                        Intent i=new Intent(context.getApplicationContext(),
                                MainActivity.class);
                        PendingIntent pi= PendingIntent.getActivity(
                                context.getApplicationContext(),
                                0,
                                i,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        mynotif.setContentIntent(pi);

                    }

                }
            }
        }
    }
}