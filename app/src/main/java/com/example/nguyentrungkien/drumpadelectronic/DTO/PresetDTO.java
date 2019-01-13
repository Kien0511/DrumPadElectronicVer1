package com.example.nguyentrungkien.drumpadelectronic.DTO;

public class PresetDTO {
    String record_name;
    String preset_name;

    public String getRecord_name() {
        return record_name;
    }

    public void setRecord_name(String record_name) {
        this.record_name = record_name;
    }

    public String getPreset_name() {
        return preset_name;
    }

    public void setPreset_name(String preset_name) {
        this.preset_name = preset_name;
    }

    public PresetDTO() {

    }

    public PresetDTO(String record_name, String preset_name) {

        this.record_name = record_name;
        this.preset_name = preset_name;
    }
}
