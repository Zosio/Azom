package com.gec.zshop.service.impl;

import java.util.List;

import com.gec.zshop.dao.SysUserDao;
import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.SysUser;
import com.gec.zshop.service.SysUserService;

import com.gec.zshop.dao.impl.SysUserDaoImpl;

public class SysUserServiceImpl implements SysUserService {
	
	private SysUserDao sysUserDao;
	
	public SysUserServiceImpl()
	{
		sysUserDao=new SysUserDaoImpl();
	}
	

	@Override
	public List<SysUser> login(String loginName, String password) {
		
		SysUser sysUser=new SysUser();
		sysUser.setLoginName(loginName);
		sysUser.setPassword(password);
		
		return sysUserDao.findUserByLoginNameAndPassword(sysUser);
	}
	
	@Override
	public List<SysUser> query(SysUser sysUser) {
		// TODO Auto-generated method stub
		return sysUserDao.query(sysUser);
	}
	@Override
	public int insert(SysUser sysUser) {
		// TODO Auto-generated method stub
		return sysUserDao.insert(sysUser);
	}

	@Override
	public SysUser findUserById(Integer id) {
		// TODO Auto-generated method stub
		return sysUserDao.findUserById(id);
	}
	
	@Override
	public int update(SysUser sysUser) {
		// TODO Auto-generated method stub
		return sysUserDao.update(sysUser);
	}
	@Override
	public List<SysUser> queryByPage(SysUser sysUser, PageModel pageModel) {
		// TODO Auto-generated method stub
		
		//获取数据表的总记录条数
		int count=sysUserDao.queryByCount(sysUser);
		pageModel.setTotalRecordSum(count);
		
		return sysUserDao.queryByPage(sysUser, pageModel);
	}

}
