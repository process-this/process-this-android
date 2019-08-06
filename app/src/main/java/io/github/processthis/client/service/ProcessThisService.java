package io.github.processthis.client.service;

import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.model.Like;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.model.UserProfile;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.logging.Level;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProcessThisService {



  @GET("users/{userId}/sketches/{sketchId}")
  Single<Sketch> getSingleSketch(@Header("Authorization") String oauthHeader, @Path("userId") String userId, @Path("sketchId") String sketchId);

  @GET("users/{userId}/sketches/")
  Observable<List<Sketch>> getUserProfileSketches(@Header("Authorization") String oauthHeader, @Path("userId") String userId);

  @GET("users/{userId}")
  Single<UserProfile> getSingleUserProfile(@Header("Authorization") String oauthHeader, @Path("userId") String userId);

  @GET("users/{userId}/likes")
  Observable<List<Like>> getUserProfilesLikes(@Header("Authorization") String oauthHeader, @Path("userId") String userId);

  @GET("users/{userId}/sketches/{sketchId}/likes")
  Observable<List<Like>>  getSketchLikes(@Header("Authorization") String oauthHeader, @Path("userId") String userId, @Path("sketchId") String sketchId);

  @POST
  Single<UserProfile> postUserProfile(@Header("Authorization") String oauthHeader);

  @POST
  Single<Sketch> postSketch(@Header("Authorization") String oauthHeader);

  @PUT
  Single<UserProfile> putLike(@Header("Authorization") String oauthHeader, @Path("userId") String userId, @Path("sketchId") String sketchId);


  @GET("search/feed")
  Observable<List<Sketch>> getFeatured(@Query(value = "count") int count);

  @GET("search")
  Observable<List<Sketch>> searchSketches(@Query("q") String searchTerm);

  @DELETE("users/{userId}/likes/{likeId}")
  Completable deleteUserLike(@Header("Authorization") String oauthHeader, @Path("userId") String userId, @Path("likeId") String likeId);

  @DELETE("users/{userId}/sketches/{sketchId}")
  Completable deleteSketch(@Header("Authorization") String oauthHeader, @Path("userId") String userId, @Path("sketchId") String likeId);

  @GET("search/home/")
  Observable<List<Sketch>> getRecentSketches();


  static ProcessThisService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final ProcessThisService INSTANCE;

    static {
      // Following five lines should be removed/commented out for production release.
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
 //     interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .client(client) // This should be removed/commented out for production release.
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create()) // TODO Check; maybe change?
          .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(ProcessThisService.class);
    }

  }

}

