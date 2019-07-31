package io.github.processthis.client.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;

public class SketchViewFragment extends Fragment {
  private WebView preview;
  private EditText codeEditor;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);

    codeEditor = frag.findViewById(R.id.editor);
    preview = frag.findViewById(R.id.sketch_view);
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

    SpannableString spannable = new SpannableString("Hello SpannableString Example.");
    ForegroundColorSpan backgroundColorSpan = new ForegroundColorSpan(Color.GREEN);

    spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 10, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

    codeEditor.setText(spannable, BufferType.SPANNABLE);

    return frag;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  private SpannableString highLightText(String text){
    SpannableString result = new SpannableString(text);

    return result;
  }
}
