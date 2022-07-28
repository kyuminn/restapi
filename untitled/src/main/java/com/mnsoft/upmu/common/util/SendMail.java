package com.mnsoft.upmu.common.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

//import org.apache.log4j.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mnsoft.upmu.system.vo.FileInfo;

//import com.hbms.common.Constant;

public class SendMail {
    Logger logger = LoggerFactory.getLogger("SendMail.class");

    private String _HOST = "";
    
    //개발 : false, 운영 : true
    private boolean isReal = false;

    public SendMail(){
        try {
        	
        	if(StringUtil.getSystemArea().equals("LOCAL")){
        		isReal = false;
			}else if(StringUtil.getSystemArea().equals("TEST")){
				isReal = false;
			}else{
				isReal = true;
			}
        	
            if(isReal){
                _HOST = StringUtil.getPropinfo().getProperty("MAIL_ADDR");
            }else{
                _HOST = StringUtil.getPropinfo().getProperty("MAIL_ADDR");
            }
        }catch (Exception e){
            StringUtil.printStackTrace(e);
        }
    }

    public SendMail(String str){
        this._HOST = str;
    }

    public String getHost(){
        return this._HOST;
    }

    /**
     * mail sedding
     * ex) oMail.sendMail("","","This is test", "asdfasdf", 0, false);
     * 
     * @param to       - 받는사람 메일주소
     * @param from     - 보내는 사람 메일주소
     * @param subject  - 제목
     * @param body     -
     * @param flag     - 메일 보낼 형식 text:0, html:1
     * @param debug    - 디버그 여부 디버그:false, 안함:true, debug 가 true 이면 운영자에게 보내기
     * @return success - 성공여부, 성공:true, 실패:false
     */
    public boolean sendMail(String to, String from, String subject, String body, int flag, boolean debug) throws Exception {
        boolean success = true;
        
        logger.info("mailtoReal : " + to);
        if(!isReal){
        	to = "O1959@ict-partners.co.kr";
        }
        
        if (to.equals("")) {
            return false;
        }
        

        try{
            Properties props = System.getProperties();
            props.put("mail.smtp.host", getHost());
            System.out.println(getHost());

            Session session = Session.getDefaultInstance(props, null);

            if(debug){
                session.setDebug(true);
            }

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject, "utf-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

            if(flag == 0){
                message.setContent(body, "text/plain; charset=utf-8");
            }else{
	                message.setContent(body, "text/html; charset=utf-8");
	                //message.setText(body, "UTF-8", "html");        
              
            }

            /*if(isReal){
                Transport.send(message);
            }*/
            
            // kjhward
            // Transport.send(message);
            
            logger.info("method : sendEmail ");
            logger.info("mail server : " + getHost());
            logger.info("from : " + from);
            logger.info("mailto : " + to);
            logger.info("body : " + body);
        } catch (Exception e) {
        	StringUtil.printStackTrace(e);
            success = false;
            logger.error("method : sendEmail ");
            logger.error("mail server : " + getHost());
            logger.error("from : " + from);
            logger.error("mailto : " + to);
            logger.error("body : " + body);
            logger.error(e.getMessage());
            throw e;
        }

        return success;
		 
    }
    //1개 첨부파일 발송 시
    public boolean sendMailFile(String to, String from, String subject, String body, String fileUrl, String orgFileName, int flag, boolean debug) throws MessagingException, Exception {
		boolean success = true;
		
		if(!isReal){
            to = "O1959@ict-partners.co.kr";
			//to = "naho8545@naver.com";
        }
		
		if(to.equals("")){
			return false;
		}
		
		try {
			
			Properties props = System.getProperties();
	        props.put("mail.smtp.host", getHost());

	        Session session = Session.getDefaultInstance(props, null);
	            
			if(debug){
				session.setDebug(true);
			}
			
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.setSubject(subject, "utf-8");
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			if (flag == 0) {
				message.setContent(body, "text/plain; charset=utf-8");
			} else {
				message.setContent(body, "text/html; charset=utf-8");
			}
			
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(body, "text/html; charset=utf-8");
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(fileUrl);
			mbp2.setDataHandler(new DataHandler(fds));
			//mbp2.setFileName(MimeUtility.encodeText(orgFileName, "utf-8", "Q"));
			mbp2.setFileName(orgFileName);
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			message.setContent(mp);
			
			// kjhward
			// Transport.send(message);
		}catch(MessagingException mex){
			StringUtil.printStackTrace(mex);
			logger.error("method : sendMailFile");
			logger.error("area : " + StringUtil.getSystemArea().toUpperCase());
			logger.error("mail server : " + getHost());
			logger.error("from : " + from);
			logger.error("mailto : " + to);
			logger.error("body : "  + body);
			logger.error(mex.getMessage());
			success = false;
			throw mex;
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			logger.error("method : sendMailFile");
			logger.error("area : " + StringUtil.getSystemArea().toUpperCase());
			logger.error("mail server : " + getHost());
			logger.error("from : " + from);
			logger.error("mailto : " + to);
			logger.error("body : "  + body);
			logger.error(e.getMessage());
			success = false;
			throw e;
		}
		
		return success;
	}
    //다수의 첨부파일 발송 시
    public boolean sendMailFiles(String to, String from, String subject, String body, List<FileInfo> File_Infos , int flag, boolean debug, String taskcd) throws MessagingException, Exception {
		boolean success = true;
		
		if(!isReal){
            to = "O1959@ict-partners.co.kr";
			//to = "naho8545@naver.com";
        }
		
		if(to.equals("")){
			return false;
		}
		try {
			
			Properties props = System.getProperties();
	        props.put("mail.smtp.host", getHost());
	        props.put("mail.mime.splitlongparameters", false);
	        
	        Session session = Session.getDefaultInstance(props, null);
	            
			if(debug){
				session.setDebug(true);
			}
			
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.setSubject(subject, "utf-8");
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			if (flag == 0) {
				message.setContent(body, "text/plain; charset=utf-8");
			} else {
				message.setContent(body, "text/html; charset=utf-8");
			}
			
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setContent(body, "text/html; charset=utf-8");
			
			
			
			
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			for(int i = 0 ; i < File_Infos.size(); i++) {
				MimeBodyPart mbp2 = new MimeBodyPart();
				FileDataSource fds = new FileDataSource("D:/Tomcat/webapps/upmu/upload/"+taskcd+"/"+File_Infos.get(i).getMfy_file_name());
				mbp2.setDataHandler(new DataHandler(fds));
				//mbp2.setFileName(MimeUtility.encodeText(orgFileName, "utf-8", "Q"));
				
				mbp2.setFileName(File_Infos.get(i).getOrg_file_name());
				mp.addBodyPart(mbp2);
				logger.info("파일명1 : " + File_Infos.get(i).getOrg_file_name());
				logger.info("파일명2 : " + mbp2.getFileName());
			}
			message.setContent(mp);
			
			// kjhward
			// Transport.send(message);
		}catch(MessagingException mex){
			StringUtil.printStackTrace(mex);
			logger.error("method : sendMailFile");
			logger.error("area : " + StringUtil.getSystemArea().toUpperCase());
			logger.error("mail server : " + getHost());
			logger.error("from : " + from);
			logger.error("mailto : " + to);
			logger.error("body : "  + body);
			logger.error(mex.getMessage());
			success = false;
			throw mex;
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			logger.error("method : sendMailFile");
			logger.error("area : " + StringUtil.getSystemArea().toUpperCase());
			logger.error("mail server : " + getHost());
			logger.error("from : " + from);
			logger.error("mailto : " + to);
			logger.error("body : "  + body);
			logger.error(e.getMessage());
			success = false;
			throw e;
		}
		
		return success;
		 
	}
}