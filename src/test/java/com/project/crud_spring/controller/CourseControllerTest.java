package com.project.crud_spring.controller;

import com.project.crud_spring.dto.CourseDTO;
import com.project.crud_spring.exception.RecordNotFoundException;
import com.project.crud_spring.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    private CourseController controller;

    @BeforeEach
    void setUp() {
        controller = new CourseController(courseService);
    }

    @Test
    @DisplayName("Should return a list of courses when list() is called")
    void list() {
        CourseDTO c1 = new CourseDTO(1L, "Java Básico", "Back-end");
        CourseDTO c2 = new CourseDTO(2L, "Angular", "Front-end");
        when(courseService.list()).thenReturn(List.of(c1, c2));

        List<CourseDTO> result = controller.list();

        assertEquals(2, result.size());
        assertSame(c1, result.get(0));
        assertSame(c2, result.get(1));
        verify(courseService).list();
    }

    @Test
    @DisplayName("Should return a course when it has an id and error when it doesn't")
    void findById() {
        CourseDTO dto = new CourseDTO(1L, "Java", "Back-end");
        when(courseService.findById(1L)).thenReturn(dto);

        CourseDTO result = controller.findById(1L);

        assertEquals(dto, result);
        verify(courseService).findById(1L);
    }

    @Test
    @DisplayName("findById method should throw an error when id doesn't exist")
    void findByIdShouldThrowWhenNotFound() {
        when(courseService.findById(99L)).thenThrow(new RecordNotFoundException(99L));

        assertThrows(RecordNotFoundException.class, () -> controller.findById(99L));
        verify(courseService).findById(99L);
    }

    @Test
    @DisplayName("Should create a course when method create is called")
    void create() {
        CourseDTO input = new CourseDTO(null, "Spring Boot", "Back-end");
        CourseDTO created = new CourseDTO(3L, "Spring Boot", "Back-end");
        when(courseService.create(input)).thenReturn(created);

        CourseDTO result = controller.create(input);

        assertEquals(created, result);
        verify(courseService).create(input);
    }

    @Test
    @DisplayName("Should update the data of a course when update is called")
    void update() {
        CourseDTO input = new CourseDTO(null, "Java Avançado", "Back-end");
        CourseDTO updated = new CourseDTO(1L, "Java Avançado", "Back-end");
        when(courseService.update(1L, input)).thenReturn(updated);

        CourseDTO result = controller.update(1L, input);

        assertEquals(updated, result);
        verify(courseService).update(1L, input);
    }

    @Test
    @DisplayName("Delete method should call service")
    void deleteShouldCallServiceAndDoNothingWhenExists() {
        assertDoesNotThrow(() -> controller.delete(1L));
        verify(courseService).delete(1L);
    }

    @Test
    @DisplayName("Delete method should throw an error when id doesn't exist")
    void deleteShouldThrowWhenNotFound() {
        doThrow(new RecordNotFoundException(42L)).when(courseService).delete(42L);

        assertThrows(RecordNotFoundException.class, () -> controller.delete(42L));
        verify(courseService).delete(42L);
    }
}
