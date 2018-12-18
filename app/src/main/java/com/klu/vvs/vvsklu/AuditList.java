package com.klu.vvs.vvsklu;

/**
 * Created by bhavna on 11/15/2018.
 */

public class AuditList {

    private String auditno,date,percent,scholar,stand;

    public AuditList(String auditno, String date, String percent, String scholar, String stand) {
        this.auditno = auditno;
        this.date = date;
        this.percent = percent;
        this.scholar = scholar;
        this.stand = stand;
    }

    public String getAuditno() {
        return auditno;
    }

    public void setAuditno(String auditno) {
        this.auditno = auditno;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getScholar() {
        return scholar;
    }

    public void setScholar(String scholar) {
        this.scholar = scholar;
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand;
    }
}
