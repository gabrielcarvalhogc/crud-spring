package com.project.crud_spring.service;

import com.project.crud_spring.model.Course;
import com.project.crud_spring.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    private Course cursoJava;
    private Course cursoSpring;

    @BeforeEach
    void setup() {
        cursoJava = new Course();
        cursoJava.setId(1L);
        cursoJava.setName("Java");
        cursoJava.setCategory("Back-end");
        cursoJava.setStatus("Ativo");

        cursoSpring = new Course();
        cursoSpring.setId(2L);
        cursoSpring.setName("Spring Boot");
        cursoSpring.setCategory("Back-end");
        cursoSpring.setStatus("Ativo");
    }

    @Test
    @DisplayName("Should return a list of all courses")
    void list() {
        List<Course> coursesMock = List.of(cursoJava, cursoSpring);
        when(courseRepository.findAll()).thenReturn(coursesMock);

        List<Course> result = courseService.list();

        assertEquals(2, result.size());
        assertEquals("Java", result.getFirst().getName());
        assertEquals("Spring Boot", result.get(1).getName());

        verify(courseRepository).findAll();
    }

    @Test
    @DisplayName("Should find a course by id and return it")
    void findById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.ofNullable(cursoJava));

        Optional<Course> result = courseService.findById(1L);

        assertEquals("Java", result.get().getName());
        assertEquals("Back-end", result.get().getCategory());

        verify(courseRepository).findById(1L);
    }

    @Test
    @DisplayName("Should create a course with name, category and status=ativo")
    void create() {
        when(courseRepository.save(cursoJava)).thenReturn(cursoJava);

        Course result = courseService.create(cursoJava);

        assertEquals("Java", result.getName());
        assertEquals("Back-end", result.getCategory());
        assertEquals("Ativo", result.getStatus());

        verify(courseRepository).save(cursoJava);
    }

    @Test
    @DisplayName("Should update a course")
    void update() {
        when(courseRepository.findById(2L)).thenReturn(Optional.ofNullable(cursoSpring));
        when(courseRepository.save(cursoSpring)).thenReturn(cursoSpring);

        Optional<Course> result = courseService.update(2L, cursoSpring);

        assertEquals("Spring Boot", result.get().getName());
        assertEquals("Back-end", result.get().getCategory());

        verify(courseRepository).findById(2L);
        verify(courseRepository).save(cursoSpring);
    }

    @Test
    @DisplayName("Should delete a course by id")
    void delete() {
        when(courseRepository.findById(2L)).thenReturn(Optional.ofNullable(cursoSpring));

        Boolean result = courseService.delete(2L);

        assertEquals(true, result);

        verify(courseRepository).findById(2L);
    }
}