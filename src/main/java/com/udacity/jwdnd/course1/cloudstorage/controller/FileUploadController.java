package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.storage.StorageService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/file")
public class FileUploadController {
    private final StorageService storageService;
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FileUploadController(StorageService storageService, FileService fileService,UserService userService) {
        this.storageService = storageService;
        this.fileService = fileService;
        this.userService = userService;
    }
    @GetMapping("/view")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "/download";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }



    @GetMapping("/delete/{fileId}")
    public String delete(@PathVariable (value= "fileId") String fileId, Authentication authentication,Model model) throws InvalidParameterException {
        Integer auth_userId = userService.getUser(authentication.getName()).getUserId();
        try{
            Integer fileToDelete = Integer.parseInt(fileId);
            Files files = fileService.getFile(fileToDelete);
            if(auth_userId.equals(files.getUserId()))
            {
                fileService.deleteFile(fileId);
                model.addAttribute("resultSuccessMessage","Your changes were successfully saved.");
            }
            else {
                model.addAttribute("resultErrorMessage", "You do have access to this file");
            }
        }
        catch(NumberFormatException e)
        {
            throw new InvalidParameterException();
        }

        return "/result";
    }


    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Files files, Model model, Authentication authentication) throws Exception {

        Integer auth_userid = userService.getUser(authentication.getName()).getUserId();

            if(file.isEmpty())
            {
                redirectAttributes.addFlashAttribute("errorMessage","Please select a file.");
                return "redirect:/home";
            }

            if(isFileExist(file,auth_userid))
            {
                redirectAttributes.addFlashAttribute("errorMessage","File already exists, upload a different file.");
                return "redirect:/home";
            }
            storageService.store(file);
            fileService.addFile(file,auth_userid);
            model.addAttribute("resultSuccessMessage","Successfully saved the file.");
        return "/result";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private boolean isFileExist(MultipartFile filename,Integer userId) throws Exception {
        for(Files file:fileService.getAllFiles(userId))
        {
            String userFilename = StringUtils.cleanPath(filename.getOriginalFilename());
            if(file.getFilename().equalsIgnoreCase(userFilename))
            {
                return true;
            }
        }
        return false;
    }
}
