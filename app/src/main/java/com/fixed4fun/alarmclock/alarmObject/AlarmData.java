package com.fixed4fun.alarmclock.alarmObject;


import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class AlarmData implements Parcelable {

    private int hour;
    private int minute;
    private boolean monday_friday;
    private boolean saturday_sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private boolean onOrOff;
    private boolean selected;
    private int flag;

    public AlarmData(int hour, int minute, boolean monday_friday, boolean saturday_sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, boolean onOrOff, boolean selected, int flag) {
        this.hour = hour;
        this.minute = minute;
        this.monday_friday = monday_friday;
        this.saturday_sunday = saturday_sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.onOrOff = onOrOff;
        this.selected = selected;
        this.flag = flag;
    }

    protected AlarmData(Parcel in) {
        hour = in.readInt();
        minute = in.readInt();
        monday_friday = in.readByte() != 0;
        saturday_sunday = in.readByte() != 0;
        monday = in.readByte() != 0;
        tuesday = in.readByte() != 0;
        wednesday = in.readByte() != 0;
        thursday = in.readByte() != 0;
        friday = in.readByte() != 0;
        saturday = in.readByte() != 0;
        sunday = in.readByte() != 0;
        onOrOff = in.readByte() != 0;
        selected = in.readByte() != 0;
        flag = in.readInt();
    }

    public static final Creator<AlarmData> CREATOR = new Creator<AlarmData>() {
        @Override
        public AlarmData createFromParcel(Parcel in) {
            return new AlarmData(in);
        }

        @Override
        public AlarmData[] newArray(int size) {
            return new AlarmData[size];
        }
    };

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isMonday_friday() {
        return monday_friday;
    }

    public void setMonday_friday(boolean monday_friday) {
        this.monday_friday = monday_friday;
    }

    public boolean isSaturday_sunday() {
        return saturday_sunday;
    }

    public void setSaturday_sunday(boolean saturday_sunday) {
        this.saturday_sunday = saturday_sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isOnOrOff() {
        return onOrOff;
    }

    public void setOnOrOff(boolean onOrOff) {
        this.onOrOff = onOrOff;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public long getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "hour=" + hour +
                ", minute=" + minute +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeByte((byte) (monday_friday ? 1 : 0));
        dest.writeByte((byte) (saturday_sunday ? 1 : 0));
        dest.writeByte((byte) (monday ? 1 : 0));
        dest.writeByte((byte) (tuesday ? 1 : 0));
        dest.writeByte((byte) (wednesday ? 1 : 0));
        dest.writeByte((byte) (thursday ? 1 : 0));
        dest.writeByte((byte) (friday ? 1 : 0));
        dest.writeByte((byte) (saturday ? 1 : 0));
        dest.writeByte((byte) (sunday ? 1 : 0));
        dest.writeByte((byte) (onOrOff ? 1 : 0));
        dest.writeByte((byte) (selected ? 1 : 0));
        dest.writeInt(flag);
    }
}
