package ui.javafx.tasks;

import javafx.concurrent.Task;
import viewModel.FileLoaderViewModel;

public class LoadXmlTask extends Task<Boolean> {

    private final String m_FilePath;
    private final int TIME_TO_SLEEP = 200;
    private final int NUMBER_OF_PROGRESS_PARTS = 11;
    FileLoaderViewModel m_FileLoaderViewModel;

    public LoadXmlTask(String i_filePath) {
        m_FilePath = i_filePath;
        m_FileLoaderViewModel = new FileLoaderViewModel();
    }

    @Override
    protected Boolean call() {

        int i = 1;
        try {
            updateMessage("Verify file content...");
            updateProgress(0, NUMBER_OF_PROGRESS_PARTS);
            m_FileLoaderViewModel.loadSuperDuperDataFromXml(m_FilePath);
            while (i < NUMBER_OF_PROGRESS_PARTS + 1) {
                updateProgress(i, NUMBER_OF_PROGRESS_PARTS);
                i++;
                Thread.sleep(TIME_TO_SLEEP);
            }
            updateMessage("Xml file was loaded successfully");
            updateValue(true);
            return true;
        } catch (Exception e) {
            updateMessage("Error: " + e.getMessage());
            updateValue(false);
            return false;
        }
    }
}
