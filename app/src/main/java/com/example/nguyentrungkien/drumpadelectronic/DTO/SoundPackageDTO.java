package com.example.nguyentrungkien.drumpadelectronic.DTO;

public class SoundPackageDTO {
    private int soundpackage_id;
    private String soundpackage_name;
    private String soundpackage_content;
    private String soundpackage_unlock;

    public String getSoundpackage_unlock() {
        return soundpackage_unlock;
    }

    public void setSoundpackage_unlock(String soundpackage_unlock) {
        this.soundpackage_unlock = soundpackage_unlock;
    }

    public SoundPackageDTO(int soundpackage_id, String soundpackage_name, String soundpackage_content, String soundpackage_unlock) {
        this.soundpackage_id = soundpackage_id;
        this.soundpackage_name = soundpackage_name;
        this.soundpackage_content = soundpackage_content;
        this.soundpackage_unlock = soundpackage_unlock;
    }

    public int getSoundpackage_id() {
        return soundpackage_id;
    }

    public void setSoundpackage_id(int soundpackage_id) {
        this.soundpackage_id = soundpackage_id;
    }

    public String getSoundpackage_name() {
        return soundpackage_name;
    }

    public void setSoundpackage_name(String soundpackage_name) {
        this.soundpackage_name = soundpackage_name;
    }

    public String getSoundpackage_content() {
        return soundpackage_content;
    }

    public void setSoundpackage_content(String soundpackage_content) {
        this.soundpackage_content = soundpackage_content;
    }



    public SoundPackageDTO() {

    }





}
