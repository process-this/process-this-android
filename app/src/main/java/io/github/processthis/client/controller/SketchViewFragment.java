package io.github.processthis.client.controller;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;

public class SketchViewFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);

    WebView preview = frag.findViewById(R.id.sketch_view);
    preview.setWebChromeClient(new WebChromeClient() {
      @Override
      public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        //Log the message here.
        Log.d("TRACE", consoleMessage.message());

        return super.onConsoleMessage(consoleMessage);
      }
    });
    preview.setWebViewClient(new WebViewClient() {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
      }

    });
    WebSettings settings = preview.getSettings();

    settings.setJavaScriptEnabled(true);// TODO We need to be very very careful here
    settings.setDomStorageEnabled(true);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setSupportZoom(true);
    settings.setDefaultTextEncodingName("utf-8");

    preview.loadUrl("file:///android_asset/sketch_web_view.html");

    return frag;
  }
}
