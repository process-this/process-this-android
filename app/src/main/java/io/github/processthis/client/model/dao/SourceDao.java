package io.github.processthis.client.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.github.processthis.client.model.Source;
import java.util.List;

@Dao
public interface SourceDao {



    @Insert
    void insert(Source source);

    @Query("SELECT * FROM Source")
    LiveData<List<Source>> getAll();

    @Query("SELECT * FROM Source WHERE source_id = :sourceId")
    LiveData<Source> findById (Long sourceId);

  }

