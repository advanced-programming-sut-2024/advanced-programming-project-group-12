import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mygdx.game.model.game.card.AbstractElementAdapter;
import com.mygdx.game.model.game.card.AbstractCard;
import com.mygdx.game.model.game.card.AllCards;

public class Test {
    @org.junit.jupiter.api.Test
    public void testFriendRequest() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(AbstractCard.class, new AbstractElementAdapter());
        Gson gson = gsonBuilder.create();
        String string = gson.toJson(AllCards.ALBRICH.getAbstractCard());
        System.out.println(string);
        AbstractCard abstractCard = gson.fromJson(string, AbstractCard.class);
        assert abstractCard.equals(AllCards.ALBRICH.getAbstractCard());
    }
}
