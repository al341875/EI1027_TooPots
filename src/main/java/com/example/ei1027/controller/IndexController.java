package com.example.ei1027.controller;

import com.example.ei1027.dao.ImatgesPromocionalsDao;
import com.example.ei1027.model.ImatgePromocional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by CIT on 24/06/2019.
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ImatgesPromocionalsDao imatgesPromocionalsDao;

    @GetMapping
    public String index(Model model) {
        List<ImatgePromocional> list = imatgesPromocionalsDao.findAll();
        model.addAttribute("imatges", list);
        model.addAttribute("imgPromo", true);
        return "index";
    }
}
