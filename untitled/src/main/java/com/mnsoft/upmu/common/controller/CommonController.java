package com.mnsoft.upmu.common.controller;


import java.net.URLDecoder;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mnsoft.upmu.carpurchase.vo.CarPurchase;
import com.mnsoft.upmu.common.service.CommonService;
import com.mnsoft.upmu.common.util.AES256Cipher;
import com.mnsoft.upmu.common.util.AbstractCntr;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.CurrentDateTime;
import com.mnsoft.upmu.common.util.JSONBaseArray;
import com.mnsoft.upmu.common.util.JSONBaseVO;
import com.mnsoft.upmu.common.util.MpointCall;
import com.mnsoft.upmu.common.util.RSAutil;
import com.mnsoft.upmu.common.util.StringUtil;
import com.mnsoft.upmu.common.util.ws.SitemapWSSoapProxy;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.ApprovalAw;
import com.mnsoft.upmu.common.vo.Common;
import com.mnsoft.upmu.common.vo.CommonAppl;
import com.mnsoft.upmu.common.vo.DateInfo;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.common.vo.GroupInfo;
import com.mnsoft.upmu.common.vo.Mpoint;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.personalWelfare.vo.HealthCheck;
import com.mnsoft.upmu.personalWelfare.vo.IncentiveInfo;
import com.mnsoft.upmu.personalWelfare.vo.IncomeInfo;
import com.mnsoft.upmu.personalWelfare.vo.InsuranceInfo;
import com.mnsoft.upmu.personalWelfare.vo.PromotionPoint;
import com.mnsoft.upmu.personalWelfare.vo.ViewDateInfo;
import com.mnsoft.upmu.resort.service.ResortService;
import com.mnsoft.upmu.resort.vo.Resort;
import com.mnsoft.upmu.system.service.SystemService;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.yearEducation.vo.YearEducation;

import net.sf.json.JSONObject;



@Controller
public class CommonController {
	// 자동 의존주입
	@Resource(name = "commonService")
	private CommonService commonService;
	
	@Resource(name = "systemService")
	private SystemService systemService;
	
	@Resource(name = "resortService")
	private ResortService resortService;	
	

	@RequestMapping("/index.do")
	public String index(HttpServletRequest request, Model model) throws Exception{
		
		RSAutil rsa = new RSAutil();
		// RSA 키 생성
		rsa.initRsa(request);
		
		TaskNote vo = new TaskNote();
		
		vo.setTask_cd("LO");
		
		TaskNote taskNote = commonService.selectAdminInfo(vo);

		if(StringUtil.isNullToString(taskNote.getNt_content()).equals("")) {
			model.addAttribute("contents", ""); //관리자정보
		}else{
			model.addAttribute("contents", taskNote.getNt_content()); //관리자정보
		}

		String strLang = StringUtil.setLocalLang(request);
		model.addAttribute("lang",strLang);
		
		return "/index";
	}
	
	/*
	@RequestMapping("/indexAdmin.do")
	public String indexAdmin(HttpServletRequest request,Model model) throws Exception{
		
		RSAutil rsa = new RSAutil();
		// RSA 키 생성
		rsa.initRsa(request);
		
		TaskNote vo = new TaskNote();
		
		vo.setTask_cd("LO");
		TaskNote taskNote = commonService.selectAdminInfo(vo);
		if(StringUtil.isNullToString(taskNote.getNt_content()).equals("")) {
			model.addAttribute("contents", ""); //관리자정보
		}else{
			System.out.println("taskNote.getNt_content():"+taskNote.getNt_content());
			model.addAttribute("contents", taskNote.getNt_content()); //관리자정보
		}
		
		String strLang = StringUtil.setLocalLang(request);
		model.addAttribute("lang",strLang);
		
		return "/indexAdmin";
	}
	*/
	
	@RequestMapping("/indexMail.do")
	public String indexMail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{

		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");

		if(userinfo != null) {
			String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
			AES256Cipher aes256 = new AES256Cipher(key);
			String url = aes256.aesDecode(request.getParameter("url"));
			
			url = url.replace(".do", "");
			model.addAttribute("url", url);

			return url;
			
		}else {
			RSAutil rsa = new RSAutil();
			String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
			AES256Cipher aes256 = new AES256Cipher(key);
			String url = aes256.aesDecode(request.getParameter("url"));
			String appr_no = aes256.aesDecode(request.getParameter("appr_no"));
			model.addAttribute("url", url);
			model.addAttribute("appr_no", appr_no);
			
			Approval apprVo = new Approval();
			apprVo.setAppr_no(appr_no);
			String apprDocUrl = commonService.selectApprDocUrl(apprVo);
			model.addAttribute("appr_doc_no", apprDocUrl);
			
			// RSA 키 생성
			rsa.initRsa(request);
			
			TaskNote vo = new TaskNote();
			
			vo.setTask_cd("LO");
			TaskNote taskNote = commonService.selectAdminInfo(vo);
			if(StringUtil.isNullToString(taskNote.getNt_content()).equals("")) {
				model.addAttribute("contents", ""); //관리자정보
			}else{
				model.addAttribute("contents", taskNote.getNt_content()); //관리자정보
			}
			
			String strLang = StringUtil.setLocalLang(request);
			model.addAttribute("lang",strLang);

			return "/indexMail";
		}
	}
	
	@RequestMapping("/indexApprovalMail.do")
	public String indexApprovalMail(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		
		//세션체크
		HttpSession session = request.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");
		
		String moveUrl = "/indexApprovalMail";
		
		try {
			
		
			if(userinfo != null) {
				
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesDecode(request.getParameter("url"));
				
				String apprNo = URLDecoder.decode(request.getParameter("appr_no"));
				
				String appr_no = aes256.aesDecode(apprNo);
				url = "/approval"+url.replace(".do", "");
				model.addAttribute("url", url);
				model.addAttribute("appr_no", appr_no);
				
				Approval apprVo = new Approval();
				apprVo.setAppr_no(appr_no);
				String apprDocUrl = commonService.selectApprDocUrl(apprVo);
				model.addAttribute("appr_doc_no", apprDocUrl);
				moveUrl = url;
				
			}else {
				RSAutil rsa = new RSAutil();
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesDecode(request.getParameter("url"));
				String appr_no = aes256.aesDecode(request.getParameter("appr_no"));
				model.addAttribute("url", url);
				model.addAttribute("appr_no", appr_no);
				
				Approval apprVo = new Approval();
				apprVo.setAppr_no(appr_no);
				String apprDocUrl = commonService.selectApprDocUrl(apprVo);
				model.addAttribute("appr_doc_no", apprDocUrl);
				
				// RSA 키 생성
				rsa.initRsa(request);
				
				TaskNote vo = new TaskNote();
				
				vo.setTask_cd("LO");
				TaskNote taskNote = commonService.selectAdminInfo(vo);
				if(StringUtil.isNullToString(taskNote.getNt_content()).equals("")) {
					model.addAttribute("contents", ""); //관리자정보
				}else{
					model.addAttribute("contents", taskNote.getNt_content()); //관리자정보
				}
				
				String strLang = StringUtil.setLocalLang(request);
				model.addAttribute("lang",strLang);
				moveUrl = "/indexApprovalMail";
			}
			
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
		}
		return moveUrl;
		
	}

	@RequestMapping("/main.do")
	public String main(Model model) throws Exception{
		return "main";
	}
	@RequestMapping("/main1.do")
	public String main1(Model model) throws Exception{
		return "main1";
	}

	@RequestMapping("/sessionError.do")
	public String sessionError(Model model) throws Exception{
		return "/common/sessionError";
	}
	
	@RequestMapping("/authError.do")
	public String authError(Model model) throws Exception{
		return "/common/authError";
	}
	
	@RequestMapping("/ssoLoginError.do")
	public String ssoLoginError(Model model) throws Exception{
		return "/common/ssoLoginError";
	}
	
	@RequestMapping("/approvalSign.do")
	public String approvalSign(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalSign";
	}
	@RequestMapping("/approvalProgress.do")
	public String approvalProgress(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalProgress";
	}
	@RequestMapping("/approvalSave.do")
	public String approvalSave(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalSave";
	}
	@RequestMapping("/approvalReject.do")
	public String approvalReject(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalReject";
	}
	@RequestMapping("/approvalView.do")
	public String approvalView(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalView";
	}
	@RequestMapping("/approvalUpdateCharge.do")
	public String approvalUpdateCharge(Model model) throws Exception{
		return "/approval/approvalUpdateCharge";
	}
	@RequestMapping("/approvalDue.do")
	public String approvalDue(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalDue";
	}
	@RequestMapping("/approvalRefDoc.do")
	public String approvalRefDoc(Model model) throws Exception{
		return "/approval/approvalRefDoc";
	}
	@RequestMapping("/approvalLink.do")
	public String approvalLink(Model model) throws Exception{
		return "/approval/approvalLink";
	}
	@RequestMapping("/approvalComplate.do")
	public String approvalComplate(Model model, HttpServletRequest request) throws Exception{
		
		String appr_no = StringUtil.isNullToString(request.getParameter("appr_no"));
		String appr_doc_no = StringUtil.isNullToString(request.getParameter("appr_doc_no"));
		
		model.addAttribute("appr_no",appr_no);
		model.addAttribute("appr_doc_no",appr_doc_no);
		
		return "/approval/approvalComplate";
	}
	@RequestMapping("/approvalAppoint.do")
	public String approvalAppoint(Model model) throws Exception{
		return "/approval/approvalAppoint";
	}
	
	@RequestMapping("/logout.do")
	public String logout(Model model) throws Exception{
		return "/common/logout";
	}

	@ResponseBody
	@RequestMapping(value="/getCommonCombo.do", produces="application/json; charset=utf-8")
	public String getCommonCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;
		
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		
		String sess_locale = sess_info.getLocale();

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			code.setLocale(sess_locale);
			codeList = commonService.selectCodeList(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCommonCodeInfo.do", produces="application/json; charset=utf-8")
	public String getCommonCodeInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		String rtnData = "";
		try{
			
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = new JSONBaseArray(); 

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		
		List<Code> codeList = commonService.selectInpoproCombo();

		String lcd = "";
		
		int cnt = 0;
		
		for(Code targetBean : codeList){
			cnt++;
			
			if(lcd.equals("")){
				json = new JSONBaseVO();
				json.put("name", "-");
				json.put("value", "");
				jsonArr.add(json);
			}
			
			if(!lcd.equals("") && !lcd.equals(targetBean.getLcd())){
				jso.put(lcd, jsonArr);
				jsonArr = new JSONBaseArray(); 
				
				json = new JSONBaseVO();
				json.put("name", "-");
				json.put("value", "");
				jsonArr.add(json);
			}
			
			json = new JSONBaseVO();
			json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
			json.put("value", StringUtil.isNullToString(targetBean.getCd()));

			lcd = targetBean.getLcd();
			jsonArr.add(json);
			
			if(codeList.size() == cnt){
				jso.put(lcd, jsonArr);
				jsonArr = new JSONBaseArray(); 
			}
			
		}
		
		rtnData = jso.toString();
		System.out.println("rtnData:"+rtnData);
		}catch(Exception e){
		StringUtil.printStackTrace(e);
		}
		
	return rtnData;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getCommonComboEtc_1.do", produces="application/json; charset=utf-8")
	public String getCommonComboEtc_1(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			codeList = commonService.getCommonComboEtc_1(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCommonComboEtc_2.do", produces="application/json; charset=utf-8")
	public String getCommonComboEtc_2(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			codeList = commonService.getCommonComboEtc_2(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCommonComboEtc.do", produces="application/json; charset=utf-8")
	public String getCommonComboEtc(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd,
			@RequestParam(value="cd" , required=true) String cd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			code.setCd(cd);
			codeList = commonService.getCommonComboEtc(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCommonYhCombo.do", produces="application/json; charset=utf-8")
	public String getCommonYhCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			codeList = commonService.getCommonYhCombo(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}

	//근태 시작 시간 콤보 
	@ResponseBody
	@RequestMapping(value="/getWorkAttCombo.do", produces="application/json; charset=utf-8")
	public String getWorkCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new Code();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				codeList = commonService.selectWorkCodeList(code);

				if(codePair.length > 2){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", "선택");
						}else if(codePair[2].equals("H")){
							json.put("name", "-");
						}else{
							json.put("name", "전체");
						}
						json.put("value", "");

						jsonArr.add(json);
					}
				}

				for(Code targetBean : codeList){

					json = new JSONBaseVO();
					json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
					json.put("value", StringUtil.isNullToString(targetBean.getCd()));

					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}
		//ModelAndView modelData = new ModelAndView();
		//modelAndView.setViewName(AbstractCntr.DATA_JSON_PAGE);
		//modelAndView.addObject("uiType", "ajax");
		//modelData.addObject("data", jso.toString());
		String rtnData = jso.toString();
		return rtnData;
	}

	//업무코드 조회
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getWorkComboType.do", produces="application/json; charset=utf-8")
	public String getWorkComboType(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr1 = new JSONBaseArray();
		JSONBaseArray  jsonArr2 = new JSONBaseArray();
		JSONBaseArray  jsonArr3 = new JSONBaseArray();
		JSONBaseArray  jsonArr4 = new JSONBaseArray();
		JSONBaseArray  jsonArr5 = new JSONBaseArray();
		JSONBaseArray  jsonArr6 = new JSONBaseArray();
		JSONBaseArray  jsonArr7 = new JSONBaseArray();
		JSONBaseArray  jsonArr8 = new JSONBaseArray();
		JSONBaseArray  jsonArr9 = new JSONBaseArray();
		JSONBaseArray  jsonArr10 = new JSONBaseArray();
		
		String comboName1 = "";
		String comboName2 = "";
		String comboName3 = "";
		String comboName4 = "";
		String comboName5 = "";
		String comboName6 = "";
		String comboName7 = "";
		String comboName8 = "";
		String comboName9 = "";
		String comboName10 = "";

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = new Code();
		List<Code> codeList = commonService.selectWorkComboType(code);

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");
			if( codePair.length > 1)
			{

				if(codePair.length > 2){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", "선택");
						}else if(codePair[2].equals("H")){
							json.put("name", "-");
						}else{
							json.put("name", "전체");
						}
						json.put("value", "");
						
						if(i==0){
							jsonArr1.add(json);
							comboName1 = codePair[0];
						}else if(i==1){
							jsonArr2.add(json);
							comboName2 = codePair[0];
						}else if(i==2){
							jsonArr3.add(json);
							comboName3 = codePair[0];
						}else if(i==3){
							jsonArr4.add(json);
							comboName4 = codePair[0];
						}else if(i==4){
							jsonArr5.add(json);
							comboName5 = codePair[0];
						}else if(i==5){
							jsonArr6.add(json);
							comboName6 = codePair[0];
						}else if(i==6){
							jsonArr7.add(json);
							comboName7 = codePair[0];
						}else if(i==7){
							jsonArr8.add(json);
							comboName8 = codePair[0];
						}else if(i==8){
							jsonArr9.add(json);
							comboName9 = codePair[0];
						}else if(i==9){
							jsonArr10.add(json);
							comboName10 = codePair[0];
						}
					}
				}
				
			}else{
				break;
			}
		}
		
		for(Code targetBean : codeList){

			json = new JSONBaseVO();
			json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
			json.put("value", StringUtil.isNullToString(targetBean.getCd()));
			if(targetBean.getAtt_type().equals("A")) {
				jsonArr1.add(json);
			}else if(targetBean.getAtt_type().equals("B")) {
				jsonArr2.add(json);
			}else if(targetBean.getAtt_type().equals("C")) {
				jsonArr3.add(json);
			}else if(targetBean.getAtt_type().equals("D")) {
				jsonArr4.add(json);
			}else if(targetBean.getAtt_type().equals("E")) {
				jsonArr5.add(json);
			}else if(targetBean.getAtt_type().equals("F")) {
				jsonArr6.add(json);
			}else if(targetBean.getAtt_type().equals("G")) {
				jsonArr7.add(json);
			}else if(targetBean.getAtt_type().equals("H")) {
				jsonArr8.add(json);
			}else if(targetBean.getAtt_type().equals("I")) {
				jsonArr9.add(json);
			}else if(targetBean.getAtt_type().equals("J")) {
				jsonArr10.add(json);
			}
			
		}
		jso.put(comboName1, jsonArr1);
		jso.put(comboName2, jsonArr2);
		jso.put(comboName3, jsonArr3);
		jso.put(comboName4, jsonArr4);
		jso.put(comboName5, jsonArr5);
		jso.put(comboName6, jsonArr6);
		jso.put(comboName7, jsonArr7);
		jso.put(comboName8, jsonArr8);
		jso.put(comboName9, jsonArr9);
		jso.put(comboName10, jsonArr10);
		
		String rtnData = jso.toString();
		return rtnData;
	}

	@ResponseBody
	@RequestMapping(value="/getCommonMenuCombo.do", produces="application/json; charset=utf-8")
	public String getCommonMenuCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			codeList = commonService.selectComonMenuList(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	@ResponseBody
	@RequestMapping(value="/doLogin.do", produces="application/json; charset=utf-8")
	public String doLogin(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String param )throws Exception{

		UserInfo loginInfo = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);
		CommonMessage message = new CommonMessage();

		String userId = loginInfo.getUser_id();
		String userPw = loginInfo.getUser_pw();
		
		HttpSession session = req.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAutil.RSA_WEB_KEY);
 
        RSAutil rsa = new RSAutil();
        // 복호화
        userId = rsa.decryptRsa(privateKey, userId);
        userPw = rsa.decryptRsa(privateKey, userPw);
        
        loginInfo.setUser_id(userId);
        loginInfo.setUser_pw(userPw);
 
		boolean ldapFlag = true;
		
		/*
		if(StringUtil.getSystemArea().equals("REAL")){
			ldapFlag = isAuthenticatedUser(userId, userPw);
		}
		else {
			ldapFlag = true;
		}
		*/
		

		UserInfo userInfo =commonService.selectUserInfo(loginInfo);

		commonService.setSessionInfo(req, userInfo);
/*		
		try {
			//LDAP인증 성공
			if(ldapFlag){
				UserInfo userInfo =commonService.selectUserInfo(loginInfo);

				if(userInfo != null){
					commonService.setSessionInfo(req, userInfo);
					message.setResult("S");
					
					// 개인키 삭제
			        //session.removeAttribute(RSAutil.RSA_WEB_KEY);
				}else{
					message.setResult("F");
				}
			}else{
				message.setResult("F");
			}
		}catch(Exception e){
			StringUtil.printStackTrace(e);
		}*/
		message.setResult("S");
		return JSONObject.fromObject(message).toString();
	}

	public static boolean isAuthenticatedUser(String userId, String userPw) {
		boolean isAuthenticated = false;


		String url = StringUtil.getPropinfo().getProperty("LDAP_URL");
		String domain = StringUtil.getPropinfo().getProperty("LDAP_DOMAIN")+"\\"+userId;

		//임직원
		String searchBase = StringUtil.getPropinfo().getProperty("LDAP_OU_ADM");

		//협력사
		if(userId.substring(0, 1).equals("O")){
			searchBase = StringUtil.getPropinfo().getProperty("LDAP_OU_MNL");
		}

		Hashtable<String, String> properties = new Hashtable<String, String>();

		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		properties.put(Context.PROVIDER_URL, url);
		properties.put(Context.SECURITY_AUTHENTICATION, "simple");
		properties.put(Context.SECURITY_PRINCIPAL, domain);
		properties.put(Context.SECURITY_CREDENTIALS, userPw);
		try {
			DirContext con = new InitialDirContext(properties);
			isAuthenticated = true;
			con.close();
		} catch (NamingException e) {

		}
		return isAuthenticated;
	}
	
	//업무지원센터 개별인원 로그인 예외처리(일단 보류처리함 사용X)
  	@RequestMapping(value="/doSelectHelpDesk.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectHelpDesk(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{
  		
  		UserInfo vo = (UserInfo)AbstractCntr.getJsonToBean(params, UserInfo.class);
		
  		CommonMessage data = commonService.doSelectHelpDesk(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",data);
		return modelData;
	}
	
	@ResponseBody
	@RequestMapping(value="/doLoginAdmin.do", produces="application/json; charset=utf-8")
	public String doLoginAdmin(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String param )throws Exception{

		UserInfo loginInfo = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);
		CommonMessage message = new CommonMessage();

		String userId = loginInfo.getUser_id();
		String userPw = loginInfo.getUser_pw();
		
		HttpSession session = req.getSession();
        PrivateKey privateKey = (PrivateKey) session.getAttribute(RSAutil.RSA_WEB_KEY);
 
        RSAutil rsa = new RSAutil();
        // 복호화
        userId = rsa.decryptRsa(privateKey, userId);
        userPw = rsa.decryptRsa(privateKey, userPw);
        
        loginInfo.setUser_id(userId);
        loginInfo.setUser_pw(userPw);

		//LDAP 인증
		boolean ldapFlag = true;

		try {
			//LDAP인증 성공
			if(ldapFlag){
				UserInfo userInfo =commonService.selectUserInfo(loginInfo);

				if(userInfo != null){
					commonService.setSessionInfo(req, userInfo);
					message.setResult("S");
					// 개인키 삭제
			        //session.removeAttribute(RSAutil.RSA_WEB_KEY);
				}else{
					message.setResult("F");
				}
			}else{
				message.setResult("F");
			}
		}catch(Exception e){
			StringUtil.printStackTrace(e);
		}
		return JSONObject.fromObject(message).toString();
	}


	
	

	//부서콤보 조회
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getCommonDeptCombo.do", produces="application/json; charset=utf-8")
	public String getCommonDeptCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd,
			@RequestParam(value="auth_yn" , required=false) String auth_yn) throws Exception{

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		codePair = codknd.split(":");

		if( codePair.length > 1)
		{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			code.setAuth_yn(StringUtil.isNullToString(auth_yn));
			codeList = commonService.selectCommonDeptList(code, req);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else if(codePair[2].equals("A")){
						json.put("name", "전체");
					}else{
						json.put("name", " ");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){
				String dept_tab = "";

				if(codePair.length > 3){
					if(codePair[3].equals("Y")){

						String dept_lv = StringUtil.isNullToString(targetBean.getCd_tc());

						if(dept_lv.equals("")){
							dept_lv = "0";
						}
						int cnt = Integer.parseInt(dept_lv);

						if(cnt > 1){
							String blk = "";
							for(int i=1 ; i<cnt ; i++){
								blk += "　";
							}
							dept_tab  = blk + "└ ";
						}
					}
				}

				json = new JSONBaseVO();
				json.put("name", dept_tab+StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}

			jso.put(codePair[0], jsonArr);
		}else{
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	// 특허페이지 부서콤보 조회
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping(value="/getPatentDeptCombo.do", produces="application/json; charset=utf-8")
		public String getPatentDeptCombo(HttpServletRequest req, HttpServletResponse res,
				@RequestParam(value="codknd" , required=true) String codknd,
				@RequestParam(value="auth_yn" , required=false) String auth_yn) throws Exception{

			JSONBaseVO jso = new JSONBaseVO();
			JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			String []codePair = null;

			Code code = null;
			List<Code> codeList = null;

			codePair = codknd.split(":");

			if( codePair.length > 1)
			{
				code = new Code();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				code.setAuth_yn(StringUtil.isNullToString(auth_yn));
				codeList = commonService.selectPatentDeptList(code, req);

				if(codePair.length > 2){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", "선택");
						}else if(codePair[2].equals("H")){
							json.put("name", "-");
						}else if(codePair[2].equals("A")){
							json.put("name", "전체");
						}else{
							json.put("name", " ");
						}
						json.put("value", "");

						jsonArr.add(json);
					}
				}

				for(Code targetBean : codeList){
					String dept_tab = "";

					if(codePair.length > 3){
						if(codePair[3].equals("Y")){

							String dept_lv = StringUtil.isNullToString(targetBean.getCd_tc());

							if(dept_lv.equals("")){
								dept_lv = "0";
							}
							int cnt = Integer.parseInt(dept_lv);

							if(cnt > 1){
								String blk = "";
								for(int i=1 ; i<cnt ; i++){
									blk += "　";
								}
								dept_tab  = blk + "└ ";
							}
						}
					}

					json = new JSONBaseVO();
					json.put("name", dept_tab+StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
					json.put("value", StringUtil.isNullToString(targetBean.getCd()));

					jsonArr.add(json);
				}

				jso.put(codePair[0], jsonArr);
			}else{
			}
			String rtnData = jso.toString();
		return rtnData;
		}

	//사용자 검색
	@ResponseBody
	@RequestMapping(value="/selectCommonUserList.do", produces="application/json; charset=utf-8")
	public String selectCommonUserList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String param )throws Exception{

		UserInfo searchParam = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);

		List<UserInfo> user_list = commonService.selectCommonUserList(searchParam);

		JSONBaseVO jso = new JSONBaseVO();
		jso.put("data", user_list);
		return JSONObject.fromObject(jso).toString();
	}

	//조직도 조회
	@RequestMapping(value="/selectOrgListForTree.do")
	public ModelAndView selectOrgListForTree(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String param )throws Exception{

		DeptInfo searchParam = (DeptInfo)AbstractCntr.getJsonToBean(param, DeptInfo.class);

		ModelAndView modelData = new ModelAndView("jsonView");
		List<DeptInfo> list = commonService.selectOrgListForTree(searchParam);

		modelData.addObject("data", list);

		return modelData;
	}
	
	//리더기준 조직도 조회
	@RequestMapping(value="/selectOrgListForTreeLeader.do")
	public ModelAndView selectOrgListForTreeLeader(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String param )throws Exception{

		UserInfo searchParam = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);
		
		ModelAndView modelData = new ModelAndView("jsonView");
		try {
			List<DeptInfo> list = commonService.selectOrgListForTreeLeader(searchParam);
			modelData.addObject("data", list);
		} catch(Exception e) {
			StringUtil.printStackTrace(e);
		}

		return modelData;
	}

	@ResponseBody
	@RequestMapping(value="/selectOrgListForUser.do", produces="application/json; charset=utf-8")
	public String selectOrgListForUser(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String param )throws Exception{
		
		UserInfo searchParam = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);
		
		List<UserInfo> user_list = commonService.selectOrgListForUser(searchParam);
		
		JSONBaseVO jso = new JSONBaseVO();
		jso.put("data", user_list);
		return JSONObject.fromObject(jso).toString();
	}

	@ResponseBody
	@RequestMapping(value="/selectOrgListForDept.do", produces="application/json; charset=utf-8")
	public String selectOrgListForDept(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String param )throws Exception{
		
		UserInfo searchParam = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);
		
		List<UserInfo> dept_list = commonService.selectOrgListForDept(searchParam);
		
		JSONBaseVO jso = new JSONBaseVO();
		jso.put("data", dept_list);
		return JSONObject.fromObject(jso).toString();
	}

	@RequestMapping(value="/doSSOLogin.do")
	public String doSSOLogin(HttpServletRequest req, HttpServletResponse res )throws Exception{
		
		try{
			String orgnid = StringUtil.isNullToString(req.getParameter("orgnid"));
			String accntid = StringUtil.isNullToString(req.getParameter("accntid"));
			String calltime = StringUtil.isNullToString(req.getParameter("calltime"));

			String key = StringUtil.getPropinfo().getProperty("ASE_KEY");

			AES256Cipher aes256 = new AES256Cipher(key);


			orgnid = aes256.aesDecode(orgnid);
			accntid = aes256.aesDecode(accntid);
			calltime = aes256.aesDecode(calltime);
			
			orgnid = orgnid.toUpperCase();

			if(!StringUtil.isNullToString(accntid).equals("")){
				UserInfo loginInfo = new UserInfo();
				loginInfo.setUser_id(accntid);
				UserInfo userInfo =commonService.selectUserInfo(loginInfo);
				if(userInfo != null){
					
					Calendar calendar = Calendar.getInstance();
			        Date date = calendar.getTime();
			        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					Date df1 = dateFormat.parse(today);// 현재시간
					Date df2 = dateFormat.parse(calltime);

					long diff = df1.getTime() - df2.getTime();
					long diffSec = diff/1000;
					if(diffSec > 5){
						//5초초과
						res.sendRedirect("/ssoLoginError.do");
					}else{
						//SSO인증 성공
						commonService.setSessionInfo(req, userInfo);//세션생성
						res.sendRedirect("/work/temp.do?menuId=1001&menuType=Y");
					}
				}else{
					//사용자 정보 없음 에러처리
					res.sendRedirect("/ssoLoginError.do");
				}
			}else {
				res.sendRedirect("/ssoLoginError.do");
			}
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			res.sendRedirect("/ssoLoginError.do");
		}

		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getCommonLinkCombo.do", produces="application/json; charset=utf-8")
	public ModelAndView getCommonLinkCombo(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute("Code") Code code, Model model) throws Exception{
		System.out.println("확ㄱ인12313" + code.getLocale());
		List<Code> typeACombo = null;
		List<Code> typeBCombo = null;

		Code aCode = new Code();
		aCode.setCodknd("A");
		aCode.setLocale(code.getLocale());
		
		Code bCode = new Code();
		bCode.setCodknd("B");
		bCode.setLocale(code.getLocale());

		typeACombo = commonService.selectCommonLinkList(aCode);
		typeBCombo = commonService.selectCommonLinkList(bCode);
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("aType",typeACombo);
		modelData.addObject("bType",typeBCombo);

		return modelData;
	}
	
	   /**
     * request page : 파일삭제
     * function     :
     * parameter    : 검색조건
     * output type  : form
     */
    @RequestMapping(value="/doCommonFileDelete.do", produces="application/json; charset=utf-8")
    public ModelAndView doCommonFileDelete(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
        //조회조건 설정
        FileInfo param = (FileInfo)AbstractCntr.getJsonToBean(paramJson, FileInfo.class);
        //검색
        CommonMessage rtnDto = commonService.deleteFileMgmt(req, param);

        ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",rtnDto);
		return modelData;
    }
    
    /**
     * request page : 공통 파일 다운로드
     * function     : doCommonFileDownload
     * parameter    : 원래 파일이름, 변경된 파일이름
     * output type  :
     */
    @RequestMapping(value="/doCommonFileDownload.do")
    public ModelAndView doCommonFileDownload(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="org_file_name" , required=true) String org_file_name,
            @RequestParam(value="mfy_file_name" , required=true) String mfy_file_name,
            @RequestParam(value="file_fold" , required=true) String file_fold
            ) throws Exception{

        HashMap<String, String> fileInfoMap = new HashMap<String, String>();
        fileInfoMap.put("fileRealName", mfy_file_name);
        fileInfoMap.put("fileName", org_file_name);
        fileInfoMap.put("folderName", file_fold);

        return new ModelAndView("download","fileData",fileInfoMap);
    }
    
    //사용자 검색
  	@ResponseBody
  	@RequestMapping(value="/selectUserListForDept.do", produces="application/json; charset=utf-8")
  	public String selectUserListForDept(HttpServletRequest req, HttpServletResponse res,
  			@RequestParam(value="paramJson", required=true) String param )throws Exception{

  		UserInfo searchParam = (UserInfo)AbstractCntr.getJsonToBean(param, UserInfo.class);

  		List<UserInfo> user_list = commonService.selectUserListForDept(searchParam);

  		JSONBaseVO jso = new JSONBaseVO();
  		jso.put("data", user_list);
  		return JSONObject.fromObject(jso).toString();
  	}
	
	//공휴일여부 체크
  	@SuppressWarnings("unchecked")
	@ResponseBody
  	@RequestMapping(value="/doCheckHoliday.do", produces="application/json; charset=utf-8")
  	public String doCheckHoliday(HttpServletRequest req, HttpServletResponse res,
  			@RequestParam(value="paramJson", required=true) String param )throws Exception{

  		DateInfo vo = (DateInfo)AbstractCntr.getJsonToBean(param, DateInfo.class);


  		DateInfo info = commonService.selectCheckHoliday(vo);
  		
  		if(info == null) {
  			info = new DateInfo();
  			info.setGubun("D");
  		}

  		JSONBaseVO jso = new JSONBaseVO();
  		jso.put("data", info);
  		return JSONObject.fromObject(jso).toString();
  	}
  	
  	
  	//결재할 리스트 조회
  	@RequestMapping(value="/doSelectApprovalSignList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalSignList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		UserInfo patentVo = new UserInfo();
		
		patentVo.setUser_id(vo.getAppr_id());
		UserInfo patentAuth = commonService.selectParentAuth(patentVo);
		
		//특허관리자 확인
		System.out.println("특허관리자 유무" + patentAuth.getPatent_auth_yn());
		if(patentAuth.getPatent_auth_yn().equals("Y")){
			//특허관리자인 경우
			vo.setPatent_auth_yn("Y");
		}else {
			//아닌 경우
			vo.setPatent_auth_yn("");
		}
		List<Approval> list = commonService.selectApprovalSignList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	//예결함  리스트 조회
  	@RequestMapping(value="/doSelectApprovalDueList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalDueList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalDueList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	//진행할 리스트 조회
  	@RequestMapping(value="/doSelectApprovalProgressList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalProgressList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalProgressList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	//작성중 문서 리스트 조회
  	@RequestMapping(value="/doSelectApprovalSaveList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalSaveList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalSaveList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	//반려함 리스트 조회
  	@RequestMapping(value="/doSelectApprovalRejectList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalRejectList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalRejectList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	//완료함  리스트 조회
  	@RequestMapping(value="/doSelectApprovalComplateList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalComplateList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalComplateList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
    //신청서 결재창 기본정보 조회
  	@RequestMapping(value="/selectApprInitInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView selectApprInitInfo(HttpServletRequest req,
			@RequestParam(value="task_cd", required=true) String task_cd,
			@RequestParam(value="locale", required=true) String locale)throws Exception{
		
  		//유의사항 조회
  		TaskNote taskNoteVo = new TaskNote();
  		taskNoteVo.setTask_cd(task_cd);
  		TaskNote noteInfo = systemService.selectNoteInfo(taskNoteVo);
  		
  		//업무별 결재선 조회
  		Approval apprVo = new Approval();
  		apprVo.setTask_cd(task_cd);
  		apprVo.setLocale(locale);
  		
  		List<Approval> apprList = null;
  		List<Approval> apprCoopList = null;
  		
  		if(!task_cd.equals("CI")) {
  			apprList = commonService.selectApprLineInfoByTask(req, apprVo);
  			
  			// OSI_HM801-790 정보처리요청서 협조부서 별도처리
  			if(task_cd.equals("IP")) {
  				apprCoopList = commonService.selectApprCoopLineInfoByIP(req, apprVo);
  			} else {
  				apprCoopList = commonService.selectApprCoopLineInfoByTask(req, apprVo);
  			}
  		}
  		
  		Approval applapprInfo = commonService.selectApplApprInfo(req, apprVo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("noteInfo",noteInfo);
		modelData.addObject("apprList",apprList);
		modelData.addObject("apprCoopList",apprCoopList);
		modelData.addObject("applapprInfo",applapprInfo);
		return modelData;
	}
  	
  //신청서 결재창 기본정보 조회
  	@RequestMapping(value="/selectApprInitInfo2.do", produces="application/json; charset=utf-8")
  	public ModelAndView selectApprInitInfo2(HttpServletRequest req,
			@RequestParam(value="task_cd", required=true) String task_cd,
			@RequestParam(value="locale", required=true) String locale,
			@RequestParam(value="apprTypeFlag", required=true) String apprTypeFlag,
			@RequestParam(value="selectApprType", required=true) String selectApprType)throws Exception{
		
  		//유의사항 조회
  		TaskNote taskNoteVo = new TaskNote();
  		taskNoteVo.setTask_cd(task_cd);
  		TaskNote noteInfo = systemService.selectNoteInfo(taskNoteVo);
  		
  		//업무별 결재선 조회
  		Approval apprVo = new Approval();
  		apprVo.setTask_cd(task_cd);
  		apprVo.setLocale(locale);
  		apprVo.setApprTypeFlag(apprTypeFlag);
  		apprVo.setSelectApprType(selectApprType);
  		
  		List<Approval> apprList = null;
  		List<Approval> apprCoopList = null;
  		
  		if(!task_cd.equals("CI")) {
  			apprList = commonService.selectApprLineInfoByTask(req, apprVo);
  			
  			apprCoopList = commonService.selectApprCoopLineInfoByTask(req, apprVo);
  		}
  		
  		Approval applapprInfo = commonService.selectApplApprInfo(req, apprVo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("noteInfo",noteInfo);
		modelData.addObject("apprList",apprList);
		modelData.addObject("apprCoopList",apprCoopList);
		modelData.addObject("applapprInfo",applapprInfo);
		return modelData;
	}
  	
  //신청서 결재창 기본정보 조회
  	@RequestMapping(value="/selectApprViewInitInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView selectApprViewInitInfo(HttpServletRequest req,
			@RequestParam(value="appr_no", required=true) String appr_no,
			@RequestParam(value="locale", required=true) String locale)throws Exception{
  		
  		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		
  		Approval approvalVo = new Approval();
  		approvalVo.setAppr_no(appr_no);
  		approvalVo.setAppr_id(sess_user_id);
  		approvalVo.setLocale(locale);
  		
  		//결재정보 조회
  		Approval apprInfo = commonService.selectApprInfo(approvalVo);
  		
  		//결재리스트 조회
  		List<Approval> apprlist = commonService.selectApprList(approvalVo);
  		
  		//기결재문서 조회
  		List<Approval> refDocList = commonService.selectApprRefDocList(approvalVo);
  		
  		//참조자 조회
  		List<Approval> refUserList = commonService.selectApprRefUSerList(approvalVo);
  		
  		CommonAppl commonApplVo = new CommonAppl();
  		commonApplVo.setAc_task_cd(apprInfo.getTask_cd());
  		
  		//신청서 기본정보
  		CommonAppl applInfo = commonService.selectTaskApplInfo(commonApplVo);
  		
  		//수정사유 조회
  		List<Approval> updateResnList = commonService.selectApprUpdateResnList(approvalVo);

		//협조 담당자 조회 
		List<Approval> chargeList = commonService.selectApprChargeList(approvalVo);
		//협조 담당자 카운트 
		Approval chargeCnt = commonService.selectApprChargeCnt(approvalVo);
		

		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("apprInfo",apprInfo);
		modelData.addObject("apprList",apprlist);
		modelData.addObject("refDocList",refDocList);
		modelData.addObject("refUserList",refUserList);
		modelData.addObject("applInfo",applInfo);
		modelData.addObject("updateResnList",updateResnList);
		modelData.addObject("chargeList",chargeList);
		modelData.addObject("chargeCnt",chargeCnt);
		return modelData;
	}
  	
  	@RequestMapping(value="/selectObDefaultRefUser.do", produces="application/json; charset=utf-8")
  	public ModelAndView selectObDefaultRefUser(HttpServletRequest req,
  			@RequestParam(value="task_cd", required=true) String task_cd)throws Exception{

  		Approval approvalVo = new Approval();
  		approvalVo.setTask_cd(task_cd);
  		
		List<Approval> data = commonService.selectObDefaultRefUser(approvalVo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("refUserList",data);
		return modelData;
	}
  	
  	//결재 승인
  	@ResponseBody
  	@RequestMapping(value="/doUpdateApprConfirm.do", produces="application/json; charset=utf-8")
  	public String doUpdateApprConfirm(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.updateApprConfirm(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	//결재 반려
  	@ResponseBody
  	@RequestMapping(value="/doUpdateApprReject.do", produces="application/json; charset=utf-8")
  	public String doUpdateApprReject(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.updateApprReject(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
    //사용자검색(이름)
  	@RequestMapping(value="/doSelectUserListFoName.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectUserListFoName(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		UserInfo vo = (UserInfo)AbstractCntr.getJsonToBean(paramJson, UserInfo.class);
		List<UserInfo> list = commonService.selectUserListFoName(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}

	@RequestMapping(value="/doSelectDeptListFoName.do", produces="application/json; charset=utf-8")
	public ModelAndView doSelectDeptListFoName(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
	
	DeptInfo vo = (DeptInfo)AbstractCntr.getJsonToBean(paramJson, DeptInfo.class);
	List<DeptInfo> list = commonService.selectDeptListFoName(vo);	
	
	ModelAndView modelData = new ModelAndView("jsonView");
	modelData.addObject("data",list);
		return modelData;
	}

	@RequestMapping(value="/application/applicationAppl.do")
	public String applicationAppl(HttpServletRequest req, HttpServletResponse res,  Model model) throws Exception{
		/*CommonAppl vo = new CommonAppl();
		vo.setSearchMenuId(menuId);
		List<CommonAppl> list = commonService.selectApplicationAppl(vo);

		model.addAttribute("taskCd", list.get(0).getAc_task_cd());*/
		return "application/applicationAppl";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/common/saveCommonAppl.do", produces="application/json; charset=utf-8")
	public String saveCommonAppl(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{

		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);

		CommonMessage message = commonService.saveCommonAppl(req, vo, apprVo, apprList);
		return JSONObject.fromObject(message).toString();
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/common/doApproveCommonAppl.do", produces="application/json; charset=utf-8")
	public String doApproveCommonAppl(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);
		
		CommonMessage message = commonService.approveCommonAppl(req, vo, apprVo, apprList);
		return JSONObject.fromObject(message).toString();
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/common/doUpdateCommonAppl.do", produces="application/json; charset=utf-8")
	public String doUpdateCommonAppl(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);
		
		CommonMessage message = commonService.updateCommonAppl(req, vo, apprVo, apprList);
		return JSONObject.fromObject(message).toString();
	}
	
	@RequestMapping(value="/common/doSearchApplInfoMenu.do")
	public ModelAndView doSearchApplInfoMenu(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		
		CommonAppl apprInfo = commonService.searchApplInfoMenu(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",apprInfo);
		return modelData;
	}

	@RequestMapping(value="/common/doSelectCommonApplInfo.do")
	public ModelAndView doSelectCommonApplInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		
		CommonAppl apprInfo = commonService.selectCommonApplInfo(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("apprInfo",apprInfo);
		return modelData;
	}
	
	
	//결재선 정보저장
	@ResponseBody
	@RequestMapping(value="/doSaveApprLine.do", produces="application/json; charset=utf-8")
	public String doSaveApprLine(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="params", required=true) String params)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = (List<Approval>)AbstractCntr.getJsonToList(params, Approval.class);

		CommonMessage message = commonService.insertApprLine(vo, list);
		return JSONObject.fromObject(message).toString();
	}
	
	//결재선 정보 삭제
	@ResponseBody
	@RequestMapping(value="/doDeleteApprLine.do", produces="application/json; charset=utf-8")
	public String doDeleteApprLine(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

		CommonMessage message = commonService.deleteApprLine(vo);
		return JSONObject.fromObject(message).toString();
	}
	
	//결재선 정보 조회
	@RequestMapping(value="/doSelectApprLine.do", produces="application/json; charset=utf-8")
	public ModelAndView doSelectApprLine(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

	Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
  		
  		List<Approval> list = commonService.selectApprLine(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}

	//결재선 정보 상세조회
	@RequestMapping(value="/doSelectApprLineDetail.do", produces="application/json; charset=utf-8")
	public ModelAndView doSelectApprLineDetail(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

		List<Approval> list = commonService.selectApprLineDetail(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}

	@RequestMapping(value="/application/applicationApplList.do")
	public String applicationList(@RequestParam String menuId, @RequestParam String menuType, @ModelAttribute("Code") Code code, Model model) throws Exception{
		CommonAppl vo = new CommonAppl();
		vo.setSearchMenuId(menuId);
		List<CommonAppl> list = commonService.selectApplicationAppl(vo);

		model.addAttribute("taskCd", list.get(0).getAc_task_cd());
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "application/applicationList";
	}
	
	@RequestMapping(value="/common/selectCommonAppl.do")
	public ModelAndView selectCommonAppl(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		
		List<CommonAppl> list = commonService.selectCommonAppl(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}

	@RequestMapping(value="/common/doExcelCommonApplList.do")
	public ModelAndView doExcelCommonApplList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
	
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		
		String columnIds="";
		String columnNames="";
		String fileName="";
		
		columnIds="row_num,dept_nm,user_nm,req_dt,sts_nm,edit_user_nm,edit_dt";
		columnNames="No.,부서,이름,신청일자,진행상태,수정자,수정일";
		fileName ="신청서_"+CurrentDateTime.getDate();
		List<CommonAppl> list = commonService.selectCommonAppl(vo);
		
	
		ModelAndView modelAndView = new ModelAndView();
		
		List<String> recNames = Arrays.asList(StringUtils.split(columnIds.replaceAll(" ", ""),","));
		List<String> recTitles = Arrays.asList(StringUtils.split(columnNames.replaceAll(" ", ""),","));			 
	
		modelAndView.addObject("records", list);
		modelAndView.addObject("recNames", recNames);
		modelAndView.addObject("recTitles", recTitles);
		modelAndView.addObject("fileName", fileName);
		modelAndView.setViewName("excelDownloadView");
		
		return modelAndView;
	}
	@RequestMapping("/application/applicationApplNoticeMgmt.do")
	public String applicationNoticeMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		CommonAppl vo = new CommonAppl();
		vo.setSearchMenuId(menuId);
		List<CommonAppl> list = commonService.selectApplicationAppl(vo);

		model.addAttribute("taskCd", list.get(0).getAc_task_cd());
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "application/applicationNoticeMgmt";
	}

	//대상자 그룹 조회
	@RequestMapping(value="/doSelectSubjectGroup.do", produces="application/json; charset=utf-8")
	public ModelAndView doSelectSubjectGroup(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		GroupInfo vo = (GroupInfo)AbstractCntr.getJsonToBean(paramJson, GroupInfo.class);
		
		List<GroupInfo> list = commonService.selectSubjectGroup(vo);
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
	
	//대상자 그룹 상세 조회	
	@RequestMapping(value="/doSelectSubjectGroupDetail.do", produces="application/json; charset=utf-8")
	public ModelAndView doSelectSubjectGroupDetail(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		GroupInfo vo = (GroupInfo)AbstractCntr.getJsonToBean(paramJson, GroupInfo.class);

		List<GroupInfo> list = commonService.selectSubjectGroupDetail(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
	
	@ResponseBody
	@RequestMapping(value="/doSaveSubjectGroup.do", produces="application/json; charset=utf-8")
	public String doSaveSubjectGroup(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="params", required=true) String params)throws Exception{

		GroupInfo vo = (GroupInfo)AbstractCntr.getJsonToBean(paramJson, GroupInfo.class);
		List<GroupInfo> list = (List<GroupInfo>)AbstractCntr.getJsonToList(params, GroupInfo.class);

		CommonMessage message = commonService.insertSubjectGroup(vo, list);
		return JSONObject.fromObject(message).toString();
	}
	
	//결재선 정보 삭제
	@ResponseBody
	@RequestMapping(value="/doDeleteSubjectGroup.do", produces="application/json; charset=utf-8")
	public String doDeleteSubjectGroup(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		GroupInfo vo = (GroupInfo)AbstractCntr.getJsonToBean(paramJson, GroupInfo.class);

		CommonMessage message = commonService.deleteSubjectGroup(vo);
		return JSONObject.fromObject(message).toString();
	}
	
		
	@RequestMapping("/application/doPopupListView.do")
	public String doPopupListView(@RequestParam String menuId, @RequestParam String menuType, @RequestParam String apprNo, Model model) throws Exception{

		model.addAttribute("apprNo", apprNo);
		return "application/applicationApprView";
	}

	@RequestMapping(value="/common/doSelectApplInfo.do")
	public ModelAndView doSelectApplInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		CommonAppl vo = (CommonAppl)AbstractCntr.getJsonToBean(paramJson, CommonAppl.class);
		
		List<CommonAppl> list = commonService.selectCommonAppl(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}

	// 공통코드 조회
	@RequestMapping(value="/selectCommonCodeMgmtList.do")
	public ModelAndView selectCommonCodeMgmtList(HttpServletRequest req,
		@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		Code vo = (Code)AbstractCntr.getJsonToBean(paramJson, Code.class);
		
		List<Code> edList = commonService.selectCommonCodeMgmtList(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",edList);
		
		return modelData;
	}
	
	// 공통코드 저장
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/saveCommonCodeMgmtList.do", produces="application/json; charset=utf-8")
	public String saveCommonCodeMgmtList(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);

		CommonMessage message = commonService.saveCommonCodeMgmtList(list);
		return JSONObject.fromObject(message).toString();
	}
	
	//공통코드 삭제
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/deleteCommonCodeMgmtList.do", produces="application/json; charset=utf-8")
	public String deleteCommonCodeMgmtList(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);

		CommonMessage message = commonService.deleteCommonCodeMgmtList(list);
		return JSONObject.fromObject(message).toString();
	}
	
	//결재현황 조회
  	@RequestMapping(value="/doSelectApprDetailList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprDetailList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprDetailList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
    //기결재 문서 조회
  	@RequestMapping(value="/doSelectApprovalRefDocList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalRefDocList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.selectApprovalRefDocList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
  	//결재선 변경
  	@ResponseBody
  	@RequestMapping(value="/doUpdateApprLineChange.do", produces="application/json; charset=utf-8")
  	public String doUpdateApprLineChange(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson,
  			@RequestParam(value="params", required=true) String params)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = (List<Approval>)AbstractCntr.getJsonToList(params, Approval.class);

  		CommonMessage message = commonService.updateApprLineChange(vo, list, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
  	//결재 취소
  	@ResponseBody
  	@RequestMapping(value="/doUpdateApprCancel.do", produces="application/json; charset=utf-8")
  	public String doUpdateApprCancel(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.updateApprCancel(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
  	//결재 삭제
  	@ResponseBody
  	@RequestMapping(value="/doDeleteApprInfo.do", produces="application/json; charset=utf-8")
  	public String doDeleteApprInfo(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.deleteApprInfo(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
    //연계 시스템 결재 조회
  	@RequestMapping(value="/doSelectApprovalLinkList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectApprovalLinkList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = commonService.doSelectApprovalLinkList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
  	//기결재 문서 조회
  	@RequestMapping(value="/doSelectMainInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectMainInfo(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		UserInfo vo = (UserInfo)AbstractCntr.getJsonToBean(paramJson, UserInfo.class);
		
		HttpSession session = req.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");
		
		ModelAndView modelData = new ModelAndView("jsonView");
		
		try {
			
			
			UserInfo userInfo =commonService.selectUserInfo(vo);
			
			//if(!userInfo.getSabun().substring(0, 1).equals("O")) {
			if(userInfo.getSabun().substring(0, 1).equals("P") || userInfo.getSabun().substring(0, 1).equals("B") ||
			   userInfo.getSabun().substring(0, 1).equals("T") || userInfo.getSabun().substring(0, 1).equals("D")) {
				//연차정보조회
				Holiday holiVo = new Holiday();
				holiVo.setUser_id(vo.getUser_id());
				holiVo.setHoli_year(vo.getYear());
				Holiday holiInfo = commonService.selectYearHoliInfo(holiVo);
				
				modelData.addObject("holiInfo",holiInfo);
				
				//연간교육정보조회
				YearEducation yEduVo = new YearEducation();
				yEduVo.setUser_id(vo.getUser_id());
				yEduVo.setYy(vo.getYear());
				
				List<YearEducation> yEduInfo = commonService.selectYearEduInfo(yEduVo);
				
				modelData.addObject("eduInfo",yEduInfo);
			}
			if(userInfo.getSabun().substring(0, 1).equals("P") || userInfo.getSabun().substring(0, 1).equals("B")) {
				//M포인트 조회
				Mpoint mVo = new Mpoint();
				mVo.setEmpen(vo.getUser_id());
				MpointCall mp = new MpointCall();
				Mpoint mPointInfo = mp.selectMPointInfo(mVo);
				
				//리조트 사용일수 조회
				
				Resort resortVo = new Resort();
				resortVo.setUser_id(vo.getUser_id());
				
				List<Resort> resortUseInfo = resortService.selectCanCnt2(resortVo);

				//차량지원금 지원여부
				String joinDate = CurrentDateTime.addDate(userInfo.getJoincompanydate(),0,6,1);
				
				String rstDate = commonService.selectCarPurchaseDateInfo(vo);
				String carpurYmd = "";
				
				if(rstDate == null) {
					carpurYmd = CurrentDateTime.getDate();
				}else {
					carpurYmd = CurrentDateTime.addDate(rstDate,2,0,1);
				}
				
				String currYmd = CurrentDateTime.getDate();
				
				// 차량 구입 가능여부
				String carPurFlag = "N";
				//차량 구입 가능일
				String carPurYmd = "";
				//입사후 6개월지났는지 체크
				if(Integer.parseInt(currYmd) < Integer.parseInt(joinDate)) {
					carPurFlag = "N";
					carPurYmd = joinDate;
				}else {
					//최근구입일이 2년이내인지 여부 판단
					if(Integer.parseInt(currYmd) < Integer.parseInt(carpurYmd)) {
						carPurFlag = "N";
						carPurYmd = carpurYmd;
					}
					else {
						carPurFlag = "Y";
						carPurYmd = currYmd;
					}
					
				}
				CarPurchase carPurchaseInfo = new CarPurchase();
				carPurchaseInfo.setPurchase_yn(carPurFlag);
				carPurchaseInfo.setCar_reg_il(carPurYmd);
				
				
				//건강검진 정보
				HealthCheck healthCheckVo = new HealthCheck();
				healthCheckVo.setUser_id(vo.getUser_id());
				healthCheckVo.setHe_year(vo.getYear());
				HealthCheck healthCheckInfo = commonService.selectYearHealthCheckInfo(healthCheckVo);
				
				ViewDateInfo viewPr = new ViewDateInfo();
				viewPr.setCurr_ymd(CurrentDateTime.getDate());
				
				//승진포인트 조회
				String checkPrYear = commonService.selectCheckPromotionView(viewPr);
				
				PromotionPoint prInfo = null;
				if(checkPrYear != null ) {
					PromotionPoint prVo = new PromotionPoint();
					prVo.setPr_year(checkPrYear);
					prVo.setUser_id(vo.getUser_id());
					prVo.setLocale(vo.getLocale());
					prInfo = commonService.selectPromotionPointInfo(prVo);
				}
				
				//연봉정보 조회
				String checkIcYear = commonService.selectCheckIncomeView(viewPr);
				
				IncomeInfo icInfo = null;
				if(checkIcYear != null ) {
					IncomeInfo icVo = new IncomeInfo();
					icVo.setIc_year(checkIcYear);
					icVo.setUser_id(vo.getUser_id());
					icVo.setLocale(vo.getLocale());
					icInfo = commonService.selectIncomeInfo(icVo);
					if(icInfo != null)
						icVo.setEtc1(icInfo.getEtc1());
					icInfo = commonService.selectIncomeInfo2(icVo);
				}
				
				//인센티브정보 조회
				String checkIctYear = commonService.selectCheckIncentiveView(viewPr);
				
				IncentiveInfo ictInfo = null;
				if(checkIctYear != null ) {
					IncentiveInfo ictVo = new IncentiveInfo();
					ictVo.setIct_year(checkIctYear);
					ictVo.setUser_id(vo.getUser_id());
					ictVo.setLocale(vo.getLocale());
					ictInfo = commonService.selectIncentiveInfo(ictVo);
				}
				
				//건강보험료 정보 조회
				String checkIsYear = commonService.selectCheckInsuranceView(viewPr);
				
				InsuranceInfo isInfo = null;
				if(checkIsYear != null ) {
					InsuranceInfo isVo = new InsuranceInfo();
					isVo.setIs_year(checkIsYear);
					isVo.setUser_id(vo.getUser_id());
					isVo.setLocale(vo.getLocale());
					isInfo = commonService.selectInsuranceInfo(isVo);
				}
				
				modelData.addObject("mPointInfo",mPointInfo);
				modelData.addObject("resortUseInfo",resortUseInfo);
				modelData.addObject("carPurchaseInfo",carPurchaseInfo);
				modelData.addObject("healthCheckInfo",healthCheckInfo);
				modelData.addObject("prInfo",prInfo);
				modelData.addObject("icInfo",icInfo);
				modelData.addObject("ictInfo",ictInfo);
				modelData.addObject("isInfo",isInfo);
			}
			
			//결재정보 조회
			Approval apprVo = new Approval();
			apprVo.setAppr_id(vo.getUser_id());
			apprVo.setLocale(vo.getLocale());
			
			UserInfo patentVo = new UserInfo();
			patentVo.setUser_id(vo.getUser_id());
			UserInfo patentAuth = commonService.selectParentAuth(patentVo);
			
			//특허관리자 확인
			if(patentAuth.getPatent_auth_yn().equals("Y")){
				//특허관리자인 경우
				apprVo.setPatent_auth_yn("Y");
			}else {
				//아닌 경우
				apprVo.setPatent_auth_yn("");
			}
			List<Approval> apprSignList = commonService.selectApprovalSignListForMain(apprVo);	
			
			List<Approval> apprProgList = commonService.selectApprovalProgressListForMain(apprVo);	
			
			modelData.addObject("apprSignList",apprSignList);
			modelData.addObject("apprProgList",apprProgList);
			
		} catch (Exception e) {
			StringUtil.printStackTrace(e);
		}
		
		return modelData;
	}
  	
  	@RequestMapping(value = "/changeLocale.do", produces="application/json; charset=utf-8")
    public String changeLocale(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String locale) {
        HttpSession session = request.getSession();
        Locale lo = null;
        //step. 파라메터에 따라서 로케일 생성, 기본은 KOREAN 
        if (locale.matches("en")) {
                lo = Locale.ENGLISH;
        } else {
                lo = Locale.KOREAN;
        }
        // step. Locale을 새로 설정한다.          
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, lo);
        // step. 해당 컨트롤러에게 요청을 보낸 주소로 돌아간다.
        
        
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		sess_info.setLocale(locale);
		
		Common commonVo = new Common();
		commonVo.setLang_user_id(sess_user_id);
		commonVo.setLocale(locale);
        CommonMessage message = commonService.updateLocaleInfo(commonVo);
        
        String redirectURL = "redirect:" + request.getHeader("referer");
        return "main";

    }


  	//협조 담당자 추가 
	@ResponseBody
	@RequestMapping(value="/doAddApprChargePic.do", produces="application/json; charset=utf-8")
	public String doAddApprChargePicConfirm(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
	
		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
	
		CommonMessage message = commonService.addApprChargePic(vo, req);
		return JSONObject.fromObject(message).toString();
	}

	@ResponseBody
	@RequestMapping(value="/doDeleteApprChargePic.do", produces="application/json; charset=utf-8")
	public String doDeleteApprChargePic(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<Approval> list = (List<Approval>)AbstractCntr.getJsonToList(params, Approval.class);
		
		CommonMessage message = commonService.deleteApprChargePic(list, req);
		return JSONObject.fromObject(message).toString();
	}

	@ResponseBody
	@RequestMapping(value="/doSaveApprChargePic.do", produces="application/json; charset=utf-8")
	public String doSaveApprChargePic(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		
		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		
		CommonMessage message = commonService.saveApprChargePic(vo, req);
		return JSONObject.fromObject(message).toString();
	}

	@ResponseBody
	@RequestMapping(value="/doSaveChargeOpinionConfirm.do", produces="application/json; charset=utf-8")
	public String doSaveChargeOpinionConfirm(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		
		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		
		CommonMessage message = commonService.saveChargeOpinionConfirm(vo, req);
		return JSONObject.fromObject(message).toString();
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/sendChargeMail.do", produces="application/json; charset=utf-8")
	public String sendCampaignMail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		
		CommonMessage message = commonService.sendChargeMail(vo, req);

		return JSONObject.fromObject(message).toString();
	}

	
	//대결자 조회
  	@RequestMapping(value="/doSelectChargeChangeList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectChargeChangeList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> data = commonService.selectChargeChangeList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",data);
		return modelData;
	}

    //대결자 지정
  	@ResponseBody
  	@RequestMapping(value="/doSaveAppointInfo.do", produces="application/json; charset=utf-8")
  	public String doSaveAppointInfo(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.saveAppointInfo(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
  	//대결자 삭제
  	@ResponseBody
  	@RequestMapping(value="/doDeleteAppointInfo.do", produces="application/json; charset=utf-8")
  	public String doDeleteAppointInfo(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

  		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);

  		CommonMessage message = commonService.deleteAppointInfo(vo, req);
  		return JSONObject.fromObject(message).toString();
  	}
  	
  	//대결자 조회
  	@RequestMapping(value="/doSelectAppointInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectAppointInfo(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		Approval data = commonService.selectAppointInfo(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",data);
		return modelData;
	}
  	

	@ResponseBody
	@RequestMapping(value="/getCommonComboCd.do", produces="application/json; charset=utf-8")
	public String getCommonComboCd(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd,
			@RequestParam(value="cd" , required=true) String cd) throws Exception{
		
		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		Code code = null;
		List<Code> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
			code = new Code();
			jsonArr = new JSONBaseArray();

			code.setCodknd(codePair[1]);
			code.setCd(cd);
			codeList = commonService.getCommonComboCd(code);

			if(codePair.length > 2){
				json = new JSONBaseVO();
				if(!codePair[2].equals("Z")){
					if(codePair[2].equals("S")){
						json.put("name", "선택");
					}else if(codePair[2].equals("H")){
						json.put("name", "-");
					}else{
						json.put("name", "전체");
					}
					json.put("value", "");

					jsonArr.add(json);
				}
			}

			for(Code targetBean : codeList){

				json = new JSONBaseVO();
				json.put("name", StringUtil.getHtmlDecode(StringUtil.isNullToString(targetBean.getCd_nm())));
				json.put("value", StringUtil.isNullToString(targetBean.getCd()));

				jsonArr.add(json);
			}
			jso.put(codePair[0], jsonArr);
			}else{
			break;
			}
		}
		String rtnData = jso.toString();
	return rtnData;
	}
	
	//신청서 업무코드 조회
  	@RequestMapping(value="/doSelectCommonTaskList.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectCommonTaskList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Code vo = (Code)AbstractCntr.getJsonToBean(paramJson, Code.class);
		List<Code> data = commonService.selectCommonTaskList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",data);
		return modelData;
	}
  	
    //사용자 상세 조회
  	@RequestMapping(value="/doSelectUserDetail.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectUserDetail(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
  		UserInfo vo = (UserInfo)AbstractCntr.getJsonToBean(paramJson, UserInfo.class);

  		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_locale = sess_info.getLocale();
		
		vo.setLocale(sess_locale);
		
  		UserInfo data = commonService.selectUserDetail(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",data);
		return modelData;
	}
  	
  	
  	
  	@RequestMapping(value="/doApprovalInfoLink.do")
	public ModelAndView doApprovalInfoLink(HttpServletRequest req, HttpServletResponse res )throws Exception{
		
  		ModelAndView modelData = new ModelAndView("jsonView");
  		
		try{
			String userId = StringUtil.isNullToString(req.getParameter("userId"));
			String callTime = StringUtil.isNullToString(req.getParameter("callTime"));

			String key = StringUtil.getPropinfo().getProperty("AW_ENC_ID");

			AES256Cipher aes256 = new AES256Cipher(key);
			
			System.out.println("key::"+key);
			System.out.println("userId::"+userId);
			System.out.println("callTime::"+callTime);

			userId = aes256.aesDecode(userId).toUpperCase();
			callTime = aes256.aesDecode(callTime);
			
			System.out.println("userId1::"+userId);
			System.out.println("callTime2::"+callTime);

			if(!StringUtil.isNullToString(userId).equals("") && !StringUtil.isNullToString(callTime).equals("")){
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String currtime = dateFormat.format(new Date());
				
				System.out.println("currtime::"+currtime);
				
				Date df1 = dateFormat.parse(currtime);// 현재시간
				Date df2 = dateFormat.parse(callTime);
				
				long diff = df1.getTime() - df2.getTime();
				long diffSec = diff/1000;
				System.out.println("diffSec::"+diffSec);
				
				
				if(diffSec > 30){
					//30초초과
					modelData.addObject("returnCode", "2000");
				}else{
					Approval vo = new Approval();
					vo.setAppr_id(userId);
					List<ApprovalAw> listSign = commonService.selectApprovalSignListForAW(vo);	
					List<ApprovalAw> listProg = commonService.selectApprovalProgressListForAW(vo);	
					
					modelData.addObject("appr_url","doSSOApprovalAutoway.do");
					modelData.addObject("sign",listSign);
					modelData.addObject("progress",listProg);
					modelData.addObject("returnCode", "0");
				}
				
			}else {
				modelData.addObject("returnCode", "1000");
			}
			
			
			
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			modelData.addObject("returnCode", "1");
			return modelData;
		}

		return modelData;
	}
  	
  	//차세대 SSO 로그인
  	@RequestMapping(value="/doSSOLoginAutoway.do")
	public String doSSOLoginAutoway(HttpServletRequest req, HttpServletResponse res, Model model )throws Exception{
		
		try{
			String HKMCENC_ID = StringUtil.isNullToString(req.getParameter("HKMCENC_ID"));
			String CompanyCode = StringUtil.isNullToString(req.getParameter("CompanyCode"));
			String Encode = StringUtil.isNullToString(req.getParameter("Encode"));
			
			System.out.println("====================================================");
			System.out.println("SSO Loing From autoway");
			System.out.println("HKMCENC_ID:"+HKMCENC_ID);
			System.out.println("CompanyCode:"+CompanyCode);
			System.out.println("Encode:"+Encode);

			String strPlainText = "";
			
			String[] arrParam = null;
			String[] arrParam2 = null;
			
			SitemapWSSoapProxy devProxy = new SitemapWSSoapProxy(); // SitemapWSSoapProxy : 가져온 웹서비스 Proxy
			strPlainText = devProxy.getPlainText(HKMCENC_ID, CompanyCode, Encode);
			System.out.println("strPlainText:"+strPlainText);
			System.out.println("====================================================");
			
			arrParam = strPlainText.toString().split("___");
			String url = "";
			String callTime = "";
			String empId = "";
			
			for(int i=0;i<arrParam.length;i++)
			{
				arrParam2 = arrParam[i].split("\\|\\|");
				if (arrParam2.length > 1) {
					if(StringUtil.isNullToString(arrParam2[0]).equals("url")) {
						url = StringUtil.isNullToString(arrParam2[1]);
					}else if(StringUtil.isNullToString(arrParam2[0]).equals("ssoTIME")) {
						callTime = StringUtil.isNullToString(arrParam2[1]);
					}else if(StringUtil.isNullToString(arrParam2[0]).equals("Emp_ID")) {
						empId = StringUtil.isNullToString(arrParam2[1]);
					}
				}
				
			}

			empId = empId.toUpperCase();
			
			System.out.println("url:"+url);

			if(!StringUtil.isNullToString(empId).equals("")){
				UserInfo loginInfo = new UserInfo();
				loginInfo.setUser_id(empId);
				UserInfo userInfo =commonService.selectUserInfo(loginInfo);
				if(userInfo != null){
					Calendar calendar = Calendar.getInstance();
			        Date date = calendar.getTime();
			        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					Date df1 = dateFormat.parse(today);// 현재시간
					Date df2 = dateFormat.parse(callTime);

					long diff = df1.getTime() - df2.getTime();
					long diffSec = diff/1000;
					
					System.out.println("df1.getTime():"+df1.getTime());
					System.out.println("df2.getTime():"+df2.getTime());
					System.out.println("diffSec:"+diffSec);
					
					//if(false) {
					if(diffSec > 10){
						System.out.println("SSOCheckTime: 10sec over");
						//10초초과
						res.sendRedirect("/ssoLoginError.do");
					}else{
						System.out.println("SSO 인증성공");
						//SSO인증 성공
						commonService.setSessionInfo(req, userInfo); //세션생성
						model.addAttribute("ssoUrl", url);
						return "/forward";
					}
				}else{
					System.out.println("사용자 접보없음");
					//사용자 정보 없음 에러처리
					res.sendRedirect("/ssoLoginError.do");
				}
			}else {
				System.out.println("사번 파라미터 접보없음");
				res.sendRedirect("/ssoLoginError.do");
			}
		}catch(Exception e){
			System.out.println("인증에러 발생");
			StringUtil.printStackTrace(e);
			res.sendRedirect("/ssoLoginError.do");
		}

		return null;
	}
  	//차세대 오토웨이 결재정보 클릭시 호출
  	@RequestMapping(value="/doSSOApprovalAutoway.do")
	public String doSSOApprovalAutoway(HttpServletRequest req, HttpServletResponse res, Model model )throws Exception{
		
		try{
			String HKMCENC_ID = StringUtil.isNullToString(req.getParameter("HKMCENC_ID"));
			String CompanyCode = StringUtil.isNullToString(req.getParameter("CompanyCode"));
			String Encode = StringUtil.isNullToString(req.getParameter("Encode"));
			
			System.out.println("====================================================");
			System.out.println("SSO Approval From autoway");
			System.out.println("HKMCENC_ID:"+HKMCENC_ID);
			System.out.println("CompanyCode:"+CompanyCode);
			System.out.println("Encode:"+Encode);

			String strPlainText = "";
			//String strPlainText = "apprType||progress___apprNo||T1811141134074745153___User_ID||P0507___ssoTIME||20181112162032";
			
			String[] arrParam = null;
			String[] arrParam2 = null;
			
			SitemapWSSoapProxy devProxy = new SitemapWSSoapProxy(); // SitemapWSSoapProxy : 가져온 웹서비스 Proxy
			strPlainText = devProxy.getPlainText(HKMCENC_ID, CompanyCode, Encode);

			System.out.println("strPlainText:"+strPlainText);
			System.out.println("====================================================");
			
			arrParam = strPlainText.toString().split("___");
			
			String callTime = "";
			String apprType = "";
			String apprNo = "";
			String empId = "";
			
			for(int i=0;i<arrParam.length;i++)
			{
				arrParam2 = arrParam[i].split("\\|\\|");
				if (arrParam2.length > 1) {
					if(StringUtil.isNullToString(arrParam2[0]).equals("ssoTIME")) {
						callTime = StringUtil.isNullToString(arrParam2[1]);
					}else if(StringUtil.isNullToString(arrParam2[0]).equals("apprType")) {
						apprType = StringUtil.isNullToString(arrParam2[1]);
					}else if(StringUtil.isNullToString(arrParam2[0]).equals("apprNo")) {
						apprNo = StringUtil.isNullToString(arrParam2[1]);
					}else if(StringUtil.isNullToString(arrParam2[0]).equals("Emp_ID")) {
						empId = StringUtil.isNullToString(arrParam2[1]);
					}
				}
				
			}

			empId = empId.toUpperCase();
			
			System.out.println("empId:"+empId);

			if(!StringUtil.isNullToString(empId).equals("")){
				UserInfo loginInfo = new UserInfo();
				loginInfo.setUser_id(empId);
				UserInfo userInfo =commonService.selectUserInfo(loginInfo);
				
				if(userInfo != null){
					
					Calendar calendar = Calendar.getInstance();
			        Date date = calendar.getTime();
			        String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));

			        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					Date df1 = dateFormat.parse(today);// 현재시간
					Date df2 = dateFormat.parse(callTime);

					long diff = df1.getTime() - df2.getTime();
					long diffSec = diff/1000;
					
					System.out.println("df1.getTime():"+df1.getTime());
					System.out.println("df2.getTime():"+df2.getTime());
					
					if(diffSec > 10){
						System.out.println("SSOCheckTime: 10sec over");
						//10초초과
						res.sendRedirect("/ssoLoginError.do");
					}else{
						System.out.println("SSO 인증성공");
						//SSO인증 성공
						commonService.setSessionInfo(req, userInfo);//세션생성
						
						String url = "";
						
						if(apprType.equals("sign")) {
							url = "/approval/approvalSign";
						}else if(apprType.equals("progress")) {
							url = "/approval/approvalProgress";
						}
						
						model.addAttribute("url", url);
						model.addAttribute("appr_no", apprNo);
						
						Approval apprVo = new Approval();
						apprVo.setAppr_no(apprNo);
						String apprDocUrl = commonService.selectApprDocUrl(apprVo);
						model.addAttribute("appr_doc_no", apprDocUrl);
						
						return url;
					}
				}else{
					System.out.println("사용자 접보없음");
					//사용자 정보 없음 에러처리
					res.sendRedirect("/ssoLoginError.do");
				}
			}else {
				System.out.println("사번 파라미터 접보없음");
				res.sendRedirect("/ssoLoginError.do");
			}
		}catch(Exception e){
			System.out.println("인증에러 발생");
			StringUtil.printStackTrace(e);
			res.sendRedirect("/ssoLoginError.do");
		}

		return null;
	}
  	
  	//사용자검색(사번)
  	@RequestMapping(value="/doSelectUserListForSabun.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectUserListForSabun(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		UserInfo vo = (UserInfo)AbstractCntr.getJsonToBean(paramJson, UserInfo.class);
		List<UserInfo> list = commonService.selectUserListForSabun(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
}
