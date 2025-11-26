package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BankBean;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.BankModel;
import in.co.rays.proj4.utill.DataUtility;
import in.co.rays.proj4.utill.DataValidator;
import in.co.rays.proj4.utill.PropertyReader;
import in.co.rays.proj4.utill.ServletUtility;

@WebServlet(name = "BankCtl", urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("accountNo"))) {
			request.setAttribute("accountNo", PropertyReader.getValue("error.require", "accountNo"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("accountHolderName"))) {
			request.setAttribute("accountHolderName", PropertyReader.getValue("error.require", "accountHolderName"));
			pass = false;
		}else if(DataValidator.isName(request.getParameter("accountHolderName"))) {
			request.setAttribute("accountHolderName", "Invalid Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("accountType"))) {
			request.setAttribute("accountType", PropertyReader.getValue("error.require", "accountType"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("branch"))) {
			request.setAttribute("branch", PropertyReader.getValue("error.require", "branch"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("balance"))) {
			request.setAttribute("balance", PropertyReader.getValue("error.require", "balance"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "phoneNo"));
			pass = false;
		}else if(DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("phoneNo", "Invalid phoneNo Length"));
			pass = false;
		}else if(DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("phoneNo", "Invalid Mobile No"));
			pass = false;
		}
		
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		BankBean bean = new BankBean();
		
		bean.setAccountNumber(DataUtility.getString(request.getParameter("accountNo")));
		bean.setAccountHolderName(DataUtility.getString(request.getParameter("accountHolderName")));
		bean.setAccountType(DataUtility.getString(request.getParameter("accountType")));
		bean.setBranch(DataUtility.getString(request.getParameter("branch")));
		bean.setBalance(DataUtility.getDouble(request.getParameter("balance")));
		bean.setBalance(DataUtility.getDouble(request.getParameter("phoneNo")));
		
		populateDTO(bean, request);
		return bean;
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BankModel model = new BankModel();
		BankBean bean = (BankBean)populateBean(request);
		try {
			model.add(bean);
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Bank User Added Successfully", request);
		} catch (ApplicationException | DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Account No already exists", request);
		}
	}
	

	@Override
	protected String getView() {
		return ORSView.BANK__VIEW;
	}

}
