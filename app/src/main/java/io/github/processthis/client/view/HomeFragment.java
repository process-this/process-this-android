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
 * Ths fragment displays the ten most recently created sketches
 */
public class HomeFragment extends Fragment {

  private RecyclerView recyclerView;
  private SketchViewModel viewModel;

  /**
   * The required fragment constructor
   */
  public HomeFragment() {

  }

  /**
   * The beginning of the android lifecycle for a fragment, this method inflates the associated
   * fragment_home layout and returns a view as  a recycler view
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_home, container, false);
    recyclerView = view.findViewById(R.id.recycler_view_home);
    recyclerView.setLayoutManager(new GridAutoFitLayoutManager(getContext(),
        (int) getContext().getResources().getDimension(R.dimen.home_cell_size)));
    return view;
  }

  /*** This method loads any saved instance data into the recycler view when the main activity is
   * created
   * @param savedInstanceState
   */
  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    viewModel = ViewModelProviders.of(getActivity()).get(SketchViewModel.class);
    getActivity().getLifecycle().addObserver(viewModel);
    viewModel.getRecentSketches().observe(this, (sketches) -> {
      RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), sketches);
      recyclerView.setAdapter(adapter);
    });
  }
}



