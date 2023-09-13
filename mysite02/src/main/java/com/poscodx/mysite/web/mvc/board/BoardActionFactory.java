package com.poscodx.mysite.web.mvc.board;


import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class BoardActionFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
//		if("delete".equals(actionName)) {
//			action = new DeleteAction();
//		} else if("deleteform".equals(actionName)) {
//			action = new DeleteFormAction();
//		} else if("write".equals(actionName)) {
//			action = new WriteAction();
//		} else {
//			action = new ListAction();
//		}
		
		if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("viewform".equals(actionName)) {
			action = new ViewFormAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else { 
			action = new ListAction();
		}
		       
		return action;
	}
}