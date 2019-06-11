package cn.itcast.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        //需要在默认的 SendErrorFilter 之前
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // Needs to run before SendErrorFilter which has filterOrder == 0
    }

    @Override
    public boolean shouldFilter() {
        // only forward to errorPath if it hasn't been forwarded to already
        //true就是启用过滤，false就是不启用过滤
        return true;
    }

    @Override
    public Object run() {
        try {
            //获取请求上下文
            RequestContext ctx = RequestContext.getCurrentContext();
            //获取request
            HttpServletRequest request = ctx.getRequest();
            //获取请求参数access-token
            String token = request.getParameter("access-token");
            //判断是否存在
            if(StringUtils.isBlank(token)){
                //不存在，未登陆，则拦截
                ctx.setSendZuulResponse(false);
                //返回403禁止访问
                ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

