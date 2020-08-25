package ui.console.utils;

import dtoModel.ItemDto;

public class ItemDtoUtils {

    public static String getBasicItemString(ItemDto i_ItemDto) {
        return UniquelyUtil.getIdString(i_ItemDto.getId()) + "\n" +
                UniquelyUtil.getNameString(i_ItemDto.getItemName()) + "\n" +
                "- Item Purchase Form: " + i_ItemDto.getPurchaseForm();
    }

    public static String getPriceString(ItemDto i_ItemDto)
    {
        return i_ItemDto.getPrice() !=null? "- Price: "+  FormatUtils.DecimalFormat.format(i_ItemDto.getPrice()) :"- Price: Not for sell";
    }

    public static String getAmountOfSellsString(double i_AmountOfSells, String i_Title)
    {
        return i_Title + FormatUtils.DecimalFormat.format(i_AmountOfSells);
    }

}
