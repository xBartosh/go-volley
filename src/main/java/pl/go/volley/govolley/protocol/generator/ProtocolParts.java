package pl.go.volley.govolley.protocol.generator;

import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProtocolParts {
    private Image image;
    private Paragraph title;
    private Paragraph field;
    private Paragraph round;
    private PdfPTable gameTable;
    private Paragraph scoreAndRefSign;
    private PdfPTable gameResultsTable;
    private PdfPTable mvpTable;
    private Paragraph captainsSigns;
}
