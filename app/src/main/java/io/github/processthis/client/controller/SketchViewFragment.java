package io.github.processthis.client.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView.BufferType;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;
import io.github.processthis.client.parsing.Token;
import io.github.processthis.client.parsing.Token.TokenType;
import io.github.processthis.client.parsing.Tokenizer;
import io.github.processthis.client.view.LineNumberedEditText;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SketchViewFragment extends Fragment {

  private WebView preview;
  private LineNumberedEditText codeEditor;
  private ImageButton actionButton;
  private boolean isEditing;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);
    // TODO Set up debug console.
    String src = "alert('Hello!');\n"
        + "function setup(){\n"
        + "alert('setup()');\n"
        + "createCanvas(640, 480);\n"
        + "fill(255, 0, 0);\n"
        + "ellipse(40, 40, 80, 80);\n"
        + "}\n"
        + "\n"
        + "function draw(){\n"
        + "print('draw');\n"
        + "if (mouseIsPressed){\n"
        + "fill(0);\n"
        + "}\n"
        + "else{\n"
        + "fill(255);\n"
        + "}\n"
        + "ellipse(mouseX, mouseY, 80, 80);\n"
        + "}";

    codeEditor = frag.findViewById(R.id.editor);
    actionButton = frag.findViewById(R.id.actionButton);

    actionButton.setOnClickListener(new ActionButtonListener());

    codeEditor.setText(highlightText(formatString(src)));
    preview = frag.findViewById(R.id.sketchPreview);

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

  public String getEncodedSource(){
    return null;
  }

  public void setSource(String encodedSource){

  }

  private void runSketch(){
    String htmlFormatter = "<!DOCTYPE html>\n"
        + "<html lang=\"en\">\n"
        + "<head>\n"
        + "  <meta charset=\"UTF-8\">\n"
        + "  <script src=\"file:///android_asset/p5.js\"></script>\n"
        + "  <title>Title</title>\n"
        + "</head>\n"
        + "  <body>\n"
        + "  <script> %s </script>\n"
        + "  </body>\n"
        + "</html>";
    String source = String.format(htmlFormatter, codeEditor.getText().toString());

    preview.loadDataWithBaseURL("file:///android_asset/p5.js", source, "text/html", "UTF-8", null);
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

  private class ActionButtonListener implements OnClickListener{
    private boolean editorOpen = true;

    @Override
    public void onClick(View view) {
      editorOpen = !editorOpen;

      if (editorOpen){
        preview.setVisibility(View.GONE);
        codeEditor.setVisibility(View.VISIBLE);
        actionButton.setImageDrawable(ContextCompat.getDrawable(getContext(),
            R.drawable.start_script_icon));
      } else {
        preview.setVisibility(View.VISIBLE);
        codeEditor.setVisibility(View.GONE);
        actionButton.setImageDrawable(ContextCompat.getDrawable(getContext(),
            R.drawable.stop_script_icon));
        runSketch();
      }
    }
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
