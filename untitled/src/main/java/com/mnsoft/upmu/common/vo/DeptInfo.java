package com.mnsoft.upmu.common.vo;

public class DeptInfo extends Common{
	private String deptid            = "";
	private String deptname          = "";
	private String parentid          = "";
	private String parentname        = "";
	private String statisticslevel   = "";
	private String representativeid  = "";
	private String representative_nm = "";
	private String dutyid            = "";
	private String duty_nm           = "";
	private String eng_duty_nm       = "";
	private String wp_code           = "";
	private String wp_name           = "";
	private String emg_wp_name       = "";
	private String status            = "";
	private String eng_deptname      = "";
	private String deptemail         = "";

	private String id         		= ""; // 부모
    private String pId         		= ""; // 자식
    private String name         	= ""; // 트리이름
	private String open         	= ""; // 트리 OPEN 여부, true/false
	private String isParent         = ""; // 부모 여부, true/false
	private String web         		= ""; // 링크 URL
	private String up_in         	= ""; // update insert 구분
	private String menu_id2         = ""; // 메뉴아이디 생성시 필요한 메뉴아이디
	private String t         		= ""; // 링크 URL
	private String chkDisabled      = ""; // 체크할수없게 플래그
	private String checked         	= ""; // 기본체크셋팅 유무 플래그




	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getUp_in() {
		return up_in;
	}
	public void setUp_in(String up_in) {
		this.up_in = up_in;
	}
	public String getMenu_id2() {
		return menu_id2;
	}
	public void setMenu_id2(String menu_id2) {
		this.menu_id2 = menu_id2;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(String chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getStatisticslevel() {
		return statisticslevel;
	}
	public void setStatisticslevel(String statisticslevel) {
		this.statisticslevel = statisticslevel;
	}
	public String getRepresentativeid() {
		return representativeid;
	}
	public void setRepresentativeid(String representativeid) {
		this.representativeid = representativeid;
	}
	public String getRepresentative_nm() {
		return representative_nm;
	}
	public void setRepresentative_nm(String representative_nm) {
		this.representative_nm = representative_nm;
	}
	public String getDutyid() {
		return dutyid;
	}
	public void setDutyid(String dutyid) {
		this.dutyid = dutyid;
	}
	public String getDuty_nm() {
		return duty_nm;
	}
	public void setDuty_nm(String duty_nm) {
		this.duty_nm = duty_nm;
	}
	public String getEng_duty_nm() {
		return eng_duty_nm;
	}
	public void setEng_duty_nm(String eng_duty_nm) {
		this.eng_duty_nm = eng_duty_nm;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEng_deptname() {
		return eng_deptname;
	}
	public void setEng_deptname(String eng_deptname) {
		this.eng_deptname = eng_deptname;
	}
	public String getDeptemail() {
		return deptemail;
	}
	public void setDeptemail(String deptemail) {
		this.deptemail = deptemail;
	}


}
