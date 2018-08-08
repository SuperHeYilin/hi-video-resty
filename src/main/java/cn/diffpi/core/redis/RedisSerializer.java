package cn.diffpi.core.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * @author LB 2017 8 14
 */
public class RedisSerializer implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static String charset = "UTF-8";

    /**
     * 序列化
     *
     * @param String 对象
     * @return byte[]
     * @throws UnsupportedEncodingException
     */
    public static byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes byte数据
     * @return String
     * @throws UnsupportedEncodingException
     */
    public static Object unserialize(byte[] bytes) throws UnsupportedEncodingException {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

}

