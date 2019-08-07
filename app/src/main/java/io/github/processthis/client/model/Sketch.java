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
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

/**
 * This class defies the attributes of a sketch object, the relvant column info for storage in an ORM, and provides setters and getters for those fields
 */
@Entity
public class Sketch {


  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "sketch_id")
  private long sketchId;

  @NonNull
  @ColumnInfo(name = "sketch_title")
  @SerializedName("name")
  private String sketchTitle;

  @ColumnInfo(name = "sketch_description")
  private String sketchDescription;

  @ColumnInfo(name = "files")
  private int numberOfFiles;

  @ColumnInfo(name = "is_shared")
  private boolean isShared;

  private String code;

  /**
   * This constructor is used to get an instance of a sketch object
   */
  public Sketch() {
  }

  /**This get the code attribute replacing the source class for this Beta release
   * @return 
   */
  public String getCode() {
    return code;
  }

  /**This sets the code attribute replacing the source class for this Beta release
   * @param code 
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**This method gets the  sketch UUID
   * @param sketchId 
   */
  public void setSketchId(long sketchId) {
    this.sketchId = sketchId;
  }

  /**This method sets the  sketch UUID
   * @return 
   */
  public long getSketchId() {
    return sketchId;
  }

  /**This method gets the sketch title
   * @return 
   */
  public String getSketchTitle() {
    return sketchTitle;
  }

  /**This method sets the sketch title
   * @param sketchTitle 
   */
  public void setSketchTitle(String sketchTitle) {
    this.sketchTitle = sketchTitle;
  }

  /**This method gets the sketch description
   * @return 
   */
  public String getSketchDescription() {
    return sketchDescription;
  }

  /**This method gets the sketch description
   * @param sketchDescription 
   */
  public void setSketchDescription(String sketchDescription) {
    this.sketchDescription = sketchDescription;
  }

  /**This method gets the number of files comprising a sketch
   * @return 
   */
  public int getNumberOfFiles() {
    return numberOfFiles;
  }

  /**This method sets the number of files comprising a sketch
   * @param numberOfFiles 
   */
  public void setNumberOfFiles(int numberOfFiles) {
    this.numberOfFiles = numberOfFiles;
  }

  /**this method gets the boolean designating whether or not the user is sharing a sketch publicly
   * @return 
   */
  public boolean isShared() {
    return isShared;
  }

  /**this method sets the boolean designating whether or not the user is sharing a sketch publicly
   * @param shared 
   */
  public void setShared(boolean shared) {
    isShared = shared;
  }


}



