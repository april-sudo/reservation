package carrotproject.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name="item", url="${api.path.item}", fallback = ItemServiceImpl.class)
public interface ItemService {
    @RequestMapping(method= RequestMethod.GET, path="/items/{itemId}")
    public Item getItem(@PathVariable("itemId") Long itemId);

}