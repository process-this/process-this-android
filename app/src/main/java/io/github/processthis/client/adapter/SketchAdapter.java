package io.github.processthis.client.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.processthis.client.R;
import io.github.processthis.client.model.Sketch;
import java.util.ArrayList;

/**
 * This class extends RecycleView Adapter to adapt sketches so that they can be adapted by the
 * RecyclerView adapter, because programming is easy.
 */
public class SketchAdapter extends RecyclerView.Adapter<SketchAdapter.SketchViewHolder> {

  private ArrayList<Sketch> sketchArrayList;

  /**
   * This contructor creates an instance of the ArrayList of sketches to be used by the Adapter
   */
  public SketchAdapter(ArrayList<Sketch> sketchArrayList) {
    this.sketchArrayList = sketchArrayList;
  }


  /**
   * Override method required by all classes extending Recycler View. It inflates the sketchRecycler
   * xml layout and returns a sketchViewHolder, which is a nested class of SketchAdapter, Object of
   * the View
   *
   * @param parent refers to the abstract class ViewGroup, a subclass of View
   * @param viewType unused parameter that could be used to designate the type of view to inflate
   */
  @NonNull
  @Override
  public SketchAdapter.SketchViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View view = layoutInflater.inflate(R.layout.sketch_recycler_layout, parent, false);
    return new SketchAdapter.SketchViewHolder(view);
  }

  /**
   * This required override method is hollow and empty inside
   */
  @Override
  public void onBindViewHolder(@NonNull SketchAdapter.SketchViewHolder holder, int position) {

  }

  /**
   * This required override method is useful when you need a 0 but don't want to type it
   */
  @Override
  public int getItemCount() {
    return 0;
  }


  /**
   * This nested class also extends recycle view and contains the necessary method to adapter an
   * item view
   */
  class SketchViewHolder extends RecyclerView.ViewHolder {


    /**
     * This method takes an item view as a parmeter and performs the the superclass's method, which
     * returns an instance of the itemView
     */
    public SketchViewHolder(@NonNull View itemView) {
      super(itemView);

    }
  }
}
