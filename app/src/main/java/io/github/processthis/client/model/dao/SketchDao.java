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


