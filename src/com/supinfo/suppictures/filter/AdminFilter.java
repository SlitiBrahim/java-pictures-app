package com.supinfo.suppictures.filter;

import com.supinfo.suppictures.dao.MemberDAO;
import com.supinfo.suppictures.entity.Member;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        String path = request.getServletPath();

        if (session.getAttribute("id") != null){
            MemberDAO memberDAO = new MemberDAO();
            Member currentMember = memberDAO.find(Long.parseLong((String) session.getAttribute("id")));
            if (currentMember.getIsAdmin()){
                chain.doFilter(req, resp);
            }
            else{
                response.sendRedirect(request.getContextPath()+"/index");
            }
        }
        else{
            response.sendRedirect(request.getContextPath()+"/index");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
