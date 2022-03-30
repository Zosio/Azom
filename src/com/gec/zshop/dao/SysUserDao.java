package com.gec.zshop.dao;

import java.util.List;

import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.SysUser;

public interface SysUserDao {

	public List<SysUser> findUserByLoginNameAndPassword(SysUser sysUser);
	//实现查询用户列表
	public List<SysUser> query(SysUser sysUser);
	//实现用户添加
	public int insert(SysUser sysUser);
	
	//根据id获取用户数据
	public SysUser findUserById(Integer id);

	public int update(SysUser sysUser);
	
	List<SysUser> queryByPage(SysUser sysUser, PageModel pageModel);
	
	int queryByCount(SysUser sysUser);

}