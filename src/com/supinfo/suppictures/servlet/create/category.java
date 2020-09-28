package com.supinfo.suppictures.servlet.create;

import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "create_category", urlPatterns = "/admin/create/category")
public class category extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        String name = request.getParameter("name");

        if (name == null || name.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            Category category = new Category(name);
            CategoryDAO categoryDAO = new CategoryDAO();
            categoryDAO.create(category);

            RequestDispatcher rd = request.getRequestDispatcher("/admin.jsp");
            rd.forward(request, response);
        }
    }
}
