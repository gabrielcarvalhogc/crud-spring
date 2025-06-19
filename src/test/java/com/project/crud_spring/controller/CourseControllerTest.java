package com.project.crud_spring.controller;

import com.project.crud_spring.model.Course;
import com.project.crud_spring.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController controller;

    private Course cursoJava;
    private Course cursoSpring;
    @BeforeEach
    void setUp() {
        cursoJava = new Course();
        cursoJava.setId(1L);
        cursoJava.setName("Java Básico");
        cursoJava.setCategory("Back-end");

        cursoSpring = new Course();
        cursoSpring.setId(2L);
        cursoSpring.setName("Spring Boot");
        cursoSpring.setCategory("Back-end");
    }

    @Test
    @DisplayName("Should return a list of courses when list() is called")
    void list() {
        List<Course> cursosEsperados = List.of(cursoJava, cursoSpring);

        when(courseService.list()).thenReturn(cursosEsperados);

        List<Course> resultado = controller.list();

        assertThat(resultado).isNotNull().hasSize(2).containsExactlyElementsOf(cursosEsperados);
    }

    @Test
    @DisplayName("Should return a course when it has an id and error when it doesn't")
    void findById() {
        when(courseService.findById(1L)).thenReturn(cursoJava);

        Course result = controller.findById(1L);

        assertEquals("Java Básico", result.getName());
        assertEquals("Back-end", result.getCategory());
    }

    @Test
    @DisplayName("Should create a course when method create is called")
    void create() {
        Course novoCurso = new Course();
        novoCurso.setName("Java Avançado");
        novoCurso.setCategory("backend");

        Course cursoSalvo = new Course();
        cursoSalvo.setId(1L);
        cursoSalvo.setName("Java Avançado");
        cursoSalvo.setCategory("backend");

        when(courseService.create(cursoJava)).thenReturn(cursoJava);

        Course resultado = controller.create(cursoJava);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getName()).isEqualTo("Java Básico");
        assertThat(resultado.getCategory()).isEqualTo("Back-end");
    }

    @Test
    @DisplayName("Should update the data of a course when update is called")
    void update() {
        when(courseService.update(cursoSpring.getId(), cursoJava)).thenReturn(cursoJava);

        Course response = controller.update(cursoSpring.getId(), cursoJava);

        assertEquals("Java Básico", response.getName());
        assertEquals("Back-end", response.getCategory());
    }
}