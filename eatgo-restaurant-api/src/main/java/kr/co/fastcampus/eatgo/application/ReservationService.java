package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Region;
import kr.co.fastcampus.eatgo.domain.RegionRepository;
import kr.co.fastcampus.eatgo.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private RegionRepository regionRepository;

    @Autowired
    public ReservationService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Reservation> getReservations(Long restaurantId) {
        return null;
    }
}
