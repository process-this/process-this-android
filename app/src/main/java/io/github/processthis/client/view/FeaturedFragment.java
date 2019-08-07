package io.github.processthis.client.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.github.processthis.client.R;
import io.github.processthis.client.adapter.RecyclerViewAdapter;
import io.github.processthis.client.viewmodel.SketchViewModel;

/**
 * This fragment displays the featured (most-liked) sketches
 */
public class FeaturedFragment extends Fragment {

  private RecyclerView recyclerView;
  private SketchViewModel viewModel;

  /**
   * This is a required public constructor for a fragment
   */
  public FeaturedFragment() {

  }

  /**
   * The beginning of the android lifecycle for a fragment, this method inflates the associated
   * recyclerView fragment_featured layout layout and returns a view
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_featured, container, false);
    recyclerView = view.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new GridAutoFitLayoutManager(getContext(),
        (int) getContext().getResources().getDimension(R.dimen.featured_cell_size)));
    return view;
  }

  /**
   * This method loads any saved instance data into the recycler view when the main activity is
   * started
   */
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(getActivity()).get(SketchViewModel.class);
    getActivity().getLifecycle().addObserver(viewModel);
    viewModel.getFeaturedFeed(10).observe(this, (sketches) -> {
      RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), sketches);
      recyclerView.setAdapter(adapter);
    });
  }
}
