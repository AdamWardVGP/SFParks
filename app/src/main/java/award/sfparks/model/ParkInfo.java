package award.sfparks.model;

import com.google.gson.annotations.SerializedName;

public class ParkInfo {

    public String parktype;
    public String parkname;
    public String email;
    public String zipcode;
    public String parkid;
    public String supdist;
    public String number;
    public String parkservicearea;
    @SerializedName("location_1")
    public Location location;
    public String acreage;
    public String psamanager;

}
