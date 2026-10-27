package com.tortugolen.patchouliplus.Base;

import com.google.gson.annotations.SerializedName;

public enum EEntityPose {
    @SerializedName("default")
    DEFAULT(0, -45, 180),
    @SerializedName("display")
    DISPLAY(-15, 45, 180),

    @SerializedName("front_view")
    FRONT_VIEW(0, 0, 180),
    @SerializedName("side_view")
    SIDE_VIEW(0, -90, 180),
    @SerializedName("top_view")
    TOP_VIEW(-90, 0, 180);

    public final float x;
    public final float y;
    public final float z;

    EEntityPose(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}