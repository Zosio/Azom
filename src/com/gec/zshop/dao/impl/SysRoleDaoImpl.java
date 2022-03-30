package com.gec.zshop.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gec.zshop.dao.SysRoleDao;
import com.gec.zshop.db.DbUtils;
import com.gec.zshop.domain.Role;

public class SysRoleDaoImpl implements SysRoleDao
{

	@Override
	public List<Role> query(Role role) {
		
		List<Role> roleList=new ArrayList<>();

        //select * from t_emp where and  book_name like 'aa%'
        StringBuffer querysql=new StringBuffer("select * from t_role where 1=1");
        

        if(role.getRoleName()!=null && !role.getRoleName().equals(""))
        {
        	querysql.append(" and role_name = '"+role.getRoleName()+"'");
        }
      
        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(querysql.toString());

            while (rs.next())
            {
            	Role inRole=new Role();
            	inRole.setId(rs.getInt(1));
            	inRole.setRoleName(rs.getString(2));

            	roleList.add(inRole);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return roleList;
	}

}
