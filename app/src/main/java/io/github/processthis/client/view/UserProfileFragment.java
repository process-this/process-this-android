/*  Copyright [2019] [Asher Bearce, Jeffery Franken, Matthew Jones, Jennifer Nevares-Diaz]
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
   limitations under the License.
*/

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

/**
 * This Fragment is where the user will interact with his/her/their profile
 */
public class UserProfileFragment extends Fragment {

  private RecyclerView recyclerView;
  private SketchViewModel viewModel;

  private GoogleSignInAccount acct;


  private ImageView profileImage;


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
    acct= GoogleSignIn.getLastSignedInAccount(getActivity());
    if (acct != null) {
      String personName = acct.getDisplayName();
      String personFamilyName = acct.getFamilyName();
      String personEmail = acct.getEmail();
      String personId = acct.getId();
      Uri personPhoto = acct.getPhotoUrl();
    }

     profileImage = (ImageView) view.findViewById(R.id.user_image);
    profileImage.setImageDrawable(getContext().getDrawable(R.drawable.user));
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
