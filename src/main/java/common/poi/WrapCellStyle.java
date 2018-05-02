package common.poi;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class WrapCellStyle extends DefaultCellStyle
{
    
    public WrapCellStyle(SXSSFWorkbook workbook)
    {
        super(workbook);
        style.setWrapText(true);
    }
    
}

