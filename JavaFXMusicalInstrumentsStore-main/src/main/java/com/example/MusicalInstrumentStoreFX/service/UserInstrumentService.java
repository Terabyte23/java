package com.example.MusicalInstrumentStoreFX.service;

import com.example.MusicalInstrumentStoreFX.model.entity.AppUser;
import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;
import com.example.MusicalInstrumentStoreFX.model.entity.UserInstrument;
import com.example.MusicalInstrumentStoreFX.model.repository.UserInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInstrumentService {
    @Autowired
    private UserInstrumentRepository repository;

    public Optional<UserInstrument> find(AppUser user, Instruments instrument) {
        return repository.findByUserAndInstrument(user, instrument);
    }

    public void saveOrUpdate(AppUser user, Instruments instrument, int quantity) {
        Optional<UserInstrument> existing = find(user, instrument);
        UserInstrument record = existing.orElseGet(() -> {
            UserInstrument ui = new UserInstrument();
            ui.setUser(user);
            ui.setInstrument(instrument);
            ui.setQuantity(0);
            return ui;
        });

        record.setQuantity(record.getQuantity() + quantity);
        repository.save(record);
    }

    public void returnInstrument(AppUser user, Instruments instrument, int quantity) {
        UserInstrument record = find(user, instrument).orElseThrow(() ->
                new IllegalArgumentException("Невозможно вернуть то, что не покупали"));

        if (record.getQuantity() < quantity) {
            throw new IllegalArgumentException("Нельзя вернуть больше, чем куплено");
        }

        record.setQuantity(record.getQuantity() - quantity);
        repository.save(record);
    }
}

