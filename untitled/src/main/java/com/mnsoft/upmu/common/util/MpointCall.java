package com.mnsoft.upmu.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mnsoft.upmu.common.vo.Mpoint;
import com.mnsoft.upmu.common.vo.UserInfo;

public class MpointCall {

	public Mpoint selectMPointInfo(Mpoint vo) {
		String returnMassege = "";
		Mpoint mpoint  = new Mpoint();
		try {	
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");	//년월일시
				Calendar c1 = Calendar.getInstance();
				String strToday = sdf.format(c1.getTime());
				String cmpyNo ="D63";	// 고객사코드 : 현대엠엔소프트 ( D63 )
				String aesKey = cmpyNo+"_"+strToday+"_P";	// 인증키 : 고객사코드 + 년월일시 + P
				MPOINT_AES256 test = new MPOINT_AES256();
				String empen = vo.getEmpen(); //사번 , 전체일경우 "ALL" 
				String urlEnc = URLEncoder.encode(test.encrypt(empen, aesKey), "UTF-8");
				Date today = new Date();
		        String apiURL = "https://mnsoft.benecafe.co.kr/wl/servlets/tbs.api.point.WelfarePointD63";
		        URL url = new URL(apiURL);
		        HttpURLConnection con = (HttpURLConnection)url.openConnection();
		        con.setRequestMethod("POST");
		        con.setConnectTimeout(10000);       //컨텍션타임아웃 10초
		        con.setReadTimeout(5000);           //컨텐츠조회 타임아웃 5총

		        // post request(16진수 문자열 세팅)
		        Map<String,Object> params = new LinkedHashMap<>();
		        params.put("reqParm", urlEnc);
		        
		        StringBuilder postData = new StringBuilder();
		        for (Map.Entry<String,Object> param : params.entrySet()) {
		            if (postData.length() != 0) postData.append('&');
		            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
		            postData.append('=');
		            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		        }
		        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
		        
		        con.setDoOutput(true);
		        con.getOutputStream().write(postDataBytes);
//		        wr.flush();
//		        wr.close();
		        int responseCode = con.getResponseCode();
		        BufferedReader br;
		        if(responseCode==200) { // 정상 호출
		            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		        } else {  // 에러 발생
		            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		        }
		        
		        String inputLine;
		        StringBuffer response = new StringBuffer();
		        while ((inputLine = br.readLine()) != null) {
		            response.append(inputLine);
		        }
		        br.close();
		        System.out.println(response.toString());
			
		        returnMassege = response.toString();
		        //System.out.println("returnMassege"+returnMassege);
		        if(!returnMassege.equals("")){
		        	JSONParser jsonParser = new JSONParser();
		            JSONObject jsonObj = (JSONObject) jsonParser.parse(returnMassege);
		            JSONArray mPointArray = (JSONArray) jsonObj.get("result");
		            for(int i=0 ; i<mPointArray.size() ; i++){
		                JSONObject tempObj = (JSONObject) mPointArray.get(i);
		                
		                mpoint.setPrvePnt(tempObj.get("prvePnt").toString());
		                mpoint.setRmnPnt(tempObj.get("rmnPnt").toString());
		            }
		        }
		        
			} catch(Exception e) { 
				StringUtil.printStackTrace(e);
				Mpoint mpoint1  = new Mpoint();
				return mpoint1;
			}
		return mpoint;
		
	}
}
