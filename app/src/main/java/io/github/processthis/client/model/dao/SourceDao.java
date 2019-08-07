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

