package com.mnsoft.upmu.system.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.mnsoft.upmu.common.dao.CommonDAO;
import com.mnsoft.upmu.common.service.CommonService;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.StringUtil;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.system.dao.SystemDao;
import com.mnsoft.upmu.system.service.SystemService;
import com.mnsoft.upmu.system.vo.Application;
import com.mnsoft.upmu.system.vo.Code;
import com.mnsoft.upmu.system.vo.FileInfo;
import com.mnsoft.upmu.system.vo.Guide;
import com.mnsoft.upmu.system.vo.Link;
import com.mnsoft.upmu.system.vo.Role;
import com.mnsoft.upmu.system.vo.TaskNote;
import com.mnsoft.upmu.system.vo.Time;
import com.mnsoft.upmu.system.vo.Tree;
import com.mnsoft.upmu.vo.Menu;
import com.mnsoft.upmu.work.vo.Personal;
/*import com.namo.crossuploader.FileItem;
import com.namo.crossuploader.FileUpload;*/

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	@Autowired
	SystemDao systemDao;
	
	@Autowired
    CommonService commonService;
	
	@Autowired
    CommonDAO commonDao;
	
	@Autowired 
	MessageSource messageSource;

	@Override
	public List<Role> roleList(Role role) throws Exception {
		List<Role>roleList = systemDao.selectRoleList(role);
		return roleList;
	}

	@Override
	public List<Role> menu3List(Role role) throws Exception {
		List<Role>roleList = systemDao.menu3List(role);
		return roleList;
	}
	
	@Override
	public CommonMessage saveRole(HttpServletRequest req, List<Role> list) throws Exception {
		 CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getEdit_id().equals("I")) {
					systemDao.insertRole(list.get(i));
				}else {
					systemDao.updateRole(list.get(i));
				}
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
	public CommonMessage delRole(HttpServletRequest req, List<Role> list) throws Exception {

		CommonMessage message = new CommonMessage();
		try {
				for(int i=0 ; i<list.size() ; i++ ){
					systemDao.delRole(list.get(i));
					message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
					message.setSuccess("S");
				}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Role> holeList(Role role) throws Exception {
		List<Role>holeList = systemDao.selectHoleList(role);
		return holeList;
	}

	@Override
	public CommonMessage saveHoli(HttpServletRequest req, List<Role> list) throws Exception {
		 CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
					systemDao.margeHoli(list.get(i));
					
					if(!list.get(i).getHoli_code().equals("D")) {
						// 수정한 내용이 구분이 평일이 아닌 경우 upmu_plan 수정
						systemDao.updateUserWorkPlan(list.get(i));
						
						// 해당 날짜 모든 사용자의 해당날짜 실적(휴가) 삭제
						systemDao.deleteFutureWorkExecute(list.get(i));
						
					} else {
						// 수정한 내용이 평일인 경우 정상근무 비어있는 사용자 데이터 수정
						systemDao.updateUserWorkPlanNormalWork(list.get(i));
						// 수정한 내용이 평일인 경우 비정상근무 비어있는 사용자 데이터 수정
						systemDao.updateUserWorkPlanUnNormalWork(list.get(i));
					}
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
	public CommonMessage delHoli(HttpServletRequest req, List<Role> list) throws Exception {
		CommonMessage message = new CommonMessage();
		try {
				for(int i=0 ; i<list.size() ; i++ ){
					systemDao.delHoli(list.get(i));
					systemDao.updateUserWorkPlanNormalWork(list.get(i));
					systemDao.updateUserWorkPlanUnNormalWork(list.get(i));
					message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
					message.setSuccess("S");
				}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}

	@Override
	public List<Time> restTimeList(Time time) throws Exception {
		List<Time>restTimeList = systemDao.selectRestTimeList(time);
		return restTimeList;
	}


	@Override
	public int insertRestTime(Time time) throws Exception {
		return systemDao.insertRestTime(time);
	}

	@Override
	public int updateRestTime(Time time) throws Exception {
		return systemDao.updateRestTime(time);
	}

	@Override
	public int delRestTime(Time time) throws Exception {
		String [] restId = time.getSeq().split(",");
		Time delRest = new Time();
		if(restId != null && restId.length >0 ) {
			for(int i=0 ; i<restId.length ; i++) {
				delRest.setSeq(restId[i]);
				systemDao.delRestTime(delRest);
			}
			return 1;
		}else {
			return 0;
		}
	}

	@Override
	public List<Time> restTimeDetailList(Time time) throws Exception {
		List<Time>restTimeDetailList = systemDao.selectRestTimeDetailList(time);
		return restTimeDetailList;
	}

	@Override
	public int insertRestTimeDetail(Time time) throws Exception {
		return systemDao.insertRestTimeDetail(time);
	}

	@Override
	public int updateRestTimeDetail(Time time) throws Exception {
		return systemDao.updateRestTimeDetail(time);
	}

	@Override
	public List<Code> getCode(Code code) throws Exception {
		List<Code>CodeList = systemDao.selectCodeList(code);
		return CodeList;
	}

	@Override
	public List<Time> restExceList(Time time) throws Exception {
		List<Time>restExceList = systemDao.selectRestExceList(time);
		return restExceList;
	}

	@Override
	public List<Time> restExceDetail(Time time) throws Exception {
		List<Time>restExceDetail = systemDao.selectRestExceDetail(time);
		return restExceDetail;
	}

	@Override
	public List<Time> userList(Time time) throws Exception {
		List<Time>userList = systemDao.selectUserList(time);
		return userList;
	}

	@Override
	public List<Tree> treeList(Tree tree) throws Exception {
		// TODO Auto-generated method stub
		List<Tree>treeList = systemDao.selectTreeList(tree);
		return treeList;
	}

	@Override
	public List<Tree> selectTreeListUser(Tree tree) throws Exception {
		// TODO Auto-generated method stub
		List<Tree>treeListUser = systemDao.selectTreeListUser(tree);
		return treeListUser;
	}

	@Override
	public Personal getPersonalInfo(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return systemDao.getPersonalInfo(params);
	}


	/*@Override
	public int insertFile(FileInfo fileInfo) throws Exception {
		return systemDao.insertFile(fileInfo);
	}*/

	@Override
	public List<Menu> menuList(Menu menu) throws Exception {
		// TODO Auto-generated method stub
		List<Menu>  menuList = systemDao.selectMenuList(menu);
		return menuList;
	}


	@Override
	public CommonMessage saveMenu(HttpServletRequest req, List<Menu> list) throws Exception {



	 CommonMessage message = new CommonMessage();

	 try {
		for(int i=0 ; i<list.size() ; i++ ){
			if(list.get(i).getUp_in().equals("I")) {
				systemDao.saveMenu(list.get(i));
			}else {
				systemDao.updateMenu(list.get(i));
			}
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
	public List<Role> roleMnList(Role role) throws Exception {
		// TODO Auto-generated method stub
		List<Role>  roleMnList = systemDao.selectRoleMnList(role);
		return roleMnList;
	}

	@Override
	public CommonMessage saveRoleMn(HttpServletRequest req, List<Role> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getUp_in().equals("I")) {
					systemDao.saveRoleMn(list.get(i));
					systemDao.saveRoleMn1(list.get(i));
				}else {
					systemDao.updateRoleMn(list.get(i));
				}
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
	public CommonMessage deleteMenu(HttpServletRequest req, List<Menu> list) throws Exception {

		 CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				systemDao.deleteRoleMenu(list.get(i));
				systemDao.deleteMenu(list.get(i));
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}

	@Override
	public CommonMessage saveNoteInfo(HttpServletRequest req, HttpServletResponse res,TaskNote vo) {
		CommonMessage message = new CommonMessage();


		try {

			if(StringUtil.isNullToString(vo.getFile_id()).equals("")){
				vo.setFile_id(StringUtil.getDocNo());
			}

//			systemDao.deleteNoteInfo(vo);
			int count = systemDao.selectCountNoteInfo(vo);
			if(count == 0){
				systemDao.insertNoteInfo(vo);
			}else{
				systemDao.updateNoteInfo(vo);
			}
//			systemDao.saveNoteInfo(vo);
			
			/*CommonMessage fileMsg = commonService.insertFileMgmt(req, "WK", vo.getFile_id());
			
			if(!fileMsg.getResult().equals("")) {
				message.setMessage(fileMsg.getMessage());
				message.setSuccess("F");
			}else {
				message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				message.setSuccess("S");
			}*/

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
	public CommonMessage saveNoteFileInfo(HttpServletRequest req, HttpServletResponse res,TaskNote vo) {
		CommonMessage message = new CommonMessage();


		try {

			if(StringUtil.isNullToString(vo.getFile_id()).equals("")){
				vo.setFile_id(StringUtil.getDocNo());
			}

			CommonMessage fileMsg = commonService.insertFileMgmt(req, vo.getTask_cd(), vo.getFile_id());
			
			if(!fileMsg.getResult().equals("")) {
				message.setMessage(fileMsg.getMessage());
				message.setSuccess("F");
			}else {
				message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				message.setSuccess("S");
			}

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
	public TaskNote selectNoteInfo(TaskNote vo) {
		
		TaskNote rtnVo = systemDao.selectNoteInfo(vo);
		
		if(rtnVo != null){
            FileInfo fVo = new FileInfo();
            fVo.setFile_id(rtnVo.getFile_id());
            rtnVo.setFileList(commonDao.selectFileList(fVo));
            
        }
		return rtnVo;
	}
	
	@Override
	public List<Menu> treeRoleMenu(List<Menu> list) {
		// TODO Auto-generated method stub
		return systemDao.selectRoleMenuList(list.get(0));
	}

	@Override
	public CommonMessage saveRoleMnList(HttpServletRequest req, List<Role> list) {
		CommonMessage message = new CommonMessage();

		 try {
			 systemDao.deleteRoleMnList(list.get(0));
			for(int i=0 ; i<list.size() ; i++ ){
				
				systemDao.saveRoleMnList(list.get(i));
				
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
	public List<Role> userSearchList(Role role) {
		List<Role>  roleMnList = systemDao.selectUserSearchList(role);
		return roleMnList;
	}

	@Override
	public List<Role> roleUserList(Role role) {
		// TODO Auto-generated method stub
		List<Role>  roleMnList = systemDao.selectRoleUserList(role);
		return roleMnList;
	}

	@Override
	public CommonMessage saveRoleUser(HttpServletRequest req, List<Role> list) {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getGubun().equals("N")) {
					List<Role> userList = systemDao.selectRoleUserConfirm(list.get(i));
					if(userList.size() > 0) {
						message.setMessage(messageSource.getMessage("msg.auth.exist", null, StringUtil.getLocalLang(req))); //권한이 이미 존재합니다.
						message.setSuccess("N");
					} else {
						if(list.get(i).getUp_in().equals("I")) {
							systemDao.saveRoleUser(list.get(i));
						}else {
							systemDao.updateRoleUser(list.get(i));
						}
						message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
						message.setSuccess("S");
					}
				} else {
					systemDao.updateRoleUser(list.get(i));
					message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
					message.setSuccess("S");
				}
				
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	
	@Override
	public List<Menu> roleMenuMain(Menu menu) {
		// TODO Auto-generated method stub
		return systemDao.selectRoleMenuMain(menu);
	}
	
	@Override
	public List<Menu> roleMenuLeft(Menu menu) {
		
		if(menu.getMenu_type().equals("N") ) {//담당자
			return systemDao.selectRoleMenuLeftMn(menu);
		}else {
			return systemDao.selectRoleMenuLeft(menu);
		}
		
	}

	
	
	@Override
	public CommonMessage delRoleMn(HttpServletRequest req, List<Role> list) throws Exception {

		 CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){

				systemDao.delRoleMn(list.get(i));
				systemDao.deleteRoleMnList(list.get(0));
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	@Override
	public CommonMessage delRoleUser(HttpServletRequest req, List<Role> list) throws Exception {

		 CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){

				systemDao.delRoleUser(list.get(i));
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	
	@Override
	public List<Code> selectLcdList(Code code) {
		// TODO Auto-generated method stub
				List<Code>  codeList = systemDao.selectLcdList(code);
				return codeList;
	}

	@Override
	public List<Code> selectCdList(Code code) {
		// TODO Auto-generated method stub
				List<Code>  codeList = systemDao.selectCdList(code);
				return codeList;
	}
	
	@Override
	public List<Code> selectCdList2(Code code) {
		// TODO Auto-generated method stub
				List<Code>  codeList = systemDao.selectCdList2(code);
				return codeList;
	}
	
	@Override
	public List<Code> selectCdList3(Code code) {
		// TODO Auto-generated method stub
				List<Code>  codeList = systemDao.selectCdList3(code);
				return codeList;
	}
	
	@Override
	public List<Code> selectCdList4(Code code) {
		// TODO Auto-generated method stub
				List<Code>  codeList = systemDao.selectCdList4(code);
				return codeList;
	}
	
	@Override
	public List<Code> selectCdList5(Code code) {
		// TODO Auto-generated method stub
		List<Code>  codeList = systemDao.selectCdList5(code);
		return codeList;
	}
	
	@Override
	public List<Code> selectEmailList(Code code) {
		// TODO Auto-generated method stub
		List<Code>  codeList = systemDao.selectEmailList(code);
		return codeList;
	}

	@Override
	public CommonMessage saveLcd(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getUp_in().equals("I")) {
					systemDao.saveLcd(list.get(i));
				}else {
					systemDao.updateLcd(list.get(i));
				}
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
	public CommonMessage saveLcd2(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
				for(int i=0 ; i<list.size() ; i++ ){
					//System.out.println("----------------"+list.get(i).getUp_in());
					
					systemDao.saveLcd2(list.get(i));
					
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
	public CommonMessage delLcd(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				
				systemDao.delLcd(list.get(i));
				
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	@Override
	public CommonMessage delLcd2(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				
				systemDao.delLcd2(list.get(i));
				
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	
	@Override
	public CommonMessage saveCd(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getUp_in().equals("I")) {
					systemDao.saveCd(list.get(i));
				}else {
					systemDao.updateCd(list.get(i));
				}
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
	public CommonMessage saveCd2(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				if(list.get(i).getUp_in().equals("I")) {
					systemDao.saveCd2(list.get(i));
				}else {
					systemDao.updateCd2(list.get(i));
				}
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
	public CommonMessage delCd(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
			for(int i=0 ; i<list.size() ; i++ ){
				
				systemDao.delCd(list.get(i));
				
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		 	return message;
	}
	

	@Override
	public List<Link> selectLinkList(Link vo) {
		return systemDao.selectLinkList(vo);
	}
	
	@Override
	public CommonMessage saveLink(HttpServletRequest req, List<Link> list) {
		CommonMessage msg = new CommonMessage();

		try{
			for (int i=0;i<list.size();i ++) {
				if(list.get(i).getUp_in().equals("U") ){
					systemDao.updateLink(list.get(i));
				}else{
					systemDao.insertLink(list.get(i));
				}
			}
			msg.setSuccess("S");
			msg.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}
	@Override
	public CommonMessage deleteLink(HttpServletRequest req, List<Link> list) {
		CommonMessage msg = new CommonMessage();

		try{
			for(Link vo : list){
				systemDao.deleteLink(vo);
			}
			msg.setSuccess("S");
			msg.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}
	
	@Override
	public List<Guide> selectGuideList(Guide vo) {
		return systemDao.selectGuideList(vo);
	}
	
	@Override
	public List<Guide> selectGuideFile(Guide vo) {
		return systemDao.selectGuideFile(vo);
	}
	
	@Override
	public CommonMessage saveGuide(HttpServletRequest req, List<Guide> list) {
		CommonMessage msg = new CommonMessage();

		try{
			for (int i=0;i<list.size();i ++) {
				
				if(list.get(i).getUp_in().equals("U") ){
					systemDao.updateGuide(list.get(i));
				}else{
					systemDao.insertGuide(list.get(i));
				}
			}
			
			msg.setSuccess("S");
			msg.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}
	
	@Override
	public CommonMessage deleteGuide(HttpServletRequest req, List<Guide> list) {
		CommonMessage msg = new CommonMessage();
		
		try{
			for (int i=0;i<list.size();i ++) {
				systemDao.deleteGuide(list.get(i));
			}
			
			msg.setSuccess("S");
			msg.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}
	
	@Override
	public CommonMessage saveGuideFile(HttpServletRequest req, HttpServletResponse res, Guide vo) {
		CommonMessage msg = new CommonMessage();
		CommonMessage fileMsg = new CommonMessage();

		try{
			
			if(StringUtil.isNullToString(vo.getFile_id()).equals("")){
				vo.setFile_id(StringUtil.getDocNo());
				
				fileMsg = commonService.insertFileMgmt2(req, "GD", vo.getFile_id(), vo);
				
			}
			
			if(!fileMsg.getResult().equals("")) {
				msg.setMessage(fileMsg.getMessage());
				msg.setSuccess("F");
			}else {
				msg.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
				msg.setSuccess("S");
			}
			
			msg.setSuccess("S");
			msg.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			msg.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}
	
	@Override
	public CommonMessage deleteGuideFile(HttpServletRequest req, Guide vo) {
		 CommonMessage message = new CommonMessage();
	        String fileId  = vo.getFile_id();
	        String fileSeq = vo.getFile_seq();
	        
	        vo.setFile_id(fileId);
	        vo.setFile_seq(fileSeq);
	        int delCnt = systemDao.deleteGuideFile(vo);
	        if(delCnt == 0){
	            message.setResult("");
	            message.setMessage(messageSource.getMessage("msg.delete05", null, StringUtil.getLocalLang(req))); //삭제할 데이터가 존재하지 않습니다.
	        }else{
	            message.setResult(fileId);
	            message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
	        }
	        
	        return message;
	}
	
	@Override
	public CommonMessage saveRoleTask(HttpServletRequest req, List<Role> list) throws Exception {
		// TODO Auto-generated method stub
		CommonMessage message = new CommonMessage();
		//String role_seq = list.get(0).getRole_seq();

		 try {
			 systemDao.deleteRoleTask(list.get(0));
			for(int i=0 ; i<list.size() ; i++ ){
				
				
				systemDao.saveRoleTask(list.get(i));
				
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

	//업무신청서 리스트 
	@Override
	public List<Application> selectApplicationList(Application vo) {
		return systemDao.selectApplicationList(vo);
	}

	//업무코드 중복체크
	@Override
	public CommonMessage chkWorkCode(HttpServletRequest req, Application vo) {
		CommonMessage msg = new CommonMessage();
		try{
			List<Application> chkCount = systemDao.chkWorkCodeCount(vo);
			if("0".equals(chkCount.get(0).getAc_task_cd())){
				msg.setSuccess("S");
				//msg.setMessage("등록가능한 코드입니다.");
			}else {
				msg.setSuccess("D");
				msg.setMessage(messageSource.getMessage("msg.check.dup.code", null, StringUtil.getLocalLang(req))); //중복된 코드입니다.
			}
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			msg.setSuccess("F");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return msg;
	}

	@Override
	public CommonMessage saveApplication(HttpServletRequest req, HttpServletResponse res,Application vo) throws Exception {
		CommonMessage message = new CommonMessage();
		try {

			if("".equals(vo.getDoc_no())){
				String docNo = StringUtil.getDocNo();
				vo.setDoc_no(docNo);
				
				//메뉴 사용자(신청서) 3레벨추가
				systemDao.insertApplicationMenu(vo);

				//메뉴 등록 후 조회 
				List<Application> menuList = systemDao.menuIdSelect(vo);
				vo.setAc_menu_id(menuList.get(0).getAc_menu_id());
				vo.setAc_appl_id(menuList.get(0).getAc_appl_id());
				vo.setAc_list_id(menuList.get(0).getAc_list_id());
				vo.setAc_noti_id(menuList.get(0).getAc_noti_id());

				//메뉴 사용자(신청서) 4레벨추가
				systemDao.insertApplicationAppl(vo);
				//메뉴 관리자(조회) 추가
				systemDao.insertApplicationMenuList(vo);
				//메뉴 관리자(유의사항관리) 추가
				systemDao.insertApplicationMenuNoti(vo);
				//신청서 정보 추가
				systemDao.insertApplication(vo);
				//코드 추가 XX010 
				systemDao.insertApplCode(vo);
				//코드 추가 XX008
				systemDao.insertApplTableCode(vo);

				if(!"".equals(vo.getAc_m_user_id())) {
					String[] users = vo.getAc_m_user_id().split(",");
					String[] seq = vo.getAc_seq().split(",");
					System.out.println("Insert"+users.length);
					for(int i = 0; i < users.length; i++){
						vo.setAc_m_user_id(users[i]);
						vo.setAc_seq(seq[i]);
						systemDao.insertApplicationUser(vo);
					}
				}

				if(!"".equals(vo.getAc_m_dept_id())) {
					String[] depts = vo.getAc_m_dept_id().split(",");
					System.out.println("Insert"+depts.length);
					for(int i = 0; i < depts.length; i++){
						vo.setAc_m_dept_id(depts[i]);
						systemDao.insertApplicationDept(vo);
					}
				}

			}else {
				//메뉴 업데이트 3레벨 4레벨 
				systemDao.updateApplicationMenu(vo);
				//메뉴 관리자(조회) 업데이트
				systemDao.updateApplicationMenuList(vo);
				//메뉴 관리자(유의사항관리) 업데이트
				systemDao.updateApplicationMenuNoti(vo);

				//신청서 정보 업데이트
				systemDao.deleteApplication(vo);
				systemDao.insertApplication(vo);

				String beforeVal = vo.getBf_task_cd();
				String nowVal = vo.getAc_task_cd();
				if(beforeVal.equals(nowVal)) {
				}else {
					//신청서 리스트 업데이트
					systemDao.updateApplicationList(vo);
					
					//코드 업데이트 xx010
					systemDao.deleteApplicationCode(vo);
					systemDao.insertApplCode(vo);
					
					//코드 업데이트 xx008
					systemDao.deleteApplicationTableCode(vo);
					systemDao.insertApplTableCode(vo);

					systemDao.updateApplNoteCode(vo);
				}

				//담당자 지정 변경시 삭제후 Insert
				systemDao.deleteApplicationUser(vo);
				if(!"".equals(vo.getAc_m_user_id())) {
					String[] users = vo.getAc_m_user_id().split(",");
					String[] seq = vo.getAc_seq().split(",");
					for(int i = 0; i < users.length; i++){
						vo.setAc_m_user_id(users[i]);
						vo.setAc_seq(seq[i]);
						systemDao.insertApplicationUser(vo);
					}
				}
				
				//협조부서 지정 변경시 삭제후 Insert
				systemDao.deleteApplicationDept(vo);
				if(!"".equals(vo.getAc_m_dept_id())) {
					String[] depts = vo.getAc_m_dept_id().split(",");
					System.out.println(depts.length);
					for(int i = 0; i < depts.length; i++){
						vo.setAc_m_dept_id(depts[i]);
						systemDao.insertApplicationDept(vo);
					}
					
				}
			}

			message.setMessage(messageSource.getMessage("msg.save01", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
			message.setSuccess("S");
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.save02", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return message;
	}

	@Override
	public List<Application> popApplicationDetail(Application vo) {
		return systemDao.popApplicationDetail(vo);
	}

	@Override
	public CommonMessage deleteApplication(HttpServletRequest req, List<Application> list) throws Exception {
		
		CommonMessage message = new CommonMessage();
		try {
			if(list.get(0).getAc_task_cd() !="" || list.get(0).getAc_menu_id() !="") {
				for(int i=0 ; i<list.size() ; i++ ){
					//메뉴 권한 삭제
					systemDao.deleteApplicationRoleMenu(list.get(i));
					//4레벨 메뉴 삭제
					systemDao.deleteApplicationMenuDown(list.get(i));
					//3레벨 메뉴 삭제
					systemDao.deleteApplicationMenu(list.get(i));
					//신청서 삭제 
					systemDao.deleteApplication(list.get(i));
					//신청서 리스트 
					systemDao.deleteApplicationList(list.get(i));
					//시스템 코드 삭제 xx010
					systemDao.deleteApplicationCode(list.get(i));
					//시스템 코드 삭제  xx008
					systemDao.deleteApplicationTableCode(list.get(i));
					//시스템 유의사항 삭제 
					systemDao.deleteApplicationNoteCode(list.get(i));
					//신청서 담당지 삭제 
					systemDao.deleteApplicationUser(list.get(i));
					//신청서 협조부서 삭제 
					systemDao.deleteApplicationDept(list.get(i));
				}
				message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 삭제 되었습니다.
				message.setSuccess("S");
			}else {
				message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
				message.setSuccess("F");
			}
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //삭제중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}
	
	@Override
	public CommonMessage saveAdminInfo(HttpServletRequest req, TaskNote vo) {
		CommonMessage message = new CommonMessage();

		try {
			systemDao.deleteNoteInfo(vo);
			systemDao.saveNoteInfo(vo);

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
	public CommonMessage saveResortCd(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
				for(int i=0 ; i<list.size() ; i++ ){
					//System.out.println("----------------"+list.get(i).getUp_in());
					
					systemDao.saveResortCd(list.get(i));
					
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
	public CommonMessage saveResortCd2(HttpServletRequest req, List<Code> list) throws Exception {
		CommonMessage message = new CommonMessage();

		 try {
				for(int i=0 ; i<list.size() ; i++ ){
					//System.out.println("----------------"+list.get(i).getUp_in());
					
					systemDao.saveResortCd2(list.get(i));
					
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
	public List<Guide> selectPopupleftMenu(Guide guide) {
		/*if(guide.getGubun().equals("WF")){
			guide.setTable_nm("UPMU_WF_NOTE");
		}else if(guide.getGubun().equals("CA")){
			guide.setTable_nm("UPMU_CA_NOTE");
		}else{
			guide.setTable_nm("UPMU_TODAY_MEAL");
		}*/
		
		return systemDao.selectPopupleftMenu(guide);
	}
	
	@Override
	public List<Guide> selectPopupHeader(Guide guide) {
		return systemDao.selectPopupHeader(guide);
	}
	
	@Override
	public List<Guide> selectPopupContents(Guide guide) {
		return systemDao.selectPopupContents(guide);
	}

	@Override
	public FileInfo selectCommonFileMgmt(FileInfo vo) {
		return systemDao.selectCommonFileMgmt(vo);
	}

	@Override
	public CommonMessage saveCommonFileMgmt(HttpServletRequest req, FileInfo vo) {
		CommonMessage message = new CommonMessage();

		try {
			 systemDao.saveCommonFileMgmt(vo);
			 
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
	public List<Approval> selectReferMgmtList(Approval vo) {
		List<Approval> list = null;
		try {
			list = systemDao.selectReferMgmtList(vo);
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
		}
		return list;
	}

	@Override
	public List<DeptInfo> selectDeptListAll(DeptInfo vo) {
		return systemDao.selectDeptListAll(vo);
	}

	@Override
	public CommonMessage insertApprDocList(HttpServletRequest req, List<Approval> list) {
		CommonMessage message = new CommonMessage();

		try {
			 
			for(int i=0 ; i<list.size() ; i++) {
				
				String refStr  = list.get(i).getRef_info_arr();
				if(!StringUtil.isNullToString(refStr).equals("")) {
					String arrRef[] = refStr.split("#");
					
					for(int n=0 ; n < arrRef.length ; n++) {
						Approval refApprInfo = new Approval();
						
						String arrRefDtl[] =  arrRef[n].split("@");
						
						refApprInfo.setAppr_no(list.get(i).getAppr_no());
						refApprInfo.setRef_info(arrRefDtl[0]);
						refApprInfo.setRef_type(arrRefDtl[1]);
						commonDao.insertApprRefUsers(refApprInfo);
					}
				}
			}
			
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
	public CommonMessage deleteApprDocList(HttpServletRequest req, List<Approval> list) {
		CommonMessage message = new CommonMessage();

		try {
			 
			for(int i=0 ; i<list.size() ; i++) {
				commonDao.deleteApprMaster(list.get(i));
				commonDao.deleteApprDetail(list.get(i));
				commonDao.deleteApprRefDoc(list.get(i));
				commonDao.deleteApprRefUsers(list.get(i)); 
				commonDao.deleteApplInfo(list.get(i));
			}
			
			message.setMessage(messageSource.getMessage("msg.delete03", null, StringUtil.getLocalLang(req))); //정상적으로 저장 되었습니다.
			message.setSuccess("S");
				
		}catch(Exception e) {
			StringUtil.printStackTrace(e);
			message.setMessage(messageSource.getMessage("msg.delete04", null, StringUtil.getLocalLang(req))); //저장중 에러가 발생하였습니다.
			message.setSuccess("F");
		}
		return message;
	}
	
	
}
