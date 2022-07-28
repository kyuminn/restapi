package com.mnsoft.upmu.bizcard.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mnsoft.upmu.bizcard.service.BizCardService;
import com.mnsoft.upmu.bizcard.vo.BizCard;
import com.mnsoft.upmu.common.util.AbstractCntr;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.system.vo.Code;

import net.sf.json.JSONObject;

@Controller
public class BizCardController {

	@Resource(name="bizCardService")
	BizCardService bizCardService;


	
	/**
	 * 담당자 페이지 호출
	 */
	@RequestMapping(value="/bizcard/bizCardEmail.do")
	public String businessCard(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "bizcard/bizCardEmail";
	}
	
	
	/**
	 * 담당자 페이지 호출
	 */
	@RequestMapping(value="/bizcard/bizCardList.do")
	public String bizCardList(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "bizcard/bizCardList";
	}
	
	/**
	 * 담당자 페이지 호출
	 */
	@RequestMapping(value="/bizcard/bizCardAddress.do")
	public String bizCardAddress(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "bizcard/bizCardAddress";
	}
	
	/**
	 * 담당자 페이지 호출
	 */
	@RequestMapping(value="/bizcard/bizCardCnt.do")
	public String bizCardCnt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "bizcard/bizCardCnt";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/bizcard/saveBizCard.do", produces="application/json; charset=utf-8")
	public String saveBizCard(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		
		List<BizCard> list = (List<BizCard>)AbstractCntr.getJsonToList(params, BizCard.class);
		CommonMessage message = bizCardService.saveBizCard(req, list);

		return JSONObject.fromObject(message).toString();
	}
		
	/**
	 * 사용자 리스트 호출
	 */
	@RequestMapping(value="/bizcard/userInfo.do")
	public ModelAndView userInfo(@ModelAttribute("userInfo") UserInfo userInfo, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<UserInfo> userInfoList = bizCardService.userInfo(userInfo);	
		modelData.addObject("data",userInfoList);
		return modelData;
	}	
	
	
	
	/**
	 * 명함신청목록 리스트 호출
	 */
	@RequestMapping(value="/bizcard/selectBizCardList.do")
	public ModelAndView selectBizCardList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{		
		BizCard vo = (BizCard)AbstractCntr.getJsonToBean(paramJson, BizCard.class);
		List<BizCard> bizCardList = bizCardService.selectBizCardList(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",bizCardList);
		return modelData;
	}
	
	/**
	 * 명함 상세내용 호출
	 */
	@RequestMapping(value="/bizcard/bizCardInfo.do")
	public ModelAndView bizCardInfo(@ModelAttribute("bizCard") BizCard bizCard, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<BizCard> bizCardInfo = bizCardService.bizCardInfo(bizCard);	
		modelData.addObject("data",bizCardInfo);
		return modelData;
	}
	
	/**
	 * 제작업체 이메일 수정
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/bizcard/saveCdEmail.do", produces="application/json; charset=utf-8")
	public String saveCdEmail(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		
		CommonMessage message = bizCardService.saveCdEmail(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	
	/**
	 * 주소 저장
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/bizcard/saveAddr.do", produces="application/json; charset=utf-8")
	public String saveAddr(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = bizCardService.saveAddr(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 신청통수 저장
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/bizcard/saveCnt.do", produces="application/json; charset=utf-8")
	public String saveCnt(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = bizCardService.saveCnt(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 메일전송
	 */
	@ResponseBody
	@RequestMapping(value="/bizcard/sendMail.do", produces="application/json; charset=utf-8")
	public String sendMail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		List<BizCard> list = (List<BizCard>)AbstractCntr.getJsonToList(paramJson, BizCard.class);
		CommonMessage message = bizCardService.sendMail(list, req);

		return JSONObject.fromObject(message).toString();
	}
	
	
	/**
	 * 당일 신청여부
	 */
	@RequestMapping(value="/bizcard/bizCardInfoToday.do")
	public ModelAndView bizCardInfoToday(@ModelAttribute("bizCard") BizCard bizCard, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<BizCard> bizCardInfo = bizCardService.bizCardInfoToday(bizCard);	
		modelData.addObject("data",bizCardInfo);
		return modelData;
	}


}
