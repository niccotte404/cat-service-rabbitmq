package itmo.dev.cat_microservice.operations;

import itmo.dev.cat_microservice.services.interfaces.CatService;
import itmo.dev.common.dto.CatDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class GetCatByIdOperation implements Operation{

    private final RabbitTemplate rabbitTemplate;
    private final CatService catService;

    public GetCatByIdOperation(RabbitTemplate rabbitTemplate, CatService catService) {
        this.rabbitTemplate = rabbitTemplate;
        this.catService = catService;
    }

    @Override
    public void execute(Message message, String nameQueue) {
        Integer ownerId = (Integer) message.getMessageProperties().getHeaders().get("ownerId");
        Integer id = (Integer) message.getMessageProperties().getHeaders().get("catId");
        CatDto resultById = catService.getCatById(ownerId, id);
        rabbitTemplate.convertAndSend(nameQueue, resultById);
    }
}
