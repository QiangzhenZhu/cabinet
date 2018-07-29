package com.hzdongcheng.device.bean;

/**
 * Created by Peace on 2017/9/17.
 * Modify by zxy on 2017/9/19
 */

public class BoxInfo {
    /**
     * 箱体编号
     */
    public String BoxNo;
    /**
     * 箱体名称
     */
    public String BoxName;
    /**
     * 驱动板号
     */
    public int iDeskNo;//DriverNum;

    /**
     * 箱体实地址(在驱动板中的编号)
     */
    public int iBoxNo;//BoxAddressing;

    /**
     * 箱体类型
     */
    public String BoxType;

    /**
     * 物品状态(0无物，1有物,-1未知默认)
     */
    public int Article = -1;

    /**
     * 箱门状态(0关，1开,-1未知默认)
     */
    public int OpenStatus = -1;

    //public int BoxSize;
    //public int BoxNumber;
}
