package WizardTD;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import WizardTD.App;

public class MonsterPathfinder {
    private static final char START = 'X';
    private static final char TARGET = 'W';
    private static final char WALL = 'S';
    private static final char WALL2 = ' ';
    public static List path = new ArrayList();
    List<Point> pointpath = new ArrayList<>();
    public static int difficult;


    public static List main(String[] args) {
        List<List<Point>> allPaths = new ArrayList<>();
        difficult = 0;
        String struct = App.struct;
        String[] map = struct.split("\n");
        List<Point> startPoints = findEdgeStartPoints(map, difficult);

        for (Point startPoint : startPoints) {
            List<Point> path = findPath(map, startPoint);
            if (path != null) {
                allPaths.add(path);
            }
        }

        return allPaths;
    }

    // find the start points on the map
    public static List<Point> findEdgeStartPoints(String[] map,int difficult) {
        List<Point> startPoints = new ArrayList<>();
        int rows = 20;
        int cols = 20;
            try{
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (j==0 || j==cols-1 ||i==0 || i==rows-1){
                        char currentChar = map[i].charAt(j);
                        // 如果当前字符是 'X'，则检查上、下、左、右四个方向的字符
                        if (currentChar == START) {
                            boolean isXUp = (i > 0 && map[i - 1].charAt(j) == 'X');
                            boolean isXDown = (i < rows - 1 && map[i + 1].charAt(j) == 'X');
                            boolean isXLeft = (j > 0 && map[i].charAt(j - 1) == 'X');
                            boolean isXRight = (j < cols - 1 && map[i].charAt(j + 1) == 'X');
                            int falsecount=0;
                            if (!isXUp) {
                                falsecount++;
                            }
                            if (!isXDown) {
                                falsecount++;
                            }
                            if (!isXLeft) {
                                falsecount++;
                            }
                            if (!isXRight) {
                                falsecount++;
                            }
                            if (falsecount==3){
                                startPoints.add(new Point(j, i));
                            }
                        }
                    }
                    
                    
                }
        }
            }catch (ArrayIndexOutOfBoundsException e) {
                
            } catch (StringIndexOutOfBoundsException e) {
                    
            }

        
        return startPoints;
    }
    


    // find the path to the target
    public static List<Point> findPath(String[] map, Point start) {
        // copy the map to a temp map
        String[] temp_map = Arrays.copyOf(map, map.length);
    
        int rows = temp_map.length;
        int cols = temp_map[0].length();
        boolean[][] visited = new boolean[rows][cols];
        Stack<Point> stack = new Stack<>();
        stack.push(start);
    
        while (!stack.isEmpty()) {
            Point current = stack.peek();
            int x = current.x;
            int y = current.y;
    
            if (temp_map[y].charAt(x) == TARGET) {
                List<Point> path = reconstructPath(current);
                // 在这里将 x 和 y 放大 32 倍
                for (Point point : path) {
                    point.x *= 32;
                    point.y *= 32;
                }
                return path;
            }
    
            visited[y][x] = true;
            boolean foundNextMove = false;
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};
    
            try {
                for (int i = 0; i < 4; i++) {
                    int newX = x + dx[i];
                    int newY = y + dy[i];
                    if (isValidMove(temp_map, newX, newY, visited)) {
                        Point next = new Point(newX, newY);
                        next.parent = current; // set parent
                        stack.push(next);
                        foundNextMove = true;
                    }
                }
                if (!foundNextMove) {
                    // if no valid move, mark the current point as a dead end
                    temp_map[y] = temp_map[y].substring(0, x) + WALL + temp_map[y].substring(x + 1);
                    stack.pop();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            } catch (StringIndexOutOfBoundsException e) {
                break;
            }
        }
        return null; // path not found
    }
    


    // check the movement is valid or not
    private static boolean isValidMove(String[] map, int x, int y, boolean[][] visited) {
        int rows = map.length;
        int cols = map[0].length();
        return x >= 0 && x < cols && y >= 0 && y < rows && !visited[y][x] && map[y].charAt(x) != WALL&& map[y].charAt(x) != WALL2;
        //return  map[y].charAt(x) != WALL && map[y].charAt(x) != WALL2 && x >= 0 && x < cols && y >= 0 && y < rows;
    }




    // generate the path from the target
    public static List<Point> reconstructPath(Point target) {
        List<Point> path = new ArrayList<>();
        Point current = target;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }


    // showing the point
    public static class Point {
        int x, y;
        Point parent;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }


}