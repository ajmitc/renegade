package renegade.game;

public enum GamePhaseStep {
    START_PHASE,
    END_PHASE,

    SETUP_BUILD_BOARD,
    SETUP_PLACE_AVATARS,
    SETUP_PLACE_DATA_NODES,

    COMMANDS_START_TURN,
    EXECUTE_COMMANDS,
    EXECUTE_COMMANDS_MOVE,
    EXECUTE_COMMANDS_CHOOSE_PARTITION,
    EXECUTE_COMMANDS_SHIFT,
    COMMANDS_END_TURN,

    COUNTERMEASURES_SMC_REVENGE,
    COUNTERMEASURES_SUCCESS_FAILURE,
    COUNTERMEASURES_SUCCESS_FAILURE_PLACE,
    COUNTERMEASURES_MOVE_SPARKS,
    COUNTERMEASURES_SCORING
}
