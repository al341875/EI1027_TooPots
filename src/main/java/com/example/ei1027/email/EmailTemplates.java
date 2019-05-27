package com.example.ei1027.email;

/**
 * Created by CIT on 26/05/2019.
 */
public enum EmailTemplates {
    SOLICITUD_ACCEPTADA("mailAccepted.html", "Sol·licitud Acceptada"),
    SOLICITUD_REBUTJADA("mailRejected.html", "Sol·licitud Rebutjada"),
    SOLICITUD_ENVIADA("mailSent.html", "Sol·licitud Rebuda");

    private String subject;
    private String templateName;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    public String fileName() {
        return templateName;
    }

    public String subject() {
        return subject;
    }


}
