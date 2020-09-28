package com.supinfo.suppictures.servlet;

import com.supinfo.suppictures.dao.MemberDAO;
import com.supinfo.suppictures.entity.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "CheckRegisterServlet", urlPatterns = "/checkRegisterServlet")
public class CheckRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameters = request.getParameterMap();
        Object[] isParams = checkParams(parameters);
        boolean registrable = (boolean) isParams[0];

        if (registrable){

            MemberDAO memberDAO = new MemberDAO();
            Member newMember = new Member(
                    (long) 0,
                    request.getParameter("username"),
                    request.getParameter("password"),
                    request.getParameter("firstname"),
                    request.getParameter("lastname"),
                    request.getParameter("phone"),
                    request.getParameter("address"),
                    request.getParameter("email"),
                    false
            );
            Member getReturnedMember = memberDAO.create(newMember);
            if (getReturnedMember == null){
                request.setAttribute("error", "Failed");
            }
        }else {
            request.setAttribute("error", isParams[1]);
        }

        if (request.getAttribute("error") == null){
            response.sendRedirect(request.getContextPath()+"/login");
        }
        else {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
            rd.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private Object[] checkParams(Map<String, String[]> parameters) {
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            if (entry.getValue()[0].isEmpty()) {
                return new Object[]{false, entry.getKey() + " is missing !"};
            }
            switch (entry.getKey()) {
                case "username":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9]{3,50}")) {
                        return new Object[]{false, entry.getKey() + " wrong format ! Only letters and numbers"};
                    }
                    break;
                case "password":
                case "confirm":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9:;./,_èéà()=+!ù%&]{3,50}")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }

                    if (entry.getKey().equals("confirm")){
                        if (!entry.getValue()[0].equals(parameters.get("password")[0])){
                            return new Object[]{false, "Check password and confirm password - not the same"};
                        }
                    }
                    break;
                case "firstname":
                case "lastname":
                    if (!entry.getValue()[0].matches("[a-zA-Z]{3,50}")) {
                        return new Object[]{false, entry.getKey() + " wrong format ! - Only letters"};
                    }
                    break;
                case "address":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9\\s,']*")) {
                        return new Object[]{false, entry.getKey() + " wrong format ! - Letters numbers spaces , ' authorized"};
                    }
                    break;
                case "email":
                    if (!entry.getValue()[0].matches("^[A-Za-z0-9._%+]+@[A-Za-z0-9.]+\\.[A-Za-z]{2,6}$")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                case "phone":
                    if (!entry.getValue()[0].matches("[0-9\\s]*")) {
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                default:
                    break;
            }
        }
        return new Object[]{true};
    }
}
