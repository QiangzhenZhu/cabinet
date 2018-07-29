package com.hzdongcheng.persistent.sequence;

/**
 * Created by Peace on 2016/12/20.
 */

public final class SQLScript {

    public final static String SQL1 = "PRAGMA foreign_keys = off;";
    public final static String SQL2 = "BEGIN TRANSACTION;";

    public final static String SQL3 = "DROP TABLE IF EXISTS OPOperatorLog;";
    public final static String SQL4 = "CREATE TABLE OPOperatorLog (OperLogID BIGINT PRIMARY KEY, FunctionID VARCHAR (8), OperID VARCHAR (64) NOT NULL COLLATE NOCASE, OperContent VARCHAR (4000) NOT NULL COLLATE NOCASE, OccurTime DATETIME NOT NULL COLLATE NOCASE, Remark VARCHAR (255) COLLATE NOCASE)";


    public final static String SQL5 = "DROP TABLE IF EXISTS PAControlParam;";
    public final static String SQL6 = "CREATE TABLE PAControlParam (CtrlTypeID VARCHAR (10) COLLATE NOCASE NOT NULL, CtrlTypeName VARCHAR (30) NOT NULL COLLATE NOCASE, KeyString VARCHAR (30) NOT NULL COLLATE NOCASE, DefaultValue VARCHAR (30) NOT NULL COLLATE NOCASE DEFAULT (0), LastModifyTime DATETIME NOT NULL COLLATE NOCASE, Remark VARCHAR (255) COLLATE NOCASE, PRIMARY KEY (CtrlTypeID ASC, KeyString ASC))";

    public final static String SQL7 = "DROP TABLE IF EXISTS PADictonary;";
    public final static String SQL8 = "CREATE TABLE PADictonary (DictTypeID INTEGER NOT NULL COLLATE NOCASE, DictTypeName VARCHAR (60) NOT NULL COLLATE NOCASE, DictID VARCHAR (6) COLLATE NOCASE NOT NULL, DictName VARCHAR (60) NOT NULL COLLATE NOCASE, OtherDictName VARCHAR (64) COLLATE NOCASE, Remark VARCHAR (255) COLLATE NOCASE, PRIMARY KEY (DictTypeID, DictID));";

    public final static String SQL9 = "DROP TABLE IF EXISTS PASequence;";
    public final static String SQL10 = "CREATE TABLE PASequence (SeqName VARCHAR (32) NOT NULL COLLATE NOCASE PRIMARY KEY, SeqValue INTEGER NOT NULL COLLATE NOCASE, CacheSize INTEGER NOT NULL COLLATE NOCASE DEFAULT (100), SeqMaxValue INTEGER NOT NULL COLLATE NOCASE DEFAULT (0));";
    public final static String SQL11 = "INSERT INTO PASequence (SeqName, SeqValue, CacheSize, SeqMaxValue) VALUES ('SeqOperLogID', 0, 100, 0);";
    public final static String SQL12 = "INSERT INTO PASequence (SeqName, SeqValue, CacheSize, SeqMaxValue) VALUES ('SeqWaterID', 0, 300, 0);";
    public final static String SQL13 = "INSERT INTO PASequence (SeqName, SeqValue, CacheSize, SeqMaxValue) VALUES ('SeqReportWaterID', 0, 100, 0);";
    public final static String SQL14 = "INSERT INTO PASequence (SeqName, SeqValue, CacheSize, SeqMaxValue) VALUES ('SeqTradeWaterID', 0, 100, 0);";

    public final static String SQL15 = "DROP TABLE IF EXISTS PTInBoxPackage;";
    public final static String SQL16 = "CREATE TABLE PTInBoxPackage (WayBillNo VARCHAR (64) NOT NULL COLLATE NOCASE PRIMARY KEY, PackageID VARCHAR (64),WaterNo VARCHAR (64), BoxName VARCHAR (32) NOT NULL COLLATE NOCASE, BillSize DOUBLE COLLATE NOCASE, Weight DOUBLE NOT NULL COLLATE NOCASE, OrigOrgCode VARCHAR (64) NOT NULL COLLATE NOCASE, DestOrgCode VARCHAR (64) NOT NULL COLLATE NOCASE, ScanTime DATETIME NOT NULL COLLATE NOCASE,ScanType INTEGER NOT NULL COLLATE NOCASE, CreateUserCode VARCHAR (64), CreateDeptCode VARCHAR (64), Remark VARCHAR (255),DestSiteCode VARCHAR (32));";

    public final static String SQL17 = "DROP TABLE IF EXISTS PTPackRoute;";
    public final static String SQL18 = "CREATE TABLE PTPackRoute (WayBillNo VARCHAR (64) COLLATE NOCASE NOT NULL PRIMARY KEY, BillSize DOUBLE COLLATE NOCASE, Weight DOUBLE NOT NULL COLLATE NOCASE, PathDetails TEXT NOT NULL COLLATE NOCASE, LastDateTime DATETIME NOT NULL COLLATE NOCASE, WaterNo VARCHAR (64) COLLATE NOCASE, Remark VARCHAR (255) COLLATE NOCASE);";

    public final static String SQL19 = "DROP TABLE IF EXISTS PTPacksHistory;";
    public final static String SQL20 = "CREATE TABLE PTPacksHistory (PackageNo VARCHAR (64) PRIMARY KEY NOT NULL COLLATE NOCASE, CreateDeptCode VARCHAR (64) NOT NULL COLLATE NOCASE, CreateDeptName VARCHAR (128) COLLATE NOCASE NOT NULL, CreateTime DATETIME NOT NULL COLLATE NOCASE, CreateUserCode VARCHAR (64) NOT NULL COLLATE NOCASE, CreateUserName VARCHAR (128) COLLATE NOCASE NOT NULL, BoxName VARCHAR (32) NOT NULL COLLATE NOCASE, OrigOrgCode VARCHAR (64) NOT NULL COLLATE NOCASE, DestOrgCode VARCHAR (64) NOT NULL, DestOrgName VARCHAR (128) COLLATE NOCASE NOT NULL, PkgType VARCHAR (32) NOT NULL COLLATE NOCASE, ScanType VARCHAR (32) NOT NULL, PackageNum INTEGER NOT NULL COLLATE NOCASE, PacksInfo TEXT NOT NULL COLLATE NOCASE, PackedFlag VARCHAR (2) NOT NULL, UploadTime DATETIME, UploadFlag VARCHAR (2) NOT NULL COLLATE NOCASE, LastModifyTime DATETIME COLLATE NOCASE NOT NULL, Remark VARCHAR (255),LabelDetail STRING);";
    public final static String SQL21 = "DROP TABLE IF EXISTS PTSortScheme;";
    public final static String SQL22 = "CREATE TABLE PTSortScheme (SchemeCode VARCHAR (64) COLLATE NOCASE NOT NULL, SchemeName VARCHAR (128) COLLATE NOCASE, OrigOrgCode VARCHAR (64) COLLATE NOCASE, OrigOrgName VARCHAR (128) COLLATE NOCASE, DeskNo VARCHAR (6) COLLATE NOCASE, BoxNo VARCHAR (6) COLLATE NOCASE NOT NULL, DestOrgCode VARCHAR (64) COLLATE NOCASE NOT NULL, DestOrgName VARCHAR (128) COLLATE NOCASE,DestSiteCode VARCHAR (128) COLLATE NOCASE, PrintName VARCHAR (128) COLLATE NOCASE, InCode VARCHAR (64) COLLATE NOCASE, IsPrintFlag VARCHAR (2) COLLATE NOCASE, PackAgeType VARCHAR (64) COLLATE NOCASE, Remark VARCHAR (255));";

    public final static String SQL23 = "DROP TABLE IF EXISTS TBBox;";
    public final static String SQL24 = "CREATE TABLE TBBox (DeskNo VARCHAR (6) NOT NULL COLLATE RTRIM, BoxNo VARCHAR (6), BoxName VARCHAR (30) NOT NULL COLLATE NOCASE, BoxStatus INTEGER NOT NULL COLLATE NOCASE);";

    public final static String SQL25 = "DROP TABLE IF EXISTS TBDesk;";
    public final static String SQL26 = "CREATE TABLE TBDesk (DeskNo VARCHAR (6) NOT NULL COLLATE NOCASE, BoxNum INTEGER NOT NULL COLLATE NOCASE, DeskInCode INTEGER NOT NULL COLLATE NOCASE DEFAULT (0), Remark VARCHAR (255) COLLATE NOCASE)";

    public final static String SQL27 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('10000', '管理权限', 'operID', 'admin', '2016-12-14 15:58:00', '终端管理员账号');";
    public final static String SQL28 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('10000', '管理权限', 'operPwd', 'A91B03FA760A1A8F06551CBBBD37D029', '2016-12-14 15:58:00', '管理员密码');";
    public final static String SQL29 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('10001', '驱动板端口', 'devicePort', 'COM1', '2016-12-14 15:58:00', '驱动板端口号');";
    public final static String SQL30 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣设置', 'currentSchemeCode', '0', '2016-12-14 15:58:00', '当前分拣方案编码');";
    public final static String SQL31 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣设置', 'currentSchemeName', '0', '2016-12-14 15:58:00', '当前分拣方案');";
    public final static String SQL32 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '路由保存时间', 'routeCacheTime', '2', '2016-12-14 15:58:00', 'l路由数据保存时间');";
    public final static String SQL33 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '同步时间间隔', 'syncRouteInterval', '0', '2016-12-14 15:58:00', '同步间隔');";
    public final static String SQL34 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '同步时间跨度', 'syncRouteSpan', '10', '2016-12-14 15:58:00', '同步时间跨度');";
    public final static String SQL35 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '同步开始时间', 'syncRouteTime', '0', '2016-12-14 15:58:00', '同步路由开始时间');";
    public final static String SQL36 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣设置', 'threshold', '30', '2016-12-14 15:58:00', '自动建包重量');";
    public final static String SQL37 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30001', '终端配置', 'serverIP', '0', '2016-12-14 15:58:00', '服务器IP地址');";
    public final static String SQL38 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30001', '终端配置', 'serverPort', '0', '2016-12-14 15:58:00', '服务器端口');";
    public final static String SQL39 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30002', '打印机设置', 'printerIP', '0', '2016-12-14 15:58:00', '打印机IP地址');";
    public final static String SQL40 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30002', '打印机设置', 'printerModel', '0', '2016-12-14 15:58:00', '打印机工作模式(0,网络打印印 1,1串口)');";
    public final static String SQL41 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30002', '打印机设置', 'printerPort', '453', '2016-12-14 15:58:00', '打印机网络端口');";
    public final static String SQL42 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30002', '打印机设置', 'printerSerialPort', 'COM2', '2016-12-14 15:58:00', '串口打印机端口');";
    public final static String SQL43 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30003', '扫描枪设置', 'scannerBrand', '0', '2016-12-14 15:58:00', '扫描枪厂家');";
    public final static String SQL44 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('30003', '扫描枪设置', 'scannerPort', 'COM3', '2016-12-14 15:58:00', '扫描枪串口');";
    public final static String SQL45 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣设置', 'storageTime', '7', '2017-03-11 15:58:00', '建包历史保存时间');";
    public final static String SQL46 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣方案更新时间', 'updateTime', '', '2016-12-14 15:58:00', '分拣方案更新时间');";
    public final static String SQL47 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '设备编号', 'terminalNo', '', '2017-03-11 15:58:00', '设备编号');";
    public final static String SQL48 = " INSERT INTO PAControlParam (CtrlTypeID, CtrlTypeName, KeyString, DefaultValue, LastModifyTime, Remark) VALUES ('20001', '分拣场编号', 'ofDepartmentID', '', '2017-03-11 15:58:00', '分拣场编号');";

    public final static String SQL49 = "DROP TABLE IF EXISTS TBTerminal;";
    public final static String SQL50 = "CREATE TABLE TBTerminal (TerminalNo VARCHAR (20)  NOT NULL,DepartmentID VARCHAR (20),TerminalName VARCHAR (64),DeskNum INT,BoxNum INT,Location VARCHAR (100),RegisterFlag   VARCHAR (2),TerminalStatus VARCHAR (2),UpdateTime VARCHAR (30),Remark VARCHAR (255),PRIMARY KEY (TerminalNo));";

    public final static String SQL51 = "COMMIT TRANSACTION;";
    public final static String SQL52 = "PRAGMA foreign_keys = on;";
}
