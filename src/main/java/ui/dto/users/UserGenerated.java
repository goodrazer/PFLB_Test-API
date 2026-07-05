package ui.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Builder
public class UserGenerated {
    private final String firstName;
    private final String lastName;
    private final String age;
    private final String sex;
    private final String money;
}
