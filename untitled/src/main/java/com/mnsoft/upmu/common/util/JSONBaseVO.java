package com.mnsoft.upmu.common.util;

import org.json.simple.JSONObject;

public class JSONBaseVO extends JSONObject {
	
	public JSONBaseVO() {
		super();
	}
	public String field(Object key) {
		try{
			return ((String)super.get(key)).trim();
		}catch(NullPointerException ne){
			return "";
		}
	}
}