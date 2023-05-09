package group.gan.mvc.controller.game.impl;


import group.gan.events.Event;
import group.gan.events.EventListener;
import group.gan.events.ListenerType;
import group.gan.mvc.controller.command.Command;
import group.gan.mvc.controller.command.CommandType;
import group.gan.mvc.controller.game.Game;
import group.gan.mvc.model.game.GameModel;
import group.gan.mvc.view.View;
import group.gan.mvc.view.impl.BoardView;
import group.gan.mvc.view.impl.PlayerInfoView;
import group.gan.utils.Display;


/**
 * @author Tianyi Liu
 * Class Name: GameImpl
 * @version 1.0
 * created by: 25/4/2023
 */
public class GameFacade implements Game, EventListener {
    /**
     * a private variable to store the game model
     */
    private GameModel gameModel;

    /**
     * a boolean value indicating whether the game needs to exit
     */
    private Boolean quit = false;

    private Boolean onEventMill=false;

    /**
     * Starting game
     */
    @Override
    public void run() {
        Display display = new Display();
        while (!gameModel.isGameOver() && !quit) {
            // Render game board
            View boardView = new BoardView(gameModel.getBoard().getAllPositionsFromBoard());
            boardView.draw(display);
            //current player to act and token type
            View userInfoView = new PlayerInfoView(gameModel.getTurn().getPollableInstance());
            userInfoView.draw(display);

            //main game loop
            // Request a command from the player
            Command command = gameModel.getTurn().runTurn();

            // Issue & execute the command
            if (command.getCommandType() == CommandType.MOVE) {
                command.init(this, gameModel.getBoard());
            } else if (command.getCommandType() == CommandType.QUIT) {
                command.init(this);
            }
            Boolean result = command.execute();

            // check execution result, if result == false, retrieve coordinates
            while (!result){
                Command refillCommand = gameModel.getTurn().refillCommand(command);
                // Issue & execute the command
                if (refillCommand.getCommandType() == CommandType.MOVE) {
                    refillCommand.init(this, gameModel.getBoard());
                } else if (refillCommand.getCommandType() == CommandType.QUIT) {
                    refillCommand.init(this);
                }
                result = refillCommand.execute();
            }


            // =========  FUTURE  =========
            // In the following iteration, we will implement the operation of checking whether a mill has been formed
            // and the player has taken a token. We will not implement this function in sprint2 for the time being
            // =========  FUTURE  =========

            //check if there's a mill event.
            if(onEventMill){
                //tell turn to ask for a command from player to remove a token on the board.
                //check if the mill command is valid, if not ask again
                //toggle onEventMill to false once command is executed.
            }

            // Check if the game exits
            if (!quit) {
                //start next turn
                gameModel.getTurn().switchPollableObject();
            }

        }


    }

    /**
     * Quit
     */
    @Override
    public void quit() {
        this.quit = true;
        Display display = new Display();
        display.displayMessage("  Game Quit!" + display.getNewLine());
    }

    /**
     * Quit after winning
     */
    @Override
    public void quitForWin() {
        this.quit = true;
        Display display = new Display();
        display.displayMessage("  You Won!" + display.getNewLine());
    }

    /**
     * Build the game model
     *
     * @param gameModel game model
     */
    @Override
    public void build(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void onEvent(Event event) {
        //check if event is mill,
        onEventMill = true;
    }

    @Override
    public ListenerType getListenerType() {
        return null;
    }
}
