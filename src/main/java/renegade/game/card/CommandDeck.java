package renegade.game.card;

import renegade.game.Server;
import renegade.game.card.advanced.*;

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
        shuffle();
    }

    private void buildAdvancedCommandDeck(){
        addCard(new AdvancedCommandAdrenalineSurge());
        addCard(new AdvancedCommandAdrenalineSurge());
        addCard(new AdvancedCommandBlackMarket());
        addCard(new AdvancedCommandChristmasTree());
        addCard(new AdvancedCommandCinoBracelet());
        addCard(new AdvancedCommandCinoBracelet());
        addCard(new AdvancedCommandConsoleCowboy());
        addCard(new AdvancedCommandDancingPig());
        addCard(new AdvancedCommandDancingPig());
        addCard(new AdvancedCommandDataShift());
        addCard(new AdvancedCommandDataShift());
        addCard(new AdvancedCommandDigitalTattoo());
        addCard(new AdvancedCommandDigitalTattoo());
        addCard(new AdvancedCommandDigitalTattoo());
        addCard(new AdvancedCommandDuality());
        addCard(new AdvancedCommandDuality());
        addCard(new AdvancedCommandDuality());
        addCard(new AdvancedCommandEmitEMP());
        addCard(new AdvancedCommandEmitEMP());
        addCard(new AdvancedCommandExoticSoftware());
        addCard(new AdvancedCommandGingerCool());
        addCard(new AdvancedCommandGingerCool());
        addCard(new AdvancedCommandHologram());
        addCard(new AdvancedCommandIteration());
        addCard(new AdvancedCommandLivewireVoodoo());
        addCard(new AdvancedCommandLivewireVoodoo());
        addCard(new AdvancedCommandMicroBionix());
        addCard(new AdvancedCommandNydamShip());
        addCard(new AdvancedCommandNydamShip());
        addCard(new AdvancedCommandNydamShip());
        addCard(new AdvancedCommandOverwriteInterrupt());
        addCard(new AdvancedCommandOverwriteInterrupt());
        addCard(new AdvancedCommandOverwriteInterrupt());
        addCard(new AdvancedCommandPiedPiper());
        addCard(new AdvancedCommandPiedPiper());
        addCard(new AdvancedCommandProjectConsciousness());
        addCard(new AdvancedCommandReadRequestIntercept());
        addCard(new AdvancedCommandReadRequestIntercept());
        addCard(new AdvancedCommandReplicantJar());
        addCard(new AdvancedCommandResetSpark());
        addCard(new AdvancedCommandSelfModifyingInhibitor());
        addCard(new AdvancedCommandSelfModifyingInhibitor());
        addCard(new AdvancedCommandSidekick());
        addCard(new AdvancedCommandSidekick());
        addCard(new AdvancedCommandVulnerabilityScanner());
        addCard(new AdvancedCommandVulnerabilityScanner());
    }

    private void buildBlueDeck(){
        addCard(new CommandCard("Starting Blue1.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Blue2.jpg", new Server[]{Server.BLUE, Server.PURPLE}));
        addCard(new CommandCard("Starting Blue3.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Blue4.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Blue5.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Blue6.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Blue7.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Blue8.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Blue9.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Blue10.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Blue11.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Blue12.jpg", new Server[]{Server.GREEN, Server.PURPLE}));
        addCard(new CommandCard("Starting Blue13.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Blue14.jpg", new Server[]{Server.RED, Server.PURPLE}));
        addCard(new CommandCard("Starting Blue15.jpg", new Server[]{Server.YELLOW, Server.PURPLE}));
    }

    private void buildGreenDeck(){
        addCard(new CommandCard("Starting Green1.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Green2.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Green3.jpg", new Server[]{Server.BLUE, Server.PURPLE}));
        addCard(new CommandCard("Starting Green4.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Green5.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Green6.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Green7.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Green8.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Green9.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Green10.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Green11.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Green12.jpg", new Server[]{Server.GREEN, Server.PURPLE}));
        addCard(new CommandCard("Starting Green13.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Green14.jpg", new Server[]{Server.RED, Server.PURPLE}));
        addCard(new CommandCard("Starting Green15.jpg", new Server[]{Server.YELLOW, Server.PURPLE}));
    }

    private void buildRedDeck(){
        addCard(new CommandCard("Starting Red1.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Red2.jpg", new Server[]{Server.BLUE, Server.PURPLE}));
        addCard(new CommandCard("Starting Red3.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Red4.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Red5.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Red6.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Red7.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Red8.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Red9.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Red10.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Red11.jpg", new Server[]{Server.GREEN, Server.PURPLE}));
        addCard(new CommandCard("Starting Red12.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Red13.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Red14.jpg", new Server[]{Server.RED, Server.PURPLE}));
        addCard(new CommandCard("Starting Red15.jpg", new Server[]{Server.YELLOW, Server.PURPLE}));
    }

    private void buildPurpleDeck(){
        addCard(new CommandCard("Starting Violet1.jpg", new Server[]{Server.PURPLE}));
        addCard(new CommandCard("Starting Violet2.jpg", new Server[]{Server.PURPLE}));
        addCard(new CommandCard("Starting Violet3.jpg", new Server[]{Server.PURPLE}));
        addCard(new CommandCard("Starting Violet4.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Violet5.jpg", new Server[]{Server.BLUE, Server.PURPLE}));
        addCard(new CommandCard("Starting Violet6.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Violet7.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Violet8.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Violet9.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Violet10.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Violet11.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Violet12.jpg", new Server[]{Server.GREEN, Server.PURPLE}));
        addCard(new CommandCard("Starting Violet13.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Violet14.jpg", new Server[]{Server.RED, Server.PURPLE}));
        addCard(new CommandCard("Starting Violet15.jpg", new Server[]{Server.YELLOW, Server.PURPLE}));
    }

    private void buildYellowDeck(){
        addCard(new CommandCard("Starting Yellow1.jpg", new Server[]{Server.GREEN}));
        addCard(new CommandCard("Starting Yellow2.jpg", new Server[]{Server.BLUE, Server.PURPLE}));
        addCard(new CommandCard("Starting Yellow3.jpg", new Server[]{Server.BLUE}));
        addCard(new CommandCard("Starting Yellow4.jpg", new Server[]{Server.BLUE, Server.BLUE}));
        addCard(new CommandCard("Starting Yellow5.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Yellow6.jpg", new Server[]{Server.YELLOW, Server.YELLOW}));
        addCard(new CommandCard("Starting Yellow7.jpg", new Server[]{Server.GREEN, Server.GREEN}));
        addCard(new CommandCard("Starting Yellow8.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Yellow9.jpg", new Server[]{Server.RED, Server.RED}));
        addCard(new CommandCard("Starting Yellow10.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Yellow11.jpg", new Server[]{Server.YELLOW}));
        addCard(new CommandCard("Starting Yellow12.jpg", new Server[]{Server.GREEN, Server.PURPLE}));
        addCard(new CommandCard("Starting Yellow13.jpg", new Server[]{Server.RED}));
        addCard(new CommandCard("Starting Yellow14.jpg", new Server[]{Server.RED, Server.PURPLE}));
        addCard(new CommandCard("Starting Yellow15.jpg", new Server[]{Server.YELLOW, Server.PURPLE}));
    }
}
