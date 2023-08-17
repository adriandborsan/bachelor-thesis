package com.adriandborsan.authlistener.accounts.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserNodeRepository extends Neo4jRepository<UserNode,String> {
}
