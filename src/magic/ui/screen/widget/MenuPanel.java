package magic.ui.screen.widget;

import magic.ui.widget.FontsAndBorders;
import magic.ui.widget.TexturedPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import magic.ui.screen.interfaces.IThemeStyle;
import magic.ui.theme.Theme;
import magic.ui.MagicStyle;

@SuppressWarnings("serial")
public class MenuPanel extends TexturedPanel implements IThemeStyle {

    private final String title;
    private final List<MenuButton> menuItems = new ArrayList<>();

    public MenuPanel() {
        this(null);
    }

    public MenuPanel(final String title0) {

        this.title = title0;

        setPreferredSize(new Dimension(300, 380));
        setMaximumSize(new Dimension(300, 380));

        refreshStyle();

        setMenuPanelLayout();

    }

    public void addMenuItem(final MenuButton button) {
        menuItems.add(button);
    }
    public void addMenuItem(final String caption, final AbstractAction action, final String tooltip) {
        addMenuItem(new MenuButton(caption, action, tooltip));
    }
    public void addMenuItem(final String caption, final AbstractAction action) {
        addMenuItem(caption, action, null);
    }

    public void addBlankItem() {
        final MenuButton emptyButton = new MenuButton("", null);
        emptyButton.setMinimumSize(new Dimension(0, 10));
        menuItems.add(emptyButton);
    }

    public void refreshLayout() {
        setMenuPanelLayout();
    }

    private void setMenuPanelLayout() {
        removeAll();
        setLayout(new MigLayout("insets 6, gap 0, flowy"));
        if (this.title != null) {
            add(getMenuTitlePanel(), "w 100%, pad 0 0 10 0, gapbottom 15");
        }
        for (MenuButton menuItem : menuItems) {
            add(menuItem, "w 100%");
        }
    }

    private CaptionPanel getMenuTitlePanel() {
        final CaptionPanel p = new CaptionPanel(this.title);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        return p;
    }

    @Override
    public void refreshStyle() {
        final Color refBG = MagicStyle.getTheme().getColor(Theme.COLOR_TITLE_BACKGROUND);
        final Color thisBG = MagicStyle.getTranslucentColor(refBG, 200);
        setBorder(FontsAndBorders.BLACK_BORDER);
        setBackground(thisBG);
    }

}
