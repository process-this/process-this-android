package io.github.processthis.client.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.processthis.client.model.Sketch;
import java.util.List;

@Dao
public interface SketchDao {


  @Insert
  void insert(Sketch sketch);

  @Query("SELECT * FROM sketch")
  LiveData<List<Sketch>> getAll();

  @Query("SELECT * FROM sketch WHERE sketch_id = :sketchId")
  LiveData<Sketch> findById(Long sketchId);

}


