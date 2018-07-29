package com.hzdongcheng.persistent.db;



import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

/**
 * Created by tony.zhongli on 2017/4/24.
 */

public class JDBCField {
    //bindArgs only byte[], String, Long and Double are supported in bindArgs
    public final static int INT_TYPE = 1;
    public final static int LONG_TYPE = 2;
    public final static int DOUBLE_TYPE = 3;
    public final static int STRING_TYPE = 4;
    public final static int DATE_TYPE = 5;
    public final static int TIMESTAMP_TYPE = 6;

    public String name;
    public Object value;
    public String operator = " = ";
    public int type = STRING_TYPE;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getStringValue() {
        String result = "";
        if (value != null)
            result = value.toString();

        return result;
    }

    public int getIntValue(){
        int result = 0;

        if(value != null){
            result = ((Integer)value).intValue();
        }

        return result;
    }

    public long getLongValue(){
        long result = 0L;
        if(value != null){
            result = ((Long)value).longValue();
        }

        return result;
    }

    public double getDoubleValue(){
        double result = 0.0D;

        if(value != null){
            result = ((Double)value).doubleValue();
        }

        return result;
    }

    public java.util.Date getDateValue(){
        java.util.Date result = null;

        if(value != null){
            result = DateUtils.stringToDate(value.toString());
        }

        return result;
    }

    public String quoteVal()
    {
        String str = "";

        if (value != null)
        {
            if (value instanceof String){
                str = StringUtils.addQuote( (String) value);
            }else if (value instanceof Double){
                str = StringUtils.formatDouble(((Double)value).doubleValue());
            }else if (value instanceof java.util.Date){
                str = StringUtils.formatDate((java.util.Date)value);
            }else
            {
                str = value.toString();
            }
        }

        return str;
    }
}
