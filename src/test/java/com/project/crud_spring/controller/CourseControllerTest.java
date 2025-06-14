package com.project.crud_spring.controller;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseController controller;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve retornar todos os cursos quando List() é chamado")
    void list() {
        Course curso1 = new Course();
        curso1.setId(1L);
        curso1.setName("Java Básico");
        curso1.setCategory("backend");

        Course curso2 = new Course();
        curso2.setId(2L);
        curso2.setName("Spring Boot");
        curso2.setCategory("backend");

        List<Course> cursosEsperados = List.of(curso1, curso2);

        when(courseRepository.findAll()).thenReturn(cursosEsperados);

        List<Course> resultado = controller.list();

        assertThat(resultado).isNotNull().hasSize(2).containsExactlyElementsOf(cursosEsperados);

        verify(courseRepository, times(1)).findAll();
        verifyNoMoreInteractions(courseRepository);

    }

    @Test
    @DisplayName("deve criar um novo curso quando o método create() é chamado")
    void create() {
        // Arrange (Dado)
        Course novoCurso = new Course();
        novoCurso.setName("Java Avançado");
        novoCurso.setCategory("backend");

        Course cursoSalvo = new Course();
        cursoSalvo.setId(1L);
        cursoSalvo.setName("Java Avançado");
        cursoSalvo.setCategory("backend");

        when(courseRepository.save(novoCurso)).thenReturn(cursoSalvo);

        // Act (Quando)
        Course resultado = controller.create(novoCurso);

        // Assert (Então)
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getName()).isEqualTo("Java Avançado");
        assertThat(resultado.getCategory()).isEqualTo("backend");

        verify(courseRepository, times(1)).save(novoCurso);
    }
}