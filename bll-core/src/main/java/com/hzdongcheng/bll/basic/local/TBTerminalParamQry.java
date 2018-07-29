package com.hzdongcheng.bll.basic.local;

import com.hzdongcheng.bll.AbstractLocalBusiness;
import com.hzdongcheng.bll.DBSContext;
import com.hzdongcheng.bll.basic.dto.InParamTBTerminalParamQry;
import com.hzdongcheng.bll.basic.dto.OutParamTBTerminalParamQry;
import com.hzdongcheng.bll.common.IRequest;
import com.hzdongcheng.bll.constant.DBSErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;
import com.hzdongcheng.components.toolkits.utils.NumberUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

/**
 * 柜体设备参数配置查询
 * 1、ScreensoundFlag  语音提示开关
 * 2、RefuseCloseDoor  拒关箱门次数
 * 3、ArticleInspectFlag 物品检测开关
 * 4、DoorInspectFlag  箱门检测开关
 * 5、PowerInspectFlag 电源检测开关
 * 6、ShockInspectFlag 震动检测开关
 * 7、ExistSuperLargeBox 是否存在超大箱
 * 8、ExistFreshBox  是否存在生鲜箱
 */
public class TBTerminalParamQry extends AbstractLocalBusiness {
    public OutParamTBTerminalParamQry doBusiness(
            InParamTBTerminalParamQry inParam) throws DcdzSystemException {
        OutParamTBTerminalParamQry result;
        result = callMethod(inParam);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected OutParamTBTerminalParamQry handleBusiness(IRequest request) throws DcdzSystemException {
        InParamTBTerminalParamQry inParam = (InParamTBTerminalParamQry) request;
        OutParamTBTerminalParamQry result = new OutParamTBTerminalParamQry();

        // 1. 验证输入参数是否有效，如果无效返回-1。
        if (StringUtils.isEmpty(inParam.OperID))
            throw new DcdzSystemException(DBSErrorCode.ERR_SYSTEM_PARAMTER);


        result.TerminalNo = DBSContext.terminalUid;
        result.ScreensoundFlag = DBSContext.ctrlParam.screensoundFlag;
        result.RefuseCloseDoor = NumberUtils.parseInt(DBSContext.ctrlParam.refuseCloseDoor);
        result.ArticleInspectFlag = DBSContext.ctrlParam.articleInspectFlag;
        result.DoorInspectFlag = DBSContext.ctrlParam.doorInspectFlag;
        result.PowerInspectFlag = DBSContext.ctrlParam.powerInspectFlag;
        result.ShockInspectFlag = DBSContext.ctrlParam.shockInspectFlag;
        result.ExistSuperLargeBox = DBSContext.ctrlParam.existSuperLargeBox;
        result.ExistFreshBox = DBSContext.ctrlParam.existSameTypeBox;
        result.LampOffTime = DBSContext.ctrlParam.lampOffTime;
        result.LampOnTime = DBSContext.ctrlParam.lampOnTime;

        return result;
    }
}
