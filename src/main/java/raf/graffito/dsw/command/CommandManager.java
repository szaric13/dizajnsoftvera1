package raf.graffito.dsw.command;

import raf.graffito.dsw.gui.swing.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    private List<AbstractCommand> commands;
    private int currentCommand;

    public CommandManager() {
        commands = new ArrayList<>();
        currentCommand = 0;
    }

    public void addCommand(AbstractCommand command) {
        while (commands.size() > currentCommand) {
            commands.remove(commands.size() - 1);
        }
        commands.add(command);
        currentCommand++;
        command.doCommand();
        updateActions();
    }

    public void undoCommand() {
        if (currentCommand > 0) {
            currentCommand--;
            commands.get(currentCommand).undoCommand();
            updateActions();
        }
    }

    public void redoCommand() {
        if (currentCommand < commands.size()) {
            commands.get(currentCommand).doCommand();
            currentCommand++;
            updateActions();
        }
    }

    public boolean canUndo() {
        return currentCommand > 0;
    }

    public boolean canRedo() {
        return currentCommand < commands.size();
    }

    private void updateActions() {
        if (MainFrame.getInstance() != null && MainFrame.getInstance().getActionManager() != null) {
            MainFrame.getInstance().getActionManager().getUndoAction().updateEnabledState();
            MainFrame.getInstance().getActionManager().getRedoAction().updateEnabledState();
        }
    }
}

