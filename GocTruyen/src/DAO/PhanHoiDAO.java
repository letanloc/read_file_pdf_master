package DAO;

import java.util.ArrayList;
import java.util.List;

import model.PhanHoi;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHelper;

public class PhanHoiDAO {
	SQLiteDatabase db;
	
	public PhanHoiDAO(Activity activity) {
		db = new DbHelper(activity).getWritableDatabase();
	}
	
	public void insert(PhanHoi ph) {
		ContentValues values = new ContentValues();
//		values.put("maph", ph.MaPH);
		values.put("ten", ph.Ten);
		values.put("email", ph.Email);
		values.put("danhgia", ph.DanhGia);
		values.put("noidung", ph.NoiDung);
		
		db.insert("phanhoi", null, values);
	}
	
	public void update(PhanHoi ph) {
		ContentValues values = new ContentValues();
		values.put("maph", ph.MaPH);
		values.put("ten", ph.Ten);
		values.put("email", ph.Email);
		values.put("danhgia", ph.DanhGia);
		values.put("noidung", ph.NoiDung);
		
		db.update("phanhoi", values, "maph=?", new String[]{String.valueOf(ph.MaPH)});
	}
	
	public void delete(int maph) {
		db.delete("phanhoi", "maph=?", new String[]{String.valueOf(maph)});
	}
	

	public PhanHoi getPhanHoi(String maph) {
		String sql = "SELECT * FROM phanhoi WHERE maph=?";
		List<PhanHoi> list = getBySql(sql, maph);
		return list.get(0);
	}
	
	public List<PhanHoi> getPhanHoi() {
		String sql = "SELECT * FROM phanhoi";
		return getBySql(sql);
	}
	
	public List<PhanHoi> getBySql(String sql, String...args) {
		List<PhanHoi> list = new ArrayList<PhanHoi>();
		
		Cursor c = db.rawQuery(sql, args);
		while (c.moveToNext()){
			PhanHoi  ph = new PhanHoi();
			ph.MaPH = c.getInt(c.getColumnIndex("maph"));
			ph.Ten = c.getString(c.getColumnIndex("ten"));
			ph.Email = c.getString(c.getColumnIndex("email"));
			ph.DanhGia = c.getString(c.getColumnIndex("danhgia"));
			ph.NoiDung = c.getString(c.getColumnIndex("noidung"));

			list.add(ph);
		} 
		c.close();
		return list;
	}
}
