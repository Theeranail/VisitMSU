package com.example.visitmsu.visitmsu.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by TheeranaiAsipong on 27/11/2559.
 */

public class Msu {
    public Msu(){}

    public class ListData implements Serializable{
        public ListData() {}

        @SerializedName("id_building")
        private int id_building;
        @SerializedName("name_building")
        private String name_building;
        @SerializedName("tel_building")
        private String tel_building;
        @SerializedName("email_building")
        private String email_building;
        @SerializedName("detail_building")
        private String detail_building;
        @SerializedName("id_type_building")
        private int id_type_building;
        @SerializedName("latitude_building")
        private String latitude_building;
        @SerializedName("longitude_building")
        private String longitude_building;
        @SerializedName("address_building")
        private String address_building;
        @SerializedName("image_main")
        private String image_main;
     //   private List<Msu.listSubbuilding> sub_building;

        public int getId_building() {
            return id_building;
        }

        public void setId_building(int id_building) {
            this.id_building = id_building;
        }

        public String getImage_main() {
            return image_main;
        }

        public void setImage_main(String image_main) {
            this.image_main = image_main;
        }

        public String getAddress_building() {
            return address_building;
        }

        public void setAddress_building(String address_building) {
            this.address_building = address_building;
        }

        public String getLatitude_building() {
            return latitude_building;
        }

        public void setLatitude_building(String latitude_building) {
            this.latitude_building = latitude_building;
        }

        public String getLongitude_building() {
            return longitude_building;
        }

        public void setLongitude_building(String longitude_building) {
            this.longitude_building = longitude_building;
        }

        public int getId_type_building() {
            return id_type_building;
        }

        public void setId_type_building(int id_type_building) {
            this.id_type_building = id_type_building;
        }

        public String getDetail_building() {
            return detail_building;
        }

        public void setDetail_building(String detail_building) {
            this.detail_building = detail_building;
        }

        public String getEmail_building() {
            return email_building;
        }

        public void setEmail_building(String email_building) {
            this.email_building = email_building;
        }

        public String getTel_building() {
            return tel_building;
        }

        public void setTel_building(String tel_building) {
            this.tel_building = tel_building;
        }

        public String getName_building() {
            return name_building;
        }

        public void setName_building(String name_building) {
            this.name_building = name_building;
        }

//        public List<listSubbuilding> getSub_building() {
//            return sub_building;
//        }
//
//        public void setSub_building(List<listSubbuilding> sub_building) {
//            this.sub_building = sub_building;
//        }
    }

    public class listSubbuilding  implements Serializable{
        public listSubbuilding() {}

        @SerializedName("id_sub_building")
        private int id_sub_building;
        @SerializedName("name_sub_building")
        private String name_sub_building;
        @SerializedName("email_sub_building")
        private String email_sub_building;
        @SerializedName("tel_sub_building")
        private String tel_sub_building;
        @SerializedName("detail_sub_building")
        private String detail_sub_building;
        @SerializedName("id_building")
        private int id_building;
        @SerializedName("address_sub_building")
        private String address_sub_building;
        @SerializedName("image_main_sub")
        private String image_main_sub;

        public int getId_sub_building() {
            return id_sub_building;
        }

        public void setId_sub_building(int id_sub_building) {
            this.id_sub_building = id_sub_building;
        }

        public String getName_sub_building() {
            return name_sub_building;
        }

        public void setName_sub_building(String name_sub_building) {
            this.name_sub_building = name_sub_building;
        }

        public String getEmail_sub_building() {
            return email_sub_building;
        }

        public void setEmail_sub_building(String email_sub_building) {
            this.email_sub_building = email_sub_building;
        }

        public String getTel_sub_building() {
            return tel_sub_building;
        }

        public void setTel_sub_building(String tel_sub_building) {
            this.tel_sub_building = tel_sub_building;
        }

        public String getDetail_sub_building() {
            return detail_sub_building;
        }

        public void setDetail_sub_building(String detail_sub_building) {
            this.detail_sub_building = detail_sub_building;
        }

        public int getId_building() {
            return id_building;
        }

        public void setId_building(int id_building) {
            this.id_building = id_building;
        }

        public String getAddress_sub_building() {
            return address_sub_building;
        }

        public void setAddress_sub_building(String address_sub_building) {
            this.address_sub_building = address_sub_building;
        }

        public String getImage_main_sub() {
            return image_main_sub;
        }

        public void setImage_main_sub(String image_main_sub) {
            this.image_main_sub = image_main_sub;
        }
    }

    public class getSearchList implements  Serializable{
        public getSearchList(){}

        @SerializedName("id_building")
        private int id_building;
        @SerializedName("name_building")
        private String name_building;
        @SerializedName("id_type_building")
        private int id_type_building;
        @SerializedName("latitude_building")
        private String latitude_building;
        @SerializedName("longitude_building")
        private String longitude_building;
        @SerializedName("id_sub_building")
        private int id_sub_building;
        @SerializedName("name_sub_building")
        private String name_sub_building;
        @SerializedName("image_main")
        private String image_main;
        @SerializedName("image_main_sub")
        private String image_main_sub;

        public int getId_building() {
            return id_building;
        }

        public void setId_building(int id_building) {
            this.id_building = id_building;
        }

        public String getName_building() {
            return name_building;
        }

        public void setName_building(String name_building) {
            this.name_building = name_building;
        }

        public int getId_type_building() {
            return id_type_building;
        }

        public void setId_type_building(int id_type_building) {
            this.id_type_building = id_type_building;
        }

        public String getLatitude_building() {
            return latitude_building;
        }

        public void setLatitude_building(String latitude_building) {
            this.latitude_building = latitude_building;
        }

        public String getLongitude_building() {
            return longitude_building;
        }

        public void setLongitude_building(String longitude_building) {
            this.longitude_building = longitude_building;
        }

        public int getId_sub_building() {
            return id_sub_building;
        }

        public void setId_sub_building(int id_sub_building) {
            this.id_sub_building = id_sub_building;
        }

        public String getName_sub_building() {
            return name_sub_building;
        }

        public void setName_sub_building(String name_sub_building) {
            this.name_sub_building = name_sub_building;
        }

        public String getImage_main() {
            return image_main;
        }

        public void setImage_main(String image_main) {
            this.image_main = image_main;
        }

        public String getImage_main_sub() {
            return image_main_sub;
        }

        public void setImage_main_sub(String image_main_sub) {
            this.image_main_sub = image_main_sub;
        }
    }

    public class Test implements Serializable{
        public Test(){}

        @SerializedName("id_building")
        private int id_building;
        @SerializedName("name_building")
        private String name_building;
        @SerializedName("tel_building")
        private String tel_building;
        @SerializedName("email_building")
        private String email_building;
        @SerializedName("detail_building")
        private String detail_building;
        @SerializedName("id_type_building")
        private int id_type_building;
        @SerializedName("latitude_building")
        private String latitude_building;
        @SerializedName("longitude_building")
        private String longitude_building;
        @SerializedName("address_building")
        private String address_building;
        @SerializedName("image_main")
        private String image_main;

        public int getId_building() {
            return id_building;
        }

        public void setId_building(int id_building) {
            this.id_building = id_building;
        }

        public String getName_building() {
            return name_building;
        }

        public void setName_building(String name_building) {
            this.name_building = name_building;
        }

        public String getTel_building() {
            return tel_building;
        }

        public void setTel_building(String tel_building) {
            this.tel_building = tel_building;
        }

        public String getEmail_building() {
            return email_building;
        }

        public void setEmail_building(String email_building) {
            this.email_building = email_building;
        }

        public String getDetail_building() {
            return detail_building;
        }

        public void setDetail_building(String detail_building) {
            this.detail_building = detail_building;
        }

        public int getId_type_building() {
            return id_type_building;
        }

        public void setId_type_building(int id_type_building) {
            this.id_type_building = id_type_building;
        }

        public String getLatitude_building() {
            return latitude_building;
        }

        public void setLatitude_building(String latitude_building) {
            this.latitude_building = latitude_building;
        }

        public String getLongitude_building() {
            return longitude_building;
        }

        public void setLongitude_building(String longitude_building) {
            this.longitude_building = longitude_building;
        }

        public String getAddress_building() {
            return address_building;
        }

        public void setAddress_building(String address_building) {
            this.address_building = address_building;
        }

        public String getImage_main() {
            return image_main;
        }

        public void setImage_main(String image_main) {
            this.image_main = image_main;
        }
    }
}
