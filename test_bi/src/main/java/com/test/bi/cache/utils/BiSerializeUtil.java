package com.test.bi.cache.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xueliangxia.
 */
public class BiSerializeUtil {

    /**
     * 序列化对象
     *
     * @throws IOException
     */
    public static byte[] serializeObject(Object object) {
        ByteArrayOutputStream saos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(saos);
            oos.writeObject(object);
            oos.flush();
            return saos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * 反序列化对象
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static Object deserializeObject(byte[] buf) {

        ByteArrayInputStream sais = new ByteArrayInputStream(buf);
        try {
            ObjectInputStream ois = new ObjectInputStream(sais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
