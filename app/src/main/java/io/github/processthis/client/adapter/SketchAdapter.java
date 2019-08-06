package io.github.processthis.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.processthis.client.R;
import io.github.processthis.client.model.Sketch;
import java.util.ArrayList;

public class SketchAdapter extends RecyclerView.Adapter<SketchAdapter.SketchViewHolder> {

  private ArrayList<Sketch> sketchArrayList;

  public SketchAdapter(ArrayList<Sketch> sketchArrayList) {
    this.sketchArrayList = sketchArrayList;
  }


  @NonNull
  @Override
  public SketchAdapter.SketchViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.sketch_recycler_layout, parent, false);
    return new SketchAdapter.SketchViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SketchAdapter.SketchViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 0;
  }


  class SketchViewHolder extends RecyclerView.ViewHolder {


    public SketchViewHolder(@NonNull View itemView) {
      super(itemView);

    }
  }
}
