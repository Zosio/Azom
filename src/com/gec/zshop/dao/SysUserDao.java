package com.gec.zshop.dao;

import java.util.List;

import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.SysUser;

public interface SysUserDao {

	public List<SysUser> findUserByLoginNameAndPassword(SysUser sysUser);
	//ʵ�ֲ�ѯ�û��б�
	public List<SysUser> query(SysUser sysUser);
	//ʵ���û����
	public int insert(SysUser sysUser);
	
	//����id��ȡ�û�����
	public SysUser findUserById(Integer id);

	public int update(SysUser sysUser);
	
	List<SysUser> queryByPage(SysUser sysUser, PageModel pageModel);
	
	int queryByCount(SysUser sysUser);

}