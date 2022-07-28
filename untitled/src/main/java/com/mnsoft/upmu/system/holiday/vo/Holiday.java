package com.mnsoft.upmu.system.holiday.vo;

import java.util.List;

public class Holiday {
	private String doc_no = "";
	private String user_id = "";
	private String dept_cd = "";
	private String pos_cd = "";
	private String req_dt = "";
	private String appr_no = "";
	private String file_no = "";
	private String edit_dt = "";
	private String edit_user_id = "";

	private String doc_seq = "";
	private String holi_type = "";
	private String strt_ymd = "";
	private String fnsh_ymd = "";
	private String aftr_req_resn = "";

	private String sts_cd = "";
	private String user_nm = "";
	private String dept_nm = "";
	private String pos_nm = "";
	private String sts_nm = "";

	private String row_num = "";
	private String edit_user_nm = "";

	private String ymd_info = "";

	private List<?> fileList = null;

	private String appr_doc_url = "";

	private String dt_enter = "";
	private String dy_ytot = "";
	private String dy_ytot_cnt = "";
	private String dy_yplus = "";
	private String dy_yplus_cnt = "";
	private String dy_holiday = "";
	private String dy_huse = "";
	private String holi_year = "";

	private String holi_day_type = "";

	private String diff_date = "";

	private String appr_no_fin = "";

	private String att_type = "";

	private String holi_date = "";

	private String chk_type = "";

	private String holi_type_cd = "";
	
	private String sts_dt = "";

	public String getChk_type() {
		return chk_type;
	}

	public void setChk_type(String chk_type) {
		this.chk_type = chk_type;
	}

	public String getHoli_date() {
		return holi_date;
	}

	public void setHoli_date(String holi_date) {
		this.holi_date = holi_date;
	}

	public String getAtt_type() {
		return att_type;
	}

	public void setAtt_type(String att_type) {
		this.att_type = att_type;
	}

	public String getAppr_no_fin() {
		return appr_no_fin;
	}

	public void setAppr_no_fin(String appr_no_fin) {
		this.appr_no_fin = appr_no_fin;
	}

	public String getDiff_date() {
		return diff_date;
	}

	public void setDiff_date(String diff_date) {
		this.diff_date = diff_date;
	}

	public String getHoli_day_type() {
		return holi_day_type;
	}

	public void setHoli_day_type(String holi_day_type) {
		this.holi_day_type = holi_day_type;
	}

	public String getDt_enter() {
		return dt_enter;
	}

	public void setDt_enter(String dt_enter) {
		this.dt_enter = dt_enter;
	}

	public String getDy_ytot() {
		return dy_ytot;
	}

	public void setDy_ytot(String dy_ytot) {
		this.dy_ytot = dy_ytot;
	}

	public String getDy_ytot_cnt() {
		return dy_ytot_cnt;
	}

	public void setDy_ytot_cnt(String dy_ytot_cnt) {
		this.dy_ytot_cnt = dy_ytot_cnt;
	}

	public String getDy_yplus() {
		return dy_yplus;
	}

	public void setDy_yplus(String dy_yplus) {
		this.dy_yplus = dy_yplus;
	}

	public String getDy_yplus_cnt() {
		return dy_yplus_cnt;
	}

	public void setDy_yplus_cnt(String dy_yplus_cnt) {
		this.dy_yplus_cnt = dy_yplus_cnt;
	}

	public String getDy_holiday() {
		return dy_holiday;
	}

	public void setDy_holiday(String dy_holiday) {
		this.dy_holiday = dy_holiday;
	}

	public String getDy_huse() {
		return dy_huse;
	}

	public void setDy_huse(String dy_huse) {
		this.dy_huse = dy_huse;
	}

	public String getHoli_year() {
		return holi_year;
	}

	public void setHoli_year(String holi_year) {
		this.holi_year = holi_year;
	}

	public String getAppr_doc_url() {
		return appr_doc_url;
	}

	public void setAppr_doc_url(String appr_doc_url) {
		this.appr_doc_url = appr_doc_url;
	}

	public List<?> getFileList() {
		return fileList;
	}

	public void setFileList(List<?> fileList) {
		this.fileList = fileList;
	}

	public String getYmd_info() {
		return ymd_info;
	}

	public void setYmd_info(String ymd_info) {
		this.ymd_info = ymd_info;
	}

	public String getEdit_user_nm() {
		return edit_user_nm;
	}

	public void setEdit_user_nm(String edit_user_nm) {
		this.edit_user_nm = edit_user_nm;
	}

	public String getRow_num() {
		return row_num;
	}

	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}

	public String getPos_nm() {
		return pos_nm;
	}

	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}

	public String getSts_nm() {
		return sts_nm;
	}

	public void setSts_nm(String sts_nm) {
		this.sts_nm = sts_nm;
	}

	public String getSts_cd() {
		return sts_cd;
	}

	public void setSts_cd(String sts_cd) {
		this.sts_cd = sts_cd;
	}

	public String getDoc_seq() {
		return doc_seq;
	}

	public void setDoc_seq(String doc_seq) {
		this.doc_seq = doc_seq;
	}

	public String getHoli_type() {
		return holi_type;
	}

	public void setHoli_type(String holi_type) {
		this.holi_type = holi_type;
	}

	public String getStrt_ymd() {
		return strt_ymd;
	}

	public void setStrt_ymd(String strt_ymd) {
		this.strt_ymd = strt_ymd;
	}

	public String getFnsh_ymd() {
		return fnsh_ymd;
	}

	public void setFnsh_ymd(String fnsh_ymd) {
		this.fnsh_ymd = fnsh_ymd;
	}

	public String getAftr_req_resn() {
		return aftr_req_resn;
	}

	public void setAftr_req_resn(String aftr_req_resn) {
		this.aftr_req_resn = aftr_req_resn;
	}

	public String getDoc_no() {
		return doc_no;
	}

	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDept_cd() {
		return dept_cd;
	}

	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}

	public String getPos_cd() {
		return pos_cd;
	}

	public void setPos_cd(String pos_cd) {
		this.pos_cd = pos_cd;
	}

	public String getReq_dt() {
		return req_dt;
	}

	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
	}

	public String getAppr_no() {
		return appr_no;
	}

	public void setAppr_no(String appr_no) {
		this.appr_no = appr_no;
	}

	public String getFile_no() {
		return file_no;
	}

	public void setFile_no(String file_no) {
		this.file_no = file_no;
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

	public String getHoli_type_cd() {
		return holi_type_cd;
	}

	public void setHoli_type_cd(String holi_type_cd) {
		this.holi_type_cd = holi_type_cd;
	}

	public String getSts_dt() {
		return sts_dt;
	}

	public void setSts_dt(String sts_dt) {
		this.sts_dt = sts_dt;
	}
}
