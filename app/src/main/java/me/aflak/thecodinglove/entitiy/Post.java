package me.aflak.thecodinglove.entitiy;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable{
    private String description;
    private String link;

    public Post(String description, String link) {
        this.description = description;
        this.link = link;
    }

    protected Post(Parcel in) {
        description = in.readString();
        link = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(link);
    }
}
