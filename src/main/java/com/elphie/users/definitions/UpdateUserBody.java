package com.elphie.users.definitions;

@Data
public class UpdateUserBody {
    // Properties
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private byte[] profileImage;
    private String accountType;
    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getAccountType() { return accountType; }
    public byte[] getProfileImage() { return profileImage; }
}
