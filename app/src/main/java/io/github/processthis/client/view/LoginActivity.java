package io.github.processthis.client.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import io.github.processthis.client.R;
import io.github.processthis.client.controller.MainActivity;
import io.github.processthis.client.service.GoogleSignInService;

/**
 * This class is part of google sign in and is the activity that hosts the sign-in View
 */
public class LoginActivity extends AppCompatActivity {

  private static final int REQUEST_CODE = 1000;

  /**This methid loads any saved instances of login data
   * @param savedInstanceState
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    findViewById(R.id.sign_in).setOnClickListener((view) -> signIn());
  }

  /**
   * This method calls the Goggle-Sign in service upon start of the lifecycle
   */
  @Override
  protected void onStart() {
    super.onStart();
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    if (account != null) {
      GoogleSignInService.getInstance().setAccount(account);
      switchToMain();
    }
  }

  /**This activity reacts to a successful or unsuccesful sign-in process
   * @param requestCode
   * @param resultCode
   * @param data
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switchToMain();// TODO Comment this out and add the other stuff back in
    /*if (requestCode == REQUEST_CODE) {
      try {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        GoogleSignInService.getInstance().setAccount(account);
        switchToMain();
      } catch (ApiException e) {
        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
      }
    }*/
  }


  /**
   * this method compares request codes and determines sign-in efficacy
   */
  private void signIn() {
    Intent intent = GoogleSignInService.getInstance().getClient().getSignInIntent();
    startActivityForResult(intent, REQUEST_CODE);
  }

  /**
   * Id sign-in was succesful this activity calls the main activity, starting the app in earnest
   */
  private void switchToMain() {
    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

}
