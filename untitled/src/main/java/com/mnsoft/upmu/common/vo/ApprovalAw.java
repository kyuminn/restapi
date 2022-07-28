package com.mnsoft.upmu.common.vo;

public class ApprovalAw {
	private String title     = "";
	private String req_user  = "";
	private String req_dt    = "";
	private String cur_step  = "";
	private String tot_step  = "";
	private String cur_appr_user  = "";
	private String appr_url  = "";
	private String appr_no   = "";
	
	public String getAppr_no() {
		return appr_no;
	}
	public void setAppr_no(String appr_no) {
		this.appr_no = appr_no;
	}
	public String getCur_appr_user() {
		return cur_appr_user;
	}
	public void setCur_appr_user(String cur_appr_user) {
		this.cur_appr_user = cur_appr_user;
	}
	public String getAppr_url() {
		return appr_url;
	}
	public void setAppr_url(String appr_url) {
		this.appr_url = appr_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getReq_user() {
		return req_user;
	}
	public void setReq_user(String req_user) {
		this.req_user = req_user;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
	}
	public String getCur_step() {
		return cur_step;
	}
	public void setCur_step(String cur_step) {
		this.cur_step = cur_step;
	}
	public String getTot_step() {
		return tot_step;
	}
	public void setTot_step(String tot_step) {
		this.tot_step = tot_step;
	}

	
}
