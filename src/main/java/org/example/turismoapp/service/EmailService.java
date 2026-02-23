package org.example.turismoapp.service;

import lombok.RequiredArgsConstructor;
import org.example.turismoapp.model.Reserva;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remitente;


    @Async
    public void enviarCorreoAsincrono(String destinatario, String asunto, String cuerpo) {

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            mensaje.setFrom(remitente);

            mailSender.send(mensaje);
            log.info("Correo enviado con éxito a {}", destinatario);

        } catch (Exception e) {
            log.error("Error al enviar el correo a {}: {}", destinatario, e.getMessage());
        }
    }

    @Async
    public void enviarBonoReserva(Reserva reserva) {
        log.info("Generando PDF y enviando bono de reserva a: {}", reserva.getCliente().getEmail());

        try {
            org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
            context.setVariable("reserva", reserva);

            String htmlParaPdf = templateEngine.process("pdf-bono-reserva", context);

            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            org.xhtmlrenderer.pdf.ITextRenderer renderer = new org.xhtmlrenderer.pdf.ITextRenderer();
            renderer.setDocumentFromString(htmlParaPdf);
            renderer.layout();
            renderer.createPDF(outputStream);

            jakarta.mail.internet.MimeMessage mensaje = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper =
                    new org.springframework.mail.javamail.MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setFrom(remitente);
            helper.setTo(reserva.getCliente().getEmail());
            helper.setSubject("Tu Bono de Reserva en " + reserva.getHotel().getNombre() + " - TurismoApp");

            String cuerpoTexto = "¡Hola " + reserva.getCliente().getNombre() + "!\n\n"
                    + "Tu reserva está confirmada. Adjuntamos a este correo tu bono oficial en formato PDF.\n"
                    + "Puedes imprimirlo o llevarlo en tu móvil.\n\n"
                    + "Te recordamos que también puedes imprimirlo desde la propia app."
                    + "El equipo de TurismoAPP";
            helper.setText(cuerpoTexto, false);

            org.springframework.core.io.ByteArrayResource recursoPdf =
                    new org.springframework.core.io.ByteArrayResource(outputStream.toByteArray());

            helper.addAttachment("Bono_Reserva_" + reserva.getId() + ".pdf", recursoPdf);

            mailSender.send(mensaje);

        } catch (Exception e) {
            log.error("Error grave al enviar el PDF de la reserva: {}", e.getMessage(), e);
        }
    }
}