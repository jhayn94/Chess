package chess.state.action;

import chess.config.ModelFactory;
import chess.controller.ApplicationStateContext;
import chess.model.ChessBoardModel;
import chess.model.Color;
import chess.model.piece.ChessPiece;
import chess.state.GameState;
import chess.view.core.ChessBoardView;

/**
 * This class contains methods to show or hide the application context menu.
 */
public class ClickedCellState extends GameState {

    private final ModelFactory modelFactory;

    private int row;

    private int col;

    public ClickedCellState(final ApplicationStateContext context, final ModelFactory modelFactory) {
        super(context);
        this.modelFactory = modelFactory;
        this.row = -1;
        this.col = -1;
    }

    @Override
    public void onEnter() {

        if (this.row == -1 || this.col == -1) {
            throw new IllegalStateException("Must set row and column index first.");
        }
        System.out.println("CLICKED CELL " + this.row + " " + this.col);

        // If selection + cell empty -> move piece
        final int selectedCellRow = this.context.getSelectedCellRow();
        final int selectedCellCol = this.context.getSelectedCellCol();

        final ChessBoardModel chessBoardModel = this.context.getChessBoardModel();
        final ChessBoardView chessBoardView = this.context.getChessBoardView();
        final Color newSelectedPieceColor = chessBoardModel.getPieceColorForCell(this.row,
                this.col);
        if (selectedCellRow > -1 && selectedCellCol > -1) {
            final Color oldSelectedPieceColor =
                    chessBoardModel.getPieceColorForCell(selectedCellRow, selectedCellCol);

            if (oldSelectedPieceColor != newSelectedPieceColor) {
                this.doMove(selectedCellRow, selectedCellCol, chessBoardModel, oldSelectedPieceColor);
            } else {
                this.context.setSelectedRow(this.row);
                this.context.setSelectedCol(this.col);
            }

        } else if (Color.NONE != newSelectedPieceColor) {
            this.context.setSelectedRow(this.row);
            this.context.setSelectedCol(this.col);
        }

        // clear style

        // If selected cell, set allowed move style

    }

    private void doMove(final int selectedCellRow, final int selectedCellCol, final ChessBoardModel chessBoardModel, final Color oldSelectedPieceColor) {
        final int selectedPiece = chessBoardModel.getPieceForCell(selectedCellRow, selectedCellCol);
        final ChessPiece.PieceType pieceType = ChessPiece.PieceType.fromCode(selectedPiece);
//                final ChessPiece chessPiece = this.modelFactory.chessPiece(chessBoardModel, pieceType, oldSelectedPieceColor);
        // TODO - verify move is allowed.

        this.clearCell(selectedCellRow, selectedCellCol);
        this.updateBoardWithPiece(this.row, this.col, pieceType, oldSelectedPieceColor);
        this.context.setSelectedRow(-1);
        this.context.setSelectedCol(-1);
    }

    public void setCol(final int col) {
        this.col = col;
    }

    public void setRow(final int row) {
        this.row = row;
    }
}
