package br.univali.m3_bernardobraga;

public class dados {
    private String Latitude;
    private String Longitude;
    private String Base64;

    public dados(String lat,String longi , String Base64){
        this.Latitude = lat;
        this.Longitude = longi;
        this.Base64 = Base64;

    }
    public dados(){


    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getBase64() {
        return Base64;
    }

    public void setBase64(String base64) {
        Base64 = base64;
    }
}
