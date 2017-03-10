package com.example.scalephoto.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import com.example.scalephoto.cache.db.NetMntDbContent;
import com.example.scalephoto.cache.db.NetMntHelper;
import com.example.scalephoto.cache.models.NetMntLogData;
import com.example.scalephoto.cache.utils.NetMntSerializeUtil;
import com.example.scalephoto.utils.NetMntLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by xiaxl on 2017.03.07.
 */
public class NetMntDbManager {

    private static final String TAG = "NetMntDbManager";


    //---------------单例begin----------------
    private volatile static NetMntDbManager instance;

    private NetMntDbManager() {
    }

    public static NetMntDbManager getInstance() {
        if (instance == null) {
            synchronized (NetMntDbManager.class) {
                if (instance == null) {
                    instance = new NetMntDbManager();
                }
            }
        }
        return instance;
    }
    //---------------单例end----------------

    //#########################################SnsBiData的增删改查begin##########################################

    private NetMntHelper mSnsBiDbHelper = null;

    private synchronized NetMntHelper getSnsBiDbHelper(Context context) {
        if (mSnsBiDbHelper == null) {
            mSnsBiDbHelper = new NetMntHelper(context);
        }
        return mSnsBiDbHelper;
    }


    /**
     * 插入单条数据
     *
     * @param data
     * @return
     */
    public boolean insert(Context context, NetMntLogData data) {
        NetMntLogUtils.d(TAG, "---insert---");
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        return mSnsBiDbHelper.insert(NetMntDbContent.SnsBiTable.TABLE_NAME, objectToMap(data));
    }

    /**
     * 插入多条数据
     *
     * @param data
     * @return
     */
    public boolean batchInsert(Context context, List<NetMntLogData> data) {

        if (data == null || data.size() == 0) {
            return false;
        }
        //-----------------
        ContentValues[] valuesArr = new ContentValues[data.size()];
        //
        for (int i = 0; i < data.size(); i++) {
            ContentValues value = objectToMap(data.get(i));
            valuesArr[i] = value;
        }
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        return mSnsBiDbHelper.bulkInsert(NetMntDbContent.SnsBiTable.TABLE_NAME, valuesArr) >= 0;
    }

    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean delete(Context context, String title) {
        if (TextUtils.isEmpty(title)) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(NetMntDbContent.SnsBiTable.CTIME);
        deleteSql.append(" ='");
        deleteSql.append(title);
        deleteSql.append("'");
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        boolean deleteRet = mSnsBiDbHelper.delete(NetMntDbContent.SnsBiTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }


    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean deleteOldData(Context context, Long timeStart) {
        if (timeStart < 0) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(NetMntDbContent.SnsBiTable.CTIME);
        deleteSql.append(" <=");
        deleteSql.append(timeStart);
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        boolean deleteRet = mSnsBiDbHelper.delete(NetMntDbContent.SnsBiTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }


    /**
     * 删除所有数据
     *
     * @return
     */
    public boolean batchDelete(Context context) {
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        boolean deleteRet = mSnsBiDbHelper.delete(NetMntDbContent.SnsBiTable.TABLE_NAME, null, null) >= 0;
        return deleteRet;
    }

    /**
     * 查询所有的数据(以SnsBiDbContent.SnsBiTable.INSERT_TIME降序排序)
     *
     * @return
     */
    public List<NetMntLogData> query(Context context) {
        List<NetMntLogData> dataList = new ArrayList<NetMntLogData>();
        ;
        //
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        Cursor cursor = mSnsBiDbHelper.query(NetMntDbContent.SnsBiTable.TABLE_NAME, null, null, null, NetMntDbContent.SnsBiTable.CTIME + " desc");
        //
        try {
            if (cursor != null) {
                //

                //
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    int ctime = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.CTIME);
                    int page = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.PAGE);
                    int event = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.EVENT);
                    int ip = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.IP);
                    int network_type = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.NETWORK_TYPE);
                    int app_version = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.APP_VERSION);
                    int operator = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.OPERATOR);
                    int sequence = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.SEQUENCE);
                    int bi_data = cursor
                            .getColumnIndex(NetMntDbContent.SnsBiTable.BI_DATA);
                    do {
                        NetMntLogData data = new NetMntLogData();
                        //
                        data.setCtime(cursor.getLong(ctime));
                        data.setEvent(cursor.getString(event));
                        data.setPos(cursor.getString(page));
                        data.setIp(cursor.getString(ip));
                        data.setNetwork(cursor.getString(network_type));
                        data.setApp_version(cursor.getString(app_version));
                        data.setOperator(cursor.getString(operator));
                        data.setSequence(cursor.getString(sequence));
                        data.setData(NetMntSerializeUtil.deserializeObject(cursor.getBlob(bi_data)));
                        //
                        dataList.add(data);

                    } while (cursor.moveToNext());
                }
            }
        } catch (RuntimeException e) {
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return dataList;

    }

    /**
     * 获取数据条目
     *
     * @return
     */
    public int getDataCount(Context context) {
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        Cursor cursor = mSnsBiDbHelper.query(NetMntDbContent.SnsBiTable.TABLE_NAME, null, null, null, null);
        //
        try {
            if (cursor != null) {
                return cursor.getCount();
            }
        } catch (RuntimeException e) {
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return 0;
    }

    /**
     * 更新指定数据
     */
    public boolean update(Context context, NetMntLogData data) {
        //

        StringBuilder updateSql = new StringBuilder();
        updateSql.append(NetMntDbContent.SnsBiTable.CTIME);
        updateSql.append(" ='");
        updateSql.append(data.getCtime());
        updateSql.append("'");
        updateSql.append(";");
        //
        String sql = updateSql.toString();
        //-------------
        NetMntHelper mSnsBiDbHelper = getSnsBiDbHelper(context);
        return mSnsBiDbHelper.update(NetMntDbContent.SnsBiTable.TABLE_NAME, objectToMap(data), sql, null) > 0;
    }

    /**
     * 数据转为contentValues
     *
     * @param data
     * @return
     */
    private ContentValues objectToMap(NetMntLogData data) {
        ContentValues value = new ContentValues();
        value.put(NetMntDbContent.SnsBiTable.CTIME, data.getCtime());
        value.put(NetMntDbContent.SnsBiTable.PAGE, data.getPos());
        value.put(NetMntDbContent.SnsBiTable.EVENT, data.getEvent());
        value.put(NetMntDbContent.SnsBiTable.IP, data.getIp());
        value.put(NetMntDbContent.SnsBiTable.NETWORK_TYPE, data.getNetwork());
        value.put(NetMntDbContent.SnsBiTable.APP_VERSION, data.getApp_version());
        value.put(NetMntDbContent.SnsBiTable.OPERATOR, data.getOperator());
        value.put(NetMntDbContent.SnsBiTable.SEQUENCE, data.getSequence());
        value.put(NetMntDbContent.SnsBiTable.BI_DATA, NetMntSerializeUtil.serializeObject(data.getData()));
        return value;
    }


}
