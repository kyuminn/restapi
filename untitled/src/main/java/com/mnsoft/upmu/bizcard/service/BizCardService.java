package com.mnsoft.upmu.bizcard.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mnsoft.upmu.bizcard.vo.BizCard;
import com.mnsoft.upmu.common.util.CommonMessage;
import com.mnsoft.upmu.common.vo.UserInfo;
import com.mnsoft.upmu.system.vo.Code;

public interface BizCardService {
	public CommonMessage saveBizCard(HttpServletRequest req, List<BizCard> bizCard)throws Exception;
	public List<UserInfo> userInfo(UserInfo userInfo)throws Exception;
	public List<BizCard> selectBizCardList(BizCard bizCard)throws Exception;
	public List<BizCard> bizCardInfo(BizCard bizCard)throws Exception;
	public CommonMessage saveCdEmail(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage saveAddr(HttpServletRequest req, List<Code> list)throws Exception;
	public CommonMessage saveCnt(HttpServletRequest req, List<Code> list)throws Exception;	
	public CommonMessage sendMail(List<BizCard> list, HttpServletRequest req) throws Exception;
	public List<BizCard> bizCardInfoToday(BizCard bizCard)throws Exception;

}
