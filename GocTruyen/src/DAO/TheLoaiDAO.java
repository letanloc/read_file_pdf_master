package DAO;

import java.util.ArrayList;
import java.util.List;

import model.TheLoai;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHelper;

public class TheLoaiDAO {
	SQLiteDatabase db;
	
	public TheLoaiDAO(Activity activity) {
		db = new DbHelper(activity).getWritableDatabase();
	}
	
	public void insert(TheLoai tl) {
		ContentValues values = new ContentValues();
		
		values.put("matl", tl.MaTL);
		values.put("hinhtl", tl.HinhTL);
		values.put("tentl", tl.TenTL);
		
		db.insert("theloai", null, values);
	}
	
	public void update(TheLoai tl) {
		ContentValues values = new ContentValues();
		
		values.put("matl", tl.MaTL);
		values.put("hinhtl", tl.HinhTL);
		values.put("tentl", tl.TenTL);
		
		db.update("theloai", values, "matl=?", new String[]{tl.MaTL});
	}
	
	public void delete(String matl) {
		db.delete("theloai", "matl=?", new String[]{matl});
	}
	

	public TheLoai getTheLoai(String matl) {
		String sql = "SELECT * FROM theloai WHERE matl=?";
		List<TheLoai> list = getBySql(sql, matl);
		return list.get(0);
	}
	
	public List<TheLoai> getTheLoai() {
		String sql = "SELECT * FROM theloai";
		return getBySql(sql);
	}
	
	public List<TheLoai> getBySql(String sql, String...args) {
		List<TheLoai> list = new ArrayList<TheLoai>();
		
		Cursor c = db.rawQuery(sql, args);
		while (c.moveToNext()){
			TheLoai  tl = new TheLoai();
			tl.MaTL = c.getString(c.getColumnIndex("matl"));
			tl.HinhTL = c.getInt(c.getColumnIndex("hinhtl"));
			tl.TenTL = c.getString(c.getColumnIndex("tentl"));

			list.add(tl);
		} 
		c.close();
		return list;
	}
}
