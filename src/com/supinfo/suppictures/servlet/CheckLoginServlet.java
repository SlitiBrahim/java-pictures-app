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
import java.util.Map;

@WebServlet(name = "CheckLoginServlet", urlPatterns = "/checkLoginServlet")
public class CheckLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameters = request.getParameterMap();
        Object[] isParams = checkParams(parameters);
        boolean loggable = (boolean) isParams[0];

        if (loggable){

            MemberDAO memberDAO = new MemberDAO();
            Member currentUser = memberDAO.ComparePassword(request.getParameter("username"), request.getParameter("password"));

            if (currentUser != null){
                HttpSession session = request.getSession();
                session.setAttribute("id", currentUser.getId());
                session.setAttribute("username", currentUser.getUsername());
                session.setAttribute("admin", currentUser.getIsAdmin());

                response.sendRedirect(request.getContextPath()+"/");
            }
            else{
                request.setAttribute("error", "This user doesn't exist - Check username or password");
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
                rd.forward(request, response);
            }
        }else {
            request.setAttribute("error", isParams[1]);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
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
                        return new Object[]{false, entry.getKey() + " wrong format !"};
                    }
                    break;
                case "password":
                    if (!entry.getValue()[0].matches("[a-zA-Z0-9:;./,_èéà()=+!ù%&]{3,50}")) {
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
