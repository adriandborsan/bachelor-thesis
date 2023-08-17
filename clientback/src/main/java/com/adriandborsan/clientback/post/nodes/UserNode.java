package com.adriandborsan.clientback.post.nodes;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class UserNode {
    @Id
    private final String id;
}
