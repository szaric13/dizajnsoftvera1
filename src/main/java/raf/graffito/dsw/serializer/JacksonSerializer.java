package raf.graffito.dsw.serializer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import raf.graffito.dsw.graffRepository.composite.GraffNode;
import raf.graffito.dsw.graffRepository.implementation.Project;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JacksonSerializer {
    private ObjectMapper objectMapper;

    public JacksonSerializer() {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void saveProject(Project project) {
        if (project == null) {
            return;
        }

        String filePath = project.getFilePath();
        if (filePath == null || filePath.isEmpty()) {
            filePath = setProjectPath(project);
        }

        if (filePath == null || filePath.isEmpty()) {
            return;
        }

        try {
            objectMapper.writeValue(new File(filePath), project);
            project.setChanged(false);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving project: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String setProjectPath(Project project) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Project");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON Files (*.json)";
            }
        });

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }
            project.setFilePath(filePath);
            return filePath;
        }
        return null;
    }

    public Project openProject() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Project");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON Files (*.json)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Project project = objectMapper.readValue(selectedFile, Project.class);
                project.setFilePath(selectedFile.getAbsolutePath());
                project.setChanged(false);
                project.setParent(null);
                if (project.getChildren() == null) {
                    project.setChildren(new ArrayList<>());
                } else {
                    for (GraffNode child : project.getChildren()) {
                        setParentRecursively(child, project);
                    }
                }
                return project;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error opening project: " + e.getMessage(),
                        "Open Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public void saveProjectTemplate(Project project) {
        if (project == null) {
            return;
        }

        File templatesDir = new File("resources/templates");
        if (!templatesDir.exists()) {
            templatesDir.mkdirs();
        }

        JFileChooser fileChooser = new JFileChooser(templatesDir);
        fileChooser.setDialogTitle("Save Project as Template");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON Template Files (*.json)";
            }
        });

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }
            try {
                objectMapper.writeValue(new File(filePath), project);
                JOptionPane.showMessageDialog(null, "Template saved successfully!",
                        "Template Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving template: " + e.getMessage(),
                        "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public Project loadProjectTemplate() {
        File templatesDir = new File("resources/templates");
        if (!templatesDir.exists()) {
            templatesDir.mkdirs();
        }

        JFileChooser fileChooser = new JFileChooser(templatesDir);
        fileChooser.setDialogTitle("Load Project Template");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".json");
            }

            @Override
            public String getDescription() {
                return "JSON Template Files (*.json)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Project project = objectMapper.readValue(selectedFile, Project.class);
                project.setFilePath(null);
                project.setChanged(true);
                project.setParent(null);
                if (project.getChildren() == null) {
                    project.setChildren(new ArrayList<>());
                } else {
                    for (GraffNode child : project.getChildren()) {
                        setParentRecursively(child, project);
                    }
                }
                return project;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error loading template: " + e.getMessage(),
                        "Load Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private void setParentRecursively(GraffNode node, GraffNode parent) {
        if (node == null) return;
        node.setParent(parent);
        if (node instanceof raf.graffito.dsw.graffRepository.composite.GraffNodeComposite) {
            raf.graffito.dsw.graffRepository.composite.GraffNodeComposite composite =
                    (raf.graffito.dsw.graffRepository.composite.GraffNodeComposite) node;
            if (composite.getChildren() != null) {
                for (GraffNode child : composite.getChildren()) {
                    setParentRecursively(child, composite);
                }
            }
        }
    }
}

