package com.adriandborsan.clientback.nodes;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PostNodeRepository extends Neo4jRepository<PostNode,Long> {
}
