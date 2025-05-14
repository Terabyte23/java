package com.example.MusicalInstrumentStoreFX.viewmodel;

import com.example.MusicalInstrumentStoreFX.model.entity.Instruments;

public class InstrumentRatingViewModel {
    private final String title;
    private final Integer count;
    private final Instruments instrument;

    public InstrumentRatingViewModel(String title, Integer count, Instruments instrument) {
        this.title = title;
        this.count = count;
        this.instrument = instrument;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCount() {
        return count;
    }

    public Instruments getInstrument() {
        return instrument;
    }
}
