import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class XMLBuilder {
    private String rootElement;
    private List<Object> children = new ArrayList<>();
    private Map<String, String> attributes = new HashMap<>();
    private String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public XMLBuilder(XMLNode rootElement) {
        this.rootElement = String.valueOf(rootElement);
    }

    public XMLBuilder addAttribute(String name, String value) {
        attributes.put(name, value);
        return this;
    }

    public XMLBuilder addChild(XMLBuilder child) {
        children.add(child);
        return this;
    }

    public XMLBuilder addChild(String name, String content) {
        children.add(new ChildElement(name, content));
        return this;
    }

    public XMLBuilder addCDataChild(String name, String cdataContent) {
        children.add(new CDataElement(name, cdataContent));
        return this;
    }

    public XMLBuilder setXmlDeclaration(String declaration) {
        this.xmlDeclaration = declaration;
        return this;
    }

    public void toFile(String filePath) throws XMLException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(xmlDeclaration + "\n");
            writer.write("<" + rootElement);

            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                writer.write(" " + entry.getKey() + "=\"" + escapeXml(entry.getValue()) + "\"");
            }

            writer.write(">");
            for (Object child : children) {
                writer.write(child.toString());
            }

            writer.write("</" + rootElement + ">");
        } catch (IOException e) {
            throw new XMLException("Ошибка при записи XML файла.", e);
        }
    }

    private String escapeXml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    public static class XMLException extends Exception {
        public XMLException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private interface XMLComponent {
        String toString();
    }

    private class ChildElement implements XMLComponent {
        private final String name;
        private final String content;

        ChildElement(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String toString() {
            return "<" + name + ">" + escapeXml(content) + "</" + name + ">";
        }
    }

    private class CDataElement implements XMLComponent {
        private final String name;
        private final String cdataContent;

        CDataElement(String name, String cdataContent) {
            this.name = name;
            this.cdataContent = cdataContent;
        }

        public String toString() {
            return "<" + name + "><![CDATA[" + cdataContent + "]]></" + name + ">";
        }
    }
}
