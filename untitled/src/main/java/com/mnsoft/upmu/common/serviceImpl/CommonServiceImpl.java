package com.mnsoft.upmu.common.serviceImpl;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mnsoft.upmu.assetInout.dao.AssetInoutDao;
import com.mnsoft.upmu.assetInout.vo.AssetInout;
import com.mnsoft.upmu.book.dao.BookDao;
import com.mnsoft.upmu.book.vo.Book;
import com.mnsoft.upmu.cardRequest.vo.CardRequest;
import com.mnsoft.upmu.carpurchase.dao.CarPurchaseDao;
import com.mnsoft.upmu.carpurchase.vo.CarPurchase;
import com.mnsoft.upmu.chulsan.dao.ChulsanDao;
import com.mnsoft.upmu.chulsan.vo.Chulsan;
import com.mnsoft.upmu.common.dao.CommonDAO;
import com.mnsoft.upmu.common.dao.CommonDAOERP;
import com.mnsoft.upmu.common.service.CommonService;
import com.mnsoft.upmu.common.util.AES256Cipher;
import com.mnsoft.upmu.common.util.CommonApproval;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.CurrentDateTime;
import com.mnsoft.upmu.common.util.FileUtil;
import com.mnsoft.upmu.common.util.SendMail;
import com.mnsoft.upmu.common.util.StringUtil;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.ApprovalAw;
import com.mnsoft.upmu.common.vo.Common;
import com.mnsoft.upmu.common.vo.CommonAppl;
import com.mnsoft.upmu.common.vo.DateInfo;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.common.vo.ErpCarPurchase;
import com.mnsoft.upmu.common.vo.ErpDispatch;
import com.mnsoft.upmu.common.vo.ErpEducation;
import com.mnsoft.upmu.common.vo.ErpEducationalExpenses;
import com.mnsoft.upmu.common.vo.ErpExpenditureResolution;
import com.mnsoft.upmu.common.vo.ErpHoliday;
import com.mnsoft.upmu.common.vo.ErpHolidayWork;
import com.mnsoft.upmu.common.vo.ErpLeave;
import com.mnsoft.upmu.common.vo.ErpMedical;
import com.mnsoft.upmu.common.vo.GroupInfo;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.dispatch.dao.DispatchDao;
import com.mnsoft.upmu.dispatch.vo.Dispatch;
import com.mnsoft.upmu.education.dao.EducationDao;
import com.mnsoft.upmu.education.vo.Education;
import com.mnsoft.upmu.educationalExpenses.dao.EducationalExpensesDao;
import com.mnsoft.upmu.educationalExpenses.vo.EducationalExpenses;
import com.mnsoft.upmu.expenditureResolution.dao.ExpenditureResolutionDao;
import com.mnsoft.upmu.expenditureResolution.vo.ExpenditureResolution;
import com.mnsoft.upmu.holiday.dao.HolidayDao;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.holidayWork.dao.HolidayWorkDao;
import com.mnsoft.upmu.holidayWork.vo.HolidayWork;
import com.mnsoft.upmu.infoProcess.vo.InfoProcess;
import com.mnsoft.upmu.leave.dao.LeaveDao;
import com.mnsoft.upmu.leave.vo.Leave;
import com.mnsoft.upmu.medical.dao.MedicalDao;
import com.mnsoft.upmu.medical.vo.Medical;
import com.mnsoft.upmu.officialDocument.dao.OfficialDocumentDao;
import com.mnsoft.upmu.officialDocument.vo.OfficialDocument;
import com.mnsoft.upmu.overseasBusinessTrip.dao.OverseasBusinessTripDao;
import com.mnsoft.upmu.overseasBusinessTrip.vo.OverseasBusinessTrip;
import com.mnsoft.upmu.personalWelfare.vo.HealthCheck;
import com.mnsoft.upmu.personalWelfare.vo.IncentiveInfo;
import com.mnsoft.upmu.personalWelfare.vo.IncomeInfo;
import com.mnsoft.upmu.personalWelfare.vo.InsuranceInfo;
import com.mnsoft.upmu.personalWelfare.vo.PromotionPoint;
import com.mnsoft.upmu.personalWelfare.vo.ViewDateInfo;
import com.mnsoft.upmu.sourceOut.dao.SourceOutDao;
import com.mnsoft.upmu.sourceOut.vo.SourceOut;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.Guide;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.wedding.dao.WeddingDao;
import com.mnsoft.upmu.wedding.vo.Wedding;
import com.mnsoft.upmu.work.dao.WorkDao;
import com.mnsoft.upmu.work.service.WorkService;
import com.mnsoft.upmu.work.vo.Work;
import com.mnsoft.upmu.yearEducation.vo.YearEducation;

@Transactional(rollbackFor=Exception.class)
@Service("commonService")
public class CommonServiceImpl implements CommonService{

	@Autowired CommonDAO commonDAO;
	
	@Autowired CommonDAOERP commonDAOERP;
	
	@Autowired SourceOutDao sourceOutDao;
	
	@Autowired AssetInoutDao assetInoutDao;
	
	@Autowired BookDao bookDao;
	
	@Autowired OfficialDocumentDao officialDocumentDao;
	
	@Autowired WeddingDao weddingDao;
	
	@Autowired HolidayDao holidayDao;
	
	@Autowired OverseasBusinessTripDao overseasBusinessTripDao;

	@Autowired LeaveDao leaveDao;
	
	@Autowired HolidayWorkDao holidayWorkDao;
	
	@Autowired CarPurchaseDao carPurchaseDao;
	@Autowired ChulsanDao chulsanDao;

	@Autowired DispatchDao dispatchDao;
	
	@Autowired EducationDao educationDao;
	
	@Autowired EducationalExpensesDao educationalExpensesDao;
	
	@Autowired MedicalDao medicalDao;
	
	@Autowired ExpenditureResolutionDao expenditureResolutionDao;

	@Autowired WorkService workService;

	@Autowired MessageSource messageSource;
	
	@Autowired WorkDao workDao;
	
	
	@Override
	public List<String> selecttestList() {
		return commonDAO.selecttestList();
	}
	
	@Override
	public List<Code> selectCodeList(Code code) throws Exception {
		return commonDAO.selectCodeList(code);
	}

	
	@Override
	public List<Code> getCommonComboEtc_1(Code code) throws Exception {
		return commonDAO.getCommonComboEtc_1(code);
	}
	
	@Override
	public List<Code> getCommonComboEtc_2(Code code) throws Exception {
		return commonDAO.getCommonComboEtc_2(code);
	}

	@Override
	public List<Code> selectWorkCodeList(Code code) throws Exception {
		return commonDAO.selectWorkCodeList(code);
	}

	@Override
	public List<Code> selectWorkComboType(Code code) throws Exception {
		return commonDAO.selectWorkComboType(code);
	}

	@Override
	public List<Code> selectComonMenuList(Code code) throws Exception {
		return commonDAO.selectComonMenuList(code);
	}

	@Override
	public UserInfo selectUserInfo(UserInfo userInfo) throws Exception {
		return commonDAO.selectUserInfo(userInfo);
	}

	@Override
	public void setSessionInfo(HttpServletRequest req, UserInfo userInfo) {

		Map<String, UserInfo> map = new HashMap<String,UserInfo>();
		
		HttpSession session1 = req.getSession();
		session1.invalidate();
		

		HttpSession session = req.getSession();
		
		String strLang = commonDAO.selectUserLangInfo(userInfo);
		
		if(StringUtil.isNullToString(strLang).equals("")) {
			strLang = "ko";
		}
		
		userInfo.setLocale(strLang);
		
		Locale lo = Locale.KOREAN;
		if(strLang.equals("en")) {
			lo = Locale.ENGLISH;
		}
		
		session.setMaxInactiveInterval(60*60*15); //15시간
		session.setAttribute("sess_user_info", userInfo);
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, lo);
	}

	@Override
	public List<Code> selectCommonDeptList(Code code, HttpServletRequest req) throws Exception{
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		String sess_dept_id = sess_info.getDeptid();
		
		code.setReg_user_id(sess_user_id);
		code.setDept_id(sess_dept_id);
		if(code.getAuth_yn().equals("Y") || sess_user_id.equals("O1294")){
			code.setCodknd("1");
		}else{
			code.setCodknd("0");
		}
		
		String SystemArea = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
		System.out.println("SystemArea : " +SystemArea);
		if(SystemArea.equals("REAL")){
			code.setTable_id("V_HNMS_DEPTUSERS");
		}else{
			code.setTable_id("UPMU_DEPTUSERS");
		}
		
		return commonDAO.selectCommonDeptList(code);
	}
	
	@Override
	public List<Code> selectPatentDeptList(Code code, HttpServletRequest req) throws Exception{
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		String sess_dept_id = sess_info.getDeptid();
		
		code.setReg_user_id(sess_user_id);
		code.setDept_id(sess_dept_id);
		if(code.getAuth_yn().equals("Y") || sess_user_id.equals("O1294")){
			code.setCodknd("1");
		}else{
			code.setCodknd("0");
		}
		
		String SystemArea = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
		System.out.println("SystemArea : " +SystemArea);
		if(SystemArea.equals("REAL")){
			code.setTable_id("V_HNMS_DEPTUSERS");
		}else{
			code.setTable_id("UPMU_DEPTUSERS");
		}
		
		return commonDAO.selectPatentDeptList(code);
	}

	@Override
	public List<UserInfo> selectCommonUserList(UserInfo searchParam) {
		return commonDAO.selectCommonUserList(searchParam);
	}

	@Override
	public List<DeptInfo> selectOrgListForTree(DeptInfo searchParam) {
		return commonDAO.selectOrgListForTree(searchParam);
	}

	@Override
	public List<DeptInfo> selectOrgListForTreeLeader(UserInfo searchParam) {
		return commonDAO.selectOrgListForTreeLeader(searchParam);
	}
	
	@Override
	public List<UserInfo> selectOrgListForUser(UserInfo searchParam) {
		List<String> list = new ArrayList<String>();

		String[] words = searchParam.getSabun().split(",");
		int[] words1 = new int[words.length];
			for(int i = 0; i < words.length; i++){
				list.add(words[i]);
			}

		return commonDAO.selectOrgListForUser(list);
	}

	@Override
	public List<UserInfo> selectOrgListForDept(UserInfo searchParam) {
		List<String> list = new ArrayList<String>();
		
		String[] words = searchParam.getDeptid().split(",");
		for(int i = 0; i < words.length; i++){
			list.add(words[i]);
		}
		
		return commonDAO.selectOrgListForDept(list);
	}

	@Override
	public List<UserInfo> selectUserListForDept(UserInfo searchParam) {
		
		String[]dutyArr = searchParam.getDutyid().split(",");
		List<String> dutyList = new ArrayList<String>();
		
		for(String item : dutyArr) {
			dutyList.add(item);
		}
		
		searchParam.setDuty_arr(dutyList);
		
		List<UserInfo> list = null;
		
		if(!searchParam.getDutyid().equals("")) {
			list = commonDAO.selectHeadUserListForDept(searchParam);
		}
		else {
			list = commonDAO.selectUserListForDept(searchParam);
		}
		
		return list;
	}
	
	@Override
	public List<Code> selectCommonLinkList(Code code) throws Exception {
		return commonDAO.selectCommonLinkList(code);
	}
	
	@Override
	public CommonMessage insertFileMgmt(HttpServletRequest req, String foldNm, String fileId) {
		 CommonMessage message = new CommonMessage();
	        String[] paramVal = new String[2];
	        paramVal[0]       = "uploadFile";
	        paramVal[1]       = foldNm;
	        String resultStr  = FileUtil.uploadFile(req, paramVal);
	        //String fileId     = StringUtil.getFileNm("F");
	        message.setResult(fileId);
	        
	        HttpSession session=req.getSession();
	        UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
	        String sess_user_id = sess_info.getSabun();



	        
	        if(!"".equals(resultStr) && !"E01".equals(resultStr) && !"E02".equals(resultStr)){
	            String[] fileList = resultStr.split(";");
	            for(int i=0; i<fileList.length; i++){
	                FileInfo fileVo = new FileInfo();
	                String[] retInfo = fileList[i].split(":");
	                /**
	                 * [0]:결과
	                 * [1]:원본파일명
	                 * [2]:변경파일명
	                 * [3]:파일사이즈
	                 */
	                if("S".equals(retInfo[0])){
	                    fileVo.setFile_id(fileId);
	                    fileVo.setOrg_file_name(retInfo[1]);
	                    fileVo.setMfy_file_name(retInfo[2]);
	                    fileVo.setFile_size(retInfo[3]);
	                    fileVo.setFile_fold(foldNm);
	                    fileVo.setEdit_user_id(sess_user_id);
	                    commonDAO.insertFileMgmt(fileVo);
	                    message.setCode01(retInfo[2]);
	                }else{
	                    message.setResult("");
	                    message.setMessage(fileList[i]);
	                }
	            }
	        }else{
	            message.setResult("");
	            if("E01".equals(resultStr)){
	                message.setMessage("허용되지 않는 확장자입니다.");
	            }else if("E02".equals(resultStr)){
	                message.setMessage("파일사이즈가 초과되었습니다.");
	            }
	            else{
	                message.setMessage("파일 업로드 에러 입니다. 관리자에게 문의하세요.");
	            }
	            
	        }
	        
	        return message;
	}

	@Override
	public CommonMessage deleteFileMgmt(HttpServletRequest req, FileInfo fileVo) {
		 CommonMessage message = new CommonMessage();
	        String fileId  = fileVo.getFile_id();
	        String fileSeq = fileVo.getFile_seq();
	        
	        fileVo.setFile_id(fileId);
	        fileVo.setFile_seq(fileSeq);
	        int delCnt = commonDAO.deleteFileMgmt(fileVo);
	        if(delCnt == 0){
	            message.setResult("");
	            message.setMessage("삭제할 데이타가 존재 하지 않습니다.");            
	        }else{
	            message.setResult(fileId);
	            message.setMessage("삭제 되었습니다.");
	        }
	        
	        return message;
	}
	
	@Override
	public CommonMessage insertFileMgmt2(HttpServletRequest req, String foldNm, String fileId, Guide guide) {
		 CommonMessage message = new CommonMessage();
	        String[] paramVal = new String[2];
	        paramVal[0]       = "uploadFile";
	        paramVal[1]       = foldNm;
	        String resultStr  = FileUtil.uploadFile(req, paramVal);
	        //String fileId     = StringUtil.getFileNm("F");
	        message.setResult(fileId);
	        
	        HttpSession session=req.getSession();
	        UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
	        String sess_user_id = sess_info.getSabun();



	        
	        if(!"".equals(resultStr) && !"E01".equals(resultStr) && !"E02".equals(resultStr)){
	            String[] fileList = resultStr.split(";");
	            for(int i=0; i<fileList.length; i++){
	                Guide fileVo = new Guide();
	                String[] retInfo = fileList[i].split(":");
	                /**
	                 * [0]:결과
	                 * [1]:원본파일명
	                 * [2]:변경파일명
	                 * [3]:파일사이즈
	                 */
	                if("S".equals(retInfo[0])){
	                    fileVo.setFile_id(fileId);
	                    fileVo.setOrg_file_name(retInfo[1]);
	                    fileVo.setMfy_file_name(retInfo[2]);
	                    fileVo.setFile_size(retInfo[3]);
	                    fileVo.setFile_fold(foldNm);
	                    fileVo.setEdit_user_id(sess_user_id);
	                    fileVo.setGuide_code(guide.getGuide_code());
	                    commonDAO.insertFileMgmt2(fileVo);
	                    message.setCode01(retInfo[2]);
	                }else{
	                    message.setResult("");
	                    message.setMessage(fileList[i]);
	                }
	            }
	        }else{
	            message.setResult("");
	            if("E01".equals(resultStr)){
	                message.setMessage("허용되지 않는 확장자입니다.");
	            }else if("E02".equals(resultStr)){
	                message.setMessage("파일사이즈가 초과되었습니다.");
	            }
	            else{
	                message.setMessage("파일 업로드 에러 입니다. 관리자에게 문의하세요.");
	            }
	            
	        }
	        
	        return message;
	}

	@Override
	public DateInfo selectCheckHoliday(DateInfo vo) {
		return commonDAO.selectCheckHoliday(vo);
	}

	@Override
	public List<Approval> selectApprovalSignList(Approval vo) {
		return commonDAO.selectApprovalSignList(vo);
	}
	
	@Override
	public List<Approval> selectApprovalDueList(Approval vo) {
		return commonDAO.selectApprovalDueList(vo);
	}

	@Override
	public List<Approval> selectApprovalProgressList(Approval vo) {
		List<Approval> list = null;
		
		try {
			list = commonDAO.selectApprovalProgressList(vo);
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
		}
		
		return list;
	}

	@Override
	public List<Approval> selectApprovalSaveList(Approval vo) {
		return commonDAO.selectApprovalSaveList(vo);
	}

	@Override
	public List<Approval> selectApprovalRejectList(Approval vo) {
		return commonDAO.selectApprovalRejectList(vo);
	}
	
	@Override
	public List<Approval> selectApprovalComplateList(Approval vo) {
		
		List<Approval> list = null;
		
		//내가 결재 요청한 문서
		if(StringUtil.isNullToString(vo.getSearch_ctgy()).equals("A")) {
			list = commonDAO.selectApprovalComplateListForReq(vo);
		}//내가 결재한 문서
		else if(StringUtil.isNullToString(vo.getSearch_ctgy()).equals("B")) {
			list = commonDAO.selectApprovalComplateListForAppr(vo);
		}//양식별
		else if(StringUtil.isNullToString(vo.getSearch_ctgy()).equals("C")) {
			list = commonDAO.selectApprovalComplateListForAll(vo);
		}
		
		
		return list;
	}

	@Override
	public List<Approval> selectApprList(Approval approvalVo) {
		return commonDAO.selectApprList(approvalVo);
	}

	@Override
	public Approval selectApprInfo(Approval approvalVo) throws Exception {
		
		Approval apprInfo = commonDAO.selectApprInfo(approvalVo);
		
		UserInfo userVo = new UserInfo();
		userVo.setUser_id(approvalVo.getAppr_id());
		
		UserInfo userInfo = commonDAO.selectUserInfo(userVo);
		if(userInfo != null) {
			Approval viewApprVo = new Approval();
			viewApprVo.setAppr_no(approvalVo.getAppr_no());
			viewApprVo.setAppr_id(approvalVo.getAppr_id());
			viewApprVo.setView_id(userInfo.getSabun());
			viewApprVo.setView_nm(userInfo.getName());
			viewApprVo.setView_dept_cd(userInfo.getDeptid());
			viewApprVo.setView_pos(userInfo.getPositionid());
			
			//개봉일시 저장
			commonDAO.updateApprViewTime(viewApprVo);
		}
		
		
		/*if(apprInfo != null && apprInfo.getSts_cd().equals("A")) {
			if(StringUtil.isNullToString(apprInfo.getView_dt()).equals("")) {
				
				String apprId = StringUtil.isNullToString(apprInfo.getAppr_id());
				String appointId = StringUtil.isNullToString(apprInfo.getAppoint_id());
				
				if(!apprId.equals("")) {
					Approval viewApprVo = new Approval();
					viewApprVo.setAppr_no(apprInfo.getAppr_no());
					viewApprVo.setAppr_id(apprInfo.getAppr_id());
					
					UserInfo userVo = new UserInfo();
					
					
					if(!appointId.equals("") && !apprId.equals(appointId)){
				        userVo.setUser_id(apprInfo.getAppoint_id());
					}
					else {
				        userVo.setUser_id(apprInfo.getAppr_id());
					}
					
					UserInfo userInfo = commonDAO.selectUserInfo(userVo);
					if(userInfo != null) {
						viewApprVo.setView_id(userInfo.getSabun());
						viewApprVo.setView_nm(userInfo.getName());
						viewApprVo.setView_dept_cd(userInfo.getDeptid());
						viewApprVo.setView_pos(userInfo.getPositionid());
						
						//개봉일시 저장
						commonDAO.updateApprViewTime(viewApprVo);
					}
				}
				
			}
		}*/
		
		return apprInfo;
	}

	@Override
	public CommonMessage updateApprConfirm(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();

		String docNo = "";
		
		 try {
			 
			//공통결재 호출
			 CommonMessage result = CommonApproval.approvalConfirm(vo, req); 
			 
			 if(result.getResult().equals("S")){
				 
				 //결재 완료시 후처리
				 if(result.getCode01().equals("C")) {
					 
					 String SystemArea = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
					 
					 
					 HttpSession session = req.getSession();
					UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
					String sess_user_id = sess_info.getSabun();
					String sess_user_nm = sess_info.getName();
					String sess_dept_cd = sess_info.getDeptid();
					 
					 //결재완료시 완료결재번호 세팅
					 vo.setAppr_no_fin(result.getCode02());
					 
					 //휴가신청 결재 완료시 ERP 연동
					 if(vo.getTask_cd().equals("HD")){
						 
						 
						 //if(!SystemArea.equals("REAL")) {
							 Holiday holidayVo = new Holiday();
							 holidayVo.setAppr_no(vo.getAppr_no());
							 List<Holiday> holidayList = holidayDao.selectHolidayApplInfoForErp(holidayVo);
							 
							 for(int i=0 ; i<holidayList.size() ; i++) {
								 int chkCnt = holidayDao.selectHolidayRequestCheck(holidayList.get(i));
								 if(chkCnt > 0) {
									 message.setMessage("신청한 일자의 휴가 신청서가 존재하여 신청이 불가합니다.");
									 message.setSuccess("F");
									 return message;
								 }
							 }
							 
							 for(int i=0 ; i<holidayList.size() ; i++) {
								 ErpHoliday erpHolidayVo = new ErpHoliday();
								 
								 erpHolidayVo.setCd_company("0101");
								 erpHolidayVo.setCd_bizarea("1000");
								 erpHolidayVo.setNo_emp(holidayList.get(i).getUser_id());
								 
								 if(holidayList.size() == 1) {
									 erpHolidayVo.setNo_proposal(holidayList.get(i).getAppr_no_fin());
								 }else {
									 erpHolidayVo.setNo_proposal(holidayList.get(i).getAppr_no_fin()+"_"+(i+1));
								 }
								 
								 
								 String holiType = holidayList.get(i).getHoli_type();
								 String holiTypeReal = "";
								 //반차(추가)_오전  반차(추가)_오후 일경우 반차(추가)로 변경
								 if(holiType.equals("G58") || holiType.equals("G59")) {
									 holiTypeReal = "G40";
								 }
								 else {
									 holiTypeReal = holiType;
								 }
								 
								 erpHolidayVo.setCd_wcode(holiTypeReal);
								 erpHolidayVo.setDt_proposal(holidayList.get(i).getReq_dt());
								 erpHolidayVo.setDt_start(holidayList.get(i).getStrt_ymd());
								 erpHolidayVo.setDt_close(holidayList.get(i).getFnsh_ymd());
								 erpHolidayVo.setTm_start("0");
								 erpHolidayVo.setTm_close("0");
								 erpHolidayVo.setDy_proposal(StringUtil.isNullToString2(holidayList.get(i).getHoli_date(), "0"));
								 
								 String reqResn = URLDecoder.decode(holidayList.get(i).getAftr_req_resn(),"UTF-8");
								 //반차(오전), 대휴(오전), 반차(추가)_오전
								 if(holiType.equals("G58")) {
									 erpHolidayVo.setDc_rmk("오전_"+reqResn);
								 }//반차(오후), 대휴(오후), 반차(추가)_오후
								 else if(holiType.equals("G59")) {
									 erpHolidayVo.setDc_rmk("오후_"+reqResn);
								 }
								 else {
									 erpHolidayVo.setDc_rmk(reqResn);
								 }
								 
								 erpHolidayVo.setNo_cemp("");
								 erpHolidayVo.setCd_consent("001");
								 erpHolidayVo.setNo_tel_emer("");
								 erpHolidayVo.setNo_tel("");
								 erpHolidayVo.setNo_emp_an("");
								 erpHolidayVo.setState("N");
								 erpHolidayVo.setCd_wtype("001");
								 erpHolidayVo.setId_insert("MISO");
								 erpHolidayVo.setDt_consent("");
								 erpHolidayVo.setNm_cemp("");
								 erpHolidayVo.setDc_rmk1("");
								 
								 System.out.println("erpHolidayVo.getCd_company()); :::"+erpHolidayVo.getCd_company());
								 System.out.println("erpHolidayVo.getCd_bizarea()); :::"+erpHolidayVo.getCd_bizarea());
								 System.out.println("erpHolidayVo.getNo_emp());     :::"+erpHolidayVo.getNo_emp());
								 System.out.println("erpHolidayVo.getNo_proposal());:::"+erpHolidayVo.getNo_proposal());
								 System.out.println("erpHolidayVo.getCd_wcode());   :::"+erpHolidayVo.getCd_wcode());
								 System.out.println("erpHolidayVo.getDt_proposal());:::"+erpHolidayVo.getDt_proposal());
								 System.out.println("erpHolidayVo.getDt_start());   :::"+erpHolidayVo.getDt_start());
								 System.out.println("erpHolidayVo.getDt_close());   :::"+erpHolidayVo.getDt_close());
								 System.out.println("erpHolidayVo.getTm_start());   :::"+erpHolidayVo.getTm_start());
								 System.out.println("erpHolidayVo.getTm_close());   :::"+erpHolidayVo.getTm_close());
								 System.out.println("erpHolidayVo.getDy_proposal());:::"+erpHolidayVo.getDy_proposal());
								 System.out.println("erpHolidayVo.getDc_rmk());     :::"+erpHolidayVo.getDc_rmk());
								 System.out.println("erpHolidayVo.getNo_cemp());    :::"+erpHolidayVo.getNo_cemp());
								 System.out.println("erpHolidayVo.getCd_consent()); :::"+erpHolidayVo.getCd_consent());
								 System.out.println("erpHolidayVo.getNo_tel_emer());:::"+erpHolidayVo.getNo_tel_emer());
								 System.out.println("erpHolidayVo.getNo_tel());     :::"+erpHolidayVo.getNo_tel());
								 System.out.println("erpHolidayVo.getNo_emp_an());  :::"+erpHolidayVo.getNo_emp_an());
								 System.out.println("erpHolidayVo.getState());      :::"+erpHolidayVo.getState());
								 System.out.println("erpHolidayVo.getCd_wtype());   :::"+erpHolidayVo.getCd_wtype());
								 System.out.println("erpHolidayVo.getId_insert());  :::"+erpHolidayVo.getId_insert());
								 System.out.println("erpHolidayVo.getDt_consent()); :::"+erpHolidayVo.getDt_consent());
								 System.out.println("erpHolidayVo.getNm_cemp());    :::"+erpHolidayVo.getNm_cemp());
								 System.out.println("erpHolidayVo.getDc_rmk1());    :::"+erpHolidayVo.getDc_rmk1());
								 
								 if(SystemArea.equals("REAL")) {
									 commonDAOERP.insertHolidayInfoForErp(erpHolidayVo);
								 }
								
								
							}
							List<Work> wlist = new ArrayList<Work>();
							List<Work> templist = new ArrayList<Work>();
							Integer maxSeq = workDao.selectMaxDocSeq(vo);
							for(int j=1; j<=maxSeq; j++) {
								 vo.setDoc_seq(Integer.toString(j));
								 templist = workDao.doSelectWorkDayListByHoliday(vo);
								 wlist.addAll(templist);
							}
							workService.saveExcuteList(wlist);
							 
						 //}
						//해외 출장품위서
					 }else if(vo.getTask_cd().equals("OB")){
						//해외출장 vo
						OverseasBusinessTrip obVo = new OverseasBusinessTrip();
						//정보처리 vo
						InfoProcess infoProcessVo = new InfoProcess();
						//법인카드 vo
						CardRequest cardInfoVo = new CardRequest();

						obVo.setAppr_no(vo.getAppr_no());
						//해외출장 정보 ( 정보처리 ID , 법인카드 ID) 
						OverseasBusinessTrip obInfo =overseasBusinessTripDao.selectOBApplInfo(obVo);

						//정보처리 신청서 결제 올림 
						if(!StringUtil.isNullToString(obInfo.getInfo_doc_no()).equals("")) {
							//결제선 정보 vo
							Approval apprInfoVo = new Approval();
							apprInfoVo.setTask_cd("IP");
							apprInfoVo.setLocale("ko");
							apprInfoVo.setAppr_id(obInfo.getUser_id());
							apprInfoVo.setReq_id(obInfo.getUser_id());
							apprInfoVo.setAppr_no(obInfo.getAppr_no());
							apprInfoVo.setFile_no(obInfo.getFile_no());
							apprInfoVo.setSts_cd("A");
							//결제선 리스트 

							System.out.println("해외출장 정보처리 연계신청서 생성");
							Approval applInfoInfo = commonDAO.selectApprLineTypeByTask(apprInfoVo);

							List<Approval> apprList = null;
							apprList = commonDAO.selectApprLineInfoByTaskOB(apprInfoVo);

							String newFileId ="";
							if(!StringUtil.isNullToString(obInfo.getFile_no()).equals("")) {
								//파일 저장
								FileInfo applFileVo = new FileInfo();
								applFileVo.setFile_id(apprInfoVo.getFile_no());
								List<FileInfo> applFileList = commonDAO.selectFileList(applFileVo);
								//참조문서정보 입력
								newFileId = StringUtil.getDocNo();
								for(int i=0 ; i < applFileList.size() ; i++) {
									applFileList.get(i).setFile_id(newFileId);
									commonDAO.insertApplFileInfo(applFileList.get(i));
								}
							}
							
							CommonMessage result1 = CommonApproval.approvalOBRequest(apprInfoVo, apprList, "A");
							if(result1.getResult().equals("S")){
								infoProcessVo.setAppr_no(result1.getCode01());
								infoProcessVo.setDoc_no(obInfo.getInfo_doc_no());
								infoProcessVo.setSts_cd("A");
								infoProcessVo.setFile_no(newFileId);
								overseasBusinessTripDao.updateInfoApplStatus(infoProcessVo);
							}
						}

						//법인 카드 신청서 결제 올림 
						if(!StringUtil.isNullToString(obInfo.getCard_doc_no()).equals("")) {
							Approval apprCardVo = new Approval();
							apprCardVo.setTask_cd("CR");
							apprCardVo.setLocale("ko");
							apprCardVo.setSts_cd("A");
							apprCardVo.setAppr_no(obInfo.getAppr_no());
							apprCardVo.setFile_no(obInfo.getFile_no());
							//apprCardVo.setLocale(vo.getLocale());
							//카드 리스트 호출 
							List<String> list = new ArrayList<String>();
							String[] docs = obInfo.getCard_doc_no().split(",");
							for(int i = 0; i < docs.length; i++){
								list.add(docs[i]);
							}

							List<CardRequest> cardList = overseasBusinessTripDao.selectOBCardReq(list);
							for(int i=0 ; i<cardList.size() ; i++) {
								System.out.println("해외출장 카드연계신청서 생성");
								Approval applCardInfo = commonDAO.selectApprLineTypeByTask(apprCardVo);
								apprCardVo.setAppr_id(cardList.get(i).getUser_id());
								apprCardVo.setReq_id(cardList.get(i).getUser_id());

								List<Approval> apprList = null;
								//결재라인 만들기 기안 결재는 해외출장 정보 가져오기 
								apprList = commonDAO.selectApprLineInfoByTaskOB(apprCardVo);
								
								String newCardFileId ="";
								if(!StringUtil.isNullToString(obInfo.getFile_no()).equals("")) {
									//파일 저장
									FileInfo applCardFileVo = new FileInfo();
									applCardFileVo.setFile_id(apprCardVo.getFile_no());
									List<FileInfo> applFileList = commonDAO.selectFileList(applCardFileVo);
									//참조문서정보 입력
									newCardFileId = StringUtil.getDocNo();
									for(int k=0 ; k < applFileList.size() ; k++) {
										applFileList.get(k).setFile_id(newCardFileId);
										commonDAO.insertApplFileInfo(applFileList.get(k));
									}
								}
								
								CommonMessage result1 = CommonApproval.approvalOBRequest(apprCardVo, apprList, "A");
								if(result1.getResult().equals("S")){
									cardInfoVo.setAppr_no(result1.getCode01());
									cardInfoVo.setDoc_no(cardList.get(i).getDoc_no());
									cardInfoVo.setSts_cd("A");
									cardInfoVo.setFile_no(newCardFileId);
									cardInfoVo.setDoc_no2(vo.getAppr_no_fin());
									overseasBusinessTripDao.updateCardApplStatus(cardInfoVo);
								}
							}
							
							
						}
				     // 휴직신청시		 
					 }else if(vo.getTask_cd().equals("LV")){	
						 
						 Leave leaveVo = new Leave();
						 leaveVo.setAppr_no(vo.getAppr_no());
						 Leave leaveInfo = leaveDao.selectLeaveApplInfoForErp(leaveVo);
						 
						 String campanyCode = "0101";
						 String currYm = CurrentDateTime.getDate().substring(0, 6);
						 
						 ErpLeave lvVo = new ErpLeave();
						 lvVo.setCd_company(campanyCode);
						 lvVo.setCurr_ym(currYm);
						 
						 String maxNoAn = commonDAOERP.selectHuanHeadMaxNo(lvVo);
						 
						 String idxCode = "";
						 if(StringUtil.isNullToString(maxNoAn).equals("")) {
							 idxCode = currYm + "001";
						 }else {
							 idxCode = maxNoAn;
						 }
						 
						 //인사발령 HEAD
						 ErpLeave lvVoHead = new ErpLeave();
						 lvVoHead.setStatus("I");
						 lvVoHead.setCd_company(campanyCode);
						 lvVoHead.setNo_an(idxCode);
						 lvVoHead.setDt_write(CurrentDateTime.getDate());
						 lvVoHead.setNm_an(CurrentDateTime.getDate() + " 휴직발령");
						 lvVoHead.setCd_bizarea("1000");
						 lvVoHead.setCd_dept(leaveInfo.getDept_cd());
						 lvVoHead.setNm_writer(leaveInfo.getUser_id());
						 lvVoHead.setNm_bal("");
						 lvVoHead.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoHeadForErp(lvVoHead);
						 }
						 
						 //인사발령 Detail (사업장)
						 ErpLeave lvVoDtl1 = new ErpLeave();
						 lvVoDtl1.setStatus("I");
						 lvVoDtl1.setCd_company(campanyCode);
						 lvVoDtl1.setNo_an(idxCode);
						 lvVoDtl1.setNo_emp(leaveInfo.getUser_id());						 
						 lvVoDtl1.setCd_ancode("014");
						 lvVoDtl1.setCd_anexpen("H01");
						 lvVoDtl1.setDc_aftan("1000");
						 lvVoDtl1.setDc_befan("1000");
						 lvVoDtl1.setYn_ss("N");
						 lvVoDtl1.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl1);
						 }
						 
						 //인사발령 Detail (부서)
						 ErpLeave lvVoDtl2 = new ErpLeave();
						 lvVoDtl2.setStatus("I");
						 lvVoDtl2.setCd_company(campanyCode);
						 lvVoDtl2.setNo_an(idxCode);
						 lvVoDtl2.setNo_emp(leaveInfo.getUser_id());						 
						 lvVoDtl2.setCd_ancode("014");
						 lvVoDtl2.setCd_anexpen("H02");
						 lvVoDtl2.setDc_aftan(leaveInfo.getDept_cd());
						 lvVoDtl2.setDc_befan(leaveInfo.getDept_cd());
						 lvVoDtl2.setYn_ss("N");
						 lvVoDtl2.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl2);
						 }
						 
						 //인사발령 Detail (직위)
						 ErpLeave lvVoDtl3 = new ErpLeave();
						 lvVoDtl3.setStatus("I");
						 lvVoDtl3.setCd_company(campanyCode);
						 lvVoDtl3.setNo_an(idxCode);
						 lvVoDtl3.setNo_emp(leaveInfo.getUser_id());						 
						 lvVoDtl3.setCd_ancode("014");
						 lvVoDtl3.setCd_anexpen("H03");
						 lvVoDtl3.setDc_aftan(leaveInfo.getPos_cd());
						 lvVoDtl3.setDc_befan(leaveInfo.getPos_cd());
						 lvVoDtl3.setYn_ss("N");
						 lvVoDtl3.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl3);
						 }
						 
						 ErpLeave lvVoInsa = new ErpLeave();
						 
						 String workResn = URLDecoder.decode(leaveInfo.getLv_resn(),"UTF-8").replaceAll("<br/>", "\n");
						 
						 lvVoInsa.setCd_company(campanyCode);
						 lvVoInsa.setNo_an(idxCode);
						 lvVoInsa.setNo_emp(leaveInfo.getUser_id());
						 lvVoInsa.setCd_ancode("014");
						 lvVoInsa.setDt_from(leaveInfo.getLv_strt_ymd());
						 lvVoInsa.setDt_to(leaveInfo.getLv_fnsh_ymd());
						 lvVoInsa.setDc_reason(leaveInfo.getLv_type() +"_"+ workResn);
						 lvVoInsa.setYn_pay("N");
						 lvVoInsa.setYn_retire("N");
						 lvVoInsa.setTp_an("001");
						 lvVoInsa.setDt_an(CurrentDateTime.getDate().substring(0, 8));
						 lvVoInsa.setDc_rmk(leaveInfo.getPs_yn());
						 lvVoInsa.setId_insert("MISO");
						 lvVoInsa.setCd_rest("");
						 
						 System.out.println("lvVoInsa.getCd_company();" +  lvVoInsa.getCd_company());
						 System.out.println("lvVoInsa.getNo_an();     " +  lvVoInsa.getNo_an());
						 System.out.println("lvVoInsa.getNo_emp();    " +  lvVoInsa.getNo_emp());
						 System.out.println("lvVoInsa.getCd_ancode(); " +  lvVoInsa.getCd_ancode());
						 System.out.println("lvVoInsa.getDt_from();   " +  lvVoInsa.getDt_from());
						 System.out.println("lvVoInsa.getDt_to();     " +  lvVoInsa.getDt_to());
						 System.out.println("lvVoInsa.getDc_reason(); " +  lvVoInsa.getDc_reason());
						 System.out.println("lvVoInsa.getYn_pay();    " +  lvVoInsa.getYn_pay());
						 System.out.println("lvVoInsa.getYn_retire(); " +  lvVoInsa.getYn_retire());
						 System.out.println("lvVoInsa.getTp_an();     " +  lvVoInsa.getTp_an());
						 System.out.println("lvVoInsa.getDt_an();     " +  lvVoInsa.getDt_an());
						 System.out.println("lvVoInsa.getDc_rmk();    " +  lvVoInsa.getDc_rmk());
						 System.out.println("lvVoInsa.getId_insert(); " +  lvVoInsa.getId_insert());
						 System.out.println("lvVoInsa.getCd_rest();   " +  lvVoInsa.getCd_rest());
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoInsaForErp(lvVoInsa);
						 }
						 
						 
					 //출산신청 결재 완료시
					 }else if(vo.getTask_cd().equals("CH")){	
						 
						 Chulsan chulsanVo = new Chulsan();
						 chulsanVo.setAppr_no(vo.getAppr_no());
						// Leave leaveInfo = leaveDao.selectLeaveApplInfoForErp(leaveVo);
						 Chulsan chulsanInfo = chulsanDao.selectChulsanApprInfo(chulsanVo);
						 
												 
						 String campanyCode = "0101";
						 String currYm = CurrentDateTime.getDate().substring(0, 6);
						 
						 ErpLeave lvVo = new ErpLeave();
						 lvVo.setCd_company(campanyCode);
						 lvVo.setCurr_ym(currYm);
						 
						 String maxNoAn = commonDAOERP.selectHuanHeadMaxNo(lvVo);
						 
						 String idxCode = "";
						 if(StringUtil.isNullToString(maxNoAn).equals("")) {
							 idxCode = currYm + "001";
						 }else {
							 idxCode = maxNoAn;
						 }
						 
						 //인사발령 HEAD
						 ErpLeave lvVoHead = new ErpLeave();
						 lvVoHead.setStatus("I");
						 lvVoHead.setCd_company(campanyCode);
						 lvVoHead.setNo_an(idxCode);
						 lvVoHead.setDt_write(CurrentDateTime.getDate());
						 lvVoHead.setNm_an(CurrentDateTime.getDate() + " 출산휴직발령");
						 lvVoHead.setCd_bizarea("1000");
						 lvVoHead.setCd_dept(chulsanInfo.getDept_cd());
						 lvVoHead.setNm_writer(chulsanInfo.getUser_id());
						 lvVoHead.setNm_bal("");
						 lvVoHead.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoHeadForErp(lvVoHead);
						 }
						 
						 //인사발령 Detail (사업장)
						 ErpLeave lvVoDtl1 = new ErpLeave();
						 lvVoDtl1.setStatus("I");
						 lvVoDtl1.setCd_company(campanyCode);
						 lvVoDtl1.setNo_an(idxCode);
						 lvVoDtl1.setNo_emp(chulsanInfo.getUser_id());						 
						 lvVoDtl1.setCd_ancode("014");
						 lvVoDtl1.setCd_anexpen("H01");
						 lvVoDtl1.setDc_aftan("1000");
						 lvVoDtl1.setDc_befan("1000");
						 lvVoDtl1.setYn_ss("N");
						 lvVoDtl1.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl1);
						 }
						 
						 //인사발령 Detail (부서)
						 ErpLeave lvVoDtl2 = new ErpLeave();
						 lvVoDtl2.setStatus("I");
						 lvVoDtl2.setCd_company(campanyCode);
						 lvVoDtl2.setNo_an(idxCode);
						 lvVoDtl2.setNo_emp(chulsanInfo.getUser_id());						 
						 lvVoDtl2.setCd_ancode("014");
						 lvVoDtl2.setCd_anexpen("H02");
						 lvVoDtl2.setDc_aftan(chulsanInfo.getDept_cd());
						 lvVoDtl2.setDc_befan(chulsanInfo.getDept_cd());
						 lvVoDtl2.setYn_ss("N");
						 lvVoDtl2.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
						 	commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl2);
						 }
						 
						 //인사발령 Detail (직위)
						 ErpLeave lvVoDtl3 = new ErpLeave();
						 lvVoDtl3.setStatus("I");
						 lvVoDtl3.setCd_company(campanyCode);
						 lvVoDtl3.setNo_an(idxCode);
						 lvVoDtl3.setNo_emp(chulsanInfo.getUser_id());						 
						 lvVoDtl3.setCd_ancode("014");
						 lvVoDtl3.setCd_anexpen("H03");
						 lvVoDtl3.setDc_aftan(chulsanInfo.getPos_cd());
						 lvVoDtl3.setDc_befan(chulsanInfo.getPos_cd());
						 lvVoDtl3.setYn_ss("N");
						 lvVoDtl3.setId_update("MISO");
						 
						 if(SystemArea.equals("REAL")) {
							 commonDAOERP.insertLeaveInfoDetailForErp(lvVoDtl3);
						 }
						 
						 ErpLeave lvVoInsa = new ErpLeave();
						 
						 String workResn = URLDecoder.decode(chulsanInfo.getBigo(),"UTF-8").replaceAll("<br/>", "\n");
						 
						 lvVoInsa.setCd_company(campanyCode);
						 lvVoInsa.setNo_an(idxCode);
						 lvVoInsa.setNo_emp(chulsanInfo.getUser_id());
						 lvVoInsa.setCd_ancode("014");
						 lvVoInsa.setDt_from(chulsanInfo.getCh_from());
						 lvVoInsa.setDt_to(chulsanInfo.getCh_to());
						 lvVoInsa.setDc_reason(workResn);
						 lvVoInsa.setYn_pay("N");
						 lvVoInsa.setYn_retire("N");
						 lvVoInsa.setTp_an("001");
						 lvVoInsa.setDt_an(CurrentDateTime.getDate().substring(0, 8));
						 lvVoInsa.setDc_rmk("");
						 lvVoInsa.setId_insert("MISO");
						 lvVoInsa.setCd_rest("");
					
						 
						 System.out.println("lvVoInsa.getCd_company();" +  lvVoInsa.getCd_company());
						 System.out.println("lvVoInsa.getNo_an();     " +  lvVoInsa.getNo_an());
						 System.out.println("lvVoInsa.getNo_emp();    " +  lvVoInsa.getNo_emp());
						 System.out.println("lvVoInsa.getCd_ancode(); " +  lvVoInsa.getCd_ancode());
						 System.out.println("lvVoInsa.getDt_from();   " +  lvVoInsa.getDt_from());
						 System.out.println("lvVoInsa.getDt_to();     " +  lvVoInsa.getDt_to());
						 System.out.println("lvVoInsa.getDc_reason(); " +  lvVoInsa.getDc_reason());
						 System.out.println("lvVoInsa.getYn_pay();    " +  lvVoInsa.getYn_pay());
						 System.out.println("lvVoInsa.getYn_retire(); " +  lvVoInsa.getYn_retire());
						 System.out.println("lvVoInsa.getTp_an();     " +  lvVoInsa.getTp_an());
						 System.out.println("lvVoInsa.getDt_an();     " +  lvVoInsa.getDt_an());
						 System.out.println("lvVoInsa.getDc_rmk();    " +  lvVoInsa.getDc_rmk());
						 System.out.println("lvVoInsa.getId_insert(); " +  lvVoInsa.getId_insert());
						 System.out.println("lvVoInsa.getCd_rest();   " +  lvVoInsa.getCd_rest());
						 
						 if(SystemArea.equals("REAL")) {
							commonDAOERP.insertChulsanInfoInsaForErp(lvVoInsa);
						 }
						 
						 
					 //휴일근로신청 결재 완료시
					 }else if(vo.getTask_cd().equals("HW")){
					 
						 HolidayWork hwVo = new HolidayWork();
						 hwVo.setAppr_no(vo.getAppr_no());
						 HolidayWork hwInfo = holidayWorkDao.selectHolidayWorkApplInfoForErp(hwVo);
						 
						 ErpHolidayWork hwErpVo = new ErpHolidayWork();	
						 
						 String wCode = "";
						 if(hwInfo.getHoli_type().equals("A")){
							 wCode = "G16";
						 }else if(hwInfo.getHoli_type().equals("B")){
							 wCode = "G23";
						 }
						 
						 hwErpVo.setCd_company("0101");   
						 hwErpVo.setCd_bizarea("1000");   
						 hwErpVo.setNo_emp(hwInfo.getUser_id());       
						 hwErpVo.setNo_proposal(hwInfo.getAppr_no_fin());  
						 hwErpVo.setCd_wcode(wCode);     
						 hwErpVo.setDt_proposal(hwInfo.getReq_dt());  
						 hwErpVo.setDt_start(hwInfo.getWork_ymd());     
						 hwErpVo.setDt_close(hwInfo.getWork_ymd());
						 
						 int timeStart = Integer.parseInt(hwInfo.getWork_time_dtl_cd().substring(1));
						 int workTime =  Integer.parseInt(hwInfo.getWork_time());
						 
						 int workStart = timeStart*60;
						 int workFinish = (timeStart+workTime)*60;
						 
						 //8시간일 경우 60분추가
						 if(workTime == 8) {
							 workFinish = workFinish+60;
						 }
						 
						 hwErpVo.setTm_start(Integer.toString(workStart));     
						 hwErpVo.setTm_close(Integer.toString(workFinish));
						 
						 hwErpVo.setDy_proposal(Integer.toString(workTime*60));  
						 
						 String workArea = "";
						 if(hwInfo.getWork_area_cd().equals("I")) {
							 workArea = hwInfo.getWork_area();
						 }else if(hwInfo.getWork_area_cd().equals("O")) {
							 workArea = hwInfo.getWork_area() + "(" + hwInfo.getWork_area_dtl() +  ")";
						 }else if(hwInfo.getWork_area_cd().equals("S")) {
							 workArea = hwInfo.getWork_area() + "(" + hwInfo.getWork_area_dtl() +  ")";
						 }
						 
						 hwErpVo.setDc_rmk(workArea);       
						 hwErpVo.setNo_cemp("");      
						 hwErpVo.setCd_consent("001");   
						 hwErpVo.setNo_tel_emer("");  
						 hwErpVo.setNo_tel("");       
						 hwErpVo.setNo_emp_an("");    
						 hwErpVo.setState("N");  	    
						 hwErpVo.setCd_wtype("003");     
						 hwErpVo.setId_insert("MISO");  
						 
						 hwErpVo.setDt_consent("");   
						 hwErpVo.setNm_cemp("");    
						 
						 String workResn = URLDecoder.decode(hwInfo.getWork_resn(),"UTF-8").replaceAll("<br/>", "\n");
						 hwErpVo.setDc_rmk1(workResn); 
						 
						 if(hwInfo.getHoli_type().equals("A")){
							 hwErpVo.setDy_ysub("0");		
							 hwErpVo.setYn_pay("N");		
							 hwErpVo.setNm_start("");		
							 hwErpVo.setDc_rmk2("");		
							 hwErpVo.setDc_rmk3("");		
							 hwErpVo.setDt_wreplace("");  
						 }else if(hwInfo.getHoli_type().equals("B")){
							 hwErpVo.setDy_ysub(Integer.toString(workTime*60));		
							 hwErpVo.setYn_pay("Y");		
							 hwErpVo.setNm_start("");		
							 hwErpVo.setDc_rmk2("");		
							 hwErpVo.setDc_rmk3("");		
							 hwErpVo.setDt_wreplace(hwInfo.getHoli_ymd_sub());  
						 }
						 
						 
						 System.out.println("hwErpVo.getCd_company()); :::"+hwErpVo.getCd_company());
						 System.out.println("hwErpVo.getCd_bizarea()); :::"+hwErpVo.getCd_bizarea());
						 System.out.println("hwErpVo.getNo_emp());     :::"+hwErpVo.getNo_emp());
						 System.out.println("hwErpVo.getNo_proposal());:::"+hwErpVo.getNo_proposal());
						 System.out.println("hwErpVo.getCd_wcode());   :::"+hwErpVo.getCd_wcode());
						 System.out.println("hwErpVo.getDt_proposal());:::"+hwErpVo.getDt_proposal());
						 System.out.println("hwErpVo.getDt_start());   :::"+hwErpVo.getDt_start());
						 System.out.println("hwErpVo.getDt_close());   :::"+hwErpVo.getDt_close());
						 System.out.println("hwErpVo.getTm_start());   :::"+hwErpVo.getTm_start());
						 System.out.println("hwErpVo.getTm_close());   :::"+hwErpVo.getTm_close());
						 System.out.println("hwErpVo.getDy_proposal());:::"+hwErpVo.getDy_proposal());
						 System.out.println("hwErpVo.getDc_rmk());     :::"+hwErpVo.getDc_rmk());
						 System.out.println("hwErpVo.getNo_cemp());    :::"+hwErpVo.getNo_cemp());
						 System.out.println("hwErpVo.getCd_consent()); :::"+hwErpVo.getCd_consent());
						 System.out.println("hwErpVo.getNo_tel_emer());:::"+hwErpVo.getNo_tel_emer());
						 System.out.println("hwErpVo.getNo_tel());     :::"+hwErpVo.getNo_tel());
						 System.out.println("hwErpVo.getNo_emp_an());  :::"+hwErpVo.getNo_emp_an());
						 System.out.println("hwErpVo.getState());      :::"+hwErpVo.getState());
						 System.out.println("hwErpVo.getCd_wtype());   :::"+hwErpVo.getCd_wtype());
						 System.out.println("hwErpVo.getId_insert());  :::"+hwErpVo.getId_insert());
						 System.out.println("hwErpVo.getDt_consent()); :::"+hwErpVo.getDt_consent());
						 System.out.println("hwErpVo.getNm_cemp());    :::"+hwErpVo.getNm_cemp());
						 System.out.println("hwErpVo.getDc_rmk1());    :::"+hwErpVo.getDc_rmk1());
						 
						 System.out.println("hwErpVo.getDy_ysub());    :::"+hwErpVo.getDy_ysub());
						 System.out.println("hwErpVo.getYn_pay());     :::"+hwErpVo.getYn_pay());
						 System.out.println("hwErpVo.getNm_start());   :::"+hwErpVo.getNm_start());
						 System.out.println("hwErpVo.getDc_rmk2());    :::"+hwErpVo.getDc_rmk2());
						 System.out.println("hwErpVo.getDc_rmk3());    :::"+hwErpVo.getDc_rmk3());
						 System.out.println("hwErpVo.getDt_wreplace());:::"+hwErpVo.getDt_wreplace());
						
						 if(SystemArea.equals("REAL")) { 
							 commonDAOERP.insertHolidayWorkInfoForErp(hwErpVo);
						 }
						  
					 //데이터반출신청 결재 완료시
					 }else if(vo.getTask_cd().equals("SO")){
						 System.out.println("결재완료 appr_no : " + vo.getAppr_no());
						 SourceOut so = new SourceOut();
						 so.setAppr_no(vo.getAppr_no());
						 List<SourceOut> ApprSoInfo = sourceOutDao.selectCompleteSoApprInfo(so);
						 
						 for(int i=0; i<ApprSoInfo.size(); i++){
							 docNo = StringUtil.getDocNo();
							 ApprSoInfo.get(i).setDoc_no(docNo);
						 	 sourceOutDao.insertCompleteSoAppl(ApprSoInfo.get(i));
						 }
					 
				     //자산반출입 결재 완료시
					 }else if(vo.getTask_cd().equals("AI")){
						 System.out.println("결재완료 appr_no : " + vo.getAppr_no());	
						 AssetInout ai = new AssetInout();
						 ai.setAppr_no(vo.getAppr_no());
						 List<AssetInout> ApprAiInfo = assetInoutDao.selectCompleteAiApprInfo(ai);
					  
						 for(int i=0; i<ApprAiInfo.size(); i++){
							 docNo = StringUtil.getDocNo();
							 ApprAiInfo.get(i).setDoc_no(docNo);
							 assetInoutDao.insertCompleteAiAppl(ApprAiInfo.get(i));
						 }
				     
					 //도서구입신청 결재 완료시
					 }else if(vo.getTask_cd().equals("BP")){
						 System.out.println("결재완료 appr_no : " + vo.getAppr_no());	
						 Book bp = new Book();
						 bp.setAppr_no(vo.getAppr_no());
						 List<Book> ApprBpInfo = bookDao.selectCompleteBpApprInfo(bp);
					  
						 for(int i=0; i<ApprBpInfo.size(); i++){
							 docNo = StringUtil.getDocNo();
							 ApprBpInfo.get(i).setDoc_no(docNo);
							 bookDao.insertCompleteBpAppl(ApprBpInfo.get(i));
						 }
					 //전자공문 결재 완료시 
					 }else if(vo.getTask_cd().equals("OD")){
						 
						 String reqYm = commonDAO.selectApprInfoReqDate(vo);
						 
						 OfficialDocument ofVo = new OfficialDocument();
						 ofVo.setDoc_no(reqYm);
						 String docSeq = officialDocumentDao.selectOfficialDocumentDocSeq(ofVo);
						 
						 String odDocNo = reqYm + "-" + docSeq;
						 
						 ofVo.setOd_doc_no(odDocNo);
						 ofVo.setAppr_no(vo.getAppr_no());
						 
						 officialDocumentDao.updateOfficialDocumentDocSeq(ofVo);
					 // 결혼화환, 웨딩카 결재 완료시	 
					 }else if(vo.getTask_cd().equals("WD")){
						 System.out.println("결재완료 appr_no : " + vo.getAppr_no());	
						 
						 Wedding wdVo = new Wedding();
						 wdVo.setAppr_no(vo.getAppr_no());
						 
						 // 해당 승인번호의 신청서 정보 가져오기 
						 Wedding wdInfo = weddingDao.doSelectWeddingApplInfo(wdVo);
						 
						 // 화환업체 email 리스트 
						 Wedding flowerVo = new Wedding(); 
						 flowerVo.setLcd("A");
						 List<Wedding> flowerEmailList = weddingDao.selectUseWdEmailList(flowerVo);
						 
						 // 웨딩카업체 email 리스트 
						 Wedding carVo = new Wedding(); 
						 carVo.setLcd("B");
						 List<Wedding> carEmailList = weddingDao.selectUseWdEmailList(carVo);
						 
						 SendMail oMail1 = new SendMail();
						 String mail_ttl1 = "화환이 신청되었습니다.";
						 StringBuffer content1= new StringBuffer();
						 // 이메일 내용 1 : 화환 신청일 때 
					     content1.append("<!DOCTYPE html>																			");		
					     content1.append(" <html>																					");		
					     content1.append(" <head>																					");		
					     content1.append(" <meta charset=\"UTF-8\">																	");	
					     content1.append(" <title></title>																			");	
					     content1.append(" </head>																					");		  																							
					     content1.append(" <body bgcolor='#ffffff' align='center'  >												");	
					     content1.append(" <body bgcolor='#ffffff' align='center'  >												");	
					     content1.append(" <span><font size='4' face='Malgun Gothic'>안녕하십니까? 현대엠엔소프트 화환 신청 내용입니다.</font></span>");
					     
					     content1.append(" 	<table align='center' border=1 width='1100' frame='box'  >								");	
					     content1.append(" 		<tr align='center'>																	");	
					     content1.append("			<th colspan=4>																	");	
					     content1.append(" 				<h3>&#91;<font face='Malgun Gothic'>신청자 정보</font>&#93;</h3>			");	
					     content1.append(" 			</th>																			");	
					     content1.append(" 		</tr>																				");						
					     content1.append(" 		<tr>                                                                                ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>문서번호</th>                                 ");
					     content1.append(" 			<td colspan=3 align='left'>"+vo.getAppr_no_fin()+"</td>                         ");
					     content1.append(" 		</tr>                                                                               ");
					     content1.append(" 		<tr>                                                                                ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>사번</th>                                     ");
					     content1.append(" 			<td width='30%' align='left'>"+wdInfo.getUser_id()+"</td>                       ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>이름</th>                                     ");
					     content1.append(" 			<td width='30%' align='left'>"+wdInfo.getUser_nm()+"</td>						");
					     content1.append(" 		</tr>                                                                               ");
					     content1.append(" 		<tr>                                                                                ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>부서</th>                                     ");
					     content1.append(" 			<td width='30%' align='left'>"+wdInfo.getDept_nm()+"</td>                       ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>직급</th>                                     ");
					     content1.append(" 			<td width='30%' align='left'>"+wdInfo.getPos_nm()+"</td>						");
					     content1.append(" 		</tr>                                                                               ");
					     content1.append(" 	</table>																			    ");
					     
					     content1.append(" 	<table align='center' border=1 width='1100' frame='box'  >								");	
					     content1.append(" 		<tr align='center'>																	");	
					     content1.append("			<th colspan=4>																	");	
					     content1.append(" 				<h3>&#91;<font face='Malgun Gothic'>결혼화환 정보</font>&#93;</h3>			");	
					     content1.append(" 			</th>																			");	
					     content1.append(" 		</tr>																				");						
					     content1.append(" 		<tr>                                                                                ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>일시</th>                                     ");
					     content1.append(" 			<td width='30%'>"+wdInfo.getWd_ymd()+"</td>                                     ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>장소</th>                                     ");
					     content1.append(" 			<td width='30%'>"+wdInfo.getWd_place()+"</td>								    ");
					     content1.append(" 		</tr>                                                                               ");
					     content1.append(" 		<tr>                                                                                ");
					     content1.append(" 			<th width='20%' bgcolor='#F5F5F5'>결혼화환</th>                                 ");
					     content1.append(" 			<td colspan=3 align='left'>                                                     ");
					     content1.append(" "+wdInfo.getWd_type()+" ");
					     if (wdInfo.getWd_type_cd().equals("X")) {                                                 
					    	 content1.append(" : "+wdInfo.getWd_type_nm()+"");
					     }
					     content1.append(" 			</td>                                                              				");
					     content1.append(" 		</tr>                                                                               ");
					     content1.append(" 	</table>																			    ");
					     content1.append(" </body>																					");
					     content1.append("</html>																					");
											
					     
						 SendMail oMail2 = new SendMail();
						 String mail_ttl2 = "웨딩카가 신청되었습니다.";
					     StringBuffer content2= new StringBuffer();
					     // 이메일 내용 2 : 웨딩카 신청일 때 
					     content2.append("<!DOCTYPE html>																			");		
					     content2.append(" <html>																					");		
					     content2.append(" <head>																					");		
					     content2.append(" <meta charset=\"UTF-8\">																	");	
					     content2.append(" <title></title>																			");	
					     content2.append(" </head>																					");		  																							
					     content2.append(" <body bgcolor='#ffffff' align='center'  >												");	
					     content2.append(" <body bgcolor='#ffffff' align='center'  >												");	
					     content2.append(" <span><font size='4' face='Malgun Gothic'>안녕하십니까? 현대엠엔소프트 웨딩카 신청 내용입니다.</font></span>");	

					     content2.append(" 	<table align='center' border=1 width='1100' frame='box'  >								");	
					     content2.append(" 		<tr align='center'>																	");	
					     content2.append("			<th colspan=4>																	");	
					     content2.append(" 				<h3>&#91;<font face='Malgun Gothic'>신청자 정보</font>&#93;</h3>			");	
					     content2.append(" 			</th>																			");	
					     content2.append(" 		</tr>																				");						
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>문서번호</th>                                 ");
					     content2.append(" 			<td colspan=3 align='left'>"+wdInfo.getAppr_no()+"</td>                         ");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>사번</th>                                     ");
					     content2.append(" 			<td width='30%' align='left'>"+wdInfo.getUser_id()+"</td>                       ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>이름</th>                                     ");
					     content2.append(" 			<td width='30%' align='left'>"+wdInfo.getUser_nm()+"</td>						");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>부서</th>                                     ");
					     content2.append(" 			<td width='30%' align='left'>"+wdInfo.getDept_nm()+"</td>                       ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>직급</th>                                     ");
					     content2.append(" 			<td width='30%' align='left'>"+wdInfo.getPos_nm()+"</td>						");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 	</table>																			    ");
					     
					     content2.append(" 	<table align='center' border=1 width='1100' frame='box'  >								");	
					     content2.append(" 		<tr align='center'>																	");	
					     content2.append("			<th colspan=4>																	");	
					     content2.append(" 				<h3>&#91;<font face='Malgun Gothic'>웨딩카 정보</font>&#93;</h3>			");	
					     content2.append(" 			</th>																			");	
					     content2.append(" 		</tr>																				");						
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th width='20%' bgcolor='#F5F5F5'>웨딩카</th>                                   ");
					     content2.append(" 			<td width='80%' align='left'>"+wdInfo.getCar_date()+"</td>                      ");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th bgcolor='#F5F5F5'>픽업장소</th>                                             ");
					     content2.append(" 			<td align='left'>"+wdInfo.getCar_place()+"</td>                                 ");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 		<tr>                                                                                ");
					     content2.append(" 			<th bgcolor='#F5F5F5'>반납방법</th>                                             ");
					     content2.append(" 			<td align='left'>"+wdInfo.getCar_return_nm()+"</td>                             ");
					     content2.append(" 		</tr>                                                                               ");
					     content2.append(" 	</table>																			    ");
					     content2.append(" </body>																					");
					     content2.append("</html>																					");
					     
					     
					     // 이메일 발송
					     
						 if(wdInfo.getWd_gubun().equals("1")){ // 결혼화환에 체크
							 // 화환업체에게 이메일 발송
							 for(int i=0 ; i<flowerEmailList.size(); i++){
						         System.out.println("보내는 이 : " + "chongmu@hyundai-mnsoft.com");
								 System.out.println("받는 이 : " + flowerEmailList.get(i).getEmail());
							 
								 oMail1.sendMail(flowerEmailList.get(i).getEmail(), "chongmu@hyundai-mnsoft.com", mail_ttl1, content1.toString(), 1, false);
							 }
							 
						 } else if (wdInfo.getWd_gubun().equals("2")){ // 웨딩카에 체크
							 // 웨딩카업체에게 이메일 발송
							 for(int i=0 ; i<carEmailList.size(); i++){
						         System.out.println("보내는 이 : " + "chongmu@hyundai-mnsoft.com");
								 System.out.println("받는 이 : " + carEmailList.get(i).getEmail());
							 
								 oMail2.sendMail(carEmailList.get(i).getEmail(), "chongmu@hyundai-mnsoft.com", mail_ttl2, content2.toString(), 1, false);
							 }
							 
						 } else if (wdInfo.getWd_gubun().equals("3")){ // 둘다 체크
							 // 화환업체에게 이메일 발송
							 for(int i=0 ; i<flowerEmailList.size(); i++){
						         System.out.println("보내는 이 : " + "chongmu@hyundai-mnsoft.com");
								 System.out.println("받는 이 : " + flowerEmailList.get(i).getEmail());
							 
								 oMail1.sendMail(flowerEmailList.get(i).getEmail(), "chongmu@hyundai-mnsoft.com", mail_ttl1, content1.toString(), 1, false);
							 }
							 
							 // 웨딩카업체에게 이메일 발송
							 for(int i=0 ; i<carEmailList.size(); i++){
						         System.out.println("보내는 이 : " + "chongmu@hyundai-mnsoft.com");
								 System.out.println("받는 이 : " + carEmailList.get(i).getEmail());
							 
								 oMail2.sendMail(carEmailList.get(i).getEmail(), "chongmu@hyundai-mnsoft.com", mail_ttl2, content2.toString(), 1, false);
							 }
						 }
					}else if(vo.getTask_cd().equals("DP")){
						Dispatch dispatchVo = new Dispatch();
						dispatchVo.setAppr_no(vo.getAppr_no());
						Dispatch dispatchInfo = dispatchDao.selectDispatchApplInfo(dispatchVo);

						ErpDispatch ErpDispatchVo = new ErpDispatch();

						String txtYM;
						//결재 완료일
						String currYmd = CurrentDateTime.getDate().substring(0, 8);
						
						int yyyy = Integer.parseInt(currYmd.substring(0, 4));
						int mm = Integer.parseInt(currYmd.substring(4, 6));
						int dd = Integer.parseInt(currYmd.substring(6));
						
						/*
						 * if(dd > 20) { if(mm > 11){ txtYM = String.valueOf(yyyy+1)+"01"; }else { txtYM
						 * = String.valueOf(yyyy)+ String.valueOf(mm+1); } }else { txtYM =
						 * String.valueOf(yyyy)+ String.valueOf(mm); }
						 */
						
						//'지급월 계산 : 전월 21일부터 금월 20일까지는 금월, 금월 21일 이후는 다음월
						 if(dd > 20) {
							 if(mm > 11){
								 txtYM = String.valueOf(yyyy+1)+"01";
							 }else {
								 String calmon = String.valueOf(mm+1);
								 if(calmon.length() == 1) {
									 calmon = "0"+calmon;
								 }
								 txtYM = String.valueOf(yyyy)+ calmon;
							 }
							 
						 }else {
							 String calmon = String.valueOf(mm);
							 if(calmon.length() == 1) {
								 calmon = "0"+calmon;
							 }
							 txtYM = String.valueOf(yyyy)+ calmon;
						 }

						ErpDispatchVo.setCd_company("0101");
						ErpDispatchVo.setTxtYM(txtYM);
						ErpDispatchVo.setSeq(1);
						ErpDispatchVo.setUser_id(dispatchInfo.getUser_id());
						ErpDispatchVo.setCd_fluct("142|");
						String dpPrice = StringUtil.isNullToString(dispatchInfo.getDp_price());
						if(dpPrice.equals("")) {
							dpPrice = "0";
						}
						
						ErpDispatchVo.setTxtValue(dpPrice+"|");
						ErpDispatchVo.setId_insert("MISO");

						if(SystemArea.equals("REAL")) {
							commonDAOERP.insertDispatchForErp(ErpDispatchVo);
						}
						System.out.println("ErpDispatchVo.getYm(); :::" + ErpDispatchVo.getTxtYM());

					 }else if(vo.getTask_cd().equals("CP")){
					 
					 
				//	 if(!SystemArea.equals("REAL")) {
						 CarPurchase hwVo = new CarPurchase();
						 hwVo.setAppr_no(vo.getAppr_no());
						 CarPurchase hwInfo = carPurchaseDao.selectCarPurchaseApprInfo(hwVo);
						 
						 ErpCarPurchase hwErpVo = new ErpCarPurchase();	
						 
						 
						 String txtYM;
						 //결재 완료일
						 String currYmd = CurrentDateTime.getDate().substring(0, 8);
						 
						 int yyyy = Integer.parseInt(currYmd.substring(0, 4));
						 int mm = Integer.parseInt(currYmd.substring(4, 6));
						 int dd = Integer.parseInt(currYmd.substring(6));
						 //'지급월 계산 : 전월 21일부터 금월 20일까지는 금월, 금월 21일 이후는 다음월
						 if(dd > 20) {
							 if(mm > 11){
								 txtYM = String.valueOf(yyyy+1)+"01";
							 }else {
								 String calmon = String.valueOf(mm+1);
								 if(calmon.length() == 1) {
									 calmon = "0"+calmon;
								 }
								 txtYM = String.valueOf(yyyy)+ calmon;
							 }
							 
						 }else {
							 String calmon = String.valueOf(mm);
							 if(calmon.length() == 1) {
								 calmon = "0"+calmon;
							 }
							 txtYM = String.valueOf(yyyy)+ calmon;
						 }
						 
						 
						 hwErpVo.setCd_company("0101");   
						 hwErpVo.setTxtYM(txtYM);   
						 hwErpVo.setSeq(1);       
						 hwErpVo.setUser_id(hwInfo.getUser_id());  
						 hwErpVo.setCd_fluct("130|");   
						 hwErpVo.setTxtValue(hwInfo.getSup_money()+"|");						  
						 hwErpVo.setId_insert("MISO");
				
					
						 
						 
						 System.out.println("hwErpVo.getCd_company()); :::"+hwErpVo.getCd_company());
						 System.out.println("hwErpVo.getTxtYM()); :::"+hwErpVo.getTxtYM());
						 System.out.println("hwErpVo.getSeq());     :::"+hwErpVo.getSeq());
						 System.out.println("hwErpVo.getUser_id());:::"+hwErpVo.getUser_id());
						 System.out.println("hwErpVo.getCd_fluct());   :::"+hwErpVo.getCd_fluct());
						 System.out.println("hwErpVo.getTxtValue());:::"+hwErpVo.getTxtValue());
						 System.out.println("hwErpVo.getId_insert());   :::"+hwErpVo.getId_insert());
						
						 if(SystemArea.equals("REAL")) { 
							 commonDAOERP.insertCarPurchaseForErp(hwErpVo);
						 }
						 
					// }
					 
			     
				 }else if(vo.getTask_cd().equals("ED")){	
					 
					 Education educationVo = new Education();
					 educationVo.setAppr_no(vo.getAppr_no());
					 Education educationInfo = educationDao.selectEducationApplInfoForErp(educationVo);
					 
					 String campanyCode = "0101";
					 String yy_edu = vo.getAppr_no_fin().substring(0, 4);
					 
					 //교육 HUEDUCODE
					 ErpEducation edVoDucode = new ErpEducation();
					 edVoDucode.setCd_company(campanyCode);
					 edVoDucode.setYy_edu(yy_edu);
					 edVoDucode.setCd_edu(vo.getAppr_no_fin());
					 edVoDucode.setNm_edu(educationInfo.getEd_nm());
					 edVoDucode.setDc_edu(URLDecoder.decode(educationInfo.getEd_purpose(),"utf-8"));
					 edVoDucode.setNm_eduser(educationInfo.getEd_org());
					 edVoDucode.setNm_loc(educationInfo.getEd_place());
					 edVoDucode.setCd_inout(educationInfo.getEd_host());
					 edVoDucode.setDt_from(educationInfo.getFrom_ymd().replaceAll("-", ""));
					 edVoDucode.setDt_to(educationInfo.getTo_ymd().replaceAll("-", ""));
					 edVoDucode.setDy_edu("0");
					 edVoDucode.setDc_edutime(educationInfo.getEd_time());
					 edVoDucode.setDc_edutar("");
					 edVoDucode.setDc_per("");
					 edVoDucode.setNm_lec("");
					 edVoDucode.setAm_edu(educationInfo.getEd_ex());
					 edVoDucode.setAm_eduemp("0"); //고용보험환급액
					 edVoDucode.setAm_eduspl("0");
					 edVoDucode.setYn_edu("N");
					 edVoDucode.setCd_epart("004");
					 edVoDucode.setDc_rmk(URLDecoder.decode(educationInfo.getEd_plan(),"utf-8"));
					 
					 edVoDucode.setCd_edupart(educationInfo.getEd_gubun()); //교육구분 코드
					 
					 edVoDucode.setCd_edu_h("");
					 edVoDucode.setId_insert("MISO");
					 edVoDucode.setNo_lec("");
					 edVoDucode.setNo_eduser("");
					 edVoDucode.setPt_mileage("0");
					 edVoDucode.setNm_edu_l5("");
					 
					 if(SystemArea.equals("REAL")) {
						 commonDAOERP.insertEducationInfoDucodeForErp(edVoDucode);
					 }
					 
					 //교육 HUEPCODE
					 ErpEducation edVoPcode = new ErpEducation();
					 edVoPcode.setCd_company(campanyCode);
					 edVoPcode.setYy_edu(yy_edu);
					 edVoPcode.setCd_edu(vo.getAppr_no_fin());
					 edVoPcode.setDc_epart(educationInfo.getUser_id());
					 edVoPcode.setId_insert("MISO");
					 
					 if(SystemArea.equals("REAL")) {
					 	commonDAOERP.insertEducationInfoPcodeForErp(edVoPcode);
					 }
					 
					 //교육 HUEDUPER
					 ErpEducation edVoDuper = new ErpEducation();
					 String insuranse_yn = educationInfo.getInsurance_yn();
					 String ingCost = "";
					 if(insuranse_yn.equals("Y")){
						 ingCost = Integer.toString(Integer.parseInt(educationInfo.getEd_ex()));
					 }else{
						 ingCost = educationInfo.getEd_ex();
					 }
					 
					 edVoDuper.setCd_company(campanyCode);           
					 edVoDuper.setYy_edu(yy_edu);                    
					 edVoDuper.setCd_edu(vo.getAppr_no_fin());
					 edVoDuper.setNo_emp(educationInfo.getUser_id());						 
					 edVoDuper.setCd_dept(educationInfo.getDept_cd());
					 edVoDuper.setAm_eduemp(educationInfo.getEd_ex());
					 
					 edVoDuper.setAm_eduspl(ingCost); //실부담금?
					 
					 edVoDuper.setCd_pass("100");
					 edVoDuper.setPt_attend("0");
					 edVoDuper.setPt_attit("0");
					 edVoDuper.setPt_assign("0");
					 edVoDuper.setDc_eudass("");
					 edVoDuper.setDc_rmk(URLDecoder.decode(educationInfo.getEd_plan(),"utf-8"));
					 edVoDuper.setDc_edutime_per(educationInfo.getEd_time());
					 edVoDuper.setId_insert("MISO");
					 edVoDuper.setPt_adj("0");
					 
					 if(SystemArea.equals("REAL")) {
						 commonDAOERP.insertEducationInfoDuperForErp(edVoDuper);
					 }
					 
					 System.out.println("educationInfo.getEd_host(); :::" + educationInfo.getEd_host());
					 System.out.println("educationInfo.getAppr_no(); :::" + vo.getAppr_no_fin());
					 System.out.println("educationInfo.getEd_nm(); :::" + educationInfo.getEd_nm());
					 System.out.println("educationInfo.getEd_purpose(); :::" + educationInfo.getEd_purpose());
					 System.out.println("educationInfo.getEd_org(); :::" + educationInfo.getEd_org());
					 System.out.println("educationInfo.getEd_place(); :::" + educationInfo.getEd_place());
					 System.out.println("educationInfo.getFrom_ymd(); :::" + educationInfo.getFrom_ymd());
					 System.out.println("educationInfo.getTo_ymd(); :::" + educationInfo.getTo_ymd());
					 System.out.println("educationInfo.getEd_time(); :::" + educationInfo.getEd_time());
					 System.out.println("educationInfo.getEd_ex(); :::" + educationInfo.getEd_ex());
					 System.out.println("educationInfo.getEd_plan(); :::" + educationInfo.getEd_plan());
					 System.out.println("educationInfo.getEd_gubun(); :::" + educationInfo.getEd_gubun());
					 System.out.println("educationInfo.getUser_id(); :::" + educationInfo.getUser_id());
					 System.out.println("educationInfo.getDept_cd(); :::" + educationInfo.getDept_cd());
					 System.out.println("educationInfo.getEd_ex(); :::" + educationInfo.getEd_ex());
					 System.out.println("educationInfo.getEd_plan(); :::" + educationInfo.getEd_plan());
					 System.out.println("educationInfo.getEd_time(); :::" + educationInfo.getEd_time());
					 
				 }else if(vo.getTask_cd().equals("EC")){
					 EducationalExpenses edExpensesVo = new EducationalExpenses();
					 edExpensesVo.setAppr_no(vo.getAppr_no());
					 EducationalExpenses edExpensesInfo = educationalExpensesDao.selectEdExpensesApplInfoForErp(edExpensesVo);
					 
					 String campanyCode = "0101";
					 String txtYM;
					 //결재 완료일
					 String currYmd = CurrentDateTime.getDate().substring(0, 8);
					 
					 int yyyy = Integer.parseInt(currYmd.substring(0, 4));
					 int mm = Integer.parseInt(currYmd.substring(4, 6));
					 int dd = Integer.parseInt(currYmd.substring(6));
					 
					/*
					 * if(dd > 20) { if(mm > 11){ txtYM = String.valueOf(yyyy+1)+"01"; }else { txtYM
					 * = String.valueOf(yyyy)+ String.valueOf(mm+1); }
					 * 
					 * }else { txtYM = String.valueOf(yyyy)+ String.valueOf(mm); }
					 */
					 
					//'지급월 계산 : 전월 21일부터 금월 20일까지는 금월, 금월 21일 이후는 다음월
					 if(dd > 20) {
						 if(mm > 11){
							 txtYM = String.valueOf(yyyy+1)+"01";
						 }else {
							 String calmon = String.valueOf(mm+1);
							 if(calmon.length() == 1) {
								 calmon = "0"+calmon;
							 }
							 txtYM = String.valueOf(yyyy)+ calmon;
						 }
						 
					 }else {
						 String calmon = String.valueOf(mm);
						 if(calmon.length() == 1) {
							 calmon = "0"+calmon;
						 }
						 txtYM = String.valueOf(yyyy)+ calmon;
					 }
					 
					//교육 HUEDUCODE
					 ErpEducationalExpenses ecVoPfluct = new ErpEducationalExpenses();
					 ecVoPfluct.setCd_company(campanyCode);
					 ecVoPfluct.setYm(txtYM);
					 ecVoPfluct.setNo_seq("1");
					 ecVoPfluct.setNo_emp(edExpensesInfo.getUser_id());
					 ecVoPfluct.setCd_fluct("150|");
					 ecVoPfluct.setAm_fpay(edExpensesInfo.getSum_expenses()+"|");
					 ecVoPfluct.setId_update("MISO");
					 
					 if(SystemArea.equals("REAL")) {
						 commonDAOERP.insertEdExpensesInfoPfluctForErp(ecVoPfluct);
					 }
					 
					 System.out.println("edExpensesInfo.getUser_id(); :::" + edExpensesInfo.getUser_id());
					 System.out.println("edExpensesInfo.getSum_expenses(); :::" + edExpensesInfo.getSum_expenses());
					 System.out.println("ecVoPfluct.setYm(); :::" + ecVoPfluct.getYm());
					 
				 }else if(vo.getTask_cd().equals("MC")){
					 Medical medicalVo = new Medical();
					 medicalVo.setAppr_no(vo.getAppr_no());
					 Medical medicalInfo = medicalDao.selectMedicalApplInfoForErp(medicalVo);
					 
					 String campanyCode = "0101";
					 String txtYM;
					 //결재 완료일
					 String currYmd = CurrentDateTime.getDate().substring(0, 8);
					 
					 int yyyy = Integer.parseInt(currYmd.substring(0, 4));
					 int mm = Integer.parseInt(currYmd.substring(4, 6));
					 int dd = Integer.parseInt(currYmd.substring(6));
					 
					/*
					 * if(dd > 20) { if(mm > 11){ txtYM = String.valueOf(yyyy+1)+"01"; }else { txtYM
					 * = String.valueOf(yyyy)+ String.valueOf(mm+1); }
					 * 
					 * }else { txtYM = String.valueOf(yyyy)+ String.valueOf(mm); }
					 */
					 
					//'지급월 계산 : 전월 21일부터 금월 20일까지는 금월, 금월 21일 이후는 다음월
					 if(dd > 20) {
						 if(mm > 11){
							 txtYM = String.valueOf(yyyy+1)+"01";
						 }else {
							 String calmon = String.valueOf(mm+1);
							 if(calmon.length() == 1) {
								 calmon = "0"+calmon;
							 }
							 txtYM = String.valueOf(yyyy)+ calmon;
						 }
						 
					 }else {
						 String calmon = String.valueOf(mm);
						 if(calmon.length() == 1) {
							 calmon = "0"+calmon;
						 }
						 txtYM = String.valueOf(yyyy)+ calmon;
					 }
					 
					//교육 HUEDUCODE
					 ErpMedical medicalVoPfluct = new ErpMedical();
					 medicalVoPfluct.setCd_company(campanyCode);
					 medicalVoPfluct.setYm(txtYM);
					 medicalVoPfluct.setNo_seq("1");
					 medicalVoPfluct.setNo_emp(medicalInfo.getUser_id());
					 medicalVoPfluct.setCd_fluct("490|");
					 medicalVoPfluct.setAm_fpay(medicalInfo.getSum_expenses()+"|");
					 medicalVoPfluct.setId_update("MISO");
					 
					 if(SystemArea.equals("REAL")) {
						 commonDAOERP.insertMedicalInfoPfluctForErp(medicalVoPfluct);
					 }
					 
					 System.out.println("medicalVoPfluct.getUser_id(); :::" + medicalInfo.getUser_id());
					 System.out.println("medicalVoPfluct.getSum_expenses(); :::" + medicalInfo.getSum_expenses());
					 System.out.println("medicalVoPfluct.getYm(); :::" + medicalVoPfluct.getYm());
					 
				 }else if(vo.getTask_cd().equals("ER")){
					 ExpenditureResolution ErVo = new ExpenditureResolution();
					 ErVo.setAppr_no(vo.getAppr_no());
					 ExpenditureResolution expenditureResolutionInfo = expenditureResolutionDao.selectErApplInfoForErp(ErVo);
					 
					 String st_stat = "1"; //'진행중 '0', 종결 '1', 반려 '-1', 미상신 '2', 취소 '3'
					 String campanyCode = "0101";
					 String cd_pc = "1000";
					 
					 //지출결의서 FI_GWDOCU
					 ErpExpenditureResolution erVoFi = new ErpExpenditureResolution();
					 erVoFi.setSt_stat_cd(st_stat);
					 erVoFi.setNm_pumm(vo.getAppr_no_fin());
					 erVoFi.setCd_company(campanyCode);
					 erVoFi.setCd_pc(cd_pc);
					 erVoFi.setNo_docu(expenditureResolutionInfo.getNo_docu());
					 erVoFi.setId_write(expenditureResolutionInfo.getId_write());
					 
					 if(SystemArea.equals("REAL")) {
						 commonDAOERP.updateErInfoFiForErp(erVoFi);
					 }
					 
					 System.out.println("erVoFi.getSt_stat_cd(); :::" + erVoFi.getSt_stat_cd());
					 System.out.println("erVoFi.getNm_pumm(); :::" + erVoFi.getNm_pumm());
					 System.out.println("erVoFi.getCd_company(); :::" + erVoFi.getCd_company());
					 System.out.println("erVoFi.getCd_pc(); :::" + erVoFi.getCd_pc());
					 System.out.println("erVoFi.getNo_docu(); :::" + erVoFi.getNo_docu());
					 System.out.println("erVoFi.getId_write(); :::" + erVoFi.getId_write());
					 
				 }
					 
				 }
				 
				 message.setMessage("정상적으로 결재 처리가 되었습니다.");
				 message.setSuccess("S");
			 }
			 else {
				 message.setMessage(result.getMessage());
				 message.setSuccess("F");
			 }
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("결재 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Code> getCommonYhCombo(Code code) throws Exception {
		// TODO Auto-generated method stub
		return commonDAO.getCommonYhCombo(code);
	}
	
	@Override
	public CommonMessage updateApprReject(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			//공통결재 호출
			 CommonMessage result = CommonApproval.approvalReject(vo, req); 
			 
			 if(result.getResult().equals("S")){
				 
				 message.setMessage("정상적으로 반송 처리가 되었습니다.");
				 message.setSuccess("S");
			 }
			 else {
				 message.setMessage(result.getMessage());
				 message.setSuccess("F");
			 }
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("반려 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public List<UserInfo> selectUserListFoName(UserInfo vo) {
		return commonDAO.selectUserListFoName(vo);
	}

	@Override
	public List<DeptInfo> selectDeptListFoName(DeptInfo vo) {
		return commonDAO.selectDeptListFoName(vo);
	}
	
	@Override
	public List<CommonAppl> selectApplicationAppl(CommonAppl commonAppl) {
		List<CommonAppl>list = commonDAO.selectApplicationAppl(commonAppl);
		return list;
	}

	@Override
	public List<CommonAppl> doPopupListView(CommonAppl commonAppl) {
		List<CommonAppl>list = commonDAO.doPopupListView(commonAppl);
		return list;
	}

	@Override
	public CommonAppl selectCommonApplInfo(CommonAppl vo) {
		CommonAppl cAppl = commonDAO.selectCommonApplInfo(vo);
		if(cAppl != null){
			FileInfo fVo = new FileInfo();
			fVo.setFile_id(cAppl.getFile_no());
			cAppl.setFileList(commonDAO.selectFileList(fVo));
		}
		
		return cAppl;
	}

	@Override
	public CommonAppl searchApplInfoMenu(CommonAppl vo) {
		CommonAppl cAppl = commonDAO.searchApplInfoMenu(vo);
		return cAppl;
	}

	@Override
	public CommonMessage saveCommonAppl(HttpServletRequest req, CommonAppl vo , Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();
		String docNo = "";
		String saveType ="";

		try {
			if(StringUtil.isNullToString(vo.getDoc_no()).equals("")) {
				docNo = StringUtil.getDocNo();
				vo.setDoc_no(docNo);
				vo.setFile_no(docNo);
				saveType = "I";
			}

			apprVo.setDoc_no("T"+vo.getDoc_no());
			CommonMessage result = CommonApproval.approvalRequest(apprVo, apprList, "S");

			if(result.getResult().equals("S")){
				vo.setAppr_no(result.getCode01());
				if(saveType.equals("I")) {
					commonDAO.insertCommonAppl(vo);
				}else {
					commonDAO.updateCommonAppl(vo);
				}

				CommonMessage fileMsg = insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());
				
				message.setMessage("정상적으로 저장 되었습니다.");
				message.setSuccess("S");
			}else {
				message.setMessage(result.getMessage());
				message.setSuccess("F");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage("저장중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage approveCommonAppl(HttpServletRequest req, CommonAppl vo , Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();
		String docNo = "";
		String saveType ="";
		
		try {
			CommonMessage result = CommonApproval.approvalRequest(apprVo, apprList, "A");

			if(result.getResult().equals("S")){
				if(StringUtil.isNullToString(vo.getDoc_no()).equals("")) {
					docNo = StringUtil.getDocNo();
					vo.setDoc_no(docNo);
					vo.setFile_no(docNo);
					saveType = "I";
				}

				vo.setAppr_no(result.getCode01());

				if(saveType.equals("I")) {
					commonDAO.insertCommonAppl(vo);
				}else {
					commonDAO.updateCommonAppl(vo);
				}

				CommonMessage fileMsg = insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());
				
				message.setMessage("정상적으로 결재 상신 되었습니다.");
				message.setSuccess("S");
			}else {
				message.setMessage(result.getMessage());
				message.setSuccess("F");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage("결재 상신 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage updateCommonAppl(HttpServletRequest req, CommonAppl vo , Approval apprVo, List<Approval> apprList) {
		CommonMessage message = new CommonMessage();
		try {

			commonDAO.updateCommonAppl(vo);

			//첨부파일
			CommonMessage fileMsg = insertFileMgmt(req, apprVo.getTask_cd(), vo.getFile_no());

			HttpSession session = req.getSession();
			UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
			String sess_user_id = sess_info.getSabun();

			UserInfo UserVo = new UserInfo();
			UserVo.setUser_id(sess_user_id);
			UserInfo userInfo = selectUserInfo(UserVo);
			apprVo.setEdit_user_id(userInfo.getSabun());
			apprVo.setUser_nm(userInfo.getName());
			apprVo.setPos_cd(userInfo.getPositionid());
			apprVo.setDept_id(userInfo.getDeptid());
			commonDAO.insertApprUpdateHistory(apprVo);

			message.setMessage("정상적으로 수정 되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("수정중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}
	
	@Override
	public CommonMessage insertApprLine(Approval vo, List<Approval> list) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			 String apprLineNo = StringUtil.getDocNo();
			 vo.setAppr_line_no(apprLineNo);
			 
			 commonDAO.insertApprLineHeader(vo);
			 
			 for(int i=0 ; i < list.size(); i++) {
				 list.get(i).setAppr_line_no(apprLineNo);
				 commonDAO.insertApprLineDetail(list.get(i));
			 }
			 
			 message.setMessage("정상적으로 저장 되었습니다.");
			 message.setSuccess("S");
			 
			
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public CommonMessage deleteApprLine(Approval vo) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			 commonDAO.deleteApprLineHeader(vo);
			 
			 commonDAO.deleteApprLineDetail(vo);
			 
			 message.setMessage("정상적으로 삭제 되었습니다.");
			 message.setSuccess("S");
			 
			
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("삭제중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public List<Approval> selectApprLine(Approval vo) {
		return commonDAO.selectApprLine(vo);
	}


	@Override
	public List<Approval> selectApprLineDetail(Approval vo) {
		return commonDAO.selectApprLineDetail(vo);
	}

	@Override
	public List<CommonAppl> selectCommonAppl(CommonAppl commonAppl) {
		List<CommonAppl>list = commonDAO.selectCommonAppl(commonAppl);
		return list;
	}

	@Override
	public List<GroupInfo> selectSubjectGroup(GroupInfo vo) {
		return commonDAO.selectSubjectGroup(vo);
	}

	@Override
	public List<GroupInfo> selectSubjectGroupDetail(GroupInfo vo) {
		return commonDAO.selectSubjectGroupDetail(vo);
	}

	@Override
	public CommonMessage insertSubjectGroup(GroupInfo vo, List<GroupInfo> list) throws Exception{
		CommonMessage message = new CommonMessage();
		
		try {
			
			String groupNo = StringUtil.getDocNo();
			vo.setGroup_hd_no(groupNo);

			commonDAO.insertSubjectGroupHeader(vo);
			for(int i=0 ; i < list.size(); i++) {
				list.get(i).setGroup_dtl_no(groupNo);
				commonDAO.insertSubjectGroupDetail(list.get(i));
			}
			message.setMessage("정상적으로 저장 되었습니다.");
			message.setSuccess("S");

		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage deleteSubjectGroup(GroupInfo vo) {
		CommonMessage message = new CommonMessage();
		
		try {
			commonDAO.deleteSubjectGroupHeader(vo);
			commonDAO.deleteSubjectGroupDetail(vo);

			message.setMessage("정상적으로 삭제 되었습니다.");
			message.setSuccess("S");
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("삭제중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Code> selectCommonCodeMgmtList(Code vo) {
		return commonDAO.selectCommonCodeMgmtList(vo);
	}


	@Override
	public CommonMessage saveCommonCodeMgmtList(List<Code> list) {
		CommonMessage msg = new CommonMessage();

		try{
			for (int i=0;i<list.size();i ++) {
				if(StringUtil.isNullToString(list.get(i).getHid_cd()).equals("") ){
					commonDAO.insertCommonCodeMgmt(list.get(i));
				}else{
					commonDAO.updateCommonCodeMgmt(list.get(i));
				}
			}
			msg.setSuccess("S");
			msg.setMessage("정상적으로 저장되었습니다.");
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage("저장에 실패하였습니다.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}


	@Override
	public CommonMessage deleteCommonCodeMgmtList(List<Code> list) {
		CommonMessage msg = new CommonMessage();

		try{
			for(Code vo : list){
				commonDAO.deleteCommonCodeMgmt(vo);
			}
			msg.setSuccess("S");
			msg.setMessage("정상적으로 삭제되었습니다.");
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage("삭제에 실패하였습니다.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}


	@Override
	public List<Approval> selectApprDetailList(Approval vo) {
		List<Approval> list = commonDAO.selectApprDetailList(vo);
		for(int i=0 ; i< list.size() ; i++) {
			if(list.get(i).getAppr_type().equals("A")){
				list.get(i).setAppr_info(list.get(i).getAppr_dt());
			}else {
				String apprProc = "미결";
				String apprView = "미개봉";
				if(!StringUtil.isNullToString(list.get(i).getAppr_dt()).equals("")) {
					apprProc = list.get(i).getAppr_dt();
				}

				if(!StringUtil.isNullToString(list.get(i).getView_dt()).equals("")) {
					apprView = "개봉 : " + list.get(i).getView_dt();
				}
				list.get(i).setAppr_info(apprProc+"("+apprView+")");
			}
			
			String apprId = StringUtil.isNullToString(list.get(i).getAppr_id());
			String viewId = StringUtil.isNullToString(list.get(i).getView_id());
			
			if(list.get(i).getAppoint_yn().equals("Y")) {
				String appointNm = "대결";
				list.get(i).setAppr_nm("("+appointNm+")"+list.get(i).getAppr_nm());
			}
			
		}
		
		return list;
	}


	@Override
	public List<Approval> selectApprovalRefDocList(Approval vo) {
		return commonDAO.selectApprovalRefDocList(vo);
	}
	
	@Override
	public TaskNote selectAdminInfo(TaskNote vo) {
		return commonDAO.selectCommonAdminInfo(vo);
	}


	@Override
	public List<Approval> selectApprRefDocList(Approval vo) {
		return commonDAO.selectApprRefDocList(vo);
	}


	@Override
	public List<Approval> selectApprRefUSerList(Approval vo) {
		return commonDAO.selectApprRefUSerList(vo);
	}


	@Override
	public List<Approval> selectApprLineInfoByTask(HttpServletRequest req, Approval vo) {
		
		Approval applinfo = commonDAO.selectApprLineTypeByTask(vo);
		
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		vo.setAppr_id(sess_user_id);
		
		List<Approval> apprList = null;
		
		String apprTypeFlag = vo.getApprTypeFlag();
		String selectApprType = vo.getSelectApprType();
		
		if(StringUtil.isNullToString(applinfo.getAppr_ord()).equals("") || StringUtil.isNullToString(applinfo.getAppr_ord()).equals("1")) {
			//결재선 조회
			
			if(apprTypeFlag.equals("SELECT")) {
				if(selectApprType != null && selectApprType != "") {
					applinfo.setAppr_type(selectApprType);
				}
			}
			if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("A")) {
				vo.setAppr_line_no("0");
				apprList = commonDAO.selectApprLineInfoByTask(vo);
			}
			else if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("B")) {
				vo.setAppr_line_no("1");
				apprList = commonDAO.selectApprLineInfoByTask(vo);
			}
			else if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("C")) {
				vo.setAppr_line_no("2");
				apprList = commonDAO.selectApprLineInfoByTask(vo);
			}
		}
		else {
			
			if(apprTypeFlag.equals("SELECT")) {
				if(selectApprType != null && selectApprType != "") {
					applinfo.setAppr_type(selectApprType);
				}
			}
			//결재선 조회 담당자 우선
			if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("A")) {
				vo.setAppr_line_no("0");
				apprList = commonDAO.selectApprLineInfoByTaskForChrg(vo);
			}
			else if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("B")) {
				vo.setAppr_line_no("1");
				apprList = commonDAO.selectApprLineInfoByTaskForChrg(vo);
			}
			else if(StringUtil.isNullToString(applinfo.getAppr_type()).equals("C")) {
				vo.setAppr_line_no("2");
				apprList = commonDAO.selectApprLineInfoByTaskForChrg(vo);
			}
		}
		
		
		return apprList;
	}
	
	@Override
	public List<Approval> selectApprCoopLineInfoByTask(HttpServletRequest req, Approval vo) {
		
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		vo.setAppr_id(sess_user_id);
		
		List<Approval> apprList = null;
		
		apprList = commonDAO.selectApprCoopLineInfoByTask(vo);
		
		
		return apprList;
	}


	@Override
	public CommonMessage updateApprLineChange(Approval vo, List<Approval> list, HttpServletRequest req) {
		
		CommonMessage message = new CommonMessage();
		
		try {
			
			//기존 결재선 삭제
			commonDAO.deleteApprLineChange(vo);
			
			for(int i=0 ; i<list.size() ; i++) {
				
				UserInfo userVo = new UserInfo();
				userVo.setUser_id(list.get(i).getAppr_id());
				UserInfo userInfo = commonDAO.selectUserInfo(userVo);
				
				list.get(i).setAppr_no(vo.getAppr_no());
				list.get(i).setAppr_nm(userInfo.getName());
				list.get(i).setAppr_dept_cd(userInfo.getDeptid());
				list.get(i).setAppr_pos(userInfo.getPositionid());
				
				if(i == 0) {
					list.get(i).setAppr_sts_cd("A");
				}
				
				String appointId = commonDAO.selectApprAppointIdInfo(list.get(i));
				if(!StringUtil.isNullToString(appointId).equals("")) {
					UserInfo appointVo = new UserInfo();
					appointVo.setUser_id(appointId);
					UserInfo appointUserInfo = commonDAO.selectUserInfo(appointVo);
					list.get(i).setAppoint_id(appointUserInfo.getSabun());
					list.get(i).setAppoint_nm(appointUserInfo.getName());
					list.get(i).setAppoint_dept_cd(appointUserInfo.getDeptid());
					list.get(i).setAppoint_pos(appointUserInfo.getPositionid());
				}
				
				commonDAO.insertApprLineChange(list.get(i));
			}
			
			message.setMessage("정상적으로 저장 되었습니다.");
			message.setSuccess("S");
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
		
		
	}


	@Override
	public CommonMessage updateApprCancel(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {

		    int viewCnt = commonDAO.selectApprViewCnt(vo);
		    if(viewCnt > 0) {
		    	message.setMessage("이미 개봉되어서 회수 할 수 없습니다.");
				message.setSuccess("V");
		    }else {
				
		    	commonDAO.updateApprCancel(vo);
		    	
		    	//결재신청문서 저장중으로 변경
				vo.setCode_type("XX008");
				String applTable = commonDAO.selectApprCommonCodeInfo(vo);
				vo.setAppl_table(applTable);
				commonDAO.updateApplInfoCancel(vo);
				
				//지출결의서 ERP 상태값 변경
				if(vo.getTask_cd().equals("ER")){
					ExpenditureResolution ErVo = new ExpenditureResolution();
					ErVo.setAppr_no(vo.getAppr_no());
					ExpenditureResolution expenditureResolutionInfo = expenditureResolutionDao.selectErApplInfoForErp(ErVo);
					 
					String st_stat = "2"; //'진행중 '0', 종결 '1', 반려 '-1', 미상신 '2', 취소 '3'
					String campanyCode = "0101";
					String cd_pc = "1000";
					 
					//지출결의서 FI_GWDOCU
					ErpExpenditureResolution erVoFi = new ErpExpenditureResolution();
					erVoFi.setSt_stat_cd(st_stat);
					erVoFi.setNm_pumm(vo.getAppr_no());
					erVoFi.setCd_company(campanyCode);
					erVoFi.setCd_pc(cd_pc);
					erVoFi.setNo_docu(expenditureResolutionInfo.getNo_docu());
					erVoFi.setId_write(expenditureResolutionInfo.getId_write());
					
					String SystemArea = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
					System.out.println("SystemArea : " +SystemArea);
					
					if(SystemArea.equals("REAL")){ 
						commonDAOERP.updateErInfoFiForErp(erVoFi);
					}
					 
					System.out.println("erVoFi.getSt_stat_cd(); :::" + erVoFi.getSt_stat_cd());
					System.out.println("erVoFi.getNm_pumm(); :::" + erVoFi.getNm_pumm());
					System.out.println("erVoFi.getCd_company(); :::" + erVoFi.getCd_company());
					System.out.println("erVoFi.getCd_pc(); :::" + erVoFi.getCd_pc());
					System.out.println("erVoFi.getNo_docu(); :::" + erVoFi.getNo_docu());
					System.out.println("erVoFi.getId_write(); :::" + erVoFi.getId_write());
				}
				
				message.setMessage("정상적으로 회수 되었습니다.");
				message.setSuccess("S");
		    }
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("회수 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}
	
	@Override
	public CommonMessage deleteApprInfo(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {

			//지출결의서 ERP 상태값 변경
			if(vo.getTask_cd().equals("ER")){
				
				ExpenditureResolution ErVo = new ExpenditureResolution();
				ErVo.setAppr_no(vo.getAppr_no());
				ExpenditureResolution expenditureResolutionInfo = expenditureResolutionDao.selectErApplInfoForErp(ErVo);
				 
				String st_stat = "3"; //'진행중 '0', 종결 '1', 반려 '-1', 미상신 '2', 취소 '3'
				String campanyCode = "0101";
				String cd_pc = "1000";
				 
				//지출결의서 FI_GWDOCU
				ErpExpenditureResolution erVoFi = new ErpExpenditureResolution();
				erVoFi.setSt_stat_cd(st_stat);
				erVoFi.setNm_pumm(vo.getAppr_no());
				erVoFi.setCd_company(campanyCode);
				erVoFi.setCd_pc(cd_pc);
				erVoFi.setNo_docu(expenditureResolutionInfo.getNo_docu());
				erVoFi.setId_write(expenditureResolutionInfo.getId_write());
				 
				String SystemArea = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
				System.out.println("SystemArea : " +SystemArea);
				
				if(SystemArea.equals("REAL")){
					commonDAOERP.updateErInfoFiForErp(erVoFi);
				}
				 
				System.out.println("erVoFi.getSt_stat_cd(); :::" + erVoFi.getSt_stat_cd());
				System.out.println("erVoFi.getNm_pumm(); :::" + erVoFi.getNm_pumm());
				System.out.println("erVoFi.getCd_company(); :::" + erVoFi.getCd_company());
				System.out.println("erVoFi.getCd_pc(); :::" + erVoFi.getCd_pc());
				System.out.println("erVoFi.getNo_docu(); :::" + erVoFi.getNo_docu());
				System.out.println("erVoFi.getId_write(); :::" + erVoFi.getId_write());
			}
			// 휴가신청서 삭제일 경우 휴가로 등록된 실적 데이터 조회하여 제거
			else if(vo.getTask_cd().equals("HD")){
				List<Work> wlist = new ArrayList<Work>();
				List<Work> templist = new ArrayList<Work>();
				Integer maxSeq = workDao.selectMaxDocSeq(vo);
				for(int j=1; j<=maxSeq; j++) {
					 vo.setDoc_seq(Integer.toString(j));
					 templist = workDao.doSelectWorkDayListByHoliday(vo);
					 wlist.addAll(templist);
				}
				String today = (new SimpleDateFormat("yyyyMMdd").format(new Date()));
				
				for(int i=0; i<wlist.size(); i++) {
					if(CurrentDateTime.diffOfDate(today, wlist.get(i).getEdate()) > -1) {
						workDao.excuteDelete(wlist.get(i));
					}
				}
				
			}
			
			commonDAO.deleteApprHeaderInfo(vo);
			commonDAO.deleteApprDetailInfo(vo);
	    	
	    	//결재신청문서 저장중으로 변경
			vo.setCode_type("XX008");
			String applTable = commonDAO.selectApprCommonCodeInfo(vo);
			vo.setAppl_table(applTable);
			commonDAO.deleteApplInfo(vo);
			
			message.setMessage("정상적으로 삭제 되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("삭제 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	//지출결의서 ERP 정보 불러오기
	@Override
	public List<Approval> doSelectApprovalLinkList(Approval vo) {
		return commonDAO.doSelectApprovalLinkList(vo);
		//List<Approval> erErp = commonDAOERP.doSelectApprovalLinkListErp(vo);
		//return erErp;
	}
	
	@Override
	public Holiday selectYearHoliInfo(Holiday vo) {
		return commonDAO.selectYearHoliInfo(vo);
	}


	@Override
	public String selectCarPurchaseDateInfo(UserInfo userInfo) {
		return commonDAO.selectCarPurchaseDateInfo(userInfo);
	}
	
	@Override
	public List<Code> getCommonComboEtc(Code code) throws Exception {
		return commonDAO.getCommonComboEtc(code);
	}


	@Override
	public CommonAppl selectTaskApplInfo(CommonAppl commonApplVo) {
		return commonDAO.selectTaskApplInfo(commonApplVo);
	}


	@Override
	public List<Approval> selectApprovalSignListForMain(Approval apprVo) {
		return commonDAO.selectApprovalSignListForMain(apprVo);
	}


	@Override
	public List<Approval> selectApprovalProgressListForMain(Approval apprVo) {
		return commonDAO.selectApprovalProgressListForMain(apprVo);
	}


	@Override
	public List<Approval> selectApprUpdateResnList(Approval approvalVo) {
		return commonDAO.selectApprUpdateResnList(approvalVo);
	}

	@Override
	public List<Approval> selectApprChargeList(Approval approvalVo) {
		return commonDAO.selectApprChargeList(approvalVo);
	}

	@Override
	public Approval selectApprChargeCnt(Approval approvalVo) {
		return commonDAO.selectApprChargeCnt(approvalVo);
	}
	
	@Override
	public CommonMessage saveAppointInfo(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			 commonDAO.saveAppointInfo(vo);
			 
			 UserInfo userVo = new UserInfo();
			 userVo.setUser_id(vo.getAppoint_id());
			 UserInfo userInfo = commonDAO.selectUserInfo(userVo);
			 
			 //현재 결재중 문서의 대결자 업데이트
			/*
			 * List<Approval> appointList = commonDAO.selectAppointList(vo);
			 * 
			 * for(int i=0 ; i<appointList.size() ; i++) {
			 * appointList.get(i).setAppoint_id(userInfo.getSabun());
			 * appointList.get(i).setAppoint_nm(userInfo.getName());
			 * appointList.get(i).setAppoint_pos(userInfo.getPositionid());
			 * appointList.get(i).setAppoint_dept_cd(userInfo.getDeptid());
			 * 
			 * commonDAO.updateApprAppointInfo(appointList.get(i)); }
			 */
			 
			 //근태업무 대결자 업데이트
			 commonDAO.updateWorkAppointInfo(vo);
			 
			 message.setMessage("정상적으로 저장 되었습니다.");
			 message.setSuccess("S");
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Approval> selectChargeChangeList(Approval approvalVo) {
		return commonDAO.selectChargeChangeList(approvalVo);
	}
	
	@Override
	public CommonMessage addApprChargePic(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {
			HttpSession session=req.getSession();
			UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");

			String[] users = vo.getAppr_id().split(",");
			for(int i = 0; i < users.length; i++){
				vo.setUser_id(users[i]);
				//협조 담당자 등록
				commonDAO.addApprChargePic(vo);

				String apprUserId = sess_info.getSabun();
				UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
				UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getUser_id());
				SendMail oMail = new SendMail();

				String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
				
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesEncode("/approvalSign.do");
				String mApprNo = URLEncoder.encode(aes256.aesEncode(vo.getAppr_no()));
				String Link ="";
				String imgUrl = "";
				
				if(StringUtil.getSystemArea().equals("REAL")) {
					Link = "https://miso.hyundai-mnsoft.com/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
					imgUrl = "https://miso.hyundai-mnsoft.com";
				}else if(StringUtil.getSystemArea().equals("TEST")){
					Link = "https://misodev.hyundai-mnsoft.com/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
					imgUrl = "https://misodev.hyundai-mnsoft.com";
				}else {
					Link = "http://127.0.0.1:8080/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
					imgUrl = "http://127.0.0.1:8080";
				}
				
				String mail_ttl = "[업무지원시스템]협조 담당자 지정되었습니다.";


				StringBuffer content= new StringBuffer();
				
				content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
				content.append("<head>");
				content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				content.append("<title>Untitled Document</title>");
				content.append("<style type='text/css'>");
				content.append("</style>");
				content.append("</head>");
				content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12px;font-family: Arial, Malgun Gothic; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
				content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("    <td width='590' height='80'><h5 style='font-size: 18px; color:#002c5f;'>협조 담당자로 지정되었습니다.</h5></td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("    <td width='590' height='180'>");
				content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 14px;font-weight: 300; border: 1px solid #002c5f;'>");
				content.append("            <tbody>");
				content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
				content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 16px;color: #ffffff;'>신청서정보</th>");
				content.append("                </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
				content.append("              </tr>");
				content.append("            </tbody>");
				content.append("    	</table>");
				content.append("    </td>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("  <td width='590' height='100'>");
				content.append("	<h5 style='font-size: 18px;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
				content.append("  </td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12px;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
				content.append("  </tr>");
				content.append("</table>");
				content.append("</body>");
				content.append("</html>");

				
				oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
			}

			
			message.setMessage("정상적으로 저장 되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage deleteApprChargePic(List<Approval> list, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
	
		try {
			for(int i=0 ; i<list.size() ; i++ ){
				commonDAO.deleteApprChargePic(list.get(i));
				message.setMessage("정상적으로 삭제 되었습니다.");
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("삭제 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage saveApprChargePic(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {
			
			commonDAO.deleteApprChargePic(vo);
			vo.setUser_id(vo.getAppr_id());
			commonDAO.addApprChargePic(vo);
			
			message.setMessage("정상적으로 변경되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("변경 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage saveChargeOpinionConfirm(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {
			commonDAO.saveChargeOpinionConfirm(vo);
			
			message.setMessage("정상적으로 저장되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("저장 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage sendChargeMail(Approval vo, HttpServletRequest req) throws Exception {
		CommonMessage message = new CommonMessage();

		try {

			HttpSession session=req.getSession();
			UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");

			String apprUserId = sess_info.getSabun();
			UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
			String reqUserId = commonDAO.selectApprManagerUser(vo);
			UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(reqUserId);

			SendMail oMail = new SendMail();

			String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
			
			String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
			AES256Cipher aes256 = new AES256Cipher(key);
			String url = aes256.aesEncode("/approvalView.do");
			String mApprNo = URLEncoder.encode(aes256.aesEncode(vo.getAppr_no()));
			String Link ="";
			String imgUrl = "";
			
			if(StringUtil.getSystemArea().equals("REAL")) {
				Link = "https://miso.hyundai-mnsoft.com/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
				imgUrl = "https://miso.hyundai-mnsoft.com";
			}else if(StringUtil.getSystemArea().equals("TEST")){
				Link = "https://misodev.hyundai-mnsoft.com/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
				imgUrl = "https://misodev.hyundai-mnsoft.com";
			}else {
				Link = "http://127.0.0.1:8080/indexApprovalMail.do?url="+url+"&appr_no="+mApprNo;
				imgUrl = "http://127.0.0.1:8080";
			}
			
			String mail_ttl = "[업무지원시스템]협조 담당자 변경 요청드립니다.";


			StringBuffer content= new StringBuffer();
			
			content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			content.append("<head>");
			content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
			content.append("<title>Untitled Document</title>");
			content.append("<style type='text/css'>");
			content.append("</style>");
			content.append("</head>");
			content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12px;font-family: Arial, Malgun Gothic; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
			content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
			content.append("  <tr>");
			content.append("  <td width='40'></td>");
			content.append("    <td width='590' height='80'><h5 style='font-size: 18px; color:#002c5f;'>협조 담당자 변경 요청드립니다.</h5></td>");
			content.append("  <td width='40'></td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("    <td width='40' height='180'>&nbsp;</td>");
			content.append("    <td width='590' height='180'>");
			content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 14px;font-weight: 300; border: 1px solid #002c5f;'>");
			content.append("            <tbody>");
			content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
			content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 16px;color: #ffffff;'>신청서정보</th>");
			content.append("                </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
			content.append("              </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+apprUserEmailInfo.getName()+" "+apprUserEmailInfo.getPositionname()+"</td>");
			content.append("              </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; '>"+apprUserEmailInfo.getDeptname()+"</td>");
			content.append("              </tr>");
			content.append("            </tbody>");
			content.append("    	</table>");
			content.append("    </td>");
			content.append("    <td width='40' height='180'>&nbsp;</td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("  <td width='40'></td>");
			content.append("  <td width='590' height='100'>");
			content.append("	<h5 style='font-size: 18px;'><a href='"+Link+"' target=\'_blank\'>품의서 바로가기</a></h5>");
			content.append("  </td>");
			content.append("  <td width='40'></td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12px;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
			content.append("  </tr>");
			content.append("</table>");
			content.append("</body>");
			content.append("</html>");

			
			oMail.sendMail(reqUserEmailInfo.getEmail(), apprUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);

			message.setMessage("정상적으로 메일발송 되었습니다.");
			message.setSuccess("S");
			
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("메일발송 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public CommonMessage deleteAppointInfo(Approval vo, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			 commonDAO.deleteAppointInfo(vo);
			 
			 UserInfo userVo = new UserInfo();
			 userVo.setUser_id(vo.getAppoint_id());
			 UserInfo userInfo = commonDAO.selectUserInfo(userVo);
			 
			 //현재 결재중인 대결자 초기화
			 List<Approval> appointList = commonDAO.selectAppointList(vo);
			 
			 for(int i=0 ; i<appointList.size() ; i++) {
				 appointList.get(i).setAppoint_id(userInfo.getSabun());
				 appointList.get(i).setAppoint_nm(userInfo.getName());
				 appointList.get(i).setAppoint_pos(userInfo.getPositionid());
				 appointList.get(i).setAppoint_dept_cd(userInfo.getDeptid());
				 
				 commonDAO.updateApprAppointInfoClear(appointList.get(i));
			 }
			 
			//근태업무 대결자 초기화
			 commonDAO.updateWorkAppointInfoClear(vo);
			 
			 message.setMessage("정상적으로 해제 되었습니다.");
			 message.setSuccess("S");
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("해제 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public Approval selectAppointInfo(Approval vo) {
		return  commonDAO.selectAppointInfo(vo);
	}
	
	@Override
	public List<Code> getCommonComboCd(Code code) throws Exception {
		return commonDAO.getCommonComboCd(code);
	}


	@Override
	public HealthCheck selectYearHealthCheckInfo(HealthCheck vo) {
		return commonDAO.selectYearHealthCheckInfo(vo);
	}


	@Override
	public String selectCheckPromotionView(ViewDateInfo vo) {
		return commonDAO.selectCheckPromotionView(vo);
	}


	@Override
	public PromotionPoint selectPromotionPointInfo(PromotionPoint vo) {
		return commonDAO.selectPromotionPointInfo(vo);
	}


	@Override
	public String selectCheckIncomeView(ViewDateInfo vo) {
		return commonDAO.selectCheckIncomeView(vo);
	}


	@Override
	public IncomeInfo selectIncomeInfo(IncomeInfo vo) {
		return commonDAO.selectIncomeInfo(vo);
	}
	
	@Override
	public IncomeInfo selectIncomeInfo2(IncomeInfo vo) {
		return commonDAO.selectIncomeInfo2(vo);
	}


	@Override
	public List<Code> selectCommonTaskList(Code vo) {
		return commonDAO.selectCommonTaskList(vo);
	}

	@Override
	public UserInfo selectUserDetail(UserInfo vo) {
		return commonDAO.selectUserDetail(vo);
	}


	@Override
	public List<ApprovalAw> selectApprovalSignListForAW(Approval vo) {
		return commonDAO.selectApprovalSignListForAW(vo);
	}
	
	@Override
	public List<ApprovalAw> selectApprovalProgressListForAW(Approval vo) {
		return commonDAO.selectApprovalProgressListForAW(vo);
	}


	@Override
	public CommonMessage updateLocaleInfo(Common vo) {
		CommonMessage message = new CommonMessage();

		 try {
			 
			 commonDAO.updateLocaleInfo(vo);
			 
			 message.setMessage("정상적으로 변경 되었습니다.");
			 message.setSuccess("S");
			 
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setMessage("변경 중 에러가 발생하였습니다.");
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public Approval selectApplApprInfo(HttpServletRequest req, Approval vo) {
		return commonDAO.selectApplApprInfo(vo);
	}


	@Override
	public String selectApprDocUrl(Approval apprVo) {
		return commonDAO.selectApprDocUrl(apprVo);
	}
	
	@Override
	public CommonMessage insertFileMgmt3(HttpServletRequest req, String foldNm, String fileId, OfficialDocument voo) {
		 CommonMessage message = new CommonMessage();
	        String[] paramVal = new String[2];
	        paramVal[0]       = "uploadFile";
	        paramVal[1]       = foldNm;
	        String resultStr  = FileUtil.uploadFile(req, paramVal);
	        //String fileId     = StringUtil.getFileNm("F");
	        message.setResult(fileId);
	        
	        HttpSession session=req.getSession();
	        UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
	        String sess_user_id = sess_info.getSabun();



	        
	        if(!"".equals(resultStr) && !"E01".equals(resultStr) && !"E02".equals(resultStr)){
	            String[] fileList = resultStr.split(";");
	            for(int i=0; i<fileList.length; i++){
	            	OfficialDocument fileVo = new OfficialDocument();
	                String[] retInfo = fileList[i].split(":");
	                /**
	                 * [0]:결과
	                 * [1]:원본파일명
	                 * [2]:변경파일명
	                 * [3]:파일사이즈
	                 */
	                if("S".equals(retInfo[0])){
	                    fileVo.setFile_id(fileId);
	                    fileVo.setOrg_file_name(retInfo[1]);
	                    fileVo.setMfy_file_name(retInfo[2]);
	                    fileVo.setFile_size(retInfo[3]);
	                    fileVo.setFile_fold(foldNm);
	                    fileVo.setEdit_user_id(sess_user_id);
	                    fileVo.setOd_cd(voo.getOd_cd());
	                    commonDAO.insertFileMgmt3(fileVo);
	                    message.setCode01(retInfo[2]);
	                }else{
	                    message.setResult("");
	                    message.setMessage(fileList[i]);
	                }
	            }
	        }else{
	            message.setResult("");
	            if("E01".equals(resultStr)){
	                message.setMessage("허용되지 않는 확장자입니다.");
	            }else if("E02".equals(resultStr)){
	                message.setMessage("파일사이즈가 초과되었습니다.");
	            }
	            else{
	                message.setMessage("파일 업로드 에러 입니다. 관리자에게 문의하세요.");
	            }
	            
	        }
	        
	        return message;
	}


	@Override
	public List<Code> selectInpoproCombo() {
		return commonDAO.selectInpoproCombo();
	}
	
	@Override
	public CommonMessage doSelectHelpDesk(UserInfo vo) {
		CommonMessage message = new CommonMessage();
		UserInfo helpDeskCount = commonDAO.doSelectHelpDesk(vo);
		System.out.println(helpDeskCount.getCount());
		System.out.println(Integer.parseInt(helpDeskCount.getCount()));
		if(Integer.parseInt(helpDeskCount.getCount()) > 0) {
			message.setResult("S");
		}else{
			message.setResult("F");
			message.setMessage("업무지원센터 대표 계정으로 로그인 하세요.");
		}
		
		return message;
	}
	
	
	@Override
	public UserInfo selectParentAuth(UserInfo vo) {
		return commonDAO.selectParentAuth(vo);
	}
	
	@Override
	public List<Approval> selectObDefaultRefUser(Approval vo) {
		return commonDAO.selectObDefaultRefUser(vo);
	}
	
	@Override
	public String selectCheckIncentiveView(ViewDateInfo vo) {
		return commonDAO.selectCheckIncentiveView(vo);
	}
	
	@Override
	public IncentiveInfo selectIncentiveInfo(IncentiveInfo vo) {
		return commonDAO.selectIncentiveInfo(vo);
	}

	@Override
	public List<YearEducation> selectYearEduInfo(YearEducation yEduVo) {
		return commonDAO.selectYearEduInfo(yEduVo);
	}
	
	@Override
	public String selectCheckInsuranceView(ViewDateInfo vo) {
		return commonDAO.selectCheckInsuranceView(vo);
	}
	
	@Override
	public InsuranceInfo selectInsuranceInfo(InsuranceInfo vo) {
		return commonDAO.selectInsuranceInfo(vo);
	}
	
	@Override
	public List<Approval> selectApprCoopLineInfoByIP(HttpServletRequest req, Approval vo) {
		
		HttpSession session = req.getSession();
		UserInfo sess_info = (UserInfo)session.getAttribute("sess_user_info");
		String sess_user_id = sess_info.getSabun();
		vo.setAppr_id(sess_user_id);
		
		List<Approval> apprList = null;
		
		apprList = commonDAO.selectApprCoopLineInfoByIP(vo);
		
		
		return apprList;
	}
	
	@Override
	public List<UserInfo> selectUserListForSabun(UserInfo vo) {
		return commonDAO.selectUserListForSabun(vo);
	}
	

}
