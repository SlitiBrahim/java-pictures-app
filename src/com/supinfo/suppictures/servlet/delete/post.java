package com.supinfo.suppictures.servlet.delete;


import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delete_post", urlPatterns = "/admin/delete/post")
public class post extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            // send a bad request response if not "q" parameter provided
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            PostDAO postDAO = new PostDAO();
            Post deletePost = postDAO.find(Long.parseLong(id));
            postDAO.delete(deletePost);

            RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);
        }
    }
}
