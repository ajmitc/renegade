package renegade.view;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.logging.Logger;

public class LogPanel extends JPanel {
    private static Logger logger = Logger.getLogger(LogPanel.class.getName());
    public static final String REGULAR = "regular";
    public static final String ITALIC = "italic";
    public static final String BOLD = "bold";
    public static final String BOLD_ITALIC = "bold italic";

    private JTextPane output;
    private StyledDocument document;

    public LogPanel(){
        super(new BorderLayout());

        output = new JTextPane();
        output.setDoubleBuffered(true);
        output.setEditable(false);
        output.setAutoscrolls(true);
        output.setPreferredSize(new Dimension(1000, 200));
        document = output.getStyledDocument();

        add(new JScrollPane(output), BorderLayout.CENTER);

        setupStyles();
    }

    private void setupStyles(){
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = document.addStyle(REGULAR, def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = document.addStyle(ITALIC, regular);
        StyleConstants.setItalic(s, true);

        s = document.addStyle(BOLD, regular);
        StyleConstants.setBold(s, true);

        s = document.addStyle(BOLD_ITALIC, regular);
        StyleConstants.setBold(s, true);
        StyleConstants.setItalic(s, true);
    }

    public void writeln(String text){
        write(text + "\n", REGULAR);
    }

    public void writeln(String text, String style){
        write(text + "\n", style);
    }

    public void write(String text, String style){
        try {
            document.insertString(document.getLength(), text, document.getStyle(style));
        }
        catch (Exception e){
            logger.severe("" + e);
        }
    }
}
