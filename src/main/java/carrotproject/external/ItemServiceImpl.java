package carrotproject.external;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    public Item getItem(Long itemId) {
        System.out.println("@@@@@@@ 상품조회가 지연중 입니다. @@@@@@@@@@@@");
        System.out.println("@@@@@@@ 상품조회가 지연중 입니다. @@@@@@@@@@@@");
        System.out.println("@@@@@@@ 상품조회가 지연중 입니다. @@@@@@@@@@@@");
       
        Item item = new Item();
        return item;
    }
    
}
