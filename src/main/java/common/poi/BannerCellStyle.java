package common.poi;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class BannerCellStyle extends DefaultCellStyle
{
    
    public BannerCellStyle(SXSSFWorkbook workbook)
    {
        super(workbook);
        
        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setBold(true);
        font.setFontHeightInPoints((short)10);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColorPredefined.LIGHT_GREEN.getIndex());
    }
    
}

