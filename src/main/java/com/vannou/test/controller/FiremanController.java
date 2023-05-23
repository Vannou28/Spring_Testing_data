package com.vannou.test.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.vannou.test.dto.FiremanStatsDTO;
import com.vannou.test.entity.Fire;
import com.vannou.test.entity.Fireman;
import com.vannou.test.repository.FireRepository;
import com.vannou.test.repository.FiremanRepository;

@RestController
@RequestMapping("/fireman")
public class FiremanController {
    @Autowired
    FiremanRepository firemanRepository;

    @Autowired
    FireRepository fireRepository;

    record FiremanData(Long id, String name, int firesCount) {
        static FiremanData fromFireman(Fireman fireman) {
            return new FiremanData(fireman.getId(), fireman.getName(), fireman.getFires().size());
        }
    }

    @GetMapping("/veteran")
    public FiremanData getVeteran() {
        Optional<Fireman> veteranMaybe = firemanRepository.getVeteran();
        Fireman veteran = veteranMaybe.orElseThrow(() -> new NotFoundException());
        return FiremanData.fromFireman(veteran);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }

    @GetMapping("/stats")
    public FiremanStatsDTO getStats() {
        List<Fireman> firemen = firemanRepository.findAll();
        List<Fire> fires = fireRepository.findAll();
        
        FiremanStatsDTO stats = new FiremanStatsDTO();
        stats.setFiremenCount(firemen.size());
        stats.setFiresCount(fires.size());

        return stats;
    }
}
