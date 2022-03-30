package com.gec.zshop.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gec.zshop.domain.PageModel;
import com.gec.zshop.domain.Role;
import com.gec.zshop.domain.SysUser;
import com.gec.zshop.service.SysRoleService;
import com.gec.zshop.service.SysUserService;
import com.gec.zshop.service.impl.SysRoleServiceImpl;
import com.gec.zshop.service.impl.SysUserServiceImpl;
import com.gec.zshop.utils.ResponseResult;

/**
 * Servlet implementation class SysUserController
 */
@WebServlet(urlPatterns = { "/backend/sysuser/login","/backend/sysuser/showLogin","/backend/sysuser/list","/backend/sysuser/add","/backend/sysuser/modifyStatus","/backend/sysuser/findUserById","/backend/sysuser/modifySave","/backend/main" })
public class SysUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SysUserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");

		String uri = request.getRequestURI();

		String action = uri.substring(uri.lastIndexOf("/") + 1);
		
		SysUserService sysUserService=new SysUserServiceImpl();

		if(action.equals("login"))
		{
			//将请求参数获取
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			
			List<SysUser> sysUsers=sysUserService.login(loginName, password);
			
			//通过判断sysUsers集合是否有数据，如果有数据则用户存在，登录成功，否则登录失败
			if(sysUsers!=null && sysUsers.size()>0)
			{
				//进入 主页
				// 重定向到显示商品列表的servlet
				// 重写向地址=上下文+路径
				String redirectUrl = getServletContext().getContextPath() + "/backend/main";
				response.sendRedirect(redirectUrl);
				
			}else {
				if(loginName !=null)
				{request.setAttribute("errorMsg", "用户名及密码不对，请重新输入");}
				//返回登录页
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

			}
			
		}else if(action.equals("showLogin"))
		{
			//转发登录页
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

		}else if(action.equals("main"))
		{
			request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);

		}else if (action.equals("list")) {
			SysRoleService sysRoleService = new SysRoleServiceImpl();
			// 获取查询条件参数
			String name = request.getParameter("name");
			String loginName = request.getParameter("loginName");
			String phone = request.getParameter("phone");
			String roleId = request.getParameter("role");
			String isValid = request.getParameter("isValid");
			//获取pageIndex参数
			String pageIndex=request.getParameter("pageIndex");
			
			PageModel pageModel=new PageModel();
			pageModel.setPageIndex(pageIndex!=null&&!pageIndex.equals("")?Integer.parseInt(pageIndex):1);
			

			SysUser sysUser = new SysUser();
			sysUser.setName(name);
			sysUser.setLoginName(loginName);
			sysUser.setPhone(phone);

			if (roleId != null) {
				Role role = new Role();
				role.setId(Integer.parseInt(roleId));
				sysUser.setRole(role);

			}

			if (isValid != null) {
				sysUser.setIsValid(Integer.parseInt(isValid));
			}

			List<SysUser> sysUsers = sysUserService.queryByPage(sysUser, pageModel);
			Role role = new Role();
			List<Role> roles = sysRoleService.query(role);

			request.setAttribute("pageModel", pageModel);
			request.setAttribute("roles", roles);
			// 将结果集存储到request作用域
			request.setAttribute("sysUsers", sysUsers);
			// 将查询出来的结果集转发到视图组件显示
			request.getRequestDispatcher("/WEB-INF/views/sysuserManager.jsp").forward(request, response);

		}else if (action.equals("add")) {
			System.out.println("SysUserController add");
			// 实现保存用户信息
			String name = request.getParameter("name");
			String loginName = request.getParameter("loginName");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String roleId = request.getParameter("roleId");
			String password = request.getParameter("password");

			SysUser sysUser = new SysUser();
			sysUser.setName(name);
			sysUser.setEmail(email);
			sysUser.setPhone(phone);
			sysUser.setLoginName(loginName);
			sysUser.setCreateDate(new java.util.Date());
			sysUser.setIsValid(1);
			sysUser.setPassword(password);

			if (roleId != null && !roleId.equals("")) {
				Role role = new Role();
				role.setId(Integer.parseInt(roleId));
				sysUser.setRole(role);
			}

			System.out.println(sysUser);
			int flag = sysUserService.insert(sysUser);

			ObjectMapper objectMapper = new ObjectMapper();
			// 将json数据写回到客户端
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			if (flag > 0) {
				String json = objectMapper.writeValueAsString(ResponseResult.success());
				response.getWriter().println(json);

			}

		}
		else if(action.equals("modifyStatus"))
		{
			String id=request.getParameter("id");
			
			SysUser sysUser=sysUserService.findUserById(Integer.parseInt(id));
			
			if(sysUser.getIsValid()==1)
			{
				sysUser.setIsValid(0);
			}else {
				sysUser.setIsValid(1);
			}
			
			int flag=sysUserService.update(sysUser);
			
			ObjectMapper objectMapper = new ObjectMapper();
			// 将json数据写回到客户端
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			if (flag > 0) {
				String json = objectMapper.writeValueAsString(ResponseResult.success());
				response.getWriter().println(json);

			}
			
		}
		else if(action.equals("findUserById"))
		{
			String id=request.getParameter("id");
			SysUser sysUser=sysUserService.findUserById(Integer.parseInt(id));
			ObjectMapper objectMapper = new ObjectMapper();
			// 将json数据写回到客户端
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			String json = objectMapper.writeValueAsString(ResponseResult.success(sysUser));
			response.getWriter().println(json);
		}
		else if(action.equals("modifySave"))
		{
			//获取修改参数数据
			String id=request.getParameter("id");
			String name = request.getParameter("name");
			String loginName = request.getParameter("loginName");
			String phone = request.getParameter("phone");
			String email = request.getParameter("email");
			String roleId = request.getParameter("roleId");
			String password = request.getParameter("password");
			
			SysUser updateSysUser=sysUserService.findUserById(Integer.parseInt(id));
			
			if(name!=null && !name.equals(""))
			{
				updateSysUser.setName(name);
			}
			
			if(loginName!=null && !loginName.equals(""))
			{
				updateSysUser.setLoginName(loginName);
			}
			
			if(phone!=null && !phone.equals(""))
			{
				updateSysUser.setPhone(phone);
			}
			
			if(email!=null && !email.equals(""))
			{
				updateSysUser.setEmail(email);
			}
			
			if (roleId != null && !roleId.equals("")) {
				Role role = new Role();
				role.setId(Integer.parseInt(roleId));
				updateSysUser.setRole(role);
			}
			
			if(password!=null && !password.equals(""))
			{
				updateSysUser.setPassword(password);
			}
			
			
			int flag=sysUserService.update(updateSysUser);
			
			ObjectMapper objectMapper = new ObjectMapper();
			// 将json数据写回到客户端
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			if (flag > 0) {
				String json = objectMapper.writeValueAsString(ResponseResult.success());
				response.getWriter().println(json);
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}