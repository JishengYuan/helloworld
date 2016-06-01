package com.sinobridge.eoss.business.stock.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.sinobridge.base.core.dao.imp.DefaultBaseDao;
import com.sinobridge.eoss.business.stock.model.ExcelModel;

/**
 *	excel
 */
@Repository
public class ExcelDao extends DefaultBaseDao<ExcelModel, String>{
	
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 查询excel表字段数据对应到库存表
	 * @return
	 */
	public int excelToStock(){
		StringBuffer hql = new StringBuffer();
		hql.append("INSERT INTO business_stock (InboundCode,OrderCode,ContractCode,Pono,Model,StockNum,RecipientName) ");
		hql.append(" SELECT inboundCode,orderCode,contractCode,pono,model,sum(number) number,recipientName FROM business_excel ");
		hql.append(" group by InboundCode,OrderCode,ContractCode,Pono,Model,Number,RecipientName ");
		return this.createSQLQuery(hql.toString()).executeUpdate();
	}
	
	/**
	 * 查询excel表字段数据对应到序列号表
	 * @return
	 */
	public int excelToSerial(){
		StringBuffer hql = new StringBuffer();
		hql.append("INSERT INTO business_serial (ContractCode,InboundCode,OrderCode,Serial) ");
		hql.append(" SELECT ContractCode,InboundCode,OrderCode,Serial FROM business_excel ");
		hql.append(" group by OrderCode,InboundCode,OrderCode,Serial ");
		return this.createSQLQuery(hql.toString()).executeUpdate();
	}
	
	/**
	 * 删除excel表数据
	 */
	public int delExcel(){
		return this.createSQLQuery("delete from business_excel").executeUpdate();
	}
	
//	
//	/**
//	 * 批量导入excel数据
//	 * @param excel
//	 */
//	public void importExcel(final List<Excel> excel){
//		String sql = "insert into business_excel(ID,InboundCode,OrderCode,Pono,Model,Number,RecipientName) values (SEQ_KC_KQCLAP.NEXTVAL,?,?,?,?,?,?)" ;
//		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//			public void setValues(java.sql.PreparedStatement ps, int i)throws SQLException {
//				Excel ex = excel.get(i);
//				ps.setString(1,ex.getInboundCode());
//				ps.setString(2,ex.getOrderCode()) ;
//				ps.setString(3,ex.getPono()) ;
//				ps.setString(4,ex.getModel()) ;
//				ps.setLong(5,ex.getNumber()) ;
//				ps.setString(6,ex.getRecipientName()) ;
//			}
//			public int getBatchSize() {
//				return excel.size() ;
//			}
//		});
//	}
//	
//	/**
//	 * 批量导入序列号
//	 * @param serial
//	 */
//	public void importSerial(final List<Serial> serial){
//		String sql = "insert into business_serial(Id,ContractCode,InboundCode,OrderCode,Serial) values (SEQ_KC_KQCLAP.NEXTVAL,?,?,?,?)";
//		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//			public void setValues(java.sql.PreparedStatement ps, int i)throws SQLException {
//				Serial se = serial.get(i);
//				ps.setString(1,se.getInboundCode());
//				ps.setString(2,se.getOrderCode()) ;
//				ps.setString(3,se.getContractCode()) ;
//				ps.setString(4,se.getSerial()) ;
//			}
//			public int getBatchSize() {
//				return serial.size() ;
//			}
//		});
//	}
//	
}