package com.netCracker.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonFileUtils {
    public static boolean writeEntity(Object entity, String fileName) {
        boolean flag = false;
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(fileName, true));
            Gson gson = new Gson();

            buf.append(gson.toJson(entity));
            buf.newLine();
            buf.flush();
            flag = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static List<Object> getAllEntitys(String fileName, Class clazz) {
        List<Object> list = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            Gson gson = new Gson();

            String line = buf.readLine();
            while (line != null) {
                Object object = gson.fromJson(line, clazz);
                list.add(object);
                line = buf.readLine();
            }
            buf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean rewriteFile(List list,String fileName){
        boolean flag = false;
        try {File temp = new File("json/temp.txt");
            File file = new File(fileName);

            BufferedWriter buf = new BufferedWriter(new FileWriter(temp));
            Gson gson = new Gson();

            for (Object obj : list){
                buf.append(gson.toJson(obj));
                buf.newLine();
            }
            buf.close();
            flag = temp.renameTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
