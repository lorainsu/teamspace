package common.poi;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class EnumCellStyle extends DefaultCellStyle
{
    
    public EnumCellStyle(SXSSFWorkbook workbook)
    {
        super(workbook);
        
        style.setAlignment(HorizontalAlignment.CENTER);
    }
    
}

