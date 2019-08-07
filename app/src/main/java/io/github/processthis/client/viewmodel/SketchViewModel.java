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
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.service.ProcessThisService;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * Viewmodel for the Featured fragment, this view model is lifecycle aware, populates the data needed
 * for the view in the Featured fragment.
 */

public class SketchViewModel extends AndroidViewModel implements LifecycleObserver {

  private ProcessThisService service;
  private MutableLiveData<List<Sketch>> searchResults = new MutableLiveData<>();
  private MutableLiveData<List<Sketch>> featuredFeed = new MutableLiveData<>();
  private MutableLiveData<List<Sketch>> recentSketches = new MutableLiveData<>();
  private MutableLiveData<List<Sketch>> userSketches = new MutableLiveData<>();

  private CompositeDisposable pending = new CompositeDisposable();


  /**
   * Constructer to provide the application instance for the view model.
   * @param application
   */
  public SketchViewModel(@NonNull Application application) {
    super(application);
    service = ProcessThisService.getInstance();
  }

  /**
   * Livedata method that provides a list of sketches to the view
   * @param count
   * @return returns the list of sketches
   */
  public LiveData<List<Sketch>> getFeaturedFeed(int count) {
    pending.add(
        service.getFeatured(count)
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> featuredFeed.postValue(sketches)));
    return featuredFeed;
  }

  /**
   * Livedata method that provides a list of sketches to client based on thier searchterms
   * @param searchTerm
   * @return searchResults of sketches
   */
  public LiveData<List<Sketch>> getSearchResults(String searchTerm) {
    pending.add(
        service.searchSketches(searchTerm)
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> searchResults.postValue(sketches)));
    return searchResults;
  }

  public LiveData<List<Sketch>> getRecentSketches() {
    pending.add(
        service.getRecentSketches()
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> recentSketches.postValue(sketches)));
    return recentSketches;
  }

  public LiveData<List<Sketch>> getUserProfileSketches(String oauthHeader, String userId) {
    pending.add(
        service.getUserProfileSketches(oauthHeader, userId)
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> userSketches.postValue(sketches)));
    return userSketches;
  } // TODO verify this method will result in sketches by current user


  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending() {
    pending.clear();
  }

}
