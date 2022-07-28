package com.mnsoft.upmu.common.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mnsoft.upmu.common.dao.CommonDAO;
import com.mnsoft.upmu.common.dao.CommonDAOERP;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.ErpExpenditureResolution;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.expenditureResolution.dao.ExpenditureResolutionDao;
import com.mnsoft.upmu.expenditureResolution.vo.ExpenditureResolution;
import com.mnsoft.upmu.system.vo.Application;

@Component
public class CommonApproval {

	private static CommonDAO commonDAO;
	private static ExpenditureResolutionDao expenditureResolutionDAO;
	private static CommonDAOERP commonDAOERP;
	
	@Autowired
	public void setCommonDAO(CommonDAO commonDAO){
		CommonApproval.commonDAO = commonDAO;
	}
	
	@Autowired
	public void setExpenditureResolutionDao(ExpenditureResolutionDao expenditureResolutionDao){
		CommonApproval.expenditureResolutionDAO = expenditureResolutionDao;
	}
	
	@Autowired
	public void setCommonDAOERP(CommonDAOERP commonDAOERP){
		CommonApproval.commonDAOERP = commonDAOERP;
	}
	
	@SuppressWarnings("deprecation")
	public static CommonMessage approvalRequest(Approval apprInfo, List<Approval> list, String type) throws Exception {
		CommonMessage resultMessage = new CommonMessage();
		
		String appointId = "";

		try {
			//결재업무정보 조회
			//apprInfo.setCode_type("XX008");
			Application taskInfo = commonDAO.selectApprTaskInfo(apprInfo);
			System.out.println("결재상신 결재타입 = "+ type);
			
			apprInfo.setAppr_ttl(taskInfo.getAc_knm());
			apprInfo.setAppr_ttl_en(taskInfo.getAc_enm());
			
			//결재문서 URL
			apprInfo.setCode_type("XX010");
			String apprDocUrl = commonDAO.selectApprCommonCodeInfo(apprInfo);
			
			String curr_year = CurrentDateTime.getDate().substring(0,4);
			apprInfo.setTask_info(curr_year+"-"+taskInfo.getAc_task_nm());
			apprInfo.setAppr_doc_url(apprDocUrl);
			
			String apprNo = "";
			
			if(type.equals("S")) {
				//2019.03.06 반송문서 수정 후 저장 시 master 문서번호 남아있는 현상 수정
				commonDAO.deleteApprMaster(apprInfo);
				commonDAO.deleteApprDetail(apprInfo);
				commonDAO.deleteApprRefDoc(apprInfo);
				commonDAO.deleteApprRefUsers(apprInfo);
				
				apprNo = apprInfo.getDoc_no();
				apprInfo.setAppr_no(apprNo);
				commonDAO.deleteApprMaster(apprInfo);
				commonDAO.deleteApprDetail(apprInfo);
				commonDAO.deleteApprRefDoc(apprInfo);
				commonDAO.deleteApprRefUsers(apprInfo);
				
				
			}else {
				
				commonDAO.deleteApprMaster(apprInfo);
				commonDAO.deleteApprDetail(apprInfo);
				commonDAO.deleteApprRefDoc(apprInfo);
				commonDAO.deleteApprRefUsers(apprInfo);
				
				apprNo = "T"+StringUtil.getDocNo();
				apprInfo.setAppr_no(apprNo);
				
			}
			
			UserInfo userVo = new UserInfo();
			userVo.setUser_id(apprInfo.getReq_id());
			UserInfo userInfoM = commonDAO.selectUserInfo(userVo);
			apprInfo.setReq_nm(userInfoM.getName());
			apprInfo.setReq_dept_cd(userInfoM.getDeptid());
			apprInfo.setReq_pos(userInfoM.getPositionid());
			apprInfo.setReq_nm_en(userInfoM.getEng_name());
			
			commonDAO.insertApprMaster(apprInfo);
			
			String apprUserId = "";
			Boolean coopFlag = false; // 2019.02.27 MCOLS-486 첫 결재자 협조부서 여부 확인
			
			for(int i=0 ; i<list.size() ; i++) {
				
				if(i == 1) {
					apprUserId = list.get(i).getAppr_id();
				}
				
				UserInfo vo = new UserInfo();
				vo.setUser_id(list.get(i).getAppr_id());
				UserInfo userInfo = commonDAO.selectUserInfo(vo);
				list.get(i).setAppr_nm(userInfo.getName());
				list.get(i).setAppr_dept_cd(userInfo.getDeptid());
				list.get(i).setAppr_pos(userInfo.getPositionid());
				list.get(i).setAppr_nm_en(userInfo.getEng_name());
				
				if(!type.equals("S")) {
					list.get(i).setSts_cd("A");
					if(i == 0) {
						list.get(i).setAppr_sts_cd("C");
					}else if(i == 1) {
						list.get(i).setAppr_sts_cd("A");
						if(list.get(i).getAppr_type().equals("D")){ //2019.02.27 MCOLS-486 첫 결재자(1번째)가 협조부서인 경우 협조부서 전체 상태코드 A로 변경
							coopFlag = true;
						}
					}
					
					//2019.02.27 MCOLS-486 첫 결재자(1번째)가 협조부서인 경우 협조부서 전체 상태코드 A로 변경
					if(coopFlag == true){
						if(list.get(i).getAppr_type().equals("D")){
							// 정보처리요청서 협조부서 변경 관련.
							if(!apprInfo.getTask_cd().equals("IP")) {
								list.get(i).setAppr_sts_cd("A");
							}
						}
					}
					
					if(!list.get(i).getAppr_type().equals("A")) {
						appointId = commonDAO.selectApprAppointIdInfo(list.get(i));
						if(!StringUtil.isNullToString(appointId).equals("")) {
							UserInfo appointVo = new UserInfo();
							appointVo.setUser_id(appointId);
							UserInfo appointUserInfo = commonDAO.selectUserInfo(appointVo);
							list.get(i).setAppoint_id(appointUserInfo.getSabun());
							list.get(i).setAppoint_nm(appointUserInfo.getName());
							list.get(i).setAppoint_dept_cd(appointUserInfo.getDeptid());
							list.get(i).setAppoint_pos(appointUserInfo.getPositionid());
							list.get(i).setAppoint_nm_en(appointUserInfo.getEng_name());
						}
					}
				}
				else {
					list.get(i).setSts_cd("S");
				}
				list.get(i).setAppr_no(apprNo);
				commonDAO.insertApprDetail(list.get(i));
			}
			
			//참조문서정보 입력
			String refApprStr  = apprInfo.getRef_appr_no();
			
			if(!StringUtil.isNullToString(refApprStr).equals("")) {
				String arrRef[] = refApprStr.split(",");
				
				for(int i=0 ; i < arrRef.length ; i++) {
					Approval refApprInfo = new Approval();
					System.out.println("arrRef[i]::"+arrRef[i]);
					String[]arrRefDtl = arrRef[i].split("@");
					
					String refApprNo = arrRefDtl[0];
					String refApprTxt = arrRefDtl[1];
					if(refApprNo.equals("N")) {
						refApprNo = "N"+StringUtil.getDocNo()+i;
					}
					
					refApprInfo.setAppr_no(apprNo);
					refApprInfo.setRef_appr_no(refApprNo);
					refApprInfo.setRef_appr_txt(refApprTxt);
					
					commonDAO.insertApprRefDoc(refApprInfo);
				}
			}
			
			//참조자 입력
			String refStr  = apprInfo.getRef_info_arr();
			System.out.println("test"+refStr);
			if(!StringUtil.isNullToString(refStr).equals("")) {
				String arrRef[] = refStr.split(",");
				
				for(int i=0 ; i < arrRef.length ; i++) {
					Approval refApprInfo = new Approval();
					
					String arrRefDtl[] =  arrRef[i].split("@");
					System.out.println("test22"+arrRefDtl.length);
					if(arrRefDtl.length > 0) {
						refApprInfo.setAppr_no(apprNo);
						refApprInfo.setRef_info(arrRefDtl[0]);
						refApprInfo.setRef_type(arrRefDtl[1]);
						commonDAO.insertApprRefUsers(refApprInfo);
					}
				}
			}
			
			if(type.equals("A")) {
				
				//메일 상신시 결재자에게 메일발송
				SendMail oMail = new SendMail();
				
				String taskNmInfo = commonDAO.selectApprTaskNameInfo(apprInfo);
				
				UserInfo apprUserEmailInfo = null;
				
				if(!StringUtil.isNullToString(appointId).equals("")) {
					apprUserEmailInfo = commonDAO.selectCommonEmailInfo(appointId);
				}else {
					apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
				}
				
				UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(apprInfo.getReq_id());
				
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesEncode("/approvalSign.do");
				String mApprNo = URLEncoder.encode(aes256.aesEncode(apprInfo.getAppr_no()));
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
				
				String mail_ttl = "[업무지원시스템]결재가 상신되었습니다.";
				
				StringBuffer content= new StringBuffer();
				
				content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
				content.append("<head>");
				content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				content.append("<title>Untitled Document</title>");
				content.append("<style type='text/css'>");
				content.append("</style>");
				content.append("</head>");
				content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
				content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 상신되었습니다.</h5></td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("    <td width='590' height='180'>");
				content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
				content.append("            <tbody>");
				content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
				content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
				content.append("                </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000;font-size: 12pt; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;font-size: 12pt; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000;font-size: 12pt; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;font-size: 12pt; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000;font-size: 12pt; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;font-size: 12pt; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
				content.append("              </tr>");
				content.append("            </tbody>");
				content.append("    	</table>");
				content.append("    </td>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("  <td width='590' height='100'>");
				content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
				content.append("  </td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
				content.append("  </tr>");
				content.append("</table>");
				content.append("</body>");
				content.append("</html>");
				
				oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
			}
			
			resultMessage.setResult("S");
			resultMessage.setCode01(apprNo);
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			System.out.println("결재 상신 중 오류 발생");
			StringUtil.printStackTrace(e);
			resultMessage.setResult("F");
			resultMessage.setMessage("결재중 오류가 발생하였습니다.");
		}
		
		System.out.println("결재값"+resultMessage.getResult());
		return resultMessage;
	}

	public static CommonMessage approvalConfirm(Approval vo, HttpServletRequest req) {
		CommonMessage resultMessage = new CommonMessage();
		
		try {
			
			HttpSession session=req.getSession();
	        UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
	        vo.setAppr_id(sess_info.getSabun());
	        
	        Approval apprVo = commonDAO.selectOrgApprIdInfo(vo);
	        
	        if(apprVo.getAppr_id().equals(vo.getAppr_id())) {
	        	
	        }else if(!apprVo.getAppr_id().equals(vo.getAppr_id()) && apprVo.getAppoint_id().equals(vo.getAppr_id())) {
	        	UserInfo userVo = new UserInfo();
		        userVo.setUser_id(vo.getAppr_id());
				UserInfo userInfo = commonDAO.selectUserInfo(userVo);
				vo.setAppoint_id(vo.getAppr_id());
				vo.setAppoint_nm(userInfo.getName());
				vo.setAppoint_dept_cd(userInfo.getDeptid());
				vo.setAppoint_pos(userInfo.getPositionid());
	        }else if(!apprVo.getAppr_id().equals(vo.getAppr_id()) && !apprVo.getAppoint_id().equals(vo.getAppr_id())) {
	        	resultMessage.setResult("E");
	        	resultMessage.setMessage("결재 권한이 없습니다.");
	        	return resultMessage;
	        }
	        
	        
			commonDAO.updateApprConfirm(vo);
			
			Approval nextAppr = commonDAO.selectApprNextInfo(vo);
			
			//결재 완료시
			if(nextAppr == null) {
				// 신청 테이블명 조회
				vo.setCode_type("XX008");
				String applTable = commonDAO.selectApprCommonCodeInfo(vo);
				vo.setAppl_table(applTable.replaceAll("'", ""));
				
				String taskInfo = "";
				String edType = "";
				if(vo.getTask_cd().equals("ED")){
					edType = commonDAO.selectEdType(vo);
					/*
					 * if(edType.equals("100")){ taskInfo = "필수"; }else if(edType.equals("200")){
					 * taskInfo = "대체"; }else if(edType.equals("300")){ taskInfo = "선택"; }else{
					 * Application applInfo = commonDAO.selectApprTaskInfo(vo); taskInfo =
					 * applInfo.getAc_task_nm(); }
					 */
					
					taskInfo = commonDAO.selectEdApprName(edType);
				}else{
					Application applInfo = commonDAO.selectApprTaskInfo(vo);
					taskInfo = applInfo.getAc_task_nm();
				}
				
				//해외출장 결재완료 시 기결재 문서 디폴트 등록
				/*
				 * if(vo.getTask_cd().equals("OB")){ System.out.println(vo.getAppr_no()); int
				 * refCnt = commonDAO.selectApprRefDocCnt(vo); System.out.println(refCnt);
				 * 
				 * if(refCnt < 1){ System.out.println(taskInfo); } }
				 */
				
				String curr_year = CurrentDateTime.getDate().substring(0,4);
				vo.setTask_info(curr_year+"-"+taskInfo);
				
				String apprNoMax = "";
				//휴가신청인 경우 시퀀스로 문서번호 세팅(ERP 중복 방지)
				if(vo.getTask_cd().equals("HD")){
					apprNoMax = commonDAO.selectHdApprNo(vo);
				}else{
					apprNoMax = commonDAO.selectApprNo(vo);
					
				}
				
				if(StringUtil.isNullToString(apprNoMax).equals("")) {
					apprNoMax = "00001";
				}
				String apprNo = vo.getTask_info()+"-"+apprNoMax;
				
				vo.setAppr_no_fin(apprNo);
				
				
				//결재문서 승인처리
				commonDAO.updateApprMasterConfirm(vo);
				resultMessage.setCode01("C");
				resultMessage.setCode02(apprNo);
				
				//결재신청문서 승인처리
				commonDAO.updateApplInfoConfirm(vo);
		
				//결재 완료시 상신자에게 메일발송
				SendMail oMail = new SendMail();
				
				String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
				
				String apprUserId = sess_info.getSabun();
				UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
				UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getReq_id());
				
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesEncode("/approvalComplate.do");
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
				
				String mail_ttl = "[업무지원시스템]결재가 완료되었습니다.";
				
				StringBuffer content= new StringBuffer();
				
				content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
				content.append("<head>");
				content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				content.append("<title>Untitled Document</title>");
				content.append("<style type='text/css'>");
				content.append("</style>");
				content.append("</head>");
				content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
				content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 완료되었습니다.</h5></td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("    <td width='590' height='180'>");
				content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
				content.append("            <tbody>");
				content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
				content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
				content.append("                </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000;'>신청서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000;'>부서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
				content.append("              </tr>");
				content.append("            </tbody>");
				content.append("    	</table>");
				content.append("    </td>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("  <td width='590' height='100'>");
				content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>완료함 바로가기</a></h5>");
				content.append("  </td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
				content.append("  </tr>");
				content.append("</table>");
				content.append("</body>");
				content.append("</html>");
				
				oMail.sendMail(reqUserEmailInfo.getEmail(), apprUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
				
			}
			else {
				// 정보처리요청서 협조부서 변경으로 인한 변경(정보처리요청서 협조부서 두명 이상 정상처리)
				if(!vo.getTask_cd().equals("IP") && (apprVo.getAppr_type().equals("B")|| apprVo.getAppr_type().equals("C")) && nextAppr.getAppr_type().equals("D")) {
					Approval apprProc = new Approval();
					apprProc.setAppr_no(vo.getAppr_no());
					apprProc.setAppr_type("D");
					apprProc.setAppr_sts_cd("A");
					commonDAO.updateApprProcessStatusCoop(apprProc);
					
					List<Approval> coopList = commonDAO.selectApprCoopList(vo);
					
					//다음이 협조부서일때 협조부서에게 메일발송
					for(int i=0 ; i<coopList.size() ; i++) {
						
						SendMail oMail = new SendMail();
						
						String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
						
						UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(coopList.get(i).getAppr_id());
						UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getReq_id());
						
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
						
						String mail_ttl = "[업무지원시스템]결재가 상신되었습니다.";
						
						StringBuffer content= new StringBuffer();
						
						content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
						content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
						content.append("<head>");
						content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
						content.append("<title>Untitled Document</title>");
						content.append("<style type='text/css'>");
						content.append("</style>");
						content.append("</head>");
						content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
						content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
						content.append("  <tr>");
						content.append("  <td width='40'></td>");
						content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 상신되었습니다.</h5></td>");
						content.append("  <td width='40'></td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("    <td width='40' height='180'>&nbsp;</td>");
						content.append("    <td width='590' height='180'>");
						content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
						content.append("            <tbody>");
						content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
						content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
						content.append("                </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px;border-bottom: 1px solid #000; font-family: Malgun Gothic, Arial; '>"+taskNmInfo+"</td>");
						content.append("              </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; background: #d6d6d6; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000;'>이름</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
						content.append("              </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
						content.append("              </tr>");
						content.append("            </tbody>");
						content.append("    	</table>");
						content.append("    </td>");
						content.append("    <td width='40' height='180'>&nbsp;</td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("  <td width='40'></td>");
						content.append("  <td width='590' height='100'>");
						content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
						content.append("  </td>");
						content.append("  <td width='40'></td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
						content.append("  </tr>");
						content.append("</table>");
						content.append("</body>");
						content.append("</html>");
						
						oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
						
					}
					
				// 정보처리요청서 협조부서 변경으로 인한 변경(정보처리요청서 협조부서 두명 이상 정상처리)
				}else if(!vo.getTask_cd().equals("IP") && apprVo.getAppr_type().equals("D")){
					
					int ApprCnt = commonDAO.selectApprCoopCnt(vo);
					if(ApprCnt == 0) {
						
						Approval nextCoopAppr = commonDAO.selectApprCoopNextInfo(vo);
						
						Approval apprProc = new Approval();
						apprProc.setAppr_no(vo.getAppr_no());
						apprProc.setAppr_seq(nextCoopAppr.getAppr_seq());
						commonDAO.updateApprProcessStatus(apprProc);
						
						//협조부서 결재가 모두 완료시 다음 결재자에게 메일발송
						
						SendMail oMail = new SendMail();
						
						String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
						
						UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(nextAppr.getAppr_id());
						UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getReq_id());
						
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
						
						String mail_ttl = "[업무지원시스템]결재가 상신되었습니다.";
						
						StringBuffer content= new StringBuffer();
						
						content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
						content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
						content.append("<head>");
						content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
						content.append("<title>Untitled Document</title>");
						content.append("<style type='text/css'>");
						content.append("</style>");
						content.append("</head>");
						content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
						content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
						content.append("  <tr>");
						content.append("  <td width='40'></td>");
						content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 상신되었습니다.</h5></td>");
						content.append("  <td width='40'></td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("    <td width='40' height='180'>&nbsp;</td>");
						content.append("    <td width='590' height='180'>");
						content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial;font-weight: 300; border: 1px solid #002c5f;'>");
						content.append("            <tbody>");
						content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
						content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
						content.append("                </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
						content.append("              </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
						content.append("              </tr>");
						content.append("              <tr>");
						content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
						content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
						content.append("              </tr>");
						content.append("            </tbody>");
						content.append("    	</table>");
						content.append("    </td>");
						content.append("    <td width='40' height='180'>&nbsp;</td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("  <td width='40'></td>");
						content.append("  <td width='590' height='100'>");
						content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
						content.append("  </td>");
						content.append("  <td width='40'></td>");
						content.append("  </tr>");
						content.append("  <tr>");
						content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
						content.append("  </tr>");
						content.append("</table>");
						content.append("</body>");
						content.append("</html>");
						
						oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
					}
					
				}else {
					Approval apprProc = new Approval();
					apprProc.setAppr_no(vo.getAppr_no());
					apprProc.setAppr_seq(nextAppr.getAppr_seq());
					commonDAO.updateApprProcessStatus(apprProc);
					
					//다음 결재자에게 메일발송
					SendMail oMail = new SendMail();
					
					String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
					
					UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(nextAppr.getAppr_id());
					UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getReq_id());
					
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
					
					String mail_ttl = "[업무지원시스템]결재가 상신되었습니다.";
					
					StringBuffer content= new StringBuffer();
					
					content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
					content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
					content.append("<head>");
					content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
					content.append("<title>Untitled Document</title>");
					content.append("<style type='text/css'>");
					content.append("</style>");
					content.append("</head>");
					content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
					content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
					content.append("  <tr>");
					content.append("  <td width='40'></td>");
					content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 상신되었습니다.</h5></td>");
					content.append("  <td width='40'></td>");
					content.append("  </tr>");
					content.append("  <tr>");
					content.append("    <td width='40' height='180'>&nbsp;</td>");
					content.append("    <td width='590' height='180'>");
					content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
					content.append("            <tbody>");
					content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
					content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
					content.append("                </tr>");
					content.append("              <tr>");
					content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
					content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
					content.append("              </tr>");
					content.append("              <tr>");
					content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
					content.append("                    <td height='40' valign='middle' style='width:80%; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
					content.append("              </tr>");
					content.append("              <tr>");
					content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
					content.append("                    <td height='40' valign='middle' style='width:80%; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
					content.append("              </tr>");
					content.append("            </tbody>");
					content.append("    	</table>");
					content.append("    </td>");
					content.append("    <td width='40' height='180'>&nbsp;</td>");
					content.append("  </tr>");
					content.append("  <tr>");
					content.append("  <td width='40'></td>");
					content.append("  <td width='590' height='100'>");
					content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
					content.append("  </td>");
					content.append("  <td width='40'></td>");
					content.append("  </tr>");
					content.append("  <tr>");
					content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
					content.append("  </tr>");
					content.append("</table>");
					content.append("</body>");
					content.append("</html>");
					
					oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
					
				}
				resultMessage.setCode01("A");
				
			}
			
			resultMessage.setResult("S");
		}catch (Exception e) {
			StringUtil.printStackTrace(e);
			resultMessage.setResult("F");
			resultMessage.setMessage("결재처리 중 오류가 발생하였습니다.");
		}
		
		
		return resultMessage;
	}
	
	public static CommonMessage approvalReject(Approval vo, HttpServletRequest req) {
		CommonMessage resultMessage = new CommonMessage();
		
		try {
			
			HttpSession session=req.getSession();
	        UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
	        vo.setAppr_id(sess_info.getSabun());
	        
	        commonDAO.updateApprMasterReject(vo);
	        
			commonDAO.updateApprReject(vo);
			
			//결재신청문서 승인처리
			vo.setCode_type("XX008");
			String applTable = commonDAO.selectApprCommonCodeInfo(vo);
			vo.setAppl_table(applTable);
			
			commonDAO.updateApplInfoReject(vo);
			
			//결재 완료시 상신자에게 메일발송
			SendMail oMail = new SendMail();
			
			String taskNmInfo = commonDAO.selectApprTaskNameInfo(vo);
			
			String apprUserId = sess_info.getSabun();
			UserInfo apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
			UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(vo.getReq_id());
			
			String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
			AES256Cipher aes256 = new AES256Cipher(key);
			String url = aes256.aesEncode("/approvalReject.do");
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
			
			String mail_ttl = "[업무지원시스템]결재가 반송되었습니다.";
			
			StringBuffer content= new StringBuffer();
			
			content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			content.append("<head>");
			content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
			content.append("<title>Untitled Document</title>");
			content.append("<style type='text/css'>");
			content.append("</style>");
			content.append("</head>");
			content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
			content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
			content.append("  <tr>");
			content.append("  <td width='40'></td>");
			content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 반송되었습니다.</h5></td>");
			content.append("  <td width='40'></td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("    <td width='40' height='180'>&nbsp;</td>");
			content.append("    <td width='590' height='180'>");
			content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
			content.append("            <tbody>");
			content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
			content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
			content.append("                </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
			content.append("              </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
			content.append("              </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
			content.append("              </tr>");
			content.append("              <tr>");
			content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>반송사유</th>");
			content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+StringUtil.getHtmlDecode(vo.getReject_resn())+"</td>");
			content.append("              </tr>");
			content.append("            </tbody>");
			content.append("    	</table>");
			content.append("    </td>");
			content.append("    <td width='40' height='180'>&nbsp;</td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("  <td width='40'></td>");
			content.append("  <td width='590' height='100'>");
			content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>반송함 바로가기</a></h5>");
			content.append("  </td>");
			content.append("  <td width='40'></td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
			content.append("  </tr>");
			content.append("</table>");
			content.append("</body>");
			content.append("</html>");
			
			oMail.sendMail(reqUserEmailInfo.getEmail(), apprUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
			
			
			
			//결재완료자에게 메일발송
			
			List<Approval> apprConfirmList = commonDAO.selectApprConformUserList(vo);
			
			for(int i=0 ; i < apprConfirmList.size() ; i++) {
				UserInfo apprUserEmailInfo1 = commonDAO.selectCommonEmailInfo(apprUserId);
				UserInfo reqUserEmailInfo1 = commonDAO.selectCommonEmailInfo(apprConfirmList.get(i).getAppr_id());
				
				String mail_ttl1 = "[업무지원시스템]결재가 반송되었습니다.";
				
				StringBuffer content1= new StringBuffer();
				
				content1.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				content1.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
				content1.append("<head>");
				content1.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				content1.append("<title>Untitled Document</title>");
				content1.append("<style type='text/css'>");
				content1.append("</style>");
				content1.append("</head>");
				content1.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
				content1.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
				content1.append("  <tr>");
				content1.append("  <td width='40'></td>");
				content1.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 반송되었습니다.</h5></td>");
				content1.append("  <td width='40'></td>");
				content1.append("  </tr>");
				content1.append("  <tr>");
				content1.append("    <td width='40' height='180'>&nbsp;</td>");
				content1.append("    <td width='590' height='180'>");
				content1.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
				content1.append("            <tbody>");
				content1.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
				content1.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
				content1.append("                </tr>");
				content1.append("              <tr>");
				content1.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
				content1.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
				content1.append("              </tr>");
				content1.append("              <tr>");
				content1.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
				content1.append("                    <td height='40' valign='middle' style='width:80%; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
				content1.append("              </tr>");
				content1.append("              <tr>");
				content1.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial;  background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
				content1.append("                    <td height='40' valign='middle' style='width:80%; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
				content1.append("              </tr>");
				content1.append("            </tbody>");
				content1.append("    	</table>");
				content1.append("    </td>");
				content1.append("    <td width='40' height='180'>&nbsp;</td>");
				content1.append("  </tr>");
				content1.append("  <tr>");
				content1.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
				content1.append("  </tr>");
				content1.append("</table>");
				content1.append("</body>");
				content1.append("</html>");
				
				oMail.sendMail(reqUserEmailInfo1.getEmail(), apprUserEmailInfo1.getEmail(), mail_ttl, content1.toString(), 1, false);
			}
			
			if(vo.getTask_cd().equals("ER")){
				
				ExpenditureResolution ErVo = new ExpenditureResolution();
				ErVo.setAppr_no(vo.getAppr_no());
				ExpenditureResolution expenditureResolutionInfo = expenditureResolutionDAO.selectErApplInfoForErp(ErVo);
				 
				String st_stat = "-1"; //'진행중 '0', 종결 '1', 반려 '-1', 미상신 '2', 취소 '3'
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
			
			
			resultMessage.setResult("S");
		}catch (Exception e) {
			System.out.println("반려 진행 중 오류 발생");
			StringUtil.printStackTrace(e);
			resultMessage.setResult("F");
			resultMessage.setMessage("결재처리 중 오류가 발생하였습니다.");
		}
		
		
		return resultMessage;
	}

	public static CommonMessage approvalRefUpdate(Approval apprInfo) {
		CommonMessage resultMessage = new CommonMessage();
		
		try {
			
			commonDAO.deleteApprRefDoc(apprInfo);
			commonDAO.deleteApprRefUsers(apprInfo);
				
			//참조문서정보 입력
			String refApprStr  = apprInfo.getRef_appr_no();
			
			if(!StringUtil.isNullToString(refApprStr).equals("")) {
				String arrRef[] = refApprStr.split(",");
				
				for(int i=0 ; i < arrRef.length ; i++) {
					Approval refApprInfo = new Approval();
					System.out.println("arrRef[i]::"+arrRef[i]);
					String[]arrRefDtl = arrRef[i].split("@");
					
					String refApprNo = arrRefDtl[0];
					String refApprTxt = arrRefDtl[1];
					if(refApprNo.equals("N")) {
						refApprNo = "N"+StringUtil.getDocNo()+i;
					}
					
					refApprInfo.setAppr_no(apprInfo.getAppr_no());
					refApprInfo.setRef_appr_no(refApprNo);
					refApprInfo.setRef_appr_txt(refApprTxt);
					
					commonDAO.insertApprRefDoc(refApprInfo);
				}
			}
			
			//참조자 입력
			String refStr  = apprInfo.getRef_info_arr();
			if(!StringUtil.isNullToString(refStr).equals("")) {
				String arrRef[] = refStr.split(",");
				
				for(int i=0 ; i < arrRef.length ; i++) {
					Approval refApprInfo = new Approval();
					
					String arrRefDtl[] =  arrRef[i].split("@");
					
					refApprInfo.setAppr_no(apprInfo.getAppr_no());
					refApprInfo.setRef_info(arrRefDtl[0]);
					refApprInfo.setRef_type(arrRefDtl[1]);
					commonDAO.insertApprRefUsers(refApprInfo);
				}
			}
			
			resultMessage.setResult("S");
		}catch (Exception e) {
			StringUtil.printStackTrace(e);
			resultMessage.setResult("F");
			resultMessage.setMessage("수정중  오류가 발생하였습니다.");
		}
		
		return resultMessage;
		
	}

	//해외출장 결제완료시 법인카드처리요청 , 정보처리 상신 올리기 
	@SuppressWarnings("deprecation")
	public static CommonMessage approvalOBRequest(Approval apprInfo, List<Approval> list, String type) throws Exception {
		CommonMessage resultMessage = new CommonMessage();
		
		String appointId = "";
		String etcApprNo = "";

		try {
			//기결재문서 , 참조자 가져오는 ID ,File 정보 
			etcApprNo = apprInfo.getAppr_no();
			//결재업무정보 조회
			Application taskInfo = commonDAO.selectApprTaskInfo(apprInfo);
			
			apprInfo.setAppr_ttl(taskInfo.getAc_knm());
			apprInfo.setAppr_ttl_en(taskInfo.getAc_enm());
			
			//결재문서 URL
			apprInfo.setCode_type("XX010");
			String apprDocUrl = commonDAO.selectApprCommonCodeInfo(apprInfo);
			
			String curr_year = CurrentDateTime.getDate().substring(0,4);
			apprInfo.setTask_info(curr_year+"-"+taskInfo.getAc_task_nm());
			apprInfo.setAppr_doc_url(apprDocUrl);
			
			String apprNo = "";

			apprNo = "T"+StringUtil.getDocNo();
			apprInfo.setAppr_no(apprNo);

			UserInfo userVo = new UserInfo();
			userVo.setUser_id(apprInfo.getReq_id());
			UserInfo userInfoM = commonDAO.selectUserInfo(userVo);
			apprInfo.setReq_nm(userInfoM.getName());
			apprInfo.setReq_dept_cd(userInfoM.getDeptid());
			apprInfo.setReq_pos(userInfoM.getPositionid());
			apprInfo.setReq_nm_en(userInfoM.getEng_name());
			
			commonDAO.insertApprMaster(apprInfo);
			
			String apprUserId = "";
			int cSize =0;
			for(int i=0 ; i<list.size() ; i++) {
				if(list.get(i).getAppr_type().equals("C")) {
					//담당에게 메일 발송  
					if(cSize == 0) {
						apprUserId = list.get(i).getAppr_id();
					}

					appointId = commonDAO.selectApprAppointIdInfo(list.get(i));
					if(!StringUtil.isNullToString(appointId).equals("")) {
						UserInfo appointVo = new UserInfo();
						appointVo.setUser_id(appointId);
						UserInfo appointUserInfo = commonDAO.selectUserInfo(appointVo);
						list.get(i).setAppoint_id(appointUserInfo.getSabun());
						list.get(i).setAppoint_nm(appointUserInfo.getName());
						list.get(i).setAppoint_dept_cd(appointUserInfo.getDeptid());
						list.get(i).setAppoint_pos(appointUserInfo.getPositionid());
						list.get(i).setAppoint_nm_en(appointUserInfo.getEng_name());
					}
				}

				list.get(i).setAppr_no(apprNo);
				commonDAO.insertApprDetailOb(list.get(i));
			}
			//기존 해외출장 문서에서 기결문서 또는 참조가 정보 가져오기 
			Approval apprNewVo = new Approval();
			apprNewVo.setAppr_no(etcApprNo);
			List<Approval> applRefDocList = commonDAO.selectApprRefDocAppr(apprNewVo);
			//참조문서정보 입력
			for(int i=0 ; i < applRefDocList.size() ; i++) {
				applRefDocList.get(i).setAppr_no(apprNo);
				commonDAO.insertApprRefDoc(applRefDocList.get(i));
			}
		
			//참조자 입력
			List<Approval> applRefUsersList = commonDAO.selectApprRefUsersAppr(apprNewVo);
			for(int i=0 ; i < applRefUsersList.size() ; i++) {
				applRefUsersList.get(i).setAppr_no(apprNo);
				commonDAO.insertApprRefUsers(applRefUsersList.get(i));
			}
			
			if(type.equals("A")) {
				
				//메일 상신시 결재자에게 메일발송
				SendMail oMail = new SendMail();
				
				String taskNmInfo = commonDAO.selectApprTaskNameInfo(apprInfo);
				
				UserInfo apprUserEmailInfo = null;
				
				if(!StringUtil.isNullToString(appointId).equals("")) {
					apprUserEmailInfo = commonDAO.selectCommonEmailInfo(appointId);
				}else {
					apprUserEmailInfo = commonDAO.selectCommonEmailInfo(apprUserId);
				}
				
				UserInfo reqUserEmailInfo = commonDAO.selectCommonEmailInfo(apprInfo.getReq_id());
				
				String key = StringUtil.getPropinfo().getProperty("ASE_KEY");
				AES256Cipher aes256 = new AES256Cipher(key);
				String url = aes256.aesEncode("/approvalSign.do");
				String mApprNo = URLEncoder.encode(aes256.aesEncode(apprInfo.getAppr_no()));
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
				
				String mail_ttl = "[업무지원시스템]결재가 상신되었습니다.";
				
				StringBuffer content= new StringBuffer();
				
				content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
				content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
				content.append("<head>");
				content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
				content.append("<title>Untitled Document</title>");
				content.append("<style type='text/css'>");
				content.append("</style>");
				content.append("</head>");
				content.append("<body style='margin:0px;padding:0px;width:100%;box-sizing: border-box;font-size:12pt;font-family: Malgun Gothic, Arial; font-smoothing:antialiased; -webkit-text-size-adjust:none;-webkit-font-smoothing:antialiased !important;'>");
				content.append("<table width='670' border='0' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto'>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("    <td width='590' height='80'><h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial; color:#002c5f;'>결재가 상신되었습니다.</h5></td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("    <td width='590' height='180'>");
				content.append("    	<table cellspacing='0' cellpadding='0' width='100%' style='padding: 0;border-spacing:0;text-align:left;font-size: 12pt; font-family: Malgun Gothic, Arial; font-weight: 300; border: 1px solid #002c5f;'>");
				content.append("            <tbody>");
				content.append("                <tr cellspacing='0' cellpadding='0' style='background: #002c5f; text-align: center;font-weight: normal;'>");
				content.append("                    <th cellspacing='0' cellpadding='0' height='40' colspan='2' align='center' valign='middle' style='width: 100%;border-right: 1px solid #000;font-weight: bold;font-size: 12pt; font-family: Malgun Gothic, Arial; color: #ffffff;'>신청서정보</th>");
				content.append("                </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>신청서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+taskNmInfo+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>이름</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%; font-family: Malgun Gothic, Arial; padding-left: 6px;border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getName()+" "+reqUserEmailInfo.getPositionname()+"</td>");
				content.append("              </tr>");
				content.append("              <tr>");
				content.append("                    <th height='40' align='center' valign='middle' style='width:20%;border-right: 1px solid #000; font-family: Malgun Gothic, Arial; background: #d6d6d6;border-bottom: 1px solid #000;'>부서</th>");
				content.append("                    <td height='40' valign='middle' style='width:80%;padding-left: 6px; font-family: Malgun Gothic, Arial; border-bottom: 1px solid #000; '>"+reqUserEmailInfo.getDeptname()+"</td>");
				content.append("              </tr>");
				content.append("            </tbody>");
				content.append("    	</table>");
				content.append("    </td>");
				content.append("    <td width='40' height='180'>&nbsp;</td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='40'></td>");
				content.append("  <td width='590' height='100'>");
				content.append("	<h5 style='font-size: 12pt; font-family: Malgun Gothic, Arial;'><a href='"+Link+"' target=\'_blank\'>결재함 바로가기</a></h5>");
				content.append("  </td>");
				content.append("  <td width='40'></td>");
				content.append("  </tr>");
				content.append("  <tr>");
				content.append("  <td width='670' height='30' colspan='3' align='center' valign='middle' bgcolor='#666666' style='color:#fff;font-size:12pt; font-family: Malgun Gothic, Arial;'>Copyright © 2021 HNCIS. All Rights Reserved.</td>");
				content.append("  </tr>");
				content.append("</table>");
				content.append("</body>");
				content.append("</html>");
				
				oMail.sendMail(apprUserEmailInfo.getEmail(), reqUserEmailInfo.getEmail(), mail_ttl, content.toString(), 1, false);
			}
			
			resultMessage.setResult("S");
			resultMessage.setCode01(apprNo);
		}catch (Exception e) {
			System.out.println("해외출장 연계신청서 오류발생");
			StringUtil.printStackTrace(e);
			resultMessage.setResult("F");
			resultMessage.setMessage("결재중 오류가 발생하였습니다.");
		}
		
		
		return resultMessage;
	}
}
