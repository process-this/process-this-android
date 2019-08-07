package io.github.processthis.client.service;

import io.github.processthis.client.BuildConfig;
import io.github.processthis.client.model.Like;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.model.UserProfile;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.List;
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


  /**
   * Gets an observable single Sketch object from the server, requires oauth, usedId, and sketchId
   * for path
   */
  @GET("users/{userId}/sketches/{sketchId}")
  Single<Sketch> getSingleSketch(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId, @Path("sketchId") String sketchId);

  /**
   * Gets an observable  List of all Sketch objects made by a UserProfile from the server, requires
   * oauth and usedId for path
   */
  @GET("users/{userId}/sketches/")
  Observable<List<Sketch>> getUserProfileSketches(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId);

  /**
   * Gets an observable single UserProfile object from the server, requires oauth and usedId for
   * path
   */
  @GET("users/{userId}")
  Single<UserProfile> getSingleUserProfile(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId);

  /**
   * Gets an observable  List of all Like objects made by a UserProfile from the server, requires
   * oauth and usedId for path
   */
  @GET("users/{userId}/likes")
  Observable<List<Like>> getUserProfilesLikes(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId);

  /**
   * Gets an observable list of all Likes attached to a Sketch requires oauth, usedId of
   * Sketch-creating UserProfile, and sketchId for path
   */
  @GET("users/{userId}/sketches/{sketchId}/likes")
  Observable<List<Like>> getSketchLikes(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId, @Path("sketchId") String sketchId);

  /**
   * Adds a new UserProfile to the server. Requires oauth in header
   */
  @POST
  Single<UserProfile> postUserProfile(@Header("Authorization") String oauthHeader);

  /**
   * Posts a sketch. Requies oath in header. Should require userId for path, but I dont want to
   * change anything at this point
   */
  @POST
  Single<Sketch> postSketch(@Header("Authorization") String oauthHeader);

  /**
   * Creates a Like object from a userprofile to a sketch. requires userId of the "liking" user and
   * the "liked" sketch
   */
  @PUT
  Single<UserProfile> putLike(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId, @Path("sketchId") String sketchId);


  /**
   * Gets an observable list of only sketches that have been liked, ordered by most likes
   */
  @GET("search/feed")
  Observable<List<Sketch>> getFeatured(@Query(value = "count") int count);

  /**
   * Gets a list of all sketches whose name contains the String search term
   */
  @GET("search")
  Observable<List<Sketch>> searchSketches(@Query("q") String searchTerm);

  /**
   * Unlikes a sketch. Deletes Like object. Reuires unliked userId and unliked SketchId and oauth
   * for path
   */
  @DELETE("users/{userId}/likes/{likeId}")
  Completable deleteUserLike(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId, @Path("likeId") String likeId);

  /**
   * Deletes a sketch. requires sketch creator userId and sketchId and oauth for path
   */
  @DELETE("users/{userId}/sketches/{sketchId}")
  Completable deleteSketch(@Header("Authorization") String oauthHeader,
      @Path("userId") String userId, @Path("sketchId") String likeId);

  /**
   * Gets an observable list of the ten most recently created sketches.
   */
  @GET("search/home")
  Observable<List<Sketch>> getRecentSketches();


  /**
   * Gets an instance of the process this service
   */
  static ProcessThisService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * This nested class contains the static block that builds the retrofit builder with the Okhttp
   * interceptor
   */
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

