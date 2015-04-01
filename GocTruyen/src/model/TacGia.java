package model;

import java.io.Serializable;

public class TacGia implements Serializable{
	public String MaTG;
	public String TenTG;
	public String getMaTG() {
		return MaTG;
	}
	public void setMaTG(String maTG) {
		MaTG = maTG;
	}
	public String getTenTG() {
		return TenTG;
	}
	public void setTenTG(String tenTG) {
		TenTG = tenTG;
	}
	
	
}

