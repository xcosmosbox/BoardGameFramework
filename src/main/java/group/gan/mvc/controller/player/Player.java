package group.gan.mvc.controller.player;

import group.gan.mvc.controller.command.Command;
import group.gan.mvc.model.player.PlayerModel;
import group.gan.mvc.model.player.PlayerStateEnum;
import group.gan.mvc.model.token.Token;

/**
 * Player is controlled by Turn. When the Turn informs the Player that the player needs to play the game, the Player can perform the current round of game
 * operations. Player needs to observe the Board and feed back the observed Board
 * situation to PlayerState. Player can create Command.
 */

public interface Player {

    /**
     * play the game
     * @return
     */
    Command play();

    /**
     * set the state manager
     */
    void setStateManager(PlayerModel playerModel);

    /**
     * get the uid of the player
     * @return
     */
    Integer getUid();

    /**
     * get one token
     * @return
     */
    Token getOneToken();

    /**
     * get the player name
     * @return
     */
    String getPlayerName();

    /**
     * The player returns a number according to the prompt
     * @return
     */
    Integer requestOneIntegerInput();

    /**
     * The player returns a list numbers according to the prompt
     * @return
     */
    Integer[] requestIntegersInput();











}
