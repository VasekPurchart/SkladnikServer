package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.VendingMachine.Recipe;
import cz.cvut.jboss.storagecycle.VendingMachine.VendingMachine;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author vasek
 */
public class VendingMachineDTO {

	public long id;

	public int number;

	public String address;

	public Collection<RecipeDTO> recipes;

	public VendingMachineDTO() {
	}

	public VendingMachineDTO(VendingMachine vendingMachine) {
		id = vendingMachine.getId();
		number = vendingMachine.getNumber();
		address = vendingMachine.getAddress();
		recipes = new ArrayList<RecipeDTO>();
		for (Recipe recipe : vendingMachine.getRecipes()) {
			recipes.add(new RecipeDTO(recipe));
		}
	}
}
