package com.mnsoft.upmu.common.util;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 컨트롤에서 공통적으로 사용될 메소드를 정의해 놓은 클래스
 */
public class AbstractCntr extends CommonsMultipartResolver{

    //view 페이지 경로
    public final static String VIEW_PATH = "view";
    //data 페이지 경로
    public final static String DATA_PATH = "data";
    //Json 데이터 페이지 명
    public final static String DATA_JSON_PAGE = DATA_PATH + "/" + "data.json";

    //Json 데이터 페이지 명
    public final static String DATA_JSON_IFRAME_PAGE = DATA_PATH + "/" + "data.json.iframe";
    //Json 데이터 Key
    public final static String JSON_DATA_KEY = "data";
    //기본 Json 데이터 액션 성공시 기본 Json 데이터
    public final static String DEFAULT_SUCCESS_JSON_DATA = "{'success':true,'errmsg':'','data':[]}";

    /**
     * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
     * 이 때 지정한 Class로 bean이 생성된다.
     * @param jsonString 원본 Json 데이터
     * @param c 생성할 Bean Object
     * @return 생성된 Bean Object가 추가된 List Object
     */
    public static List<?> getJsonToList(String jsonString, Class<?> c) {
        String safe = Jsoup.clean(StringUtil.tagRemove(jsonString), Whitelist.basicWithImages());
        System.out.println("safe="+safe);
        return (List<?>) JSONArray.toCollection(JSONArray.fromObject(safe.replace("&quot;", "\"")), c);
    }

    /**
     * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
     * @param jsonString 원본 Json 데이터
     * @param c 생성할 Bean Object
     * @return 생성된 Bean Object 의 배열
     */
    public static Object[] getJsonToArray(String jsonString, Class<?> c){
        String safe = Jsoup.clean(StringUtil.tagRemove(jsonString), Whitelist.basicWithImages());
        return (Object[]) JSONArray.toArray(JSONArray.fromObject(safe.replace("&quot;", "\"")), c);
    }

    /**
     * {a='1'} 형태의 Json 데이터를 파싱하여 html을 제거한후 Object 로 리턴한다.
     * @param jsonString 원본 Json 데이터
     * @param c 생성할 Bean Object
     * @return 생성된 Bean Object
     */
    public static Object getJsonToBean(String jsonString, Class<?> c) {
        String safe = Jsoup.clean(jsonString, Whitelist.basicWithImages());
        return JSONObject.toBean(JSONObject.fromObject(safe.replace("&quot;", "\"")), c);
    }
}