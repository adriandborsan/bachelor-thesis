package com.adriandborsan.clientback.post.nodes.repositories;

import com.adriandborsan.clientback.post.nodes.PostNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PostNodeRepository extends Neo4jRepository<PostNode,Long> {
}
