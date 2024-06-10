package com.example.server.note;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

        /* TODO: remove below */
        noteRepository.deleteAll();
        noteRepository.save(new Note(1, false, "first"));
        noteRepository.save(new Note(2, false, "second"));
        noteRepository.save(new Note(3, false, "third"));
        noteRepository.update(new Note(2, true, "second"));
        noteRepository.deleteById(1);
        /* TODO: remove above */
    }

    @GetMapping("")
    Iterable<Note> findAll() {
        return noteRepository.findAll();
    }

    @GetMapping("/{id}")
    Note find(@PathVariable Integer id) {
        var note = noteRepository.findById(id);
        if (note.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return note.get();
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    void add(@RequestBody Note note) {
        noteRepository.save(new Note(
                noteRepository.maxId() + 1,
                note.processing(),
                note.content()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@RequestBody Note note) {
        noteRepository.update(note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        noteRepository.deleteById(id);
    }

}
