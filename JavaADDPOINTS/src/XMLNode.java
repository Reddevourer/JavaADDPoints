import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLNode {
    private String name;
    private String content;
    private Map<String, String> attributes = new HashMap<>();
    private List<XMLNode> children = new ArrayList<>();

    public XMLNode(String name) {
        this.name = name;
    }

    public XMLNode addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public XMLNode addChild(XMLNode child) {
        children.add(child);
        return this;
    }

    public XMLNode setContent(String content) {
        this.content = content;
        return this;
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