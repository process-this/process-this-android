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

package io.github.processthis.client.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.github.processthis.client.R;
import io.github.processthis.client.service.GoogleSignInService;
import io.github.processthis.client.view.LoginActivity;


/**
 * This class is a required class for any Android project. It includes required methods that define the views and fragments hosted by the Main Activity
 */
public class MainActivity extends AppCompatActivity {

//  private SketchEditorFragment sketchView;

  private static int SPLASH_SCREEN_TIME_OUT = 2000;


  /**
   * The initial method and step in the Android Lifecycle. It consumes any previously saved data and defines te host functions for the main activity
   * @param savedInstanceState any saved instances passed ot of a previous lifecycle
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    NavigationUI.setupWithNavController(navigation, navController);

    FloatingActionButton fab = findViewById(R.id.fab);

    Navigation.setViewNavController(fab, navController);

    fab.setOnClickListener(new OnClickListener() {
      /** This method defines whoch vew will be navigated to upon click of the floating action button
       * @param view
       */
      @Override
      public void onClick(View view) {
        navController.navigate(R.id.navigation_sketch_view);
      }
    });


  }

  private boolean loadFragment(Fragment fragment) {
    if (fragment != null) {

      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.nav_host_fragment, fragment)
          .commit();

      return true;
    }
    return false;
  }

  /**
   * Creates and inflates the options menu.
   *
   * @param menu the menu
   * @return true
   */
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true;
  }


  /**
   * Currently contains the only option in the options menu, to sign out.
   *
   * @param item item selected from the {@link Menu}
   * @return {@link Boolean} if the {@link android.content.ClipData.Item} was selected
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.sign_out:
        signOut();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }
  //TODO create a reset option in the options menu!

  /**
   * Initialized a GoogleSignInService and sets the account to null, i.e. signing out.
   */
  private void signOut() {
    GoogleSignInService service = GoogleSignInService.getInstance();
    service.getClient().signOut().addOnCompleteListener((task) -> {
      service.setAccount(null);
      Intent intent = new Intent(this, LoginActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    });
  }

}
