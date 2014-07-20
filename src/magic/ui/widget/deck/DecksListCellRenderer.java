package magic.ui.widget.deck;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import magic.model.MagicDeck;

@SuppressWarnings("serial")
class DecksListCellRenderer extends DefaultListCellRenderer {

    private static Font invalidDeckFont = null;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        final MagicDeck deck = (MagicDeck) value;
        final Component c = super.getListCellRendererComponent(list, deck.getName(), index, isSelected, cellHasFocus);
        if (deck.isValid() == false) {
            if (invalidDeckFont == null) {
                Map attributes = c.getFont().getAttributes();
                attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                invalidDeckFont = new Font(attributes);
            }
            c.setFont(invalidDeckFont);
            c.setForeground(isSelected ? list.getSelectionForeground() : Color.RED.darker());
        }
        return c;
    }

}