package com.heydieproject.service;

import com.heydieproject.entity.Student;
import com.heydieproject.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class StudentService {
    @Autowired
    private StudentRepository repository;

    @Async
    public CompletableFuture<List<Student>> saveStudents(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Student> students = parseCSVFile(file);
        log.info("saving list of STUDENT of size {}", students.size(), "" + Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end - start));

        return CompletableFuture.completedFuture(repository.saveAll(students));
    }

    @Async
    public CompletableFuture<List<Student>> studentList() {
        log.info("get list of STUDENT by "+Thread.currentThread().getName());
        return CompletableFuture.completedFuture(repository.findAll());
    }

    private List<Student> parseCSVFile(MultipartFile file) throws Exception {
        final List<Student> students = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Student student = new Student();
                    student.setName(data[0]);
                    student.setAddress(data[1]);
                    student.setGender(data[2]);
                    students.add(student);
                }
                return students;
            }
        } catch (final IOException e) {
            log.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }

    }

}
