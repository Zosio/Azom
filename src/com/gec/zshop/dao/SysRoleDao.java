package com.gec.zshop.dao;

import java.util.List;

import com.gec.zshop.domain.Role;

public interface SysRoleDao {

	public List<Role> query(Role role);
}