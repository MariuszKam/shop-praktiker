package com.practice.shop.entities.admin;

import java.util.List;

public abstract class Report<T> {

    private List<T> reportList;

    public Report(List<T> reportList) {
        this.reportList = reportList;
    }

    public List<T> getReportList() {
        return reportList;
    }

    public void setReportList(List<T> reportList) {
        this.reportList = reportList;
    }
}
