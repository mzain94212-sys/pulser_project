
// Model class

package com.example.producer;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String phone;
    private String website;
}
