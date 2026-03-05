package raf.graffito.dsw.graffRepository.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class GraffNodeComposite extends GraffNode {
    List<GraffNode> children;

    public GraffNodeComposite(String name, GraffNode parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }
    public List<GraffNode> getChildren() {
        return children;
    }

    public void setChildren(List<GraffNode> children) {
        this.children = children;
    }
    public GraffNodeComposite(String name, GraffNode parent, List<GraffNode> children) {
        super(name, parent);
        this.children = children;
    }

    protected GraffNodeComposite() {
        this.children = new ArrayList<>();
    }

    @JsonIgnore
    public abstract void addChild(GraffNode child);
    @JsonIgnore
    public void addChild(GraffNode child, int index) {
        if (index < 0 || index > children.size()) index = children.size();
        children.add(index, child);
        child.setParent(this);
    }
    @JsonIgnore
    public abstract void removeChild(GraffNode child);

    @JsonIgnore
    public GraffNode getByName(String name) {
        for (GraffNode child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
}