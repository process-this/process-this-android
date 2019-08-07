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

package io.github.processthis.client.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
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


  /**
   * MainViewModel class for communications with ProcessThis RESTful API, provides methods
   * to call, query, and post the API.
   *
   * @param application
   */
  public MainViewModel(@NonNull Application application) {
    super(application);
    userId = "1cf96d61-3c68-42a5-a4a6-6711430efb04";
    GoogleSignInAccount account = GoogleSignInService.getInstance().getAccount();

    if (account != null) {
      oauthHeader = String.format(BuildConfig.AUTHORIZATION_FORMAT, account.getIdToken());
    }
    else {
      Log.d("Trace", "No oauth");
      oauthHeader = "";
    }
  }

  public Sketch getSketch() {
    return sketch;
  }

  public void setSketch(Sketch sketch) {
    this.sketch = sketch;
  }

  /**
   * Finds a sketch, requiring the oauthHeader for authentiction to the server, a userId and
   * a sketchId so that a particular sketch may be retrieved by the client to view etc.
   */

  public void findASketch() {
    pending.add(
        ProcessThisService.getInstance().getSingleSketch(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketch) -> sketchResult.setValue(sketch))
    );
  }

  /**
   * Method that takes a userId to retrieve a list of all sketches posted by a particular user.
   *
   */
  public void findUserSketchList() {
    pending.add(
        ProcessThisService.getInstance().getUserProfileSketches(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketches) -> sketchListResult.setValue(sketches))
    );
  }

  /**
   * Method that takes a userId to retrieve the profile information that is associated with the id.
   */
  public void findUser(){
    pending.add(
        ProcessThisService.getInstance().getSingleUserProfile(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  /**
   * Method that takes userId to retrieve a list of sketches that the user has given likes to.
   */
  public void findUserLikes(){
    pending.add(
        ProcessThisService.getInstance().getUserProfilesLikes(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((likes) -> likeListResult.setValue(likes))
    );
  }

  public void findSketchLikes(){
    pending.add(
        ProcessThisService.getInstance().getSketchLikes(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((likes) -> likeListResult.setValue(likes))
    );
  }

  /**
   * Method that will allow user to post their profile and the associated data that is needed to complete
   * this request.
   */
  public void addUserProfile(){
    pending.add(
        ProcessThisService.getInstance().postUserProfile(oauthHeader)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  /**
   * Method that will allow a user to post and publish a sketch they have created.
   */
  public void addSketch(){
    pending.add(
        ProcessThisService.getInstance().postSketch(oauthHeader, userId, sketch)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((sketch) -> sketchResult.setValue(sketch))
    );
  }

  /**
   * Method that will allow a user to post a like to a sketch by passing the userId of the
   * of the client and the sketchId of the liked sketch
   */
  public void addLike(){
    pending.add(
        ProcessThisService.getInstance().putLike(oauthHeader, userId, sketchId )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((userProfile) -> userProfileResult.setValue(userProfile))
    );
  }

  /**
   * Method that will allow user to unlike a sketch, passing the userId and SketchId to call the
   * delete method on the server to delete the like.
   */
  public void unlike(){
    pending.add(
        ProcessThisService.getInstance().deleteUserLike(oauthHeader, userId, likeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {}, (ex) -> {})
    );
  }

  /**
   * Method that will pass the UserId and the SketchId in order to call the delete method in the
   * sketchController of the API and call its delete method.
   */
  public void deleteSketch(){
    pending.add(
        ProcessThisService.getInstance().deleteSketch(oauthHeader, userId, sketchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {}, (ex) -> {})
    );
  }

  /**
   * Adds the observer so that the changes in the data in the view model may be observed and reflected
   * in the views of the app.
   *
   * @param dataBufferObserver
   */
  @Override
  public void addObserver(DataBufferObserver dataBufferObserver) {

  }

  /**
   * Method that can remove the Observer from the data that is associated with the ProcessThis web API.
   * @param dataBufferObserver
   */
  @Override
  public void removeObserver(DataBufferObserver dataBufferObserver) {

  }


}
