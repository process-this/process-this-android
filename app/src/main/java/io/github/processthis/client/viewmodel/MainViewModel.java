package io.github.processthis.client.viewmodel;

import android.app.Application;
import android.database.Observable;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.service.GoogleSignInService;

public class MainViewModel extends AndroidViewModel  {

  private String oauthHeader;

  public MainViewModel(@NonNull Application application) {
    super(application);
    oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT,
        GoogleSignInService.getInstance().getAccount().getIdToken());
  }
}
