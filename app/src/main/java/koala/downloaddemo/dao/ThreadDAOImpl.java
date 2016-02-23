package koala.downloaddemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import koala.downloaddemo.entity.ThreadInfo;

/**
 * Created by taoxj on 16-2-22.
 */
public class ThreadDAOImpl implements ThreadDAO {
    private Context context;
    private  DBHelper dbHelper = null;
    public ThreadDAOImpl(Context ctx){
        this.context = ctx;
        dbHelper = new DBHelper(context);
    }
    @Override
    public void insertThread(ThreadInfo info) {
       SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
                new Object[]{info.getId(),info.getUrl(),info.getStart(),info.getEnd(),info.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{url,thread_id});
        db.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished,url,thread_id});
        db.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ?", new String[]{url});
        List<ThreadInfo> result = new ArrayList<ThreadInfo>();
        while(cursor.moveToNext()){
            ThreadInfo info = new ThreadInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            info.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            info.setUrl(url);
            result.add(info);
        }
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public boolean isExist(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, Integer.toString(thread_id)});
        if(cursor.moveToNext()){
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }
}
