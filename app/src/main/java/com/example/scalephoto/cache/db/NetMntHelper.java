package com.example.scalephoto.cache.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * BI DB helper
 * <p>
 * create by xiaxl on 2017.03.07.
 */
public class NetMntHelper extends SQLiteOpenHelper {
    private static final String TAG = "NetMntHelper";


    public NetMntHelper(Context context) {
        super(context, NetMntDbContent.DATABASE_NAME, null, NetMntDbContent.DATABASE_VERSION);
    }

    // 建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表
        createSnsBiTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级后，删除旧表
        if (newVersion >= 2 && oldVersion < 2) {
            destorySnsBiTable(db);
        }
        // 升级后，重建表
        onCreate(db);
    }
    //#############################################################建表###删表###############################################################

    /**
     * 建立Sns BI 数据存储表
     *
     * @param db
     */
    private void createSnsBiTable(SQLiteDatabase db) {
        StringBuffer createCacheSql = new StringBuffer()
                .append("CREATE TABLE IF NOT EXISTS ")
                //
                .append(NetMntDbContent.SnsBiTable.TABLE_NAME)
                //
                .append(" (")
                //
                .append("_id integer primary key autoincrement,")
                //
                .append(NetMntDbContent.SnsBiTable.CTIME)
                .append(" INTEGER,")
                //
                .append(NetMntDbContent.SnsBiTable.PAGE)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.EVENT)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.IP)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.NETWORK_TYPE)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.APP_VERSION)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.OPERATOR)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.SEQUENCE)
                .append(" varchar(255),")
                //
                .append(NetMntDbContent.SnsBiTable.BI_DATA)
                .append(" blob")
                //
                .append(");");
        db.execSQL(createCacheSql.toString());
    }

    /**
     * destory sns BI 数据存储表
     *
     * @param db
     */
    private void destorySnsBiTable(SQLiteDatabase db) {
        db.execSQL(new StringBuffer().append("DROP TABLE IF EXISTS ")
                .append(NetMntDbContent.SnsBiTable.TABLE_NAME).append(";")
                .toString());
    }

    //##################################################################SnsBi 数据库的 增删改查 begin####################################################################

    /**
     * 插入一条数据
     *
     * @param table
     * @param values
     * @return
     */
    public synchronized boolean insert(String table, ContentValues values) {
        Log.i(TAG, "Insert data to flash DB. table:" + table);
        if (values == null || TextUtils.isEmpty(table)) {
            return false;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return false;
        }
        try {
            return db.insert(table, null, values) >= 0;
        } catch (Exception e) {
            Log.e(TAG, "Insert data to client fail!", e);
            return true;
        }
    }


    public synchronized Cursor query(String table, String[] projection,
                                     String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "Query data from flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return null;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return null;
        }
        Cursor queryResult = null;
        try {
            queryResult = db.query(table, projection, selection, selectionArgs,
                    null, null, sortOrder);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Query SQL error:" + e.getMessage(), e);
        } catch (SQLiteException e) {
            Log.e(TAG, "Query database error!", e);
        } catch (SQLException e) {
            Log.e(TAG, "Query fail!", e);
        }
        return queryResult;
    }


    /**
     * Batch insert new data.
     */
    public synchronized int bulkInsert(String table, ContentValues[] values) {
        Log.i(TAG, "Batch insert data to flash DB. table:" + table);
        if (values == null || TextUtils.isEmpty(table)) {
            return -1;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return -1;
        }
        int numValues = values.length;
        try {
            // start transaction
            db.beginTransaction();
            try {
                for (int i = 0; i < numValues; i++) {
                    if (values[i] != null) {
                        db.insert(table, null, values[i]);
                    }
                }
                // Marks the current transaction as successful
                db.setTransactionSuccessful();
            } finally {
                // Commit or rollback by flag of transaction
                db.endTransaction();
            }
        } catch (Exception e) {
            Log.e(TAG, "BulkUpdate fail! table: " + table, e);
            return -1;
        }
        return numValues;
    }

    /**
     * 删除
     *
     * @param table
     * @param selection
     * @param selectionArgs
     * @return
     */
    public synchronized int delete(String table, String selection,
                                   String[] selectionArgs) {
        Log.i(TAG, "Delete data on flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return -1;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return -1;
        }
        try {
            return db.delete(table, selection, selectionArgs);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Delete fail!", e);
            return -1;
        }
    }

    /**
     * 更新
     *
     * @param table
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    public synchronized int update(String table, ContentValues values,
                                   String selection, String[] selectionArgs) {
        int result = 0;
        Log.i(TAG, "Update data on flash DB. table:" + table);
        if (TextUtils.isEmpty(table)) {
            return -1;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return -1;
        }
        if (values == null && selection != null) {
            // execute a SQL
            db.execSQL(selection);
            return 1;
        } else if (values == null) {
            return -1;
        } else {
            result = db.update(table, values, selection, selectionArgs);
        }
        return result;
    }

    /**
     * Get instance of SQLiteDatabase,deal with exception
     *
     * @return
     */
    public synchronized SQLiteDatabase getSnsBiDb() {
        return this.getWritableDatabase();
    }


}
