package com.milkteaking.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author TanJJ
 * @time 2018/5/8 20:04
 * @des 数据库实体类
 */

@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private long id;
    private String name;
    private String avatar;
    private String gender;
    private String address;
    @Generated(hash = 851262146)
    public UserProfile(long id, String name, String avatar, String gender,
            String address) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.address = address;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
