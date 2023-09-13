import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLNode {
    private String name;
    private String content;
    private Map<String, String> attributes = new HashMap<>();
    private List<XMLNode> children = new ArrayList<>();
    private XMLNode parent;

    public XMLNode(String name) {
        this.name = name;
    }

    public XMLNode addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public XMLNode addChild(XMLNode child) {

        child.setParent(this);
        children.add(child);
        return this;
    }

    public XMLNode setContent(String content) {
        this.content = content;
        return this;
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }

    public void removeChild(XMLNode child) {
        children.remove(child);
        child.setParent(null);
    }

    public void addContent(String content) {

        this.content = content;
    }

    public String getName() {
        return name;
    }

    public List<XMLNode> getChildren() {
        return children;
    }

    public XMLNode getParent() {
        return parent;
    }

    private void setParent(XMLNode parent) {
        this.parent = parent;
    }

    private boolean isDescendantOf(XMLNode potentialParent) {
        XMLNode currentNode = this;
        while (currentNode != null) {
            if (currentNode == potentialParent) {
                return true;
            }
            currentNode = currentNode.getParent();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(name);

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            builder.append(" ").append(entry.getKey()).append("=\"").append(escapeXml(entry.getValue())).append("\"");
        }

        if (content != null) {
            builder.append(">").append(escapeXml(content));
            builder.append("</").append(name).append(">");
        } else if (!children.isEmpty()) {
            builder.append(">");
            for (XMLNode child : children) {
                builder.append(child.toString());
            }
            builder.append("</").append(name).append(">");
        } else {
            builder.append("/>");
        }

        return builder.toString();
    }

    private String escapeXml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}