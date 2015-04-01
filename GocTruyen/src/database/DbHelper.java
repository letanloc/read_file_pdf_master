package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "TruyenTranh";
	public static final int DB_VERSION = 1;
	
	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String theLoai_createTable = 
				"CREATE TABLE theloai (" +
					"matl TEXT PRIMARY KEY,"+
					"hinhtl TEXT NOT NULL," +
					"tentl TEXT NOT NULL)";
		db.execSQL(theLoai_createTable);
		
		String tacGia_createTable = 
				"CREATE TABLE tacgia (" +
					"matg TEXT PRIMARY KEY," +
					"tentg TEXT NOT NULL)";
		db.execSQL(tacGia_createTable);
		
		String noiDung_createTable = 
				"CREATE TABLE noidung (" +
					"mand INTEGER  PRIMARY KEY," +
					"tentruyen TEXT NOT NULL,"+
					"hinh TEXT NOT NULL," +
					"file TEXT NOT NULL," +
					"matl TEXT NOT NULL REFERENCES tentl(matl)," +
					"matg TEXT NOT NULL	REFERENCES tentg(matg)," +
					"quocgia TEXT)";
		db.execSQL(noiDung_createTable);
		
		String phanHoi_createTable = 
				"CREATE TABLE phanhoi (" +
				"maph INTEGER PRIMARY KEY," +
				"ten TEXT NOT NULL," +
				"email TEXT," +
				"danhgia TEXT NOT NULL," +
				"noidung TEXT NOT NULL)";
		db.execSQL(phanHoi_createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String theLoai_createTable = "DROP TABLE IF EXISTS theloai";
		db.execSQL(theLoai_createTable);
		
		String tacGia_createTable = "DROP TABLE IF EXISTS tacgia";
		db.execSQL(tacGia_createTable);
		
		String noiDung_createTable = "DROP TABLE IF EXISTS noidung";
		db.execSQL(noiDung_createTable);
		
		String phanHoi_createTable = "DROP TABLE IF EXISTS phanhoi";
		db.execSQL(phanHoi_createTable);
		
		onCreate(db);
	}
}