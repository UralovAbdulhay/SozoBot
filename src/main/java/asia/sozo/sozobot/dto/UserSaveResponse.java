package asia.sozo.sozobot.dto;

import asia.sozo.sozobot.entity.TgUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSaveResponse {

    TgUser user;

    boolean isSaved;

}
