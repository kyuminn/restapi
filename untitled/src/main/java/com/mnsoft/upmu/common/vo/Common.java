package com.mnsoft.upmu.common.vo;

public class Common {

	private int startRow;
    private int endRow;
    private int rnum;
    private int perPageSize;
    private int tnum;
    private String locale;
    private String lang_user_id;

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getLang_user_id() {
		return lang_user_id;
	}
	public void setLang_user_id(String lang_user_id) {
		this.lang_user_id = lang_user_id;
	}
	public int getTnum() {
		return tnum;
	}
	public void setTnum(int tnum) {
		this.tnum = tnum;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public int getPerPageSize() {
		return perPageSize;
	}
	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}


}
