package com.adriandborsan.authlistener.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class UserNode {
    @Id
    private final String id;
}

