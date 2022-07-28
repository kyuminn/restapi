package com.mnsoft.upmu.system.holiday.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.vo.Approval;
import com.mnsoft.upmu.holiday.vo.Holiday;
import com.mnsoft.upmu.holiday.vo.HolidayCode;

public interface HolidayService {

	CommonMessage saveHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList);
	
	CommonMessage approveHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList);
	
	CommonMessage updateHolidayAppl(HttpServletRequest req, Holiday vo, List<Holiday> list, Approval apprVo, List<Approval> apprList);
	
	List<Holiday> selectHolidayList(Holiday vo);

	List<HolidayCode> selectHolidayCodeList(HolidayCode vo);

	CommonMessage saveHolidayCodeList(HttpServletRequest req, List<HolidayCode> list);

	List<HolidayCode> selectWorkgListForHolidayAppl(HolidayCode vo);

	Holiday selectHolidayApplInfo(Holiday vo);

	List<Holiday> selectHolidayApprList(Holiday vo);

	Holiday selectHoliDayType(Holiday vo);

	Holiday doSelectDiffDayExceptHoliday(Holiday vo);


}
