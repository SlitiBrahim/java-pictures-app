package com.supinfo.suppictures.servlet.api;

import com.supinfo.suppictures.dao.MemberDAO;
import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "ApiPostServlet", urlPatterns = "/api/posts")
public class PostServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        String idParam = request.getParameter("id");

        // an id parameter provided
        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);

            getPost(response, id);
        }
        else {
            String limitParam = request.getParameter("limit");
            int limit = 15;

            if (limitParam != null && !limitParam.isEmpty()) limit = Integer.parseInt(limitParam);

            getPosts(response, limit);
        }
    }

    private PrintWriter getPrintWriter(HttpServletResponse response) {
        PrintWriter out = null;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out;
    }

    private void getPost(HttpServletResponse response, Long id) {
        PrintWriter out = getPrintWriter(response);
        assert out != null;

        PostDAO postDAO = new PostDAO();

        Post post = postDAO.find(id);

        // return post as json
        out.print(Post.toJson(post));
    }

    private void getPosts(HttpServletResponse response, int limit) {
        PrintWriter out = getPrintWriter(response);
        assert out != null;

        PostDAO postDAO = new PostDAO();

        List<Post> posts = postDAO.findAllPost(limit);
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
