package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void addNote(Notes notes) {
        notesMapper.insert(notes);

    }
    public Notes getNote(Integer noteid) {
        return notesMapper.select(noteid);
    }

    public List<Notes> getAllNotes(Integer userId)
    {
        return notesMapper.getAllNotesById(userId);
    }

    public void update(Notes notes) {
        notesMapper.update(notes);
    }

    public void deleteNote(Integer noteid) {
        notesMapper.delete(noteid);
    }
}
