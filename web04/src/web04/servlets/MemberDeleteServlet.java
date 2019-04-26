package web04.servlets;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            ServletContext sc = this.getServletContext();
            Class.forName(sc.getInitParameter("driver"));
            conn = DriverManager.getConnection(sc.getInitParameter("url"), sc.getInitParameter("username"), sc.getInitParameter("password"));
            stmt = conn.prepareStatement("delete from members where mno = ?");
            stmt.setInt(1, Integer.parseInt(request.getParameter("no")));
            stmt.executeUpdate();
            response.sendRedirect("list");
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if(stmt !=null) stmt.close();} catch (Exception e) {}
            try { if (conn != null) conn.close();} catch (Exception e) {}
        }
    }
}