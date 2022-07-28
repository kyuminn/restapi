package com.mnsoft.upmu.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * <pre>
 * Statements
 * </pre>
 *
 * @ClassName   : FileDownView.java
 * @Description : 클래스 설명을 기술합니다.
 * @author mong
 * @since 2015. 9. 1.
 * @version 1.0
 * @see
 * @Modification Information
 * <pre>
 *     since          author              description
 *  ===========    =============    ===========================
 *  2015. 9. 1.     mong                  최초 생성
 * </pre>
 */

public class FileDownView extends AbstractView{
    
    public void Download(){
        setContentType("application/download; utf-8");
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected void renderMergedOutputModel(Map model, HttpServletRequest req, HttpServletResponse res) throws Exception {
        Map    datas         = (Map)model.get("fileData");
        String fileRealName  = (String)datas.get("fileRealName");
        String fileName      = (String)datas.get("fileName");
        String folderName    = (String)datas.get("folderName");
        String fileDelete    = StringUtil.isNullToString((String)datas.get("fileDelete"));
        String realFolder    = "";
        
        String SYS_AREA = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
        realFolder = "";
        if(SYS_AREA.equals("REAL")) {
        	realFolder = StringUtil.getPropinfo().getProperty("PROD_PATH");
        }else if(SYS_AREA.equals("TEST")) {
        	realFolder = StringUtil.getPropinfo().getProperty("TEST_PATH");
        }else {
        	realFolder = StringUtil.getPropinfo().getProperty("LOCAL_PATH");
        }
        fileRealName = fileRealName.replaceAll("\\.\\.", "");
        
        File downloadFile = new File(realFolder+"/"+folderName+"/"+fileRealName);
        res.setContentType(getContentType());
        res.setContentLength((int)downloadFile.length());
        
        // 한글 깨짐문제 수정 16.01.15 백승훈
        String browser = req.getHeader("User-Agent");
        //파일 인코딩
        if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){             
            fileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
        } else {               
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        

        System.out.println("fileName="+fileName);
        
        res.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";");
        res.setContentType("application/octer-stream");
        res.setHeader("Content-Transfer-Encoding", "binary");
        
        OutputStream out = res.getOutputStream();
        FileInputStream fis = null;
        
        try{
            fis = new FileInputStream(downloadFile);
            FileCopyUtils.copy(fis, out);
        }catch(Exception e){
            StringUtil.printStackTrace(e);
        }finally{
            if(fis != null){
                try{
                    fis.close();
                }catch(Exception e){}
            }
        }
        out.flush();
        
        if(fileDelete.equals("Y")){
            FileUtil.deleteFile(folderName, fileRealName);
        }
    }
}
