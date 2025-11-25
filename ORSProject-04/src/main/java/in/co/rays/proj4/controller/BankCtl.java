package in.co.rays.proj4.controller;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "BankCtl", urlPatterns = { "/BankCtl" })
public class BankCtl extends BaseCtl {
	
	
	

	@Override
	protected String getView() {
		return ORSView.BANK__VIEW;
	}

}
