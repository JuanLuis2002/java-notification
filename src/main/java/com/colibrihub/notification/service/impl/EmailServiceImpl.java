package com.colibrihub.notification.service.impl;

import com.colibrihub.notification.dto.ClienteDto;
import com.colibrihub.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void enviarCorreo(ClienteDto correoDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(correoDto.getEmail());
            helper.setSubject("Detalles de su solicitud");

            // Template HTML con los datos del ClienteDto
            String htmlContent = "<!DOCTYPE html>" +
                    "<html lang='es'>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "<title>Detalles de Cliente</title>" +
                    "<style>" +
                    "body {font-family: 'Arial', sans-serif; background-color: #f9f9f9; color: #333; margin: 0; padding: 0;}" +
                    ".container {width: 100%; max-width: 600px; margin: 20px auto; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);}" +
                    ".header {text-align: center; margin-bottom: 20px;}" +
                    ".header h2 {color: #007bff; margin: 0;}" +
                    ".content {font-size: 14px; line-height: 1.6;}" +
                    ".content p {margin: 10px 0;}" +
                    ".content .label {font-weight: bold;}" +
                    ".footer {text-align: center; font-size: 12px; color: #777; margin-top: 20px;}" +
                    ".footer a {color: #007bff; text-decoration: none;}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<div class='header'>" +
                    "<h2>Detalles del Cliente</h2>" +
                    "</div>" +
                    "<div class='content'>" +
                    "<p><span class='label'>Nombre:</span> " + obtenerValor(correoDto.getNombre()) + " " + obtenerValor(correoDto.getApellido()) + "</p>" +
                    "<p><span class='label'>Email:</span> " + obtenerValor(correoDto.getEmail()) + "</p>" +
                    "<p><span class='label'>Teléfono:</span> " + obtenerValor(correoDto.getTelefono()) + "</p>" +
                    "<p><span class='label'>Dirección:</span> " + obtenerValor(correoDto.getDireccion()) + "</p>" +
                    "<p><span class='label'>DUI:</span> " + obtenerValor(correoDto.getDui()) + "</p>" +
                    "<p><span class='label'>NIT:</span> " + obtenerValor(correoDto.getNit()) + "</p>" +
                    "<p><span class='label'>Género:</span> " + obtenerValor(correoDto.getGenero()) + "</p>" +
                    "<p><span class='label'>Estado Civil:</span> " + obtenerValor(correoDto.getEstadoCivil()) + "</p>" +
                    "<p><span class='label'>Ocupación:</span> " + obtenerValor(correoDto.getOcupacion()) + "</p>" +
                    "<p><span class='label'>Observaciones:</span> " + obtenerValor(correoDto.getObservaciones()) + "</p>" +
                    "</div>" +
                    "<div class='footer'>" +
                    "<p>Este es un correo automatizado. Si tienes alguna pregunta, no dudes en <a href='mailto:support@empresa.com'>contactarnos</a>.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            helper.setFrom("loquendor34@gmail.com");
            javaMailSender.send(mimeMessage);

        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }

    // Método para manejar valores nulos o vacíos
    private String obtenerValor(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? "No proporcionado" : valor;
    }
}
