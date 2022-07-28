package com.mnsoft.upmu.bizcard.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.mnsoft.upmu.bizcard.dao.BizCardDao;
import com.mnsoft.upmu.bizcard.service.BizCardService;
import com.mnsoft.upmu.bizcard.vo.BizCard;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.SendMail;
import com.mnsoft.upmu.common.util.StringUtil;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.system.dao.SystemDao;
import com.mnsoft.upmu.system.vo.Code;


@Service("bizCardService")
public class BizCardServiceImpl implements BizCardService {

	@Autowired
	BizCardDao bizCardDao;
	
	@Autowired
	SystemDao systemDao;
	
	@Autowired 
	MessageSource messageSource;	
	
	@Override
	public CommonMessage saveBizCard(HttpServletRequest req, List<BizCard> list) throws Exception {

	 CommonMessage message = new CommonMessage();

	try {		
		 bizCardDao.saveBizCard(list.get(0));			
		 message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
		 message.setSuccess("S");		
	}catch(Exception e) {
		StringUtil.printStackTrace(e);
		message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
		message.setSuccess("F");
	}
	 	return message;
	}

	@Override
	public List<UserInfo> userInfo(UserInfo userInfo) throws Exception {
		List<UserInfo>userInfoList = bizCardDao.userInfo(userInfo);
		return userInfoList;
	}

	@Override
	public List<BizCard> selectBizCardList(BizCard bizCard) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("----getGubungetGubungetGubungetGubungetGubun------------"+bizCard.getGubun());
		List<BizCard>bizCardInfoList = bizCardDao.selectBizCardList(bizCard);
		return bizCardInfoList;
	}

	@Override
	public List<BizCard> bizCardInfo(BizCard bizCard) throws Exception {
		// TODO Auto-generated method stub
		List<BizCard> bizCardInfo = bizCardDao.bizCardInfo(bizCard);
		return bizCardInfo;
	}
	
	@Override
	public CommonMessage saveCdEmail(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				System.out.println("----------------"+list.get(i).getUp_in());
				
				bizCardDao.updateCdEmail(list.get(i));
				
				message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}

	@Override
	public CommonMessage saveAddr(HttpServletRequest req, List<Code> list) throws Exception {
		// TODO Auto-generated method stub
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				System.out.println("----------------"+list.get(i).getUp_in());
				
				bizCardDao.saveAddr(list.get(i));
				
				message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}

	@Override
	public CommonMessage saveCnt(HttpServletRequest req, List<Code> list) throws Exception {
		// TODO Auto-generated method stub
				CommonMessage message = new CommonMessage();

				 try {
					for(int i=0 ; i<list.size() ; i++ ){
						System.out.println("----------------"+list.get(i).getUp_in());
						
						bizCardDao.saveCnt(list.get(i));
						
						message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
						message.setSuccess("S");
					}
				}catch(Exception e) {
					StringUtil.printStackTrace(e);
					message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
					message.setSuccess("F");
				}
				 	return message;
	}
	
	//미입력현황 메일 발송
	@Override
	public CommonMessage sendMail(List<BizCard> list, HttpServletRequest req) throws Exception {
		 CommonMessage message = new CommonMessage();
		 SendMail oMail = new SendMail();
		 BizCard mailList = new BizCard();
		 
		 try {
			 HttpSession session = req.getSession();
		     UserInfo sess_info         = (UserInfo)session.getAttribute("sess_user_info");
		     String sess_user_id = sess_info.getSabun();
		     System.out.println("로그인 계정 : "+ sess_user_id);
		     list.get(0).setFrom_id(sess_user_id);
		     BizCard fromEmail = bizCardDao.selectFromMailB(list.get(0));
		     
		     List<BizCard> maillist = bizCardDao.selectToMailB();
		     System.out.println("명함내용" + list.get(0).getContent());
		     StringBuffer content= new StringBuffer();

		     content.append("<!DOCTYPE html>																			");		
		     content.append(" <html>																					");		
		     content.append(" <head>																					");		
		     content.append(" <meta charset=\"UTF-8\">																");	
		     content.append(" <title></title>																			");	
		     content.append(" </head>																					");		  																							
		     content.append(" <body bgcolor='#ffffff' align='center'  >											");	
		     content.append(" <span><font size='4' face='Malgun Gothic'>안녕하십니까? 현대엠엔소프트 명함신청 내용입니다.</font></span>");									
		     
		     content.append(" 	<table align='center' cellspacing='0' cellpadding='0' style='border:1px solid #999; margin:0 auto' width='1100' frame='box'  >								");	
		    
		  /*   content.append(" 		<tr  align='left'>																");	
		     content.append("				<td colspan=5><font size='4' face='Malgun Gothic'>안녕하십니까? 현대엠엔소프트 명함신청 내용입니다.</font>");	
		     content.append(" 			</td>																			");	
		     content.append(" 		</tr>																				");	*/
		     content.append(" 		<tr align='center'>																	");	
		     content.append("			<th colspan=5 style='border-bottom: 1px solid #000;'>																			");	
		     content.append(" 				<h3>&#91;<font face='Malgun Gothic'>명함 정보</font>&#93;</h3>					");	
		     content.append(" 			</th>																			");	
		     content.append(" 		</tr>																				");	
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th bgcolor='#F5F5F5' style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;'>회사</th>                                                                                           ");
		     content.append(" 			<th bgcolor='#F5F5F5' style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;'>한글</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'><input  id='' type='text' readonly value='"+list.get(0).getCompany()+"'></td>                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                         ");
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input  id='' type='text' readonly value='"+list.get(0).getEng_company()+"'></td>                                           ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>성명</th>                                                                                           ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>한글</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;' ><input  id='name' type='text' readonly value='"+list.get(0).getName()+"'></td>                                                              ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input  id='engName' type='text' readonly value='"+list.get(0).getEng_name()+"'></td>                                                           ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%;border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>부서</th>                                                                                           ");
		     content.append(" 			<th style='width:10%;border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>한글</th>                                                                                           ");
		     content.append(" 			<td style='width:35%;border-right: 1px solid #999;border-bottom: 1px solid #999;'><input  id='deptname' type='text' readonly value='"+list.get(0).getDeptname()+"'></td>                                                          ");
		     content.append(" 			<th style='width:10%;border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                           ");
		     content.append(" 			<td style='width:35%;border-bottom: 1px solid #999;'><input  id='engDeptname' type='text' readonly value='"+list.get(0).getEng_deptname()+"'></td>                                                       ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>직위</th>                                                                                           ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>한글</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'><input id='positionname' type='text' readonly value='"+list.get(0).getPositionname()+"'></td>                                                       ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input id='engEmpGradeNm' type='text' readonly value='"+list.get(0).getEng_emp_grade_nm()+"'></td>                                                      ");
		     content.append(" 		</tr>                                                                                                                         ");
			 if(list.get(0).getDutyname().length() > 1) {
			     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>직책</th>                                                                                           ");
			     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>한글</th>                                                                                           ");
			     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'><input id='dutyname' type='text' readonly value='"+list.get(0).getDutyname()+"'></td>                                                       ");
			     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                           ");
			     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input id='eng_duty_nm' type='text' readonly value='"+list.get(0).getEng_duty_nm()+"'></td>                                                      ");
			     content.append(" 		</tr>                                                                                                                         ");
		     }
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>주소</th>                                                                                           ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>한글</th>                                                                                           ");
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'>                                                                                                                      ");
		     content.append(" 				<input id='address' type='text' readonly value='"+list.get(0).getAddress()+"'></td>                                                            ");
		     content.append(" 			</td>                                                                                                                     ");
		     content.append(" 							                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>영문</th>                                                                                                             ");
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'>                                                                                                                     ");
		     content.append(" 				<input id='engAddress' type='text' readonly value='"+list.get(0).getEng_address()+"'></td>                                                         ");
		     content.append(" 			</td>                                                                                                                     ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>연락처1</th>                                                                                        ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>전화</th>                                            ");
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'><input  id='localPhoneNo' type='text' readonly value='"+list.get(0).getLocalphoneno()+"'></td>                                                      ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' style='background: rgb(245, 251, 255); color: rgb(0, 170, 210);'>팩스</th>                                            ");
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input  id='officeFaxNo' type='text' readonly value='"+list.get(0).getOffice_fax_no()+"'></td>                                                       ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>연락처2</th>                                                                                        ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>핸드폰<span style='color: red;'></span></th>        ");      
		     content.append(" 			<td style='width:35%; border-right: 1px solid #999;border-bottom: 1px solid #999;'><input  id='mobile' type='text' value='"+list.get(0).getMobile()+"'></td>                                                                        ");
		     content.append(" 			<th style='width:10%; border-right: 1px solid #999;border-bottom: 1px solid #999;' bgcolor='#F5F5F5'>e-mail<span style='color: red;'></span></th>        ");      
		     content.append(" 			<td style='width:35%; border-bottom: 1px solid #999;'><input  id='email' type='text' value='"+list.get(0).getEmail()+"'></td>                                                                         ");
		     content.append(" 		</tr>                                                                                                                         ");
		     content.append(" 		<tr>                                                                                                                          ");
		     content.append(" 			<th style='width:10%;border-right: 1px solid #999;' bgcolor='#F5F5F5'>신청</th>                                                                                           ");
		     content.append(" 			<th style='width:10%;border-right: 1px solid #999;' bgcolor='#F5F5F5'>신청통수<span style='color: red;'></span></th>      ");        
		     content.append(" 			<td style='width:35%;border-right: 1px solid #999;'>                                                                                                                      ");
		     content.append(" 				<input id='offerCnt' type='text' readonly value='"+list.get(0).getOffer_cnt()+"'></td>                                                           ");
		     content.append(" 			</td>                                                                                                                     ");	
		     content.append(" 			<td style='width:10%;border-right: 1px solid #999;'></td>                                                                                                                     ");	
		     content.append(" 			<td></td>                                                                                                                     ");	
		     content.append(" 		</tr>                                                                                                                         ");	
		     content.append(" 	</table>																			                                              ");
		     content.append(" </body>																					                                              ");
		     content.append("</html>																					                                              ");
		       
		 	
			for(int i=0 ; i<maillist.size(); i++){
				
				
				
				System.out.println("보내는 이 : " + fromEmail.getFrom_mail());
				System.out.println("받는 이 : " + maillist.get(i).getTo_mail());
				//메일 전송
				oMail.sendMail(maillist.get(i).getTo_mail(), fromEmail.getFrom_mail(), list.get(0).getMtitle(), content.toString(), 1, false);
			}
			message.setSuccess("S");
			message.setMessage(messageSource.getMessage("msg.trans.email01", null, StringUtil.getLocalLang(req))); //정상적으로 전송 되었습니다.
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.trans.email02", null, StringUtil.getLocalLang(req))); //전송중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}


	@Override
	public List<BizCard> bizCardInfoToday(BizCard bizCard) throws Exception {
		// TODO Auto-generated method stub
		List<BizCard> bizCardInfo = bizCardDao.bizCardInfoToday(bizCard);
		return bizCardInfo;
	}
	
}
