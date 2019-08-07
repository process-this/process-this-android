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

/**
 * Abstracted class  to create the context instance of the Room Database
 * returns this instance.
 */
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

  /**
   * Type converter class for conversion of time from Unix Epoch time value to
   * a new (@link Date) and also contains a method to reverse the conversion.
   */
  public static class Converters {

    /**
     * Converts the long value of seconds into a (@link Date).
     *
     * @param dateLong
     * @return new Date
     */
    @TypeConverter
    public Date longToDate(Long dateLong) {
      return (dateLong != null) ? new Date(dateLong) : null;
    }

    /**
     * Reverses the conversion from (@link Date) to long.
     * @param date
     * @return Long
     */
    @TypeConverter
    public long dateToLong(Date date) {
      return (date != null) ? date.getTime() : null;
    }
  }

}
