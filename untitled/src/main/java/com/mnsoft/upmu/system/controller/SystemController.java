package com.mnsoft.upmu.system.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mnsoft.upmu.common.util.AbstractCntr;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.util.JSONBaseVO;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.common.vo.DeptInfo;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.holiday.vo.Holiday;
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

import net.sf.json.JSONObject;

@Controller
public class SystemController {

	@Resource(name="systemService")
	SystemService systemService;



	/**
	 * 권한 관리 페이지 호출
	 */
	@RequestMapping("/system/role.do")
	public String role(@RequestParam String menuId, @RequestParam String menuType, Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/role";
	}
	
	@RequestMapping("/system/test1.do")
	public String test1(@RequestParam String menuId, @RequestParam String menuType, Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/test1";
	}
	
	@RequestMapping("/system/referMgmt.do")
	public String referMgmt(@RequestParam String menuId, @RequestParam String menuType, Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/referMgmt";
	}

	/**
	 * 권한 관리 리스트 호출
	 */
	@RequestMapping(value="/system/roleList.do")
	public ModelAndView getRoleList(@ModelAttribute("role") Role role, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> roleList = systemService.roleList(role);
		modelData.addObject("data",roleList);
		return modelData;
	}
	
	/**
	 * 권한 관리 리스트 호출
	 */
	@RequestMapping(value="/system/menu3List.do")
	public ModelAndView Menu3List(@ModelAttribute("role") Role role, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> roleList = systemService.menu3List(role);
		modelData.addObject("data",roleList);
		return modelData;
	}


	@ResponseBody
	@RequestMapping(value="/system/saveRole.do", produces="application/json; charset=utf-8")
	public String saveRole(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveRole(req, list);

		return JSONObject.fromObject(message).toString();
	}


	/**
	 * 권한 관리 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/delRole.do", produces="application/json; charset=utf-8")
	public String delRole(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params , Model model)throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.delRole(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 공휴일 관리 페이지 호출
	 */
	@RequestMapping(value="/system/holiday.do")
	public String holiday(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/holiday";
	}
	
	@RequestMapping(value="/system/fileMgmt.do")
	public String fileMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/fileMgmt";
	}

	/**
	 * tree 호출
	 */
	@RequestMapping(value="/system/tree.do")
	public String tree(@ModelAttribute("menu") Menu menu, Model model) throws Exception{

		return "system/tree";

	}


	/**
	 * tree 호출
	 */
	@RequestMapping(value="/system/treeMenu.do")
	public ModelAndView tree2( Menu menu, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");

		List<Menu> menuList = systemService.menuList(menu);		
		modelData.addObject("data",menuList);
		return modelData;

	}


	/**
	 * tree 사용자호출
	 */
	@RequestMapping(value="/system/treeUser.do")
	public ModelAndView treeUser(@ModelAttribute("Tree") Tree tree, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Tree> treeListUser = systemService.selectTreeListUser(tree);

		
		modelData.addObject("data" , treeListUser);
		return modelData;
	}

	/**
	 * 공휴일 관리 리스트 호출
	 */
	@RequestMapping(value="/system/holidayList.do")
	public ModelAndView getHolidayList(@ModelAttribute("role") Role role, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> holeList = systemService.holeList(role);
		modelData.addObject("data",holeList);
		return modelData;
	}


	@ResponseBody
	@RequestMapping(value="/system/saveHoli.do", produces="application/json; charset=utf-8")
	public String saveHoli(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveHoli(req, list);

		//ModelAndView modelAndView = new ModelAndView("system/saveHoli");
		//modelAndView.addObject(AbstractCntr.JSON_DATA_KEY, JSONObject.fromObject(message).toString());

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 공휴일 관리 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/delHoli.do", produces="application/json; charset=utf-8")
	public String delHoli(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params , Model model)throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.delHoli(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 휴게시간 관리 페이지 호출
	 */
	@RequestMapping(value="/system/restTime.do")
	public String restTime(@RequestParam String menuId, @RequestParam String menuType ,Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/restTime";
	}

	/**
	 * 휴게시간 관리 리스트 호출
	 */
	@RequestMapping(value="/system/restTimeList.do")
	public ModelAndView getRestTimeList(@ModelAttribute("time") Time time, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Time> restTimeList = systemService.restTimeList(time);
		modelData.addObject("data",restTimeList);
		return modelData;
	}

	/**
	 * 휴게시간 관리  등록
	 */
	@ResponseBody
	@RequestMapping(value="/system/insertRestTime.do", produces="application/json; charset=utf-8")
	public int insertRestTime(@ModelAttribute("time") Time time, Model model)throws Exception{
		return systemService.insertRestTime(time);
	}

	/**
	 * 휴게시간 관리  수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/updateRestTime.do", produces="application/json; charset=utf-8")
	public int updateRestTime(@ModelAttribute("time") Time time, Model model)throws Exception{
		return systemService.updateRestTime(time);
	}

	/**
	 * 휴게시간 관리 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/delRestTime.do", produces="application/json; charset=utf-8")
	public int delRestTime(@ModelAttribute("time") Time time, Model model)throws Exception{
		return systemService.delRestTime(time);
	}


	/**
	 * 휴게시간 관리 디테일 리스트 호출
	 */
	@RequestMapping(value="/system/restTimeDetailList.do")
	public ModelAndView getRestTimeDetailList(@ModelAttribute("time") Time time, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Time> restTimeDetailList = systemService.restTimeDetailList(time);
		modelData.addObject("data",restTimeDetailList);
		return modelData;
	}

	/**
	 * 휴게시간 관리  디테일 등록
	 */
	@ResponseBody
	@RequestMapping(value="/system/insertRestTimeDetail.do", produces="application/json; charset=utf-8")
	public int insertRestTimeDetail(@ModelAttribute("time") Time time, Model model)throws Exception{
		return systemService.insertRestTimeDetail(time);
	}

	/**
	 * 휴게시간 관리  디테일 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/updateRestTimeDetail.do", produces="application/json; charset=utf-8")
	public int updateRestTimeDetail(@ModelAttribute("time") Time time, Model model)throws Exception{
		return systemService.updateRestTimeDetail(time);
	}


	/**
	 * 휴게시간 예외관리 페이지 호출
	 */
	@RequestMapping(value="/system/restTimeExce.do")
	public String restTimeExce(@RequestParam String menuId, @RequestParam String menuType ,Model model) throws Exception{

		Code code = new Code();
		List<Code> deptCodeList = systemService.getCode(code);

		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		model.addAttribute("deptCode", deptCodeList);

		return "system/restTimeExce";
	}

	/**
	 * 휴게시간 예외 리스트 호출
	 */
	@RequestMapping(value="/system/restExceList.do")
	public ModelAndView getRestExceList(@ModelAttribute("time") Time time, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Time> restExceList = systemService.restExceList(time);
		modelData.addObject("data",restExceList);
		return modelData;
	}

	/**
	 * 휴게시간 예외 디테일 리스트 호출
	 */
	@RequestMapping(value="/system/restExceDetail.do")
	public ModelAndView getRrestExceDetail(@ModelAttribute("time") Time time, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Time> restExceDetail = systemService.restExceDetail(time);
		modelData.addObject("data",restExceDetail);
		return modelData;
	}

	/**
	 * 휴게시간 예외 모달 사용자 리스트
	 */
	@RequestMapping(value="/system/userList.do")
	public ModelAndView getUserList(@ModelAttribute("time") Time time, Model model) throws Exception{

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Time> userList = systemService.userList(time);
		modelData.addObject("data",userList);
		return modelData;
	}





	/*@ResponseBody
	@RequestMapping(value="/system/boardInsert")
	public int boardInsertProc(@ModelAttribute("fileInfo") FileInfo fileInfo, Model model) throws Exception{
		return fileUtils.fileUpload(fileInfo);
	}*/

	/**
	 * 메뉴 관리 페이지 호출
	 */
	@RequestMapping(value="/system/menu.do")
	public String menu(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/menu";
	}

	/**
	 * 메뉴 관리 리스트 호출
	 */
	@RequestMapping(value="/system/menuList.do")
	public ModelAndView getMenuList(@ModelAttribute("menu") Menu menu, Model model) throws Exception{


		ModelAndView modelData = new ModelAndView("jsonView");
		List<Menu> menuList = systemService.menuList(menu);
		modelData.addObject("data",menuList);
		return modelData;
	}


	/**
	 * 메뉴관리 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveMenu.do", produces="application/json; charset=utf-8")
	public String saveMenu(@ModelAttribute("menu") Menu menu, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Menu> list = (List<Menu>)AbstractCntr.getJsonToList(params, Menu.class);
		CommonMessage message = systemService.saveMenu(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 롤 관리 페이지 호출
	 */
	@RequestMapping(value="/system/roleMenu.do")
	public String roleMenu(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/roleMenu";
	}
	
	/**
	 * 롤 관리 페이지 호출
	 */
	@RequestMapping(value="/system/roleUser.do")
	public String roleUser(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/roleUser";
	}
	
	/**
	 * 사용자조회 팝업 호출
	 */
	@RequestMapping(value="/system/roleUserList.do")
	public ModelAndView roleUserList(@ModelAttribute("role") Role role, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> roleMnList = systemService.roleUserList(role);
		modelData.addObject("data",roleMnList);
		return modelData;
	}
	
	/**
	 * 사용자조회 팝업 호출
	 */
	@RequestMapping(value="/system/userListPopup.do")
	public ModelAndView userListPopup(@ModelAttribute("role") Role role, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> roleMnList = systemService.userSearchList(role);
		modelData.addObject("data",roleMnList);
		return modelData;
	}
	
	/**
	 * 사용자조회 팝업 호출
	 */
	@RequestMapping(value="/system/deptListPopup.do")
	public ModelAndView deptListPopup(@ModelAttribute("deptInfo") DeptInfo deptInfo, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<DeptInfo> roleMnList = systemService.selectDeptListAll(deptInfo);
		modelData.addObject("data",roleMnList);
		return modelData;
	}
	
	/**
	 * 롤 사용자  등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveRoleUser.do", produces="application/json; charset=utf-8")
	public String saveRoleUser(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveRoleUser(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	
	

	/**
	 * 롤 관리 리스트 호출
	 */
	@RequestMapping(value="/system/roleMnList.do")
	public ModelAndView getRoleMnList(@ModelAttribute("role") Role role, Model model) throws Exception{


		ModelAndView modelData = new ModelAndView("jsonView");
		List<Role> roleMnList = systemService.roleMnList(role);
		modelData.addObject("data",roleMnList);
		return modelData;
	}
	
	/**
	 * tree 호출
	 */
	/*@RequestMapping(value="/system/treeRoleMenu.do")
	public ModelAndView treeRoleMenu( @ModelAttribute("menu") Menu menu, Model model) throws Exception{		
		Object obj=menu;
		for (Field field : obj.getClass().getDeclaredFields()){
		    field.setAccessible(true);
		    Object value=field.get(obj);
		    System.out.println(field.getName()+","+value);
		}
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Menu> menuList = systemService.treeRoleMenu(menu);		
		modelData.addObject("data",menuList);
		return modelData;
	}*/
	
	
	@ResponseBody
	@RequestMapping(value="/system/treeRoleMenu.do", produces="application/json; charset=utf-8")
	public ModelAndView treeRoleMenu(@ModelAttribute("menu") Menu menu, Model model
			,@RequestParam(value="params", required=true) String params )throws Exception{

		/*ModelAndView modelData = new ModelAndView("jsonView");
		List<Menu> menuList = systemService.treeRoleMenu(menu);		
		modelData.addObject("data",menuList);
		return modelData;*/
		

		ModelAndView modelData = new ModelAndView("jsonView");
		List<Menu> list = (List<Menu>)AbstractCntr.getJsonToList(params, Menu.class);
		List<Menu> menuList = systemService.treeRoleMenu(list);

		modelData.addObject("data",menuList);
		return modelData;
	}

	


	/**
	 * 메뉴관리 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveRoleMn.do", produces="application/json; charset=utf-8")
	public String saveRoleMn(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveRoleMn(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 메뉴관리 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveRoleMnList.do", produces="application/json; charset=utf-8")
	public String saveRoleMnList(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveRoleMnList(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 메뉴관리 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/deleteMenu.do", produces="application/json; charset=utf-8")
	public String deleteMenu(@ModelAttribute("menu") Menu menu, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Menu> list = (List<Menu>)AbstractCntr.getJsonToList(params, Menu.class);
		CommonMessage message = systemService.deleteMenu(req, list);

		return JSONObject.fromObject(message).toString();
	}

	@ResponseBody
	@RequestMapping(value="/system/saveNoteInfo.do", produces="application/json; charset=utf-8")
    public ResponseEntity<String> saveNoteInfo(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		TaskNote vo = (TaskNote)AbstractCntr.getJsonToBean(paramJson, TaskNote.class);

        CommonMessage message = systemService.saveNoteInfo(req, res, vo);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
        return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
    }
	
	@ResponseBody
	@RequestMapping(value="/system/saveNoteFileInfo.do", produces="application/json; charset=utf-8")
    public ResponseEntity<String> saveNoteFileInfo(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		TaskNote vo = (TaskNote)AbstractCntr.getJsonToBean(paramJson, TaskNote.class);

        CommonMessage message = systemService.saveNoteFileInfo(req, res, vo);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
        return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
    }

	@ResponseBody
	@RequestMapping(value="/system/selectNoteInfo.do", produces="application/json; charset=utf-8")
	public String selectNoteInfo(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

		TaskNote vo = (TaskNote)AbstractCntr.getJsonToBean(paramJson, TaskNote.class);

		TaskNote noteInfo = systemService.selectNoteInfo(vo);

		JSONBaseVO jso = new JSONBaseVO();
		jso.put("data", noteInfo);
		return JSONObject.fromObject(jso).toString();
	}
	

	
	@RequestMapping(value="/system/roleMenuMain.do")
	public ModelAndView roleMenuMain(@ModelAttribute("menu") Menu menu, Model model, HttpServletRequest req) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		
		HttpSession session = req.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");
		menu.setLocale(userinfo.getLocale());
		
		List<Menu> menuList = systemService.roleMenuMain(menu);
		modelData.addObject("data",menuList);
		return modelData;
	}
	
	@RequestMapping(value="/system/roleMenuLeft.do")
	public ModelAndView roleMenuLeft(@ModelAttribute("menu") Menu menu, Model model, HttpServletRequest req) throws Exception{
		
	/*	Object obj=menu;
		for (Field field : obj.getClass().getDeclaredFields()){
		    field.setAccessible(true);
		    Object value=field.get(obj);
		    System.out.println(field.getName()+","+value);
		}*/
		
		HttpSession session = req.getSession();
		UserInfo userinfo = (UserInfo)session.getAttribute("sess_user_info");
		menu.setLocale(userinfo.getLocale());
		
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Menu> menuList = systemService.roleMenuLeft(menu);
		modelData.addObject("data",menuList);
		return modelData;
	}
	
	/**
	 * 롤 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/delRoleMn.do", produces="application/json; charset=utf-8")
	public String delRoleMn(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.delRoleMn(req, list);

		return JSONObject.fromObject(message).toString();
	}
	/**
	 * 롤사용자 삭제
	 */
	@ResponseBody
	@RequestMapping(value="/system/delRoleUser.do", produces="application/json; charset=utf-8")
	public String delRoleUser(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{

		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.delRoleUser(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 공통코드 페이지 호출
	 */
	@RequestMapping(value="/system/code.do")
	public String code(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/code";
	}
	
	/**
	 * 공통코드 대분류조회
	 */
	@RequestMapping(value="/system/selectLcdList.do")
	public ModelAndView selectLcdList(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectLcdList(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회
	 */
	@RequestMapping(value="/system/selectCdList.do")
	public ModelAndView selectCdList(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectCdList(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회
	 */
	@RequestMapping(value="/system/selectCdList2.do")
	public ModelAndView selectCdList2(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectCdList2(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회
	 */
	@RequestMapping(value="/system/selectCdList3.do")
	public ModelAndView selectCdList3(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectCdList3(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회
	 */
	@RequestMapping(value="/system/selectCdList4.do")
	public ModelAndView selectCdList4(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectCdList4(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회(리조트용)
	 */
	@RequestMapping(value="/system/selectCdList5.do")
	public ModelAndView selectCdList5(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectCdList5(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 소분류조회
	 */
	@RequestMapping(value="/system/selectEmailList.do")
	public ModelAndView selectEmailList(@ModelAttribute("code") Code code, Model model) throws Exception{
		ModelAndView modelData = new ModelAndView("jsonView");
		List<Code> codeList = systemService.selectEmailList(code);
		modelData.addObject("data",codeList);
		return modelData;
	}
	
	/**
	 * 공통코드 대분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveLcd.do", produces="application/json; charset=utf-8")
	public String saveLcd(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveLcd(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 공통코드 대분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/delLcd.do", produces="application/json; charset=utf-8")
	public String delLcd(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.delLcd(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 공통코드 대분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/delLcd2.do", produces="application/json; charset=utf-8")
	public String delLcd2(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.delLcd2(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	
	/**
	 * 공통코드 소분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveCd.do", produces="application/json; charset=utf-8")
	public String saveCd(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveCd(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 공통코드 소분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveCd2.do", produces="application/json; charset=utf-8")
	public String saveCd2(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveCd2(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 공통코드 대분류 등록, 수정
	 */
	@ResponseBody
	@RequestMapping(value="/system/delCd.do", produces="application/json; charset=utf-8")
	public String delCd(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{



		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.delCd(req, list);

		return JSONObject.fromObject(message).toString();
	}

	/**
	 * 링크 관리 페이지
	 */
	@RequestMapping(value="/system/linkMgmt.do", produces="application/json; charset=utf-8")
	public String linkMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/linkMgmt";
	}
	
	//링크관리 리스트 
	@RequestMapping(value="/system/selectLinkList.do" , method = RequestMethod.POST)
	public ModelAndView selectLinkList(@ModelAttribute("Link") Link link, Model model) throws Exception{

		List<Link> linkCodeList = systemService.selectLinkList(link);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",linkCodeList);
		return modelData;
	}
	
	//링크관리 등록
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/system/saveLink.do", produces="application/json; charset=utf-8")
	public String saveLink(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<Link> list = (List<Link>)AbstractCntr.getJsonToList(params, Link.class);

		CommonMessage message = systemService.saveLink(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	//링크관리 삭제
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/system/deleteLink.do", produces="application/json; charset=utf-8")
	public String deleteLink(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{

		List<Link> list = (List<Link>)AbstractCntr.getJsonToList(params, Link.class);

		CommonMessage message = systemService.deleteLink(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 담당자 페이지 호출(근태)
	 */
	@RequestMapping(value="/system/manager.do")
	public String manager() throws Exception{

		return "system/manager";
	}
	
	/**
	 * 담당자 페이지 호출(업무지원시스템)
	 */
	@RequestMapping(value="/system/manager2.do")
	public String manager2() throws Exception{
		
		return "system/manager2";
	}
	
	@RequestMapping(value="/system/approvalMgmt.do")
	public String approvalMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/approvalMgmt";
	}
	
	/**
	 * 롤 타스크 저장
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveRoleTask.do", produces="application/json; charset=utf-8")
	public String saveRoleTask(@ModelAttribute("role") Role role, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Role> list = (List<Role>)AbstractCntr.getJsonToList(params, Role.class);
		CommonMessage message = systemService.saveRoleTask(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 가이드 관리 페이지
	 */
	@RequestMapping(value="/system/guideMgmt.do")
	public String guideMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/guideMgmt";
	}
	
	//가이드 관리 리스트 
	@RequestMapping(value="/system/selectGuideList.do" , method = RequestMethod.POST)
	public ModelAndView selectGuideList(@ModelAttribute("Guide") Guide guide, Model model) throws Exception{

		List<Guide> linkCodeList = systemService.selectGuideList(guide);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",linkCodeList);
		return modelData;
	}
	
	//가이드 관리 저장
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/system/saveGuide.do", produces="application/json; charset=utf-8")
	public String saveGuide(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{
		
		List<Guide> list = (List<Guide>)AbstractCntr.getJsonToList(params, Guide.class);

		CommonMessage message = systemService.saveGuide(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	//가이드 관리 삭제
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/system/deleteGuide.do", produces="application/json; charset=utf-8")
	public String deleteGuide(HttpServletRequest req,
			@RequestParam(value="params", required=true) String params)throws Exception{
		
		List<Guide> list = (List<Guide>)AbstractCntr.getJsonToList(params, Guide.class);

		CommonMessage message = systemService.deleteGuide(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	//가이드 관리 파일리스트
	@RequestMapping(value="/system/selectGuideFile.do" , method = RequestMethod.POST)
	public ModelAndView selectGuideFile(@ModelAttribute("Guide") Guide guide, Model model) throws Exception{

		List<Guide> fileList = systemService.selectGuideFile(guide);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",fileList);
		return modelData;
	}
	
	//가이드 관리 파일 저장
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/system/saveGuideFile.do", produces="application/json; charset=utf-8")
	public ResponseEntity<String> saveGuideFile(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{
		
		Guide vo = (Guide)AbstractCntr.getJsonToBean(paramJson, Guide.class);

		CommonMessage message = systemService.saveGuideFile(req, res, vo);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	//가이드 관리 파일 삭제
	@RequestMapping(value="/system/deleteGuideFile.do", produces="application/json; charset=utf-8")
    public ModelAndView deleteGuideFile(HttpServletRequest req, HttpServletResponse res,
            @RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
        //조회조건 설정
		Guide vo = (Guide)AbstractCntr.getJsonToBean(paramJson, Guide.class);
        //검색
        CommonMessage message = systemService.deleteGuideFile(req, vo);

        ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",message);
		return modelData;
    }

	//신청서관리
	@RequestMapping(value="/system/applicationMgmt.do")
	public String applicationMgmt(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/applicationMgmt";
	}
	
	//신청서관리 리스트
	@RequestMapping(value="/system/selectApplicationList.do" , produces="application/json; charset=utf-8")
	public ModelAndView selectApplicationList(@RequestParam(value="paramJson", required=true) String paramJson, Model model) throws Exception{

		Application vo = (Application)AbstractCntr.getJsonToBean(paramJson, Application.class);
		
		List<Application> applList = systemService.selectApplicationList(vo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",applList);
		return modelData;
	}
	
	@RequestMapping(value="/system/applAddPopup.do")
	public String applAddPopup(@RequestParam String menuId, @RequestParam String menuType,@RequestParam String docNo, @ModelAttribute("Code") Code code, Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		model.addAttribute("docNo", docNo);
		return "system/applicationAddPopup";
	}

	@ResponseBody
	@RequestMapping(value="/system/chkWorkCode.do", produces="application/json; charset=utf-8")
	public String chkWorkCode(HttpServletRequest req, @RequestParam(value="paramJson", required=true) String paramJson
			,@ModelAttribute("Application") Application application)throws Exception{

		Application vo = (Application)AbstractCntr.getJsonToBean(paramJson, Application.class);

		CommonMessage message = systemService.chkWorkCode(req, vo);
		return JSONObject.fromObject(message).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/system/saveApplication.do", produces="application/json; charset=utf-8")
	public ResponseEntity<String> saveApplication(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		Application vo = (Application)AbstractCntr.getJsonToBean(paramJson, Application.class);

		CommonMessage message = systemService.saveApplication(req, res, vo);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=UTF-8");
		return new ResponseEntity<String>(JSONObject.fromObject(message).toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	@ResponseBody
	@RequestMapping(value="/system/popApplicationDetail.do", produces="application/json; charset=utf-8")
	public ModelAndView popApplicationDetail( HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception {

		Application applicationVo = (Application)AbstractCntr.getJsonToBean(paramJson, Application.class);
		List<Application> calDetailList = systemService.popApplicationDetail(applicationVo);
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",calDetailList);
		return modelData;
	}
	
	@ResponseBody
	@RequestMapping(value="/system/deleteApplication.do", produces="application/json; charset=utf-8")
	public String deleteApplication(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params , Model model)throws Exception{

		List<Application> list = (List<Application>)AbstractCntr.getJsonToList(params, Application.class);
		CommonMessage message = systemService.deleteApplication(req, list);

		return JSONObject.fromObject(message).toString();
	}
	
	//로그인페이지 관리자 정보
	@RequestMapping(value="/system/adminInfo.do")
	public String adminTel(@RequestParam String menuId, @RequestParam String menuType , Model model) throws Exception{
		model.addAttribute("menuId", menuId);
		model.addAttribute("menuType", menuType);
		return "system/adminInfo";
	}
	
	//로그인페이지 관리자 정보 저장
	@ResponseBody
	@RequestMapping(value="/system/saveAdminInfo.do", produces="application/json; charset=utf-8")
	public String saveAdminInfo(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson )throws Exception{

		TaskNote taskNote = (TaskNote)AbstractCntr.getJsonToBean(paramJson, TaskNote.class);
		CommonMessage message = systemService.saveAdminInfo(req, taskNote);

		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 리조트목록 리조트명 저장
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveLcd2.do", produces="application/json; charset=utf-8")
	public String saveLcd2(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveLcd2(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	/**
	 * 리조트목록 리조트명 저장
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveResortCd.do", produces="application/json; charset=utf-8")
	public String saveResortCd(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveResortCd(req, list);
		return JSONObject.fromObject(message).toString();
	}
	/**
	 * 리조트사용가능일수 저장
	 */
	@ResponseBody
	@RequestMapping(value="/system/saveResortCd2.do", produces="application/json; charset=utf-8")
	public String saveResortCd2(@ModelAttribute("code") Code code, HttpServletRequest req
			,@RequestParam(value="params", required=true) String params )throws Exception{
		List<Code> list = (List<Code>)AbstractCntr.getJsonToList(params, Code.class);
		CommonMessage message = systemService.saveResortCd2(req, list);
		return JSONObject.fromObject(message).toString();
	}
	
	// 메인창 주요안내 팝업헤더
 	@RequestMapping(value="/system/openCommonPopup.do", method = RequestMethod.GET)
 	public ModelAndView openCommonPopup(@ModelAttribute("Guide") Guide guide, Model model) throws Exception{
 		
 		//팝업 헤더
 		List<Guide> header = systemService.selectPopupHeader(guide);
 		
 		ModelAndView modelData = new ModelAndView("jsonView");
 		modelData.addObject("header",header);
 		
 		return modelData;
 	}
 	
 	// 메인창 주요안내 팝업 왼쪽메뉴
  	@RequestMapping(value="/system/doSelectPopupleftMenu.do")
  	public ModelAndView doSelectPopupleftMenu(@ModelAttribute("Guide") Guide guide, Model model) throws Exception{

  		List<Guide> leftMenu = systemService.selectPopupleftMenu(guide);
  		
  		ModelAndView modelData = new ModelAndView("jsonView");
  		modelData.addObject("leftMenu",leftMenu);
  		
  		return modelData;
  	}
  	
  	// 메인창 주요안내 팝업 내용
   	@RequestMapping(value="/system/doSelectPopupContents.do")
   	public ModelAndView doSelectPopupContents(@ModelAttribute("Guide") Guide guide, Model model) throws Exception{

   		List<Guide> contents = systemService.selectPopupContents(guide);
   		
   		ModelAndView modelData = new ModelAndView("jsonView");
   		modelData.addObject("contents",contents);
   		
   		return modelData;
   	}
  	
   	
   	// 첨부파일 관리 조회
   	@RequestMapping(value="/system/doSearchCommonFileMgmt.do")
   	public ModelAndView doSearchCommonFileMgmt(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

   		FileInfo vo = (FileInfo)AbstractCntr.getJsonToBean(paramJson, FileInfo.class);
   		
   		FileInfo fileInfo = systemService.selectCommonFileMgmt(vo);
   		
   		ModelAndView modelData = new ModelAndView("jsonView");
   		modelData.addObject("data",fileInfo);
   		
   		return modelData;
   	}
 // 첨부파일 관리 저장
   	@ResponseBody
	@RequestMapping(value="/system/doSaveCommonFileMgmt.do", produces="application/json; charset=utf-8")
	public String doSaveCommonFileMgmt(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson )throws Exception{

		FileInfo vo = (FileInfo)AbstractCntr.getJsonToBean(paramJson, FileInfo.class);
		CommonMessage message = systemService.saveCommonFileMgmt(req, vo);

		return JSONObject.fromObject(message).toString();
	}
	
   	
   	@RequestMapping(value="/system/doSelectReferMgmtList.do")
	public ModelAndView doSelectReferMgmtList(HttpServletRequest req,
			@RequestParam(value="paramJson", required=true) String paramJson)throws Exception{

		Approval vo = (Approval)AbstractCntr.getJsonToBean(paramJson, Approval.class);
		List<Approval> list = systemService.selectReferMgmtList(vo);	
		
		ModelAndView modelData = new ModelAndView("jsonView");
		modelData.addObject("data",list);
		return modelData;
	}
  	
   	@ResponseBody
	@RequestMapping(value="/system/insertApprDocList.do", produces="application/json; charset=utf-8")
	public String insertApprDocList(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params , Model model)throws Exception{

		List<Approval> list = (List<Approval>)AbstractCntr.getJsonToList(params, Approval.class);
		CommonMessage message = systemService.insertApprDocList(req, list);

		return JSONObject.fromObject(message).toString();
	}
   	
   	@ResponseBody
	@RequestMapping(value="/system/deleteApprDocList.do", produces="application/json; charset=utf-8")
	public String deleteApprDocList(HttpServletRequest req
			,@RequestParam(value="params", required=true) String params , Model model)throws Exception{

		List<Approval> list = (List<Approval>)AbstractCntr.getJsonToList(params, Approval.class);
		CommonMessage message = systemService.deleteApprDocList(req, list);

		return JSONObject.fromObject(message).toString();
	}
}
