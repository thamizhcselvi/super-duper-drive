package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.*;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileSystemStorageService;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private NotesService notesService;
    private CredentialsService credentialsService;
    private FileSystemStorageService fileSystemStorageService;
    private StorageService storageService;
    private FileService fileService;

    public HomeController(UserService userService, NotesService notesService, CredentialsService credentialsService,FileSystemStorageService fileSystemStorageService,StorageService storageService,FileService fileService) {
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
        this.fileSystemStorageService = fileSystemStorageService;
        this.storageService = storageService;
        this.fileService = fileService;
    }

    @GetMapping()
    public String homePage(Authentication authentication,Model model) throws Exception {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("resultNotes",notesService.getAllNotes(userId));
        model.addAttribute("resultCred",credentialsService.getAllCredentials(userId));
        model.addAttribute("resultFiles",fileService.getAllFiles(userId));
        return "home";
    }
}
