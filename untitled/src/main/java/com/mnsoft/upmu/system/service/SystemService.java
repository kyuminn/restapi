package com.mnsoft.upmu.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.education.vo.Education;
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


public interface SystemService {

	public List<Role> roleList(Role role) throws Exception;
	public List<Role> menu3List(Role role) throws Exception;
	public CommonMessage saveRole(HttpServletRequest req, List<Role> list)throws Exception;
	public CommonMessage delRole(HttpServletRequest req, List<Role> list)throws Exception;

	public List<Role> holeList(Role role) throws Exception;
	public CommonMessage saveHoli(HttpServletRequest req, List<Role> list)throws Exception;
	public CommonMessage delHoli(HttpServletRequest req, List<Role> list)throws Exception;


	public List<Time> restTimeList(Time time) throws Exception;
	public int insertRestTime(Time time)throws Exception;
	public int updateRestTime(Time time)throws Exception;
	public int delRestTime(Time time)throws Exception;

	public List<Time> restTimeDetailList(Time time) throws Exception;
	public int insertRestTimeDetail(Time time)throws Exception;
	public int updateRestTimeDetail(Time time)throws Exception;

	public List<Code> getCode(Code code) throws Exception;

	public List<Time> restExceList(Time time) throws Exception;

	public List<Time> restExceDetail(Time time) throws Exception;

	public List<Time> userList(Time time) throws Exception;

	public List<Tree> treeList(Tree tree) throws Exception;
	public List<Tree> selectTreeListUser(Tree tree) throws Exception;

	public Personal getPersonalInfo(Map<String, Object> params);

	//public int insertFile(FileInfo fileInfo)throws Exception;

	public List<Menu> menuList(Menu menu) throws Exception;
	public CommonMessage saveMenu(HttpServletRequest req, List<Menu> list) throws Exception;
	public List<Role> roleMnList(Role role) throws Exception;
	public CommonMessage saveRoleMn(HttpServletRequest req, List<Role> list)throws Exception;
	public CommonMessage deleteMenu(HttpServletRequest req, List<Menu> list)throws Exception;


	public CommonMessage saveNoteInfo(HttpServletRequest req, HttpServletResponse res, TaskNote vo);
	public CommonMessage saveNoteFileInfo(HttpServletRequest req, HttpServletResponse res, TaskNote vo);
	public TaskNote selectNoteInfo(TaskNote vo);
	public List<Menu> treeRoleMenu(List<Menu> list);
	public CommonMessage saveRoleMnList(HttpServletRequest req, List<Role> list);
	public List<Role> userSearchList(Role role);
	public List<Role> roleUserList(Role role);
	public CommonMessage saveRoleUser(HttpServletRequest req, List<Role> list);
	public List<Menu> roleMenuMain(Menu menu);
	public List<Menu> roleMenuLeft(Menu menu);
	public CommonMessage delRoleMn(HttpServletRequest req, List<Role> list)throws Exception;
	public CommonMessage delRoleUser(HttpServletRequest req, List<Role> list)throws Exception;
	public List<Code> selectLcdList(Code code);
	public List<Code> selectCdList(Code code);
	public CommonMessage saveLcd(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage delLcd(HttpServletRequest req, List<Code> list)throws Exception;
	
	public CommonMessage saveCd(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage saveCd2(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage delCd(HttpServletRequest req, List<Code> list)throws Exception;
	public List<Link> selectLinkList(Link vo) throws Exception;
	public CommonMessage saveLink(HttpServletRequest req, List<Link> list);
	public CommonMessage deleteLink(HttpServletRequest req, List<Link> list);
	public CommonMessage saveRoleTask(HttpServletRequest req, List<Role> list)throws Exception;	
	
	public List<Guide> selectGuideList(Guide guide) throws Exception;
	public CommonMessage saveGuide(HttpServletRequest req, List<Guide> list) throws Exception;
	public CommonMessage deleteGuide(HttpServletRequest req, List<Guide> list) throws Exception;
	public List<Guide> selectGuideFile(Guide guide) throws Exception;
	public CommonMessage saveGuideFile(HttpServletRequest req, HttpServletResponse res, Guide vo) throws Exception;
	public CommonMessage deleteGuideFile(HttpServletRequest req, Guide vo) throws Exception;

	public List<Application> selectApplicationList(Application application) throws Exception;
	public CommonMessage chkWorkCode(HttpServletRequest req, Application vo);
	public CommonMessage saveApplication(HttpServletRequest req, HttpServletResponse res, Application vo) throws Exception;
	public List<Application> popApplicationDetail(Application application) throws Exception;
	public CommonMessage deleteApplication(HttpServletRequest req, List<Application> list) throws Exception;
	public CommonMessage saveAdminInfo(HttpServletRequest req, TaskNote taskNote) throws Exception;
	public CommonMessage saveLcd2(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage delLcd2(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage saveResortCd(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage saveResortCd2(HttpServletRequest req, List<Code> list)throws Exception;
	public List<Guide> selectPopupleftMenu(Guide guide)throws Exception;
	public List<Guide> selectPopupHeader(Guide guide)throws Exception;
	public List<Guide> selectPopupContents(Guide guide)throws Exception;
	
	public FileInfo selectCommonFileMgmt(FileInfo vo);
	public CommonMessage saveCommonFileMgmt(HttpServletRequest req, FileInfo vo);
	public List<Code> selectCdList2(Code code) throws Exception;
	public List<Approval> selectReferMgmtList(Approval vo);
	public List<DeptInfo> selectDeptListAll(DeptInfo vo);
	public CommonMessage insertApprDocList(HttpServletRequest req, List<Approval> list);
	public CommonMessage deleteApprDocList(HttpServletRequest req, List<Approval> list);
	public List<Code> selectCdList3(Code code);
	public List<Code> selectCdList4(Code code);
	public List<Code> selectCdList5(Code code);
	public List<Code> selectEmailList(Code code);
}
