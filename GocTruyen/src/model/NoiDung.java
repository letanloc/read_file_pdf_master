package model;

import java.io.Serializable;

public class NoiDung implements Serializable{
	public int MaND;
	public String TenTruyen;
	public int Hinh;
	public int File;
	public String MaTL;
	public String MaTG;
	public String QuocGia;
	
	
	
	public int getMaND() {
		return MaND;
	}
	public void setMaND(int maND) {
		MaND = maND;
	}
	public String getTenTruyen() {
		return TenTruyen;
	}
	public void setTenTruyen(String tenTruyen) {
		TenTruyen = tenTruyen;
	}
	public int getFile() {
		return File;
	}
	public void setFile(int file) {
		File = file;
	}
	public String getMaTL() {
		return MaTL;
	}
	public void setMaTL(String maTL) {
		MaTL = maTL;
	}
	public String getMaTG() {
		return MaTG;
	}
	public void setMaTG(String maTG) {
		MaTG = maTG;
	}
	public String getQuocGia() {
		return QuocGia;
	}
	public void setQuocGia(String quocGia) {
		QuocGia = quocGia;
	}
	public int getHinh() {
		return Hinh;
	}
	public void setHinh(int hinh) {
		Hinh = hinh;
	}
	
	
}

