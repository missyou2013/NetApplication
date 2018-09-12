package net.com.mvp.ac.wuxi;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-07-18.
 */

public class WeatherBean {
    String CityName,CityDescription,LiveWeather;
    List<Map<String,Object>> list;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityDescription() {
        return CityDescription;
    }

    public void setCityDescription(String cityDescription) {
        CityDescription = cityDescription;
    }

    public String getLiveWeather() {
        return LiveWeather;
    }

    public void setLiveWeather(String liveWeather) {
        LiveWeather = liveWeather;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }
}
