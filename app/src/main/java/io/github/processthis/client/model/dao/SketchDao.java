package io.github.processthis.client.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.processthis.client.model.Sketch;
import java.util.List;

/**
 * public dao interface includes the methods that will interact with Room Data
 */
@Dao
public interface SketchDao {


  /**
   * Insert a sketch object into the ORM
   */
  @Insert
  void insert(Sketch sketch);

  /**
   * Query method that grabs a list of all sketch objects surrounded by an observable Live data
   */
  @Query("SELECT * FROM sketch")
  LiveData<List<Sketch>> getAll();

  /**
   * Query method that pulls a specific Sketch object with a given sketchId surrounded by an
   * observable LiveData
   */
  @Query("SELECT * FROM sketch WHERE sketch_id = :sketchId")
  LiveData<Sketch> findById(Long sketchId);

}


