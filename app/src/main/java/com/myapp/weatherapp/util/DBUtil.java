package com.myapp.weatherapp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.myapp.weatherapp.R;


/**
 * Created by wjh on 2015/10/28.
 */
public class DBUtil {
    private final int BUFFER_SIZE = 1024;
    public static final String DB_NAME = "city.db";
    public static final String PACKAGE_NAME = "com.myapp.weatherapp";
    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"+ PACKAGE_NAME;
    private static DBUtil instance = null;
    private static SQLiteDatabase database;
    private Context context;
    private File file=null;

    private DBUtil(Context context) {
        this.context = context;
    }

    public static void initDB(Context context){
          if (instance == null)
              instance = new DBUtil(context);
    }
    public static DBUtil getDB(){
       return instance;
    }
    public void openDatabase() {
        database = openDatabase(DB_PATH + "/" + DB_NAME);
    }
    public static SQLiteDatabase getDatabase(){
        return database;
    }

    private SQLiteDatabase openDatabase(String dbfile) {
        try {
            file = new File(dbfile);
            if (!file.exists()) {
                InputStream is = context.getResources().openRawResource(R.raw.city);
                FileOutputStream fos = new FileOutputStream(dbfile);

                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count =is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                    fos.flush();
                }
                fos.close();
                is.close();
            }
            database = SQLiteDatabase.openOrCreateDatabase(dbfile,null);
            return database;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
        }
        return null;
    }
    public void closeDatabase() {
        if(database!=null)
            database.close();
    }
}
