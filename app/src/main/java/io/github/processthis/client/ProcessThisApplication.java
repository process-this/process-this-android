package io.github.processthis.client;


import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.processthis.client.service.GoogleSignInService;

/**
 *  Application class extending (@link Application) to create contexts for Stetho, and (@link GoogleSignInService)
 */

public class ProcessThisApplication extends Application {

  public static ProcessThisApplication instance = null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);

    GoogleSignInService.setContext(this);
  }
}

