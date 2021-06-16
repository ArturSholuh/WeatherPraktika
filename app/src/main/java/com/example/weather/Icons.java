package com.example.weather;

public class Icons {
    public static int getIcon(int id){
        if(id >= 200 && id < 300) return R.drawable.ic_11d_thunderstorms;
        if(id >= 300 && id < 400) return R.drawable.ic_09d_cloudy_with_heavy_rain;
        if(id >= 500 && id <= 504) return R.drawable.ic_10d_heavy_rain_showers;
        if(id == 511) return R.drawable.ic_13d_cloudy_with_heavy_snow;
        if(id >= 520 && id <= 531) return R.drawable.ic_09d_cloudy_with_heavy_rain;
        if(id >= 600 && id < 700) return R.drawable.ic_13d_cloudy_with_heavy_snow;
        if(id >= 701 && id < 800) return R.drawable.ic_50d_mist;
        if(id == 800) return R.drawable.ic_01d_clear_sky;
        if(id == 801) return R.drawable.ic_02d_few_clouds;
        if(id == 802) return R.drawable.ic_03_scattered_clouds;
        if(id == 803 || id == 804) return R.drawable.ic_04d_black_low_cloud;

        return 0;
    }

    public static int getBackground(int id){
        if(id >= 200 && id < 300) return R.drawable.lightning;
        if(id >= 300 && id < 400) return R.drawable.rain;
        if(id >= 500 && id <= 504) return R.drawable.rain;
        if(id == 511) return R.drawable.snow;
        if(id >= 520 && id <= 531) return R.drawable.rain;
        if(id >= 600 && id < 700) return R.drawable.snow;
        if(id >= 701 && id < 800) return R.drawable.fog;
        if(id == 800) return R.drawable.sun;
        if(id == 801) return R.drawable.sun_and_clouds;
        if(id == 802) return R.drawable.cloudy;
        if(id == 803 || id == 804) return R.drawable.cloudy;

        return 0;
    }

    public static int getToolbarColor(int id){
        if(id >= 200 && id < 300) return R.color.gray_700;
        if(id >= 300 && id < 400) return R.color.gray_700;
        if(id >= 500 && id <= 504) return R.color.gray_700;
        if(id == 511) return R.color.gray_700;
        if(id >= 520 && id <= 531) return R.color.gray_700;
        if(id >= 600 && id < 700) return R.color.gray_700;
        if(id >= 701 && id < 800) return R.color.gray_700;
        if(id == 800) return R.color.light_blue_700; //
        if(id == 801) return R.color.blue_700; //
        if(id == 802) return R.color.gray_700;
        if(id == 803 || id == 804) return R.color.gray_700;

        return 0;
    }

    public static int getStatusBarColor(int id){
        if(id >= 200 && id < 300) return R.color.gray_800;
        if(id >= 300 && id < 400) return R.color.gray_800;
        if(id >= 500 && id <= 504) return R.color.gray_800;
        if(id == 511) return R.color.gray_800;
        if(id >= 520 && id <= 531) return R.color.gray_800;
        if(id >= 600 && id < 700) return R.color.gray_800;
        if(id >= 701 && id < 800) return R.color.gray_800;
        if(id == 800) return R.color.light_blue_800; //
        if(id == 801) return R.color.blue_800; //
        if(id == 802) return R.color.gray_800;
        if(id == 803 || id == 804) return R.color.gray_800;

        return 0;
    }
}
