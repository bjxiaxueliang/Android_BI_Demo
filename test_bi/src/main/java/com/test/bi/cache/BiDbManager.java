package com.test.bi.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.test.bi.cache.db.BiDbContent;
import com.test.bi.cache.db.BiDbHelper;
import com.test.bi.cache.models.BiData;
import com.test.bi.cache.models.BiSessionData;
import com.test.bi.cache.utils.BiSerializeUtil;
import com.test.bi.utils.BiLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * create by xiaxl.
 */
public class BiDbManager {

    private static final String TAG = "NetMntDbManager";


    //---------------单例begin----------------
    private volatile static BiDbManager instance;

    private BiDbManager() {
    }

    public static BiDbManager getInstance() {
        if (instance == null) {
            synchronized (BiDbManager.class) {
                if (instance == null) {
                    instance = new BiDbManager();
                }
            }
        }
        return instance;
    }
    //---------------单例end----------------

    //#########################################SessionData的增删改查begin##########################################

    private BiDbHelper mBiDbHelper = null;

    private synchronized BiDbHelper getBiDbHelper(Context context) {
        if (mBiDbHelper == null) {
            mBiDbHelper = new BiDbHelper(context);
        }
        return mBiDbHelper;
    }


    /**
     * 插入单条数据
     *
     * @param data
     * @return
     */
    public boolean insertBiSessionData(Context context, BiSessionData data) {
        BiLogUtils.d(TAG, "---insert---");
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.insert(BiDbContent.BiSessionTable.TABLE_NAME, sessionDataToMap(data));
    }

    /**
     * 插入多条数据
     *
     * @param data
     * @return
     */
    public boolean batchInsertBiSessionData(Context context, List<BiSessionData> data) {

        if (data == null || data.size() == 0) {
            return false;
        }
        //-----------------
        ContentValues[] valuesArr = new ContentValues[data.size()];
        //
        for (int i = 0; i < data.size(); i++) {
            ContentValues value = sessionDataToMap(data.get(i));
            valuesArr[i] = value;
        }
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.bulkInsert(BiDbContent.BiSessionTable.TABLE_NAME, valuesArr) >= 0;
    }

    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean deleteBiSessionData(Context context, Long timeId) {
        if (timeId < 0) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(BiDbContent.BiSessionTable.TIMEID);
        deleteSql.append(" =");
        deleteSql.append(timeId);
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiSessionTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }


    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean deleteUploadSessionData(Context context, Long timeStart) {
        if (timeStart < 0) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(BiDbContent.BiSessionTable.TIMEID);
        deleteSql.append(" <=");
        deleteSql.append(timeStart);
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiSessionTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }


    /**
     * 删除所有数据
     *
     * @return
     */
    public boolean batchDeleteBiSessionData(Context context) {
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiSessionTable.TABLE_NAME, null, null) >= 0;
        return deleteRet;
    }

    /**
     * 查询所有的数据(以testBiDbContent.testBiTable.INSERT_TIME降序排序)
     *
     * @return
     */
    public List<BiSessionData> queryBiSessionData(Context context) {
        List<BiSessionData> dataList = new ArrayList<BiSessionData>();
        ;
        //
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        Cursor cursor = mBiDbHelper.query(BiDbContent.BiSessionTable.TABLE_NAME, null, null, null, BiDbContent.BiSessionTable.TIMEID + " desc");
        //
        try {
            if (cursor != null) {
                //

                //
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    int timeid = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.TIMEID);
                    int sessionId = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.SESSIONID);
                    int uid = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.UID);
                    int token = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.TOKEN);
                    int cid = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.CID);
                    int mac = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.MAC);
                    int ip = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.IP);
                    int lon = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.LON);
                    int lat = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.LAT);
                    int network = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.NETWORK);
                    int operator = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.OPERATOR);
                    int os = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.OS);
                    int brand = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.BRAND);
                    int model = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.MODEL);
                    int hw = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.HW);
                    int gid = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.GID);
                    int idfa = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.IDFA);
                    int imei = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.IMEI);
                    int imsi = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.IMSI);
                    int app_version = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.APP_VERSION);
                    int cname = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.CNAME);
                    int cversion = cursor
                            .getColumnIndex(BiDbContent.BiSessionTable.CVERSION);
                    do {
                        BiSessionData data = new BiSessionData();
                        //
                        data.timeId = cursor.getLong(timeid);
                        data.sessionId = cursor.getString(sessionId);
                        data.uid = cursor.getString(uid);
                        data.token = cursor.getString(token);
                        data.cid = cursor.getString(cid);
                        data.mac = cursor.getString(mac);
                        data.ip = cursor.getString(ip);
                        data.lon = cursor.getString(lon);
                        data.lat = cursor.getString(lat);
                        data.network = cursor.getString(network);
                        data.operator = cursor.getString(operator);
                        data.os = cursor.getString(os);
                        data.brand = cursor.getString(brand);
                        data.model = cursor.getString(model);
                        data.hw = cursor.getString(hw);
                        data.gid = cursor.getString(gid);
                        data.idfa = cursor.getString(idfa);
                        data.imei = cursor.getString(imei);
                        data.imsi = cursor.getString(imsi);
                        data.appVersion = cursor.getString(app_version);
                        data.cname = cursor.getString(cname);
                        data.cversion = cursor.getString(cversion);
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
    public int getBiSessionDataCount(Context context) {
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        Cursor cursor = mBiDbHelper.query(BiDbContent.BiSessionTable.TABLE_NAME, null, null, null, null);
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
    public boolean updateBiSessionData(Context context, BiSessionData data) {
        //

        StringBuilder updateSql = new StringBuilder();
        updateSql.append(BiDbContent.BiSessionTable.TIMEID);
        updateSql.append(" ='");
        updateSql.append(data.timeId);
        updateSql.append("'");
        updateSql.append(";");
        //
        String sql = updateSql.toString();
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.update(BiDbContent.BiSessionTable.TABLE_NAME, sessionDataToMap(data), sql, null) > 0;
    }

    /**
     * 数据转为contentValues
     *
     * @param data
     * @return
     */
    private ContentValues sessionDataToMap(BiSessionData data) {
        ContentValues value = new ContentValues();
        value.put(BiDbContent.BiSessionTable.TIMEID, data.timeId);
        value.put(BiDbContent.BiSessionTable.SESSIONID, data.sessionId);
        value.put(BiDbContent.BiSessionTable.UID, data.uid);
        value.put(BiDbContent.BiSessionTable.TOKEN, data.token);
        value.put(BiDbContent.BiSessionTable.CID, data.cid);
        value.put(BiDbContent.BiSessionTable.MAC, data.mac);
        value.put(BiDbContent.BiSessionTable.IP, data.ip);
        value.put(BiDbContent.BiSessionTable.LON, data.lon);
        value.put(BiDbContent.BiSessionTable.LAT, data.lat);
        value.put(BiDbContent.BiSessionTable.NETWORK, data.network);
        value.put(BiDbContent.BiSessionTable.OPERATOR, data.operator);
        value.put(BiDbContent.BiSessionTable.OS, data.os);
        value.put(BiDbContent.BiSessionTable.BRAND, data.brand);
        value.put(BiDbContent.BiSessionTable.MODEL, data.model);
        value.put(BiDbContent.BiSessionTable.HW, data.hw);
        value.put(BiDbContent.BiSessionTable.GID, data.gid);
        value.put(BiDbContent.BiSessionTable.IDFA, data.idfa);
        value.put(BiDbContent.BiSessionTable.IMEI, data.imei);
        value.put(BiDbContent.BiSessionTable.IMSI, data.imsi);
        value.put(BiDbContent.BiSessionTable.APP_VERSION, data.appVersion);
        value.put(BiDbContent.BiSessionTable.CNAME, data.cname);
        value.put(BiDbContent.BiSessionTable.CVERSION, data.cversion);
        return value;
    }

    //#########################################testBiData的增删改查begin##########################################


    /**
     * 插入单条数据
     *
     * @param data
     * @return
     */
    public boolean insertBiData(Context context, BiData data) {
        BiLogUtils.d(TAG, "---insertBiData---");
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.insert(BiDbContent.BiTable.TABLE_NAME, biDataToMap(data));
    }

    /**
     * 插入多条数据
     *
     * @param data
     * @return
     */
    public boolean batchInsertBiData(Context context, List<BiData> data) {

        if (data == null || data.size() == 0) {
            return false;
        }
        //-----------------
        ContentValues[] valuesArr = new ContentValues[data.size()];
        //
        for (int i = 0; i < data.size(); i++) {
            ContentValues value = biDataToMap(data.get(i));
            valuesArr[i] = value;
        }
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.bulkInsert(BiDbContent.BiTable.TABLE_NAME, valuesArr) >= 0;
    }

    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean deleteBiData(Context context, Long timeId) {
        if (timeId < 0) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(BiDbContent.BiTable.TIMEID);
        deleteSql.append(" =");
        deleteSql.append(timeId);
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }

    /**
     * 删除特定数据
     *
     * @return
     */
    public boolean deleteUploadBiData(Context context, Long timeStart) {
        if (timeStart < 0) {
            return false;
        }
        //
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(BiDbContent.BiTable.TIMEID);
        deleteSql.append(" <=");
        deleteSql.append(timeStart);
        deleteSql.append(";");
        String sql = deleteSql.toString();
        //
        Log.i(TAG, "del sql:" + sql);
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiTable.TABLE_NAME, sql, null) >= 0;
        Log.i(TAG, "del flowers result:" + deleteRet);
        return deleteRet;
    }


    /**
     * 删除所有数据
     *
     * @return
     */
    public boolean batchDeleteBiData(Context context) {
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        boolean deleteRet = mBiDbHelper.delete(BiDbContent.BiTable.TABLE_NAME, null, null) >= 0;
        return deleteRet;
    }

    /**
     * 查询所有的数据(以testBiDbContent.testBiTable.INSERT_TIME降序排序)
     *
     * @return
     */
    public List<BiData> queryBiData(Context context) {
        List<BiData> dataList = new ArrayList<BiData>();
        ;
        //
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        Cursor cursor = mBiDbHelper.query(BiDbContent.BiTable.TABLE_NAME, null, null, null, BiDbContent.BiTable.TIMEID + " desc");
        //
        try {
            if (cursor != null) {
                //

                //
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    int timeid = cursor
                            .getColumnIndex(BiDbContent.BiTable.TIMEID);
                    int sessionId = cursor
                            .getColumnIndex(BiDbContent.BiTable.SESSIONID);
                    int uid = cursor
                            .getColumnIndex(BiDbContent.BiTable.UID);
                    int key = cursor
                            .getColumnIndex(BiDbContent.BiTable.KEY);
                    int parameters = cursor
                            .getColumnIndex(BiDbContent.BiTable.PARAMETERS);
                    int sequence = cursor
                            .getColumnIndex(BiDbContent.BiTable.SEQUENCE);
                    do {
                        BiData data = new BiData();
                        //
                        data.timeId = cursor.getLong(timeid);
                        data.sessionId = cursor.getString(sessionId);
                        data.uid = BiSerializeUtil.deserializeObject(cursor.getBlob(uid));
                        data.key = cursor.getString(key);
                        data.parameters = BiSerializeUtil.deserializeObject(cursor.getBlob(parameters));
                        data.sequence = cursor.getString(sequence);
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
    public int getBiDataCount(Context context) {
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        Cursor cursor = mBiDbHelper.query(BiDbContent.BiTable.TABLE_NAME, null, null, null, null);
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
    public boolean updateBiData(Context context, BiData data) {
        //

        StringBuilder updateSql = new StringBuilder();
        updateSql.append(BiDbContent.BiTable.TIMEID);
        updateSql.append(" ='");
        updateSql.append(data.timeId);
        updateSql.append("'");
        updateSql.append(";");
        //
        String sql = updateSql.toString();
        //-------------
        BiDbHelper mBiDbHelper = getBiDbHelper(context);
        return mBiDbHelper.update(BiDbContent.BiTable.TABLE_NAME, biDataToMap(data), sql, null) > 0;
    }

    /**
     * 数据转为contentValues
     *
     * @param data
     * @return
     */
    private ContentValues biDataToMap(BiData data) {
        ContentValues value = new ContentValues();
        value.put(BiDbContent.BiTable.TIMEID, data.timeId);
        value.put(BiDbContent.BiTable.SESSIONID, data.sessionId);
        value.put(BiDbContent.BiTable.UID, BiSerializeUtil.serializeObject(data.uid));
        value.put(BiDbContent.BiTable.KEY, data.key);
        value.put(BiDbContent.BiTable.PARAMETERS, BiSerializeUtil.serializeObject(data.parameters));
        value.put(BiDbContent.BiTable.SEQUENCE, data.sequence);
        return value;
    }


}
