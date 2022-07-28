package com.mnsoft.upmu.system.holiday.vo;

import com.mnsoft.upmu.common.vo.Common;

public class HolidayCode extends Common{
	
	private String att_code        = "";
	private String att_nm          = "";
	private String ord             = "";
	private String use_yn          = "";
	private String edit_dt         = "";
	private String edit_user_id    = "";
	private String att_type        = "";
	private String att_nm_en       = "";
	private String chk_type        = "";
	
	public String getChk_type() {
		return chk_type;
	}
	public void setChk_type(String chk_type) {
		this.chk_type = chk_type;
	}
	public String getAtt_nm_en() {
		return att_nm_en;
	}
	public void setAtt_nm_en(String att_nm_en) {
		this.att_nm_en = att_nm_en;
	}
	public String getAtt_type() {
		return att_type;
	}
	public void setAtt_type(String att_type) {
		this.att_type = att_type;
	}
	public String getAtt_code() {
		return att_code;
	}
	public void setAtt_code(String att_code) {
		this.att_code = att_code;
	}
	public String getAtt_nm() {
		return att_nm;
	}
	public void setAtt_nm(String att_nm) {
		this.att_nm = att_nm;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getEdit_dt() {
		return edit_dt;
	}
	public void setEdit_dt(String edit_dt) {
		this.edit_dt = edit_dt;
	}
	public String getEdit_user_id() {
		return edit_user_id;
	}
	public void setEdit_user_id(String edit_user_id) {
		this.edit_user_id = edit_user_id;
	}
	
	
}
