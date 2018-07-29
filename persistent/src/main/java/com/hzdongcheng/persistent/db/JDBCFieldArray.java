package com.hzdongcheng.persistent.db;


import com.hzdongcheng.components.toolkits.utils.DateUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.util.ArrayList;

/**
 * Created by tony.zhongli on 2017/4/24.
 */

public class JDBCFieldArray {
    //保存所有字段
    protected ArrayList list = new ArrayList();

    private ArrayList<String> whereArgsList = new ArrayList();

    public JDBCFieldArray() {

    }

    /**
     * 增加字符型的条件表达式,默认的操作符为=
     * @param name String
     * @param value String
     */
    public void add(String name, String value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);

        this.addField(field);
    }

    /**
     * 增加整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value int
     */
    public void add(String name, int value) {
        JDBCField field = new JDBCField();

        //bindArgs only byte[], String, Long and Double are supported in bindArgs
        field.setName(name);
        field.setValue(new Long(value));
        field.setType(JDBCField.LONG_TYPE);

        this.addField(field);
    }

    /**
     * 增加长整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value long
     */
    public void add(String name, long value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Long(value));
        field.setType(JDBCField.LONG_TYPE);

        this.addField(field);
    }

    /**
     * 增加双精度型的条件表达式，默认的操作符为=
     * @param name String
     * @param value double
     */
    public void add(String name, double value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Double(value));
        field.setType(JDBCField.DOUBLE_TYPE);

        this.addField(field);
    }

    /**
     * 增加日期型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Date
     */
    public void add(String name, java.util.Date value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(DateUtils.datetimeToString(value));
        field.setType(JDBCField.STRING_TYPE);

        this.addField(field);
    }

    /**
     * 增加字符型的条件表达式,默认的操作符为=
     * @param name String
     * @param value String
     */
    public void checkAdd(String name, String value) {
        if (StringUtils.isNotEmpty(value)) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value int
     */
    public void checkAdd(String name, int value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加长整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value long
     */
    public void checkAdd(String name, long value) {
        if (value > 0L) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);

            this.addField(field);
        }
    }


    /**
     * 增加双精度型的条件表达式，默认的操作符为=
     * @param name String
     * @param value double
     */
    public void checkAdd(String name, double value) {
        if (value > 0.0D) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Double(value));
            field.setType(JDBCField.DOUBLE_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加日期型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Date
     */
    public void checkAdd(String name, java.util.Date value) {
        if (value != null ) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(DateUtils.datetimeToString(value));
            field.setType(JDBCField.STRING_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加字符型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value String
     */
    public void add(String name, String operator, String value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     */
    public void add(String name, String operator, int value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Long(value));
        field.setType(JDBCField.LONG_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加长整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value long
     */
    public void add(String name, String operator, long value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Long(value));
        field.setType(JDBCField.LONG_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }


    /**
     * 增加双精度型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value double
     */
    public void add(String name, String operator, double value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Double(value));
        field.setType(JDBCField.DOUBLE_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加日期型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Date
     */
    public void add(String name, String operator, java.util.Date value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(DateUtils.datetimeToString(value));
        field.setType(JDBCField.STRING_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加字符型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value String
     */
    public void checkAdd(String name, String operator, String value) {
        if (StringUtils.isNotEmpty(value)) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     */
    public void checkAdd(String name, String operator, int value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value long
     */
    public void checkAdd(String name, String operator, long value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }


    /**
     * 增加双精度型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value double
     */
    public void checkAdd(String name, String operator, double value) {
        if (value > 0.0D) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Double(value));
            field.setType(JDBCField.DOUBLE_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加日期型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Date
     */
    public void checkAdd(String name, String operator, java.util.Date value) {
        if (value != null) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(DateUtils.datetimeToString(value));
            field.setType(JDBCField.STRING_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 直接传入SQL语句表达式
     * @param expression String
     */
    public void addSQL(String expression) {
        JDBCField field = new JDBCField();

        field.setName(null);
        field.setValue(expression);

        this.addField(field);
    }

    public int size() {
        return list.size();
    }

    public JDBCField get(int index) {
        return (JDBCField) list.get(index);
    }

    public String toSetSQL(){
        StringBuilder sbSet = new StringBuilder(128);
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).name == null)
                sbSet.append(this.get(i).value.toString());
            else {
                sbSet.append(this.get(i).name);
                sbSet.append(" ");
                sbSet.append(this.get(i).operator );
                sbSet.append(" ");
                sbSet.append("?");
            }

            if (i != (this.size() - 1))
                sbSet.append(",");
        }

        return sbSet.toString();
    }

    public String toWhereSQL(){

        StringBuilder sbWhere = new StringBuilder(128);
        whereArgsList.clear();
        sbWhere.append(" 1=1 ");
        for (int j = 0; j < this.size(); j++) {
            if (this.get(j).name == null)
                sbWhere.append(this.get(j).value.toString());
            else{
                sbWhere.append(" AND ");
                sbWhere.append(this.get(j).name);
                sbWhere.append(" ");
                sbWhere.append(this.get(j).operator);
                sbWhere.append(" " );
                sbWhere.append("?");

                whereArgsList.add(this.get(j).getStringValue());
            }
        }

        return sbWhere.toString();
    }

    public String[] toWhereArgs(){

        String[] whereArgs = null;
        if(whereArgsList.size() > 0){
            whereArgs = new String[whereArgsList.size()];
            for (int j = 0; j < whereArgsList.size(); j++) {
                whereArgs[j] = whereArgsList.get(j);
            }
        }
        return whereArgs;
    }
    protected void addField(JDBCField f){
        this.list.add(f);
    }

    public String toLogSetSQL(){
        StringBuilder sbSet = new StringBuilder(256);
        for (int i = 0; i < this.size(); i++) {
            JDBCField f = this.get(i);

            if (f.name == null)
                sbSet.append(f.value.toString());
            else{
                sbSet.append(f.name);
                sbSet.append(" ");
                sbSet.append(f.operator);
                sbSet.append(" ");
                sbSet.append( f.quoteVal());
            }

            if (i != (this.size() - 1))
                sbSet.append(",");
        }

        return sbSet.toString();
    }

    public String toLogWhereSQL(){
        StringBuilder sbWhere = new StringBuilder(256);

        sbWhere.append(" WHERE 1=1 ");
        for (int j = 0; j < this.size(); j++) {
            JDBCField f = this.get(j);
            if (f.name == null)
                sbWhere.append(f.value.toString());
            else{
                sbWhere.append(" AND ");
                sbWhere.append(f.name);
                sbWhere.append(" ");
                sbWhere.append(f.operator);
                sbWhere.append(" " );
                sbWhere.append("?");
            }
        }

        return sbWhere.toString();
    }

    public String toLogWhereSQL(String sql){
        StringBuilder sbWhere = new StringBuilder(256);

        int index = sql.indexOf(" 1=1 ");
        if(index != -1){
            sql = sql.substring(0,index) + " 1=1 ";
            sbWhere.append(sql);
        }

        for (int j = 0; j < this.size(); j++) {
            JDBCField f = this.get(j);
            if (f.name == null)
                sbWhere.append(f.value.toString());
            else{
                sbWhere.append(" AND ");
                sbWhere.append(f.name);
                sbWhere.append(" ");
                sbWhere.append(f.operator);
                sbWhere.append(" " );
                sbWhere.append("?");
            }
        }

        return sbWhere.toString();
    }
}
