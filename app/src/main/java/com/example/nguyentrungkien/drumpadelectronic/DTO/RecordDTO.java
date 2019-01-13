package com.example.nguyentrungkien.drumpadelectronic.DTO;

public class RecordDTO {
    String name;
    String duration;
    String size;
    String presetName;

    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public RecordDTO(String name, String duration, String size) {

        this.name = name;
        this.duration = duration;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecordDTO() {

    }

    public RecordDTO(String name) {

        this.name = name;
    }
}
