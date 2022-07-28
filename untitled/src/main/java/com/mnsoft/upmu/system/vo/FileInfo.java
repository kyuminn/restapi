package com.mnsoft.upmu.system.vo;

import java.util.Map;

public class FileInfo {

	
	private String file_id          = "";
	private String file_seq         = "";
	private String org_file_name    = "";
	private String mfy_file_name    = "";
	private String file_size        = "";
	private String file_fold        = "";
	private String edit_dt          = "";
	private String edit_user_id     = "";
	private String p_file_id     	= "";
	private String file_size_tot    = "";
	private String file_ext    		= "";
	private String type    			= "";
	
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public String getFile_size_tot() {
		return file_size_tot;
	}
	public void setFile_size_tot(String file_size_tot) {
		this.file_size_tot = file_size_tot;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getFile_seq() {
		return file_seq;
	}
	public void setFile_seq(String file_seq) {
		this.file_seq = file_seq;
	}
	public String getOrg_file_name() {
		return org_file_name;
	}
	public void setOrg_file_name(String org_file_name) {
		this.org_file_name = org_file_name;
	}
	public String getMfy_file_name() {
		return mfy_file_name;
	}
	public void setMfy_file_name(String mfy_file_name) {
		this.mfy_file_name = mfy_file_name;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getFile_fold() {
		return file_fold;
	}
	public void setFile_fold(String file_fold) {
		this.file_fold = file_fold;
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
	public String getP_file_id() {
		return p_file_id;
	}
	public void setP_file_id(String p_file_id) {
		this.p_file_id = p_file_id;
	}
	public Map<String, Object> createMap() {
		// TODO Auto-generated method stub
		return null;
	}

}
