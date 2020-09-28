package com.supinfo.suppictures.servlet;

import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.dao.PostDAO;
import com.supinfo.suppictures.entity.Category;
import com.supinfo.suppictures.entity.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;

@WebServlet(name = "UploadServlet", urlPatterns = "/uploadServlet")
@MultipartConfig(maxFileSize = 32354430) //32Mb soit 4Mo
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        InputStream inputStream = null;

        HttpSession session = request.getSession();

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.findAllCategory();
        request.setAttribute("categoryList", categoryList);

        //Get the image file
        Part filePart = request.getPart("picture");

        //Check parameters
        Map<String, String[]> parameters = request.getParameterMap();
        Object[] isParams = checkParams(parameters);
        boolean saveable = (boolean) isParams[0];

        //return field missing error
        if (!saveable){
            request.setAttribute("categoryList", categoryList);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/post.jsp");
            rd.forward(request, response);
        }

        if (saveable && filePart != null && filePart.getContentType().substring(0, 5).equals("image")) {
            if (filePart.getSize() <= 32354430){

                String typeImage = filePart.getContentType().substring(6);

                inputStream = filePart.getInputStream();
                String uniqueID = UUID.randomUUID().toString()+"."+typeImage;
                PrintWriter writer = response.getWriter();

                String path = this.getClass().getClassLoader().getResource("").getPath();
                String fullPath = URLDecoder.decode(path, "UTF-8");
                String initPatchProject[] = fullPath.split("/out");

                String uploadFilePath = initPatchProject[0].substring(1)+"/web"; //To stock

                File uploadFolder = new File(uploadFilePath+"/img");
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                String pathToSave = "/img/" + uniqueID;
                String contentType = filePart.getContentType();
                filePart.write(uploadFilePath + pathToSave);

                //TODO change user id
                PostDAO postDAO = new PostDAO();
                Post post = new Post(
                        request.getParameter("title"),
                        request.getParameter("description"),
                        pathToSave,
                        request.getParameter("locality"),
                        Long.parseLong(request.getParameter("category")),
                        (Long) session.getAttribute("id")
                );

                Post newPost = postDAO.create(post);
                if (newPost == null){
                    request.setAttribute("error", "Failed");
                }
                else {
                    request.setAttribute("success", "Your post have been created !");
                }

            }
            else {
                request.setAttribute("error", "Image size exceed 32 Mb");
            }
        }
        else {
            request.setAttribute("error", "missing Picture");
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/post.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean checkCategoryId(String categoryLong){
        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = categoryDAO.find(Long.parseLong(categoryLong));

        return category != null;
    }

    private Object[] checkParams(Map<String, String[]> parameters){
        for (Map.Entry<String, String[]> entry : parameters.entrySet())
        {
            if (entry.getValue()[0].isEmpty()){
                return new Object[]{false, entry.getKey() + " is missing !"};
            }
            switch (entry.getKey()){
                case "title":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9!';.,éèçà()\\s]{3,50}")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                case "description":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9 !';.,àéè()\\s]{3,300}")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                case "locality":
                    if (!entry.getValue()[0].matches("[a-zA-Z]{2,20}")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                case "category":
                    if (!checkCategoryId(entry.getValue()[0])){
                        return new Object[]{false, entry.getKey() + " doesn't exist !"};
                    }
                    break;
                default:
                    System.out.println("checkParam default case switch");
                    break;
            }
        }
        return new Object[]{true};
    }
}
