package com.example.ei1027.controller;


import com.example.ei1027.dao.AcreditaDao;
import com.example.ei1027.dao.AcreditacioDao;
import com.example.ei1027.model.Acredita;
import com.example.ei1027.model.Acreditacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/acreditacio")
public class AcreditacioController {

    @Autowired
    private AcreditacioDao acreditacioDao;
    @Autowired
    private AcreditaDao acreditaDao;
    @Value("${upload.file.directory}")
    private String uploadDirectory;
    @GetMapping(value = "/add")
    public String addAcreditacio(Model model) {
        model.addAttribute("acreditacio", new Acreditacio());
        model.addAttribute("acredita", new Acredita());
        return "acreditacio/add";
    }

    @PostMapping(value = "/add")
    public String addAcreditacio(@ModelAttribute("acreditacio") Acreditacio acreditacio, BindingResult bindingResult, @SessionAttribute("username") String user,
                                 @RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "acreditacio/add";
        }
        if (file.isEmpty()) {
            // Enviar mensaje de error porque no hay fichero seleccionado
            redirectAttributes.addFlashAttribute("message",
                    "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {
            // Obtener el fichero y guardarlo
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDirectory + "pdfs/"
                    + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        acreditacio.setCertificatUrl(file.getOriginalFilename());
        acreditacio.setIdInstructor(user);
        acreditacio.setEstat("pendent");
        acreditacioDao.addAcreditacio(acreditacio);
        return "redirect:list";
    }
    @RequestMapping(value = "/pdf/{id}")
    public String mostrarAcreditacio(Model model,@PathVariable String id) {
        model.addAttribute("acreditacio", acreditacioDao.getAcreditacio(id));
        return "acreditacio/list";
    }
    @RequestMapping(value = "/list/{id}")
    public String mostrarAcreditacions(Model model,@PathVariable String id) {
        model.addAttribute("acreditacions", acreditacioDao.getAcreditacio2(id));
        return "acreditacio/list";
    }
    @RequestMapping(value = "/pdfs/{id}")
    public String mostrarImatge(Model model,@PathVariable String id) {
        model.addAttribute("client", acreditacioDao.getPdf(id));
        return "client/list";
    }

}