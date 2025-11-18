package com.smartcinema.cinema_api.dao;

import com.smartcinema.cinema_api.entities.Role;
import com.smartcinema.cinema_api.entities.User;

public interface AuthDAO {
    User save(User theUser);
    boolean existsByEmail(String email);
    Role saveRole(Role role);
}
