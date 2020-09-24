package ui.javafx.tasks;

import javafx.concurrent.Task;
import viewModel.FileLoaderViewModel;

import java.util.Random;

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
            Random rand = new Random();
            int lowBorder = 5;
            int randInt = rand.nextInt(NUMBER_OF_PROGRESS_PARTS -lowBorder) + lowBorder;
            while (i < randInt) {
                updateProgress(i, NUMBER_OF_PROGRESS_PARTS);
                i++;
                try {
                    Thread.sleep(TIME_TO_SLEEP);
                } catch (InterruptedException ignored) {
                }
            }
            updateMessage("Error: " + e.getMessage());
            updateValue(false);
            return false;
        }
    }
}
