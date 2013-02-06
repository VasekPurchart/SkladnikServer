package cz.cvut.jboss.storagecycle.Api.Remote;

import cz.cvut.jboss.storagecycle.VendingMachine.Recipe;

/**
 *
 * @author vasek
 */
public class RecipeDTO {

	public long recipeId;

	public long position;

	public RecipeDTO() {
	}

	public RecipeDTO(Recipe recipe) {
		recipeId = recipe.getId();
		position = recipe.getPosition();
	}
}
