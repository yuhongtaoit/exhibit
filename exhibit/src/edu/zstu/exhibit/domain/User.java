package edu.zstu.exhibit.domain;

public class User {
    private Integer Id;

    private String UserName;

    private String UserDepartment;

    private int UserType;

    private String LogInName;

    private String UserPassword;

    private int Status;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserDepartment() {
        return UserDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        UserDepartment = userDepartment;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getLogInName() {
        return LogInName;
    }

    public void setLogInName(String logInName) {
        LogInName = logInName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", UserName='" + UserName + '\'' +
                ", UserDepartment='" + UserDepartment + '\'' +
                ", UserType=" + UserType +
                ", LogInName='" + LogInName + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", Status=" + Status +
                '}';
    }
}