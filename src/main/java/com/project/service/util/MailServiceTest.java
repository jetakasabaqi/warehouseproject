package com.project.service.util;

import com.lowagie.text.DocumentException;
import com.project.config.thymeleaf.StringContext;
import com.project.domain.Status;
import com.project.domain.User;
import io.github.jhipster.config.JHipsterProperties;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

@Component
public class MailServiceTest {

    private final SpringTemplateEngine templateEngine;

    private final JHipsterProperties jHipsterProperties;

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String STATUS="status";

    private String template = "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "    <head>\n" +
        "        <title th:text=\"#{email.activation.title}\">JHipster activation</title>\n" +
        "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "        <link rel=\"shortcut icon\" th:href=\"@{|${baseUrl}/favicon.ico|}\" />\n" +
        "    </head>\n" +
        "    <body>\n" +
        "        <p th:text=\"#{email.activation.greeting(${user.login})}\">\n" +
        "            Dear\n" +
        "        </p>\n" +
        "        <p th:text=\"#{email.activation.text1}\">\n" +
        "            Your JHipster account has been created, please click on the URL below to activate it:\n" +
        "        </p>\n" +
        "        <p>\n" +
        "            <a th:href=\"@{|${baseUrl}/#/activate?key=${user.activationKey}|}\"\n" +
        "               th:text=\"@{|${baseUrl}/#/activate?key=${user.activationKey}|}\">Activation Link</a>\n" +
        "        </p>\n" +
        "        <p>\n" +
        "            <span th:text=\"#{email.activation.text2}\">Regards, </span>\n" +
        "            <br/>\n" +
        "            <em th:text=\"#{email.signature}\">JHipster.</em>\n" +
        "        </p>\n" +
        "    </body>\n" +
        "</html>\n";

       private String templateList="<!DOCTYPE html>\n" +
           "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
           "<head>\n" +
           "<title>JHipster Status List</title>\n" +
           "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
           "<link rel=\"shortcut icon\" th:href=\"@{|${baseUrl}/favicon.ico|}\" />\n" +
           "\n" +
           "</head>\n" +
           "<body>\n" +
           "   <p>Here is the status list :</p>\n" +
           "\n" +
           "   <ul>\n" +
           "     <li th:each=\" status : ${status}\" th:text=\"${status.statusName}\">\n" +
           "         There are no statuses\n" +
           "     </li>\n" +
           "\n" +
           "\n" +
           "\n" +
           "\n" +
           "   </ul>\n" +
           "\n" +
           "\n" +
           "\n" +
           "</body>\n" +
           "</html>";
    public MailServiceTest(SpringTemplateEngine templateEngine, JHipsterProperties jHipsterProperties) {
        this.templateEngine = templateEngine;
        this.jHipsterProperties = jHipsterProperties;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public void sendTemplate(User user) throws IOException {
        Locale locale = Locale.forLanguageTag(user.getLangKey());

        StringContext context = new StringContext(template, locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(template, context);

        System.out.print(content);
    }


    public byte[] sendPdfTemplets(User user) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag(user.getLangKey());

        StringContext context = new StringContext(template, locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(template, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfbytes = outputStream.toByteArray();
        outputStream.close();
        return pdfbytes;
    }
    public void sendPdfTempletsLisy(User user,List<Status> status) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag(user.getLangKey());

        StringContext context = new StringContext(templateList, locale);
        context.setVariable(USER, user);
        context.setVariable(STATUS,status);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(templateList, context);

        OutputStream outputStream=new FileOutputStream("messagelist.pdf");
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

      // byte[] pdfbytes = outputStream.toByteArray();
          outputStream.close();
       //return pdfbytes;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append("test.html").append(template).toString();
    }


}







