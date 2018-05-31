package demo.web;

import demo.impl.MessageWall_and_RemoteLogin_Impl;
import demo.impl.UserAccess_Impl;
import demo.spec.MessageWall;
import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String view = perform_action(request);
        forwardRequest(request, response, view);
    }
    
    protected String perform_action(HttpServletRequest request)
        throws IOException, ServletException {
        
        String serv_path = request.getServletPath();
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        
      MessageWall_and_RemoteLogin_Impl mw = (MessageWall_and_RemoteLogin_Impl)getRemoteLogin();
      UserAccess connection = (UserAccess)session.getAttribute("useraccess");  
        
        
        if (serv_path.equals("/login.do")) {
        String usr = request.getParameter("user");
        String pwd = request.getParameter("password");
        UserAccess user = mw.connect(usr, pwd);
        if (user !=null){
        session.setAttribute("useraccess", user);
        return("/wallview");
        } 
        else{
            return("/authProblem.html");
        }
        }
        else if (serv_path.equals("/put.do")) {
           connection.put(request.getParameter("msg"));
            return "/wallview";
        } 
        else if (serv_path.equals("/refresh.do")) {
            return "/wallview";
        } 
         else if (serv_path.equals("/delete.do")) {
             UserAccess user =(UserAccess) session.getAttribute("useraccess");
            user.delete(Integer.parseInt((request.getParameter("index"))));
            return "/wallview";
        } 
        else if (serv_path.equals("/logout.do")) {
            session.removeAttribute("useraccess");
            return "/goodbye.html";
        } 
        else {
            return "/error-bad-action.html";
        }
    }

    public RemoteLogin getRemoteLogin() {
        return (RemoteLogin) getServletContext().getAttribute("remoteLogin");
    }
    public void forwardRequest(HttpServletRequest request, HttpServletResponse response, String view) 
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
        if (dispatcher == null) {
            throw new ServletException("No dispatcher for view path '"+view+"'");
        }
        dispatcher.forward(request,response);
    }
}


