package com.example.android.sunshine.data.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
@SuppressWarnings("unchecked")
public class SunshineDatabase_Impl extends SunshineDatabase {
  private volatile WeatherDao _weatherDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `weather` (`pressure` REAL NOT NULL, `humidity` REAL NOT NULL, `wind` REAL NOT NULL, `degrees` REAL NOT NULL, `idEntry` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `weather_icon_id` INTEGER NOT NULL, `date` INTEGER, `min` REAL NOT NULL, `max` REAL NOT NULL)");
        _db.execSQL("CREATE UNIQUE INDEX `index_weather_date` ON `weather` (`date`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"27712421e1eae0806f52f67613f7df08\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `weather`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWeather = new HashMap<String, TableInfo.Column>(9);
        _columnsWeather.put("pressure", new TableInfo.Column("pressure", "REAL", true, 0));
        _columnsWeather.put("humidity", new TableInfo.Column("humidity", "REAL", true, 0));
        _columnsWeather.put("wind", new TableInfo.Column("wind", "REAL", true, 0));
        _columnsWeather.put("degrees", new TableInfo.Column("degrees", "REAL", true, 0));
        _columnsWeather.put("idEntry", new TableInfo.Column("idEntry", "INTEGER", true, 1));
        _columnsWeather.put("weather_icon_id", new TableInfo.Column("weather_icon_id", "INTEGER", true, 0));
        _columnsWeather.put("date", new TableInfo.Column("date", "INTEGER", false, 0));
        _columnsWeather.put("min", new TableInfo.Column("min", "REAL", true, 0));
        _columnsWeather.put("max", new TableInfo.Column("max", "REAL", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeather = new HashSet<TableInfo.Index>(1);
        _indicesWeather.add(new TableInfo.Index("index_weather_date", true, Arrays.asList("date")));
        final TableInfo _infoWeather = new TableInfo("weather", _columnsWeather, _foreignKeysWeather, _indicesWeather);
        final TableInfo _existingWeather = TableInfo.read(_db, "weather");
        if (! _infoWeather.equals(_existingWeather)) {
          throw new IllegalStateException("Migration didn't properly handle weather(com.example.android.sunshine.data.database.WeatherEntry).\n"
                  + " Expected:\n" + _infoWeather + "\n"
                  + " Found:\n" + _existingWeather);
        }
      }
    }, "27712421e1eae0806f52f67613f7df08", "e73f016c0a96b0c496ea35b29ea57703");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "weather");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `weather`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public WeatherDao weatherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }
}
