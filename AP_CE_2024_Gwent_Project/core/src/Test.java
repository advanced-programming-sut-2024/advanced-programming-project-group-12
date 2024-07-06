import com.google.gson.Gson;
import com.mygdx.game.model.game.Game;
import com.mygdx.game.model.game.card.Action;
import com.mygdx.game.model.network.massage.serverResponse.LoginResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponse;
import com.mygdx.game.model.network.massage.serverResponse.ServerResponseType;
import com.mygdx.game.model.network.session.Session;
import com.mygdx.game.model.user.Player;
import com.mygdx.game.model.user.User;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String a = "a";
        String b = "b";
        User user1 = new User(a,a,a,a);
        user1.setDeck(new ArrayList<String>());
        Gson gson = new Gson();
        Game game = new Game(new Player(new User(a,a,a,a)), new Player(new User(b,b,b,b)), null);
        System.out.println(gson.toJson(game));
        System.out.println(gson.fromJson(gson.toJson(game), Game.class));
    }
}
