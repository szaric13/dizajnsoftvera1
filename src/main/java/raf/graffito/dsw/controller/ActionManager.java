package raf.graffito.dsw.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActionManager {

    private ExitAction exitAction;
    private AboutUsAction aboutUsAction;
    private NewProjectAction newProjectAction;
    private DeleteAction deleteAction;
    private EditAction editAction;
    private EditAuthorAction editAuthorAction;

    private SelectStateAction selectStateAction;
    private AddStateAction addStateAction;
    private MoveStateAction moveStateAction;
    private DeleteStateAction deleteStateAction;
    private AddImageAction addImageAction;
    private AddTextAction addTextAction;
    private AddLogoAction addLogoAction;
    private RotateLeftAction rotateLeftAction;
    private RotateRightAction rotateRightAction;
    private UndoAction undoAction;
    private RedoAction redoAction;
    private SaveAction saveAction;
    private SaveAsAction saveAsAction;
    private OpenAction openAction;
    private SaveProjectAsTemplateAction saveProjectAsTemplateAction;
    private LoadTemplateAction loadTemplateAction;
    private LoadImageAction loadImageAction;

    public ActionManager() {
        this.exitAction = new ExitAction();
        this.aboutUsAction = new AboutUsAction();
        this.newProjectAction = new NewProjectAction();
        this.deleteAction = new DeleteAction();
        this.editAction = new EditAction();
        this.editAuthorAction = new EditAuthorAction();

        this.selectStateAction = new SelectStateAction();
        this.addStateAction = new AddStateAction();
        this.moveStateAction = new MoveStateAction();
        this.deleteStateAction = new DeleteStateAction();
        this.addImageAction = new AddImageAction();
        this.addTextAction = new AddTextAction();
        this.addLogoAction = new AddLogoAction();
        this.rotateLeftAction = new RotateLeftAction();
        this.rotateRightAction = new RotateRightAction();
        this.undoAction = new UndoAction();
        this.redoAction = new RedoAction();
        this.saveAction = new SaveAction();
        this.saveAsAction = new SaveAsAction();
        this.openAction = new OpenAction();
        this.saveProjectAsTemplateAction = new SaveProjectAsTemplateAction();
        this.loadTemplateAction = new LoadTemplateAction();
        this.loadImageAction = new LoadImageAction();
    }

    public RotateLeftAction getRotateLeftAction() {
        return rotateLeftAction;
    }

    public RotateRightAction getRotateRightAction() {
        return rotateRightAction;
    }

    public UndoAction getUndoAction() {
        return undoAction;
    }

    public RedoAction getRedoAction() {
        return redoAction;
    }

    public SaveAction getSaveAction() {
        return saveAction;
    }

    public SaveAsAction getSaveAsAction() {
        return saveAsAction;
    }

    public OpenAction getOpenAction() {
        return openAction;
    }

    public SaveProjectAsTemplateAction getSaveProjectAsTemplateAction() {
        return saveProjectAsTemplateAction;
    }

    public LoadTemplateAction getLoadTemplateAction() {
        return loadTemplateAction;
    }

    public LoadImageAction getLoadImageAction() {
        return loadImageAction;
    }
}
