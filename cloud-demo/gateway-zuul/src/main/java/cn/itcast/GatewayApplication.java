package cn.itcast;

import cn.itcast.filter.LoginFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
public class GatewayApplication {
    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class,args);
    }
}
