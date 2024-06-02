package com.example.server.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("${api}/note")
class NoteController {

    private final NoteRepository noteRepository;

    NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
        noteRepository.createTable();
        noteRepository.deleteAll(); // TODO: remove this line
        save(1); // TODO: remove this line
        save(2); // TODO: remove this line
        save(3); // TODO: remove this line
    }

    @GetMapping("")
    Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    Note findById(@PathVariable Integer id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return note.get();
    }

    void save(int id) {
        // TODO: receive data from client
        noteRepository.save(new Note(
                id,
                false,
                "content"));
    }

}
