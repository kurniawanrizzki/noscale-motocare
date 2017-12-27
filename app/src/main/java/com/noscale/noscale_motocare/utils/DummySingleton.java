package com.noscale.noscale_motocare.utils;

import com.noscale.noscale_motocare.models.Garage;
import com.noscale.noscale_motocare.models.Schedule;
import com.noscale.noscale_motocare.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by kurniawanrrizki on 26/12/17.
 */

public class DummySingleton {

    private static DummySingleton instance;
    private List<Garage> garageList;
    private List<Schedule> schedules;

    public static DummySingleton getInstance () {
        if (null == instance) {
            instance = new DummySingleton();
        }

        return instance;
    }

    public User getDummyUser () {
        return new User(1,"test","12345", "test@noscale.com");
    }

    public List<Garage> getDummyGarage () {

        garageList = new ArrayList<>();

        createGarage(
                1, "AUTOWORKS",
                "Jl. Karet Pedurenan No. 52, RT 6/7, (021) 5254419",
                -6.2208794,
                106.7593203,
                "Bengkel A",
                Global.AVAILABLE_TO_ORDER
        );

        createGarage(
                2, "Bengkel Bayu Motor",
                "Jl. Pedurenan Mesjid 1 No.1, RT.16/RW.4, Karet Kuningan, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12940",
                -6.2214271,
                106.7602361,
                "Bengkel B",
                Global.FULL_TO_ORDER
        );

        createGarage(
                3, "Bengkel Ahass Honda Jakarta Selatan",
                "Jl. Prof. DR. Satrio Blok Tiong No.4, RT.2/RW.6, Karet Kuningan, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12940",
                -6.221175,
                106.7509444,
                "Bengkel C",
                Global.AVAILABLE_TO_ORDER
        );

        createGarage(
                4, "Trisula Motor",
                "Jl. Prof. Dr. Satrio,Jaksel., RT.4/RW.4, Karet Kuningan, Kecamatan Setiabudi, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12940",
                -6.2217564,
                106.8226249,
                "Bengkel D",
                Global.AVAILABLE_TO_ORDER
        );

        createGarage(
                5, "Yamaha Bintang Jaya Motor",
                "Jl. Kebon Kacang Raya No.3, RT.1/RW.8, Kb. Kacang, Tanah Abang, Kota Jakarta Pusat, Daerah Khusus Ibukota Jakarta 10240",
                -6.1930296,
                106.7449295,
                "Bengkel E",
                Global.FULL_TO_ORDER
        );

        return garageList;

    }

    public List<Schedule> getSchedule () {

        schedules = new ArrayList<>();
        Calendar time = Calendar.getInstance(Locale.getDefault());

        Schedule schedule = new Schedule(
                1, "Yamaha Bintang Jaya Motor", Global.SERVICE_TYPE_FULL,time.getTimeInMillis(), Global.SCHEDULE_IS_ACTIVE
        );

        schedules.add(schedule);

        time.set(2018, 1, 10);

        schedule = new Schedule(
                2, "AUTOWORKS", Global.SERVICE_TYPE_PARTIALLY, time.getTimeInMillis(), Global.SCHEDULE_NOT_ACTIVE
        );

        schedules.add(schedule);

        time.set(2018, 1, 20);

        schedule = new Schedule(
                3, "AUTOWORKS", Global.SERVICE_TYPE_PARTIALLY, time.getTimeInMillis(), Global.SCHEDULE_IS_ACTIVE
        );

        schedules.add(schedule);

        return schedules;

    }

    public void createGarage (
            int id,
            String name,
            String address,
            double lat,
            double lng,
            String description,
            int status
    ) {

        Garage garage = new Garage();
        garage.setId(id);
        garage.setName(name);
        garage.setAddress(address);
        garage.setLat(lat);
        garage.setLng(lng);
        garage.setDescription(description);
        garage.setStatus(status);

        garageList.add(garage);
    }

}
