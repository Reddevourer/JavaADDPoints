
public class Main {
    public static void main(String[] args) {
        try {
            XMLNode root = new XMLNode("root");
            root.addAttribute("version", "1.0")
                    .addAttribute("encoding", "UTF-8")
                    .addChild(new XMLNode("person").setContent("John Doe").addAttribute("id", "1"))
                    .addChild(new XMLNode("age").setContent("30"))
                    .addChild(new XMLNode("comments").setContent("This is <![CDATA[some <CDATA> content]]>."));

            XMLBuilder xmlBuilder = new XMLBuilder(root);
            xmlBuilder.setXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            XMLNode child1 = new XMLNode("child1");
            child1.addAttribute("name", "Alice");
            root.addChild(child1);

            XMLNode child2 = new XMLNode("child2");
            child2.addAttribute("name", "Bob");
            root.addChild(child2);

            xmlBuilder.toFile("example.xml");
            System.out.println("XML файл успешно создан.");
        } catch (XMLBuilder.XMLException e) {
            e.printStackTrace();
        }
    }
}