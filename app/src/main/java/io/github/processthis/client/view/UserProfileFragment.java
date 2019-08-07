package io.github.processthis.client.view;

import android.net.Uri;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import io.github.processthis.client.R;
import io.github.processthis.client.adapter.RecyclerViewAdapter;
import io.github.processthis.client.viewmodel.SketchViewModel;

public class UserProfileFragment extends Fragment {

  private RecyclerView recyclerView;
  private SketchViewModel viewModel;

  private GoogleSignInAccount acct;


  private ImageView profileImage;


  public UserProfileFragment() {

  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
    acct= GoogleSignIn.getLastSignedInAccount(getActivity());
    if (acct != null) {
      String personName = acct.getDisplayName();
      String personFamilyName = acct.getFamilyName();
      String personEmail = acct.getEmail();
      String personId = acct.getId();
      Uri personPhoto = acct.getPhotoUrl();
    }

     profileImage = (ImageView) view.findViewById(R.id.user_image);
    profileImage.setImageDrawable(getContext().getDrawable(R.drawable.sketch1));
      recyclerView = view.findViewById(R.id.recycler_view_user);
    recyclerView.setLayoutManager(new GridAutoFitLayoutManager(getContext(),
        (int) getContext().getResources().getDimension(R.dimen.featured_cell_size)));

    return view;
  }

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
