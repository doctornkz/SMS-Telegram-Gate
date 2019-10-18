package com.local.doctornkz.mobgate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsMessage;
import android.util.Log;
import android.provider.Telephony;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;
    Parameters parameters;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(parameters.TAG, "SMSGate: onReceive");

        try{
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String messageBody = smsMessage.getMessageBody();
                    new TelegramNotifier().execute("Mobile phone connected", parameters.TELEGRAMID, parameters.BOTID, parameters.API);
                    Log.d(parameters.TAG, " Message:" + messageBody);

                }
            }

        }catch(Exception e){
                        Log.d("Exception caught", e.toString());
                    }


    }
}