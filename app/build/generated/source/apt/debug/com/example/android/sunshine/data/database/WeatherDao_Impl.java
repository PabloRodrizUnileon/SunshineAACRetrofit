package com.example.android.sunshine.data.database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
@SuppressWarnings("unchecked")
public class WeatherDao_Impl implements WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWeatherEntry;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldWeather;

  public WeatherDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntry = new EntityInsertionAdapter<WeatherEntry>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `weather`(`pressure`,`humidity`,`wind`,`degrees`,`idEntry`,`weather_icon_id`,`date`,`min`,`max`) VALUES (?,?,?,?,nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WeatherEntry value) {
        stmt.bindDouble(1, value.getPressure());
        stmt.bindDouble(2, value.getHumidity());
        stmt.bindDouble(3, value.getWind());
        stmt.bindDouble(4, value.getDegrees());
        stmt.bindLong(5, value.getIdEntry());
        stmt.bindLong(6, value.getWeatherIconId());
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindLong(7, _tmp);
        }
        stmt.bindDouble(8, value.getMin());
        stmt.bindDouble(9, value.getMax());
      }
    };
    this.__preparedStmtOfDeleteOldWeather = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM weather WHERE date < ?";
        return _query;
      }
    };
  }

  @Override
  public void bullInsert(WeatherEntry... weatherEntry) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWeatherEntry.insert(weatherEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteOldWeather(Date date) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldWeather.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      final Long _tmp;
      _tmp = DateConverter.toTimestamp(date);
      if (_tmp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindLong(_argIndex, _tmp);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteOldWeather.release(_stmt);
    }
  }

  @Override
  public LiveData<WeatherEntry> getWeatherByDate(Date date) {
    final String _sql = "SELECT * FROM weather WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<WeatherEntry>() {
      private Observer _observer;

      @Override
      protected WeatherEntry compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfIdEntry = _cursor.getColumnIndexOrThrow("idEntry");
          final int _cursorIndexOfWeatherIconId = _cursor.getColumnIndexOrThrow("weather_icon_id");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfMin = _cursor.getColumnIndexOrThrow("min");
          final int _cursorIndexOfMax = _cursor.getColumnIndexOrThrow("max");
          final WeatherEntry _result;
          if(_cursor.moveToFirst()) {
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final int _tmpIdEntry;
            _tmpIdEntry = _cursor.getInt(_cursorIndexOfIdEntry);
            final int _tmpWeatherIconId;
            _tmpWeatherIconId = _cursor.getInt(_cursorIndexOfWeatherIconId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpMin;
            _tmpMin = _cursor.getDouble(_cursorIndexOfMin);
            final double _tmpMax;
            _tmpMax = _cursor.getDouble(_cursorIndexOfMax);
            _result = new WeatherEntry(_tmpIdEntry,_tmpWeatherIconId,_tmpDate,_tmpMin,_tmpMax,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<WeatherEntry>> getCurrentWeatherForecasts(Date date) {
    final String _sql = "SELECT * FROM weather WHERE date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    return new ComputableLiveData<List<WeatherEntry>>() {
      private Observer _observer;

      @Override
      protected List<WeatherEntry> compute() {
        if (_observer == null) {
          _observer = new Observer("weather") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPressure = _cursor.getColumnIndexOrThrow("pressure");
          final int _cursorIndexOfHumidity = _cursor.getColumnIndexOrThrow("humidity");
          final int _cursorIndexOfWind = _cursor.getColumnIndexOrThrow("wind");
          final int _cursorIndexOfDegrees = _cursor.getColumnIndexOrThrow("degrees");
          final int _cursorIndexOfIdEntry = _cursor.getColumnIndexOrThrow("idEntry");
          final int _cursorIndexOfWeatherIconId = _cursor.getColumnIndexOrThrow("weather_icon_id");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("date");
          final int _cursorIndexOfMin = _cursor.getColumnIndexOrThrow("min");
          final int _cursorIndexOfMax = _cursor.getColumnIndexOrThrow("max");
          final List<WeatherEntry> _result = new ArrayList<WeatherEntry>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final WeatherEntry _item;
            final double _tmpPressure;
            _tmpPressure = _cursor.getDouble(_cursorIndexOfPressure);
            final double _tmpHumidity;
            _tmpHumidity = _cursor.getDouble(_cursorIndexOfHumidity);
            final double _tmpWind;
            _tmpWind = _cursor.getDouble(_cursorIndexOfWind);
            final double _tmpDegrees;
            _tmpDegrees = _cursor.getDouble(_cursorIndexOfDegrees);
            final int _tmpIdEntry;
            _tmpIdEntry = _cursor.getInt(_cursorIndexOfIdEntry);
            final int _tmpWeatherIconId;
            _tmpWeatherIconId = _cursor.getInt(_cursorIndexOfWeatherIconId);
            final Date _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = DateConverter.toDate(_tmp_1);
            final double _tmpMin;
            _tmpMin = _cursor.getDouble(_cursorIndexOfMin);
            final double _tmpMax;
            _tmpMax = _cursor.getDouble(_cursorIndexOfMax);
            _item = new WeatherEntry(_tmpIdEntry,_tmpWeatherIconId,_tmpDate,_tmpMin,_tmpMax,_tmpHumidity,_tmpPressure,_tmpWind,_tmpDegrees);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public int countAllFutureWeather(Date date) {
    final String _sql = "SELECT COUNT(idEntry) FROM weather WHERE date >= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp;
    _tmp = DateConverter.toTimestamp(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
