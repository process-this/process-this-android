package io.github.processthis.client;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import io.github.processthis.client.ProcessThisDatabase.Converters;
import io.github.processthis.client.model.Sketch;
import io.github.processthis.client.model.Source;
import io.github.processthis.client.model.dao.SketchDao;
import io.github.processthis.client.model.dao.SourceDao;
import java.util.Date;

@Database(entities = {Sketch.class, Source.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ProcessThisDatabase extends RoomDatabase {

  public abstract SketchDao SketchDao();

  public abstract SourceDao SourceDao();

  private static ProcessThisDatabase INSTANCE;

  public static ProcessThisDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
          ProcessThisDatabase.class, "process_this_room").build();
    }
    return INSTANCE;
  }

  public static class Converters {

    @TypeConverter
    public Date longToDate(Long dateLong) {
      return (dateLong != null) ? new Date(dateLong) : null;
    }

    @TypeConverter
    public long dateToLong(Date date) {
      return (date != null) ? date.getTime() : null;
    }
  }

}
