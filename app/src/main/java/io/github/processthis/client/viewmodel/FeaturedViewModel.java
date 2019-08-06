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

public class FeaturedViewModel extends AndroidViewModel implements LifecycleObserver {

  private ProcessThisService service;
  private MutableLiveData<List<Sketch>> searchResults = new MutableLiveData<>();
  private MutableLiveData<List<Sketch>> featuredFeed = new MutableLiveData<>();
  private CompositeDisposable pending = new CompositeDisposable();


  public FeaturedViewModel(@NonNull Application application) {
    super(application);
    service = ProcessThisService.getInstance();
  }

  public LiveData<List<Sketch>> getFeaturedFeed(int count) {
    pending.add(
        service.getFeatured(count)
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> featuredFeed.postValue(sketches)));
    return featuredFeed;
  }

  public LiveData<List<Sketch>> getSearchResults(String searchTerm) {
    pending.add(
        service.searchSketches(searchTerm)
            .subscribeOn(Schedulers.io())
            .subscribe((sketches) -> searchResults.postValue(sketches)));
    return searchResults;
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void disposePending(){
    pending.clear();
  }

}
