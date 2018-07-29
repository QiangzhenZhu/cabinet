package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.basic.dto.InParamSCSyncLocalTime;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.util.Date;

public class SCSyncLocalTime extends AbstractLocalBusiness {
	public String doBusiness(InParamSCSyncLocalTime inParam) throws DcdzSystemException {
		String result;
		result = callMethod(inParam);
		return result;
	}

	@Override
	protected String handleBusiness(IRequest request) {
		InParamSCSyncLocalTime inParam = (InParamSCSyncLocalTime) request;
		String result = "";

		if (DateUtils.isNotNull(inParam.ServerTime)) {
			Date nowDateTime = DateUtils.nowDate();
			// if (nowDateTime.Year == inParam.ServerTime.Year &&
			// nowDateTime.Month == inParam.ServerTime.Month &&
			// nowDateTime.Day == inParam.ServerTime.Day &&
			// nowDateTime.Hour == inParam.ServerTime.Hour)
			if (inParam.ServerTime.getYear() > 2013) {
				// SYSTEMTIME st = new SYSTEMTIME();
				// st.FromDateTime(inParam.ServerTime);
				// SetLocalTime(ref st);
			}
		}

		return result;
	}

}
