package asia.sozo.sozobot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Objects;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TgUser {

    @Id
    Long chatId;

    String firstName;

    String lastName;

    String userName;

    String name;

    String surname;

    String lang;

    String loginPhone;


    @Enumerated(value = EnumType.STRING)
    UserState state = UserState.START;


    public boolean isState(UserState userState) {
        return Objects.equals(this.state, userState);
    }


    public String getChatIdString() {
        return String.valueOf(this.chatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TgUser)) return false;
        TgUser tgUser = (TgUser) o;
        return getChatId().equals(tgUser.getChatId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId());
    }

    @Override
    public String toString() {
        return "TgUser{" +
                "chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", lang='" + lang + '\'' +
                ", state=" + state +
                '}';
    }


}
