package com.mnsoft.upmu.common.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {
	/**
	 *
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 이 때 지정한 Class로 bean이 생성된다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object가 추가된 List Object
	 */
	public static List<?> getJsonToList(String jsonString, Class<?> c) {
		return (List<?>) JSONArray.toCollection(JSONArray.fromObject( jsonString), c);
	}

	/**
	 *
	 * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object 의 배열
	 */
	public static Object[] getJsonToArray(String jsonString, Class<?> c){
		return (Object[]) JSONArray.toArray(JSONArray.fromObject( jsonString), c);
	}

	/**
	 *
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object
	 */
	public static Object getJsonToBean(String jsonString, Class<?> c) {
		return JSONObject.toBean(JSONObject.fromObject( jsonString), c);
	}
}
