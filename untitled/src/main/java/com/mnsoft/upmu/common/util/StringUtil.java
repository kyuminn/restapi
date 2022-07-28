package com.mnsoft.upmu.common.util;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mnsoft.upmu.common.vo.UserInfo;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class StringUtil {
	private transient static Log logger = LogFactory.getLog(StringUtil.class);
    public static String tagRemove(String s) {
        StringBuffer strip_html = new StringBuffer();
        char[] buf = s.toCharArray();
        int j = 0;
        for(; j < buf.length; j++) {
            if(buf[j] == '<') {
                for(; j < buf.length; j++) {
                    if(buf[j] == '>') {
                        break;
                    }
                }

            } else {
                strip_html.append(buf[j]);
            }
        }
        return strip_html.toString();
    }

    public static Properties getPropinfo(){
        Properties properties = new Properties();
        try {
            properties.load(new ClassPathResource("/prop.properties").getInputStream());
        } catch (Exception e) {
        }
        return properties;
    }
    
    public static Properties getDbPropinfo(){
        Properties properties = new Properties();
        try {
            properties.load(new ClassPathResource("/config/spring/db.properties").getInputStream());
        } catch (Exception e) {
        }
        return properties;
    }

    public static String fileExtention(String fileName) {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if(i > 0 && i < fileName.length() -1){
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    public static String lowerCase(String str) {
        if(str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    public static String getFileNm(String key){
        SecureRandom randomGenerator = new SecureRandom();
        return CurrentDateTime.getDate() + CurrentDateTime.getTime() +  key + randomGenerator.nextInt(10) + randomGenerator.nextInt(100);
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";
        if(object != null) {
            string = object.toString();
        }
        string = replace(string, "null", "");
        return string;
    }

    public static String isNullToString2(Object object, String regExt) {
        String string = "";
        if(object != null) {
            string = object.toString();
        }
        string = replace(string, "null", regExt);
        return string;
    }

    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        while(source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            source = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    public static String replaceParameter(String s) {
        s = StringUtil.replaceString(s, "<", "");
        s = StringUtil.replaceString(s, ">", "");
        s = StringUtil.replaceString(s, "'", "");
        return s;
    }

    public static String replaceString(String str, String src, String tgt) {
        StringBuffer ret = new StringBuffer();

        if(str == null)
            return null;
        if(str.equals(""))
            return "";

        int start = 0;
        int found = str.indexOf(src);
        while(found >= 0) {
            if(found > start)
                ret.append(str.substring(start, found));

            if(tgt != null)
                ret.append(tgt);

            start = found + src.length();
            found = str.indexOf(src, start);
        }

        if(str.length() > start)
            ret.append(str.substring(start, str.length()));

        return ret.toString();
    }

    /**
     *
     * 왼쪽에서부터 채운다는 의미
     *
     * @param str
     * @param num
     * @param addStr
     * @Use lpad("ABC", 3, "0");
     * @return
     */
    public static String lpad(String str, int num, String addStr){
        if("".equals(str) || "".equals(addStr) || str.length() >= num){
            return str;
        }
        int max = (num - str.length())/addStr.length();
        for(int i=0; i<max;i++){
            str = addStr + str;
        }
        return str;
    }

    /**
     *
     * 오른쪽에서부터 채운다는 의미
     *
     * @param str
     * @param num
     * @param addStr
     * @Use rpad("ABC", 3, "0");
     * @return
     */
    public static String rpad(String str, int num, String addStr){
        if(!"".equals(str) || !"".equals(addStr) || str.length() >= num){
            return str;
        }
        int max = (num - str.length())/addStr.length();
        for(int i = 0; i < max; i++){
            str += addStr;
        }
        return str;
    }

    /**
     *  Hash 값을 BASE64Encoder로 변환
     *
     * @param strHash
     * @return
     */
    public static String getBASE64Encoder(String strHash)
    {
       BASE64Encoder baseEncoder = new BASE64Encoder();

       return baseEncoder.encode(strHash.getBytes());
    }

     /**
      * Hash 값을 BASE64Encoder로 Decoder
      *
      * @param _strHash
      * @return string
      * @throws Exception
      */
      public static String getBASE64Decoder(String _strHash) throws Exception
      {
         BASE64Decoder baseDecoder = new BASE64Decoder();

         return new String(baseDecoder.decodeBuffer(_strHash));
      }

      /**
       *
       * Html decode
       *
       * @param str
       * @return
       */
    public static String getHtmlDecode(String str){
        String rtnStr = isNullToString(str);
        rtnStr = rtnStr.replaceAll("&amp;", "&");
        rtnStr = rtnStr.replaceAll("&deg;", "°");
        rtnStr = rtnStr.replaceAll("&gt;", ">");
        rtnStr = rtnStr.replaceAll("&lt;", "<");
        rtnStr = rtnStr.replaceAll("&#39;", "'");
        rtnStr = rtnStr.replaceAll("&quot;", "\"");
        rtnStr = rtnStr.replaceAll("&iquest;", "<br>");
        return rtnStr;
    }

    public static String getHtmlEncode(String str){
        String rtnStr = isNullToString(str);
        rtnStr = rtnStr.replaceAll("&", "&amp;");
        rtnStr = rtnStr.replaceAll("°", "&deg;");
        rtnStr = rtnStr.replaceAll(">", "&gt;");
        rtnStr = rtnStr.replaceAll("<", "&lt;");
        rtnStr = rtnStr.replaceAll("'", "&#39;");
        rtnStr = rtnStr.replaceAll("\"", "&quot;");
        rtnStr = rtnStr.replaceAll("<br>", "&iquest;");
        return rtnStr;
    }

    public static boolean checkDate(String str){
		boolean dateValidity = true;

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd",Locale.KOREA); //20041102
		df.setLenient(false); // false 로 설정해야 엄밀한 해석을 함.
		try {
		Date dt = df.parse(str);
		}
		catch(ParseException pe){
			dateValidity = false;
		}catch(IllegalArgumentException ae){
			dateValidity = false;
		}catch(Exception e){
			dateValidity = false;
		}

		return dateValidity;
	}

    public static String getDocNo(){

        Random randomGenerator = new Random();
        return CurrentDateTime.getDate().substring(2) + CurrentDateTime.getTime() + CurrentDateTime.getMilScnd() + StringUtil.lpad(Integer.toString(randomGenerator.nextInt(10000)),4,"0");
    }
    
    public static String getDocNo2(String seq){
    	
    	Random randomGenerator = new Random();
    	return CurrentDateTime.getDate().substring(2) + CurrentDateTime.getTime() + CurrentDateTime.getMilScnd() + seq + StringUtil.lpad(Integer.toString(randomGenerator.nextInt(10000)),3,"0");
    }

    public static String replaceChar(String source, String subject, String object)
    {
      StringBuffer rtnStr = new StringBuffer();
      String preStr = "";
      String nextStr = source;
      String srcStr = source;

      for (int i = 0; i < subject.length(); i++) {
        char chA = subject.charAt(i);

        if (srcStr.indexOf(chA) >= 0) {
          preStr = srcStr.substring(0, srcStr.indexOf(chA));
          nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
          srcStr = preStr + object + nextStr;
        }
      }

      return srcStr;
    }

    /**
     * 숫자를 제외한 모든 문자를 제거하고 숫자문자열만 리턴한다.
     *
     * @return
     */
    public static String removeChar(String str) {
        return str.replaceAll("[^0-9]", "");
    }
    
    public static String getSystemArea(){
        Properties properties = new Properties();
        try {
            properties.load(new ClassPathResource("/prop.properties").getInputStream());
        } catch (Exception e) {
        }
        return properties.getProperty("SYSTEM_AREA");
    }
    
    public static String setLocalLang(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		
		String strLang = "ko";
	       try{
	    	   
	    	   if(session == null){
	    		   HttpSession sessNew = req.getSession();
	    		   sessNew.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
	    		   strLang = sessNew.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
	    	   }else if(session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null){
	    		   session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.KOREAN);
	    		   strLang = session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
	    	   }else{
	    		   strLang = session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
	    	   }
	    	   
	    	   
	       }catch(Exception e){
	    	   StringUtil.printStackTrace(e);
	       }
	     return strLang;
	}
    
    public static Locale getLocalLang(HttpServletRequest req){
		HttpSession session = req.getSession(false);
		
		Locale strLang = null;
	       try{
	    	   
	    	   strLang = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
	    	   
	    	   
	       }catch(Exception e){
	    	   StringUtil.printStackTrace(e);
	       }
	     return strLang;
	}
    
    public static void printStackTrace(Exception e) {
    	if(RequestContextHolder.getRequestAttributes() != null) {
	    	HttpServletRequest request =((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	    	if(request != null) {
		    	HttpSession session = request.getSession(false);
		    	if(session !=null) {
		    		UserInfo info = (UserInfo)session.getAttribute("sess_user_info");
		    		if(info != null) {
		    			String sabun = info.getSabun();
		    			if(sabun != null)
		    				MDC.put("userId", sabun);
		    		}
		    	}
	    	}
    	}
    	logger.error ("", e);
    }
}