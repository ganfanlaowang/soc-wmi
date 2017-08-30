package com.mynawang.wmi.mq;

import java.io.*;

public class SerializableUtil {


    public static byte[] serialize(Object obj) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;

        try {
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            baos.flush();
            byte[] bytes = baos.toByteArray();

            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);

            return ois.readObject();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                bais.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
