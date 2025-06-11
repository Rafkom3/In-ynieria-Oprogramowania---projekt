// src/src/model/Visit.java
package model;

import java.time.LocalDateTime;

public class Visit {
    private final String username;
    private final String serviceType;
    private final LocalDateTime dateTime;
    private final String contactInfo;
    private final String notes;
    private String status;

    public Visit(String username, String serviceType,
                 LocalDateTime dateTime,
                 String contactInfo, String notes,
                 String status) {
        this.username = username;
        this.serviceType = serviceType;
        this.dateTime = dateTime;
        this.contactInfo = contactInfo;
        this.notes = notes;
        this.status = status;
    }

    public String getUsername()       { return username; }
    public String getServiceType()    { return serviceType; }
    public LocalDateTime getDateTime(){ return dateTime; }
    public String getContactInfo()    { return contactInfo; }
    public String getNotes()          { return notes; }
    public String getStatus()         { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format(
                "Użytkownik: %s%nUsługa: %s%nTermin: %s%nKontakt: %s%nUwagi: %s%nStatus: %s",
                username, serviceType, dateTime, contactInfo, notes, status
        );
    }
}
