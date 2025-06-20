package com.project.crud_spring.service;

import com.project.crud_spring.dto.CourseDTO;
import com.project.crud_spring.dto.mapper.CourseMapper;
import com.project.crud_spring.exception.RecordNotFoundException;
import com.project.crud_spring.model.Course;
import com.project.crud_spring.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    private CourseService courseService;
    private CourseMapper courseMapper;

    private Course cursoJava;
    private Course cursoSpring;
    private CourseDTO cursoJavaDTO;
    private CourseDTO cursoSpringDTO;
    @BeforeEach
    void setup() {
        courseMapper = new CourseMapper();
        courseService = new CourseService(courseRepository, courseMapper);

        cursoJava = new Course();
        cursoJava.setId(1L);
        cursoJava.setName("Java");
        cursoJava.setCategory("Back-end");

        cursoSpring = new Course();
        cursoSpring.setId(2L);
        cursoSpring.setName("Spring Boot");
        cursoSpring.setCategory("Back-end");

        cursoJavaDTO = courseMapper.toDto(cursoJava);
        cursoSpringDTO = courseMapper.toDto(cursoSpring);
    }

    @Test
    @DisplayName("Should return a list of all courses")
    void list() {
        when(courseRepository.findAll()).thenReturn(List.of(cursoJava, cursoSpring));

        List<CourseDTO> result = courseService.list();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(cursoJavaDTO, cursoSpringDTO)));
        verify(courseRepository).findAll();
    }

    @Test
    @DisplayName("Should find a course by id and return it")
    void findById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(cursoJava));

        CourseDTO result = courseService.findById(1L);

        assertEquals(cursoJavaDTO, result);
        verify(courseRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return an exception when doesn't find an id course")
    void findByIdException() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        RecordNotFoundException ex = assertThrows(
                RecordNotFoundException.class,
                () -> courseService.findById(1L)
        );

        assertTrue(ex.getMessage().contains("1"));
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should create a course with name and category")
    void create() {
        CourseDTO inputDto = new CourseDTO(null, "Spring Boot", "Back-end");
        Course saved = new Course();
        saved.setId(3L);
        saved.setName("React");
        saved.setCategory("Front=end");
        CourseDTO savedDto = courseMapper.toDto(saved);

        when(courseRepository.save(any(Course.class))).thenReturn(saved);

        CourseDTO result = courseService.create(inputDto);

        assertEquals(savedDto, result);
        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        verify(courseRepository).save(captor.capture());
        assertEquals("React", savedDto.name());
    }

    @Test
    @DisplayName("Should update a course")
    void update() {
        CourseDTO updateDto = new CourseDTO(null, "Java Intermediário", "Back-end");
        when(courseRepository.findById(1L)).thenReturn(Optional.of(cursoJava));
        when(courseRepository.save(any(Course.class))).thenAnswer(inv -> inv.getArgument(0));

        CourseDTO result = courseService.update(1L, updateDto);

        assertEquals("Java Intermediário", result.name());
        verify(courseRepository).findById(1L);
        verify(courseRepository).save(cursoJava);
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException when update course id doesn't exist")
    void shouldThrowWhenUpdateNotFound() {
        CourseDTO updateDto = new CourseDTO(null, "X", "Cat");
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> courseService.update(999L, updateDto));
        verify(courseRepository).findById(999L);
    }

    @Test
    @DisplayName("Should delete a course by id")
    void delete() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(cursoSpring));

        courseService.delete(1L);

        verify(courseRepository).findById(1L);
        verify(courseRepository).delete(cursoSpring);
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException when delete course id doesn't exist")
    void shouldThrowWhenDeleteNotFound() {
        when(courseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> courseService.delete(999L));
        verify(courseRepository).findById(999L);
    }
}