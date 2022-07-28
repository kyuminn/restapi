package com.mnsoft.upmu.system.vo;

public class Time {
	
	private String seq;
	private String rest_id;	//휴게시간ID
	private String prest_id;	//휴게시간 부모 ID
	private String rest_sdt;	//휴게 시작 일자
	private String rest_edt;	//휴게 종료 일자
	private String stime;	//휴게시작시간
	private String etime;	//휴게종료시간 
	private String reg_user_id;	//등록자
	private String reg_dt;	//등록일시
	private String edit_user_id;	//수정자
	private String edit_dt;	//수정일시
	private String dept_nm;	//부서명
	private String dept_id;
	private String user_nm;	//이름

	private String exce_id;	
	private String pexce_id;
	private String exce_sdt;
	private String exce_edt;
	private String rsabun;
	
	private String gubun; //0:공통,1:그룹,2:개인
	private String searchUser;
	private String searchDept;
	
	private String team_leader_id;
	private String team_leader_nm;

	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getRest_id() {
		return rest_id;
	}
	public void setRest_id(String rest_id) {
		this.rest_id = rest_id;
	}
	public String getPrest_id() {
		return prest_id;
	}
	public void setPrest_id(String prest_id) {
		this.prest_id = prest_id;
	}
	public String getRest_sdt() {
		return rest_sdt;
	}
	public void setRest_sdt(String rest_sdt) {
		this.rest_sdt = rest_sdt;
	}
	public String getRest_edt() {
		return rest_edt;
	}
	public void setRest_edt(String rest_edt) {
		this.rest_edt = rest_edt;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
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
	public String getDept_nm() {
		return dept_nm;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
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
	public String getExce_id() {
		return exce_id;
	}
	public void setExce_id(String exce_id) {
		this.exce_id = exce_id;
	}
	public String getPexce_id() {
		return pexce_id;
	}
	public void setPexce_id(String pexce_id) {
		this.pexce_id = pexce_id;
	}
	public String getExce_sdt() {
		return exce_sdt;
	}
	public void setExce_sdt(String exce_sdt) {
		this.exce_sdt = exce_sdt;
	}
	public String getExce_edt() {
		return exce_edt;
	}
	public void setExce_edt(String exce_edt) {
		this.exce_edt = exce_edt;
	}
	public String getRsabun() {
		return rsabun;
	}
	public void setRsabun(String rsabun) {
		this.rsabun = rsabun;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getSearchUser() {
		return searchUser;
	}
	public void setSearchUser(String searchUser) {
		this.searchUser = searchUser;
	}
	public String getSearchDept() {
		return searchDept;
	}
	public void setSearchDept(String searchDept) {
		this.searchDept = searchDept;
	}
	public String getTeam_leader_id() {
		return team_leader_id;
	}
	public void setTeam_leader_id(String team_leader_id) {
		this.team_leader_id = team_leader_id;
	}
	public String getTeam_leader_nm() {
		return team_leader_nm;
	}
	public void setTeam_leader_nm(String team_leader_nm) {
		this.team_leader_nm = team_leader_nm;
	}


}
