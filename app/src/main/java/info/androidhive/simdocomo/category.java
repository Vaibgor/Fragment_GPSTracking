package info.androidhive.simdocomo;
//modified my vaibhav pote on 26/05/2016
// deleted unnecessary data
// model class use for Open class
public class category {
    String rec_id,acc_no,custName,name1, segmentImage,code, location;
    public category(){}
    public category(String rec_id, String ac_no, String custName, String location,
                    String code, String name, String segmentImage) {
        this.rec_id = rec_id;
        this.acc_no = ac_no;
        this.custName = custName;
        this.location = location;
        this.code = code;
        this.name1 = name;
        this.segmentImage = segmentImage;
    }

    public String getRec_id() {
        return rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(String acc_no) {
        this.acc_no = acc_no;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSegmentImage() {
        return segmentImage;
    }

    public void setSegmentImage(String segmentImage) {
        this.segmentImage = segmentImage;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}