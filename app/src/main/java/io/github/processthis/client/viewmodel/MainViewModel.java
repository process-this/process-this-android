package io.github.processthis.client.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.common.data.DataBufferObserver.Observable;
import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.model.UserProfile;
import io.github.processthis.client.service.GoogleSignInService;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class MainViewModel extends AndroidViewModel
    implements Observable, LifecycleObserver {

  private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
  private String searchTerm;
  private MutableLiveData<UserProfile> userProfile;
  private MutableLiveData<List<UserProfile>> userProfileSearchResults;
  private CompositeDisposable pending = new CompositeDisposable();
  private String oauthHeader;

  public MainViewModel(@NonNull Application application) {
    super(application);
    oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT,
        GoogleSignInService.getInstance().getAccount().getIdToken());
  }

  @Override
  public void addObserver(DataBufferObserver dataBufferObserver) {

  }

  @Override
  public void removeObserver(DataBufferObserver dataBufferObserver) {

  }
}
