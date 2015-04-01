package DAO;

import java.util.ArrayList;
import java.util.List;

import model.TacGia;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHelper;

public class TacGiaDAO {
	SQLiteDatabase db;
	
	public TacGiaDAO(Activity activity) {
		db = new DbHelper(activity).getWritableDatabase();
	}
	
	public void insert(TacGia tg) {
		ContentValues values = new ContentValues();
		
		values.put("matg", tg.MaTG);
		values.put("tentg", tg.TenTG);
		
		db.insert("tacgia", null, values);
	}
	
	public void update(TacGia tg) {
		ContentValues values = new ContentValues();
		values.put("matg", tg.MaTG);
		values.put("tentg", tg.TenTG);
		
		db.update("tacgia", values, "matg=?", new String[]{tg.MaTG});
	}
	
	public void delete(String matg) {
		db.delete("tacgia", "matg=?", new String[]{matg});
	}
	

	public TacGia getTacGia(String matg) {
		String sql = "SELECT * FROM tacgia WHERE matg=?";
		List<TacGia> list = getBySql(sql, matg);
		return list.get(0);
	}
	
	public List<TacGia> getTacGia() {
		String sql = "SELECT * FROM tacgia";
		return getBySql(sql);
	}
	
	public List<TacGia> getBySql(String sql, String...args) {
		List<TacGia> list = new ArrayList<TacGia>();
		
		Cursor c = db.rawQuery(sql, args);
		while (c.moveToNext()){
			TacGia  tg = new TacGia();
			tg.MaTG = c.getString(c.getColumnIndex("matg"));
			tg.TenTG = c.getString(c.getColumnIndex("tentg"));

			list.add(tg);
		} 
		c.close();
		return list;
	}
}
