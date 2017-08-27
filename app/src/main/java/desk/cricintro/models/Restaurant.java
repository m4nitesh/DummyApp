
package desk.cricintro.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    private String header;
    private int type = 0;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }

}
