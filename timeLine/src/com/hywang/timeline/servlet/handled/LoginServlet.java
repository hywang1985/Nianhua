package com.hywang.timeline.servlet.handled;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.utils.CipherUtil;


public class LoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1809352866866964109L;

    /**
     * Constructor of the object.
     */
    public LoginServlet() {
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
//
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//        out.println("<HTML>");
//        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//        out.println("  <BODY>");
//        out.print("    This is ");
//        out.print(this.getClass());
//        out.println(", using the GET method");
//        out.println("  </BODY>");
//        out.println("</HTML>");
//        out.flush();
//        out.close();
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

        boolean success=false;
        String uname = request.getParameter("username");
        String pwd = request.getParameter("password");
        String remember = request.getParameter("rememberme");
        
        User user =null;
        try {
            UserDAO udao =DAOFactory.getInstance().createUserDAO();
            user =  udao.getUserByName(uname);
            if(user!=null){
                if(CipherUtil.validatePassword(user.getUserPwd(), pwd)){
                  success=true;  
                }
                if(remember!=null && !"".equals(remember)){
                    if("true".equals(remember)){ //$NON-NLS-N$
                        String sessionId=request.getSession().getId();
                        Cookie useridCook =new Cookie("userid",Integer.toString(user.getId()));
                        Cookie nameCook =new Cookie("username",user.getUserName());
                        Cookie pwdCook =new Cookie("userpwd",user.getUserPwd());
                        Cookie emailCook =new Cookie("email",user.getEmail());
                        Cookie fnameCook =new Cookie("fname",user.getFirstName());
                        Cookie lnameCook =new Cookie("lname",user.getLastName());
                        Cookie sessionCook = new Cookie("sessionid", sessionId);
                        response.addCookie(useridCook);
                        response.addCookie(nameCook);
                        response.addCookie(pwdCook);
                        response.addCookie(emailCook);
                        response.addCookie(fnameCook);
                        response.addCookie(lnameCook);
                        response.addCookie(sessionCook);
                        udao.insertUserLogonValidateInfo(uname, sessionId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(success){
            //login successfully,set the user to session
            System.out.println("set user in to session"+request.getSession().getId());
            request.getSession().setAttribute("user", user);
//            String url = "/timeline.jsp?username="+uname;
            String url = "/timeline.jsp";
//            String url="/titleBar.jsp";
            System.out.println(url);
//            response.sendRedirect(url);
            request.getRequestDispatcher(url).forward(request, response);
        }else{
        	response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.print("那个...密码貌似不对啊，请再输一次");
			out.flush();
			out.close();
//            response.sendRedirect("/user/login.jsp");
        }
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//        out.println("<HTML>");
//        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//        out.println("  <BODY>");
//        out.print("    This is ");
//        out.print(this.getClass());
//        out.println(", using the POST method");
//        out.println("  </BODY>");
//        out.println("</HTML>");
//        out.flush();
//        out.close();
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
