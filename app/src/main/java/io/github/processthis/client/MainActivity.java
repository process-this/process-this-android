package io.github.processthis.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import io.github.processthis.client.service.GoogleSignInService;
import io.github.processthis.client.view.LoginActivity;

public class MainActivity extends AppCompatActivity {

  private TextView mTextMessage;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          mTextMessage.setText(R.string.home);
          return true;
        case R.id.navigation_featured:
          mTextMessage.setText(R.string.featured);
          return true;
        case R.id.navigation_add:
          mTextMessage.setText(R.string.add);
          return true;
        case R.id.navigation_notifications:
          mTextMessage.setText(R.string.notifications);
          return true;
        case R.id.navigation_userprofile:
          mTextMessage.setText(R.string.userprofile);
          return true;
      }
      return false;
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mTextMessage = (TextView) findViewById(R.id.message);
    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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