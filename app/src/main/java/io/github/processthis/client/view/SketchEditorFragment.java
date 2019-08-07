package io.github.processthis.client.view;

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
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import io.github.processthis.client.R;
import io.github.processthis.client.base64.Base64;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.parsing.Token;
import io.github.processthis.client.parsing.Token.TokenType;
import io.github.processthis.client.parsing.Tokenizer;
import io.github.processthis.client.view.LineNumberedEditText;
import io.github.processthis.client.viewmodel.MainViewModel;
import io.github.processthis.client.viewmodel.SketchViewModel;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The fragment that is responsible for handling code editing, and running code.
 */
public class SketchEditorFragment extends Fragment {

  private SketchView preview;
  private LineNumberedEditText codeEditor;
  private ImageButton actionButton;
  private boolean isEditing;
  private ImageButton saveButton;
  private EditText sketchName;
  private MainViewModel viewModel;



  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);
    // TODO Set up debug console.

    codeEditor = frag.findViewById(R.id.editor);
    actionButton = frag.findViewById(R.id.actionButton);
    saveButton = frag.findViewById(R.id.save);
    sketchName = frag.findViewById(R.id.sketchName);

    actionButton.setOnClickListener(new ActionButtonListener());

    preview = frag.findViewById(R.id.sketchPreview);

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

    viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    saveButton.setOnClickListener((view) -> {

      if(!sketchName.getText().toString().isEmpty()){
        Toast.makeText(getContext(), "You must name your sketch", Toast.LENGTH_LONG);
      }else{
        Sketch sketch = new Sketch();
        sketch.setSketchTitle(sketchName.getText().toString());
        sketch.setCode(getEncodedSource());
        viewModel.setSketch(sketch);
        viewModel.addSketch();
      }
    });
    return frag;
  }


  /**
   * Returns the base 64 encoded source code displayed in the editor.
   * @return {@link String}
   */
  public String getEncodedSource(){
    return Base64.encode(codeEditor.getText().toString());
  }

  /**
   * Sets the source code for the fragment to load into the code editor and code preview.
   * @param encodedSource The base 64 encoded source {@link String} to be loaded.
   */
  public void setSource(String encodedSource){
    String source = Base64.decode(encodedSource);
    codeEditor.setText(source);
  }

  private void runSketch(){
    preview.loadSketch(codeEditor.getText().toString());
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

        result += tabs + lines[i] + ((lines[i].isEmpty() && i == lines.length - 1) ? "" : "\n");

        for (int j = 0; j < lines[i].length(); j++) {
          if (lines[i].charAt(j) == '{') {
            tabLevel++;
          } else if (lines[i].charAt(j) == '}') {
            if (!lines[i].startsWith("}")) {
              tabLevel--;
            }
          }
        }
      }
    }

    return result;
  }

  private SpannableString highlightText(String text) {
    SpannableString result = new SpannableString(text);

    if (!text.isEmpty()) {
      LinkedList<Token> tokens = new Tokenizer(text).Tokenize();

      for (Token token : tokens) {
        if (token.getEnd() >= text.length()) {
          break;
        }
        if (token.getType() == TokenType.NUMBER) {
          result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
              R.color.numberHighlightColor)), token.getStart(), token.getEnd(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (token.getType() == TokenType.COMMENT) {
          result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
              R.color.commentHighlightColor)), token.getStart(), token.getEnd(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (token.getType() == TokenType.STRING) {
          result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
              R.color.stringHighlightColor)), token.getStart(), token.getEnd(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (token.getType() == TokenType.KEYWORD) {
          result.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(),
              R.color.keywordHighlightColor)), token.getStart(), token.getEnd(),
              Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
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
        preview.stopSketch();
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
