package com.mnsoft.upmu.common.vo;

import java.util.List;

import com.mnsoft.upmu.common.vo.Common;

public class ErpExpenditureResolution extends Common{

	private String doc_no                = "";
	private String doc_seq               = "";
	private String dept_cd               = "";
	private String dept_id               = "";
	private String dept_nm               = "";
	private String pos_cd             	 = "";
	private String pos_nm             	 = "";
	private String user_id               = "";
	private String user_nm               = "";
	private String from_ymd				 = "";
	private String to_ymd                = "";
	private String sts_cd          		 = "";
	private String sts_nm          		 = "";
	private String req_dt              	 = "";
	private String edit_dt             	 = "";
	private String edit_user_id          = ""; 
	private String edit_user_nm          = "";
	private String appr_no          	 = "";
	private String file_no				 = "";
	private String seq				 	 = "";
	private String use_yn				 = "";
	private String ord					 = "";
	private String er_cd			 	 = "";
	private String er_nm			 	 = "";
	private String hid_cr_cd			 = "";
	private String hid_cr_nm			 = "";
	private String public_yn			 = "";
	private String gubun      		 	 = "";
	private String up_in      		 	 = "";
	private String contents       		 = "";
	private String contents1       		 = "";
	private String contents2      		 = "";
	private String contents3       		 = "";
	private String contents4       		 = "";
	private String contents5       		 = "";
	private String amount                = "";
	private String cd_company            = "";
	private String cd_pc                 = "";
	private String appr_ttl              = "";
	private String appr_sts_cd           = "";
	private String appr_sts_nm           = "";
	private String app_form_knd          = "";
	private String count		          = "";
	
	private String no_docu         	 	 = "";
	private String id_write         	 = "";
	private String dt_acct         		 = "";
	private String nm_pumm        		 = "";
	private String st_stat_cd      		 = "";
	private String st_stat_nm      		 = "";
	
	private List<?> fileList      = null;
	private String appr_doc_url   = "";
	private String appr_no_fin    = "";
	
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public String getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(String doc_seq) {
		this.doc_seq = doc_seq;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String dept_cd) {
		this.dept_cd = dept_cd;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getPos_cd() {
		return pos_cd;
	}
	public void setPos_cd(String pos_cd) {
		this.pos_cd = pos_cd;
	}
	public String getPos_nm() {
		return pos_nm;
	}
	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getFrom_ymd() {
		return from_ymd;
	}
	public void setFrom_ymd(String from_ymd) {
		this.from_ymd = from_ymd;
	}
	public String getTo_ymd() {
		return to_ymd;
	}
	public void setTo_ymd(String to_ymd) {
		this.to_ymd = to_ymd;
	}
	public String getSts_cd() {
		return sts_cd;
	}
	public void setSts_cd(String sts_cd) {
		this.sts_cd = sts_cd;
	}
	public String getSts_nm() {
		return sts_nm;
	}
	public void setSts_nm(String sts_nm) {
		this.sts_nm = sts_nm;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
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
	public String getEdit_user_nm() {
		return edit_user_nm;
	}
	public void setEdit_user_nm(String edit_user_nm) {
		this.edit_user_nm = edit_user_nm;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getOrd() {
		return ord;
	}
	public void setOrd(String ord) {
		this.ord = ord;
	}
	public String getEr_cd() {
		return er_cd;
	}
	public void setEr_cd(String er_cd) {
		this.er_cd = er_cd;
	}
	public String getEr_nm() {
		return er_nm;
	}
	public void setEr_nm(String er_nm) {
		this.er_nm = er_nm;
	}
	public String getHid_cr_cd() {
		return hid_cr_cd;
	}
	public void setHid_cr_cd(String hid_cr_cd) {
		this.hid_cr_cd = hid_cr_cd;
	}
	public String getHid_cr_nm() {
		return hid_cr_nm;
	}
	public void setHid_cr_nm(String hid_cr_nm) {
		this.hid_cr_nm = hid_cr_nm;
	}
	public String getPublic_yn() {
		return public_yn;
	}
	public void setPublic_yn(String public_yn) {
		this.public_yn = public_yn;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getUp_in() {
		return up_in;
	}
	public void setUp_in(String up_in) {
		this.up_in = up_in;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCd_company() {
		return cd_company;
	}
	public void setCd_company(String cd_company) {
		this.cd_company = cd_company;
	}
	public String getCd_pc() {
		return cd_pc;
	}
	public void setCd_pc(String cd_pc) {
		this.cd_pc = cd_pc;
	}
	public String getAppr_ttl() {
		return appr_ttl;
	}
	public void setAppr_ttl(String appr_ttl) {
		this.appr_ttl = appr_ttl;
	}
	public String getAppr_sts_cd() {
		return appr_sts_cd;
	}
	public void setAppr_sts_cd(String appr_sts_cd) {
		this.appr_sts_cd = appr_sts_cd;
	}
	public String getAppr_sts_nm() {
		return appr_sts_nm;
	}
	public void setAppr_sts_nm(String appr_sts_nm) {
		this.appr_sts_nm = appr_sts_nm;
	}
	public String getApp_form_knd() {
		return app_form_knd;
	}
	public void setApp_form_knd(String app_form_knd) {
		this.app_form_knd = app_form_knd;
	}
	public String getId_write() {
		return id_write;
	}
	public void setId_write(String id_write) {
		this.id_write = id_write;
	}
	public String getDt_acct() {
		return dt_acct;
	}
	public void setDt_acct(String dt_acct) {
		this.dt_acct = dt_acct;
	}
	public String getNm_pumm() {
		return nm_pumm;
	}
	public void setNm_pumm(String nm_pumm) {
		this.nm_pumm = nm_pumm;
	}
	public String getSt_stat_cd() {
		return st_stat_cd;
	}
	public void setSt_stat_cd(String st_stat_cd) {
		this.st_stat_cd = st_stat_cd;
	}
	public String getSt_stat_nm() {
		return st_stat_nm;
	}
	public void setSt_stat_nm(String st_stat_nm) {
		this.st_stat_nm = st_stat_nm;
	}
	public String getNo_docu() {
		return no_docu;
	}
	public void setNo_docu(String no_docu) {
		this.no_docu = no_docu;
	}
	public String getContents1() {
		return contents1;
	}
	public void setContents1(String contents1) {
		this.contents1 = contents1;
	}
	public String getContents2() {
		return contents2;
	}
	public void setContents2(String contents2) {
		this.contents2 = contents2;
	}
	public String getContents3() {
		return contents3;
	}
	public void setContents3(String contents3) {
		this.contents3 = contents3;
	}
	public String getContents4() {
		return contents4;
	}
	public void setContents4(String contents4) {
		this.contents4 = contents4;
	}
	public String getContents5() {
		return contents5;
	}
	public void setContents5(String contents5) {
		this.contents5 = contents5;
	}
	public List<?> getFileList() {
		return fileList;
	}
	public void setFileList(List<?> fileList) {
		this.fileList = fileList;
	}
	public String getAppr_doc_url() {
		return appr_doc_url;
	}
	public void setAppr_doc_url(String appr_doc_url) {
		this.appr_doc_url = appr_doc_url;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getAppr_no_fin() {
		return appr_no_fin;
	}
	public void setAppr_no_fin(String appr_no_fin) {
		this.appr_no_fin = appr_no_fin;
	}
	
}
