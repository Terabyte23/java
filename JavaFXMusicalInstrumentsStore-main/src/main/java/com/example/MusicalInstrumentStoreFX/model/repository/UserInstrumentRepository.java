package com.example.MusicalInstrumentStoreFX.model.repository;

import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.model.entity.UserInstrument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInstrumentRepository extends JpaRepository<UserInstrument, Long> {
    Optional<UserInstrument> findByUserAndInstrument(AppUser user, Instruments instrument);
}
