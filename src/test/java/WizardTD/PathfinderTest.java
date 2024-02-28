package WizardTD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathfinderTest {

    private MonsterPathfinder pathfinder;
    private  String[] map;
    private static final String[] map2 = {
            "S   S    X   S    SS",
            " SS S S  X S   S SSS",


            " S   S  SX S S   SSS",


            "XXXXX S  X S SS   SS",


            " S  X X  X   S    SS",


            "   XXXXXXXXXXXXXX  S",
            "  S   X     S   X  S",
            "   S  XS  S    XXS ",
            "      S   XXXXXXX  ",
            "    S  S  X S      ",
            " S        X  S  S SS",


            "      S   X  S   S S",
            "          X    S   ",


            "          XS       ",
            "   WXXXXXXX      S ",
            "              S  S ",
            "                  S",
            "  S      S S    S  ",
            "     S  SS  S  S S S",


            "                 SS"
    };
    public List<MonsterPathfinder.Point> path;
    public List<MonsterPathfinder.Point> startPoint;
    @BeforeEach
    public void setUp() {
        pathfinder = new MonsterPathfinder();
        String struct = "      S       S X SS\n" +
                        "S  S   S    S   X   \n" +
                        "     S  S XXXXXXXXXX\n" +
                        "   S   S  X        S\n"+
                        "           W  S S   \n"+
                        "     S  S      S  S \n";
        map = struct.split("\n");
        startPoint=pathfinder.findEdgeStartPoints(map, 0);

    }

    // Test the pathfinder can find the startpoint correctly
    @Test
    public void testFindstartpoint() {
        assertEquals("(16, 0)", pathfinder.findEdgeStartPoints(map, 0).get(0).toString(), "Should be point 16,0");
    }
    //.\gradlew test
    @Test
    public void testGenpath() {
        List<MonsterPathfinder.Point> startPoint2=pathfinder.findEdgeStartPoints(map2, 0);
        List<MonsterPathfinder.Point> path = pathfinder.findPath(map2, startPoint2.get(0));
        assertEquals("(288, 0)", path.get(0).toString(), "Should be start at point 16,0");
        assertEquals("(96, 448)", path.get(path.size()-1).toString(), "The end should be point 15,0");
    }

}
