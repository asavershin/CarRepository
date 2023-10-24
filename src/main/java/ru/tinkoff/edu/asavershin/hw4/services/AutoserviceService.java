package ru.tinkoff.edu.asavershin.hw4.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.asavershin.hw4.dao.entities.Autoservice;
import ru.tinkoff.edu.asavershin.hw4.dao.repositories.AutoserviceRepository;
import ru.tinkoff.edu.asavershin.hw4.dto.ResponseAutoservice;
import ru.tinkoff.edu.asavershin.hw4.handlers.localexceptions.NotFoundException;

@Service
@RequiredArgsConstructor
public class AutoserviceService {
    private final AutoserviceRepository autoserviceRepository;

    public Autoservice createAutoservice(Autoservice autoservice){
        return autoserviceRepository.save(autoservice);
    }

    public Autoservice getAutoservice(Long autoserviceId) {
        Autoservice autoservice = autoserviceRepository.findAutoserviceById(autoserviceId);
        if(autoservice == null){
            throw new NotFoundException("Салон с Id "+autoserviceId+" не найден");
        }
        return autoservice;
    }

    public Autoservice updateAutoservice(Long autoserviceId, String name,
                                                 String address, String country) {
        Autoservice autoservice = autoserviceRepository.findAutoserviceById(autoserviceId);
        if(autoservice == null){
            throw new NotFoundException("Салон с Id "+autoserviceId+" не найден");
        }
        autoservice.setName(name);
        autoservice.setAddress(address);
        autoservice.setCountry(country);
        return autoserviceRepository.save(autoservice);
    }
}
