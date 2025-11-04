package in.co.rays.proj4.testmodel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.utill.JDBCDataSource;
import in.rays.co.proj4.bean.RoleBean;
import in.rays.co.proj4.exception.ApplicationException;
import in.rays.co.proj4.exception.DatabaseException;
import in.rays.co.proj4.exception.DuplicateRecordException;

public class TestModel {

	public static void main(String[] args) {
		//testAdd();
		//testUpdate();
		//testDelete();
		//testFindByPk();
		testFindByName();
	}

	public static void testAdd() {
		
		
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();
		bean.setName("hr");
		bean.setDescription("hr");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		 try {
			model.add(bean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("data added");
	}
	
	public static void testUpdate() {
		 RoleBean bean = new RoleBean();
		 RoleModel model = new RoleModel();
		 
		 bean.setName("lucky");
		 bean.setDescription("lucky");
		 bean.setId(1);
		 try {
			model.update(bean);
			System.out.println("Updated successfully");
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
	}
	public static void testDelete() {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();
		
		bean.setId(1);
		try {
			model.delete(bean);
			System.out.println("deleted successfully");
		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void testFindByPk() {
		RoleModel model = new RoleModel();
		try {
			RoleBean bean = model.findByPk(1);
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedDatetime());
			
		} catch (ApplicationException e) {
		
			e.printStackTrace();
		}
				 
				
	}
	public static void testFindByName() {
		RoleModel model = new RoleModel();
		
		try {
			RoleBean bean = model.findByName("hr");
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getId());
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public List<RoleBean> search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		Connection conn = null;
		ArrayList<RoleBean> list = new ArrayList<RoleBean>();

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}


