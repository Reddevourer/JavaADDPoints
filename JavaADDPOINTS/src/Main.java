
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

            xmlBuilder.toFile("example.xml");
            System.out.println("XML файл успешно создан.");
        } catch (XMLBuilder.XMLException e) {
            e.printStackTrace();
        }
    }
}