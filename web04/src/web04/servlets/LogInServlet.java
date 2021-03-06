package web04.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web04.dao.MySqlMemberDao;
import web04.vo.Member;

@WebServlet("/auth/login")
public class LogInServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginForm.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            ServletContext sc = this.getServletContext();
            MySqlMemberDao memberDao = (MySqlMemberDao)sc.getAttribute("memberDao");
            Member member = memberDao.exist(request.getParameter("email"), request.getParameter("password"));
            
            if(member != null ) {
            HttpSession session = request.getSession();
            session.setAttribute("member", member);
            request.setAttribute("viewUrl", "redirect:../member/list.do");
            } else {
                request.setAttribute("viewUrl", "/auth/LoginFail.jsp");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        } 
    }
}
