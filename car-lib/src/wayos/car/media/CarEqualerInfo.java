package wayos.car.media;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class CarEqualerInfo implements Parcelable {

    private final int EqualerValueLength = 10;
    /**
     * this is eq equaler type
     */
    private int equalerType ;
    /**
     * this is equaler effect value
     */
    private int[] equalerValue = new int[EqualerValueLength];

    /*
      *
      * audio effect const
     */
    public static final int[] EQ_POPULAR = {0, 0, -3, 0, 0, -5, 0, 0, -4, 0};
    public static final int[] EQ_ROCK = {0, 0, 7, 0, 0, -5, 0, 0, 6, 0};
    public static final int[] EQ_JAZZ = {0, 0, 0, 0, 0, 3, 0, 0, 6, 0};
    public static final int[] EQ_CLASSICAL = {0, 0, 5, 0, 0, 3, 0, 0, 6, 0};
    public static final int[] EQ_DANCE = {0, 0, 1, 0, 0, 6, 0, 0, 4, 0};
    public static final int[] EQ_FLAT = {0, 0, -3, 0, 0, 2, 0, 0, 3, 0};
    public static final int[] EQ_DIY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //user
    // TODO: fill vocal effect vaule
    public static final int[] EQ_VOCAL = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //speaker
    public static final int[] EQ_LOW={2};
    public static final int[] EQ_MIDDLE_LOW={4};
    public static final int[] EQ_BETWEEN_LOW_HIGH={5};
    public static final int[] EQ_MIDDLE_HIGH={7};
    public static final int[] EQ_HIGH={8};

    public CarEqualerInfo (int type,int[] value) {
        if (value != null && value.length <= EqualerValueLength) {
            System.arraycopy(value, 0, equalerValue, 0, value.length);
        } else if (value != null && value.length > EqualerValueLength) {
            System.arraycopy(value, 0, equalerValue, 0, EqualerValueLength);
        }
        equalerType = type;
    }
    protected CarEqualerInfo(Parcel in) {
        equalerType = in.readInt();
        in.readIntArray(equalerValue);

    }

    public int getEqualerType() {
        return equalerType;
    }

    public int[] getEqualerValue() {
        return equalerValue;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(equalerType);
        dest.writeIntArray(equalerValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarEqualerInfo> CREATOR = new Creator<CarEqualerInfo>() {
        @Override
        public CarEqualerInfo createFromParcel(Parcel in) {
            return new CarEqualerInfo(in);
        }

        @Override
        public CarEqualerInfo[] newArray(int size) {
            return new CarEqualerInfo[size];
        }
    };
}
