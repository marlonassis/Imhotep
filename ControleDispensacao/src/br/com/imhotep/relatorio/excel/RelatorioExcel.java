package br.com.imhotep.relatorio.excel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

//Melhoria #11
public abstract  class RelatorioExcel {
	private String nomeRelatorio;
	private String setor;
	private String periodo;
	
	
	private Map<String, CellStyle> styles;
	
	protected HSSFSheet sheet;
	protected char ultColuna;
	protected int numColunas;
	protected HSSFWorkbook workbook;
	
	protected int nCell;
	
	public RelatorioExcel( String nomeRelatorio,String setor,String periodo, int qtdColunas ){
		this.workbook = new HSSFWorkbook();
		sheet = workbook.createSheet(nomeRelatorio);
		styles = createStyles(workbook);
		this.nCell = 0;
		this.ultColuna = (char) (qtdColunas + 64);
		this.numColunas = qtdColunas;
		
		this.nomeRelatorio = nomeRelatorio;
		this.setor = setor;
		this.periodo = periodo;
	}
	
	public abstract void gerarPlanilha();
	
	protected void criarColunasComNome( String[] header, int[] tamanho ){
			
		if(header!=null){
			Row headerRow = sheet.createRow(nCell++);
			for (int i = 0; i < header.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(header[i]);

				CellStyle cs = styles.get("header");
				cs.setWrapText(true);
				cell.setCellStyle(cs);
			}
		}
		
		for (int i = 0; i < tamanho.length; i++) {
			sheet.setColumnWidth(i, 256*tamanho[i]);
        }
	}
	
	protected void criarColunasComNome( int[] tamanho ){
		for (int i = 0; i < tamanho.length; i++) {
			sheet.setColumnWidth(i, 256*tamanho[i]);
        }
	}
	
	protected void criarColunasComNome( String[] header ){
		if(header!=null){
			Row headerRow = sheet.createRow(nCell++);
			for (int i = 0; i < header.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(header[i]);
				cell.setCellStyle(styles.get("header"));
			}
		}
	}
	
	protected final void addCelulas(String ...cell){
		HSSFRow row2 = sheet.createRow(nCell);
		int i = 0;
		for (String string : cell) {			
			row2.createCell(i++).setCellValue(string);
		}
		
	}
	
	protected final void addCelulasLn(StringBuilder stilo, String ...cell){
		HSSFRow row2 = sheet.createRow(nCell);
		int i = 0;
		for (String conteudo : cell) {			
			Cell celula = row2.createCell(i);
			celula.setCellValue(conteudo);
			celula.setCellStyle(styles.get(stilo.toString()));
			
			i++;
		}
		nCell++;
	}
	
	protected final void addCelulasLn(String ...cell){
		HSSFRow row2 = sheet.createRow(nCell);
		int i = 0;
		for (String string : cell) {			
			row2.createCell(i++).setCellValue(string);
		}
		nCell++;
	}
	
	protected final int addCelulas(int posCelula, String ...cell){
		HSSFRow row2 = sheet.createRow(nCell);
		for (String string : cell) {			
			row2.createCell(posCelula++).setCellValue(string);
		}
		return posCelula;
	}
	
	protected final void linhaEmBranco(){
		HSSFRow row = sheet.createRow(nCell);		 
		row.createCell(0).setCellValue("");
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+ultColuna+"$"+nCell));
		nCell++;
	}
	
	protected final void criarLinha( String conteudo, String stilo, short merge, int heightRow   ){
		Row titleRowUfs = sheet.createRow(nCell++);
		titleRowUfs.setHeightInPoints(heightRow);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		titleCellUfs.setCellStyle(styles.get(stilo));
		
		char coluna = (char) (merge + 64);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+coluna+"$"+nCell));
	}
	
	protected final void criarLinha( String conteudo, String stilo, short merge ){
		Row titleRowUfs = sheet.createRow(nCell++);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		titleCellUfs.setCellStyle(styles.get(stilo));
		
		char coluna = (char) (merge + 64);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+coluna+"$"+nCell));
	}
	
	protected final void criarLinha( String conteudo, String stilo, int heightRow  ){
		Row titleRowUfs = sheet.createRow(nCell++);
		titleRowUfs.setHeightInPoints(heightRow);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		titleCellUfs.setCellStyle(styles.get(stilo));
	}
	
	protected final void criarLinha( String conteudo, String stilo ){
		Row titleRowUfs = sheet.createRow(nCell++);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		titleCellUfs.setCellStyle(styles.get(stilo));
	}
	
	protected final void criarLinha( String conteudo, short merge, int heightRow  ){
		Row titleRowUfs = sheet.createRow(nCell++);
		titleRowUfs.setHeightInPoints(heightRow);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		
		char coluna = (char) (merge + 64);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+coluna+"$"+nCell));
	}
	
	
	
	protected final void criarLinha( String conteudo, short merge  ){
		Row titleRowUfs = sheet.createRow(nCell++);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue(conteudo);
		
		char coluna = (char) (merge + 64);
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+coluna+"$"+nCell));
	}
	
	protected final void criarCabecalho(){
		//titulo
		Row titleRowUfs = sheet.createRow(nCell++);
		titleRowUfs.setHeightInPoints(24);
		Cell titleCellUfs = titleRowUfs.createCell(0);
		titleCellUfs.setCellValue("Universidade Federal de Sergipe");
		titleCellUfs.setCellStyle(styles.get("UFS"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+ultColuna+"$"+nCell));
		        		        
        Row titleRowHu = sheet.createRow(nCell++);
        titleRowHu.setHeightInPoints(22);
		Cell titleCellHu = titleRowHu.createCell(0);
		titleCellHu.setCellValue("Hospital Universitário");
		titleCellHu.setCellStyle(styles.get("HU"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+ultColuna+"$"+nCell));
		
		Row titleRowRel = sheet.createRow(nCell++);
		titleRowRel.setHeightInPoints(20);
		Cell titleCellRel = titleRowRel.createCell(0);
		titleCellRel.setCellValue(nomeRelatorio);
		titleCellRel.setCellStyle(styles.get("TITULO"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$"+ultColuna+"$"+nCell));
        
        Row titleRowSetor = sheet.createRow(nCell++);
		Cell titleCellSetor = titleRowSetor.createCell(0);
		titleCellSetor.setCellValue("Setor: "+setor);
		titleCellSetor.setCellStyle(styles.get("cell_b"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$B$"+nCell));
		
		if(periodo!=null){
			Row titleRowPeriodo = sheet.createRow(nCell++);
			Cell titleCellPeriodo = titleRowPeriodo.createCell(0);
			titleCellPeriodo.setCellValue("Período: "+periodo);
			titleCellPeriodo.setCellStyle(styles.get("cell_b"));
			sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$B$"+nCell));
		}
		
		Row titleRowImp = sheet.createRow(nCell++);
		Cell titleCellImp = titleRowImp.createCell(0);
		titleCellImp.setCellValue("Data da Impressão: "+ new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		titleCellImp.setCellStyle(styles.get("cell_b"));
		sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+nCell+":$B$"+nCell));
	}
	
	public final HSSFWorkbook getWorkbook() {
		return workbook;
	}
	
	/**
     * create a library of cell styles
     */
    private final static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        DataFormat df = wb.createDataFormat();

        CellStyle style;
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);

        Font font1 = wb.createFont();
        font1.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        //style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);

        Font font2 = wb.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);

        Font font3 = wb.createFont();
       // font3.setFontHeightInPoints((short)12);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(font3);
        style.setWrapText(true);
        styles.put("cell_h", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setWrapText(true);
        styles.put("cell_normal", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        styles.put("cell_normal_centered", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);

        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setIndention((short)1);
        style.setWrapText(true);
        styles.put("cell_indented", style);

        style = createBorderedStyle(wb);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styles.put("cell_blue", style);
        
        Font ufsFont = wb.createFont();
        ufsFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        ufsFont.setFontHeightInPoints((short)20);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(ufsFont);
        styles.put("UFS", style);
        
        Font huFont = wb.createFont();
        huFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        huFont.setFontHeightInPoints((short)18);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setFont(huFont);
        styles.put("HU", style);
        
        Font nomeRelFont = wb.createFont();
        nomeRelFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        nomeRelFont.setFontHeightInPoints((short)16);
        style = createBorderedStyle(wb);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(nomeRelFont);
        styles.put("TITULO", style);

        return styles;
    }
    
    private final static CellStyle createBorderedStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }
}
