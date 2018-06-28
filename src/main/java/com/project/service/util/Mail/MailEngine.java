package com.project.service.util.Mail;

import com.lowagie.text.DocumentException;
import com.project.config.thymeleaf.StringContext;
import com.project.domain.EmailTemplates;
import com.project.domain.Shipment;
import com.project.service.EmailTemplatesService;
import com.project.service.dto.LoyalClientsDTO;
import com.project.service.dto.NoOfPackByAnyCountryDTO;
import com.project.service.dto.NoOfPacksDeliveredDTO;
import com.project.service.dto.NoOfPacksPendingDTO;
import io.github.jhipster.config.JHipsterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MailEngine {

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



    public MailEngine(SpringTemplateEngine templateEngine, JHipsterProperties jHipsterProperties) {
        this.templateEngine = templateEngine;
        this.jHipsterProperties = jHipsterProperties;
    }


    public byte[] sendPDF(Shipment shipment, String template) throws DocumentException, IOException

    {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context =new StringContext(template,locale);
        context.setVariable(SHIPMENT,shipment);
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
    public void sendTemplate(Shipment shipment,String template) throws IOException {
        Locale locale = Locale.forLanguageTag(shipment.getSenderP().getUser().getLangKey());

        StringContext context = new StringContext(template, locale);
        context.setVariable(SHIPMENT, shipment);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(template, context);

        System.out.print(content);
    }
    public void sendShippedTemplate(Shipment shipment,String template) throws IOException {
        sendTemplate(shipment,template);
    }


    public byte[] sendShippedPdf(Shipment shipment,String template) throws IOException, DocumentException {

       return sendPDF(shipment,template);
    }



    public void sendDeliveredSTemplate(Shipment shipment,String template) throws IOException {
     sendTemplate(shipment,template);
    }


    public byte[] sendDeliveredSPDFTemplate(Shipment shipment,String template) throws IOException, DocumentException {
   return sendPDF(shipment,template);
    }

    public void sendDeliveredRTemplate(Shipment shipment,String template) throws IOException {
        sendTemplate(shipment,template);
    }


    public byte[] sendDeliveredRPDFTemplate(Shipment shipment,String template) throws IOException, DocumentException {
        return sendPDF(shipment,template);
    }

    public void sendWeekly(NoOfPacksDeliveredDTO delivered, List<NoOfPackByAnyCountryDTO> country, List<NoOfPacksPendingDTO> pending, List<LoyalClientsDTO> clients,String template) {

        Locale locale = Locale.forLanguageTag("en");

        StringContext context = new StringContext(template, locale);
        context.setVariable(DElIVERED, delivered);
        context.setVariable(COUNTRY, country);
        context.setVariable(PENDING, pending);
        context.setVariable(CLIENTS, clients);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        String content = templateEngine.process(template, context);

        System.out.print(content);

    }

    public byte[] sendWeeklyPDF(NoOfPacksDeliveredDTO delivered, List<NoOfPackByAnyCountryDTO> country, List<NoOfPacksPendingDTO> pending, List<LoyalClientsDTO> clients,String template) throws IOException, DocumentException {
        Locale locale = Locale.forLanguageTag("en");

        StringContext context = new StringContext(template, locale);

        context.setVariable(DElIVERED, delivered);
        context.setVariable(COUNTRY, country);
        context.setVariable(PENDING, pending);
        context.setVariable(CLIENTS, clients);
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



}







