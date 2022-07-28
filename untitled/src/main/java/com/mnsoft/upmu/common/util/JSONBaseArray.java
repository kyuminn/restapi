package com.mnsoft.upmu.common.util;

import org.json.simple.JSONArray;

public class JSONBaseArray extends JSONArray {
	
	public JSONBaseArray() {
		super();
	}
	public JSONBaseVO elementAt(int index) {
		return (JSONBaseVO)super.get(index);
	}
}