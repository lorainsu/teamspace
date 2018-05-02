ckage servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import bean.convertor.ProjectBeanConvertor;
import common.constants.Constants;
import common.poi.BannerCellStyle;
import common.poi.DefaultCellStyle;
import common.poi.EnumCellStyle;
import common.poi.WrapCellStyle;
import dao.DaoServiceFactory;
import dao.base.BaseDaoService;
import dao.model.ProgressModel;
import dao.model.ProjectModel;

@WebServlet("/project/export")
public class ProjectExportServlet extends HttpServlet
{
    
    private static final long serialVersionUID = 7469715936485758211L;
    
    private static SimpleDateFormat formator = new SimpleDateFormat(Constants.DATE_FORMAT_BEAN);
    
    private SXSSFWorkbook workbook;
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException
    {
        System.out.println("ProjectExportServlet Start");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        ProjectModel model = ProjectBeanConvertor.getModel(req);
        if (null == model)
        {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Illegal param");
            resp.getWriter().flush();
            return;
        }
        
        BaseDaoService<ProjectModel> projectDao = DaoServiceFactory.getService(ProjectModel.class);
        if (null == projectDao)
        {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Initialize connection to database failed");
            resp.getWriter().flush();
            return;
        }
        workbook = createWorkbook(projectDao.query(model));
        
        String dateTime = new SimpleDateFormat(Constants.DATE_FORMAT_EXCEL).format(new Date());
        String filename = new String(("项目支持信息表-" + dateTime + ".xlsx").getBytes(), "iso-8859-1");
        resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
        resp.setContentType("application/vnd.ms-excel;charset=utf-8");
        
        OutputStream os = null;
        try
        {
            os = resp.getOutputStream();
            workbook.write(os);
            os.flush();
        }
        catch (IOException e)
        {
            if (null != os)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                }
            }
            if (null != workbook)
            {
                try
                {
                    workbook.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
        System.out.println("ProjectExportServlet finish");
    }
    
    private SXSSFWorkbook createWorkbook(List<ProjectModel> models)
    {
        workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet("项目信息");
        sheet.setDefaultColumnWidth(10);
        createBanner(sheet);
        
        for (int i = 0; i < models.size(); i++)
        {
            createData(sheet, models.get(i), i);
        }
        
        return workbook;
    }
    
    private void createBanner(SXSSFSheet sheet)
    {
        CellStyle style = new BannerCellStyle(workbook).style;
        SXSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(25);
        
        row.createCell(0).setCellStyle(style);
        row.createCell(1).setCellStyle(style);
        row.createCell(2).setCellStyle(style);
        row.createCell(3).setCellStyle(style);
        row.createCell(4).setCellStyle(style);
        row.createCell(5).setCellStyle(style);
        row.createCell(6).setCellStyle(style);
        row.createCell(7).setCellStyle(style);
        row.createCell(8).setCellStyle(style);
        row.createCell(9).setCellStyle(style);
        row.createCell(10).setCellStyle(style);
        row.createCell(11).setCellStyle(style);
        row.createCell(12).setCellStyle(style);
        row.createCell(13).setCellStyle(style);
        row.createCell(14).setCellStyle(style);
        row.createCell(15).setCellStyle(style);
        row.createCell(16).setCellStyle(style);
        row.createCell(17).setCellStyle(style);
        row.createCell(18).setCellStyle(style);
        row.createCell(19).setCellStyle(style);
        row.createCell(20).setCellStyle(style);
        row.createCell(21).setCellStyle(style);
        row.createCell(22).setCellStyle(style);
        
        row.getCell(0).setCellValue("行业");
        row.getCell(1).setCellValue("生态圈");
        row.getCell(2).setCellValue("产品");
        row.getCell(3).setCellValue("项目名称");
        row.getCell(4).setCellValue("ISV");
        row.getCell(5).setCellValue("SPP解决方案");
        row.getCell(6).setCellValue("项目背景");
        row.getCell(7).setCellValue("需求描述");
        row.getCell(8).setCellValue("项目金额");
        row.getCell(9).setCellValue("交付时间");
        row.getCell(10).setCellValue("项目进展");
        row.getCell(11).setCellValue("调用能力");
        row.getCell(12).setCellValue("一线对接进展");
        row.getCell(13).setCellValue("项目接口人");
        row.getCell(14).setCellValue("接口人电话");
        row.getCell(15).setCellValue("接口人邮箱");
        row.getCell(16).setCellValue("华为接口人");
        row.getCell(17).setCellValue("华为接口部门");
        row.getCell(18).setCellValue("eSDK接口人");
        row.getCell(19).setCellValue("开始时间");
        row.getCell(20).setCellValue("结束时间");
        row.getCell(21).setCellValue("备注");
        row.getCell(22).setCellValue("SPP解决方案状态");
        
        sheet.setColumnWidth(2, 23 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 15 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 35 * 256);
        sheet.setColumnWidth(7, 35 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 25 * 256);
        sheet.setColumnWidth(11, 25 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(15, 15 * 256);
        sheet.setColumnWidth(21, 20 * 256);
        sheet.setColumnWidth(22, 20 * 256);
    }
    
    private void createData(SXSSFSheet sheet, ProjectModel project, int index)
    {
        ProgressModel progressModel = new ProgressModel();
        progressModel.setProj_id(project.getId());
        BaseDaoService<ProgressModel> progressDao = DaoServiceFactory.getService(ProgressModel.class);
        List<ProgressModel> progressList = progressDao.query(progressModel);
        String progressStr = "";
        int count = 1;
        for (ProgressModel progress : progressList)
        {
            if (count != 1)
                progressStr += "\r\n";
            progressStr += formator.format(progress.getUpdate_time()) + ":" + progress.getContent();
            count++;
        }
        
        CellStyle defaultStyle = new DefaultCellStyle(workbook).style;
        CellStyle enumStyle = new EnumCellStyle(workbook).style;
        SXSSFRow row = sheet.createRow(index + 1);
        row.setHeightInPoints(18);
        
        row.createCell(0, CellType.STRING).setCellStyle(enumStyle);
        row.createCell(1, CellType.STRING).setCellStyle(enumStyle);
        row.createCell(2, CellType.STRING).setCellStyle(enumStyle);
        row.createCell(3, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(4, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(5, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(6, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(7, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(8, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(9, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(10, CellType.STRING).setCellStyle(new WrapCellStyle(workbook).style);
        row.createCell(11, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(12, CellType.STRING).setCellStyle(enumStyle);
        row.createCell(13, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(14, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(15, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(16, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(17, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(18, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(19, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(20, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(21, CellType.STRING).setCellStyle(defaultStyle);
        row.createCell(22, CellType.STRING).setCellStyle(defaultStyle);
        
        row.getCell(0).setCellValue(project.getIndustry().toString());
        row.getCell(1).setCellValue(project.getEcosphere().toString());
        row.getCell(2).setCellValue(ProjectBeanConvertor.convertProduct(project.getProduct()));
        row.getCell(3).setCellValue(project.getProj_name());
        row.getCell(4).setCellValue(project.getIsv());
        row.getCell(5).setCellValue(getString(project.getSpp_solution()));
        row.getCell(6).setCellValue(getString(project.getProj_background()));
        row.getCell(7).setCellValue(getString(project.getRequirement()));
        row.getCell(8).setCellValue(getString(project.getAmount()));
        row.getCell(9).setCellValue(project.getDelivery_time());
        row.getCell(10).setCellValue(progressStr);
        row.getCell(11).setCellValue(project.getAbility());
        row.getCell(12).setCellValue(project.getProj_status().toString());
        row.getCell(13).setCellValue(project.getLiaison());
        row.getCell(14).setCellValue(getString(project.getLiaison_tel()));
        row.getCell(15).setCellValue(getString(project.getLiaison_email()));
        row.getCell(16).setCellValue(getString(project.getHuawei_liaison()));
        row.getCell(17).setCellValue(getString(project.getHuawei_liaison_dept()));
        row.getCell(18).setCellValue(getString(project.getEsdk_liaison()));
        if (null != project.getStart_time())
        {
            row.getCell(19).setCellValue(formator.format(project.getStart_time()));
        }
        else
        {
            row.getCell(19).setCellValue("");
        }
        if (null != project.getEnd_time())
        {
            row.getCell(20).setCellValue(formator.format(project.getEnd_time()));
        }
        else
        {
            row.getCell(20).setCellValue("");
        }
        row.getCell(21).setCellValue(project.getRemark());
        row.getCell(22).setCellValue(getString(project.getSpp_status()));
    }
    
    private static String getString(String str)
    {
        return null == str ? "" : str;
    }
}
