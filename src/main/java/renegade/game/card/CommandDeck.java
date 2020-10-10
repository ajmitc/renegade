package renegade.game.card;

import renegade.game.Containment;
import renegade.game.Server;

public class CommandDeck extends Deck<CommandCard> {

    public CommandDeck(Server server) {
        super();
        if (server == null)
            buildAdvancedCommandDeck();
        else if (server == Server.BLUE)
            buildBlueDeck();
        else if (server == Server.GREEN)
            buildGreenDeck();
        else if (server == Server.RED)
            buildRedDeck();
        else if (server == Server.YELLOW)
            buildYellowDeck();
        else if (server == Server.PURPLE)
            buildPurpleDeck();
    }

    private void buildAdvancedCommandDeck(){
        addCard(new AdvancedCommandAdrenalineSurge());
        addCard(new AdvancedCommandAdrenalineSurge());
        addCard(new AdvancedCommandBlackMarket());
        //addCard(new CommandCard("Command1.jpg", new Containment[]{Containment.UPLINK}, new Containment[]{Containment.UPLINK, Containment.UPLINK}));
    }

    private void buildBlueDeck(){
        addCard(new CommandCard("Starting Blue1.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Blue2.jpg", new Containment[]{Containment.DATA_NODE, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Blue3.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Blue4.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Blue5.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Blue6.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Blue7.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Blue8.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Blue9.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Blue10.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Blue11.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Blue12.jpg", new Containment[]{Containment.UPLINK, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Blue13.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Blue14.jpg", new Containment[]{Containment.VIRUS, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Blue15.jpg", new Containment[]{Containment.REPLICANT, Containment.ROOTKIT}));
    }

    private void buildGreenDeck(){
        addCard(new CommandCard("Starting Green1.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Green2.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Green3.jpg", new Containment[]{Containment.DATA_NODE, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Green4.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Green5.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Green6.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Green7.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Green8.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Green9.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Green10.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Green11.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Green12.jpg", new Containment[]{Containment.UPLINK, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Green13.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Green14.jpg", new Containment[]{Containment.VIRUS, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Green15.jpg", new Containment[]{Containment.REPLICANT, Containment.ROOTKIT}));
    }

    private void buildRedDeck(){
        addCard(new CommandCard("Starting Red1.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Red2.jpg", new Containment[]{Containment.DATA_NODE, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Red3.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Red4.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Red5.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Red6.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Red7.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Red8.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Red9.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Red10.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Red11.jpg", new Containment[]{Containment.UPLINK, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Red12.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Red13.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Red14.jpg", new Containment[]{Containment.VIRUS, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Red15.jpg", new Containment[]{Containment.REPLICANT, Containment.ROOTKIT}));
    }

    private void buildYellowDeck(){
        addCard(new CommandCard("Starting Violet1.jpg", new Containment[]{Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet2.jpg", new Containment[]{Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet3.jpg", new Containment[]{Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet4.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Violet5.jpg", new Containment[]{Containment.DATA_NODE, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet6.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Violet7.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Violet8.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Violet9.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Violet10.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Violet11.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Violet12.jpg", new Containment[]{Containment.UPLINK, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet13.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Violet14.jpg", new Containment[]{Containment.VIRUS, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Violet15.jpg", new Containment[]{Containment.REPLICANT, Containment.ROOTKIT}));
    }

    private void buildPurpleDeck(){
        addCard(new CommandCard("Starting Yellow1.jpg", new Containment[]{Containment.UPLINK}));
        addCard(new CommandCard("Starting Yellow2.jpg", new Containment[]{Containment.DATA_NODE, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Yellow3.jpg", new Containment[]{Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Yellow4.jpg", new Containment[]{Containment.DATA_NODE, Containment.DATA_NODE}));
        addCard(new CommandCard("Starting Yellow5.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Yellow6.jpg", new Containment[]{Containment.REPLICANT, Containment.REPLICANT}));
        addCard(new CommandCard("Starting Yellow7.jpg", new Containment[]{Containment.UPLINK, Containment.UPLINK}));
        addCard(new CommandCard("Starting Yellow8.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Yellow9.jpg", new Containment[]{Containment.VIRUS, Containment.VIRUS}));
        addCard(new CommandCard("Starting Yellow10.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Yellow11.jpg", new Containment[]{Containment.REPLICANT}));
        addCard(new CommandCard("Starting Yellow12.jpg", new Containment[]{Containment.UPLINK, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Yellow13.jpg", new Containment[]{Containment.VIRUS}));
        addCard(new CommandCard("Starting Yellow14.jpg", new Containment[]{Containment.VIRUS, Containment.ROOTKIT}));
        addCard(new CommandCard("Starting Yellow15.jpg", new Containment[]{Containment.REPLICANT, Containment.ROOTKIT}));
    }
}
