package com.adriandborsan.clientback.nodes;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
public class PostNode {
    @Id
    private final Long id;

    @Relationship(type = "POSTED_BY", direction = Relationship.Direction.INCOMING)
    private UserNode author;

    @Relationship(type = "LIKED_BY", direction = Relationship.Direction.INCOMING)
    private List<UserNode> likedByUsers=new ArrayList<>();
}