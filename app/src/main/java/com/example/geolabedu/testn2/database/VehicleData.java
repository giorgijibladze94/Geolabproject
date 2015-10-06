package com.example.geolabedu.testn2.database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by GeoLabOwl on 06.08.15.
 */
public class VehicleData implements Serializable {

    private Integer ID;
    private ArrayList<String> image;
    private String nomeri;
    private String mail,categ,modeli,weli,decskr,calendar,image2;

    public VehicleData(ArrayList<String> image,String mail,String nomeri,String categ,String modeli,String weli,String decskr,String calendar) {
        this.image = image;
        this.mail=mail;
        this.nomeri=nomeri;
        this.categ=categ;
        this.modeli=modeli;
        this.weli=weli;
        this.decskr=decskr;
        this.calendar=calendar;
    }

    public VehicleData() {
    }

    public VehicleData(Integer ID,String image, String nomeri, String mail, String categ, String modeli, String weli, String decskr,String calendar) {
        this.ID = ID;
        this.image2=image;
        this.nomeri = nomeri;
        this.mail = mail;
        this.categ = categ;
        this.modeli = modeli;
        this.weli = weli;
        this.decskr = decskr;
        this.calendar=calendar;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public VehicleData(String image) {
        this.image2=image;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getWeli() {
        return weli;
    }

    public void setWeli(String weli) {
        this.weli = weli;
    }

    public String getDecskr() {
        return decskr;
    }

    public void setDecskr(String decskr) {
        this.decskr = decskr;
    }

    public String getNomeri() {
        return nomeri;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getModeli() {
        return modeli;
    }

    public void setModeli(String modeli) {
        this.modeli = modeli;
    }

    public void setNomeri(String nomeri) {
        this.nomeri = nomeri;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImage(int i) {
        String st=image.get(i);
        return st;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "ID=" + ID +
                ", image='" + image + '\'' +
                ", nomeri='" + nomeri + '\'' +
                ", mail='" + mail + '\'' +
                ", categ='" + categ + '\'' +
                ", modeli='" + modeli + '\'' +
                ", weli='" + weli + '\'' +
                ", decskr='" + decskr + '\'' +
                ", calendar='" + calendar + '\'' +
                '}';
    }

}
