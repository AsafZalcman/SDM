package ui.console;

public class SubMenu extends abstractMenu implements IMenuComponent {

    private final String m_Title;
    private final String m_MenuTitle;

    public SubMenu(String i_Title, String i_MenuTitle, IMenuComponent... menuComponent) {
        super(menuComponent);
        this.m_Title = i_Title;
        m_MenuTitle = i_MenuTitle;
    }

    @Override
    public void selected() {
        super.run();
    }

    @Override
    public String getTitle() {
        return m_Title;
    }

    public String getMenuTitle() {
        return m_MenuTitle;
    }
}
