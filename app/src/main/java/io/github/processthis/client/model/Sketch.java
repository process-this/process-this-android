package io.github.processthis.client.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sketch {



  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "sketch_id")
  private long sketchId;

  @NonNull
  @ColumnInfo(name = "sketch_title")
  private String sketchTitle;

  @ColumnInfo(name = "sketch_description")
  private String sketchDescription;

  @ColumnInfo(name = "files")
  private int numberOfFiles;

  @ColumnInfo(name = "is_shared")
  private boolean isShared;
  
  private String code;

  public Sketch() {
  }

  public void setSketchId(long sketchId) {
    this.sketchId = sketchId;
  }

  public long getSketchId() {
    return sketchId;
  }

  public String getSketchTitle() {
    return sketchTitle;
  }

  public void setSketchTitle(String sketchTitle) {
    this.sketchTitle = sketchTitle;
  }

  public String getSketchDescription() {
    return sketchDescription;
  }

  public void setSketchDescription(String sketchDescription) {
    this.sketchDescription = sketchDescription;
  }

  public int getNumberOfFiles() {
    return numberOfFiles;
  }

  public void setNumberOfFiles(int numberOfFiles) {
    this.numberOfFiles = numberOfFiles;
  }

  public boolean isShared() {
    return isShared;
  }

  public void setShared(boolean shared) {
    isShared = shared;
  }


}



