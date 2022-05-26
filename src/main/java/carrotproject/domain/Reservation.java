package carrotproject.domain;

import carrotproject.domain.ReservationCreated;
import carrotproject.domain.ReservationCompleted;
import carrotproject.domain.ReservationConfirmed;
import carrotproject.domain.ReservationCompleteCacelled;
import carrotproject.domain.ReservationDeleted;
import carrotproject.ReservationApplication;
import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import lombok.Data;
import java.util.Date;
import carrotproject.ReservationApplication;

@Entity
@Table(name="Reservation_table")
@Data

public class Reservation  {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rsvId;
    
    
    private String rsvStatus;
    
    
    private Date rsvDate;
    
    
    private String rsvPlace;
    
    
    private Long itemId;
    
    
    private String buyerId;


    @PrePersist
    public void validate() {
        
        //Get request from Item
        try {
            carrotproject.external.Item item =
            ReservationApplication.applicationContext.getBean(carrotproject.external.ItemService.class)
            .getItem(this.itemId);
 
            if("SOLDOUT".equals(item.getStatus())) {
                throw new RuntimeException("Item is already sold out!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Item is not Exist!");
        }
        
    }

    @PostPersist
    public void onPostPersist(){

        ReservationCreated reservationCreated = new ReservationCreated();
        BeanUtils.copyProperties(this, reservationCreated);
        reservationCreated.publishAfterCommit();



    }

    @PostUpdate
    public void onPostUpdate(){
        
        if(this.rsvStatus.equals("CONFIRMED")) {
            System.out.println("onPostUpdate :"  + this.rsvStatus);
            ReservationConfirmed reservationConfirmed = new ReservationConfirmed();
            BeanUtils.copyProperties(this, reservationConfirmed);
            reservationConfirmed.publishAfterCommit();
        } else if(this.rsvStatus.equals("COMPLETED"))  {
            System.out.println("ConPostUpdateCOMPLETED :"  + this.rsvStatus);
            ReservationCompleted reservationCompleted = new ReservationCompleted();
            BeanUtils.copyProperties(this, reservationCompleted);
            reservationCompleted.publishAfterCommit();   
        } else {
            ReservationCompleteCacelled reservationCompleteCacelled = new ReservationCompleteCacelled();
            BeanUtils.copyProperties(this, reservationCompleteCacelled);
            reservationCompleteCacelled.publishAfterCommit();
        }
    }

    @PostRemove
    public void onPostRemove(){
        ReservationDeleted reservationDeleted = new ReservationDeleted();
        BeanUtils.copyProperties(this, reservationDeleted);
        reservationDeleted.publishAfterCommit();

    }


    public static ReservationRepository repository(){
        ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(ReservationRepository.class);
        return reservationRepository;
    }


    public void cancelReservationComplete(PaymentCancelled paymentCancelled){
        System.out.println("cancelReservationComplete");
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(this, reservation);
        reservation.setRsvStatus("CONFIRMED");
        repository().save(reservation);
    }

    public void completeReservation(PaymentApproved paymentApproved){

        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(this, reservation);
        reservation.setRsvStatus("COMPLETED");
        repository().save(reservation);
    }

}
