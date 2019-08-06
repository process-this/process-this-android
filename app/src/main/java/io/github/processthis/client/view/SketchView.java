package io.github.processthis.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import io.github.processthis.client.base64.Base64;

/**
 * A view object responsible for showing and running a sketch
 */
public class SketchView extends WebView {
  private static final String HTML_FORMATTER = "<!DOCTYPE html>\n"
      + "<html lang=\"en\">\n"
      + "<head>\n"
      + "<meta charset=\"UTF-8\">\n"
      + "<script src=\"file:///android_asset/p5.js\"></script>\n"
      + "<title>Title</title>\n"
      + "</head>\n"
      + "<body>\n"
      + "<script> %s </script>\n"
      + "</body>\n"
      + "</html>";

  /**
   * Constructor that takes in only a Context.
   * @param context The current application context.
   */
  public SketchView(Context context){
    super(context);
    setupSettings();
  }

  /**
   * Constructor that takes in a context, and an AttributeSet.
   * @param context The current application context.
   * @param attrs The AttributeSet to be used.
   */
  public SketchView(Context context, AttributeSet attrs){
    super(context, attrs);
    setupSettings();
  }

  /**
   * Constructor that takes in a context, an AttributeSet, and a integer.
   * @param context The current application context.
   * @param attrs The AttributeSet to be used.
   * @param defStyleAttrs an attribute in the current theme that contains a reference to a style resource that supplies default values for the view. Can be 0 to not look for defaults.
   */
  public SketchView(Context context, AttributeSet attrs, int defStyleAttrs){
    super(context, attrs, defStyleAttrs);
    setupSettings();
  }

  private void setupSettings(){
    WebSettings settings = getSettings();
    settings.setJavaScriptEnabled(true);//TODO be careful here!
    settings.setDomStorageEnabled(true);
    settings.setLoadWithOverviewMode(true);
    settings.setUseWideViewPort(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setSupportZoom(true);
    settings.setDefaultTextEncodingName("utf-8");
  }

  /**
   * Loads and runs a sketch given raw source code
   * @param sourceCode The source code {@link String} to be run.
   */
  public void loadSketch(String sourceCode){
    String source = String.format(HTML_FORMATTER, sourceCode);
    loadDataWithBaseURL("file:///android_asset/p5.js", source, "text/html", "UTF-8", null);
  }

  /**
   * Loads and runs a sketch given the base 64 encoded version of the source code
   * @param encodedSourceCode The base64 encoded version of the source code
   */
  public void loadSketchBase64(String encodedSourceCode){
    String sourceCode = Base64.decode(encodedSourceCode);
    loadSketch(sourceCode);
  }

  /**
   * Stops the sketch from playing and loads a default page
   */
  public void stopSketch(){
    loadUrl("about:blank");
  }
}
