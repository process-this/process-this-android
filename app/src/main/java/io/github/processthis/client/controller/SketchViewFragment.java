package io.github.processthis.client.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;

public class SketchViewFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View frag = inflater.inflate(R.layout.ide_fragment, container, false);

    WebView preview = frag.findViewById(R.id.sketch_view);
    preview.loadUrl("file:///android_asset/sketch_web_view.html");

    return frag;
  }
}
