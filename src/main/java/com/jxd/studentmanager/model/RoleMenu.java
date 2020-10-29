package com.jxd.studentmanager.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName RoleMenu
 * @Description TODO
 * @Author jinkaiyan
 * @Date 2020/10/29
 * @Version 1.0
 */
@TableName("rolemenu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private int rmid;
    private int role;
    private int mid;

    public int getRmid() {
        return rmid;
    }

    public void setRmid(int rmid) {
        this.rmid = rmid;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }
}
