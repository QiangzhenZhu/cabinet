package com.hzdongcheng.bll.constant;

import com.hzdongcheng.components.toolkits.exception.error.ErrorCode;
import com.hzdongcheng.components.toolkits.exception.error.ErrorTitle;

/**
 * Created by Peace on 2017/9/14.
 * Modify by zxy on 2017/9/18
 */

public final class DBSErrorCode extends ErrorCode {
    public static int SUCCESS = 0;

    // Serial port error define
    public final static int ERR_DRIVERS_ALREADYOPENED = 10000;//"10000"; // Load
    // library
    // DcdzAlxDriversSerialPort4L
    // failed.
    public final static int ERR_DRIVERS_NOTFOUNDLIBRARY = 10001;//"10001"; // Load
    // library
    // DcdzAlxDriversSerialPort4L
    // failed.
    public final static int ERR_DRIVERS_OPENFAILED = 10002;//"10002"; // Open serial
    // port
    // failed.
    public final static int ERR_DRIVERS_INITFAILED = 11003;//"11003"; // 初始化端口失败
    public final static int ERR_DRIVERS_SETQUEUEFAILED = 11004;//"11004"; // 设置串口输入输出缓冲区大小失败
    public final static int ERR_DRIVERS_SETRWTIMEOUTSFAILED = 11005;//"11005"; // 设置串口读写超时时间失败
    public final static int ERR_DRIVERS_OPENEVENTMODEFAILED = 11006;//"11006"; // 开启串口事件模式失败
    public final static int ERR_DRIVERS_SERIALEXCEPTION = 10007;//"10007"; // 串口异常
    public final static int ERR_DRIVERS_SENDDATAFAILED = 10008;//"10008"; // 串口异常,发送数据失败
    public final static int ERR_DRIVERS_SENDDATATIMEOUTS = 10009;//"10009";// 数据发送超时，请检查端口连接或硬件可能存在故障
    public final static int ERR_DRIVERS_RECVDATATIMEOUTS = 10010;//"10010";// 数据接收超时，请检查端口连接或硬件可能存在故障
    public final static int ERR_DRIVERS_OUTOFMEMORY = 10011;//"10011"; // 内存不足
    public final static int ERR_DRIVERS_CLEARFAILED = 10012;//"10012"; // 清理串口缓冲区失败
    public final static int ERR_DRIVERS_CLEARERRORFAILED = 10013;//"10013"; // 清理串口错误失败
    // protocol error define
    public final static int ERR_DRIVERS_FRAMEFORMATERROR = 11000;//"11000"; // 数据帧格式错误，硬件可能存在故障
    public final static int ERR_DRIVERS_EXCUTEFAILED = 11001;//"11001"; // 命令执行错误，硬件可能存在故障
    public final static int ERR_DRIVERS_SLEEPFILED = 11002;//"11002"; // 线程中断异常

    // card reader error define
    public final static int ERR_DRIVERS_READCARDFAILED = 12000;//"12000"; // 读取卡号失败

    // scanner error define
    public final static int ERR_DRIVERS_SEARCHVERSIONFAILED = 13000;//"13000"; // 获取扫描枪版本号失败
    public final static int ERR_DRIVERS_SWITCHMODEFAILED = 13001;//"13001"; // 切换扫描枪模式失败
    public final static int ERR_DRIVERS_STARTSCANFAILED = 13002;//"13002"; // 触发扫码失败
    public final static int ERR_DRIVERS_STOPSCANFAILED = 13003;//"13003"; // 停止扫码失败

    // net work error define
    public final static int ERR_NETWORK_INITIALIZERFAILED = 15000;//"15000"; // 初始化网络组件失败
    public final static int ERR_NETWORK_ILLEGALARGUMENT = 15003;//"15003"; // 错误的参数
    public final static int ERR_NETWORK_REQUESTFAILED = 15004;//"15004";   // 发送网络请求失败
    public final static int ERR_NETWORK_NOTWSSUPPORED = 15005;//"15005";   // ONLY WS(S) protocol supported
    public final static int ERR_NETWORK_NETWORKDISCONNECTED = 15006;//"15006"; //network is disconneted
    public final static int ERR_NETWORK_CANNOTCONNECTSERVER = 15007;//"15007"; //can not connect server
    // / <summary>
    // / S100-S199 系统级错误
    // / </summary>
    public final static int ERR_SYSTEM_RUNTIME = 100;//"100"; // java标准异常
    public final static int ERR_SYSTEM_PARAMTER = 101;//"S101"; // 输入数据不全
    public final static int ERR_SYSTEM_NOINITDEVICE = 102;//"S102"; // 设备没有初始化,请添加柜体信息
    public final static int ERR_SYSTEM_TSTATUS_ABNORMAL = 103;//"S103"; // 设备状态不正常
    public final static int ERR_SYSTEM_NOTEXISTSERVICE = 104;//"S104"; // 推送服务不存在
    public final static int ERR_SYSTEM_WRONGDATAFORMAT = 105;//"S105"; // 错误的数据格式
    // system error define
    public final static int ERR_SYSTEM_OPENFILEERROR = 106;//打开文件失败
    public final static int ERR_SYSTEM_UNSUPPORTEDENCODING = 107;//编码格式不支持

    // / <summary>
    // / R300-R399 驱动板错误
    // / </summary>
    public final static int ERR_DRIVER_BUSY = 300;//"R300";// 繁忙
    public final static int ERR_DRIVER_OPEN = 301;//"R301";// 驱动板打开失败
    public final static int ERR_DRIVER_BOXOVER = 302;//"R302";// 箱门数量超出32
    public final static int ERR_DRIVER_NOFIND = 303;//"R303";// 未找到指定箱门
    public final static int ERR_DRIVER_NOFREE = 304;//"R304";// 未找到可用箱门
    public final static int ERR_DRIVER_OPENBOXFAILD = 305;//"R305";// 开箱失败
    public final static int ERR_DRIVER_PRINTFAULT = 310;//"R310"; // 打印机故障

    // / <summary>
    // / N500-N599 网络错误
    // / </summary>
    public final static int ERR_NETWORK_NETWORKLAYER = 500;//"N500"; // 网络层错误
    public final static int ERR_NETWORK_OPENLINK = 501;//"N501"; // 网络连接失败
    public final static int ERR_NETWORK_CONF = 502;//"The network not configured");
    public final static int ERR_NETWORK_DISCONNECT = 503;//"The network not connected");
    public final static int ERR_NETWORK_SENDMSGFAIL = 505;//"N505"; // 发送消息失败
    public final static int ERR_NETWORK_SENDMSGTIMEOUT = 507;//"N507"; // 发送消息超时


    // / <summary>
    // / D700-D799 数据库错误
    // / </summary>
    public final static int ERR_DATABASE_DATABASELAYER = 700;//"D700"; // 数据持久层错误,请联系系统管理员
    public final static int ERR_DATABASE_NORECORD = 701;//"D701"; // 数据库记录不存在
    public final static int ERR_DATABASE_NVLCONNECTION = 702;//"D702"; // 获取数据库连接失败

    // / <summary>
    // /110000-199999 业务层错误
    // / </summary>
    public final static int ERR_BUSINESS_ERROR = 110000;//业务层错误
    public final static int ERR_BUSINESS_UNSUPPORT = 110001;//业务不支持
    public final static int ERR_BUSINESS_TERMINALNOEMPTY = 120001;//"B20001"; // 柜号不能为空
    public final static int ERR_BUSINESS_MODTERMINANO4REGISTER = 120002;//"B20002"; // 注册未通过，请修改柜号
    public final static int ERR_BUSINESS_DESKNOTALLOWEDEMPTY = 120003;//"B20003"; // 副柜不能为空，请添加副柜
    public final static int ERR_BUSINESS_NETPARAMNOTALLOWEDEMPTY = 120004;//"B20004"; // 网络参数不能为空
    public final static int ERR_BUSINESS_TERMINALNOTSAVED = 120005;//"B20005"; // 柜体数据还未保存
    public final static int ERR_BUSINESS_TERMINALNAMEEMPTY = 120007;//"B20007"; // 设备名称不能为空
    public final static int ERR_BUSINESS_TERMINALLOCATIONEMPTY = 120008;//"B20008"; // 安装地址不能为空
    public final static int ERR_BUSINESS_TDJHFORMATWRONG = 120009;//"B20009"; // 投递局号格式错误
    public final static int ERR_BUSINESS_TDJHNOTEMPTY = 120010;//"B20010"; // 投递局号不能为空

    public final static int ERR_BUSINESS_WRONGPWD = 133001;//"B33001"; // 用户名或密码错误
    public final static int ERR_BUSINESS_PACKDELIVERYD = 133002;//"B33002"; // 包裹已经做过投递
    public final static int ERR_BUSINESS_PACKNOTEXIST = 133003;//"B33003"; // 包裹不存在
    public final static int ERR_BUSINESS_NOFREEDBOX = 133004;//"B33004"; // 未找到可用箱门
    public final static int ERR_BUSINESS_PACKAGELOCKED = 133005;//"B33005"; // 订单已经锁定
    public final static int ERR_BUSINESS_PACKSTATUSABNORMAL = 133006;//"B33006"; // 订单状态不正常
    public final static int ERR_BUSINESS_BOXHAVEUSED = 133007;//"B33007"; // 箱门已经在使用
    public final static int ERR_BUSINESS_FORBIDNOTORDERLIST = 133008;//"B33008"; // 订单外列表不允许投递
    public final static int ERR_BUSINESS_FORBIDWITHDRAW = 133009;//"B33009"; // 订单不在回收列表中
    public final static int ERR_BUSINESS_WRONGCODE = 133011;//"B33011"; // 校验码错误

    public final static int ERR_BUSINESS_WRONGPACKAGID = 133021;//"B33021"; // 包裹单号或提货码错误
    public final static int ERR_BUSINESS_WRONGMOBILE = 133022;//"B33022"; // 手机号或提货码错误
    public final static int ERR_BUSINESS_WRONGCUSTOMERID = 133023;//"B33023"; // 用户卡号或提货码错误
    public final static int ERR_BUSINESS_WRONGPACKMOBILE = 133024;//"B33024"; // 包裹单号(手机号)或提货码错误
    public final static int ERR_BUSINESS_WRONGPICKPASSWD = 133025;//"B33025"; // 提货密码错误
    public final static int ERR_BUSINESS_WRONGMOBILEPHONE = 133026;//"B33026"; // 收件人手机号错误
    public final static int ERR_BUSINESS_MOBILEFORMATERROR = 133027;//"UIVM1028"; //
    public final static int ERR_BUSINESS_LOCK1MIN = 133031;//"B33031"; // 输错次数过多，暂时锁定1分钟
    public final static int ERR_BUSINESS_LOCK5MIN = 133032;//"B33032"; // 输错次数过多，暂时锁定5分钟
    public final static int ERR_BUSINESS_LOCK30MIN = 133033;//"B33033"; // 输错次数过多，暂时锁定30分钟
    public final static int ERR_BUSINESS_NOTEXISTOPENBOXRECORD = 150001;//"B50001"; // 开箱记录不存在
    public final static int ERR_BUSINESS_NOTEXISTRECOVERYRECORD = 150002;//"B50002"; // 回收记录不存在
    public final static int ERR_BUSINESS_NOTEXISTDELIVERYRECORD = 150003;//"B50003"; // 取件记录不存在
    public final static int ERR_BUSINESS_WRONGOPENBOXKEY = 150004;//"B50004"; // 开箱密码不正确
    public final static int ERR_BUSINESS_OPENBOXTIMEOUT = 150005;//"B50005"; // 开箱超过规定的时间
    public final static int ERR_BUSINESS_OPENBOXRECORDNOTNEWEST = 150011;//"B50011"; // 开箱记录已经不是最新
    public final static int ERR_BUSINESS_RECOVERYRECORDNOTNEWEST = 150012;//"B50012"; // 回收记录已经不是最新
    public final static int ERR_BUSINESS_DELIVERYRECORDNOTNEWEST = 150013;//"B50013"; // 取件记录已经不是最新
//    public final static int ERR_BUSINESS_NOEMERGENCYBOX = 0;//"No emergency box.");
//    public final static int ERR_BUSINESS_EMERGENCYBOXISALLUSED = 0;//"All emergency box have been used.");
//    public final static int ERR_BUSINESS_BOXNOSET = 0;//"The terminal no set boxes");

    static {
        ErrorTitle.putErrorTitle(SUCCESS, "Execute successfully.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_ERROR, "Business error.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_UNSUPPORT, "Unsupported function");
//        ErrorTitle.putErrorTitle(ERR_BUSINESS_NOEMERGENCYBOX, "No emergency box.");
//        ErrorTitle.putErrorTitle(ERR_BUSINESS_EMERGENCYBOXISALLUSED, "All emergency box have been used.");
//        ErrorTitle.putErrorTitle(ERR_BUSINESS_BOXNOSET, "The terminal no set boxes");
        ErrorTitle.putErrorTitle(ERR_NETWORK_CONF, "The network not configured");
        ErrorTitle.putErrorTitle(ERR_NETWORK_DISCONNECT, "The network not connected");
        ErrorTitle.putErrorTitle(ERR_SYSTEM_OPENFILEERROR, "Open file error.");
        ErrorTitle.putErrorTitle(ERR_SYSTEM_UNSUPPORTEDENCODING, "Unsupported Encoding.");
        ErrorTitle.putErrorTitle(ERR_DATABASE_DATABASELAYER, "Database connection error.");
        ErrorTitle.putErrorTitle(ERR_SYSTEM_PARAMTER, "System paramter error.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_BOXHAVEUSED, "Box have used.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_NOFREEDBOX, "No freed box.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_NOTEXISTOPENBOXRECORD, "There is no open box record.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_PACKDELIVERYD, "The package was taken.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_PACKNOTEXIST, "The package not exist.");
        ErrorTitle.putErrorTitle(ERR_SYSTEM_TSTATUS_ABNORMAL, "Terminal status abnormal");
        ErrorTitle.putErrorTitle(ERR_SYSTEM_NOINITDEVICE, "No init device");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_WRONGPWD, "Username or Password error.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_DESKNOTALLOWEDEMPTY, "Desk not allow empty.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_TERMINALNOTSAVED, "Terminal info not saved.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_MOBILEFORMATERROR, "Mobile format error.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_PACKAGELOCKED, "Package locked.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_WRONGCODE, "校验码错误.");
        ErrorTitle.putErrorTitle(ERR_BUSINESS_WRONGOPENBOXKEY, "取件码错误.");
    }
}
