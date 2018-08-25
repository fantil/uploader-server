package org.fanti.uploader.server.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/25
 */

public class CORSFilter implements Filter {
    public CORSFilter() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpServletResponse.setHeader(
                        "Access-Control-Allow-Headers",
                        "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken");

        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        httpServletResponse.setHeader("Access-Control-Max-Age", "1209600");

        httpServletResponse.setHeader("Access-Control-Expose-Headers", "accesstoken");

        httpServletResponse.setHeader("Access-Control-Request-Headers", "accesstoken");

        httpServletResponse.setHeader("Expires", "-1");

        httpServletResponse.setHeader("Cache-Control", "no-cache");

        httpServletResponse.setHeader("pragma", "no-cache");

        chain.doFilter(request, response);
    }


    public void init(FilterConfig fConfig) throws ServletException {

    }


    public void destroy() {

    }
}