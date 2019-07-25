package io.github.processthis.client.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Sketch.class,
childColumns = "sketch_id", parentColumns = "sketch_id"))
public class File {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "file_id")
  private long fileId;

  @NonNull
  @ColumnInfo(name = "sketch_id")
  private long sketchId;

  @NonNull
  @ColumnInfo(name = "file_name")
  private String fileName;

  @NonNull
  @ColumnInfo(name = "artist_name")
  private String artistName;

  private Date created;

  private Date updated;

  public File() {
  }


  public long getSketchId() {
    return sketchId;
  }



  public long getFileId() {
    return fileId;
  }

  public void setFileId(long fileId) {
    this.fileId = fileId;
  }

  @NonNull
  public String getFileName() {
    return fileName;
  }

  public void setFileName(@NonNull String fileName) {
    this.fileName = fileName;
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
