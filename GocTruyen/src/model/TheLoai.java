package model;

import java.io.Serializable;

public class TheLoai implements Serializable{
	public String MaTL;
	public int HinhTL;
	public String TenTL;
	
	
	public String getMaTL() {
		return MaTL;
	}
	public void setMaTL(String maTL) {
		MaTL = maTL;
	}
	public String getTenTL() {
		return TenTL;
	}
	public void setTenTL(String tenTL) {
		TenTL = tenTL;
	}
	public int getHinhTL() {
		return HinhTL;
	}
	public void setHinhTL(int hinhTL) {
		HinhTL = hinhTL;
	}
	
}
