package vn.techmaster.login.hash;

public interface Hashing {
    public String hashPassword(String password);
    public boolean validatePassword(String originalPassword, String storedPassword);
  }
