package asia.sozo.sozobot.mappings;

import asia.sozo.sozobot.entity.TgUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "chatId", source = "id"),
    })
    TgUser toMyUser(User user);

}
