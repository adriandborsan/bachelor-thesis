package com.adriandborsan.clientback.nodes;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class UserNode {
    @Id
    private final String id;
}

//    @Relationship(type = "FRIEND", direction = Relationship.Direction.OUTGOING)
//    private List<Friendship> friendships = new ArrayList<>();
//
//    @Relationship(type = "REQUESTED", direction = Relationship.Direction.OUTGOING)
//    private List<FriendRequest> friendRequests = new ArrayList<>();