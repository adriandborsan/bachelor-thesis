package com.adriandborsan.clientback;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.adriandborsan.clientback.post"
//)
//public class MysqlConfig {
//
//    @Value("${spring.authmysql1.url}")
//    private String dbUrl;
//
//    @Value("${spring.authmysql1.username}")
//    private String username;
//
//    @Value("${spring.authmysql1.password}")
//    private String password;
//
//    @Bean//(name = "mysqlDS")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url(dbUrl)
//                .username(username)
//                .password(password)
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .build();
//    }
//
////    @Bean(name = "entityManagerFactory")
////    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
////            EntityManagerFactoryBuilder builder,
////            @Qualifier("mysqlDS") DataSource dataSource
////    ) {
////        return builder
////                .dataSource(dataSource)
////                .packages("com.adriandborsan.clientback.post")
////                .persistenceUnit("mysql")
////                .build();
////    }
//}
