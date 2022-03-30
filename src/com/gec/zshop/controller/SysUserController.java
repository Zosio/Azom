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
			//�����������ȡ
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			
			List<SysUser> sysUsers=sysUserService.login(loginName, password);
			
			//ͨ���ж�sysUsers�����Ƿ������ݣ�������������û����ڣ���¼�ɹ��������¼ʧ��
			if(sysUsers!=null && sysUsers.size()>0)
			{
				//���� ��ҳ
				// �ض�����ʾ��Ʒ�б��servlet
				// ��д���ַ=������+·��
				String redirectUrl = getServletContext().getContextPath() + "/backend/main";
				response.sendRedirect(redirectUrl);
				
			}else {
				if(loginName !=null)
				{request.setAttribute("errorMsg", "�û��������벻�ԣ�����������");}
				//���ص�¼ҳ
				request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

			}
			
		}else if(action.equals("showLogin"))
		{
			//ת����¼ҳ
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

		}else if(action.equals("main"))
		{
			request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);

		}else if (action.equals("list")) {
			SysRoleService sysRoleService = new SysRoleServiceImpl();
			// ��ȡ��ѯ��������
			String name = request.getParameter("name");
			String loginName = request.getParameter("loginName");
			String phone = request.getParameter("phone");
			String roleId = request.getParameter("role");
			String isValid = request.getParameter("isValid");
			//��ȡpageIndex����
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
			// ��������洢��request������
			request.setAttribute("sysUsers", sysUsers);
			// ����ѯ�����Ľ����ת������ͼ�����ʾ
			request.getRequestDispatcher("/WEB-INF/views/sysuserManager.jsp").forward(request, response);

		}else if (action.equals("add")) {
			System.out.println("SysUserController add");
			// ʵ�ֱ����û���Ϣ
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
			// ��json����д�ص��ͻ���
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
			// ��json����д�ص��ͻ���
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
			// ��json����д�ص��ͻ���
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			
			String json = objectMapper.writeValueAsString(ResponseResult.success(sysUser));
			response.getWriter().println(json);
		}
		else if(action.equals("modifySave"))
		{
			//��ȡ�޸Ĳ�������
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
			// ��json����д�ص��ͻ���
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