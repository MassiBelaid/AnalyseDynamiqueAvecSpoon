package analyse;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Rank.RankDir;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import java.io.File;
import java.io.IOException;
import static guru.nidi.graphviz.model.Factory.*;
public class GraphVizMain {
    public static void main(String[] args) {
        Graph g = graph("example1").directed().graphAttr().with(Rank.dir(RankDir.LEFT_TO_RIGHT)).nodeAttr().with(Font.name("arial")).linkAttr().with("class", "link-class").with(node("a").with(Color.RED).link(node("b")), // .with(attr("weight", 5), Style.DASHED)
        node("b").link(to(node("c"))));
        try {
            Graphviz.fromGraph(g).height(100).render(Format.PNG).toFile(new File("./ex1.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        MutableGraph g2 = mutGraph("example1").setDirected(true).add(mutNode("a").add(Color.RED).addLink(mutNode("b")));
        try {
            Graphviz.fromGraph(g2).width(200).render(Format.PNG).toFile(new File("./ex1m.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}