package com.netCracker.utils;

import java.io.*;
import java.util.List;

public class SerializationUtils {

    public static boolean writeEntity(List list, String fileName) {
        boolean flag = false;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public static List readEntity(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream oin = null;
        List list = null;
        try {
            fis = new FileInputStream(fileName);
            oin = new ObjectInputStream(fis);
            list = (List) oin.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("File not created");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    /*NOP*/
                }
            }
            if (oin != null) {
                try {
                    oin.close();
                } catch (IOException e) {
                    /*NOP*/
                }
            }
        }
        return list;

    }
}
