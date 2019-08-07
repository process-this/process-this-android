package io.github.processthis.client.service;

import android.app.Application;
import android.net.Uri;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import io.github.processthis.client.BuildConfig;

/**
 * This class contains the necessary methods for the Google Sign-In Service
 */
public class GoogleSignInService {

  private static Application context;

  private GoogleSignInAccount account;
  private GoogleSignInClient client;

  private GoogleSignInService() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
        .requestIdToken(BuildConfig.CLIENT_ID)
        .build();
    client = GoogleSignIn.getClient(context, options);
  }


  /**This method sets the Context object to the Google Sign-in activity
   * @param context
   */
  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  public GoogleSignInClient getClient() {
    return client;
  }

  public GoogleSignInAccount getAccount() {
    return account;
  }


  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();

  }

}
