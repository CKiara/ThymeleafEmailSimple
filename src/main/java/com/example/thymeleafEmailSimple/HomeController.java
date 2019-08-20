/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.thymeleafEmailSimple;

/**
 *
 * @author cristalflores
 */
import java.util.Arrays;
import java.util.Locale;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @RequestMapping("/html-2")
    public ModelAndView welcomeHtml2() {
        ModelAndView mav = new ModelAndView("/message/index2");
        mav.addObject("name", "Thymelead Welcome");
        return mav;
    }

    @RequestMapping("/html-1")
    public ModelAndView welcomeHtml1() {
        ModelAndView mav = new ModelAndView("/message/index1");
        return mav;
    }

    @RequestMapping("/text")
    public ModelAndView welcomeText() {
        ModelAndView mav = new ModelAndView("/inicio/text");
        mav.addObject("name", "Thymelead Welcome");
        mav.addObject("hobbies", Arrays.asList("Primero", "Segundo", "tercero"));
        return mav;
    }

    @RequestMapping("/send")
    public ModelAndView send(final Locale locale) throws MessagingException {
        ModelAndView mav = new ModelAndView("/message/email-simple");
        mav.addObject("name", "Thymelead Welcome");
        mav.addObject("hobbies", Arrays.asList("Primero", "Segundo", "tercero"));
        try {
            final Context ctx = new Context(locale);
            ctx.setVariable("name", "Thymeleaf");
            ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setTo("cristal.flores@fundacion-jala.org");
            message.setSubject("This is the message subject(Prueba Thymeleaf)");
            message.setText("This is the message body(Prueba Thymeleaf)");
            final String htmlContent = htmlTemplateEngine.process("text", ctx);
            message.setText(htmlContent);
            this.mailSender.send(mimeMessage);
            log.info("Spring Boot Thymeleaf Configuration Example");
        } catch (MailException e) {
            log.info(e.getMessage());
        }
        return mav;
    }
}
