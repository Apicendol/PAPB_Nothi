package cena.mcs.nothi;

public class Users {
    String userId, name, profile, email, password;

    public Users(String userId, String name, String profile, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.profile = profile;
        this.email = email;
        this.password = password;
    }

    public String tanggal, isi;

    public Users(String t, String i) {
        this.tanggal = t;
        this.isi = i;
    }

    public String singkatan(){
        if (this.tanggal != null && this.tanggal.length() != 0)
            return this.tanggal.substring(0, 2).toUpperCase();
        else return "";
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public Users() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
