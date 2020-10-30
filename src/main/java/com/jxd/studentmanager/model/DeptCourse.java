package com.jxd.studentmanager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName DeptCourse
 * @Description TODO
 * @Author WangKai
 * @Date 2020/10/30
 * @Version 1.0
 */
@TableName("deptcourse")
public class DeptCourse {
    @TableId(type = IdType.AUTO)
    private int dcid;
    private int did;
    private int cid;

    public DeptCourse() {
    }

    public DeptCourse(int dcid, int did, int cid) {
        this.dcid = dcid;
        this.did = did;
        this.cid = cid;
    }

    public int getDcid() {
        return dcid;
    }

    public void setDcid(int dcid) {
        this.dcid = dcid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
