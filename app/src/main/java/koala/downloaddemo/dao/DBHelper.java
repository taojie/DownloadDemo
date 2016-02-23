package koala.downloaddemo.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taoxj on 16-2-22.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "download.db";
    private static final String CREAET_TABLE= "create table thread_info(id integer primary key autoincrement,thread_id integer,url text,start integer,end integer,finished integer)";
    private static final String DROP_TABLE= "drop table if exists thread_info";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
             db.execSQL(CREAET_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL(DROP_TABLE);
          db.execSQL(CREAET_TABLE);
    }
}
