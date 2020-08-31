package viewModel;

import managers.SuperDuperManager;

public class FileLoaderViewModel {

    public void loadSuperDuperDataFromXml(String i_PathToFile) throws Exception {
        SuperDuperManager.getInstance().loadSuperDuperDataFromXml(i_PathToFile);
    }
}
