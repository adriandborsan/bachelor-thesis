package com.adriandborsan.clientback.post.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FileEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String path;

    private String mimeType;

    private String originalName;

}
