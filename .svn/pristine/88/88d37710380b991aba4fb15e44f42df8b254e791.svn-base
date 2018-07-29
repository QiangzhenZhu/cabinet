package com.hzdongcheng.persistent.dao;

import android.database.sqlite.SQLiteDatabase;

import com.hzdongcheng.persistent.db.DbUtils;
import com.hzdongcheng.persistent.db.JDBCFieldArray;
import com.hzdongcheng.persistent.dto.MBLockUserTime;
import com.hzdongcheng.persistent.ex.DBErrorCode;
import com.hzdongcheng.components.toolkits.exception.DcdzSystemException;

import org.apache.log4j.Logger;

import java.util.List;

public class MBLockUserTimeDAO{
 private static final Logger log = Logger.getLogger(MBLockUserTime.class);
 private static final String TABLE_NAME = "MBLockUserTime";
 private static final Class CLASS_TYPE = MBLockUserTime.class;

 public MBLockUserTimeDAO(){}


	public int insert(SQLiteDatabase database,MBLockUserTime obj) throws DcdzSystemException{
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


	public MBLockUserTime find(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		List<MBLockUserTime> listResult = DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE);
		if(listResult == null || listResult.size()==0)
			throw new DcdzSystemException(DBErrorCode.ERR_DB_NORECORD);

		MBLockUserTime result = listResult.get(0);
		return result;
	}


	public List<MBLockUserTime> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE);
	}


	public List<MBLockUserTime> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols,String orderBy) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE,orderBy);
	}


	public List<MBLockUserTime> executeQuery(SQLiteDatabase database, JDBCFieldArray whereCols,String orderBy,int recordBegin,int recordNum) throws DcdzSystemException{
		return DbUtils.executeQuery(database,TABLE_NAME,whereCols,CLASS_TYPE,orderBy,recordBegin,recordNum);
	}

}