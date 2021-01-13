package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final HashService hashService;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, HashService hashService, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.hashService = hashService;
        this.encryptionService = encryptionService;
    }

    public void addCred(Credential credential)
    {
        credentialsMapper.insert(credential);
    }

    public List<Credential> getAllCredentials(Integer userId)
    {
        return credentialsMapper.getAllCredsById(userId);
    }

    public int update(Credential credential)
    {
        return credentialsMapper.update(credential);
    }

    public void deleteCred(Integer credentialid) {
        credentialsMapper.deleteById(credentialid);
    }

    public Credential getCred(Integer credentialid) {
        Credential credential = credentialsMapper.select(credentialid);
        String encryptedPassword = credential.getPassword();
        String key = credential.getKey();
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword,key);
        credential.setPassword(decryptedPassword);
        return credential;
    }
}
