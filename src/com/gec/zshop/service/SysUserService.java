package com.gec.zshop.service;

import java.util.List;

import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.SysUser;

public interface SysUserService {
	
	public List<SysUser> login(String loginName,String password);
	
	public List<SysUser> query(SysUser sysUser);

	public int insert(SysUser sysUser);

	public SysUser findUserById(Integer id);
	
	public int update(SysUser sysUser);

	List<SysUser> queryByPage(SysUser sysUser, PageModel pageModel);


}
