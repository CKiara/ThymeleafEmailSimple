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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class WebConfig {

    public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    @Bean
    public SpringTemplateEngine emailTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        Set<ITemplateResolver> resolvers = new HashSet<>();
        resolvers.add(textTemplateResolver());
        resolvers.add(htmlTemplateResolver());
        templateEngine.setTemplateResolvers(resolvers);
        return templateEngine;
    }
    
     @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(emailTemplateEngine());
//        resolver.setOrder(1);
        return resolver;
    }

    private ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(1);
        Set<String> singleton = new HashSet<>();
        singleton.add("/message/*");
//        singleton.add("/inicio/*");
        templateResolver.setResolvablePatterns(singleton);
        templateResolver.setPrefix("/templates");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        return templateResolver;
    }

    private ClassLoaderTemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver  templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(2);
        templateResolver.setResolvablePatterns(Collections.singleton("/inicio/*"));
        templateResolver.setPrefix("/templates");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        return templateResolver;
    }
}
