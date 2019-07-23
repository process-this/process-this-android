package io.github.processthis.client;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.processthis.client.service.GoogleSignInService;

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
