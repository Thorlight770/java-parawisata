package com.java.parawisata.javaparawisata.Entity;

public class GlobalParameter {
    private String group;
    private String criteria;
    private String value;
    private String Text;
    private String info01;
    private String info02;
    private Long sort;

    public GlobalParameter() {
    }

    public GlobalParameter(String group, String criteria, String value, String text, String info01, String info02, Long sort) {
        this.group = group;
        this.criteria = criteria;
        this.value = value;
        Text = text;
        this.info01 = info01;
        this.info02 = info02;
        this.sort = sort;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getInfo01() {
        return info01;
    }

    public void setInfo01(String info01) {
        this.info01 = info01;
    }

    public String getInfo02() {
        return info02;
    }

    public void setInfo02(String info02) {
        this.info02 = info02;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "GlobalParameter{" +
                "group='" + group + '\'' +
                ", criteria='" + criteria + '\'' +
                ", value='" + value + '\'' +
                ", Text='" + Text + '\'' +
                ", info01='" + info01 + '\'' +
                ", info02='" + info02 + '\'' +
                ", sort=" + sort +
                '}';
    }
}
