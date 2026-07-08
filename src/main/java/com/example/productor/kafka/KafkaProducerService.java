package com.example.productor.kafka;

import com.example.productor.dto.SenalVitalDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, SenalVitalDTO> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, SenalVitalDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publica una señal vital en el tópico de Kafka.
     *
     * @param senalVitalDTO Señal vital a publicar.
     */
    public void enviarSenalVital(SenalVitalDTO senalVitalDTO) {

        kafkaTemplate.send(topic, senalVitalDTO);

        System.out.println("========================================");
        System.out.println("Mensaje enviado a Kafka");
        System.out.println("Topic      : " + topic);
        System.out.println("Paciente   : " + senalVitalDTO.getNombrePaciente());
        System.out.println("Habitación : " + senalVitalDTO.getHabitacion());
        System.out.println("Alerta     : " + senalVitalDTO.getColorAlerta());
        System.out.println("signos     : " + senalVitalDTO.getSignosVitales());
        System.out.println("========================================");

    }

}
