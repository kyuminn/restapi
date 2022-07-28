package com.mnsoft.upmu.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.mnsoft.upmu.system.vo.FileInfo;


 
 
public class FileAllDownView extends AbstractView {
	public void FileAllDownView() {
		setContentType("application/download; charset=utf-8");
	}
 
	@Override
	@SuppressWarnings("rawtypes")
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {

		ArrayList listObj = new ArrayList();
		listObj = (ArrayList<Object>) model.get("list");

		ArrayList<FileInfo> fileInfo = new ArrayList<FileInfo>();
		fileInfo.addAll(listObj);

		String realFolder	= "";
		String SYS_AREA = StringUtil.getPropinfo().getProperty("SYSTEM_AREA");
			realFolder = "";
			if(SYS_AREA.equals("REAL")) {
				realFolder = StringUtil.getPropinfo().getProperty("PROD_PATH");
			}else if(SYS_AREA.equals("TEST")) {
				realFolder = StringUtil.getPropinfo().getProperty("TEST_PATH");
			}else {
				realFolder = StringUtil.getPropinfo().getProperty("LOCAL_PATH");
			}
			
		String zipFileName = System.nanoTime()+"";
		FileOutputStream fos = new FileOutputStream(realFolder+"/"+zipFileName+".zip");
		ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos);
		zos.setEncoding("UTF-8");
		 
		byte[] buff = new byte[4096];
		
		for(int i=0; i<fileInfo.size(); i++) {
			String fileRealName  = fileInfo.get(i).getOrg_file_name();
			String fileName      = fileInfo.get(i).getMfy_file_name();
			String folderName    = fileInfo.get(i).getFile_fold();

			File file = new File(realFolder+"/"+folderName+"/"+fileName);
			FileInputStream fis = new FileInputStream(file);
			ZipArchiveEntry ze = new ZipArchiveEntry(fileRealName);
			zos.putArchiveEntry(ze);
			 
			int len;
			while((len = fis.read(buff)) > 0) {
				zos.write(buff, 0, len);
			}
			 
			zos.closeArchiveEntry();
			fis.close();
		}

		zos.close();

		File zipFile = new File(realFolder+"/"+zipFileName+".zip");
		response.setContentType(getContentType());
		response.setContentLength((int)zipFile.length());
 
		String userAgent = request.getHeader("User-Agent");
		boolean ie = userAgent.indexOf("MSIE") > -1;
		String fileName = null;
 
		String orgFileName = "download.zip";
		if(ie) {
			fileName = URLEncoder.encode(orgFileName, "utf-8");
		} else {
			fileName = new String(orgFileName.getBytes("utf-8"), "iso-8859-1");
		}
 
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		OutputStream out = response.getOutputStream();
 
		FileInputStream fis2 = null;
		try {
			fis2 = new FileInputStream(zipFile);
			FileCopyUtils.copy(fis2, out);
		} finally {
			if(fis2 != null) { try { fis2.close(); } catch(IOException ioe) {} }
			zipFile.delete();
		}
		out.flush();
	}
}
