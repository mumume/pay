package com.joinpay.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	//
	private Long userId;
	// 用户名
	private String username;
	// 用户真实姓名
	private String name;
	// 密码
	private String password;
	// 部门
	private Long deptId;

	private String deptName;
	// 邮箱
	private String email;
	// 手机号
	private String mobile;
	// 状态 0:禁用，1:正常
	private Integer status;
	// 创建用户id
	private Long userIdCreate;
	// 创建时间
	private Date gmtCreate;
	// 修改时间
	private Date gmtModified;
	// 角色
	private List<Long> roleIds;
	// 性别
	private Long sex;
	// 出身日期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	// 图片ID
	private Long picId;
	// 现居住地
	private String liveAddress;
	// 爱好
	private String hobby;
	// 省份
	private String province;
	// 所在城市
	private String city;
	// 所在地区
	private String district;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserIdCreate() {
		return userIdCreate;
	}

	public void setUserIdCreate(Long userIdCreate) {
		this.userIdCreate = userIdCreate;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Long getPicId() {
		return picId;
	}

	public void setPicId(Long picId) {
		this.picId = picId;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress == null ? null : liveAddress.trim();
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby == null ? null : hobby.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	@Override
	public String toString() {
		return "SysUser [userId=" + userId + ", username=" + username + ", name=" + name + ", password=" + password
				+ ", deptId=" + deptId + ", deptName=" + deptName + ", email=" + email + ", mobile=" + mobile
				+ ", status=" + status + ", userIdCreate=" + userIdCreate + ", gmtCreate=" + gmtCreate
				+ ", gmtModified=" + gmtModified + ", roleIds=" + roleIds + ", sex=" + sex + ", birth=" + birth
				+ ", picId=" + picId + ", liveAddress=" + liveAddress + ", hobby=" + hobby + ", province=" + province
				+ ", city=" + city + ", district=" + district + "]";
	}

}