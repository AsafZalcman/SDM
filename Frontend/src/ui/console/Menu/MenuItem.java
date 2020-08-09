package ui.console.Menu;

public class MenuItem implements IMenuComponent{

    private final String m_Title;
    private Runnable m_Command;

    public MenuItem(String i_Title, Runnable i_Command) {
        this.m_Title = i_Title;
        m_Command = i_Command;
    }

    @Override
    public void selected()
    {
        m_Command.run();
    }

    @Override
    public String getTitle() {
        return m_Title;
    }
}
