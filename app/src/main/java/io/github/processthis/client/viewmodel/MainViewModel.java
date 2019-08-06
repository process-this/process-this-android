package io.github.processthis.client.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.common.data.DataBufferObserver;
import com.google.android.gms.common.data.DataBufferObserver.Observable;
import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.model.Like;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.model.UserProfile;
import io.github.processthis.client.service.GoogleSignInService;
import io.github.processthis.client.service.ProcessThisService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MainViewModel extends AndroidViewModel
    implements Observable, LifecycleObserver {


  private MutableLiveData<UserProfile> userProfileResult;
  private MutableLiveData<List<UserProfile>> userProfileListResults;
  private MutableLiveData<Sketch> sketchResult;
  private MutableLiveData<List<Sketch>> sketchListResult;
  private MutableLiveData<List<Like>> likeListResult;
  private CompositeDisposable pending = new CompositeDisposable();
  private String oauthHeader;
  private String userId;
  private String sketchId;
  private String likeId;
  private Sketch sketch;

  public MainViewModel(@NonNull Application application) {
    super(application);
    oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT,
        GoogleSignInService.getInstance().getAccount().getIdToken());
  }

  public Sketch getSketch() {
    return sketch;
  }

  public void setSketch(Sketch sketch) {
    this.sketch = sketch;
  }

  public void findASketch() {
    pending.add(
        ProcessThisService.getInstance().getSingleSketch(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketch) -> sketchResult.setValue(sketch))
    );
  }

  public void findUserSketchList() {
    pending.add(
        ProcessThisService.getInstance().getUserProfileSketches(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketches) -> sketchListResult.setValue(sketches))
    );
  }

  public void findUser() {
    pending.add(
        ProcessThisService.getInstance().getSingleUserProfile(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  public void findUserLikes() {
    pending.add(
        ProcessThisService.getInstance().getUserProfilesLikes(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((likes) -> likeListResult.setValue(likes))
    );
  }

  public void findSketchLikes() {
    pending.add(
        ProcessThisService.getInstance().getSketchLikes(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((likes) -> likeListResult.setValue(likes))
    );
  }

  public void addUserProfile() {
    pending.add(
        ProcessThisService.getInstance().postUserProfile(oauthHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  public void addSketch() {
    pending.add(
        ProcessThisService.getInstance().postSketch(oauthHeader, userId, sketch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketch) -> sketchResult.setValue(sketch))
    );
  }

  public void addLike() {
    pending.add(
        ProcessThisService.getInstance().putLike(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  public void unlike() {
    pending.add(
        ProcessThisService.getInstance().deleteUserLike(oauthHeader, userId, likeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {
            }, (ex) -> {
            })
    );
  }

  public void deleteSketch() {
    pending.add(
        ProcessThisService.getInstance().deleteSketch(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {
            }, (ex) -> {
            })
    );
  }


  @Override
  public void addObserver(DataBufferObserver dataBufferObserver) {

  }

  @Override
  public void removeObserver(DataBufferObserver dataBufferObserver) {

  }


}
