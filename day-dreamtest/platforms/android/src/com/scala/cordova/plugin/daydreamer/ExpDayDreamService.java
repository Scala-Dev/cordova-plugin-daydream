package com.scala.cordova.plugin.daydreamer;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.service.dreams.DreamService;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class ExpDayDreamService extends DreamService {
    private static final String TAG = "ExpDayDreamService";
    public static final String EXP_DAYDREAM_PREF = "exp_daydream_pref";
    public static final String EXP_PLAYER_URL = "exp_player_url";
    public static final String DEFAULT_URL_PLAYER = "https://player.goexp.io";


    @Override
     public void onAttachedToWindow() {
         super.onAttachedToWindow();

        // Exit dream upon user touch
        setInteractive(true);
        // Hide system UI
        setFullscreen(true);
        // Set the dream layout

        Application app = this.getApplication();
        String package_name = app.getPackageName();
        Resources resources = app.getResources();
        int ic = resources.getIdentifier("daydream_layout", "layout", package_name);
        setContentView(ic);

        WebView webView = (WebView) findViewById(resources.getIdentifier("webview","id",package_name));
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);



         SharedPreferences sharedPref = getSharedPreferences(EXP_DAYDREAM_PREF,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPref.edit();
         String playerUrl=null;
         if(!sharedPref.contains(EXP_PLAYER_URL) && !sharedPref.getString(EXP_PLAYER_URL,DEFAULT_URL_PLAYER).isEmpty()){
             editor.putString(EXP_PLAYER_URL, DEFAULT_URL_PLAYER);
             editor.commit();
         }else{
             //read from preference
             playerUrl = sharedPref.getString(EXP_PLAYER_URL,DEFAULT_URL_PLAYER);
         }

         webView.loadUrl(playerUrl);

     }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        Log.d(TAG,"EXP DayDream Started");
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();
        Log.d(TAG,"EXP DayDream Stopped");
    }
}

