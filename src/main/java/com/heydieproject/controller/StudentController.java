package com.heydieproject.controller;

import com.heydieproject.entity.Student;
import com.heydieproject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class StudentController {
    @Autowired
    private StudentService service;

    @PostMapping(value = "/students", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveStudents(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            service.saveStudents(file);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> getAll() {
        return service.studentList().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "/getStudentThread", produces = "application/json")
    public ResponseEntity getStudents(){
        CompletableFuture<List<Student>> student1=service.studentList();
        CompletableFuture<List<Student>> student2=service.studentList();
        CompletableFuture<List<Student>> student3=service.studentList();
        CompletableFuture.allOf(student1,student2,student3).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

}
