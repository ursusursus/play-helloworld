package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Drug extends Model {

	@Id
	public Long id;

	@Required
	public String name;
	
	@ManyToOne
	public User user;

	public static Finder<Long, Drug> find = new Finder(Long.class, Drug.class);

	public static List<Drug> findAll() {
		return find.all();
	}


}
