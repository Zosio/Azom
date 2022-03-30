package com.gec.zshop.service.impl;

import java.util.List;

import com.gec.zshop.dao.SysRoleDao;
import com.gec.zshop.dao.impl.SysRoleDaoImpl;
import com.gec.zshop.domain.Role;
import com.gec.zshop.service.SysRoleService;

public class SysRoleServiceImpl implements SysRoleService {
	
	private SysRoleDao sysRoleDao;
	
	public SysRoleServiceImpl()
	{
		sysRoleDao=new SysRoleDaoImpl();
	}

	@Override
	public List<Role> query(Role role) {
		// TODO Auto-generated method stub
		return sysRoleDao.query(role);
	}
	
	

}