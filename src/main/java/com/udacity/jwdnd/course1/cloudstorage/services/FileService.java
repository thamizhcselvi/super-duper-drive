package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.storage.FileSystemStorageService;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final FileSystemStorageService fileSystemStorageService;

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public FileService(FileMapper fileMapper,FileSystemStorageService fileSystemStorageService) {
        this.fileMapper = fileMapper;
        this.fileSystemStorageService = fileSystemStorageService;
    }

    public void addFile(MultipartFile file, Integer userId)  {
        try{
            Path copyLocation = Paths.get(uploadDir+ File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            java.nio.file.Files.copy(file.getInputStream(),copyLocation, StandardCopyOption.REPLACE_EXISTING);

            Files upload = new Files();
            upload.setFilename(file.getOriginalFilename());
            upload.setFilepath(copyLocation.toString());
            upload.setContenttype(file.getContentType());
            upload.setFiledata(file.getBytes());
            upload.setFilesize(String.valueOf(file.getSize()));
            upload.setUserId(userId);
            this.fileMapper.insert(upload);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new StorageFileNotFoundException("Could not store file " + file.getOriginalFilename() + ". Please try again");
        }
    }

    public Files getFile(Integer fileId)
    {
        return fileMapper.select(fileId);
    }

    public List<Files> getAllFiles(Integer userId) throws Exception
    {
        List<Files> files = fileMapper.getAllFilesById(userId);
        if(files == null)
        {
            throw new Exception();
        }
        return files;
    }

    public boolean deleteFile(String fileId)
    {
        return fileMapper.deleteById(fileId);
    }
}
