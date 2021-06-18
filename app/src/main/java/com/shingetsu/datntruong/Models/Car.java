package com.shingetsu.datntruong.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String name;
    private String rate;
    private String version;
    private Boolean status;
    private String seat;
    private String gate;
    private String shift;
    private String image;
    private String pice;
    private String material;
    private String address;
    private String category;
    private String pid;

    public  Car(){

    }

    protected Car(Parcel in) {
        name = in.readString();
        rate = in.readString();
        version = in.readString();
        byte tmpStatus = in.readByte();
        status = tmpStatus == 0 ? null : tmpStatus == 1;
        seat = in.readString();
        gate = in.readString();
        shift = in.readString();
        image = in.readString();
        pice = in.readString();
        material = in.readString();
        address = in.readString();
        category = in.readString();
        pid = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPice() {
        return pice;
    }

    public void setPice(String pice) {
        this.pice = pice;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Car(String name, String rate, String version, Boolean status, String seat, String gate, String shift, String image, String pice, String material, String address, String category, String pid) {
        this.name = name;
        this.rate = rate;
        this.version = version;
        this.status = status;
        this.seat = seat;
        this.gate = gate;
        this.shift = shift;
        this.image = image;
        this.pice = pice;
        this.material = material;
        this.address = address;
        this.category = category;
        this.pid = pid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rate);
        dest.writeString(version);
        dest.writeByte((byte) (status == null ? 0 : status ? 1 : 2));
        dest.writeString(seat);
        dest.writeString(gate);
        dest.writeString(shift);
        dest.writeString(image);
        dest.writeString(pice);
        dest.writeString(material);
        dest.writeString(address);
        dest.writeString(category);
        dest.writeString(pid);
    }
}


