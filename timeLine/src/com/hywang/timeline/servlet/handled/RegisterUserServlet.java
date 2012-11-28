package com.hywang.timeline.servlet.handled;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.utils.CipherUtil;


public class RegisterUserServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3927456258353631710L;

    /**
     * Constructor of the object.
     */
    public RegisterUserServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      doPost(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String pwd = CipherUtil.generateMD5Password(request.getParameter("password"));
        String email = request.getParameter("email");
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(pwd);
        user.setEmail(email);
        
        UserDAO userDao=  DAOFactory.getInstance().createUserDAO();
        
        boolean success = false;
        try {
            userDao.addUser(user);
            request.getSession().setAttribute("user", user);
            success=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//        out.println("<HTML>");
//        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//        out.println("  <BODY>");
//        if(success){
//            out.print("    The user  "+user.getUserName()+" has been created!!!");
//        }else{
//            out.print("    The user  "+user.getUserName()+" has not been created!!!");
//        }
//        out.print(this.getClass());
//        out.println("  </BODY>");
//        out.println("</HTML>");
//        out.flush();
//        out.close();
        if(success){
            String url = "/timeline.jsp?username="+username;
            System.out.println(url);
            response.sendRedirect(url);
        }else{
            response.sendRedirect("/user/register.jsp");
        }
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

}
