package com.hywang.timeline.servlet.handled;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hywang.timeline.DAOFactory;
import com.hywang.timeline.dao.UserDAO;
import com.hywang.timeline.entity.User;
import com.hywang.timeline.utils.CookiesManager;

public class LogoutServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -39096014829735819L;

    /**
     * Constructor of the object.
     */
    public LogoutServlet() {
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

        HttpSession session = request.getSession();
        Object userObject = session.getAttribute("user");
        if (userObject != null) { // 1.remove from session
            User user = (User) userObject;
            String username = user.getUserName();
            session.removeAttribute("user");
            System.out.println("delete user from session: "+session.getId());
            String sessionid = CookiesManager.getValue(request.getCookies(), "sessionid");
            UserDAO userDao = DAOFactory.getInstance().createUserDAO();
            try {
                userDao.deleteAutoLoginState(username, sessionid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url = "/timeline.jsp";
        // String url="/titleBar.jsp";
        System.out.println(url);
        // response.sendRedirect(url);
        request.getRequestDispatcher(url).forward(request, response);
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
