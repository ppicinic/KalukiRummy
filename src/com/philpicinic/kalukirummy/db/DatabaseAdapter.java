package com.philpicinic.kalukirummy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter {

	private static final String KEY_ROWID = "_id";
	private static final String KEY_SOUND_SETTING = "sound_setting";
	private static final String KEY_CHOICE_SETTING = "choice_setting";
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "game_settings";
	private static final String SETTINGS_TABLE = "settings";
	@SuppressWarnings("unused")
	private Context context;

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public DatabaseAdapter(Context context) {
		this.context = context;
		dbHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final int DATABASE_VERSION = 1;

		private static final String CREATE_SETTINGS_TABLE = " create table "
				+ SETTINGS_TABLE + " (_id integer primary key autoincrement,"
				+ " sound_setting integer not null, choice_setting integer not null);";

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL(CREATE_SETTINGS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			database.execSQL("DROP TABLE IF EXISTS todo");
			onCreate(database);
		}
	}
	
	public DatabaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
	
	public void close() {
    	dbHelper.close();
    }
	
	public long insertRecord(boolean newSoundSetting, boolean newChoiceSetting) {
		int sound = (newSoundSetting)? 1 : 0;
		int choice = (newChoiceSetting)? 1 : 0;
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SOUND_SETTING, sound);
        initialValues.put(KEY_CHOICE_SETTING, choice);
		return db.insert(SETTINGS_TABLE, null, initialValues);
    }
	
	public boolean updateRecord(long rowId, boolean newSoundSetting, boolean newChoiceSetting) {
		int sound = (newSoundSetting)? 1 : 0;
		int choice = (newChoiceSetting)? 1 : 0;
        ContentValues args = new ContentValues();
        args.put(KEY_SOUND_SETTING, sound);
        args.put(KEY_CHOICE_SETTING, choice);
        return db.update(SETTINGS_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
	
	public void insertOrUpdateRecord(boolean newSoundSetting, boolean newChoiceSetting) {
		int sound = (newSoundSetting)? 1 : 0;
		int choice = (newChoiceSetting)? 1 : 0;
		String INSERT_OR_UPDATE_RECORD =
    		"INSERT OR REPLACE INTO " + SETTINGS_TABLE + " (" + KEY_ROWID + "," + KEY_SOUND_SETTING + "," + KEY_CHOICE_SETTING + ") " +
            "VALUES (1," + sound + "," + choice +  ");";
    	db.execSQL(INSERT_OR_UPDATE_RECORD);
    }
	
	public boolean deleteRecord(long rowId) {
        return db.delete(SETTINGS_TABLE, KEY_ROWID + 
        		"=" + rowId, null) > 0;
    }
	
	public Cursor getAllRecords() {
        return db.query(SETTINGS_TABLE, new String[] {
        		KEY_ROWID,
        		KEY_SOUND_SETTING,
        		KEY_CHOICE_SETTING
        		}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
	
	public Cursor getRecord(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, SETTINGS_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_SOUND_SETTING,
                		KEY_CHOICE_SETTING
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
