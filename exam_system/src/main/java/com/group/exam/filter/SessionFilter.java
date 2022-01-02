//package com.group.exam.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.annotation.WebInitParam;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter(initParams = {@WebInitParam(name = "no_filter",value = "/login,/login.html")})
//public class SessionFilter implements Filter {
//
//    private static final String LOGIN_PAGE = "/login.html";
//    private static final String HOME_PAGE = "/";
//    private String[] no_filter;
//
//    @Override
//    public void init(FilterConfig filterConfig){
//        String noFilterString = filterConfig.getInitParameter("no_filter");
//        if (noFilterString!=null&&noFilterString.length()>0){
//            no_filter = noFilterString.split(",");
//        }
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        HttpSession session = request.getSession(false);
//        String uri = request.getRequestURI();
//
//
//        boolean needFilter = isNeedFilter(request);
//
//        if (!needFilter) { //不需要过滤直接传给下一个过滤器
//            if(session!=null&&session.getAttribute("UserID") != null) {
//                response.sendRedirect(request.getContextPath()+HOME_PAGE);
//            }else{
//                filterChain.doFilter(servletRequest, servletResponse);
//            }
//        } else { //需要过滤器
//            // session中包含user对象,则是登录状态
//            if(session!=null&&session.getAttribute("UserID") != null){
//                filterChain.doFilter(request, response);
//            }else{
//                String requestType = request.getHeader("X-Requested-With");
//                //判断是否是ajax请求
//                if("XMLHttpRequest".equals(requestType)){
//                    response.getWriter().write("NO_LOGIN");
//                }else{
//                    response.sendRedirect(request.getContextPath()+LOGIN_PAGE);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    public boolean isNeedFilter(HttpServletRequest request){
//        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
//        for (String s : no_filter) {
//            if (requestURI.contains(s)){
//                return false;
//            }
//        }
//        return true;
//    }
//}
