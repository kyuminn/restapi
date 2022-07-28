package com.mnsoft.upmu.system.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.DeptInfo;
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


@Repository("SystemDao")
public class SystemDao {

	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	//공통 List
	public List<Code> selectCodeList(Code code)throws Exception {
		return sqlSession.selectList("selectDeptCodeList",code);
	}
	public List<Time> selectUserList(Time time)throws Exception {
		return sqlSession.selectList("selectUserList",time);
	}

	//권한관리 CRUD
	public List<Role> selectRoleList(Role role)throws Exception {
		return sqlSession.selectList("selectRoleList",role);
	}
	public List<Role> menu3List(Role role)throws Exception {
		return sqlSession.selectList("menu3List",role);
	}
	public int insertRole(Role vo)throws Exception {
		return sqlSession.insert("insertRole", vo);
	}
	public int updateRole(Role role)throws Exception {
		return sqlSession.update("updateRole", role);
	}
	public int delRole(Role role)throws Exception {
		return sqlSession.delete("delRole", role);
	}

	//공휴일관리 CRUD
	public List<Role> selectHoleList(Role role)throws Exception {
		return sqlSession.selectList("selectHoleList",role);
	}
	public int margeHoli(Role role)throws Exception {
		return sqlSession.insert("margeHoli", role);
	}
	public int delHoli(Role role)throws Exception {
		return sqlSession.delete("delHoli", role);
	}

	//휴게시간관리 CRUD
	public List<Time> selectRestTimeList(Time time)throws Exception {
		return sqlSession.selectList("selectHoleList",time);
	}
	public List<Time> selectRestTimeDetailList(Time time)throws Exception {
		return sqlSession.selectList("selectRestTimeDetailList",time);
	}
	public int insertRestTime(Time time)throws Exception {
		return sqlSession.insert("insertRestTime", time);
	}
	public int updateRestTime(Time time)throws Exception {
		return sqlSession.update("updateRestTime", time);
	}

	//휴게시간 , 예외 관리 삭제 공통
	public int delRestTime(Time time)throws Exception {
		return sqlSession.delete("delRestTime", time);
	}

	//휴게시간 예외관리 CRUD
	public List<Time> selectRestExceList(Time time)throws Exception {
		return sqlSession.selectList("selectRestExceList",time);
	}
	public List<Time> selectRestExceDetail(Time time)throws Exception {
		return sqlSession.selectList("selectRestExceDetail",time);
	}
	public int insertRestTimeDetail(Time time)throws Exception {
		return sqlSession.insert("insertRestTimeDetail", time);
	}
	public int updateRestTimeDetail(Time time)throws Exception {
		return sqlSession.update("updateRestTimeDetail", time);
	}


	public List<Tree> selectTreeList(Tree tree)throws Exception {
		return sqlSession.selectList("selectTreeList",tree);
	}
	public List<Tree> selectTreeListUser(Tree tree)throws Exception {
		return sqlSession.selectList("selectTreeListUser",tree);
	}
	public Personal getPersonalInfo(Map<String, Object> params) {
		return sqlSession.selectOne("getPersonalInfo", params);
	}

	public int insertFile(FileInfo fileInfo)throws Exception {
		return sqlSession.insert("insertFile", fileInfo);
	}

	public List<Menu> selectMenuList(Menu menu)throws Exception {
		return sqlSession.selectList("selectMenuList",menu);
	}
	public int saveMenu(Menu menu)throws Exception {
		return sqlSession.update("saveMenu", menu);
	}
	public int updateMenu(Menu menu)throws Exception {
		return sqlSession.update("updateMenu", menu);
	}
	public List<Role> selectRoleMnList(Role role)throws Exception {
		return sqlSession.selectList("selectRoleMnList",role);
	}
	public int saveRoleMn(Role role)throws Exception {
		return sqlSession.update("saveRoleMn", role);
	}
	public int updateRoleMn(Role role)throws Exception {
		return sqlSession.update("updateRoleMn", role);
	}
	public int deleteMenu(Menu menu)throws Exception {
		return sqlSession.delete("deleteMenu", menu);
	}

	public void saveNoteInfo(TaskNote vo)throws Exception {
		sqlSession.update("saveNoteInfo", vo);
	}
	public void deleteNoteInfo(TaskNote vo) {
		sqlSession.delete("deleteNoteInfo", vo);
	}
	public TaskNote selectNoteInfo(TaskNote vo) {
		return sqlSession.selectOne("selectNoteInfo", vo);
	}
	public List<Menu> selectRoleMenuList(Menu menu) {
		return sqlSession.selectList("selectRoleMenuList",menu);
	}
	public int saveRoleMnList(Role role) {
		return sqlSession.update("saveRoleMnList", role);
	}
	public int deleteRoleMnList(Role role) {
		return sqlSession.delete("deleteRoleMnList", role);
	}
	public List<Role> selectUserSearchList(Role role) {
		return sqlSession.selectList("selectUserSearchList",role);
	}
	public List<Role> selectRoleUserList(Role role) {
		return sqlSession.selectList("selectRoleUserList",role);
	}
	public void saveRoleUser(Role role) {
		sqlSession.update("saveRoleUser", role);
	}
	public void updateRoleUser(Role role) {
		sqlSession.update("updateRoleUser", role);
	}
	public List<Menu> selectRoleMenuMain(Menu menu) {
		return sqlSession.selectList("selectRoleMenuMain",menu);
	}
	public List<Menu> selectRoleMenuLeft(Menu menu) {
		return sqlSession.selectList("selectRoleMenuLeft",menu);
	}
	public void delRoleMn(Role role) {
		sqlSession.delete("delRoleMn", role);
	}
	public void delRoleUser(Role role) {
		sqlSession.delete("delRoleUser", role);
	}
	
	public List<Link> selectLinkList(Link vo) {
		return sqlSession.selectList("selectLinkList",vo);
	}
	public void updateLink(Link link) {
		sqlSession.update("updateLink", link);
	}
	public void insertLink(Link link) {
		sqlSession.update("insertLink", link);
	}
	public void deleteLink(Link vo) {
		sqlSession.delete("deleteLink", vo);
	}
	public int saveLcd(Code code)throws Exception {
		return sqlSession.update("saveLcd", code);
	}
	public int updateLcd(Code code)throws Exception {
		return sqlSession.update("updateLcd", code);
	}	
	public int delLcd(Code code)throws Exception {
		return sqlSession.delete("delLcd", code);
	}
	
	public int saveCd(Code code)throws Exception {
		return sqlSession.update("saveCd", code);
	}
	public int updateCd(Code code)throws Exception {
		return sqlSession.update("updateCd", code);
	}	
	
	public int saveCd2(Code code)throws Exception {
		return sqlSession.update("saveCd2", code);
	}
	public int updateCd2(Code code)throws Exception {
		return sqlSession.update("updateCd2", code);
	}
	
	public int delCd(Code code)throws Exception {
		return sqlSession.update("delCd", code);
	}
	public List<Code> selectLcdList(Code code) {
		return sqlSession.selectList("selectLcdList",code);
	}
	public List<Code> selectCdList(Code code) {
		return sqlSession.selectList("selectCdList",code);
	}
	public List<Code> selectCdList2(Code code) {
		return sqlSession.selectList("selectCdList2",code);
	}
	public List<Code> selectCdList3(Code code) {
		return sqlSession.selectList("selectCdList3",code);
	}
	public List<Code> selectCdList4(Code code) {
		return sqlSession.selectList("selectCdList4",code);
	}
	public List<Code> selectCdList5(Code code) {
		return sqlSession.selectList("selectCdList5",code);
	}
	public List<Code> selectEmailList(Code code) {
		return sqlSession.selectList("selectEmailList",code);
	}
	public List<Menu> selectRoleMenuLeftMn(Menu menu) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("selectRoleMenuLeftMn",menu);
	}
	public int saveRoleTask(Role role)throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.update("insertRoleTaxk", role);
		
	}
	
	public void deleteRoleTask(Role role) {
		sqlSession.delete("deleteRoleTask", role);
	}
	public List<Guide> selectGuideList(Guide vo) {
		return sqlSession.selectList("selectGuideList",vo);
	}
	public void updateGuide(Guide guide) {
		sqlSession.update("updateGuide", guide);
	}
	public void insertGuide(Guide guide) {
		sqlSession.update("insertGuide", guide);
	}
	public List<Guide> selectGuideFile(Guide vo) {
		return sqlSession.selectList("selectGuideFile",vo);
	}
	public int deleteGuideFile(Guide vo) {
		return sqlSession.update("deleteGuideFile", vo);
	}

	public List<Application> selectApplicationList(Application vo) {
		return sqlSession.selectList("selectApplicationList",vo);
	}

	public List<Application> chkWorkCodeCount(Application vo) {
		return sqlSession.selectList("chkWorkCodeCount", vo);
	}
	public void insertApplicationMenu(Application application) {
		sqlSession.insert("insertApplicationMenu", application);
	}
	public void insertApplicationAppl(Application application) {
		sqlSession.insert("insertApplicationAppl", application);
	}
	public void insertApplicationMenuList(Application application) {
		sqlSession.insert("insertApplicationMenuList", application);
	}
	public void insertApplicationMenuNoti(Application application) {
		sqlSession.insert("insertApplicationMenuNoti", application);
	}
	public void updateApplicationMenu(Application application) {
		sqlSession.update("updateApplicationMenu", application);
	}
	public void updateApplicationMenuList(Application application) {
		sqlSession.update("updateApplicationMenuList", application);
	}
	public void updateApplicationMenuNoti(Application application) {
		sqlSession.update("updateApplicationMenuNoti", application);
	}
	public List<Application> menuIdSelect(Application vo) {
		return sqlSession.selectList("menuIdSelect", vo);
	}
	public void insertApplication(Application application) {
		sqlSession.insert("insertApplication", application);
	}
	public void insertApplicationUser(Application application) {
		sqlSession.insert("insertApplicationUser", application);
	}
	public void insertApplicationDept(Application application) {
		sqlSession.insert("insertApplicationDept", application);
	}
	public void updateApplication(Application application) {
		sqlSession.update("updateApplication", application);
	}
	public void updateApplicationList(Application application) {
		sqlSession.update("updateApplicationList", application);
	}
	public void deleteApplicationUser(Application application) {
		sqlSession.delete("deleteApplicationUser", application);
	}
	public void deleteApplicationDept(Application application) {
		sqlSession.delete("deleteApplicationDept", application);
	}
	public void updateApplicationUser(Application application) {
		sqlSession.update("updateApplicationUser", application);
	}
	public void insertApplCode(Application application) {
		sqlSession.insert("insertApplCode", application);
	}
	public void insertApplTableCode(Application application) {
		sqlSession.insert("insertApplTableCode", application);
	}
	public void updateApplCode(Application application) {
		sqlSession.update("updateApplCode", application);
	}
	public void updateApplNoteCode(Application application) {
		sqlSession.update("updateApplNoteCode", application);
	}
	public void insertApplNoteCode(Application application) {
		sqlSession.insert("insertApplNoteCode", application);
	}
	public List<Application> popApplicationDetail(Application vo) {
		return sqlSession.selectList("popApplicationDetail", vo);
	}
	public int deleteApplicationMenuDown(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationMenuDown", application);
	}
	public int deleteApplicationMenu(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationMenu", application);
	}
	public int deleteApplicationRoleMenu(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationRoleMenu", application);
	}
	public int deleteApplication(Application application)throws Exception {
		return sqlSession.delete("deleteApplication", application);
	}
	
	public int deleteRoleMenu(Menu menu)throws Exception {
		return sqlSession.delete("deleteRoleMenu", menu);
	}
	public int deleteApplicationList(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationList", application);
	}
	public int deleteApplicationCode(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationCode", application);
	}
	public int deleteApplicationTableCode(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationTableCode", application);
	}
	public int deleteApplicationNoteCode(Application application)throws Exception {
		return sqlSession.delete("deleteApplicationNoteCode", application);
	}
	public List<Guide> selectPopupleftMenu(Guide guide) {
		return sqlSession.selectList("selectPopupleftMenu",guide);
	}
	public List<Guide> selectPopupHeader(Guide guide) {
		return sqlSession.selectList("selectPopupHeader",guide);
	}
	public List<Guide> selectPopupContents(Guide guide) {
		return sqlSession.selectList("selectPopupContents",guide);
	}
	public int saveLcd2(Code code) {
		// TODO Auto-generated method stub
		return sqlSession.update("saveLcd2", code);
	}
	
	
	public int delLcd2(Code code)throws Exception {
		return sqlSession.delete("delLcd2", code);
	}
	public int saveResortCd(Code code) {
		// TODO Auto-generated method stub
		return sqlSession.update("saveResortCd", code);
		
	}
	public int saveResortCd2(Code code) {
		// TODO Auto-generated method stub
		return sqlSession.update("saveResortCd2", code);
	}
	public FileInfo selectCommonFileMgmt(FileInfo vo) {
		return sqlSession.selectOne("selectCommonFileMgmt", vo);
	}
	public void saveCommonFileMgmt(FileInfo vo) {
		sqlSession.insert("saveCommonFileMgmt", vo);
	}
	public int saveRoleMn1(Role role) {
		// TODO Auto-generated method stub
		return sqlSession.insert("saveRoleMnList1", role);
		
	}
	
	public int deleteGuide(Guide guide) {
		return sqlSession.delete("deleteGuide", guide);
	}
	public int selectCountNoteInfo(TaskNote vo) {
		return sqlSession.selectOne("selectCountNoteInfo",vo);
	}
	
	public void insertNoteInfo(TaskNote vo) {
		sqlSession.insert("insertNoteInfo", vo);
	}
	public void updateNoteInfo(TaskNote vo) {
		sqlSession.update("updateNoteInfo", vo);
	}
	public List<Approval> selectReferMgmtList(Approval vo) {
		return sqlSession.selectList("selectReferMgmtList",vo);
	}
	public List<DeptInfo> selectDeptListAll(DeptInfo vo) {
		return sqlSession.selectList("selectDeptListAll",vo);
	}
	public List<Role> selectRoleUserConfirm(Role role) {
		return sqlSession.selectList("selectRoleUserConfirm",role);
	}
	
	public void updateUserWorkPlan(Role role) {
		sqlSession.update("updateUserWorkPlan", role);
	}
	
	public void updateUserWorkPlanNormalWork(Role role) {
		sqlSession.update("updateUserWorkPlanNormalWork", role);
	}
	
	public void updateUserWorkPlanUnNormalWork(Role role) {
		sqlSession.update("updateUserWorkPlanUnNormalWork", role);
	}
	
	public void deleteFutureWorkExecute(Role role) {
		sqlSession.update("deleteFutureWorkExecute", role);
	}
	
}
