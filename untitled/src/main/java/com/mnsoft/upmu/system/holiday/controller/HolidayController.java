package com.mnsoft.upmu.system.holiday.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mnsoft.upmu.bizcard.vo.BizCard;
import com.mnsoft.upmu.budget.vo.Budget;
import com.mnsoft.upmu.common.service.CommonService;
import com.mnsoft.upmu.common.util.AbstractCntr;
import com.mnsoft.upmu.common.util.CommonList;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.CurrentDateTime;
import com.mnsoft.upmu.common.util.JSONBaseVO;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.holiday.service.HolidayService;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.holiday.vo.HolidayCode;
import com.mnsoft.upmu.holidayWork.vo.HolidayWork;
import com.mnsoft.upmu.meal.vo.Meal;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.work.service.WorkService;
import com.mnsoft.upmu.work.vo.AttStd;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class HolidayController {

	@Resource(name="holidayService")
	HolidayService holidayService;
	
	@Resource(name = "commonService")
	private CommonService commonService;
	/**
	 * 휴가신청서 호출
	 */
	@RequestMapping("/holiday/holidayAppl.do")
	public String holidayAppl(Model model) throws Exception{
		return "holiday/holidayAppl";
	}
	/**
	 * 휴가신청서 조회화면호출
	 */
	@RequestMapping("/holiday/holidayList.do")
	public String holidayList(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "holiday/holidayList";
	}
	/**
	 * 휴가신청서 코드관리 호출
	 */
	@RequestMapping("/holiday/holidayCode.do")
	public String holidayCode(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "holiday/holidayCode";
	}
	/**
	 * 휴가신청서 유의사항관리 호출
	 */
	@RequestMapping("/holiday/holidayNoticeMgmt.do")
	public String holidayNoticeMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "holiday/holidayNoticeMgmt";
	}
	
	//휴가신청 저장
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/holiday/doSaveHolidayAppl.do", produces="application/json; charset=utf-8")
	public ResponseEntity<String> doSaveHolidayAppl(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{

		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
		List<Holiday> list = (List<Holiday>)AbstractCntr.getJsonToList(params, Holiday.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);
		
		CommonMessage message = holidayService.saveHolidayAppl(req, vo, list, apprVo, apprList);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	//휴가신청 결재상신
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/holiday/doApproveHolidayAppl.do", produces="application/json; charset=utf-8")
	public ResponseEntity<String> doApproveHolidayAppl(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{

		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
		List<Holiday> list = (List<Holiday>)AbstractCntr.getJsonToList(params, Holiday.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);

		CommonMessage message = holidayService.approveHolidayAppl(req, vo, list, apprVo, apprList);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	//휴가신청 담당자 편집
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/holiday/doUpdateHolidayAppl.do", produces="application/json; charset=utf-8")
	public ResponseEntity<String> doUpdateHolidayAppl(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params,
			@RequestParam(value="paramJson", required=true) String paramJson,
			@RequestParam(value="apprParamJson", required=true) String apprParamJson,
			@RequestParam(value="apprParams", required=true) String apprParams)throws Exception{

		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
		List<Holiday> list = (List<Holiday>)AbstractCntr.getJsonToList(params, Holiday.class);
		Approval apprVo = (Approval)AbstractCntr.getJsonToBean(apprParamJson, Approval.class);
		List<Approval> apprList = (List<Approval>)AbstractCntr.getJsonToList(apprParams, Approval.class);

		CommonMessage message = holidayService.updateHolidayAppl(req, vo, list, apprVo, apprList);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/holiday/doSelectHolidayList.do")
	public ModelAndView doSelectHolidayList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
		List<Holiday> list = holidayService.selectHolidayList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
	
	
	@RequestMapping(value="/holiday/doSelectHolidayCodeList.do")
	public ModelAndView doSelectHolidayCodeList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		HolidayCode vo = (HolidayCode)AbstractCntr.getJsonToBean(paramJson, HolidayCode.class);
		List<HolidayCode> list = holidayService.selectHolidayCodeList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
	
	//근태기준관리 등록
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/holiday/doSaveHolidayCodeList.do", produces="application/json; charset=utf-8")
	public String doSSaveHolidayCodeList(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<HolidayCode> list = (List<HolidayCode>)AbstractCntr.getJsonToList(params, HolidayCode.class);

		CommonMessage message = holidayService.saveHolidayCodeList(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	
	@ResponseBody
  	@RequestMapping(value="/holiday/selectWorkgListForHolidayAppl.do", produces="application/json; charset=utf-8")
  	public String selectWorkgListForHolidayAppl(HttpServletRequest req, HttpServletResponse res,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
		HolidayCode vo = (HolidayCode)AbstractCntr.getJsonToBean(paramJson, HolidayCode.class);


  		List<HolidayCode> list = holidayService.selectWorkgListForHolidayAppl(vo);

  		JSONBaseVO jso = new JSONBaseVO();
  		jso.put("data", list);
  		return JSONObject.fromObject(jso).toString();
  	}
	
	//신청서 결재창 기본정보 조회
  	@RequestMapping(value="/holiday/doSelectHolidayApplInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectHolidayApplInfo(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
  		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
  		
  		//결재정보 조회
  		Holiday apprInfo = holidayService.selectHolidayApplInfo(vo);
  		
  		//결재리스트 조회
  		List<Holiday> apprList = holidayService.selectHolidayApprList(vo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("apprInfo",apprInfo);
		modelData.addObject("apprList",apprList);
		return modelData;
	}
  	
  	//연간 연차정보 조회
  	@RequestMapping(value="/holiday/doSelectYearHoliInfo.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectYearHoliInfo(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
  		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
  		
  		//연차정보
  		Holiday holiInfo = commonService.selectYearHoliInfo(vo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("holiInfo",holiInfo);
		return modelData;
	}
  	
  	//휴가타입별 일수
  	@RequestMapping(value="/holiday/doSelectHoliDayType.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectHoliDayType(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
  		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
  		
  		//연차정보
  		Holiday holiInfo = holidayService.selectHoliDayType(vo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("holiInfo",holiInfo);
		return modelData;
	}
  	
 	@RequestMapping(value="/holiday/doExcelHolidayList.do")
 	public ModelAndView doExcelHolidayList(HttpServletRequest req, HttpServletResponse res,
 		@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

  		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
  		
  		String columnIds="";
		String columnNames="";
		String fileName="";
		
		columnIds="row_num,doc_no,req_dt,sts_dt,sts_nm,dept_nm,user_nm,pos_nm,user_id,edit_user_nm,edit_dt";
		columnNames="No.,문서번호,신청일,완료일,진행상태,부서,이름,직급,사번,수정자,수정일";
		fileName ="휴가신청_"+CurrentDateTime.getDate();
		List<Holiday> list = holidayService.selectHolidayList(vo);
  		

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
 	
 	//날짜 차이 계산(공유일 제외)
  	@RequestMapping(value="/holiday/doSelectDiffDayExceptHoliday.do", produces="application/json; charset=utf-8")
  	public ModelAndView doSelectDiffDayExceptHoliday(HttpServletRequest req,
  			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
  		
  		Holiday vo = (Holiday)AbstractCntr.getJsonToBean(paramJson, Holiday.class);
  		
  		//날짜 차이
  		Holiday holiInfo = holidayService.doSelectDiffDayExceptHoliday(vo);
  		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("holiInfo",holiInfo);
		return modelData;
	}
 	
}
