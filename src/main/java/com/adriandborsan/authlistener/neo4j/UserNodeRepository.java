package com.adriandborsan.authlistener.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserNodeRepository extends Neo4jRepository<UserNode,String> {
}