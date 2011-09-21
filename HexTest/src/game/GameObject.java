package game;

import hexagon.HexCell;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Benjamin Gras
 *
 */
public class GameObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6450358867929788528L;


	private List<BasicAbility> abilityList = new ArrayList<BasicAbility>();

	private HexCell centerHexCell = null;


	private Image gameObjectImage = null;

	private boolean marked = false;
	
	private String name = "";
	

	public List<BasicAbility> getAbilityList() {
		return abilityList;
	}


	public HexCell getCenterHexCell() {
		return centerHexCell;
	}


	public Image getGameObjectImage() {
		return gameObjectImage;
	}


	public String getName() {
		return name;
	}


	public boolean isMarked() {
		return marked;
	}
	
	
	public void setAbilityList(List<BasicAbility> abilityList) {
		this.abilityList = abilityList;
	}


	public void setCenterHexCell(HexCell centerHexCell) {
		this.centerHexCell = centerHexCell;
	}


	public void setGameObjectImage(Image gameObjectImage) {
		this.gameObjectImage = gameObjectImage;
	}


	public void setMarked(boolean marked) {
		this.marked = marked;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
