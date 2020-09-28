package com.supinfo.suppictures.servlet.api;

import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ApiSearchServlet", urlPatterns = "/api/search")
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        PostDAO postDAO = new PostDAO();
        CategoryDAO categoryDAO = new CategoryDAO();

        String query = request.getParameter("q");
        String categoryIdparam = request.getParameter("categoryId");
        Long categoryId = null;

        if (query == null || query.isEmpty()) {
            // send a bad request response if not "q" parameter provided
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        if (categoryIdparam != null && !categoryIdparam.isEmpty()) {
            categoryId = categoryDAO.find(Long.parseLong(categoryIdparam)).getId();
        }

        List<Post> posts = postDAO.findPostsBySearch(query, categoryId);
        // convert posts to json array
        String jsonPosts = Post.toJson(posts);

        String jsonRes = String.format(
            "{" +
                "\"items\": [" +
                    "%s" +
                "]" +
            "}"
        , jsonPosts != null ? jsonPosts : "");

        // return posts as json
        out.print(jsonRes);
    }
}
