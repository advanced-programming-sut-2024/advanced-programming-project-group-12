import com.google.gson.Gson;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.User;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class Test {
    public static void main(String[] args) {
        String a = "a";
        Gson gson = new Gson();
        System.out.println(gson.toJson(new LoginResponse(ServerResponseType.CONFIRM, new User(a,a,a,a))));
//        try {
//            Writer writer = new StringWriter();
//            User user = new User(a,a,a,a);
//            gson.toJson(user , writer);
//            writer.close();
//            System.out.println(writer.toString());
//        } catch (IOException e) {
//            System.err.println("IO exception in request handler");
//        }

    }
}
