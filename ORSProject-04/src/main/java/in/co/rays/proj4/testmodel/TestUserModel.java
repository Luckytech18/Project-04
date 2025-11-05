package in.co.rays.proj4.testmodel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.model.UserModel;
import in.rays.co.proj4.bean.UserBean;
import in.rays.co.proj4.exception.ApplicationException;
import in.rays.co.proj4.exception.DuplicateRecordException;

public class TestUserModel {

	public static void main(String[] args) throws ParseException {

		testAdd();
	}

	public static void testAdd() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		UserModel model = new UserModel();
		UserBean bean = new UserBean();

		try {
			bean.setFirstName("lucky");
			bean.setLastName("tomar");
			bean.setLogin("lucky@gmail.com");
			bean.setPassword("123456");

			bean.setDob(sdf.parse("2005-01-18"));
			bean.setMobileNo("7697687749");
			bean.setRoleId(1);
			bean.setGender("male");
			bean.setCreatedBy("lucky");
			bean.setModifiedBy("lucky");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
			UserBean addedBean = model.findByPk(pk);

			if (addedBean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {

			e.printStackTrace();
		} catch (DuplicateRecordException e) {

			e.printStackTrace();
		}

	}

}
