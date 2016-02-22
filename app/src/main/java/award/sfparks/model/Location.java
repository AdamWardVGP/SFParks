
package award.sfparks.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("needs_recoding")
    public Boolean needsRecoding;
    public String longitude;
    public String latitude;
    @SerializedName("human_address")
    public String humanAddress;

}
