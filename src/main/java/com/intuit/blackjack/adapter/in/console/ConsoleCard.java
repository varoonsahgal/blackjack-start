package com.intuit.blackjack.adapter.in.console;

import com.intuit.blackjack.domain.Card;
import com.intuit.blackjack.domain.Rank;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleCard {

    // TRANSFORM Domain Object -> Console Output
    //  Card --> String
    public static String display(Card card) {
        String[] lines = new String[7];
        lines[0] = "┌─────────┐";
        lines[1] = String.format("│%s%s       │", card.rank().display(), card.rank() == Rank.TEN ? "" : " ");
        lines[2] = "│         │";
        lines[3] = String.format("│    %s    │", card.suit().symbol());
        lines[4] = "│         │";
        lines[5] = String.format("│       %s%s│", card.rank() == Rank.TEN ? "" : " ", card.rank().display());
        lines[6] = "└─────────┘";

        Ansi.Color cardColor = card.suit().isRed() ? Ansi.Color.RED : Ansi.Color.BLACK;
        return ansi()
                .fg(cardColor).toString()
                + String.join(ansi().cursorDown(1)
                                    .cursorLeft(11)
                                    .toString(), lines);
    }

    public static void displayBackOfCard() {
        System.out.print(
                ansi()
                        .cursorUp(7)
                        .cursorRight(12)
                        .a("┌─────────┐").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("│░ I N T ░│").cursorDown(1).cursorLeft(11)
                        .a("│░ T U I ░│").cursorDown(1).cursorLeft(11)
                        .a("│░    T  ░│").cursorDown(1).cursorLeft(11)
                        .a("│░░░░░░░░░│").cursorDown(1).cursorLeft(11)
                        .a("└─────────┘"));
    }
}
