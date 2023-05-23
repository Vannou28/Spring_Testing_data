package com.vannou.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Console;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.vannou.test.entity.Fire;
import com.vannou.test.entity.Fireman;
import com.vannou.test.repository.FireRepository;
import com.vannou.test.repository.FiremanRepository;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class DataTest {
    // on peut ensuite injecter un repository et l'utiliser normalement
    @Autowired
    FireRepository fireRepository;

    @Autowired
    FiremanRepository firemanRepository;

    @Test
    void testCreateFire() {
        int severity = 8;
        Instant date = Instant.now();
            
        var fire = new Fire(severity, date);

            // flush envoie les données instantanément à la base
        fireRepository.saveAndFlush(fire);

        Optional<Fire> fromDB = fireRepository.findById(fire.getId());
//
        assertTrue(fromDB.isPresent());
       assertEquals(fire.getId(), fromDB.get().getId());
       assertEquals(date, fromDB.get().getDate());
        assertEquals(severity, fromDB.get().getSeverity());
    }


    @Test
    void testChallenge1() {
        
        
        int severity = 8;

        Instant date = Instant.now();
        System.out.println(date);   
        Fire fire = new Fire(severity, date);
        fireRepository.saveAndFlush(fire);

        date = Instant.now();
        Fire fire2 = new Fire(severity-2, date);
        fireRepository.saveAndFlush(fire2);

        //enregistremen de fireman avec une liste de fire (2)
        String name = "vannou";
        List<Fire> fires = new ArrayList<Fire>();
        fires.add(fire);
        fires.add(fire2);
        
        Fireman fireman = new Fireman("vannou" , fires );
        firemanRepository.saveAndFlush(fireman);


        Optional<Fireman> fromDB = firemanRepository.findById(fireman.getId());
//
        assertTrue(fromDB.isPresent());
        assertEquals(fire.getId(), fromDB.get().getId());
        assertEquals(name, fromDB.get().getName());
        assertEquals(fires, fromDB.get().getFires());
    }

    @Test
    void testChallenge2() {
        //test negative fire 
        
        int severity = -1;
        Instant date = Instant.now();
        Fire fire = new Fire(severity, date);

        assertThrows(ConstraintViolationException.class, () -> {
            fireRepository.saveAndFlush(fire);
        }, "severity must be positive");

    }

   //@Test
   //void testChallenge3() {
   //    //écupérer le pompier le plus chevronné 
   //    int nbFireCreated = 20;

   //    for (int i = 0; i < nbFireCreated; i++) {
   //        int severity = new Random().nextInt(10) + 1;
   //        Instant date = Instant.now();
   //        Fire fire = new Fire(severity, date);
   //        fireRepository.saveAndFlush(fire);
   //    }


   //        String name = "VannouLeVeteran";
   //        List<Fire> fires = new ArrayList<Fire>();
   //        int nbFireList = 15;
   //        
   //        for (int j = 0; j < nbFireList; j++) {
   //            Optional<Fire> fire = fireRepository.findById(Long.valueOf((new Random().nextInt(20) + 1)));
   //            fires.add(fire.get());
   //        }
   //        
   //        Fireman firemanVannou = new Fireman(name , fires );
   //        firemanRepository.saveAndFlush(firemanVannou);
   //    

   //        name = "DadouLeJunior";
   //        List<Fire> fires2 = new ArrayList<Fire>();
   //        nbFireList = 5;
   //        
   //        for (int j = 0; j < nbFireList; j++) {
   //            Optional<Fire> fire = fireRepository.findById(Long.valueOf((new Random().nextInt(20) + 1)));
   //            fires2.add(fire.get());
   //        }
   //        
   //        Fireman firemanDadou = new Fireman(name , fires2 );
   //        firemanRepository.saveAndFlush(firemanDadou);


   //    Optional<Fireman> fromDBVeteran = firemanRepository.getVeteran();
//

   //    assertEquals(fromDBVeteran.get(), firemanVannou);
   //}

}
