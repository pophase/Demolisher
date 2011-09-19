package core;

import hexagon.HexCell;

/**
 * Klasse wird vom View mit den Eingabedaten gefüttert (bspw. User klickt Cell
 * mit ID 61). Der Controller enthält alle Stati, um mehrere
 * hintereinanderfolgende Eingaben als ein Kommando zu interpretieren und führt
 * diese Befehle im Model aus, sofern gültig.
 * 
 * @author hypno
 * @date 19.09.2011
 */
public class GameController {

	GameModel gameModel;
	HexCell[] cells;

	public GameController(GameModel gameModel) {
		super();
		this.gameModel = gameModel;
		cells = gameModel.getHexCellModel().getCells();
	}

	public void commandCellClicked(int i) {
		cells[i].toggleSelection();
	}
}
