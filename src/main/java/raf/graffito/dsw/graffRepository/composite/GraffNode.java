package raf.graffito.dsw.graffRepository.composite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import raf.graffito.dsw.graffRepository.implementation.Presentation;
import raf.graffito.dsw.graffRepository.implementation.Project;
import raf.graffito.dsw.graffRepository.implementation.Slide;
import raf.graffito.dsw.observer.Publisher;
import raf.graffito.dsw.observer.Subscriber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "node_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Project.class, name = "project"),
        @JsonSubTypes.Type(value = Presentation.class, name = "presentation"),
        @JsonSubTypes.Type(value = Slide.class, name = "slide"),
})
@Getter
@Setter
public abstract class GraffNode implements Publisher {
    @JsonIgnore
    protected List<Subscriber> subs;
    private String name;
    @JsonIgnore
    private GraffNode parent;
    public GraffNode(String name, GraffNode parent) {
        this.name = name;
        this.parent = parent;
        this.subs = new ArrayList<>();
    }

    protected GraffNode() {
        this.subs = new ArrayList<>();
    }
    @JsonIgnore
    public GraffNode getParent() {
        return parent;
    }
    @JsonIgnore
    public GraffNodeComposite getParent(int x) {
        return (GraffNodeComposite) parent;
    }
    @JsonIgnore
    public void setParent(GraffNode parent) {
        this.parent = parent;
    }
    public void setName(String name) throws IOException {
        this.name = name;
        notifySubscribers(this);
    }
    @JsonIgnore
    public GraffNode getDecoratorBase() {
        return this;
    }
    @JsonIgnore
    public boolean isDecorator() {
        return false;
    }
    @JsonIgnore
    public List<GraffNode> getChildren() {
        return new ArrayList<>();
    }
    @JsonIgnore
    public void addChild(GraffNode child) {}
    @JsonIgnore
    public void removeChild(GraffNode child) {}
    @JsonIgnore
    public List<Subscriber> getSubs() {
        return subs;
    }
    public GraffNode findByName(String name) {

        if (this.name.equals(name)) return this;

        if (this instanceof GraffNodeComposite composite) {
            for (GraffNode child : composite.getChildren()) {
                GraffNode found = child.findByName(name);
                if (found != null) return found;
            }
        }
        return null;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraffNode other) {
            return this.getName().equals(other.getName());
        }
        return false;
    }
}
