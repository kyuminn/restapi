package com.mnsoft.upmu.common.vo;

import java.util.List;

public class Approval extends Common{

	private String appr_no         = "";
	private String task_cd         = "";
	private String sts_cd          = "";
	private String edit_user_id    = "";
	private String edit_dt         = "";

	private String appr_seq        = "";
	private String appr_type       = "";
	private String appr_id         = "";
	private String appr_nm         = "";
	private String appr_pos        = "";
	private String appr_dept_cd    = "";
	private String appr_sts_cd     = "";
	private String appr_dt         = "";
	
	private String req_id     	   = "";
	private String req_dt     	   = "";
	
	private String task_info       = "";
	private String appr_ttl        = "";
	
	private String appr_dept_nm    = "";
	
	private String row_num         = "";
	
	private String code_type       = "";
	private String appr_doc_url    = "";
	private String view_dt         = "";
	
	private String appr_pos_nm     = "";
	private String appr_sts_nm     = "";
	
	private String req_nm         = "";
	private String req_pos        = "";
	private String req_pos_nm     = "";
	private String req_dept_cd    = "";
	private String req_dept_nm    = "";
	
	private String reject_resn    = "";
	
	private String user_id        = "";
	private String appr_line_no   = "";
	private String appr_line_nm   = "";
	
	private String appr_type_nm   = "";
	private String appr_info	  = "";
	
	private String ref_appr_no    = "";
	private String ref_appr_txt    = "";
	private String ref_info       = "";
	private String appr_cmmt      = "";
	
	private String ref_info_arr   = "";
	private String ref_type       = "";
	private String ref_cont       = "";
	
	private String ref_nm         = "";
	private String ref_pos_nm     = "";
	private String ref_dept_nm    = "";
	
	private List<?> fileList      = null;
	
	private String doc_no		  = "";
	private String appl_table	  = "";
	
	private String cd_company	  = "";
	private String cd_pc		  = "";
	private String note		  	  = "";
	private String amount		  = "";
	private String appr_form_knd  = "";
	
	private String search_type	  = "";
	private String search_name	  = "";
	private String search_year	  = "";
	
	private String update_resn	  = "";
	
	private String user_nm	  	  = "";
	private String pos_cd	      = "";
	private String dept_id	      = "";
	private String pos_nm	      = "";
	private String dept_nm	      = "";
	private String file_no        = "";
	
	private String search_ctgy    = "";
	
	private String appoint_id     = "";
	private String appoint_nm     = "";
	private String appoint_pos     = "";
	private String appoint_dept_cd = "";
	private String appoint_pos_nm  = "";
	private String appoint_dept_nm = "";
	private String appoint_yn     = "";
	
	private String view_id     = "";
	private String view_nm     = "";
	private String view_pos     = "";
	private String view_dept_cd = "";
	private String view_pos_nm  = "";
	private String view_dept_nm = "";
	
	private String cur_appr_nm = "";
	
	private String appr_no_fin = "";
	
	private String appr_ord = "";
	
	private String charge_m_cmmt = "";
	private String charge_cmmt = "";
	private String status = "";
	private String charge_id = "";
	private String charge_nm     = "";
	private String charge_pos     = "";
	private String charge_dept_cd = "";
	private String charge_pos_nm  = "";
	private String charge_dept_nm = "";
	private String cmmt_cnt = "";
	private String charge_dt = "";
	private String info_doc_no ="";
	private String usb_doc_no ="";
	private String drm_doc_no ="";
	private String vpn_doc_no ="";
	
	private String appr_ttl_en ="";
	private String req_nm_en ="";
	private String appr_nm_en = "";
	private String appoint_nm_en = "";
	
	private String ref_id = "";
	private String ref_dept_cd = "";
	private String patent_auth_yn = "";
	
	private String apprTypeFlag = "";
	private String selectApprType = "";
	private String doc_seq = "";
	
	public String getRef_id() {
		return ref_id;
	}
	public void setRef_id(String ref_id) {
		this.ref_id = ref_id;
	}
	public String getRef_dept_cd() {
		return ref_dept_cd;
	}
	public void setRef_dept_cd(String ref_dept_cd) {
		this.ref_dept_cd = ref_dept_cd;
	}
	public String getAppoint_nm_en() {
		return appoint_nm_en;
	}
	public void setAppoint_nm_en(String appoint_nm_en) {
		this.appoint_nm_en = appoint_nm_en;
	}
	public String getAppr_nm_en() {
		return appr_nm_en;
	}
	public void setAppr_nm_en(String appr_nm_en) {
		this.appr_nm_en = appr_nm_en;
	}
	public String getAppr_ttl_en() {
		return appr_ttl_en;
	}
	public void setAppr_ttl_en(String appr_ttl_en) {
		this.appr_ttl_en = appr_ttl_en;
	}
	public String getReq_nm_en() {
		return req_nm_en;
	}
	public void setReq_nm_en(String req_nm_en) {
		this.req_nm_en = req_nm_en;
	}
	public String getUsb_doc_no() {
		return usb_doc_no;
	}
	public void setUsb_doc_no(String usb_doc_no) {
		this.usb_doc_no = usb_doc_no;
	}
	public String getDrm_doc_no() {
		return drm_doc_no;
	}
	public void setDrm_doc_no(String drm_doc_no) {
		this.drm_doc_no = drm_doc_no;
	}
	public String getVpn_doc_no() {
		return vpn_doc_no;
	}
	public void setVpn_doc_no(String vpn_doc_no) {
		this.vpn_doc_no = vpn_doc_no;
	}
	public String getInfo_doc_no() {
		return info_doc_no;
	}
	public void setInfo_doc_no(String info_doc_no) {
		this.info_doc_no = info_doc_no;
	}
	public String getCharge_dt() {
		return charge_dt;
	}
	public void setCharge_dt(String charge_dt) {
		this.charge_dt = charge_dt;
	}
	public String getCmmt_cnt() {
		return cmmt_cnt;
	}
	public void setCmmt_cnt(String cmmt_cnt) {
		this.cmmt_cnt = cmmt_cnt;
	}
	public String getCharge_m_cmmt() {
		return charge_m_cmmt;
	}
	public void setCharge_m_cmmt(String charge_m_cmmt) {
		this.charge_m_cmmt = charge_m_cmmt;
	}
	public String getCharge_cmmt() {
		return charge_cmmt;
	}
	public void setCharge_cmmt(String charge_cmmt) {
		this.charge_cmmt = charge_cmmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCharge_id() {
		return charge_id;
	}
	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}
	public String getCharge_nm() {
		return charge_nm;
	}
	public void setCharge_nm(String charge_nm) {
		this.charge_nm = charge_nm;
	}
	public String getCharge_pos() {
		return charge_pos;
	}
	public void setCharge_pos(String charge_pos) {
		this.charge_pos = charge_pos;
	}
	public String getCharge_dept_cd() {
		return charge_dept_cd;
	}
	public void setCharge_dept_cd(String charge_dept_cd) {
		this.charge_dept_cd = charge_dept_cd;
	}
	public String getCharge_pos_nm() {
		return charge_pos_nm;
	}
	public void setCharge_pos_nm(String charge_pos_nm) {
		this.charge_pos_nm = charge_pos_nm;
	}
	public String getCharge_dept_nm() {
		return charge_dept_nm;
	}
	public void setCharge_dept_nm(String charge_dept_nm) {
		this.charge_dept_nm = charge_dept_nm;
	}
	
	
	public String getRef_appr_txt() {
		return ref_appr_txt;
	}
	public void setRef_appr_txt(String ref_appr_txt) {
		this.ref_appr_txt = ref_appr_txt;
	}
	public String getAppr_ord() {
		return appr_ord;
	}
	public void setAppr_ord(String appr_ord) {
		this.appr_ord = appr_ord;
	}
	public String getSearch_year() {
		return search_year;
	}
	public void setSearch_year(String search_year) {
		this.search_year = search_year;
	}
	public String getAppr_no_fin() {
		return appr_no_fin;
	}
	public void setAppr_no_fin(String appr_no_fin) {
		this.appr_no_fin = appr_no_fin;
	}
	public String getCur_appr_nm() {
		return cur_appr_nm;
	}
	public void setCur_appr_nm(String cur_appr_nm) {
		this.cur_appr_nm = cur_appr_nm;
	}
	public String getView_id() {
		return view_id;
	}
	public void setView_id(String view_id) {
		this.view_id = view_id;
	}
	public String getView_nm() {
		return view_nm;
	}
	public void setView_nm(String view_nm) {
		this.view_nm = view_nm;
	}
	public String getView_pos() {
		return view_pos;
	}
	public void setView_pos(String view_pos) {
		this.view_pos = view_pos;
	}
	public String getView_dept_cd() {
		return view_dept_cd;
	}
	public void setView_dept_cd(String view_dept_cd) {
		this.view_dept_cd = view_dept_cd;
	}
	public String getView_pos_nm() {
		return view_pos_nm;
	}
	public void setView_pos_nm(String view_pos_nm) {
		this.view_pos_nm = view_pos_nm;
	}
	public String getView_dept_nm() {
		return view_dept_nm;
	}
	public void setView_dept_nm(String view_dept_nm) {
		this.view_dept_nm = view_dept_nm;
	}
	public String getAppoint_yn() {
		return appoint_yn;
	}
	public void setAppoint_yn(String appoint_yn) {
		this.appoint_yn = appoint_yn;
	}
	public String getAppoint_nm() {
		return appoint_nm;
	}
	public void setAppoint_nm(String appoint_nm) {
		this.appoint_nm = appoint_nm;
	}
	public String getAppoint_pos() {
		return appoint_pos;
	}
	public void setAppoint_pos(String appoint_pos) {
		this.appoint_pos = appoint_pos;
	}
	public String getAppoint_dept_cd() {
		return appoint_dept_cd;
	}
	public void setAppoint_dept_cd(String appoint_dept_cd) {
		this.appoint_dept_cd = appoint_dept_cd;
	}
	public String getAppoint_pos_nm() {
		return appoint_pos_nm;
	}
	public void setAppoint_pos_nm(String appoint_pos_nm) {
		this.appoint_pos_nm = appoint_pos_nm;
	}
	public String getAppoint_dept_nm() {
		return appoint_dept_nm;
	}
	public void setAppoint_dept_nm(String appoint_dept_nm) {
		this.appoint_dept_nm = appoint_dept_nm;
	}
	public String getAppoint_id() {
		return appoint_id;
	}
	public void setAppoint_id(String appoint_id) {
		this.appoint_id = appoint_id;
	}
	public String getSearch_ctgy() {
		return search_ctgy;
	}
	public void setSearch_ctgy(String search_ctgy) {
		this.search_ctgy = search_ctgy;
	}
	public String getPos_nm() {
		return pos_nm;
	}
	public void setPos_nm(String pos_nm) {
		this.pos_nm = pos_nm;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getPos_cd() {
		return pos_cd;
	}
	public void setPos_cd(String pos_cd) {
		this.pos_cd = pos_cd;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getUpdate_resn() {
		return update_resn;
	}
	public void setUpdate_resn(String update_resn) {
		this.update_resn = update_resn;
	}
	public String getSearch_name() {
		return search_name;
	}
	public void setSearch_name(String search_name) {
		this.search_name = search_name;
	}
	public String getSearch_type() {
		return search_type;
	}
	public void setSearch_type(String search_type) {
		this.search_type = search_type;
	}
	public String getAppl_table() {
		return appl_table;
	}
	public void setAppl_table(String appl_table) {
		this.appl_table = appl_table;
	}
	public String getRef_nm() {
		return ref_nm;
	}
	public void setRef_nm(String ref_nm) {
		this.ref_nm = ref_nm;
	}
	public String getRef_pos_nm() {
		return ref_pos_nm;
	}
	public void setRef_pos_nm(String ref_pos_nm) {
		this.ref_pos_nm = ref_pos_nm;
	}
	public String getRef_dept_nm() {
		return ref_dept_nm;
	}
	public void setRef_dept_nm(String ref_dept_nm) {
		this.ref_dept_nm = ref_dept_nm;
	}
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public List<?> getFileList() {
		return fileList;
	}
	public void setFileList(List<?> fileList) {
		this.fileList = fileList;
	}
	public String getRef_cont() {
		return ref_cont;
	}
	public void setRef_cont(String ref_cont) {
		this.ref_cont = ref_cont;
	}
	public String getRef_info_arr() {
		return ref_info_arr;
	}
	public void setRef_info_arr(String ref_info_arr) {
		this.ref_info_arr = ref_info_arr;
	}
	public String getRef_type() {
		return ref_type;
	}
	public void setRef_type(String ref_type) {
		this.ref_type = ref_type;
	}
	public String getAppr_cmmt() {
		return appr_cmmt;
	}
	public void setAppr_cmmt(String appr_cmmt) {
		this.appr_cmmt = appr_cmmt;
	}
	public String getRef_appr_no() {
		return ref_appr_no;
	}
	public void setRef_appr_no(String ref_appr_no) {
		this.ref_appr_no = ref_appr_no;
	}
	public String getRef_info() {
		return ref_info;
	}
	public void setRef_info(String ref_info) {
		this.ref_info = ref_info;
	}
	public String getAppr_info() {
		return appr_info;
	}
	public void setAppr_info(String appr_info) {
		this.appr_info = appr_info;
	}
	public String getAppr_type_nm() {
		return appr_type_nm;
	}
	public void setAppr_type_nm(String appr_type_nm) {
		this.appr_type_nm = appr_type_nm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getAppr_line_no() {
		return appr_line_no;
	}
	public void setAppr_line_no(String appr_line_no) {
		this.appr_line_no = appr_line_no;
	}
	public String getAppr_line_nm() {
		return appr_line_nm;
	}
	public void setAppr_line_nm(String appr_line_nm) {
		this.appr_line_nm = appr_line_nm;
	}
	public String getReject_resn() {
		return reject_resn;
	}
	public void setReject_resn(String reject_resn) {
		this.reject_resn = reject_resn;
	}
	public String getReq_nm() {
		return req_nm;
	}
	public void setReq_nm(String req_nm) {
		this.req_nm = req_nm;
	}
	public String getReq_pos() {
		return req_pos;
	}
	public void setReq_pos(String req_pos) {
		this.req_pos = req_pos;
	}
	public String getReq_pos_nm() {
		return req_pos_nm;
	}
	public void setReq_pos_nm(String req_pos_nm) {
		this.req_pos_nm = req_pos_nm;
	}
	public String getReq_dept_cd() {
		return req_dept_cd;
	}
	public void setReq_dept_cd(String req_dept_cd) {
		this.req_dept_cd = req_dept_cd;
	}
	public String getReq_dept_nm() {
		return req_dept_nm;
	}
	public void setReq_dept_nm(String req_dept_nm) {
		this.req_dept_nm = req_dept_nm;
	}
	public String getAppr_sts_nm() {
		return appr_sts_nm;
	}
	public void setAppr_sts_nm(String appr_sts_nm) {
		this.appr_sts_nm = appr_sts_nm;
	}
	public String getAppr_pos_nm() {
		return appr_pos_nm;
	}
	public void setAppr_pos_nm(String appr_pos_nm) {
		this.appr_pos_nm = appr_pos_nm;
	}
	public String getAppr_doc_url() {
		return appr_doc_url;
	}
	public void setAppr_doc_url(String appr_doc_url) {
		this.appr_doc_url = appr_doc_url;
	}
	public String getView_dt() {
		return view_dt;
	}
	public void setView_dt(String view_dt) {
		this.view_dt = view_dt;
	}
	public String getCode_type() {
		return code_type;
	}
	public void setCode_type(String code_type) {
		this.code_type = code_type;
	}
	public String getRow_num() {
		return row_num;
	}
	public void setRow_num(String row_num) {
		this.row_num = row_num;
	}
	public String getAppr_dept_nm() {
		return appr_dept_nm;
	}
	public void setAppr_dept_nm(String appr_dept_nm) {
		this.appr_dept_nm = appr_dept_nm;
	}
	public String getAppr_ttl() {
		return appr_ttl;
	}
	public void setAppr_ttl(String appr_ttl) {
		this.appr_ttl = appr_ttl;
	}
	public String getTask_info() {
		return task_info;
	}
	public void setTask_info(String task_info) {
		this.task_info = task_info;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
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
	public String getTask_cd() {
		return task_cd;
	}
	public void setTask_cd(String task_cd) {
		this.task_cd = task_cd;
	}
	public String getSts_cd() {
		return sts_cd;
	}
	public void setSts_cd(String sts_cd) {
		this.sts_cd = sts_cd;
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
	public String getAppr_seq() {
		return appr_seq;
	}
	public void setAppr_seq(String appr_seq) {
		this.appr_seq = appr_seq;
	}
	public String getAppr_type() {
		return appr_type;
	}
	public void setAppr_type(String appr_type) {
		this.appr_type = appr_type;
	}
	public String getAppr_id() {
		return appr_id;
	}
	public void setAppr_id(String appr_id) {
		this.appr_id = appr_id;
	}
	public String getAppr_nm() {
		return appr_nm;
	}
	public void setAppr_nm(String appr_nm) {
		this.appr_nm = appr_nm;
	}
	public String getAppr_pos() {
		return appr_pos;
	}
	public void setAppr_pos(String appr_pos) {
		this.appr_pos = appr_pos;
	}
	public String getAppr_dept_cd() {
		return appr_dept_cd;
	}
	public void setAppr_dept_cd(String appr_dept_cd) {
		this.appr_dept_cd = appr_dept_cd;
	}
	public String getAppr_sts_cd() {
		return appr_sts_cd;
	}
	public void setAppr_sts_cd(String appr_sts_cd) {
		this.appr_sts_cd = appr_sts_cd;
	}
	public String getAppr_dt() {
		return appr_dt;
	}
	public void setAppr_dt(String appr_dt) {
		this.appr_dt = appr_dt;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAppr_form_knd() {
		return appr_form_knd;
	}
	public void setAppr_form_knd(String appr_form_knd) {
		this.appr_form_knd = appr_form_knd;
	}
	public String getFile_no() {
		return file_no;
	}
	public void setFile_no(String file_no) {
		this.file_no = file_no;
	}
	public String getPatent_auth_yn() {
		return patent_auth_yn;
	}
	public void setPatent_auth_yn(String patent_auth_yn) {
		this.patent_auth_yn = patent_auth_yn;
	}
	public String getApprTypeFlag() {
		return apprTypeFlag;
	}
	public void setApprTypeFlag(String apprTypeFlag) {
		this.apprTypeFlag = apprTypeFlag;
	}
	public String getSelectApprType() {
		return selectApprType;
	}
	public void setSelectApprType(String selectApprType) {
		this.selectApprType = selectApprType;
	}
	public String getDoc_seq() {
		return doc_seq;
	}
	public void setDoc_seq(String doc_seq) {
		this.doc_seq = doc_seq;
	}
	
	
}
