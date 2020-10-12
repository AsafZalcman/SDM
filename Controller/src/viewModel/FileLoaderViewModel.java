package viewModel;

import managers.SuperDuperManager;

public class FileLoaderViewModel {

    public void loadSuperDuperDataFromXml(String i_PathToFile,int i_OwnerId) throws Exception {
        SuperDuperManager.getInstance().loadSuperDuperDataFromXml(i_PathToFile,i_OwnerId);
    }
}
