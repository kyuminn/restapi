package com.mnsoft.upmu.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mnsoft.upmu.overseasBusinessTrip.vo.ExchangeInfo;

public class ExchangeCall {

	public ExchangeInfo selectExchangeInfo(ExchangeInfo vo) {
		String returnMassege = "";
		//List<ExchangeInfo> exchangeInfoList  = new ArrayList<ExchangeInfo>();
		ExchangeInfo exchangeInfo  = new ExchangeInfo();
		try {	
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");	//년월일시
				Calendar c1 = Calendar.getInstance();
				String searchdate = sdf.format(c1.getTime());
				String data =vo.getData();	//검색요청 API타입 AP01 : 환율, AP02 : 대출금리, AP03 : 국제금리
				String aesKey =  StringUtil.getPropinfo().getProperty("EXCHANGE_KEY");
			
				String apiURL = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
				URL url = new URL(apiURL);
				HttpURLConnection con = (HttpURLConnection)url.openConnection();
				con.setRequestMethod("POST");
				// post request
				int dayWeek = CurrentDateTime.getDayOfWeek(CurrentDateTime.getDate());
				String paramDate;
				if(dayWeek == 1){
					paramDate = CurrentDateTime.getDate(CurrentDateTime.getDate(), -2);
				}else if(dayWeek == 2){
					paramDate = CurrentDateTime.getDate(CurrentDateTime.getDate(), -3);
				}else{
					paramDate = CurrentDateTime.getDate(CurrentDateTime.getDate(), -1);
				}
				String postParams = "authkey="+aesKey+"&data="+data+"&searchdate="+paramDate;
				System.out.println("postParams:"+postParams);
				con.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream()); 
				
				wr.write(postParams);
				wr.flush();
				wr.close();
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

				returnMassege = response.toString();

				System.out.println("returnMassege"+returnMassege);
				if(!returnMassege.equals("")){
					JSONParser jsonParser = new JSONParser();
					JSONArray exchangeArray = (JSONArray) jsonParser.parse(returnMassege);

					for(int i=0; i<exchangeArray.size(); i++) {
						JSONObject tempObj = (JSONObject) exchangeArray.get(i);
						//조회 결과가 성공일때 
						if(tempObj.get("result").toString().equals("1")) {
							if(tempObj.get("cur_unit").toString().equals("USD")) {
								exchangeInfo.setUsd(tempObj.get("deal_bas_r").toString());
								exchangeInfo.setResult(tempObj.get("result").toString());
							}
							if(tempObj.get("cur_unit").toString().substring(0,3).equals("JPY")) {
								exchangeInfo.setJpy(tempObj.get("deal_bas_r").toString());
								exchangeInfo.setResult(tempObj.get("result").toString());
							}
							
							/*ExchangeInfo exchangeInfo  = new ExchangeInfo();
							exchangeInfo.setCur_unit(tempObj.get("cur_unit").toString());
							exchangeInfo.setDeal_bas_r(tempObj.get("deal_bas_r").toString());
							
							exchangeInfoList.add(exchangeInfo);*/
						}
					}
				}

		} catch(Exception e) { 
				StringUtil.printStackTrace(e);
			}
		return exchangeInfo;
		
	}
}
