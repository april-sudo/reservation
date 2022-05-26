package carrotproject.domain;

import carrotproject.domain.*;
import carrotproject.infra.AbstractEvent;
import lombok.Data;
import java.util.Date;
@Data
public class PaymentCancelled extends AbstractEvent {

    private Long payId;
    private Long rsvId;
    private Long itemId;
    private String status;
    private Integer price;

}

