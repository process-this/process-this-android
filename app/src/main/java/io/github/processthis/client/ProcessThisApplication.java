package io.github.processthis.client;


import android.app.Application;
import com.facebook.stetho.Stetho;

public class ProcessThisApplication extends Application {

  public static ProcessThisApplication instance = null;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Stetho.initializeWithDefaults(this);

  }

}


