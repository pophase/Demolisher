package core;
import hexagon.HexCellModel;

import java.io.Serializable;

/**
 * @author hypno
 * @date 19.09.2011
 */
public class GameModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HexCellModel hexCellModel;

	public GameModel() {
		hexCellModel = new HexCellModel();
	}

	public HexCellModel getHexCellModel() {
		return hexCellModel;
	}

	public void setHexCellModel(HexCellModel hexCellModel) {
		this.hexCellModel = hexCellModel;
	}

}
