package com.vexsoftware.votifier.velocity.cmd;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.command.SimpleCommand;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import com.vexsoftware.votifier.util.ArgsToVote;
import com.vexsoftware.votifier.velocity.VotifierPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class TestVoteCmd implements RawCommand {

    private final VotifierPlugin plugin;

    public TestVoteCmd(VotifierPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(RawCommand.Invocation invocation) {
        Vote v;
        try {
            v = ArgsToVote.parse(invocation.arguments().split(" "));
        } catch (IllegalArgumentException e) {
            invocation.source().sendMessage(Component.text("Error while parsing arguments to create test vote: " + e.getMessage(), NamedTextColor.DARK_RED));
            invocation.source().sendMessage(Component.text("Usage hint: /testvote [username] [serviceName=?] [username=?] [address=?] [localTimestamp=?] [timestamp=?]", NamedTextColor.GRAY));
            return;
        }

        plugin.onVoteReceived(v, VotifierSession.ProtocolVersion.TEST, "localhost.test");
        invocation.source().sendMessage(Component.text("Test vote executed: " + v.toString(), NamedTextColor.GREEN));
    }

    public boolean hasPermission(SimpleCommand.Invocation invocation) {
        return invocation.source().hasPermission("nuvotifier.testvote");
    }
}
