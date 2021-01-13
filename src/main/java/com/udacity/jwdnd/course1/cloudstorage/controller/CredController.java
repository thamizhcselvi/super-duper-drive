package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/cred")
public class CredController {
    private CredentialsService credentialsService;
    private UserService userService;
    private HashService hashService;
    private EncryptionService encryptionService;

    public CredController(CredentialsService credentialsService, UserService userService,HashService hashService,EncryptionService encryptionService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    @PostMapping(value = {"/addCred"})

    public String addCred(Authentication auth, Credential credential, Model model) {

        Integer auth_userid = userService.getUser(auth.getName()).getUserId();

        Credential encryptedCredential = encryptedCredential(credential);

        if(credential.getCredentialid() == null){
            encryptedCredential.setUserId(userService.getUser(auth.getName()).getUserId());
            credentialsService.addCred(encryptedCredential);
            model.addAttribute("resultSuccessMessage","Yor changes were successfully saved.");
        }
        else{
            Credential editedCredential = credentialsService.getCred(credential.getCredentialid());
            credential.setUserId(editedCredential.getUserId());
            if(auth_userid.equals(credential.getUserId()))
            {
                credentialsService.update(credential);
                model.addAttribute("resultSuccessMessage","Yor changes were successfully saved.");
            }
            else
            {
                model.addAttribute("resultErrorMessage","Do you have access to this credential?");
            }
        }

        return "/result";
    }
    @GetMapping("/getCred/{credentialid}")
    @ResponseBody
    public Credential getCred(@PathVariable(value = "credentialid") String credentialid,Model model,Credential credential,Authentication auth) {
        return credentialsService.getCred(Integer.parseInt(credentialid));
    }
    @GetMapping("/deleteCred/{credentialid}")
    public String deleteCred(Authentication auth,@PathVariable(name = "credentialid") String credIdToDelete,Model model) throws InvalidParameterException
    {
        Integer auth_userId = userService.getUser(auth.getName()).getUserId();
        try {
            Integer credId = Integer.parseInt(credIdToDelete);
            Credential credential = credentialsService.getCred(credId);
            if (auth_userId.equals(credential.getUserId())) {
                credentialsService.deleteCred(credId);
                model.addAttribute("resultSuccessMessage", "Your note was successfully deleted.");
            } else {
                model.addAttribute("resultErrorMessage", "You do have access to this note");
            }
        }
        catch(NumberFormatException e)
        {
            throw new InvalidParameterException();
        }
        return "/result";
    }

    private Credential encryptedCredential(Credential credential)
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedKey;
        String encrytedPassword;
        String password = credential.getPassword();
        if(credential.getCredentialid()==null){
            encodedKey = Base64.getEncoder().encodeToString(salt);
        }
        else{
            encodedKey = credential.getKey();
        }
        encrytedPassword = encryptionService.encryptValue(password,encodedKey);
        credential.setPassword(encrytedPassword);
        credential.setKey(encodedKey);
        return credential;
    }

}
