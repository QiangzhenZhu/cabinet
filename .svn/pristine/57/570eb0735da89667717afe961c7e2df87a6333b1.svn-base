package com.hzdongcheng.persistent.dao;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.MBPwdShortMsg;
import com.hzdongcheng.persistent.ex.DBErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import org.apache.log4j.Logger;

import java.util.List;

public class MBPwdShortMsgDAO{
 private static final Logger log = Logger.getLogger(MBPwdShortMsg.class);
 private static final String TABLE_NAME = "MBPwdShortMsg";
 private static final Class CLASS_TYPE = MBPwdShortMsg.class;

 public MBPwdShortMsgDAO(){}


	public int insert(SQLiteDatabase database,MBPwdShortMsg obj) throws DcdzSystemException{
		return DbUtils.executeInsert(database,obj,CLASS_TYPE);
	}


	public int update(SQLiteDatabase database, JDBCFieldArray setCols, JDBCFieldArray whereCols) throws DcdzSystemException{
		return DbUtils.executeUpdate(database,TABLE_NAME,setCols,whereCols);
	}


	public int delete(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		return DbUtils.executeDelete(database,TABLE_NAME,whereCols);
	}


	public int isExist(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		return DbUtils.executeCount(database,TABLE_NAME,whereCols);
	}


	public MBPwdShortMsg find(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		List<MBPwdShortMsg> listResult = DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE);
		if(listResult == null || listResult.size()==0)
			throw new DcdzSystemException(DBErrorCode.ERR_DB_NORECORD);

		MBPwdShortMsg result = listResult.get(0);
		return result;
	}


	public List<MBPwdShortMsg> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE);
	}


	public List<MBPwdShortMsg> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols,String orderBy) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE,orderBy);
	}


	public List<MBPwdShortMsg> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols,String orderBy,int recordBegin,int recordNum) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE,orderBy,recordBegin,recordNum);
	}

}