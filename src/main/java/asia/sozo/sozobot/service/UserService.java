package asia.sozo.sozobot.service;

import asia.sozo.sozobot.dto.UserSaveResponse;
import asia.sozo.sozobot.entity.TgUser;
import asia.sozo.sozobot.mappings.UserMapper;
import asia.sozo.sozobot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public UserSaveResponse save(Update update) {

        UserSaveResponse userSaveResponse = new UserSaveResponse();
        User chatUser = new User();


        if (update.hasMessage()) {
            chatUser = update.getMessage().getFrom();
        } else if (update.hasCallbackQuery()) {
            chatUser = update.getCallbackQuery().getFrom();
        }

        TgUser user = new TgUser();

        Optional<TgUser> byId = userRepository.findById(chatUser.getId());

        if (byId.isPresent()) {
            userSaveResponse.setUser(byId.get());

            userSaveResponse.setSaved(true);
            return userSaveResponse;
        } else {
            user = userMapper.toMyUser(chatUser);

            System.out.println(user);

            userRepository.save(user);
            userSaveResponse.setUser(user);
            userSaveResponse.setSaved(false);
            return userSaveResponse;
        }

    }


    public TgUser save(TgUser tgUser) {
        return userRepository.save(tgUser);
    }


}




