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

package io.github.processthis.client.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

/**
 * This class is presently not being used. Later versions of the app will allow for multi-source Sketches
 */
@Entity(foreignKeys = @ForeignKey(entity = Sketch.class,
    childColumns = "sketch_id", parentColumns = "sketch_id"))
public class Source {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "source_id")
  private long sourceId;

  public void setSketchId(long sketchId) {
    this.sketchId = sketchId;
  }

  @NonNull
  @ColumnInfo(name = "sketch_id")
  private long sketchId;

  @NonNull
  @ColumnInfo(name = "file_name")
  private String sourceName;

  @NonNull
  @ColumnInfo(name = "artist_name")
  private String artistName;

  private Date created;

  private Date updated;

  public Source() {
  }


  public long getSketchId() {
    return sketchId;
  }


  public long getSourceId() {
    return sourceId;
  }

  public void setSourceId(long sourceId) {
    this.sourceId = sourceId;
  }

  @NonNull
  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(@NonNull String sourceName) {
    this.sourceName = sourceName;
  }

  @NonNull
  public String getArtistName() {
    return artistName;
  }

  public void setArtistName(@NonNull String artistName) {
    this.artistName = artistName;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

}
