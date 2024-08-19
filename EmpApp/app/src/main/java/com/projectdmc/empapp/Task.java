package com.projectdmc.empapp;
public class Task {
    int t_id;
    String t_name;
    String t_issue_date;
    String t_deadline;
    String t_desc;
    String t_receiver;
    String t_sender;
    String t_status;
    String t_display;
    String createdTimestamp;

    public Task(int t_id, String t_name, String t_issue_date, String t_deadline, String t_desc, String t_receiver, String t_sender, String t_status, String t_display, String createdTimestamp) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_issue_date = t_issue_date;
        this.t_deadline = t_deadline;
        this.t_desc = t_desc;
        this.t_receiver = t_receiver;
        this.t_sender = t_sender;
        this.t_status = t_status;
        this.t_display = t_display;
        this.createdTimestamp = createdTimestamp;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_issue_date() {
        return t_issue_date;
    }

    public void setT_issue_date(String t_issue_date) {
        this.t_issue_date = t_issue_date;
    }

    public String getT_deadline() {
        return t_deadline;
    }

    public void setT_deadline(String t_deadline) {
        this.t_deadline = t_deadline;
    }



    public String getT_desc() {
        return t_desc;
    }

    public void setT_desc(String t_desc) {
        this.t_desc = t_desc;
    }

    public String getT_receiver() {
        return t_receiver;
    }

    public void setT_receiver(String t_receiver) {
        this.t_receiver = t_receiver;
    }

    public String getT_sender() {
        return t_sender;
    }

    public void setT_sender(String t_sender) {
        this.t_sender = t_sender;
    }

    public String getT_status() {
        return t_status;
    }

    public void setT_status(String t_status) {
        this.t_status = t_status;
    }

    public String getT_display() {
        return t_display;
    }

    public void setT_display(String t_display) {
        this.t_display = t_display;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }
    @Override
    public String toString() {
        return "Task{" +
                "t_id=" + t_id +
                ", t_name='" + t_name + '\'' +
                ", t_issue_date='" + t_issue_date + '\'' +
                ", t_deadline='" + t_deadline + '\'' +
                ", t_desc='" + t_desc + '\'' +
                ", t_receiver='" + t_receiver + '\'' +
                ", t_sender='" + t_sender + '\'' +
                ", t_status='" + t_status + '\'' +
                ", t_display='" + t_display + '\'' +
                ", createdTimestamp='" + createdTimestamp + '\'' +
                '}';
    }
}