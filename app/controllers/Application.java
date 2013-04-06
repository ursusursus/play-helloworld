package controllers;

import java.util.List;

import models.Drug;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	static Form<User> userForm = Form.form(User.class);
	static Form<Drug> drugForm = Form.form(Drug.class);

	public static Result index() {
		// session().clear();
		// System.out.println("hello");
		return redirect(routes.Application.drugs());
	}

	public static Result login() {
		return ok(login.render(userForm));
	}

	public static Result logout() {
		System.out.println("logout");
		
		session().remove("user_id");
		return redirect(routes.Application.login());
	}

	public static Result register() {
		System.out.println("register");

		return ok(register.render(userForm));
	}

	public static Result createUser() {
		Form<User> filledForm = userForm.bindFromRequest();
		User user = filledForm.get();
		user.save();
		return redirect(routes.Application.drugs());
	}

	public static Result authenticate() {
		System.out.println("authenticate");

		Form<User> filledForm = userForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			System.out.println("authenticate # chyba vo forme");
			return redirect(routes.Application.login());
		}
		
		User user = filledForm.get();
		User authenticatedUser = User.authenticate(user.email, user.password);
		if (authenticatedUser == null) {
			System.out.println("wrong username or password");
			return redirect(routes.Application.login());
		}
		
		session("user_id", String.valueOf(authenticatedUser.id));
		return redirect(routes.Application.drugs());
	}

	public static Result drugs() {
		String userId = session("user_id");
		if (userId == null) {
			System.out.println("You need to login first");
			return redirect(routes.Application.login());
		}

		User loggedUser = User.findById(Long.parseLong(userId));
		// List<Drug> drugsList = Drug.findAll();

		return ok(drugs.render(loggedUser));
	}

	public static Result newDrug() {
		String userId = session("user_id");
		if (userId == null) {
			System.out.println("You need to login first");
			return redirect(routes.Application.login());
		}
		return ok(newDrug.render(drugForm));
	}

	public static Result create() {
		String userId = session("user_id");
		if (userId == null) {
			System.out.println("You need to login first");
			return redirect(routes.Application.login());
		}
		
		Form<Drug> filledForm = drugForm.bindFromRequest();

		Drug drug = filledForm.get();
		drug.user = User.findById(Long.parseLong(userId));
		drug.save();
		
		return redirect(routes.Application.drugs());

	}

}
