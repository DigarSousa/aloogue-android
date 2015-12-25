package alugueis.alugueis.model;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Pedreduardo on 02/12/2015.
 */
public class LoggedUser extends SugarRecord {

    private static LoggedUser logged;

    public LoggedUser() {
    }


    private Long id;
    private String cpfCnpj;
    private String name;
    private String email;
    private String password;
    private double latitude;
    private double longitude;
    private AddressApp addressApp;
    private byte[] picture;
    private int loggedAs;
    private List<Phone> phones;
    private String businessInitialHour;
    private String businessFinalHour;

    public static LoggedUser getInstance() {
        if (logged == null)
            logged = new LoggedUser();
        return logged;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public AddressApp getAddressApp() {
        return addressApp;
    }

    public void setAddressApp(AddressApp addressApp) {
        this.addressApp = addressApp;
    }

    public String getBusinessFinalHour() {
        return businessFinalHour;
    }

    public void setBusinessFinalHour(String businessFinalHour) {
        this.businessFinalHour = businessFinalHour;
    }

    public String getBusinessInitialHour() {
        return businessInitialHour;
    }

    public void setBusinessInitialHour(String businessInitialHour) {
        this.businessInitialHour = businessInitialHour;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getLoggedAs() {
        return loggedAs;
    }

    public void setLoggedAs(int loggedAs) {
        this.loggedAs = loggedAs;
    }
}
