package io.github.processthis.client.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.processthis.client.model.Source;
import java.util.List;

/**
 * Dao interfacecurrently unused, but will tie together multiple source files for a sketch
 */
@Dao
public interface SourceDao {


  /**Inserts a source object into the ORM
   * @param source 
   */
  @Insert
  void insert(Source source);

  /**Query method that grabs a list of all source objects surrounded by an observable Live data
   * @return 
   */
  @Query("SELECT * FROM Source")
  LiveData<List<Source>> getAll();

  /**
   *    Query method that pulls a specific Source object with a given sketchId surrounded by an
   *    observable LiveData
   * @param sourceId 
   * @return
   */
  @Query("SELECT * FROM Source WHERE source_id = :sourceId")
  LiveData<Source> findById(Long sourceId);

}

