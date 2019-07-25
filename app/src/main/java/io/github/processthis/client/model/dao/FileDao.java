package io.github.processthis.client.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.io.File;
import java.util.List;

@Dao
public interface FileDao {



    @Insert
    void insert(File file);

    @Query("SELECT * FROM file")
    LiveData<List<File>> getAll();

    @Query("SELECT * FROM file WHERE file_id = :fileId")
    LiveData<File> findById (Long fileId);

  }

