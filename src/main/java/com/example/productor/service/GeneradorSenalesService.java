package com.example.productor.service;

import com.example.productor.dto.SenalVitalDTO;
import com.example.productor.kafka.KafkaProducerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GeneradorSenalesService {
    private static final Logger logger = LoggerFactory.getLogger(GeneradorSenalesService.class);

    private final KafkaProducerService kafkaProducerService;
    private final Random random = new Random();

    private final String[] pacientes =
            {"Juan Pérez", "María González", "Carlos Ruiz", "Ana Silva", "Pedro Soto"};
    private final String[] habitaciones = {"101", "102", "201", "302", "404"};

    public GeneradorSenalesService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    private int enviosRealizados = 0;

    @Scheduled(fixedRate = 1000)
    public void generarYPublicarSenal() {

        if (enviosRealizados >= 10) {
            return; // Deja de enviar después de 10
        }

        SenalVitalDTO senal = new SenalVitalDTO();

        senal.setNombrePaciente(pacientes[random.nextInt(pacientes.length)]);
        senal.setHabitacion(habitaciones[random.nextInt(habitaciones.length)]);
        senal.setColorAlerta("VERDE"); // Siempre iniciamos en verde como pediste
        senal.setEstado("Pendiente");

        // Seleccionamos aleatoriamente qué tipo de señal generar
        int tipoSenal = random.nextInt(3);

        if (tipoSenal == 0) {
            // Frecuencia cardíaca (rango normal 60-120)
            int fc = 60 + random.nextInt(91);
            senal.setSignosVitales("Frecuencia cardíaca: " + fc + " bpm");
        } else if (tipoSenal == 1) {
            // Presión arterial (sistólica 100-140)
            int sistolica = 100 + random.nextInt(41);
            senal.setSignosVitales("Presión arterial: " + sistolica + "/80 mmHg");
        } else {
            // Temperatura (35.0 a 38.0)
            double temp = 35.0 + (random.nextDouble() * 3.0);
            senal.setSignosVitales("Temperatura: " + String.format("%.1f", temp) + "°C");
        }

        kafkaProducerService.enviarSenalVital(senal);
        enviosRealizados++;
        logger.info("Envío {}/10 realizado", enviosRealizados);

    }

    public void publicarSenal(SenalVitalDTO senalVitalDTO) {
        kafkaProducerService.enviarSenalVital(senalVitalDTO);
    }
}
