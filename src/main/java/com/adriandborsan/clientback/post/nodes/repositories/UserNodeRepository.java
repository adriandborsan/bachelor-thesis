package com.adriandborsan.clientback.post.nodes.repositories;

import com.adriandborsan.clientback.post.nodes.UserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserNodeRepository extends Neo4jRepository<UserNode,String> {
}
