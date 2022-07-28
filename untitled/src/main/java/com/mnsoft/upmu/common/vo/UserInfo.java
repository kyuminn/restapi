package com.mnsoft.upmu.common.vo;

import java.util.List;

public class UserInfo extends Common{
	private String user_id             = "";
	private String user_pw             = "";
	private String sabun               = "";
	private String name                = "";
	private String eng_name            = "";
	private String first_name          = "";
	private String last_name           = "";
	private String birth_day           = "";
	private String birth_type          = "";
	private String sex_flag            = "";
	private String email               = "";
	private String photo               = "";
	private String mobile              = "";
	private String joincompanydate     = "";
	private String deptid              = "";
	private String deptname            = "";
	private String eng_deptname        = "";
	private String wp_code             = "";
	private String wp_name             = "";
	private String emg_wp_name         = "";
	private String positionid          = "";
	private String positionname        = "";
	private String eng_emp_grade_nm    = "";
	private String dutyid              = "";
	private String dutyname            = "";
	private String eng_duty_nm         = "";
	private String occ_grp_code        = "";
	private String occ_grp_name        = "";
	private String occ_grp_eng_name    = "";
	private String localphoneno        = "";
	private String office_fax_no       = "";
	private String ouposition          = "";
	private String oudescription       = "";
	private String parentname          = "";
	private String parentid            = "";
	private String grade_code          = "";
	private String grade_name          = "";
	private String eng_grade_name      = "";
	private String stats_detail        = "";
	private String role_seq			   = "";
	private String work_year		   = "";
	private String auth_info		   = "";
	
	private String asgn_flag       = "";
	private String asgn_code       = "";
	private String asgn_name       = "";
	
	private String work_vic_yn     = "";
	private String year			   = "";
	private String pg_yn		= "";
	private String count		= "";
	
	private String duty_type_team  = "";
	private String duty_type_div   = "";
	private List<String> duty_arr;
	private String patent_auth_yn = "";
	
	public List<String> getDuty_arr() {
		return duty_arr;
	}
	public void setDuty_arr(List<String> duty_arr) {
		this.duty_arr = duty_arr;
	}
	public String getDuty_type_team() {
		return duty_type_team;
	}
	public void setDuty_type_team(String duty_type_team) {
		this.duty_type_team = duty_type_team;
	}
	public String getDuty_type_div() {
		return duty_type_div;
	}
	public void setDuty_type_div(String duty_type_div) {
		this.duty_type_div = duty_type_div;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getWork_vic_yn() {
		return work_vic_yn;
	}
	public void setWork_vic_yn(String work_vic_yn) {
		this.work_vic_yn = work_vic_yn;
	}
	public String getAuth_info() {
		return auth_info;
	}
	public void setAuth_info(String auth_info) {
		this.auth_info = auth_info;
	}
	public String getRole_seq() {
		return role_seq;
	}
	public void setRole_seq(String role_seq) {
		this.role_seq = role_seq;
	}
	public String getSabun() {
		return sabun;
	}
	public void setSabun(String sabun) {
		this.sabun = sabun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(String birth_day) {
		this.birth_day = birth_day;
	}
	public String getBirth_type() {
		return birth_type;
	}
	public void setBirth_type(String birth_type) {
		this.birth_type = birth_type;
	}
	public String getSex_flag() {
		return sex_flag;
	}
	public void setSex_flag(String sex_flag) {
		this.sex_flag = sex_flag;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getJoincompanydate() {
		return joincompanydate;
	}
	public void setJoincompanydate(String joincompanydate) {
		this.joincompanydate = joincompanydate;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getEng_deptname() {
		return eng_deptname;
	}
	public void setEng_deptname(String eng_deptname) {
		this.eng_deptname = eng_deptname;
	}
	public String getWp_code() {
		return wp_code;
	}
	public void setWp_code(String wp_code) {
		this.wp_code = wp_code;
	}
	public String getWp_name() {
		return wp_name;
	}
	public void setWp_name(String wp_name) {
		this.wp_name = wp_name;
	}
	public String getEmg_wp_name() {
		return emg_wp_name;
	}
	public void setEmg_wp_name(String emg_wp_name) {
		this.emg_wp_name = emg_wp_name;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
	public String getPositionname() {
		return positionname;
	}
	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}
	public String getEng_emp_grade_nm() {
		return eng_emp_grade_nm;
	}
	public void setEng_emp_grade_nm(String eng_emp_grade_nm) {
		this.eng_emp_grade_nm = eng_emp_grade_nm;
	}
	public String getDutyid() {
		return dutyid;
	}
	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}
	public String getDutyname() {
		return dutyname;
	}
	public void setDutyname(String dutyname) {
		this.dutyname = dutyname;
	}
	public String getEng_duty_nm() {
		return eng_duty_nm;
	}
	public void setEng_duty_nm(String eng_duty_nm) {
		this.eng_duty_nm = eng_duty_nm;
	}
	public String getOcc_grp_code() {
		return occ_grp_code;
	}
	public void setOcc_grp_code(String occ_grp_code) {
		this.occ_grp_code = occ_grp_code;
	}
	public String getOcc_grp_name() {
		return occ_grp_name;
	}
	public void setOcc_grp_name(String occ_grp_name) {
		this.occ_grp_name = occ_grp_name;
	}
	public String getOcc_grp_eng_name() {
		return occ_grp_eng_name;
	}
	public void setOcc_grp_eng_name(String occ_grp_eng_name) {
		this.occ_grp_eng_name = occ_grp_eng_name;
	}
	public String getLocalphoneno() {
		return localphoneno;
	}
	public void setLocalphoneno(String localphoneno) {
		this.localphoneno = localphoneno;
	}
	public String getOffice_fax_no() {
		return office_fax_no;
	}
	public void setOffice_fax_no(String office_fax_no) {
		this.office_fax_no = office_fax_no;
	}
	public String getOuposition() {
		return ouposition;
	}
	public void setOuposition(String ouposition) {
		this.ouposition = ouposition;
	}
	public String getOudescription() {
		return oudescription;
	}
	public void setOudescription(String oudescription) {
		this.oudescription = oudescription;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getGrade_code() {
		return grade_code;
	}
	public void setGrade_code(String grade_code) {
		this.grade_code = grade_code;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getEng_grade_name() {
		return eng_grade_name;
	}
	public void setEng_grade_name(String eng_grade_name) {
		this.eng_grade_name = eng_grade_name;
	}
	public String getStats_detail() {
		return stats_detail;
	}
	public void setStats_detail(String stats_detail) {
		this.stats_detail = stats_detail;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getWork_year() {
		return work_year;
	}
	public void setWork_year(String work_year) {
		this.work_year = work_year;
	}
	public String getAsgn_flag() {
		return asgn_flag;
	}
	public void setAsgn_flag(String asgn_flag) {
		this.asgn_flag = asgn_flag;
	}
	public String getAsgn_code() {
		return asgn_code;
	}
	public void setAsgn_code(String asgn_code) {
		this.asgn_code = asgn_code;
	}
	public String getAsgn_name() {
		return asgn_name;
	}
	public void setAsgn_name(String asgn_name) {
		this.asgn_name = asgn_name;
	}
	public String getPatent_auth_yn() {
		return patent_auth_yn;
	}
	public void setPatent_auth_yn(String patent_auth_yn) {
		this.patent_auth_yn = patent_auth_yn;
	}
	public String getPg_yn() {
		return pg_yn;
	}
	public void setPg_yn(String pg_yn) {
		this.pg_yn = pg_yn;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}



}
