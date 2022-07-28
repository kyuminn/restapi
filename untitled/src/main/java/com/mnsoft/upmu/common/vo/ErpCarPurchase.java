package com.mnsoft.upmu.common.vo;

public class ErpCarPurchase {

	private String 	cd_company    	= "";   //회사코드            
	private String 	txtYM    		= "";   //급여지급월            
	private int 	seq    				= 0;   	//급여지급순번            	
	private String 	user_id          ="";	//사번
	private String 	cd_fluct         ="";	//변동급여코드
	private String 	txtValue         ="";	//지원금액
	private String 	id_insert     	= "";   //사용자코드 

	public String getCd_company() {
		return cd_company;
	}

	public void setCd_company(String cd_company) {
		this.cd_company = cd_company;
	}

	public String getTxtYM() {
		return txtYM;
	}

	public void setTxtYM(String txtYM) {
		this.txtYM = txtYM;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTxtValue() {
		return txtValue;
	}

	public void setTxtValue(String txtValue) {
		this.txtValue = txtValue;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCd_fluct() {
		return cd_fluct;
	}

	public void setCd_fluct(String cd_fluct) {
		this.cd_fluct = cd_fluct;
	}

	public String getId_insert() {
		return id_insert;
	}

	public void setId_insert(String id_insert) {
		this.id_insert = id_insert;
	}
	

	
}
