package vn.edu.iuh.fit.userservice.dtos;

public class TestDto {
    private String username;

    public TestDto() {}

    public TestDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "TestDto{" + "username='" + username + '\'' + '}';
    }
}
