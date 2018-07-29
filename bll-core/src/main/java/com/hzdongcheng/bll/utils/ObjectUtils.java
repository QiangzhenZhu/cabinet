package com.hzdongcheng.bll.utils;

import com.hzdongcheng.components.toolkits.utils.DateUtils;

import java.lang.reflect.Field;
import java.util.Date;

public class ObjectUtils {
	public static String getPropertyStrValue(Object target, String propertyName) {
		Object o = null;
		try {
			o = target.getClass().getDeclaredField(propertyName).get(target);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (o != null)
			return o.toString();

		return "";
	}

	// / <summary>
	// / 针对public 成员变量
	// / </summary>
	// / <param name="target"></param>
	// / <param name="fieldName"></param>
	// / <param name="value"></param>
	public static void setFieldValue(Object target, String fieldName,
			String value) {

		Field field;
		try {
			field = target.getClass().getField(fieldName);
			if (field != null)
				field.set(target, value);
		} catch (NoSuchFieldException | SecurityException
				| IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String GetFieldStrValue(Object target, String fieldName) {
		try {
			return target.getClass().getField(fieldName).get(target).toString();
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {

		}

		return "";
	}

	public static Date GetFieldDateTimeValue(Object target, String fieldName) {
		Object obj;
		try {
			obj = target.getClass().getField(fieldName).get(target);
			return DateUtils.stringToDateTime(obj.toString());
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return DateUtils.getMinDate();
	}

	public static boolean HaveField(Object target, String fieldName) {

		Field field = null;
		try {
			field = target.getClass().getField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
		}

		if (field != null)
			return true;

		return false;
	}
}
