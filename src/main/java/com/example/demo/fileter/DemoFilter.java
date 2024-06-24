package com.example.demo.fileter;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.po.Result;
import com.example.demo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
//@WebFilter(urlPatterns = "/*")
public class DemoFilter implements Filter {
    // 静态资源或不需要JWT验证的URL前缀列表
    private static final String[] EXCLUDED_PATHS = {"/v2/api-docs", "/swagger-ui.html", "/webjars/", "/login/*","/swagger-resources","/doc.html"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ini 初始化了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截了请求");
        HttpServletRequest ret = (HttpServletRequest) servletRequest;
        HttpServletResponse retp= (HttpServletResponse) servletResponse;
        String url=ret.getRequestURI().toString();
        log.info("请求的url: {}",url);
        // 检查是否是需要放行的URL
        if (HttpMethod.OPTIONS.toString().equals(ret.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            filterChain.doFilter(servletRequest,servletResponse);
        }
        if(url.contains("login")){
            log.info("登录操作, 放行...");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        for (String excludedPath : EXCLUDED_PATHS) {
            if (url.startsWith(excludedPath)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return; // 放行后直接返回，不再执行后续逻辑
            }
        }
        log.info("interecptor 的url: {}",url);

       // if(url.contains("doc"))filterChain.doFilter(servletRequest,servletResponse);

/*        if(url.contains("login")){
            log.info("登录操作");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }*/
        log.info(ret.getHeader("Authorization"));

        String Jwt=null;
        if(ret.getMethod().equals("OPTIONS")){
            retp.setStatus(HttpServletResponse.SC_OK);
        }else{
            Jwt= ret.getHeader("Authorization");
        }
        if(!StringUtils.hasLength(Jwt)){
            log.info("令牌为空");
            Result error= Result.error("NOT_LOGIN");
            String notlogin= JSONObject.toJSONString(error);
            retp.getWriter().write(notlogin);
            return;
        }
        try {
            JwtUtils.parseJWT(Jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("解析令牌出错");
            Result error= Result.error("登录已过期或未登录");
            String notlogin= JSONObject.toJSONString(error);
            retp.getWriter().write(notlogin);
            return;
        }

        log.info("正确");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("删除了请求");
    }
}
