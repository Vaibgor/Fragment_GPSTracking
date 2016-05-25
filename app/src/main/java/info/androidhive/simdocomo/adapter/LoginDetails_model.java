package info.androidhive.simdocomo.adapter;

/**
 * Created by web3 on 5/22/2016.
 */
public class LoginDetails_model {
    private int userId;
    private String agency_code, userName;
public LoginDetails_model(){}
    public LoginDetails_model(int userId, String agency_code, String userName) {
        this.userId = userId;
        this.agency_code = agency_code;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAgency_code() {
        return agency_code;
    }

    public void setAgency_code(String agency_code) {
        this.agency_code = agency_code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
