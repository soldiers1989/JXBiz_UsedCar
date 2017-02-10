package com.etong.android.frame.widget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxiqing on 2016/10/12.
 */
public class Etong_SqlLiteDao {
    private static Etong_SqlLiteDao instance;

    private static SQLiteOpenHelper dbHandler;

    private Boolean DBLock = false;

    private String sql;
    private String mName1,mName2;

    public static Etong_SqlLiteDao getInstance(Context context, String name1, String name2) {

        if (instance == null) {
            instance = new Etong_SqlLiteDao(context, name1,name2);
        }
        return instance;
    }

    private Etong_SqlLiteDao(Context context, String name1, String name2) {

        this.mName1 = name1;
        this.mName2 = name2;
        dbHandler = new SQLiteOpenHelper(context.getApplicationContext(), "db_data", null, 1) {

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                  int newVersion) {
                db.execSQL("drop if table exists "+mName1);
                db.execSQL("drop if table exists  "+mName2);
                onCreate(db);

            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                sql = "create table "+mName1+"(sid integer primary key autoincrement," +
                        "id varchar(200)," +
                        "name varchar(200)," +
                        "type varchar(200))";

                db.execSQL(sql);

                sql = "create table "+mName2+"(sid integer primary key autoincrement," +
                        "id varchar(200)," +
                        "name varchar(200) unique," +
                        "type varchar(200))";

                db.execSQL(sql);


            }
        };
    }

//    t_list

    /**
     * 插入记录(异步任务，数据库事务提交)
     */
    public void insert(JSONArray array, final String name) {
        if (array == null || array.size() == 0) {
            return;
        }
        DBLock = true;

                SQLiteDatabase db = dbHandler.getWritableDatabase();
                db.execSQL("delete from "+name);//清空表数据
                db.execSQL("update sqlite_sequence SET seq = 0 where name ='"+name+"'");//自增长ID归零
                String sql = "insert into "+name+" (id,name,type)";
                try {
                    db.beginTransaction(); // 手动设置开始事务
                    for (Object o : array) {
                        JSONObject root = (JSONObject) o;
                        UC_BrandCarset data = JSON.toJavaObject(root, UC_BrandCarset.class);
                        if("0".equals(data.getId())){
                            continue;
                        }
                        db.execSQL(sql + "values('" + data.getId() + "','" + data.getName() + "','" + data.getType() + "')");
                    }
                    db.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
                } catch (SQLException e) {
                    e.printStackTrace();
                    L.e(e.toString(),"SQLException");
                } finally {
                    db.endTransaction(); // 处理完成
                    db.close();
                    DBLock = false;
                }
    }

    //删除某条数据
    public void delete(String tableName,String name){
        SQLiteDatabase db = null;
        try {
            db = dbHandler.getWritableDatabase();
            db.delete(tableName, "name = ?", new String[]{name});

        } catch (Exception e) {

        } finally {
            db.close();
        }


    }

    /**
     * 模糊匹配
     */
    public List<UC_BrandCarset> find(String str,String name,int order) {
        if(DBLock){
            L.e("","数据库访问锁定，无法读取");
            return null;
        }
        List<UC_BrandCarset> lists = new ArrayList<>();
        UC_BrandCarset r = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        if(order == 0){
            sql = "select * from "+name+" where name like '%" + str + "%'";
        }else{
            sql = "select * from "+name+" where name like '%" + str + "%' order by sid desc limit 0,5" ;
        }

        // 用游标Cursor接收从数据库检索到的数据
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            r = new UC_BrandCarset();
            r.setSid(cursor.getInt(cursor.getColumnIndex("sid")));
            r.setId(cursor.getString(cursor.getColumnIndex("id")));
            r.setName(cursor.getString(cursor.getColumnIndex("name")));
            r.setType(cursor.getString(cursor
                    .getColumnIndex("type")));
            lists.add(r);
        }
        cursor.close();
        db.close();
        return lists;
    }

    /**
     * 存储数据
     */

    public void save(UC_BrandCarset car,String name){

        List<UC_BrandCarset> mSets = find("", name, 0); //查询到所有的历史记录

        if (mSets != null && !mSets.isEmpty() && mSets.size() > 5) {

            for (UC_BrandCarset set : mSets) {

                if (car.getName().equals(set.getName())) {
                    delete(name,car.getName());
                }
            }
        }

        SQLiteDatabase db = null;
        try {
            db = dbHandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("id", car.getId());
            values.put("name", car.getName());
            values.put("type", car.getType());

            db.insert(name,null, values);

        } catch (Exception e) {

            L.d("hot",e.toString());

        } finally {
            db.close();
        }

    }

    public void deleteHistory(String name){

        SQLiteDatabase db = null;
        try{
            db = dbHandler.getWritableDatabase();
            db.execSQL("DELETE FROM "+name);

        }catch (Exception e){

        }finally {
            db.close();
        }

//        db.delete(name, "sid < ?", new String[]{"1000"});


    }
}
