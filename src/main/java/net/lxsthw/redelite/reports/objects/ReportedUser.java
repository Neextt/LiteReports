package net.lxsthw.redelite.reports.objects;

import java.util.ArrayList;

public class ReportedUser {

    private String name;
    private int reportedX;
    private ArrayList<String> reportedBy = new ArrayList<String>();


    public ReportedUser(String name, int reportedX, ArrayList<String> reportedBy) {
        super();
        this.name = name;
        this.reportedX = reportedX;
        this.reportedBy = reportedBy;
    }


    public String getName() {
        return name;
    }


    public int getReportedX() {
        return reportedX;
    }


    public ArrayList<String> getReportedBy() {
        return reportedBy;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setReportedX(int reportedX) {
        this.reportedX = reportedX;
    }


    public void setReportedBy(ArrayList<String> reportedBy) {
        this.reportedBy = reportedBy;
    }



}

