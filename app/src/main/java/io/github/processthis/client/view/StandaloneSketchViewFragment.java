package io.github.processthis.client.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.github.processthis.client.R;
import io.github.processthis.client.model.Sketch;

public class StandaloneSketchViewFragment extends Fragment {

  SketchView view;
  Sketch sketch;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View frag = inflater.inflate(R.layout.stand_alone_sketch_view, container, false);
    view = frag.findViewById(R.id.mainView);

    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    view.loadSketch(sketch.getCode());
  }
}
