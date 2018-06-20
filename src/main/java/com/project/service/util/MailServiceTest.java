package com.project.service.util;

import com.lowagie.text.DocumentException;
import com.project.config.thymeleaf.StringContext;
import com.project.domain.Shipment;
import com.project.service.dto.LoyalClientsDTO;
import com.project.service.dto.NoOfPackByAnyCountryDTO;
import com.project.service.dto.NoOfPacksDeliveredDTO;
import com.project.service.dto.NoOfPacksPendingDTO;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Component
public class MailServiceTest {

    private final SpringTemplateEngine templateEngine;

    private final JHipsterProperties jHipsterProperties;

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private static final String STATUS = "status";
    private static final String SENDER = "sender";

    private static final String SHIPMENT = "shipment";

    private static final String DElIVERED = "delivered";

    private static final String COUNTRY = "country";

    private static final String PENDING = "pending";

    private static final String CLIENTS = "clients";

    private String shippedTemplate = "\n" +
        "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "<head>\n" +
        "    <title>Shipping details</title>\n" +
        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "</head>\n" +
        "<body>\n" +
        "\n" +
        "<h1>Shipping details for shipment : </h1>\n" +
        "<p th:text=\"${shipment.id}\">The id of the shipment</p>\n" +
        "<p th:text=\"${shipment.senderP.fullName}\">>Dear </p>\n" +
        "    <p>this email is sent to you to confirm that the shipment you made using our service has been shipped, and it's on its way to the destination.</p>\n" +
        "\n" +
        "<p>Thank your for using our service.\n" +
        "    If you have any questions, please contact the employee who is resposible for this shipment:</p>\n" +
        "\n" +
        "<ul>\n" +
        "    <li th:text=\"${shipment.contactEmployee.name}\">>Name</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.lastName}\">Surname</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.tel}\">Number</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.email}\">email</li>\n" +
        "\n" +
        "</ul>\n" +
        "Best regards,\n" +
        "The Warehouse Team.\n" +
        "\n" +
        "\n" +
        "</body>\n" +
        "</html>\n";
    private String deliveredTemplateSender = "\n" +
        "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "<head>\n" +
        "    <title>Delivered details</title>\n" +
        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "    <link rel=\"shortcut icon\" th:href=\"@{|${baseUrl}/favicon.ico|}\" />\n" +
        "</head>\n" +
        "<body>\n" +
        "\n" +
        "<h1>Delivery details for shipment : </h1>\n" +
        "<p th:text=\"${shipment.id}\">The id of the shipment</p>\n" +
        "<p th:text=\"${shipment.senderP.fullName}\">>Dear </p>\n" +
        "    <p>this email is sended to you to confirm that the shipment you made using our service has been delivered to</p>\n" +
        "<p th:text=\"${shipment.receiver.fullName}\"></p>\n" +
        "\n" +
        "<p>Thank your for using our service.\n" +
        "    If you have any questions, please contact the employee who is resposible for this shipment:</p>\n" +
        "\n" +
        "<ul>\n" +
        "    <li th:text=\"${shipment.contactEmployee.name}\">>Name</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.lastName}\">Surname</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.tel}\">Number</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.email}\">email</li>\n" +
        "\n" +
        "</ul>\n" +
        "Best regards,\n" +
        "The Warehouse Team.\n" +
        "\n" +
        "\n" +
        "</body>\n" +
        "</html>\n";
    private String deliveredTemplateReceiver = "\n" +
        "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "<head>\n" +
        "    <title>Delivered details</title>\n" +
        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "</head>\n" +
        "<body>\n" +
        "\n" +
        "<h1>Delivery details for shipment : </h1>\n" +
        "<p th:text=\"${shipment.id}\">The id of the shipment</p>\n" +
        "<p th:text=\"${shipment.receiver.fullname}\">Dear </p>\n" +
        "    <p>this email is sended to you to confirm that the shipment made by</p>\n" +
        "<p th:text=\"${shipment.senderP.fullname}\"></p>\n" +
        "<p>was delivered to you.</p>\n" +
        "\n" +
        "<p>Thank your for using our service.\n" +
        "    If you have any questions, please contact the employee who is resposible for this shipment:</p>\n" +
        "\n" +
        "<ul>\n" +
        "    <li th:text=\"${shipment.contactEmployee.name}\">>Name</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.lastName}\">Surname</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.tel}\">Number</li>\n" +
        "    <li  th:text=\"${shipment.contactEmployee.email}\">email</li>\n" +
        "\n" +
        "</ul>\n" +
        "Best regards,\n" +
        "The Warehouse Team.\n" +
        "\n" +
        "\n" +
        "</body>\n" +
        "</html>\n";

    private String weeklyReport = "<!DOCTYPE html>\n" +
        "<html xmlns:th=\"http://www.thymeleaf.org\">\n" +
        "    <head>\n" +
        "        <title>WEEKLY REPORT</title>\n" +
        "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
        "        <link rel=\"shortcut icon\" th:href=\"@{|${baseUrl}/favicon.ico|}\" />\n" +
        "    </head>\n" +
        "    <body>\n" +
        "        <p>\n" +
        "           Hello\n" +
        "        </p>\n" +
        "        <p>\n" +
        "           This is the your weekly report, <span th:text=\"${#dates.format(#dates.createNow(), 'dd MMM yyyy HH:mm')}\">...</span>\n" +
        "        </p>\n" +
        "        <p>\n" +
        "           At a glance :\n" +
        "        </p>\n" +
        "        <p>Number of packets delivered :</p>\n" +
        "        <p th:text=\"${delivered.number_of_Packs_delivered}\"></p>\n" +
        "        <p>Number of packets delivered by country</p>\n" +
        "        <table border=\"5\" >\n" +
        "            <tr>\n" +
        "                <td>Country</td>\n" +
        "                <td>Number of packets delivered</td>\n" +
        "            </tr>\n" +
        "            <th:block th:each=\"country : ${country}\">\n" +
        "\n" +
        "                <tr>\n" +
        "                    <td th:text=\"${country.getCountry()}\">...</td>\n" +
        "                    <td th:text=\"${country.getNumber_of_Packs_delivered()}\">...</td>\n" +
        "\n" +
        "                </tr>\n" +
        "            </th:block>\n" +
        "        </table>\n" +
        "        <p>Number of packets pending and country destination</p>\n" +
        "        <table border=\"5\" >\n" +
        "            <tr>\n" +
        "                <td>Country</td>\n" +
        "                <td>Number of packets pending</td>\n" +
        "            </tr>\n" +
        "            <th:block th:each=\"pending : ${pending}\">\n" +
        "\n" +
        "                <tr>\n" +
        "                    <td th:text=\"${pending.getCountryDestination()}\">...</td>\n" +
        "                    <td th:text=\"${pending.getNumberOfPacksPending()}\">...</td>\n" +
        "\n" +
        "                </tr>\n" +
        "            </th:block>\n" +
        "        </table>\n" +
        "\n" +
        "        <p>List of clients with 4 or more packets and number of packets</p>\n" +
        "        <table border=\"5\" >\n" +
        "            <tr>\n" +
        "                <td>Name</td>\n" +
        "                <td>Tel</td>\n" +
        "                <td>Address</td>\n" +
        "                <td>Number of packets sent</td>\n" +
        "            </tr>\n" +
        "            <th:block th:each=\"clients : ${clients}\">\n" +
        "\n" +
        "                <tr>\n" +
        "                    <td th:text=\"${clients.getName()}\">...</td>\n" +
        "                    <td th:text=\"${clients.getTel()}\">...</td>\n" +
        "                    <td th:text=\"${clients.getAddress()}\">...</td>\n" +
        "                    <td th:text=\"${clients.getNumberOfPacksSended()}\">...</td>\n" +
        "\n" +
        "                </tr>\n" +
        "            </th:block>\n" +
        "        </table>\n" +
        "\n" +
        "        <p>\n" +
        "            <span th:text=\"#{email.activation.text2}\">Regards, </span>\n" +
        "            <br/>\n" +
        "            <em th:text=\"#{email.signature}\">JHipster.</em>\n" +
        "        </p>\n" +
        "    </body>\n" +
        "</html>\n";

    public MailServiceTest(SpringTemplateEngine templateEngine, JHipsterProperties jHipsterProperties) {
        this.templateEngine = templateEngine;
        this.jHipsterProperties = jHipsterProperties;
    }

    public String getShippedTemplate() {
        return shippedTemplate;
    }

    public void setShippedTemplate(String shippedTemplate) {
        this.shippedTemplate = shippedTemplate;
    }

    public String getDeliveredTemplateSender() {
        return deliveredTemplateSender;
    }

    public void setDeliveredTemplateSender(String deliveredTemplateSender) {
        this.deliveredTemplateSender = deliveredTemplateSender;
    }

    public String getDeliveredTemplateReceiver() {
        return deliveredTemplateReceiver;
    }

    public void setDeliveredTemplateReceiver(String deliveredTemplateReceiver) {
        this.deliveredTemplateReceiver = deliveredTemplateReceiver;
    }

    public void sendShippedTemplate(Shipment shipment) throws IOException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(shippedTemplate, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(shippedTemplate, context);

        System.out.print(content);
    }


    public byte[] sendShippedPdf(Shipment shipment) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(shippedTemplate, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(shippedTemplate, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfbytes = outputStream.toByteArray();
        outputStream.close();
        return pdfbytes;
    }


    public void sendDeliveredSTemplate(Shipment shipment) throws IOException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(deliveredTemplateSender, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(deliveredTemplateSender, context);

        System.out.print(content);
    }


    public byte[] sendDeliveredSPDFTemplate(Shipment shipment) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(deliveredTemplateSender, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(deliveredTemplateSender, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfbytes = outputStream.toByteArray();
        outputStream.close();
        return pdfbytes;
    }

    public void sendDeliveredRTemplate(Shipment shipment) throws IOException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(deliveredTemplateReceiver, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(deliveredTemplateReceiver, context);

        System.out.print(content);
    }


    public byte[] sendDeliveredRPDFTemplate(Shipment shipment) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(deliveredTemplateReceiver, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(deliveredTemplateReceiver, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfbytes = outputStream.toByteArray();
        outputStream.close();
        return pdfbytes;
    }

    public void sendWeekly(NoOfPacksDeliveredDTO delivered, List<NoOfPackByAnyCountryDTO> country, List<NoOfPacksPendingDTO> pending, List<LoyalClientsDTO> clients) {

        Locale locale = Locale.forLanguageTag("en");

        StringContext context = new StringContext(weeklyReport, locale);
        context.setVariable(DElIVERED, delivered);
        context.setVariable(COUNTRY, country);
        context.setVariable(PENDING, pending);
        context.setVariable(CLIENTS, clients);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(weeklyReport, context);

        System.out.print(content);

    }

    public byte[] sendWeeklyPDF(NoOfPacksDeliveredDTO delivered, List<NoOfPackByAnyCountryDTO> country, List<NoOfPacksPendingDTO> pending, List<LoyalClientsDTO> clients) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag("en");

        StringContext context = new StringContext(weeklyReport, locale);

        context.setVariable(DElIVERED, delivered);
        context.setVariable(COUNTRY, country);
        context.setVariable(PENDING, pending);
        context.setVariable(CLIENTS, clients);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(weeklyReport, context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(outputStream);

        byte[] pdfbytes = outputStream.toByteArray();
        outputStream.close();
        return pdfbytes;
    }

//    public byte[] sendPdfTemplets(Shipment shipment) throws IOException, DocumentException {
//        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());
//        Person sender=shipment.getSenderP();
//        StringContext context = new StringContext(template, locale);
//        context.setVariable(SHIPMENT,shipment );
//        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
//
//        String content = templateEngine.process(template, context);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(content);
//        renderer.layout();
//        renderer.createPDF(outputStream);
//
//        byte[] pdfbytes = outputStream.toByteArray();
//        outputStream.close();
//        return pdfbytes;
//    }
//    public void sendPdfTempletsLisy(User user,List<Status> status) throws IOException, DocumentException {
//        Locale locale = Locale.forLanguageTag(user.getLangKey());
//
//        StringContext context = new StringContext(templateList, locale);
//        context.setVariable(USER, user);
//        context.setVariable(STATUS,status);
//        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
//
//        String content = templateEngine.process(templateList, context);
//
//        OutputStream outputStream=new FileOutputStream("messagelist.pdf");
//        ITextRenderer renderer = new ITextRenderer();
//        renderer.setDocumentFromString(content);
//        renderer.layout();
//        renderer.createPDF(outputStream);
//
//      // byte[] pdfbytes = outputStream.toByteArray();
//          outputStream.close();
//       //return pdfbytes;
//    }


}







