package com.example.vinhphuc.bakingapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("videoURL")
    @Expose
    private String videoURL;

    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;

    public final static Creator<Step> CREATOR = new Creator<Step>() {
//        @SuppressWarnings({"unchecked"})
        @Override
        public Step createFromParcel(Parcel parcel) {
            Step instance = new Step();
            instance.id = ((Integer) parcel.readValue((Integer.class.getClassLoader())));
            instance.shortDescription = ((String) parcel.readValue((String.class.getClassLoader())));
            instance.description = ((String) parcel.readValue((String.class.getClassLoader())));
            instance.videoURL = ((String) parcel.readValue((String.class.getClassLoader())));
            instance.thumbnailURL = ((String) parcel.readValue((String.class.getClassLoader())));
            return instance;
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public Step() {}

    /**
     *
     * @param id
     * @param shortDescription
     * @param description
     * @param videoURL
     * @param thumbnailURL
     */
    public Step(
            Integer id,
            String shortDescription,
            String description,
            String videoURL,
            String thumbnailURL) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return this.videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return this.thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.shortDescription);
        dest.writeValue(this.description);
        dest.writeValue(this.videoURL);
        dest.writeValue(this.thumbnailURL);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}