package api.models;

import com.google.code.gson.Gson;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

   private String password;
   private String username;
}
