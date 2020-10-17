package viewModel;

import dtoModel.ZoneDto;
import managers.SuperDuperManager;

import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

public class ZoneViewModel {

    SuperDuperManager m_SuperDuperManager = SuperDuperManager.getInstance();

    public void loadZoneFromXml(String i_PathToFile,String i_OwnerName) throws Exception {
        SuperDuperManager.getInstance().loadSuperDuperDataFromXml(i_PathToFile,i_OwnerName);
    }

   public void loadZoneFromXml(InputStream i_FileInputStream,String i_OwnerName) throws Exception {
       SuperDuperManager.getInstance().loadSuperDuperDataFromXml(i_FileInputStream,i_OwnerName);
   }


    public Collection<ZoneDto> getAllZones()
    {
        return m_SuperDuperManager.getAllZones().stream().map(ZoneDto::new).collect(Collectors.toList());
    }

    public ZoneDto getZone(String i_ZoneName)
    {
        return new ZoneDto(m_SuperDuperManager.getZone(i_ZoneName));
    }
}
