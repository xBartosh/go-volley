package pl.go.volley.govolley.protocol.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import pl.go.volley.govolley.game.Game;
import pl.go.volley.govolley.team.Team;

import java.io.IOException;

import static pl.go.volley.govolley.protocol.file.PathsToProtocols.PATH_TO_PROTOCOLS_FOLDER;

public class ProtocolPartsFactory {

    public static ProtocolParts createProtocolParts(Document document, Game game, Font font) throws DocumentException, IOException {
        return ProtocolParts.builder()
                .image(createImage(document))
                .title(createTitle(font))
                .field(createField(font))
                .round(createRound(font, game))
                .gameTable(createGameTable(font, game))
                .scoreAndRefSign(createScoreAndRefSign(font))
                .gameResultsTable(createGameResultsTable())
                .mvpTable(createMVPTable(font))
                .captainsSigns(createCaptainsSigns(font))
                .build();
    }

    private static Image createImage(Document document) throws BadElementException, IOException {
        Image image = Image.getInstance(PATH_TO_PROTOCOLS_FOLDER + "/go-volley/go-volley.png");
        image.setAlignment(Image.ALIGN_CENTER);
        int indentation = 0;
        int scaleSize = 20;
        float scale = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - indentation) / image.getWidth()) * scaleSize;
        image.scalePercent(scale);

        return image;
    }

    private static Paragraph createTitle(Font font) throws DocumentException, IOException {
        String title = "PROTOKÓŁ MECZOWY";
        Paragraph paragraph = new Paragraph(title, font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        return paragraph;
    }

    private static Paragraph createField(Font font) {
        Paragraph field = new Paragraph("BOISKO:", font);
        field.setAlignment(Paragraph.ALIGN_LEFT);
        field.setSpacingBefore(15);
        return field;
    }

    private static Paragraph createRound(Font font, Game game) {
        Paragraph round = new Paragraph("KOLEJKA: " + game.getRound(), font);
        round.setAlignment(Paragraph.ALIGN_LEFT);
        round.setSpacingBefore(10);
        return round;
    }

    private static PdfPTable createGameTable(Font font, Game game) throws DocumentException, IOException {
        PdfPTable gameTable = new PdfPTable(6);
        gameTable.setWidths(new float[]{0.4f, 3f, 0.7f, 0.4f, 3f, 0.7f});
        gameTable.setSpacingBefore(10);
        gameTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        gameTable.setWidthPercentage(100);
        BaseFont baseFont = BaseFont.createFont(PATH_TO_PROTOCOLS_FOLDER + "/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font teamNameFont = new Font(baseFont, Font.BOLDITALIC);
        teamNameFont.setSize(16);

        Team teamA = game.getTeamA();
        Team teamB = game.getTeamB();

        PdfPCell teamACell = createTeamHeaderName(teamA, "A", font, teamNameFont);

        PdfPCell mvp = new PdfPCell(new Paragraph("MVP", font));
        mvp.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);

        PdfPCell teamBCell = createTeamHeaderName(teamB, "B", font, teamNameFont);

        gameTable.addCell(teamACell);
        gameTable.addCell(mvp);
        gameTable.addCell(teamBCell);
        gameTable.addCell(mvp);

        int teamANumOfPlayers = teamA.getPlayers().size();
        int teamBNumOfPlayres = teamB.getPlayers().size();
        int maxSize = Math.max(teamANumOfPlayers, teamBNumOfPlayres);

        Font playerFont = new Font(baseFont, Font.NORMAL);
        playerFont.setSize(13);

        PdfPCell mvpCell = new PdfPCell(new Phrase("   "));

        for (int i = 0; i < maxSize; i++) {
            PdfPCell numberCell = new PdfPCell(new Phrase(Integer.toString(i + 1), font));
            numberCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            numberCell.setPadding(5);
            gameTable.addCell(numberCell);

            PdfPCell emptyPlayerNameCell = new PdfPCell(new Phrase("    ", font));
            if (teamA.getPlayers().size() > i) {
                addPlayerCell(gameTable, teamA, playerFont, i);
            } else {
                gameTable.addCell(emptyPlayerNameCell);
            }

            gameTable.addCell(mvpCell);
            gameTable.addCell(numberCell);

            if (teamB.getPlayers().size() > i) {
                addPlayerCell(gameTable, teamB, playerFont, i);
            } else {
                gameTable.addCell(emptyPlayerNameCell);
            }

            gameTable.addCell(mvpCell);
        }


        return gameTable;
    }

    private static void addPlayerCell(PdfPTable gameTable, Team team, Font playerFont, int i) {
        PdfPCell playerCell = new PdfPCell(new Phrase(team.getPlayers().get(i).getFullName(), playerFont));
        playerCell.setPadding(5);
        gameTable.addCell(playerCell);
    }

    private static Paragraph createScoreAndRefSign(Font font) {
        Paragraph refereeSign = new Paragraph("WYNIK:                                                                                                           Podpis sędziego:", font);
        refereeSign.setSpacingBefore(20);
        return refereeSign;
    }

    private static PdfPTable createGameResultsTable() throws DocumentException, IOException {
        BaseFont baseFont = BaseFont.createFont(PATH_TO_PROTOCOLS_FOLDER + "/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(baseFont, Font.BOLD);
        font.setSize(9);

        PdfPTable gameResultsTable = new PdfPTable(2);
        gameResultsTable.setSpacingBefore(5);
        gameResultsTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
        gameResultsTable.setWidthPercentage(40);

        PdfPCell firstSet = new PdfPCell(new Phrase("Pierwszy set", font));
        firstSet.setPadding(5);
        PdfPCell secondSet = new PdfPCell(new Phrase("Drugi set", font));
        secondSet.setPadding(5);
        PdfPCell thirdSet = new PdfPCell(new Phrase("Trzeci set", font));
        thirdSet.setPadding(5);
        PdfPCell fourthSet = new PdfPCell(new Phrase("Czwarty set", font));
        fourthSet.setPadding(5);
        PdfPCell fifthSet = new PdfPCell(new Phrase("Piąty set", font));
        fifthSet.setPadding(5);
        PdfPCell score = new PdfPCell(new Phrase("WYNIK MECZU", font));
        score.setPadding(5);

        gameResultsTable.addCell(firstSet);
        gameResultsTable.addCell(new PdfPCell());
        gameResultsTable.addCell(secondSet);
        gameResultsTable.addCell(new PdfPCell());
        gameResultsTable.addCell(thirdSet);
        gameResultsTable.addCell(new PdfPCell());
        gameResultsTable.addCell(fourthSet);
        gameResultsTable.addCell(new PdfPCell());
        gameResultsTable.addCell(fifthSet);
        gameResultsTable.addCell(new PdfPCell());
        gameResultsTable.addCell(score);
        gameResultsTable.addCell(new PdfPCell());

        return gameResultsTable;
    }

    private static PdfPTable createMVPTable(Font font) throws DocumentException {
        PdfPTable mvpTable = new PdfPTable(2);
        mvpTable.setWidths(new float[]{0.75f, 3.5f});
        mvpTable.setWidthPercentage(60);
        mvpTable.setSpacingBefore(20);
        mvpTable.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

        PdfPCell mvpCell = new PdfPCell(new Phrase("MVP", font));
        mvpCell.setPadding(2);
        mvpCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        mvpTable.addCell(mvpCell);
        mvpTable.addCell(new PdfPCell());

        return mvpTable;
    }

    private static Paragraph createCaptainsSigns(Font font) {
        font.setSize(12);
        Paragraph captainsSigns = new Paragraph("Podpis kapitana drużyny A:                                                              Podpis Kapitana drużyny B:", font);
        captainsSigns.setSpacingBefore(20);

        return captainsSigns;
    }

    private static PdfPCell createTeamHeaderName(Team team, String teamAOrB, Font font, Font teamNameFont) {
        PdfPCell teamCell = new PdfPCell();
        teamCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        teamCell.setPadding(3);
        teamCell.setColspan(2);

        Paragraph teamHeader = new Paragraph("Drużyna " + teamAOrB + ":", font);
        teamHeader.setAlignment(Element.ALIGN_CENTER);
        teamCell.addElement(teamHeader);

        Paragraph teamName = new Paragraph(team.getName().toUpperCase(), teamNameFont);
        teamName.setAlignment(Paragraph.ALIGN_CENTER);
        teamName.setPaddingTop(8);
        teamCell.addElement(teamName);

        return teamCell;
    }

}

