package io.github.processthis.client.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import io.github.processthis.client.R;
import io.github.processthis.client.adapter.RecyclerViewAdapter;
import io.github.processthis.client.viewmodel.SketchViewModel;

/**
 * This Fragment is where the user will interact with his/her/their profile
 */
public class UserProfileFragment extends Fragment {

  private RecyclerView recyclerView;
  private SketchViewModel viewModel;

  public UserProfileFragment() {

  }

  /**
   * At the beginning of the fragment lifecycle this method inflates the fragment_user_profile
   * layout as a recycler view and returns that as the view
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

    final ImageView profileImage = view.findViewById(R.id.user_image);
    recyclerView = view.findViewById(R.id.recycler_view_user);
    recyclerView.setLayoutManager(new GridAutoFitLayoutManager(getContext(),
        (int) getContext().getResources().getDimension(R.dimen.featured_cell_size)));

    return view;
  }

  /**
   * At lifecycle start ths methord loads any saved instance data into the recycler view
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
  } //TODO change so that the grid displays sketches by this particular user
}
