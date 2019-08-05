package io.github.processthis.client.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;
import io.github.processthis.client.parsing.Token;
import io.github.processthis.client.parsing.Token.TokenType;
import io.github.processthis.client.parsing.Tokenizer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SketchViewFragment extends Fragment {

  private WebView preview;
  private EditText codeEditor;
  private boolean isEditing;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);

    String src = "alert('Hello!');\n"
        + "function setup(){\n"
        + "alert('setup()');\n"
        + "print('setup');\n"
        + "createCanvas(640, 480);\n"
        + "fill(255, 0, 0);\n"
        + "ellipse(40, 40, 80, 80);\n"
        + "}\n"
        + "\n"
        + "function draw(){\n"
        + "alert('Draw');\n"
        + "print('draw');\n"
        + "if (mouseIsPressed){\n"
        + "print(mouseX);\n"
        + "fill(0);\n"
        + "}\n"
        + "else{\n"
        + "fill(255);\n"
        + "}\n"
        + "ellipse(mouseX, mouseY, 80, 80);\n"
        + "}\n"
        + "\n";

    codeEditor = frag.findViewById(R.id.editor);
    codeEditor.setText(highlightText(formatString(src)));
    /*preview = frag.findViewById(R.id.sketch_view);
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
    settings.setDefaultTextEncodingName("utf-8");*/

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("file:///android_asset/sketch.js"));
      writer.write(src);
      writer.close();
    } catch (IOException e){
      //Do nothing
    }

    //preview.loadUrl("file:///android_asset/sketch_web_view.html");

    codeEditor.addTextChangedListener(new DelayedTextWatcher(codeEditor) {

      @Override
      public void textChanged() {
        if (!isEditing) {
          isEditing = true;
          String autoIndented = formatString(codeEditor.getText().toString());
          SpannableString highlightedText = highlightText(autoIndented);
          codeEditor.setText(highlightedText, BufferType.SPANNABLE);
          codeEditor.setSelection(cursorPosition);
        } else {
          isEditing = false;
        }
      }
    });

    return frag;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  private String formatString(String text){
    int tabLevel = 0;
    String[] lines = text.split("\n\t*", -1);
    String result = "";

    if (lines.length != 0) {
      for (int i = 0; i < lines.length; i++) {

        if (lines[i].startsWith("}")){
          tabLevel--;
        }

        String tabs = "";

        for (int t = 0; t < tabLevel * 2; t++) {
          tabs += "\t";
        }

        result += tabs + lines[i] + "\n";

        for (int j = 0; j < lines[i].length(); j++) {
          if (lines[i].charAt(j) == '{') {
            tabLevel++;
          } else if (lines[i].charAt(j) == '}') {
            if (!lines[i].startsWith("}"))
            tabLevel--;
          }
        }
      }
    }

    return result;
  }

  private SpannableString highlightText(String text) {
    SpannableString result = new SpannableString(text);

    LinkedList<Token> tokens = new Tokenizer(text).Tokenize();

    for (Token token : tokens){
      if (token.getEnd() >= text.length()){
        break;
      }
      if (token.getType() == TokenType.NUMBER){
        result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
            R.color.numberHighlightColor)), token.getStart(), token.getEnd(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      else if (token.getType() == TokenType.COMMENT){
        result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
            R.color.commentHighlightColor)), token.getStart(), token.getEnd(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      else if (token.getType() == TokenType.STRING){
        result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
            R.color.stringHighlightColor)), token.getStart(), token.getEnd(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
      else if (token.getType() == TokenType.KEYWORD){
        result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
            R.color.keywordHighlightColor)), token.getStart(), token.getEnd(),
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    return result;
  }

  private abstract class DelayedTextWatcher implements TextWatcher {

    private final EditText watching;
    protected int cursorPosition;

    public DelayedTextWatcher(EditText tView) {
      watching = tView;
    }

    private Timer timer = new Timer();
    private final int DELAY = 10;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      cursorPosition = watching.getSelectionStart();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
      //DO NOTHING!
    }


    @Override
    public void afterTextChanged(final Editable s) {
      //isEditing = true;
      //watching.setText(formatString(watching.getText().toString(), '{', '}'));
      //isEditing = false;
      timer.cancel();
      timer = new Timer();

      timer.schedule(
          new TimerTask() {
            @Override
            public void run() {
              getActivity().runOnUiThread(() -> textChanged());
            }
          },
          DELAY
      );
    }

    public abstract void textChanged();
  }
}
