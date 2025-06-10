package cda.model;

import java.time.LocalDate;


public class Contact {
    private String firstName;
    private String lastName;
    private String nickname;
    private Gender gender;
    private LocalDate birthDate;
    private String profilePic;

    private String mobilePhone;
    private String homePhone;
    private String email;
    private String gitLink;

    private String address;
    private int zipCode;
    private String city;

    private String companyName;
    private String workPhone;
    private String companyPhone;
    private String companyEmail;
    private String website;

    private String description;

    public enum Gender {
        MALE, FEMALE, NON_BINAIRE
    }

    public Contact(String firstName, String lastName, String nickname, Gender gender, LocalDate birthDate, String profilePic, String mobilePhone, String homePhone, String email, String gitLink, String address, int zipCode, String city, String companyName, String workPhone, String companyPhone, String companyEmail, String website, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profilePic = profilePic;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.gitLink = gitLink;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.companyName = companyName;
        this.workPhone = workPhone;
        this.companyPhone = companyPhone;
        this.companyEmail = companyEmail;
        this.website = website;
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGitLink() {
        return gitLink;
    }

    public void setGitLink(String gitLink) {
        this.gitLink = gitLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
