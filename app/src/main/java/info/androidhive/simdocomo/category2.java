package info.androidhive.simdocomo;

//modified my vaibhav pote on 26/05/2016
// deleted unnecessary data
// model class use for Close and pending class
public class category2 {
    public category2(){}
    String u_id,first_name, age, segment;

    public category2(String pro, String uname, String dob, String segment) {
        this.u_id = pro;
        this.first_name = uname;
        this.age = dob;//this.imageurl = img;
        this.segment = segment;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}