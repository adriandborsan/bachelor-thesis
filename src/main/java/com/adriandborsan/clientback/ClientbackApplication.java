package com.adriandborsan.clientback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
//@EnableNeo4jRepositories(basePackages = "com.adriandborsan.clientback.nodes")
//@EnableJpaRepositories(basePackages = "com.adriandborsan.clientback.post")
public class ClientbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientbackApplication.class, args);
    }

}
