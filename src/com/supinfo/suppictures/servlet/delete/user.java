package com.supinfo.suppictures.servlet.delete;

import com.supinfo.suppictures.dao.MemberDAO;
import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Member;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delete_user", urlPatterns = "/admin/delete/user")
public class user extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            // send a bad request response if not "q" parameter provided
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            MemberDAO memberDAO = new MemberDAO();
            PostDAO postDAO= new PostDAO();
            Member deleteUser = memberDAO.find(Long.parseLong(id));

            postDAO.deleteAllPostOfMember(Long.parseLong(id));

            memberDAO.delete(deleteUser);

            RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);
        }

    }
}
