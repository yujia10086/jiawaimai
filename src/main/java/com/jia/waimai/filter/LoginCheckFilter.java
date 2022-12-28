package com.jia.waimai.filter;

import com.alibaba.fastjson.JSON;
import com.jia.waimai.common.BaseContext;
import com.jia.waimai.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求的uri
        String uri = request.getRequestURI();
        log.info("拦截到请求{}",uri);
        //定义不需要处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断请求是否需要处理
        boolean check = check(urls, uri);
        //如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",uri);
            filterChain.doFilter(request, response);
            return;
        }
        //判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee")!=null){
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("用户已登录");
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user")!=null){
            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info("用户已登录");
            filterChain.doFilter(request, response);
            return;
        }
        //如果未登录，返回未登录结果,通过输出流的方式向客户端相应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    //路径匹配，检查本次请求是否需要放行
    public boolean check(String[] urls,String uri){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, uri);
            if (match){
                return true;
            }
        }
        return false;

    }
}
