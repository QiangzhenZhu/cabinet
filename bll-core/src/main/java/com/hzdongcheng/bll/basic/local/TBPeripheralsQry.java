package com.hzdongcheng.bll.basic.local;
import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBPeripheralsQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBPeripheralsQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.ControlParam;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

/**
 * 外设配置查询
 */
public class TBPeripheralsQry extends AbstractLocalBusiness {
	public OutParamTBPeripheralsQry doBusiness(InParamTBPeripheralsQry inParam)
			throws DcdzSystemException {
		OutParamTBPeripheralsQry result;
		result = callMethod(inParam);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected OutParamTBPeripheralsQry handleBusiness(IRequest request)
			throws DcdzSystemException {
		InParamTBPeripheralsQry inParam = (InParamTBPeripheralsQry) request;
		OutParamTBPeripheralsQry result = new OutParamTBPeripheralsQry();

		// 1. 验证输入参数是否有效，如果无效返回-1。
		if (StringUtils.isEmpty(inParam.OperID))
			throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);

		ControlParam memParam = DBSContext.ctrlParam;

		result.DeviceBoardPort = memParam.deviceBoardPort;
		result.CardReaderPort = memParam.cardReaderPort;
		result.CardReaderNeedFlag = memParam.cardReaderNeedFlag;
		result.PrintPort = memParam.printPort;
		result.PrintNeedFlag = memParam.printNeedFlag;
		result.BarCodePort = memParam.barCodePort;
		result.BarNeedFlag = memParam.barNeedFlag;
		result.ShortMsgPort = memParam.shortMsgPort;
		result.ShortMsgNeedFlag = memParam.shortMsgNeedFlag;
		result.LedScreenPort = memParam.ledScreenPort;
		result.LedScreenNeedFlag = memParam.ledSreenNeedFlag;
		result.PosPort = memParam.posPort;
		result.PosNeedFlag = memParam.posNeedFlag;
		result.CoinPort = memParam.coinPort;
		result.CoinNeedFlag = memParam.coinNeedFlag;
		result.BankCardPort = memParam.bankCardPort;
		result.BankCardNeedFlag = memParam.bankCardNeedFlag;
		result.CameraPort = memParam.cameraPort;
		result.CameraNeedFlag = memParam.cameraNeedFlag;
		result.KeyboardNeedFlag = memParam.keyboardNeedFlag;
		result.KeyboardPort = memParam.keyboardPort;
		result.DvrPort = memParam.dvrPort;
		result.DvrNeedFlag = memParam.dvrNeedFlag;

		return result;
	}
}
