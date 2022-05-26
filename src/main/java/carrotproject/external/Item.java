package carrotproject.external;

import lombok.Data;
import java.util.Date;
@Data
public class Item {

    private Long itemId;
    private String name;
    private String category;
    private Integer price;
    private String description;
    private String status;
    private Integer score;


}
