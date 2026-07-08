package com.example.productor.controller;

import com.example.productor.dto.SenalVitalDTO;
import com.example.productor.service.GeneradorSenalesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productor")
public class SenalVitalController {

    private final GeneradorSenalesService generadorSenalesService;

    public SenalVitalController(GeneradorSenalesService generadorSenalesService) {
        this.generadorSenalesService = generadorSenalesService;
    }

    /**
     * Permite enviar una señal vital recibida desde Postman.
     */
    @PostMapping("/publicar")
    public ResponseEntity<String> publicarSenal(@Valid @RequestBody SenalVitalDTO senalVitalDTO) {

        generadorSenalesService.publicarSenal(senalVitalDTO);

        return ResponseEntity.ok("Señal vital publicada correctamente en Kafka.");
    }

    /**
     * Genera una señal de prueba y la publica en Kafka.
     */
    @PostMapping("/generar")
    public ResponseEntity<String> generarSenal() {

        generadorSenalesService.generarYPublicarSenal();

        return ResponseEntity.ok("Se generó y publicó una señal vital correctamente.");
    }

}
