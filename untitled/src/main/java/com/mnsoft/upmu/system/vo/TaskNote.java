package com.mnsoft.upmu.system.vo;

import java.util.List;

public class TaskNote {

	private String task_cd       = "";
	private String nt_content    = "";
	private String file_id       = "";
	private String edit_user_id  = "";
	private String edit_dt       = "";
	private String lang          = "";
	private String etc1          = "";
	private List<?> fileList        = null;
	public String getTask_cd() {
		return task_cd;
	}
	public void setTask_cd(String task_cd) {
		this.task_cd = task_cd;
	}
	public String getNt_content() {
		return nt_content;
	}
	public void setNt_content(String nt_content) {
		this.nt_content = nt_content;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getEdit_user_id() {
		return edit_user_id;
	}
	public void setEdit_user_id(String edit_user_id) {
		this.edit_user_id = edit_user_id;
	}
	public String getEdit_dt() {
		return edit_dt;
	}
	public void setEdit_dt(String edit_dt) {
		this.edit_dt = edit_dt;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getEtc1() {
		return etc1;
	}
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}
	public List<?> getFileList() {
		return fileList;
	}
	public void setFileList(List<?> fileList) {
		this.fileList = fileList;
	}
	
	


}