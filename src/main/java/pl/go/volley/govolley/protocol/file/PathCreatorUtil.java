package pl.go.volley.govolley.protocol.file;

import pl.go.volley.govolley.game.Game;

public class PathCreatorUtil {
    public static String createFileNameForGameProtocol(Game game){
        return String.format("%s-%s-kolejka%s.pdf", game.getTeamA().getName(), game.getTeamB().getName(), game.getRound());
    }
}
