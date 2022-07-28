package com.mnsoft.upmu.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mnsoft.upmu.system.vo.FileInfo;

public class FileUtil {

	
	public static String uploadFile(HttpServletRequest req, String[] paramVal){
        MultipartHttpServletRequest mhr = null;
        MultipartFile uploadFile = null;

        String fileName     = "";
        String realFolder   = "";
        String resultString = "";
       
        mhr = (MultipartHttpServletRequest) req;
        try{
        	String SYS_AREA = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
            realFolder = "";
            if(SYS_AREA.equals("REAL")) {
            	realFolder = StringUtil.getPropinfo().getProperty("PROD_PATH");
            }else if(SYS_AREA.equals("TEST")) {
            	realFolder = StringUtil.getPropinfo().getProperty("TEST_PATH");
            }else {
            	realFolder = StringUtil.getPropinfo().getProperty("LOCAL_PATH");
            }
        	
            realFolder += "/"+paramVal[1];
            
            List<MultipartFile> mpf = mhr.getFiles(paramVal[0]);
            for (int i = 0; i < mpf.size(); i++) {
                uploadFile = mpf.get(i);
                
                fileName = uploadFile.getOriginalFilename();
                
                if(!uploadFile.getOriginalFilename().equals("")){
                    String ext = StringUtil.lowerCase(StringUtil.lowerCase(StringUtil.fileExtention(fileName)));
                    if(ext.equals("")) {
                    /*if(!(ext.equals("hwp") || ext.equals("doc") || ext.equals("docx") || ext.equals("xls") || ext.equals("xlsx") ||
                         ext.equals("pdf") || ext.equals("ppt") || ext.equals("pptx")|| ext.equals("gif") || ext.equals("ncf") ||
                         ext.equals("png") || ext.equals("jpg") || ext.equals("jpge") || ext.equals("txt") || ext.equals("zip") || ext.equals("tiff") || ext.equals("tif"))){*/
                        //resultString += "E01:허용되지 않는 확장자;"+kdhecMessageSource.getMessage("FILE.0002")+";";
                        //resultString += "E01:허용되지 않는 확장자;";
                        return "E01";
                    }else{
                        String extention = StringUtil.fileExtention(uploadFile.getOriginalFilename());
                        String tmpFileName = StringUtil.getFileNm(Integer.toString(i)) + "." + extention;
                       
                        System.out.println("uploadFile.getSize()::"+uploadFile.getSize());
                        
                        //51200000 50메가
                        if(uploadFile.getSize() > 51200000){
                            resultString += "E02:파일v사이즈 초과;";
                            return "E02";
                        }else{
                        	System.out.println("uploadFile="+uploadFile);
                        	System.out.println("realFolder="+realFolder);
                        	System.out.println("tmpFileName="+tmpFileName);
                            
                            saveFile(uploadFile, realFolder, tmpFileName);
                            resultString += "S:"+fileName+":"+tmpFileName+":"+uploadFile.getSize()+";";
                        }
                    }
                }else{
                    //resultString += "E03:"+kdhecMessageSource.getMessage("FILE.0001")+";";
                }
            }
        }catch(Exception e){
            StringUtil.printStackTrace(e);
        }
        return resultString;
    }
    

	    public static String saveFile(MultipartFile multipartFile, String writeFilePath, String newFileName) throws Exception {
	        InputStream inputStream = null;
	        OutputStream outputStream = null;

	        if (multipartFile.getSize() > 0) {
	            try {
	                File dir = new File(writeFilePath);
	                if(!dir.exists()) {
	                    dir.mkdirs();
	                }

	                String newFileFullPath = writeFilePath+"/" + newFileName;
	                inputStream = multipartFile.getInputStream();
	                outputStream = new FileOutputStream(newFileFullPath);
	                int readBytes = 0;
	                byte[] buffer = new byte[1024];
	                while ((readBytes = inputStream.read(buffer, 0 , 1024))!=-1){
	                    outputStream.write(buffer, 0, readBytes);
	                }

	                outputStream.close();
	                inputStream.close();
	                return newFileFullPath;
	            } catch(Exception ex) {
	                StringUtil.printStackTrace(ex);
	                outputStream.close();
	                inputStream.close();
	            } finally {
	                if( outputStream != null ){
	                    outputStream.close();
	                }
	                if( inputStream != null ){
	                    inputStream.close();
	                }
	            }
	        }
	        return null;
	    }

	    public static String deleteFile(String delFolderName, String fileName) throws Exception {
	        String result       = "";
	        String fileFullPath = "";
	        String realFolder   = "";

	        realFolder = "/upload";

	        fileFullPath = realFolder+"/"+delFolderName+"/"+fileName;
	        try{
	            File delFile = new File(fileFullPath);
	            if(delFile.exists()){
	                delFile.delete();
	            }
	            result = "S";
	        }catch(Exception e){
	            StringUtil.printStackTrace(e);
	        }

	        return result;
	    }
}
