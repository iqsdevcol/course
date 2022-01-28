package com.iqs.emma.course.service;

import com.iqs.emma.course.dao.PaymentDao;
import com.iqs.emma.course.dao.RegisterDao;
import com.iqs.emma.course.dao.StudentDao;
import com.iqs.emma.course.domain.PaymentModel;
import com.iqs.emma.course.domain.RegisterModel;
import com.iqs.emma.course.domain.StudentModel;
import com.iqs.emma.course.dto.RegisterCourseDto;
import com.iqs.emma.course.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentDao studentDao;

    @Mock
    private RegisterDao registerDao;

    @Mock
    private PaymentDao paymentDao;

    @InjectMocks
    private StudentServiceImpl studentService;


    @Test
    void registerCourseTest() {
        RegisterCourseDto registerCourseDto = mockStudentDto();
        StudentModel student = registerCourseDto.toStudentModel();
        student.setId(1L);
        Mockito.when(studentDao.save(Mockito.any())).thenReturn(student);
        RegisterModel register = registerCourseDto.toRegisterModel(student);
        register.setId(2L);
        Mockito.when(registerDao.save(Mockito.any())).thenReturn(register);
        PaymentModel payment = registerCourseDto.toPaymentModel(register);
        payment.setId(3L);
        Mockito.when(paymentDao.save(Mockito.any())).thenReturn(payment);

        Long paymentId = studentService.registerCourse(registerCourseDto);

        Assertions.assertNotNull(paymentId);
        Assertions.assertEquals(3L, paymentId);
    }

    @Test()
    void registerCourseDocumentReadyRegister() {
        Mockito.when(studentDao.findByDocument(Mockito.anyLong())).thenReturn(Optional.of(new StudentModel()));
        RegisterCourseDto registerCourseDto = mockStudentDto();
        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () ->
                        studentService.registerCourse(registerCourseDto), "different document");

        Assertions.assertEquals(
                String.format("El documento %s ya esta registrado", registerCourseDto.getDocument()),
                thrown.getMessage()
        );

    }

    @Test()
    void registerCourseEmailReadyRegister() {
        Mockito.when(studentDao.findByDocument(Mockito.anyLong())).thenReturn(Optional.empty());
        Mockito.when(studentDao.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new StudentModel()));

        RegisterCourseDto registerCourseDto = mockStudentDto();
        IllegalArgumentException thrown = Assertions
                .assertThrows(IllegalArgumentException.class, () ->
                        studentService.registerCourse(registerCourseDto), "different email");

        Assertions.assertEquals(
                String.format("El email %s ya esta registrado", registerCourseDto.getEmail()),
                thrown.getMessage()
        );

    }

    @Test
    void ValidateFileDocumentOk() {
        Mockito.when(studentDao.findByDocument(Mockito.anyLong())).thenReturn(Optional.empty());
        String document = "1070618993";

        studentService.validateField("document", document);

        Mockito.verify(studentDao, Mockito.times(1)).findByDocument(Mockito.anyLong());
        Mockito.verify(studentDao, Mockito.times(0)).findByEmail(Mockito.anyString());
    }

    @Test
    void ValidateFileEmailOk() {
        Mockito.when(studentDao.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
        String email = "1070618993";

        studentService.validateField("email", email);

        Mockito.verify(studentDao, Mockito.times(0)).findByDocument(Mockito.anyLong());
        Mockito.verify(studentDao, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    void ValidateFileDocumentReadyExist() {
        Mockito.when(studentDao.findByDocument(Mockito.anyLong())).thenReturn(Optional.of(new StudentModel()));
        String document = "1070618993";

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        studentService.validateField("document", document),
                "different document"
        );

        Assertions.assertEquals(
                String.format("El documento %s ya esta registrado", document),
                thrown.getMessage()
        );
        Mockito.verify(studentDao, Mockito.times(1)).findByDocument(Mockito.anyLong());
        Mockito.verify(studentDao, Mockito.times(0)).findByEmail(Mockito.anyString());
    }

    @Test
    void ValidateFileEmailReadyExist() {
        Mockito.when(studentDao.findByEmail(Mockito.anyString())).thenReturn(Optional.of(new StudentModel()));
        String email = "beto-castro15@hotmail.com";

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        studentService.validateField("email", email),
                "different email"
        );

        Assertions.assertEquals(
                String.format("El email %s ya esta registrado", email),
                thrown.getMessage()
        );
        Mockito.verify(studentDao, Mockito.times(0)).findByDocument(Mockito.anyLong());
        Mockito.verify(studentDao, Mockito.times(1)).findByEmail(Mockito.anyString());
    }

    @Test
    void ValidateFileInvalidField() {
        String field = "cellphone";
        String value = "325669856";

        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        studentService.validateField(field, value),
                "Invalid Field"
        );

        Assertions.assertEquals(
                String.format("No existe validacion para el campo %s", field),
                thrown.getMessage()
        );
        Mockito.verify(studentDao, Mockito.times(0)).findByDocument(Mockito.anyLong());
        Mockito.verify(studentDao, Mockito.times(0)).findByEmail(Mockito.anyString());
    }


    private RegisterCourseDto mockStudentDto() {
        return new RegisterCourseDto(
                1L,
                1070618993L,
                "Juan",
                "Alberto",
                "Castro",
                "Espinosa",
                "beto-castr15@hotmail.com",
                3172938809L,
                LocalDate.of(2014, 5, 14),
                1L,
                1L,
                1000D,
                LocalDate.now()
        );
    }

}
