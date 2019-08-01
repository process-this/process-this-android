package io.github.processthis.client.service;

import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.model.Like;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.model.UserProfile;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
import java.util.logging.Level;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ProcessThisService {



  @GET("users/{userId}/sketches/{sketchId}")
  Single<Sketch> getSingleSketch(@Header("Authorization") String oauthHeader);

  @GET("users/{userId}/sketches/")
  Observable<List<Sketch>> getUserProfileSketches(@Header("Authorization") String oauthHeader);

  @GET("users/{userId}")
  Single<UserProfile> getSingleUserProfile(@Header("Authorization") String oauthHeader);

  @GET("users/{userId}/sketches/{sketchId}/likes")
  Observable<List<UserProfile>> getUserProfilesLikes(@Header("Authorization") String oauthHeader);

  @POST
  UserProfile createUserProfile(@Header("Authorization") String oauthHeader);

  @POST
  Sketch createSketch(@Header("Authorization") String oauthHeader);

  @PUT
  UserProfile createLike(@Header("Authorization") String oauthHeader);






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
   //       .baseUrl(BuildConfig.BASE_URL)
          .build();
      INSTANCE = retrofit.create(ProcessThisService.class);
    }

  }

}

