package com.mnsoft.upmu.system.vo;

public class Role {
	
	private String role_seq ="";	//룰순번
	private String role_seq2="";	//룰순번
	private String user_id="";	//사용자 ID
	private String user_nm="";	//사용자 이름
	private String dept_nm="";	//부서명
	private String role_id="";	//룰ID
	private String role_nm="";	//룰명
	private String grade_id="";	//권한아이디
	private String reg_user_id="";	//등록자
	private String reg_dt="";	//등록일시
	private String edit_user_id="";	//수정자
	private String edit_dt="";	//수정일시
	private String use_yn="";	//수정일시
	private String menu_id="";	//메뉴id
	private String role_url="";	//담당자 롤 url

	private String seq="";
	private String menu_seq="";
	private String dates="";
	private String weekday="";
	private String gubun="";
	private String holi_desc="";
	private String holi_code="";
	private String edit_id="";	//수정모드

	private String searchUser="";
	private String searchGrade="";
	private String searchDate="";
	private String menu_nm="";
	private String cnt="";
	private String occ_grp_name="";
	private String grade_name="";
	private String wp_name="";
	private String positionid="";
	private String deptid="";
	
	private String new_user_id="";
	private String new_role_seq="";
	
	private String up_in="";// update insert 구분
	
	private String menu_url = "";
	

	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	public String getRole_seq() {
		return role_seq;
	}
	public void setRole_seq(String role_seq) {
		this.role_seq = role_seq;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getRole_nm() {
		return role_nm;
	}
	public void setRole_nm(String role_nm) {
		this.role_nm = role_nm;
	}
	public String getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(String grade_id) {
		this.grade_id = grade_id;
	}
	public String getReg_user_id() {
		return reg_user_id;
	}
	public void setReg_user_id(String reg_user_id) {
		this.reg_user_id = reg_user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public String getHoli_desc() {
		return holi_desc;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public void setHoli_desc(String holi_desc) {
		this.holi_desc = holi_desc;
	}
	public String getHoli_code() {
		return holi_code;
	}
	public void setHoli_code(String holi_code) {
		this.holi_code = holi_code;
	}
	public String getEdit_id() {
		return edit_id;
	}
	public void setEdit_id(String edit_id) {
		this.edit_id = edit_id;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public String getSearchUser() {
		return searchUser;
	}
	public void setSearchUser(String searchUser) {
		this.searchUser = searchUser;
	}
	public String getSearchGrade() {
		return searchGrade;
	}
	public void setSearchGrade(String searchGrade) {
		this.searchGrade = searchGrade;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}
	public String getUp_in() {
		return up_in;
	}
	public void setUp_in(String up_in) {
		this.up_in = up_in;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getRole_seq2() {
		return role_seq2;
	}
	public void setRole_seq2(String role_seq2) {
		this.role_seq2 = role_seq2;
	}
	public String getMenu_seq() {
		return menu_seq;
	}
	public void setMenu_seq(String menu_seq) {
		this.menu_seq = menu_seq;
	}
	public String getRole_url() {
		return role_url;
	}
	public void setRole_url(String role_url) {
		this.role_url = role_url;
	}
	public String getMenu_nm() {
		return menu_nm;
	}
	public void setMenu_nm(String menu_nm) {
		this.menu_nm = menu_nm;
	}
	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getOcc_grp_name() {
		return occ_grp_name;
	}
	public void setOcc_grp_name(String occ_grp_name) {
		this.occ_grp_name = occ_grp_name;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public String getWp_name() {
		return wp_name;
	}
	public void setWp_name(String wp_name) {
		this.wp_name = wp_name;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
	public String getDeptid() {
		return deptid;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public String getNew_user_id() {
		return new_user_id;
	}
	public void setNew_user_id(String new_user_id) {
		this.new_user_id = new_user_id;
	}
	public String getNew_role_seq() {
		return new_role_seq;
	}
	public void setNew_role_seq(String new_role_seq) {
		this.new_role_seq = new_role_seq;
	}

}
