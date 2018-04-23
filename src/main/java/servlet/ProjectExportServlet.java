package servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

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
    
    private HSSFWorkbook workbook;
    
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
        String filename = new String(("项目支持信息表-" + dateTime + ".xls").getBytes(), "iso-8859-1");
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
    
    private HSSFWorkbook createWorkbook(List<ProjectModel> models)
    {
        workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("项目信息");
        sheet.setDefaultColumnWidth(10);
        createBanner(sheet);
        
        for (int i = 0; i < models.size(); i++)
        {
            createData(sheet, models.get(i), i);
        }
        return workbook;
    }
    
    private void createBanner(HSSFSheet sheet)
    {
        HSSFCellStyle style = new BannerCellStyle(workbook).style;
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(25);
        
        row.createCell(0).setCellValue("行业");
        row.getCell(0).setCellStyle(style);
        
        row.createCell(1).setCellValue("生态圈");
        row.getCell(1).setCellStyle(style);
        
        row.createCell(2).setCellValue("产品");
        row.getCell(2).setCellStyle(style);
        
        row.createCell(3).setCellValue("项目名称");
        row.getCell(3).setCellStyle(style);
        sheet.setColumnWidth(3, 20 * 256);
        
        row.createCell(4).setCellValue("ISV");
        row.getCell(4).setCellStyle(style);
        sheet.setColumnWidth(4, 15 * 256);
        
        row.createCell(5).setCellValue("SPP解决方案");
        row.getCell(5).setCellStyle(style);
        sheet.setColumnWidth(5, 15 * 256);
        
        row.createCell(6).setCellValue("项目背景");
        row.getCell(6).setCellStyle(style);
        sheet.setColumnWidth(6, 30 * 256);
        
        row.createCell(7).setCellValue("需求描述");
        row.getCell(7).setCellStyle(style);
        sheet.setColumnWidth(7, 30 * 256);
        
        row.createCell(8).setCellValue("项目金额");
        row.getCell(8).setCellStyle(style);
        sheet.setColumnWidth(8, 15 * 256);
        
        row.createCell(9).setCellValue("交付时间");
        row.getCell(9).setCellStyle(style);
        sheet.setColumnWidth(9, 15 * 256);
        
        row.createCell(10).setCellValue("项目进展");
        row.getCell(10).setCellStyle(style);
        sheet.setColumnWidth(10, 25 * 256);
        
        row.createCell(11).setCellValue("调用能力");
        row.getCell(11).setCellStyle(style);
        sheet.setColumnWidth(11, 25 * 256);
        
        row.createCell(12).setCellValue("一线对接进展");
        row.getCell(12).setCellStyle(style);
        sheet.setColumnWidth(12, 15 * 256);
        
        row.createCell(13).setCellValue("项目接口人");
        row.getCell(13).setCellStyle(style);
        
        row.createCell(14).setCellValue("接口人电话");
        row.getCell(14).setCellStyle(style);
        
        row.createCell(15).setCellValue("接口人邮箱");
        row.getCell(15).setCellStyle(style);
        sheet.setColumnWidth(15, 15 * 256);
        
        row.createCell(16).setCellValue("华为接口人");
        row.getCell(16).setCellStyle(style);
        
        row.createCell(17).setCellValue("华为接口部门");
        row.getCell(17).setCellStyle(style);
        
        row.createCell(18).setCellValue("eSDK接口人");
        row.getCell(18).setCellStyle(style);
        
        row.createCell(19).setCellValue("开始时间");
        row.getCell(19).setCellStyle(style);
        
        row.createCell(20).setCellValue("结束时间");
        row.getCell(20).setCellStyle(style);
        
        row.createCell(21).setCellValue("备注");
        row.getCell(21).setCellStyle(style);
        sheet.setColumnWidth(21, 20 * 256);
        
        row.createCell(22).setCellValue("SPP解决方案状态");
        row.getCell(22).setCellStyle(style);
        sheet.setColumnWidth(22, 20 * 256);
    }
    
    private void createData(HSSFSheet sheet, ProjectModel project, int index)
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
        
        HSSFCellStyle defaultStyle = new DefaultCellStyle(workbook).style;
        HSSFCellStyle enumStyle = new EnumCellStyle(workbook).style;
        HSSFRow row = sheet.createRow(index + 1);
        row.setHeightInPoints(18);
        
        row.createCell(0, CellType.STRING).setCellValue(project.getIndustry().toString());
        row.createCell(1, CellType.STRING).setCellValue(project.getEcosphere().toString());
        row.createCell(2, CellType.STRING).setCellValue(project.getProduct().toString());
        row.createCell(3, CellType.STRING).setCellValue(project.getProj_name());
        row.createCell(4, CellType.STRING).setCellValue(project.getIsv());
        row.createCell(5, CellType.STRING).setCellValue(project.getSpp_solution());
        row.createCell(6, CellType.STRING).setCellValue(project.getProj_background());
        row.createCell(7, CellType.STRING).setCellValue(project.getRequirement());
        row.createCell(8, CellType.STRING).setCellValue(project.getAmount());
        row.createCell(9, CellType.STRING).setCellValue(project.getDelivery_time());
        row.createCell(10, CellType.STRING).setCellValue(progressStr);
        row.createCell(11, CellType.STRING).setCellValue(project.getAbility());
        row.createCell(12, CellType.STRING).setCellValue(project.getProj_status().toString());
        row.createCell(13, CellType.STRING).setCellValue(project.getLiaison());
        row.createCell(14, CellType.STRING).setCellValue(project.getLiaison_tel());
        row.createCell(15, CellType.STRING).setCellValue(project.getLiaison_email());
        row.createCell(16, CellType.STRING).setCellValue(project.getHuawei_liaison());
        row.createCell(17, CellType.STRING).setCellValue(project.getHuawei_liaison_dept());
        row.createCell(18, CellType.STRING).setCellValue(project.getEsdk_liaison());
        row.createCell(21, CellType.STRING).setCellValue(project.getRemark());
        row.createCell(22, CellType.STRING).setCellValue(project.getSpp_status());
        
        row.getCell(0).setCellStyle(enumStyle);
        row.getCell(1).setCellStyle(enumStyle);
        row.getCell(2).setCellStyle(enumStyle);
        row.getCell(3).setCellStyle(defaultStyle);
        row.getCell(4).setCellStyle(defaultStyle);
        row.getCell(5).setCellStyle(defaultStyle);
        row.getCell(6).setCellStyle(defaultStyle);
        row.getCell(7).setCellStyle(defaultStyle);
        row.getCell(8).setCellStyle(defaultStyle);
        row.getCell(9).setCellStyle(defaultStyle);
        row.getCell(10).setCellStyle(new WrapCellStyle(workbook).style);
        row.getCell(11).setCellStyle(defaultStyle);
        row.getCell(12).setCellStyle(enumStyle);
        row.getCell(13).setCellStyle(defaultStyle);
        row.getCell(14).setCellStyle(defaultStyle);
        row.getCell(15).setCellStyle(defaultStyle);
        row.getCell(16).setCellStyle(defaultStyle);
        row.getCell(17).setCellStyle(defaultStyle);
        row.getCell(18).setCellStyle(defaultStyle);
        row.getCell(21).setCellStyle(defaultStyle);
        row.getCell(22).setCellStyle(defaultStyle);
        
        if (null != project.getStart_time())
        {
            row.createCell(19, CellType.STRING).setCellValue(formator.format(project.getStart_time()));
            row.getCell(19).setCellStyle(defaultStyle);
            
        }
        if (null != project.getEnd_time())
        {
            row.createCell(20, CellType.STRING).setCellValue(formator.format(project.getEnd_time()));
            row.getCell(20).setCellStyle(defaultStyle);
        }
    }
}

