package pl.lotto.domain.loginandregister;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("users")
record User(@Id String id,
            @Indexed(unique = true) String username,
            String password) {
}
