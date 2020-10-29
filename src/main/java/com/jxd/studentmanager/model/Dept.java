package com.jxd.studentmanager.model;

import com.baomidou.mybatisplus.annotation.*;

/**
 * @ClassName Dept
 * @Description TODO
 * @Author fengxueqin
 * @Date 2020/10/28
 * @Version 1.0
 */
@TableName("dept")
public class Dept {
    @TableId(type = IdType.AUTO)
    private int did;

    private String dname;
    private int dheader;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private int isdel; //0-未删除 1-已删除

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public int getDheader() {
        return dheader;
    }

    public void setDheader(int dheader) {
        this.dheader = dheader;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
