package renegade.game.card;

import renegade.game.Server;

import java.util.*;
import java.util.stream.Collectors;

public class CardSet {
    private List<CommandCard> cards = new ArrayList<>();

    public CardSet(List<CommandCard> cards){
        this.cards.addAll(cards);
    }

    public int countCommandPoints(Server containment){
        return (int) cards.stream().mapToLong(c -> c.getCommands().stream().filter(cont -> cont == containment).count()).sum();
    }

    public List<Server> getColorsWithAtLeast(int commandPoints){
        Map<Server, Integer> counts = getColorCounts();
        return counts.entrySet().stream().filter(es -> es.getValue() >= commandPoints).map(es -> es.getKey()).collect(Collectors.toList());
    }

    public Map<Server, Integer> getColorCounts(){
        Map<Server, Integer> counts = new HashMap<>();
        Arrays.stream(Server.values()).forEach(server -> counts.put(server, 0));
        cards.stream().forEach(card -> {
            for (Server server: Server.values()) {
                counts.put(server, (int) (counts.get(server) + card.getCommands().stream().filter(cmd -> cmd == server).count()));
            }
        });
        return counts;
    }

    public void deselectAll(){
        cards.stream().forEach(card -> card.setSelected(false));
    }

    public List<CommandCard> getCards() {
        return cards;
    }
}
