package com.adriandborsan.clientback;

//@Configuration
//@EnableNeo4jRepositories(
//        basePackages = "com.adriandborsan.clientback.nodes",
//        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.adriandborsan.clientback.post.*")
//)
//
//public class Neo4jConfig {
//
//    @Value("${spring.authneo4j1.uri}")
//    private String neo4jUri;
//
//    @Value("${spring.authneo4j1.authentication.username}")
//    private String username;
//
//    @Value("${spring.authneo4j1.authentication.password}")
//    private String password;
//
//    @Bean
//    public Driver neo4jDriver() {
//        return GraphDatabase.driver(neo4jUri, AuthTokens.basic(username, password));
//    }
//}














//
//@Configuration
//@EnableNeo4jRepositories(basePackages = "com.adriandborsan.clientback.nodes",
//        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.adriandborsan.clientback.post.*"))
//public class Neo4jConfig {
//
//
//    @Value("${spring.neo4j.uri}")
//    private String uri;
//
//    @Value("${spring.neo4j.authentication.username}")
//    private String username;
//
//    @Value("${spring.neo4j.authentication.password}")
//    private String password;
//
//    @Bean
//    public Driver driver() {
//        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
//    }
//
//    @Bean
//    public Neo4jClient neo4jClient(Driver driver) {
//        return Neo4jClient.create(driver);
//    }
//
//    @Bean
//    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient) {
//        return new Neo4jTemplate(neo4jClient);
//    }
//    @Bean
//    public Neo4jMappingContext neo4jMappingContext()
//    {
//        return new Neo4jMappingContext();
//    }
//    @Bean
//    public Neo4jDataProperties neo4jDataProperties(){
//        Neo4jDataProperties neo4jDataProperties = new Neo4jDataProperties();
//        return neo4jDataProperties;
//    }
//
//    // Other beans as necessary
//}
