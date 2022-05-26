package carrotproject.infra;

import javax.naming.NameParser;

import javax.naming.NameParser;

import carrotproject.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import carrotproject.domain.*;


@Service
public class PolicyHandler{
    @Autowired ReservationRepository reservationRepository;
    
    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_CancelReservationComplete(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;
        PaymentCancelled event = paymentCancelled;
        System.out.println("\n\n##### listener CancelReservationComplete : " + paymentCancelled.toJson() + "\n\n");

        reservationRepository.findById(event.getRsvId()).ifPresent(r -> r.cancelReservationComplete(event));
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_CompleteReservation(@Payload PaymentApproved paymentApproved){

        if(!paymentApproved.validate()) return;
        PaymentApproved event = paymentApproved;
        System.out.println("\n\n##### listener CompleteReservation : " + paymentApproved.toJson() + "\n\n");
      
        reservationRepository.findById(event.getRsvId()).ifPresent(r -> r.completeReservation(event));
    }


}


