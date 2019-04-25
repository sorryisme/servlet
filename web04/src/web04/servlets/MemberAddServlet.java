package web04.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/add")
public class MemberAddServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>회원등록</title><meta charset=\"UTF-8\"></head>");
        out.println("<body><h1>회원 등록</h1>");
        out.println("<form action='add' method='post'>");
        out.println("이름 : <input type='text' name='name'><br>");
        out.println("이메일 : <input type='text' name='email'><br>");
        out.println("암호: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='추가'>");
        out.println("<input type='reset' value='취소' ");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/studydb", 
                    "root", 
                    "1111");
            stmt = conn.prepareStatement("insert into members(email,pwd,mname,cre_date,mod_date) values(?,?,?,now(),now())");
            stmt.setString(1, request.getParameter("email"));
            stmt.setString(2, request.getParameter("password"));
            stmt.setString(3, request.getParameter("name"));
            stmt.executeUpdate();

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>회원등록결과</title>");
            out.println("<meta http-equiv='Refresh' content='1; url=list'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>등록 성공입니다.</p>");
            out.println("</body></html>");
            
            //response.addHeader("Refresh", "1;url=list");

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try { if(stmt != null) stmt.close(); } catch (Exception e) {}
            try { if(conn != null) conn.close(); } catch (Exception e) {}
        }

    }

}
