package com.mnsoft.upmu.common.vo;

public class ErpHolidayWork {

	private String cd_company    = "";   //회사코드                        
	private String cd_bizarea    = "";   //사업장코드                       
	private String no_emp        = "";   //사번                          
	private String no_proposal   = "";   //신청번호                        
	private String cd_wcode      = "";   //근태코드                        
	private String dt_proposal   = "";   //신청일자                        
	private String dt_start      = "";   //기간FROM                      
	private String dt_close      = "";   //기간TO                        
	private String tm_start      = "";   //시간FROM                      
	private String tm_close      = "";   //시간TO                        
	private String dy_proposal   = "";   //신청일수                        
	private String dc_rmk        = "";   //비고                          
	private String no_cemp       = "";   //승인자사번                       
	private String cd_consent    = "";   //승인유무(001.신청, 002.승인, 003.반려)
	private String no_tel_emer   = "";   //비상연락처 
	private String no_tel        = "";   //자책전화번호
	private String no_emp_an     = "";   //직무대행자 
	private String state  	     = "";   //승인반영유무
	private String cd_wtype      = "";   //근태구분  
	private String id_insert     = "";   //사용자코드 
	private String dt_consent    = "";   //승인일자   
	private String nm_cemp       = "";   //승인자명   
	private String dc_rmk1       = "";   //비고1    
	private String dy_ysub		 = "";   //대체휴가일수 
	private String yn_pay		 = "";   // 대휴적립  
	private String nm_start		 = "";   //행선지    
	private String dc_rmk2		 = "";   //작업라인   
	private String dc_rmk3		 = "";   //제품     
	private String dt_wreplace   = "";   // 대휴일자  
	
	
	public String getCd_company() {
		return cd_company;
	}
	public void setCd_company(String cd_company) {
		this.cd_company = cd_company;
	}
	public String getCd_bizarea() {
		return cd_bizarea;
	}
	public void setCd_bizarea(String cd_bizarea) {
		this.cd_bizarea = cd_bizarea;
	}
	public String getNo_emp() {
		return no_emp;
	}
	public void setNo_emp(String no_emp) {
		this.no_emp = no_emp;
	}
	public String getNo_proposal() {
		return no_proposal;
	}
	public void setNo_proposal(String no_proposal) {
		this.no_proposal = no_proposal;
	}
	public String getCd_wcode() {
		return cd_wcode;
	}
	public void setCd_wcode(String cd_wcode) {
		this.cd_wcode = cd_wcode;
	}
	public String getDt_proposal() {
		return dt_proposal;
	}
	public void setDt_proposal(String dt_proposal) {
		this.dt_proposal = dt_proposal;
	}
	public String getDt_start() {
		return dt_start;
	}
	public void setDt_start(String dt_start) {
		this.dt_start = dt_start;
	}
	public String getDt_close() {
		return dt_close;
	}
	public void setDt_close(String dt_close) {
		this.dt_close = dt_close;
	}
	public String getTm_start() {
		return tm_start;
	}
	public void setTm_start(String tm_start) {
		this.tm_start = tm_start;
	}
	public String getTm_close() {
		return tm_close;
	}
	public void setTm_close(String tm_close) {
		this.tm_close = tm_close;
	}
	public String getDy_proposal() {
		return dy_proposal;
	}
	public void setDy_proposal(String dy_proposal) {
		this.dy_proposal = dy_proposal;
	}
	public String getDc_rmk() {
		return dc_rmk;
	}
	public void setDc_rmk(String dc_rmk) {
		this.dc_rmk = dc_rmk;
	}
	public String getNo_cemp() {
		return no_cemp;
	}
	public void setNo_cemp(String no_cemp) {
		this.no_cemp = no_cemp;
	}
	public String getCd_consent() {
		return cd_consent;
	}
	public void setCd_consent(String cd_consent) {
		this.cd_consent = cd_consent;
	}
	public String getNo_tel_emer() {
		return no_tel_emer;
	}
	public void setNo_tel_emer(String no_tel_emer) {
		this.no_tel_emer = no_tel_emer;
	}
	public String getNo_tel() {
		return no_tel;
	}
	public void setNo_tel(String no_tel) {
		this.no_tel = no_tel;
	}
	public String getNo_emp_an() {
		return no_emp_an;
	}
	public void setNo_emp_an(String no_emp_an) {
		this.no_emp_an = no_emp_an;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCd_wtype() {
		return cd_wtype;
	}
	public void setCd_wtype(String cd_wtype) {
		this.cd_wtype = cd_wtype;
	}
	public String getId_insert() {
		return id_insert;
	}
	public void setId_insert(String id_insert) {
		this.id_insert = id_insert;
	}
	public String getDt_consent() {
		return dt_consent;
	}
	public void setDt_consent(String dt_consent) {
		this.dt_consent = dt_consent;
	}
	public String getNm_cemp() {
		return nm_cemp;
	}
	public void setNm_cemp(String nm_cemp) {
		this.nm_cemp = nm_cemp;
	}
	public String getDc_rmk1() {
		return dc_rmk1;
	}
	public void setDc_rmk1(String dc_rmk1) {
		this.dc_rmk1 = dc_rmk1;
	}
	public String getDy_ysub() {
		return dy_ysub;
	}
	public void setDy_ysub(String dy_ysub) {
		this.dy_ysub = dy_ysub;
	}
	public String getYn_pay() {
		return yn_pay;
	}
	public void setYn_pay(String yn_pay) {
		this.yn_pay = yn_pay;
	}
	public String getNm_start() {
		return nm_start;
	}
	public void setNm_start(String nm_start) {
		this.nm_start = nm_start;
	}
	public String getDc_rmk2() {
		return dc_rmk2;
	}
	public void setDc_rmk2(String dc_rmk2) {
		this.dc_rmk2 = dc_rmk2;
	}
	public String getDc_rmk3() {
		return dc_rmk3;
	}
	public void setDc_rmk3(String dc_rmk3) {
		this.dc_rmk3 = dc_rmk3;
	}
	public String getDt_wreplace() {
		return dt_wreplace;
	}
	public void setDt_wreplace(String dt_wreplace) {
		this.dt_wreplace = dt_wreplace;
	}
	
	
}
