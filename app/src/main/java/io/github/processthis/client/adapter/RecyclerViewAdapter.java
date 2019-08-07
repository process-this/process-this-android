package io.github.processthis.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.github.processthis.client.R;
import io.github.processthis.client.adapter.RecyclerViewAdapter.SketchHolder;
import io.github.processthis.client.model.Sketch;
import java.util.List;

/**
 * This class contains the necessary methods to adept a recycler view to be inflated by a fragment.
 * It extends Recycleriew.Adapter cass wrapper around a SketchHolder Object
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<SketchHolder> {

  private Context context;
  private List<Sketch> sketches;


  /**
   * This constructor is used to create an instance of the list of sketches to be inflated by the
   * view and the context object
   */
  public RecyclerViewAdapter(Context context,
      List<Sketch> sketches) {
    this.context = context;
    this.sketches = sketches;
  }

  /**
   * Override method required by all classes extending Recycler View. It inflates the cardView xml
   * layout and returns a sketchHolder object of the View
   *
   * @param parent refers to the abstract class ViewGroup, a subclass of View
   * @param viewType unused parameter that could be used to designate the type of view to inflate
   */
  @NonNull
  @Override
  public SketchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view;
    LayoutInflater Inflater = LayoutInflater.from(context);
    view = Inflater.inflate(R.layout.cardview, parent, false);
    return new SketchHolder(view);
  }

  /**
   * this method tells the holder to display the sketch at position x, required override
   *
   * @param holder the sketcholder object returned by on createViewHolder
   * @param position the desiganted position of the sketch to be inflated
   */
  @Override
  public void onBindViewHolder(@NonNull SketchHolder holder, int position) {
    holder.bind(sketches.get(position));
  }

  /**
   * Required override returns the size of the list of sketches held by the recycler view
   */
  @Override
  public int getItemCount() {
    return sketches.size();
  }

  /**
   * This nested class defines which sketches and thumbnail layouts will be passed to the recycler
   * view
   */
  class SketchHolder extends RecyclerView.ViewHolder {

    TextView sketchName;
    ImageView sketchThumbnail;

    private SketchHolder(View itemView) {
      super(itemView);
      sketchName = itemView.findViewById(R.id.sketch_title);
      sketchThumbnail = itemView.findViewById(R.id.sketch_image);
    }

    private void bind(Sketch sketch) {
      sketchName.setText(sketch.getSketchTitle());
//     sketchThumbnail.setImageDrawable(sketch.getThumbnail());
      sketchThumbnail.setImageDrawable(context.getDrawable(R.drawable.sketch2));
    }

  }
}
