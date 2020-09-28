package com.supinfo.suppictures.servlet.delete;


import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delete_category", urlPatterns = "/admin/delete/category")
public class category extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Category category = new Category(Long.parseLong(id), "temp");
            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.delete(category);

            RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);
        }
    }
}
