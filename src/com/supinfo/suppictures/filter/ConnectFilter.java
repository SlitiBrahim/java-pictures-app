package com.supinfo.suppictures.filter;

import com.supinfo.suppictures.dao.MemberDAO;
import com.supinfo.suppictures.entity.Member;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "ConnectFilter", urlPatterns = {"/*"})
public class ConnectFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        String path = request.getServletPath();

        String[] authorized = new String[] {
                "/login",
                "/register",
                "/picture",
                "/logout",
                "/checkLoginServlet",
                "/checkRegisterServlet",
                "/index",
        };

        for (String pathStart:authorized ) {
            if (path.startsWith(pathStart)){
                chain.doFilter(req, resp);
            }
        }

        if (!path.startsWith("/index")){
            if (session.getAttribute("id") != null){
                MemberDAO memberDAO = new MemberDAO();
                Member currentMember = memberDAO.find((Long) session.getAttribute("id"));
                if (currentMember != null){
                    chain.doFilter(req, resp);
                }
                else {
                    response.sendRedirect(request.getContextPath()+"/login");
                }
            }
            else {
                response.sendRedirect(request.getContextPath()+"/login");
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
