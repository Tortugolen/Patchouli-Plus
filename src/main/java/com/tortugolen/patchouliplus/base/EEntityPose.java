package com.tortugolen.patchouliplus.base;

public enum EEntityPose {
    DEFAULT(0, -45, 180),
    DISPLAY(-15, 45, 180),

    FRONT_VIEW(0, 0 ,180),
    SIDE_VIEW(0, -90 ,180),
    TOP_VIEW(-90, 0 ,180);


    public final float x;
    public final float y;
    public final float z;

    EEntityPose(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}