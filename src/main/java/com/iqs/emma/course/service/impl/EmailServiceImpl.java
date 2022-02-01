package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.AcademicLevelDao;
import com.iqs.emma.course.dao.CityDao;
import com.iqs.emma.course.dao.GradeOptionDao;
import com.iqs.emma.course.domain.*;
import com.iqs.emma.course.service.EmailService;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String SUBJECT_REGISTER_MESSAGE = "Registro de curso";
    private final JavaMailSender emailSender;
    private final CityDao cityDao;
    private final AcademicLevelDao academicLevelDao;
    private final GradeOptionDao gradeOptionDao;

//    private final SimpleMailMessage template;

//    private final SpringTemplateEngine thymeleafTemplateEngine;

//    private final FreeMarkerConfigurer freemarkerConfigurer;

    public EmailServiceImpl(JavaMailSender emailSender, CityDao cityDao, AcademicLevelDao academicLevelDao, GradeOptionDao gradeOptionDao) {
        this.emailSender = emailSender;
//        this.template = template;
//        this.freemarkerConfigurer = freemarkerConfigurer;
        this.cityDao = cityDao;
        this.academicLevelDao = academicLevelDao;
        this.gradeOptionDao = gradeOptionDao;
    }


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("iqs.course@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendRegisterCourseMethod(PaymentModel payment) {
        String email = payment.getRegister().getStudentModel().getEmail();
//        sendSimpleMessage(email,SUBJECT_REGISTER_MESSAGE,buildRegisterMessage(payment));
        sendSimpleMessage(email,SUBJECT_REGISTER_MESSAGE,buildRegisterMessage(payment));
    }

    private String buildRegisterMessage(PaymentModel payment) {
        RegisterModel register = payment.getRegister();
        StudentModel student = register.getStudentModel();
        StringBuilder message = new StringBuilder();
        CityModel city = cityDao.findById(student.getDocumentCity().getId()).orElseThrow(() -> new IllegalArgumentException(""));
        AcademicLevelModel academicLevel = academicLevelDao.findById(register.getAcademicLevel().getId()).orElseThrow(() -> new IllegalArgumentException(""));
        GradeOptionModel gradeOption = gradeOptionDao.findById(register.getAcademicLevel().getId()).orElseThrow(() -> new IllegalArgumentException(""));
        message.append(
                String.format("Su registro ha sido exitoso con el radicado %s, esta fue la informacion diligenciada en el formulario: %n",
                        register.getId())
        );
        message.append( String.format("Documento: %s %n",student.getDocument()));
        message.append( String.format("Departamento expedicion documento: %s - %s %n",
                city.getState().getName(),
                city.getName())
        );
        message.append( String.format("Fecha expedicion documento: %s %n",student.getDocumentDate().toString()));
        message.append( String.format("Primer nombre: %s %n",student.getName()));
        message.append( String.format("Segundo nombre: %s %n",student.getMiddleName()));
        message.append( String.format("Primer apellido: %s %n",student.getLastname()));
        message.append( String.format("Segundo apellido: %s %n",student.getSecondLastname()));
        message.append( String.format("Nivel de graduacion: %s %n",academicLevel.getName()));
        message.append( String.format("Opcion de grado: %s %n",gradeOption.getName()));
        message.append( String.format("Email: %s %n",student.getEmail()));
        message.append( String.format("Celular: %s %n",student.getCellphone()));
        message.append( String.format("Valor pago: %s %n",payment.getValue()));
        message.append( String.format("Fecha de pago pago: %s %n %n %n",payment.getPaymentDate().toString()));

        message.append("Querido estudiante, estos son los datos que ingresaste al sistema de opción de grado, si requieres algún cambio, por favor escribe al correo XXXXXX para que te sea enviado un nuevo link)");


        return message.toString();
    }
}
