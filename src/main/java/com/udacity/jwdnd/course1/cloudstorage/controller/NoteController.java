package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.Objects;

@Controller
@RequestMapping("/note")
public class NoteController {
    private NotesService notesService;
    private UserService userService;

    public NoteController(NotesService notesService, UserService userService) {
        this.notesService = notesService;
        this.userService = userService;
    }


    @PostMapping(value={"/addNote"})

    public String addNote(Authentication auth,NoteForm noteForm, Model model)
    {
        Integer auth_userid = userService.getUser(auth.getName()).getUserId();
        Notes notes = null;
        if(noteForm.getNoteid() == null)
        {
            notes = new Notes();
            notes.setNotetitle(noteForm.getNotetitle());
            notes.setNotedescription(noteForm.getNotedescription());
            notes.setUserId(userService.getUser(auth.getName()).getUserId());
            notesService.addNote(notes);
            model.addAttribute("resultSuccessMessage","Your changes were successfully saved.");
        }
        else
        {
            notes = notesService.getNote(noteForm.getNoteid());
            if(auth_userid.equals(notes.getUserId()))
            {
                notes.setNotetitle(noteForm.getNotetitle());
                notes.setNotedescription(noteForm.getNotedescription());
                notesService.update(notes);
                model.addAttribute("resultSuccessMessage","Your changes were successfully saved.");
            }
            else
            {
                model.addAttribute("resultErrorMessage","Do you have access to this note?");
            }
        }
        return "/result";
    }

    @GetMapping("/getNote/{noteid}")
    @ResponseBody
    public Notes getNote(@PathVariable(name = "noteid") String noteid) {
        return notesService.getNote(Integer.parseInt(noteid));
    }

    @GetMapping("/deleteNote/{noteid}")
    public String deleteNote(Authentication auth,@PathVariable(name = "noteid") String noteIdToDelete,Model model) throws InvalidParameterException
    {
        Integer auth_userId = userService.getUser(auth.getName()).getUserId();
        try {
            Integer noteId = Integer.parseInt(noteIdToDelete);
            Notes notes = notesService.getNote(noteId);
            if (auth_userId.equals(notes.getUserId())) {
                notesService.deleteNote(noteId);
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
}
