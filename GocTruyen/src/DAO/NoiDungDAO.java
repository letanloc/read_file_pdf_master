package DAO;

import java.util.ArrayList;
import java.util.List;

import model.NoiDung;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import database.DbHelper;

public class NoiDungDAO {
	SQLiteDatabase db;
	
	public NoiDungDAO(Activity activity) {
		db = new DbHelper(activity).getWritableDatabase();
	}
	
	public void insert(NoiDung nd) {
		ContentValues values = new ContentValues();
		
		values.put("tentruyen", nd.TenTruyen);
		values.put("file", nd.File);
		values.put("hinh", nd.Hinh);
		values.put("matl", nd.MaTL);
		values.put("matg", nd.MaTG);
		values.put("quocgia", nd.QuocGia);
		
		db.insert("noidung", null, values);
	}
	
	public void update(NoiDung nd) {
		ContentValues values = new ContentValues();
		
		values.put("tentruyen", nd.TenTruyen);
		values.put("file", nd.File);
		values.put("hinh", nd.Hinh);
		values.put("matl", nd.MaTL);
		values.put("matg", nd.MaTG);
		values.put("quocgia", nd.QuocGia);
		
		db.update("noidung", values, "mand=?", new String[]{String.valueOf(nd.MaND)});
	}
	
	public void delete(int mand) {
		db.delete("noidung", "mand=?", new String[]{String.valueOf(mand)});
	}
	
	public NoiDung getNoiDung(int mand) {
		String sql = "SELECT * FROM noidung WHERE mand=?";
		List<NoiDung> list = getByTheLoai(sql);
		return list.get(0);
	}
	
	public List<NoiDung> getNoiDung() {
//		String sql = "SELECT * FROM noidung";
		return getByTheLoai("%");
	}

	public List<NoiDung> getByTheLoai(String matl) {
		List<NoiDung> list = new ArrayList<NoiDung>();
		
		String sql = "SELECT * FROM noidung WHERE matl LIKE ?";
		Cursor c = db.rawQuery(sql,new String[]{matl});
		while (c.moveToNext()){
			NoiDung nd = new NoiDung();
			nd.MaND = c.getInt(c.getColumnIndex("mand"));
			nd.TenTruyen = c.getString(c.getColumnIndex("tentruyen"));
			nd.File = c.getInt(c.getColumnIndex("file"));
			nd.Hinh = c.getInt(c.getColumnIndex("hinh"));
			nd.MaTL = c.getString(c.getColumnIndex("matl"));
			nd.MaTG = c.getString(c.getColumnIndex("matg"));
			nd.QuocGia = c.getString(c.getColumnIndex("quocgia"));
			
			list.add(nd);
		} 
		c.close();
		return list;
	}
}

