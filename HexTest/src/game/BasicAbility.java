package game;

import java.awt.Image;
import java.io.Serializable;

/**
 * @author Benjamin Gras
 *
 */
public abstract class BasicAbility implements Serializable{

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8798923692756423636L;
	private GameObject parentGameObject = null;
	
	private Image abilityImage = null;
	
	public GameObject getParentGameObject() {
		return parentGameObject;
	}


	public void setParentGameObject(GameObject parentGameObject) {
		this.parentGameObject = parentGameObject;
	}


	public BasicAbility(GameObject parentGameObject)
	{
		this.parentGameObject = parentGameObject;
	}


	public Image getAbilityImage() {
		return abilityImage;
	}


	public void setAbilityImage(Image abilityImage) {
		this.abilityImage = abilityImage;
	}
	
	public void init()
	{
		
	}
	
	public void apply()
	{
		
	}
	
	public AbilityActivationType getInitActivationType()
	{
		return AbilityActivationType.NONE;
	}
	
	public AbilityActivationType getApplyActivationType()
	{
		return AbilityActivationType.NONE;
	}
}
