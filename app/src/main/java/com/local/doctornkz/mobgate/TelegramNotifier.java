package com.local.doctornkz.mobgate;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class TelegramNotifier extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        String text = params[0];
        String chatId = params[1];
        String apiToken = params[2];
        String urlString = params[3] + "bot%s/sendMessage?chat_id=%s&text=%s";

        try {
            urlString = String.format(urlString, apiToken, chatId, text);
            Log.d(Parameters.TAG, "SMSGate message :" + urlString);
            URL url = new URL(urlString);

            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                Log.d(Parameters.TAG, inputLine);
            in.close();
        } catch (Exception e) {
            Log.d(Parameters.TAG, e.toString());
            Log.d(Parameters.TAG, "SMSGate : Can't send message");
        }
        return Boolean.TRUE;
    }
}