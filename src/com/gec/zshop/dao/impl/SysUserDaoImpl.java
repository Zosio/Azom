package com.gec.zshop.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gec.zshop.dao.SysUserDao;
import com.gec.zshop.db.DbUtils;
import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.Role;
import com.gec.zshop.domain.SysUser;

public class SysUserDaoImpl implements SysUserDao
{
	

	@Override
	public List<SysUser> findUserByLoginNameAndPassword(SysUser sysUser) {
		
		List<SysUser> sysUsers=new ArrayList<>();

        //select * from t_emp where and  book_name like 'aa%'
		String innerSql="select * from t_sysuser t where t.login_name=? and t.password=?";
        StringBuffer strSql=new StringBuffer("select u.*,r.id as roleId,r.role_name from ("+innerSql+") u left join t_role r on u.role_id=r.id");

        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();
            
            PreparedStatement pStatemenet=connection.prepareStatement(strSql.toString());

            pStatemenet.setString(1,sysUser.getLoginName());
            pStatemenet.setString(2,sysUser.getPassword());
            
            ResultSet rs=pStatemenet.executeQuery();
            
            while (rs.next())
            {
                SysUser inSysUser=new SysUser();
                inSysUser.setId(rs.getInt(1));
                inSysUser.setName(rs.getString(2));
                inSysUser.setLoginName(rs.getString(3));
                inSysUser.setPassword(rs.getString(4));
                inSysUser.setPhone(rs.getString(5));
                inSysUser.setEmail(rs.getString(6));
                inSysUser.setIsValid(rs.getInt(7));
                inSysUser.setCreateDate(rs.getDate(8));
                
                Role role=new Role();
                role.setId(rs.getInt(9));
                role.setRoleName(rs.getString(10));
                
                inSysUser.setRole(role);
                
                sysUsers.add(inSysUser);
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return sysUsers;
	}

	@Override
	public List<SysUser> query(SysUser sysUser) {
		List<SysUser> sysUsers=new ArrayList<>();

        //select * from t_emp where and  book_name like 'aa%'
        StringBuffer innerSql=new StringBuffer("select u.*,r.id as roleId,r.role_name from t_sysuser u left join t_role r on u.role_id=r.id");
        
        StringBuffer outSql=new StringBuffer("select * from ("+innerSql.toString()+") t where 1=1");

        if(sysUser.getName()!=null && !sysUser.getName().equals(""))
        {
        	outSql.append(" and name like '"+sysUser.getName()+"%'");
        }
        
        if(sysUser.getLoginName()!=null && !sysUser.getLoginName().equals(""))
        {
        	outSql.append(" and login_name = '"+sysUser.getLoginName()+"'");
        }
        
        if(sysUser.getPhone()!=null && !sysUser.getPhone().equals(""))
        {
        	outSql.append(" and phone = '"+sysUser.getPhone()+"'");
        }
        
        if(sysUser.getRole()!=null)
        {
        	if(sysUser.getRole().getId()!=-1)
        	{
        		outSql.append(" and role_id = "+sysUser.getRole().getId());
        	}
        }

        if(sysUser.getIsValid()!=null)
        {
        	if(sysUser.getIsValid()!=-1)
        	{
        		outSql.append(" and is_valid = "+sysUser.getIsValid());
        	}
        }

        System.out.println(outSql.toString());
      
        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(outSql.toString());

            while (rs.next())
            {
                SysUser inSysUser=new SysUser();
                inSysUser.setId(rs.getInt(1));
                inSysUser.setName(rs.getString(2));
                inSysUser.setLoginName(rs.getString(3));
                inSysUser.setPassword(rs.getString(4));
                inSysUser.setPhone(rs.getString(5));
                inSysUser.setEmail(rs.getString(6));
                inSysUser.setIsValid(rs.getInt(7));
                inSysUser.setCreateDate(rs.getDate(8));
                
                Role role=new Role();
                role.setId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                
                
                inSysUser.setRole(role);
                System.out.println(inSysUser.getRole().getRoleName());
                sysUsers.add(inSysUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return sysUsers;
	}

	@Override
	public int insert(SysUser sysUser) {
		
		// TODO Auto-generated method stub
		Connection connection=null;
		try {
			connection=DbUtils.openConn();
			
			String sql="insert into t_sysuser (name,login_name,password,phone,email,is_valid,create_date,role_id) "
					+ "values(?,?,?,?,?,?,?,?) ";
			
			PreparedStatement pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, sysUser.getName());
			pstmt.setString(2, sysUser.getLoginName());
			pstmt.setString(3, sysUser.getPassword());
			pstmt.setString(4, sysUser.getPhone());
			pstmt.setString(5, sysUser.getEmail());
			pstmt.setInt(6, sysUser.getIsValid());
			pstmt.setDate(7, new Date(sysUser.getCreateDate().getTime()));
			pstmt.setInt(8, sysUser.getRole().getId());
			return pstmt.executeUpdate();
			
		}catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }
	            
		return -1;
	}
	

	@Override
	public SysUser findUserById(Integer id) {

        //select * from t_emp where and  book_name like 'aa%'
        StringBuffer innerSql=new StringBuffer("select u.*,r.id as roleId,r.role_name from t_sysuser u left join t_role r on u.role_id=r.id");
        
        StringBuffer outSql=new StringBuffer("select * from ("+innerSql.toString()+") t where id=?");

       
      
        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();

            PreparedStatement pstmt=connection.prepareStatement(outSql.toString());
            pstmt.setInt(1, id);
            
            ResultSet rs=pstmt.executeQuery();

            while (rs.next())
            {
                SysUser inSysUser=new SysUser();
                inSysUser.setId(rs.getInt(1));
                inSysUser.setName(rs.getString(2));
                inSysUser.setLoginName(rs.getString(3));
                inSysUser.setPassword(rs.getString(4));
                inSysUser.setPhone(rs.getString(5));
                inSysUser.setEmail(rs.getString(6));
                inSysUser.setIsValid(rs.getInt(7));
                inSysUser.setCreateDate(rs.getDate(8));
                
                Role role=new Role();
                role.setId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                
                
                inSysUser.setRole(role);
                
                return inSysUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return null;
	}

	@Override
	public int update(SysUser sysUser) {
		 //获取数据库连接对象
        Connection connection=null;
        try {
            connection= DbUtils.openConn();
            //name, login_name, password, phone, email, is_valid, create_date, role_id
            String updateSql="update t_sysuser set name=?,login_name=?,password=?,phone=?,email=?,is_valid=?,create_date=?,role_id=? where id=?";
            PreparedStatement pStatemenet=connection.prepareStatement(updateSql);

            pStatemenet.setString(1,sysUser.getName());
            pStatemenet.setString(2,sysUser.getLoginName());
            pStatemenet.setString(3,sysUser.getPassword());
            pStatemenet.setString(4,sysUser.getPhone());
            pStatemenet.setString(5,sysUser.getEmail());
            pStatemenet.setInt(6,sysUser.getIsValid());
            pStatemenet.setDate(7,new Date(sysUser.getCreateDate().getTime()));
            pStatemenet.setInt(8,sysUser.getRole().getId());
            pStatemenet.setInt(9, sysUser.getId());

            int pos=pStatemenet.executeUpdate();

            return pos;


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return -1;
	}
	@Override
	public List<SysUser> queryByPage(SysUser sysUser, PageModel pageModel) {
		
		List<SysUser> sysUsers=new ArrayList<>();
        //select * from t_emp where and  book_name like 'aa%'
        StringBuffer innerSql=new StringBuffer("select u.*,r.id as roleId,r.role_name from t_sysuser u left join t_role r on u.role_id=r.id");
        
        StringBuffer outSql=new StringBuffer("select * from ("+innerSql.toString()+") t where 1=1");

        if(sysUser.getName()!=null && !sysUser.getName().equals(""))
        {
        	outSql.append(" and name like '"+sysUser.getName()+"%'");
        }
        
        if(sysUser.getLoginName()!=null && !sysUser.getLoginName().equals(""))
        {
        	outSql.append(" and login_name = '"+sysUser.getLoginName()+"'");
        }
        
        if(sysUser.getPhone()!=null && !sysUser.getPhone().equals(""))
        {
        	outSql.append(" and phone = '"+sysUser.getPhone()+"'");
        }
        
        if(sysUser.getRole()!=null)
        {
        	if(sysUser.getRole().getId()!=-1)
        	{
        		outSql.append(" and role_id = "+sysUser.getRole().getId());
        	}
        }

        if(sysUser.getIsValid()!=null)
        {
        	if(sysUser.getIsValid()!=-1)
        	{
        		outSql.append(" and is_valid = "+sysUser.getIsValid());
        	}
        }

        outSql.append(" limit "+pageModel.getStartRowNum()+","+pageModel.getPageSize());
        
        System.out.println(outSql.toString());
      
        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(outSql.toString());

            while (rs.next())
            {
                SysUser inSysUser=new SysUser();
                inSysUser.setId(rs.getInt(1));
                inSysUser.setName(rs.getString(2));
                inSysUser.setLoginName(rs.getString(3));
                inSysUser.setPassword(rs.getString(4));
                inSysUser.setPhone(rs.getString(5));
                inSysUser.setEmail(rs.getString(6));
                inSysUser.setIsValid(rs.getInt(7));
                inSysUser.setCreateDate(rs.getDate(8));
                
                Role role=new Role();
                role.setId(rs.getInt("role_id"));
                role.setRoleName(rs.getString("role_name"));
                
                
                inSysUser.setRole(role);
                System.out.println(inSysUser.getRole().getRoleName());
                sysUsers.add(inSysUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return sysUsers;
	}
	
	

	@Override
	public int queryByCount(SysUser sysUser) {
		List<SysUser> sysUsers=new ArrayList<>();
        //select * from t_emp where and  book_name like 'aa%'
        StringBuffer innerSql=new StringBuffer("select count(*) from t_sysuser u left join t_role r on u.role_id=r.id");
        
        StringBuffer outSql=new StringBuffer("select * from ("+innerSql.toString()+") t where 1=1");

        if(sysUser.getName()!=null && !sysUser.getName().equals(""))
        {
        	outSql.append(" and name like '"+sysUser.getName()+"%'");
        }
        
        if(sysUser.getLoginName()!=null && !sysUser.getLoginName().equals(""))
        {
        	outSql.append(" and login_name = '"+sysUser.getLoginName()+"'");
        }
        
        if(sysUser.getPhone()!=null && !sysUser.getPhone().equals(""))
        {
        	outSql.append(" and phone = '"+sysUser.getPhone()+"'");
        }
        
        if(sysUser.getRole()!=null)
        {
        	if(sysUser.getRole().getId()!=-1)
        	{
        		outSql.append(" and role_id = "+sysUser.getRole().getId());
        	}
        }

        if(sysUser.getIsValid()!=null)
        {
        	if(sysUser.getIsValid()!=-1)
        	{
        		outSql.append(" and is_valid = "+sysUser.getIsValid());
        	}
        }

        
        System.out.println(outSql.toString());
      
        //获取数据库连接对象
        Connection connection=null;
        try {
            connection=DbUtils.openConn();

            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(outSql.toString());

            while (rs.next())
            {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	DbUtils.closeConn();
        }

        return 0;
	}
	
}
