package com.supinfo.suppictures.servlet;

import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.entity.Category;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "PostServlet", urlPatterns = "/post")
public class PostServlet extends HttpServlet{

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {

        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.findAllCategory();

        request.setAttribute("categoryList", categoryList);
        RequestDispatcher rd = request.getRequestDispatcher("/post.jsp");
        rd.forward(request, response);
    }
}
