package com.iqs.emma.course.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqs.emma.course.dto.RegisterCourseDto;
import com.iqs.emma.course.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterCourseControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @Test
    void saveCourseRegister() throws Exception {
        Mockito.when(studentService.registerCourse(Mockito.any(RegisterCourseDto.class))).thenReturn(3L);

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new RegisterCourseDto())))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.data", is(3)))
        ;
    }

    @Test
    void saveCourseRegisterIllegalArgumentException() throws Exception {
        RegisterCourseDto registerCourseDto = mockRegister();
        String errorMessage = String.format("El documento %s ya esta registrado", registerCourseDto.getDocument());
        Mockito.when(studentService.registerCourse(Mockito.any(RegisterCourseDto.class)))
                .thenThrow(new IllegalArgumentException(errorMessage));

        mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerCourseDto)))

                // Validate the response code and content type
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.data", is(errorMessage)))
        ;
    }

    private RegisterCourseDto mockRegister() {
        RegisterCourseDto registerCourseDto = new RegisterCourseDto();
        registerCourseDto.setDocument(1070618993L);
        return registerCourseDto;
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
