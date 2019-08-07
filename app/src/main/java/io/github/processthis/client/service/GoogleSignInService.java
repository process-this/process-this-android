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


  /**
   * This method sets the Context object to the Google Sign-in activity
   */
  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  /**
   * This method gets the client to be used in Sign-In
   */
  public GoogleSignInClient getClient() {
    return client;
  }

  /**
   * Gets the google account to bu used in Sign-In
   */
  public GoogleSignInAccount getAccount() {

    return account;
  }


  /**
   * Sets the google account to bu used in Sign-In
   */
  public void setAccount(GoogleSignInAccount account) {
    this.account = account;
  }

  /**
   * Gets the instance of Goggle Sign-In service to bu used in Sign-In
   */
  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();

  }

}
