package com.adriandborsan.clientback.post.nodes;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
@Data
public class PostNode {
    @Id
    private final Long id;

    @Relationship(type = "POSTED_BY", direction = Relationship.Direction.INCOMING)
    private UserNode author;

}