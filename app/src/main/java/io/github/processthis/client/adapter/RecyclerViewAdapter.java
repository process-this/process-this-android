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

public class RecyclerViewAdapter extends RecyclerView.Adapter<SketchHolder> {

  private Context context;
  private List<Sketch> sketches;


  public RecyclerViewAdapter(Context context,
      List<Sketch> sketches) {
    this.context = context;
    this.sketches = sketches;
  }

  @NonNull
  @Override
  public SketchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view;
    LayoutInflater Inflater = LayoutInflater.from(context);
    view = Inflater.inflate(R.layout.cardview, parent, false);
    return new SketchHolder(view);
  }

  // this tells the holder to display the sketch at position x
  @Override
  public void onBindViewHolder(@NonNull SketchHolder holder, int position) {
    holder.bind(sketches.get(position));
  }

  @Override
  public int getItemCount() {
    return sketches.size();
  }

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
