package models;

import java.util.Objects;

public class Users {

    private String userName;
    private String userCompanyPosition;
    private String userCompanyRole;
    private  int departmentId;
    private int id;


    public Users(String userName, String userCompanyPosition, String userCompanyRole, int departmentId){
        this.userName = userName;
        this.userCompanyPosition = userCompanyPosition;
        this.userCompanyRole = userCompanyRole;
        this.departmentId = departmentId;

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCompanyPosition() {
        return userCompanyPosition;
    }

    public void setUserCompanyPosition(String userCompanyPosition) {
        this.userCompanyPosition = userCompanyPosition;
    }

    public String getUserCompanyRole() {
        return userCompanyRole;
    }

    public void setUserCompanyRole(String userCompanyRole) {
        this.userCompanyRole = userCompanyRole;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return departmentId == users.departmentId &&
                userName.equals(users.userName) &&
                userCompanyPosition.equals(users.userCompanyPosition) &&
                userCompanyRole.equals(users.userCompanyRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userCompanyPosition, userCompanyRole, departmentId);
    }


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
