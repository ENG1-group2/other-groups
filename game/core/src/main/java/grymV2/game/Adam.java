package grymV2.game;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Adam extends Game {
    private Eve gameLoader;
    // private Cain client;

    public Adam() {
        // Load settings of some sort, e.g. predefined viewport values
        // log4j.DO_LOG()

        gameLoader = new Eve();
        // gameLoader = new Eve(Settings settings);
        // this.client = gameLoader.client
        //      Adam.client/Adam.serverThread.server
        //  vs  Adam.Eve.{client,server...}
    }

    /**
     *
     * Springsteen - Darkness on the Edge of Town, track 02
     *
     */
    public void create() {
        // try pass everything to Cain; Adam's libGDX is just a puppet wrapper
        // this.client.create();
    }

    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        // this.client.dispose()
        super.dispose();
    }

    /**
     * public void renderEvent(RenderEvent event)
     *     case $event in
     *         PAUSE)
     *             this.serverThread.server.gameEvent($event)
     *             this.client.setScreen(PAUSE)
     *             ...
     *             ;;
     *         RESUME)
     *             this.serverThread.server.gameEvent($event)
     *             this.client.setScreen(GAME)
     *             ...
     *             ;;
     *         OHSHIT)
     *             this.serverThread.server.gameEvent(CRASH)
     *             self.dispose()
     *             ;;
     *         ...
     *         etc
     *     esac
     *
     *
     */
}
