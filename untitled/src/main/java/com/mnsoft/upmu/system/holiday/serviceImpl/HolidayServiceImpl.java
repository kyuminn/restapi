package com.mnsoft.upmu.system.holiday.serviceImpl;

import java.text.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mnsoft.upmu.common.dao.CommonDAO;
import com.mnsoft.upmu.common.service.CommonService;
import com.mnsoft.upmu.common.util.*;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.holiday.dao.HolidayDao;
import com.mnsoft.upmu.holiday.service.HolidayService;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.holiday.vo.HolidayCode;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.work.dao.WorkDao;
import com.mnsoft.upmu.work.service.WorkService;
import com.mnsoft.upmu.work.vo.Work;

@Transactional(rollbackFor=Exception.class)
@Service("holidayService")
public class HolidayServiceImpl implements HolidayService{

	@Autowired
	HolidayDao holidayDao;
	
	@Autowired 
	CommonService commonService;
	
	@Autowired 
	CommonDAO commonDao;
	
	@Autowired 
	WorkService workService;
	
	@Autowired 
	WorkDao workDao;
	
	@Autowired 
	MessageSource messageSource;

	
	@Override
	public CommonMessage saveHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();

		String docNo = "";
		
		 try {
			 
			 if(StringUtil.isNullToString(vo.getDoc_no()).equals("")) {
				docNo = StringUtil.getDocNo();
				vo.setDoc_no(docNo);
				vo.setFile_no(docNo);
				apprVo.setDoc_no("T"+vo.getDoc_no());
			 }else{
				apprVo.setDoc_no(vo.getAppr_no());
			 }
			 
			 CommonMessage result = CommonApproval.approvalRequest(apprVo, apprList, "S"); 
			 
			 if(result.getResult().equals("S")){
				 
				 //결재번호 세팅
				 vo.setAppr_no(result.getCode01());
				 
				 holidayDao.saveHolidayAppl(vo);
				 
				 holidayDao.deleteHolidayApplDetail(vo);
				 
				 for(int i=0 ; i < list.size(); i++) {
					 list.get(i).setDoc_no(vo.getDoc_no());
					 holidayDao.insertHolidayApplDetail(list.get(i));
				 }
				 //첨부파일
				 CommonMessage fileMsg = commonService.insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());

				message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				message.setSuccess("S");
			 }
			 else {
				 message.setMessage(result.getMessage());
				 message.setSuccess("F");
			 }
			 
			 
		}catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}
	
	@Override
	public CommonMessage approveHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();
		
		String docNo = "";

		 try {
			 
			 for(int i=0 ; i<list.size() ; i++) {
				 list.get(i).setUser_id(vo.getUser_id());
				 int chkCnt = holidayDao.selectHolidayRequestCheck(list.get(i));
				 if(chkCnt > 0) {
					 message.setMessage("중복된 휴가 신청서가 존재하여 신청이 불가합니다.");
					 message.setSuccess("F");
					 return message;
				 }
			 }
			 
			 CommonMessage result = CommonApproval.approvalRequest(apprVo, apprList, "A"); 
			 
			 if(result.getResult().equals("S")){
				 
				 if(StringUtil.isNullToString(vo.getDoc_no()).equals("")) {
					 docNo = StringUtil.getDocNo();
					 vo.setDoc_no(docNo);
					 vo.setFile_no(docNo);
				 }
				 
				 //결재번호 세팅
				 vo.setAppr_no(result.getCode01());
				 
				 holidayDao.saveHolidayAppl(vo);
				 
				 holidayDao.deleteHolidayApplDetail(vo);
				 
				 for(int i=0 ; i < list.size(); i++) {
					 list.get(i).setDoc_no(vo.getDoc_no());
					 holidayDao.insertHolidayApplDetail(list.get(i));
				 }
				 
				 CommonMessage fileMsg = commonService.insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());
				 
				message.setMessage(messageSource.getMessage("msg.appr01", null, StringUtil.getLocalLang(req))); //정상적으로 결재 상신 되었습니다.
				message.setSuccess("S");
			 }
			 else {
				 message.setMessage(result.getMessage());
				 message.setSuccess("F");
			 }
			 
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(messageSource.getMessage("msg.appr02", null, StringUtil.getLocalLang(req))); //결재 상신 중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}
	
	@Override
	public CommonMessage updateHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();

		String docNo = "";
		
		 try {
			 
			 CommonMessage result = CommonApproval.approvalRefUpdate(apprVo); 
			 
			 // 이전 휴가신청으로 인한 실적 내용 조회 후 삭제
			 List<Work> wlist = new ArrayList<Work>();
			 List<Work> templist = new ArrayList<Work>();
			 Integer maxSeq = workDao.selectMaxDocSeq(apprVo);
			 for(int i=1; i<=maxSeq; i++) {
				 apprVo.setDoc_seq(Integer.toString(i));
				 templist = workDao.doSelectWorkDayListByHoliday(apprVo);
				 wlist.addAll(templist);
			 }
			 
			 String today = (new SimpleDateFormat("yyyyMMdd").format(new Date()));
			
			 for(int i=0; i<wlist.size(); i++) {
				if(CurrentDateTime.diffOfDate(today, wlist.get(i).getEdate()) > -1) {
					workDao.excuteDelete(wlist.get(i));
				}
			 }
			 
			 //신청서 저장
			 holidayDao.saveHolidayAppl(vo);
			 
			 holidayDao.deleteHolidayApplDetail(vo);
			 
			 for(int i=0 ; i < list.size(); i++) {
				 list.get(i).setDoc_no(vo.getDoc_no());
				 holidayDao.insertHolidayApplDetail(list.get(i));
			 }
			 
			 // 저장된 휴가신청으로 인한 실적내용 적용
			 wlist = new ArrayList<Work>();
			 templist = new ArrayList<Work>();
			 maxSeq = workDao.selectMaxDocSeq(apprVo);
			 for(int i=1; i<=maxSeq; i++) {
				 apprVo.setDoc_seq(Integer.toString(i));
				 templist = workDao.doSelectWorkDayListByHoliday(apprVo);
				 wlist.addAll(templist);
			 }
			 workService.saveExcuteList(wlist);
			 
			 //첨부파일
			 CommonMessage fileMsg = commonService.insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());
			 
			 HttpSession session = req.getSession();
			 UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
			 String sess_user_id = sess_info.getSabun();
			 
			 UserInfo UserVo = new UserInfo();
			 UserVo.setUser_id(sess_user_id);
			 UserInfo userInfo = commonDao.selectUserInfo(UserVo);
			 apprVo.setEdit_user_id(userInfo.getSabun());
			 apprVo.setUser_nm(userInfo.getName());
			 apprVo.setPos_cd(userInfo.getPositionid());
			 apprVo.setDept_id(userInfo.getDeptid());
			 commonDao.insertApprUpdateHistory(apprVo);
			 
			 message.setMessage(messageSource.getMessage("msg.update01", null, StringUtil.getLocalLang(req))); //정상적으로 수정 되었습니다.
			 message.setSuccess("S");
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(messageSource.getMessage("msg.update02", null, StringUtil.getLocalLang(req))); //수정중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Holiday> selectHolidayList(Holiday vo) {
		return holidayDao.selectHolidayList(vo);
	}

	@Override
	public List<HolidayCode> selectHolidayCodeList(HolidayCode vo) {
		return holidayDao.selectHolidayCodeList(vo);
	}

	@Override
	public CommonMessage saveHolidayCodeList(HttpServletRequest req, List<HolidayCode> list) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			for(int i=0 ; i<list.size() ; i++ ){
				holidayDao.saveHolidayCodeList(list.get(i));
			}
			message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
			message.setSuccess("S");
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<HolidayCode> selectWorkgListForHolidayAppl(HolidayCode vo) {
		return holidayDao.selectWorkgListForHolidayAppl(vo);
	}

	@Override
	public Holiday selectHolidayApplInfo(Holiday vo) {
		Holiday hday = holidayDao.selectHolidayApplInfo(vo);
		if(hday != null){
			FileInfo fVo = new FileInfo();
			fVo.setFile_id(hday.getFile_no());
			hday.setFileList(commonDao.selectFileList(fVo));
		}
		return hday;
	}

	@Override
	public List<Holiday> selectHolidayApprList(Holiday vo) {
		return holidayDao.selectHolidayApprList(vo);
	}

	@Override
	public Holiday selectHoliDayType(Holiday vo) {
		return holidayDao.selectHoliDayType(vo);
	}
	
	@Override
	public Holiday doSelectDiffDayExceptHoliday(Holiday vo) {
		return holidayDao.doSelectDiffDayExceptHoliday(vo);
	}
	

}
