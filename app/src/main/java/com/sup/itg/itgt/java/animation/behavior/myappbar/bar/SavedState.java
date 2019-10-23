package com.sup.itg.itgt.java.animation.behavior.myappbar.bar;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.customview.view.AbsSavedState;

public class SavedState extends AbsSavedState {
    public int firstVisibleChildIndex;
    public  float firstVisibleChildPercentageShown;
    public boolean firstVisibleChildAtMinimumHeight;
    public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
        public SavedState createFromParcel(Parcel source, ClassLoader loader) {
            return new SavedState(source, loader);
        }

        public SavedState createFromParcel(Parcel source) {
            return new SavedState(source, (ClassLoader) null);
        }

        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };

    public SavedState(Parcel source, ClassLoader loader) {
        super(source, loader);
        this.firstVisibleChildIndex = source.readInt();
        this.firstVisibleChildPercentageShown = source.readFloat();
        this.firstVisibleChildAtMinimumHeight = source.readByte() != 0;
    }

    public SavedState(Parcelable superState) {
        super(superState);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.firstVisibleChildIndex);
        dest.writeFloat(this.firstVisibleChildPercentageShown);
        dest.writeByte((byte) (this.firstVisibleChildAtMinimumHeight ? 1 : 0));
    }
}
