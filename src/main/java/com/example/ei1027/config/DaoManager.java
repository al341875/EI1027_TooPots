package com.example.ei1027.config;

import com.example.ei1027.dao.ActivitatDao;
import com.example.ei1027.dao.ClientDao;
import com.example.ei1027.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by CIT on 23/05/2019.
 */
@Component
public class DaoManager {
    @Autowired
    private ActivitatDao activitatDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserDao userDao;

    public ActivitatDao getActivitatDao() {
        return activitatDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
