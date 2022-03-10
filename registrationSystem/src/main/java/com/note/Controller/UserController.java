package com.note.Controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.note.Model.User;
import com.note.Service.UserService;


@Controller
public class UserController {
	
	@Resource  
	private UserService userService; 
	
	/**
	 * ��½ 
	 * @param session
	 * @param user
	 * @return
	 */
	@RequestMapping("login.do")
	@ResponseBody
	public String login(HttpSession session,User user){
			 
		String username = user.getUsername();
		String password = user.getPassword();
	
		System.out.println("��ǰ�˻�ȡ����Ϣ��" + username +"__"+ password);
		
		
		String result = "";
		System.out.println("��ʼ����select");
		User db_user = userService.selectUserByUsername(username);
		String db_password = db_user.getPassword();
		System.out.println("�����ݿ��ȡ��password�ǣ�"+db_password);
		if(password.equals(db_password)==false) {
			result = "error";
		}else if(password.equals(db_password)==true) {
			session.setAttribute("username",username);
			if(db_user.getLevel() == 1) {
				result = db_user.getLevel().toString();
			}else {
				result = db_user.getUsername();
			}

		}

		
		
		System.out.println(result);
		
	    //����һ��map	
	    return result;
	}
	
	/**
	 * �˳���½
	 * @param session
	 * @return
	 */
	@RequestMapping("loginout.do")
	public String loginout(HttpSession session){
		//���session
		session.invalidate();
		
		return "redirect:index.do";
	}
	
	/**
	 * ע��
	 * @param user
	 * @return
	 */
	@RequestMapping("register.do")
	@ResponseBody
	public String add(User user) {
		String result = "";
		
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getName();
		System.out.println("��ǰ�˻�ȡ����Ϣ��" + username +"__"+ password +"__"+name);
		
		try{
			System.out.println("��ʼ����insert");
			User db_user = userService.selectUserByUsername(username);
			Integer id = db_user.getUser_id();
			//��ѯ������ ����ǰ̨�˺��Ѵ���
			if(id != null) {
				result = "exists";
			}
		}catch(Exception e) {
			//��ѯ��������ʱ ��������
			System.out.println("��ʼinsert�����ݿ�");
			userService.AddUser(user);
			int pid = user.getUser_id();
			System.out.println(pid);
			result = "success";
		}
			
		System.out.println(result);
		return result; 
	}
	
	@RequestMapping("getUserData.do")
	@ResponseBody
	public User getUserData(User user) {
		User userData = null;
		String username = user.getUsername();
		
		try{
			User db_user = userService.selectUserByUsername(username);
			Integer id = db_user.getUser_id();
			//��ѯ������ ����ǰ̨�˺��Ѵ���
			if(id != null) {
				userData = db_user;
			}
		}catch(Exception e) {
			
		}
			
		return userData; 
	}
	
	@RequestMapping("index.do")
	public String index() {
		return "note"; 
	}
	
	/**
	 * ��������
	 * @param user
	 * @return
	 */
	@RequestMapping("/changePassword.do")
	@ResponseBody
	public String changePassword(User user) {
		String result = "";
		
		String username = user.getUsername();
		String password = user.getPassword();
		String name = user.getName();
		
		System.out.println("��ǰ�˻�ȡ����Ϣ��" + username +"__"+ password +"__"+name);
		
		try {
			System.out.println("��ʼ����update");
			int id = userService.updateUserByUsername(user);
			System.out.println(id);
			result = "success";
		}catch(Exception e) {
			//��ѯ��������ʱ ��������
			result = "error";
			
		}
		return result;
	}
}

