package com.hywang.timeline.actions;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.utils.CipherUtil;
import com.hywang.timeline.utils.CookiesManager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 868246080845674572L;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String login() {

		String returnCode = ActionSupport.ERROR;
		boolean success = false;
		ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		String uname = httpServletRequest.getParameter("username");
		String pwd = httpServletRequest.getParameter("password");
		String remember = httpServletRequest.getParameter("rememberme");

		User user = null;
		try {
			UserDAO udao = DAOFactory.getInstance().createUserDAO();
			user = udao.getUserByName(uname);
			if (user != null) {
				if (CipherUtil.validatePassword(user.getUserPwd(), pwd)) {
					success = true;
					returnCode = ActionSupport.SUCCESS;
				}
				// if remember,need to record to database
				if (remember != null && !"".equals(remember)) {
					if ("true".equals(remember)) { //$NON-NLS-N$
						rememberUser(uname, user, udao);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success) {
			// login successfully,set the user to session
			System.out.println("set user in to session"
					+ httpServletRequest.getSession().getId());
			httpServletRequest.getSession().setAttribute("user", user);
			// String url = "/timeline.jsp?username="+uname;
			// String url = "/timeline.jsp";
			// // String url="/titleBar.jsp";
			// System.out.println(url);
			// // response.sendRedirect(url);
			// httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest,
			// response);
		}
		// else{
		// // httpServletResponse.setContentType("text/html");
		// // PrintWriter out;
		// // try {
		// // out = httpServletResponse.getWriter();
		// // out.print("那个...密码貌似不对啊，请再输一次");
		// // out.flush();
		// // out.close();
		// // } catch (IOException e) {
		// // e.printStackTrace();
		// // }
		// // response.sendRedirect("/user/login.jsp");
		// }

		return returnCode;
	}
	
	public String logout(){
		String returnCode = ActionSupport.ERROR;
	     HttpSession session = httpServletRequest.getSession();
	        Object userObject = session.getAttribute("user");
	        if (userObject != null) { // 1.remove from session
	            User user = (User) userObject;
	            String username = user.getUserName();
	            session.removeAttribute("user");
	            System.out.println("delete user from session: "+session.getId());
	            String sessionid = CookiesManager.getValue(httpServletRequest.getCookies(), "sessionid");
	            UserDAO userDao = DAOFactory.getInstance().createUserDAO();
	            try {
	                userDao.deleteAutoLoginState(username, sessionid);
	                returnCode=ActionSupport.SUCCESS;
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return returnCode;
	}

	private void rememberUser(String uname, User user, UserDAO udao)
			throws Exception {
		String sessionId = httpServletRequest.getSession().getId();
		Cookie useridCook = new Cookie("userid", Integer.toString(user.getId()));
		Cookie nameCook = new Cookie("username", user.getUserName());
		Cookie pwdCook = new Cookie("userpwd", user.getUserPwd());
		Cookie emailCook = new Cookie("email", user.getEmail());
		Cookie fnameCook = new Cookie("fname", user.getFirstName());
		Cookie lnameCook = new Cookie("lname", user.getLastName());
		Cookie sessionCook = new Cookie("sessionid", sessionId);
		httpServletResponse.addCookie(useridCook);
		httpServletResponse.addCookie(nameCook);
		httpServletResponse.addCookie(pwdCook);
		httpServletResponse.addCookie(emailCook);
		httpServletResponse.addCookie(fnameCook);
		httpServletResponse.addCookie(lnameCook);
		httpServletResponse.addCookie(sessionCook);
		udao.insertUserLogonValidateInfo(uname, sessionId);
	}

	public String register() {
			String returnCode = ActionSupport.ERROR;
		   	username = httpServletRequest.getParameter("username");
	        String pwd = CipherUtil.generateMD5Password(httpServletRequest.getParameter("password"));
	        String email = httpServletRequest.getParameter("email");
	        User user = new User();
	        user.setUserName(username);
	        user.setUserPwd(pwd);
	        user.setEmail(email);
	        
	        UserDAO userDao=  DAOFactory.getInstance().createUserDAO();
	        
	        try {
	            userDao.addUser(user);
	            httpServletRequest.getSession().setAttribute("user", user);
	            returnCode=ActionSupport.SUCCESS;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return returnCode;
	}
}
