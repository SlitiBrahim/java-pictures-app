package com.supinfo.suppictures.servlet;

import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PictureServlet", urlPatterns = "/picture")
public class PictureServet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            // send a bad request response if not "q" parameter provided
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else {
            PostDAO postDAO = new PostDAO();
            Post picture = postDAO.find( Long.parseLong(id));

            RequestDispatcher rd = request.getRequestDispatcher("picture.jsp");
            request.setAttribute("picture", picture);
            rd.forward(request, response);
        }

    }

}
